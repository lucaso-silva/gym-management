package com.lucas.gym_management.gymclass.infrastructure.adapters.inbound.rest;

import com.lucas.gym_management.gymclass.application.ports.inbound.create.CreateGymClassOutput;
import com.lucas.gym_management.gymclass.application.ports.inbound.manage_students.EnrollStudentInput;
import com.lucas.gym_management.gymclass.factory.GymClassFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class GymClassControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final String BASE_URL = "/api/gym-classes";

    @Test
    @Sql(scripts = {"/sql/clear-setup.sql", "/sql/gymclass/gymclass-setup.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldCreateAGymClass_whenDataIsValid() throws Exception {
        var createGymClassInput = GymClassFactory.buildCreateGymClassInput();

        var result = mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createGymClassInput)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isString())
                .andExpect(jsonPath("$.name").value(createGymClassInput.name()))
                .andExpect(jsonPath("$.instructorId").value(createGymClassInput.instructorId().toString()))
                .andExpect(jsonPath("$.capacity").value(createGymClassInput.capacity()))
                .andExpect(jsonPath("$.schedule.dayOfWeek").value(createGymClassInput.schedule().dayOfWeek().toString()))
                .andExpect(jsonPath("$.schedule.room").value(createGymClassInput.schedule().room()))
                .andExpect(jsonPath("$.schedule.startTime").value(createGymClassInput.schedule().startTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"))))
                .andExpect(jsonPath("$.schedule.endTime").value(createGymClassInput.schedule().endTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"))))
                .andReturn();

        var responseBody = result.getResponse().getContentAsString();
        CreateGymClassOutput output = objectMapper.readValue(responseBody, CreateGymClassOutput.class);
        var gymClassId = output.id();

        mockMvc.perform(get(BASE_URL + "/" + gymClassId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(createGymClassInput.name()))
                .andExpect(jsonPath("$.schedule.dayOfWeek").value(createGymClassInput.schedule().dayOfWeek().toString()))
                .andExpect(jsonPath("$.schedule.room").value(createGymClassInput.schedule().room()));
    }

    @Test
    @Sql(scripts = {"/sql/clear-setup.sql", "/sql/gymclass/gymclass-setup.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldReturnAGymClass_whenIdIsValid() throws Exception {
        var gymClassId = "33333333-3333-3333-3333-333333333333";

        mockMvc.perform(get(BASE_URL + "/" + gymClassId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(gymClassId))
                .andExpect(jsonPath("$.name").value("Yoga Class"))
                .andExpect(jsonPath("$.capacity").value(15))
                .andExpect(jsonPath("$.schedule.dayOfWeek").value("MONDAY"))
                .andExpect(jsonPath("$.schedule.room").value("Room A"))
                .andExpect(jsonPath("$.schedule.startTime").value("07:00:00"))
                .andExpect(jsonPath("$.schedule.endTime").value("07:45:00"));
    }

    //TODO: implement GlobalExceptionHandler
//    @Test
//    @Sql(scripts = {"/sql/clear-setup.sql", "/sql/gymclass/gymclass-setup.sql"},
//            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//    void shouldThrowNotFoundException_whenGymClassIdIsInvalid() throws Exception{
//        mockMvc.perform(get(BASE_URL+"/"+ UUID.randomUUID()))
//                .andExpect(status().isNotFound());
//    }

    @Test
    @Sql(scripts = {"/sql/clear-setup.sql", "/sql/gymclass/gymclass-setup.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldReturnAListOfGymClasses_whenThereAreGymClasses() throws Exception {
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.[0].name").value("Yoga Class"))
                .andExpect(jsonPath("$.[0].schedule.dayOfWeek").value("MONDAY"))
                .andExpect(jsonPath("$.[0].schedule.room").value("Room A"))
                .andExpect(jsonPath("$.[0].schedule.startTime").value("07:00:00"))
                .andExpect(jsonPath("$.[0].schedule.endTime").value("07:45:00"))
                .andExpect(jsonPath("$.[1].name").value("Crossfit Class"))
                .andExpect(jsonPath("$.[1].schedule.dayOfWeek").value("TUESDAY"))
                .andExpect(jsonPath("$.[1].schedule.room").value("Room C"))
                .andExpect(jsonPath("$.[1].schedule.startTime").value("08:00:00"))
                .andExpect(jsonPath("$.[1].schedule.endTime").value("08:45:00"));
    }

    @Test
    @Sql(scripts = "/sql/clear-setup.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldReturnAnEmptyList_whenThereAreNoGymClasses() throws Exception {
        mockMvc.perform(get(BASE_URL))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Sql(scripts = {"/sql/clear-setup.sql",
            "/sql/gymclass/gymclass-setup.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldEnrollAStudentToAGymClass_whenIdsAreValid() throws Exception {
        var gymClassId = "33333333-3333-3333-3333-333333333333";
        var enrollStudentInput = new EnrollStudentInput(UUID.fromString("33333333-3333-3333-3333-333333333333"));

        mockMvc.perform(get(BASE_URL+"/"+gymClassId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numEnrolledStudents").value(0));

        mockMvc.perform(post(BASE_URL + "/" + gymClassId + "/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(enrollStudentInput)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numEnrolledStudents").value(1));
    }

    @Sql(scripts = {"/sql/clear-setup.sql",
    "/sql/gymclass/gymclass-setup.sql"},
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Test
    void shouldUnenrollAStudentFromAGymClass_whenIdsAreValid() throws Exception {
        var gymClassId = "33333333-3333-3333-3333-333333333333";
        var enrollStudentInput = new EnrollStudentInput(UUID.fromString("33333333-3333-3333-3333-333333333333"));
        var studentId = enrollStudentInput.studentId();

        mockMvc.perform(post(BASE_URL+"/"+gymClassId+"/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(enrollStudentInput)))
                .andExpect(jsonPath("$.numEnrolledStudents").value(1));

        mockMvc.perform(get(BASE_URL+"/"+gymClassId))
                .andExpect(jsonPath("$.numEnrolledStudents").value(1));

        mockMvc.perform(delete(BASE_URL+"/"+gymClassId+"/students/"+studentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numEnrolledStudents").value(0));

        mockMvc.perform(get(BASE_URL+"/"+gymClassId))
                .andExpect(jsonPath("$.numEnrolledStudents").value(0));
    }

    @Test
    @Sql(scripts = {"/sql/clear-setup.sql",
    "/sql/gymclass/gymclass-setup.sql"},
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldUpdateAGymClass_whenDataIsValid() throws Exception {
        var newInstructorId = UUID.fromString("55555555-5555-5555-5555-555555555555");
        var updateGymClassInput = GymClassFactory.buildUpdateGymClassInput(newInstructorId);
        var gymClassId = "33333333-3333-3333-3333-333333333333";

        mockMvc.perform(get(BASE_URL+"/"+gymClassId))
                .andExpect(jsonPath("$.id").value(gymClassId))
                .andExpect(jsonPath("$.name").value("Yoga Class"));

        mockMvc.perform(patch(BASE_URL+"/"+gymClassId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateGymClassInput)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(gymClassId))
                .andExpect(jsonPath("$.name").value("Updated-gym-class-name"))
                .andExpect(jsonPath("$.capacity").value(10))
                .andExpect(jsonPath("$.schedule.dayOfWeek").value("TUESDAY"))
                .andExpect(jsonPath("$.schedule.room").value("Updated-gym-class-room"))
                .andExpect(jsonPath("$.schedule.startTime").value("12:00:00"))
                .andExpect(jsonPath("$.schedule.endTime").value("12:30:00"));

        mockMvc.perform(get(BASE_URL+"/"+gymClassId))
                .andExpect(jsonPath("$.id").value(gymClassId))
                .andExpect(jsonPath("$.name").value("Updated-gym-class-name"));
    }

    @Test
    @Sql(scripts = {"/sql/clear-setup.sql",
            "/sql/gymclass/gymclass-setup.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldDeleteGymClass_whenIdIsValid() throws Exception {
        var gymClassId = "33333333-3333-3333-3333-333333333333";

        mockMvc.perform(get(BASE_URL))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)));

        mockMvc.perform(get(BASE_URL + "/" + gymClassId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Yoga Class"));

        mockMvc.perform(delete(BASE_URL + "/" + gymClassId))
                .andExpect(status().isNoContent());

        mockMvc.perform(get(BASE_URL))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)));

//        mockMvc.perform(get(BASE_URL+"/"+gymClassId))
//                .andExpect(status().isNotFound());
    }

    //TODO: implement error scenarios tests for all endpoints

}
