package com.lucas.gym_management.infrastructure.adapters.inbound.rest;

import com.lucas.gym_management.factory.UserFactory;
import com.lucas.gym_management.infrastructure.adapters.inbound.rest.dtos.request.UpdateUserRequest;
import com.lucas.gym_management.infrastructure.adapters.inbound.rest.dtos.response.CreateUserResponse;
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
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateUser_whenDataIsValid() throws Exception {
        var createUserRequest = UserFactory.createUserRequest();

        var result = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUserRequest)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isString())
                .andExpect(jsonPath("$.name").value("any-student-name"))
                .andExpect(jsonPath("$.login").value("any-login-student"))
                .andExpect(jsonPath("$.email").value("student@email.com"))
                .andReturn();

        var responseBody = result.getResponse().getContentAsString();
        CreateUserResponse output = objectMapper.readValue(responseBody, CreateUserResponse.class);
        UUID userId = output.id();

        mockMvc.perform(get("/users/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(createUserRequest.name()))
                .andExpect(jsonPath("$.login").value(createUserRequest.login()));
    }

    @Test
    @Sql(scripts = "/sql/users-setup.sql",
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldReturnAListOfUsers_whenThereAreUsers() throws Exception {
        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    @Sql(scripts = "/sql/clear-setup.sql",
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldReturnEmptyList_whenThereAreNoUsers() throws Exception {
        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Sql(scripts = "/sql/users-setup.sql",
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldReturnAUser_whenUserIdIsValid() throws Exception {
        var userId = "11111111-1111-1111-1111-111111111111";

        mockMvc.perform(get("/users/{id}", userId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Any-manager-name"))
                .andExpect(jsonPath("$.email").value("manager@test.com"))
                .andExpect(jsonPath("$.login").value("manager.login"))
                .andExpect(jsonPath("$.phone").value("81999990001"))
                .andExpect(jsonPath("$.address.street").value("Any-street-name"))
                .andExpect(jsonPath("$.address.number").value("any-number"))
                .andExpect(jsonPath("$.address.neighborhood").value("any-neighborhood"))
                .andExpect(jsonPath("$.address.zipCode").value("any-zip-code"))
                .andExpect(jsonPath("$.address.city").value("any-city"))
                .andExpect(jsonPath("$.address.state").value("any-state"));
    }

    @Test
    @Sql(scripts = "/sql/users-setup.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldThrowNotFoundException_whenUserIdIsNotValid() throws Exception {
        UUID userId = UUID.randomUUID();

        mockMvc.perform(get("/users/{id}", userId))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("User with id %s not found".formatted(userId.toString())));
    }

    @Test
    @Sql(scripts = "/sql/users-setup.sql",
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldUpdateUser_whenDataIsValid() throws Exception {
        var userId = "44444444-4444-4444-4444-444444444444";
        var managerId = "11111111-1111-1111-1111-111111111111";
        var input = UserFactory.buildUpdateStudentRequest();

        mockMvc.perform(patch("/users/{id}", userId)
                        .header("x-user-id", managerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.name").value("Update-student-name"))
                .andExpect(jsonPath("$.email").value("update@email.com"))
                .andExpect(jsonPath("$.phone").value("update-phone-num"))
                .andExpect(jsonPath("$.address.street").value("update-street-name"))
                .andExpect(jsonPath("$.address.number").value("update-number"))
                .andExpect(jsonPath("$.address.neighborhood").value("update-neighborhood"))
                .andExpect(jsonPath("$.address.zipCode").value("update-zip-code"))
                .andExpect(jsonPath("$.address.city").value("update-city"))
                .andExpect(jsonPath("$.address.state").value("update-state"));
    }

    @Test
    @Sql(scripts = "/sql/users-setup.sql",
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldThrowConflictException_whenEmailIsAlreadyRegistered() throws Exception {
        var userId = "44444444-4444-4444-4444-444444444444";
        var managerId = "11111111-1111-1111-1111-111111111111";
        var input = new UpdateUserRequest(null,
                "f.student@test.com",
                null, null, null, null, null, null, null);

        mockMvc.perform(patch("/users/{id}", userId)
                        .header("x-user-id", managerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.detail").value("Email %s already used".formatted(input.email())));
    }

    @Test
    @Sql(scripts = "/sql/users-setup.sql",
    executionPhase =  Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldDeleteUser_whenUserIdIsValid() throws Exception {
        var userId = "33333333-3333-3333-3333-333333333333";
        var managerId = "11111111-1111-1111-1111-111111111111";

        mockMvc.perform(delete("/users/{id}", userId)
                        .header("x-user-id", managerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/users/{id}", "33333333-3333-3333-3333-333333333333"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @Sql(scripts = "/sql/users-setup.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldThrowNotAuthorizedException_whenLoggedInUserIsNotManager() throws Exception{
        var userId = "33333333-3333-3333-3333-333333333333";
        var instructorId = "22222222-2222-2222-2222-222222222222";

        mockMvc.perform(delete("/users/{id}", userId)
                        .header("x-user-id", instructorId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.detail").value("User cannot perform delete"));
    }

    @Test
    @Sql(scripts = "/sql/users-setup.sql",
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldThrowNotFoundException_whenUserToDeleteIsNotValid() throws Exception {
        var userId = UUID.randomUUID();
        var managerId = "11111111-1111-1111-1111-111111111111";

        mockMvc.perform(delete("/users/{id}", userId)
                        .header("x-user-id", managerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("User with id %s not found".formatted(userId.toString())));
    }

    @Test
    @Sql(scripts = "/sql/users-setup.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldThrowConflictException_whenUserToDeleteHasActiveMembership() throws Exception {
        var userId = "44444444-4444-4444-4444-444444444444";
        var managerId = "11111111-1111-1111-1111-111111111111";

        mockMvc.perform(delete("/users/{id}", userId)
                        .header("x-user-id", managerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.detail").value("Cannot delete a student with active membership, id %s".formatted(userId)));
    }

}
