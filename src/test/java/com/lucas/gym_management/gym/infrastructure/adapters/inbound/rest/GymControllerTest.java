package com.lucas.gym_management.gym.infrastructure.adapters.inbound.rest;

import com.lucas.gym_management.gym.application.ports.inbound.create.CreateGymOutput;
import com.lucas.gym_management.gym.application.ports.inbound.manage_members.AddMemberInput;
import com.lucas.gym_management.gym.factory.GymFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED)
@Sql(scripts = "/sql/clear-setup.sql",
executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class GymControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final String BASE_URL = "/api/gyms";

    @Test
    void shouldCreateAGym_whenDataIsValid() throws Exception {
        var createGymInput = GymFactory.buildCreateGymInput();

        var result = mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createGymInput)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isString())
                .andExpect(jsonPath("$.name").value("Any-gym-name"))
                .andExpect(jsonPath("$.phone").value("any-phone-number"))
                .andReturn();

        var responseBody = result.getResponse().getContentAsString();
        CreateGymOutput output = objectMapper.readValue(responseBody, CreateGymOutput.class);
        var gymId = output.id();

        mockMvc.perform(get(BASE_URL +"/"+ gymId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(createGymInput.name()))
                .andExpect(jsonPath("$.phone").value(createGymInput.phone()));
    }

    @Test
    @Sql(scripts = "/sql/gym/gyms-setup.sql",
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldReturnAGym_whenGymIdIsValid() throws Exception {
        var gymId = "11111111-1111-1111-1111-111111111111";

        mockMvc.perform(get(BASE_URL +"/"+ gymId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gymId").value(gymId))
                .andExpect(jsonPath("$.name").value("First-gym-name"))
                .andExpect(jsonPath("$.phone").value("first-gym-phone-num"))
                .andExpect(jsonPath("$.members").value(0))
                .andExpect(jsonPath("$.address.street").value("First-gym-street-name"))
                .andExpect(jsonPath("$.address.number").value("any-number"))
                .andExpect(jsonPath("$.address.neighborhood").value("any-neighborhood"))
                .andExpect(jsonPath("$.address.city").value("any-city"))
                .andExpect(jsonPath("$.address.state").value("any-state"));
    }

    @Test
    @Sql(scripts = "/sql/gym/gyms-setup.sql",
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldReturnAListOfGyms_whenThereAreGyms() throws Exception {
        mockMvc.perform(get(BASE_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    @Sql(scripts = "/sql/clear-setup.sql",
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldReturnEmptyList_whenThereAreNoGyms() throws Exception {
        mockMvc.perform(get(BASE_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Sql(scripts = "/sql/gym/gyms-setup.sql",
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldReturnNotFound_whenGymIdIsInvalid() throws Exception {
        mockMvc.perform(get(BASE_URL +"/"+ UUID.randomUUID()))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    @Sql(scripts = "/sql/gym/gyms-setup.sql",
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldDeleteAGym_whenGymIdIsValid() throws Exception {
        var gymId = "11111111-1111-1111-1111-111111111111";
        var userId = "11111111-1111-1111-1111-111111111111";

        mockMvc.perform(get(BASE_URL +"/"+ gymId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("First-gym-name"));

        mockMvc.perform(delete(BASE_URL+"/"+gymId)
                        .header("x-user-id", userId))
                .andDo(print())
                .andExpect(status().isNoContent());

        mockMvc.perform(get(BASE_URL +"/"+ gymId))
                .andExpect(status().isNotFound());
    }

    @Test
    @Sql(scripts = "/sql/gym/gyms-setup.sql",
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldAddAMemberToGym_whenNotIncludedYet() throws Exception {
        var gymId = "11111111-1111-1111-1111-111111111111";
        var userId = "11111111-1111-1111-1111-111111111111";
        var memberId = "22222222-2222-2222-2222-222222222222";
        var addMemberInput = new AddMemberInput(UUID.fromString(memberId));

        mockMvc.perform(get(BASE_URL +"/"+ gymId))
                .andExpect(jsonPath("$.gymId").value(gymId))
                .andExpect(jsonPath("$.name").value("First-gym-name"))
                .andExpect(jsonPath("$.members").value(0));

        mockMvc.perform(post(BASE_URL +"/"+ gymId+"/members")
                        .header("x-user-id", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addMemberInput)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gymId").value(gymId))
                .andExpect(jsonPath("$.name").value("First-gym-name"))
                .andExpect(jsonPath("$.members").value(1));
    }

    @Test
    @Sql(scripts = "/sql/gym/gyms-setup.sql",
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldRemoveAMemberFromGym_whenMemberIsIncluded() throws Exception {
        var gymId = "11111111-1111-1111-1111-111111111111";
        var userId = "11111111-1111-1111-1111-111111111111";
        var memberId = "22222222-2222-2222-2222-222222222222";

        mockMvc.perform(post(BASE_URL+"/"+gymId+"/members")
                .header("x-user-id", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new AddMemberInput(UUID.fromString(memberId)))));

        mockMvc.perform(get(BASE_URL+"/"+gymId))
                .andExpect(jsonPath("$.gymId").value(gymId))
                .andExpect(jsonPath("$.name").value("First-gym-name"))
                .andExpect(jsonPath("$.members").value(1));

        mockMvc.perform(delete(BASE_URL +"/"+ gymId+"/members/"+memberId)
                        .header("x-user-id", userId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gymId").value(gymId))
                .andExpect(jsonPath("$.name").value("First-gym-name"))
                .andExpect(jsonPath("$.members").value(0));
    }

    @Test
    @Sql(scripts = "/sql/gym/gyms-setup.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldThrowUserNotMemberException_whenUserIsNotAGymMember() throws Exception {
        var gymId = "11111111-1111-1111-1111-111111111111";
        var userId = "11111111-1111-1111-1111-111111111111";
        var memberId = "22222222-2222-2222-2222-222222222222";
        var invalidMember = "99999999-9999-9999-9999-999999999999";

        mockMvc.perform(post(BASE_URL+"/"+gymId+"/members")
                .header("x-user-id", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new AddMemberInput(UUID.fromString(memberId)))));

        mockMvc.perform(get(BASE_URL+"/"+gymId))
                .andExpect(jsonPath("$.gymId").value(gymId))
                .andExpect(jsonPath("$.name").value("First-gym-name"))
                .andExpect(jsonPath("$.members").value(1));

        mockMvc.perform(delete(BASE_URL +"/"+ gymId+"/members/"+invalidMember)
                        .header("x-user-id", userId))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.detail").value("User is not a gym member"));

        mockMvc.perform(get(BASE_URL+"/"+gymId))
                .andExpect(jsonPath("$.gymId").value(gymId))
                .andExpect(jsonPath("$.name").value("First-gym-name"))
                .andExpect(jsonPath("$.members").value(1));
    }

    @Test
    @Sql(scripts = "/sql/gym/gyms-setup.sql",
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldUpdateAGym_whenDataIsValid() throws Exception{
        var userId = "11111111-1111-1111-1111-111111111111";
        var gymId = "11111111-1111-1111-1111-111111111111";
        var input = GymFactory.buildUpdateGymInput();

        mockMvc.perform(patch(BASE_URL+"/"+gymId)
                        .header("x-user-id", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gymId").value(gymId))
                .andExpect(jsonPath("$.name").value(input.name()))
                .andExpect(jsonPath("$.phone").value(input.phone()))
                .andExpect(jsonPath("$.address.street").value(input.address().street()))
                .andExpect(jsonPath("$.address.number").value(input.address().number()))
                .andExpect(jsonPath("$.address.neighborhood").value(input.address().neighborhood()))
                .andExpect(jsonPath("$.address.city").value(input.address().city()))
                .andExpect(jsonPath("$.address.state").value(input.address().state()));
    }
}
