package com.adp.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.MediaType;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.adp.domain.User;
import com.adp.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;


@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)

public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;

    @Test
  public void testGetAll() throws Exception {
    // Arrange
    User user1 = new User();
    user1.setId(1L);
    user1.setName("Augusto Pummell");
    User user2 = new User();
    user2.setId(2L);
    user2.setName("Harry Potter");

    // Assign
    when(userService.getAll())
        .thenReturn(Arrays.asList(user1, user2));

    // Assert
    mockMvc.perform(get("/users"))
        .andExpect(status().isOk())
        .andExpect(content().json("[{'id': 1,'name':'Augusto Pummell'},{'id': 2, 'name':'Harry Potter'}]"));
  }

  @Test
  public void testGetUserById() throws Exception {
    // Arrange
    User user = new User();
    user.setId(1L);
    user.setName("John Doe");

    // Assign
    when(userService.getUser(1L)).thenReturn(Optional.of(user));

    // Act & Assert
    mockMvc.perform(get("/users/1"))
        .andExpect(status().isOk())
        .andExpect(content().json("{'id':1,'name':'John Doe'}"));
  }
    

  @Test
  public void testDeleteUser() throws Exception {

    User existingUser = new User();
    existingUser.setId(1L);
    existingUser.setName("John Doe");
    when(userService.getUser(1L)).thenReturn(Optional.of(existingUser));

    mockMvc.perform(delete("/users/1")).andExpect(status().isNoContent());
  }

  @Test
  public void testDeleteUserNotFound() throws Exception {
    when(userService.getUser(1L)).thenReturn(Optional.empty());

    mockMvc.perform(delete("/users/1")).andExpect(status().isBadRequest());
  }

  @Test
  public void testPutUser() throws Exception {
    // Arrange
    User existingUser = new User();
    existingUser.setId(1L);
    existingUser.setName("John Doe");
    existingUser.setEmail("john.doe@example.com");
    existingUser.setPassword("password");

    User updatedUser = new User();
    updatedUser.setId(1L);
    updatedUser.setName("John Doe Updated");
    updatedUser.setEmail("john.doe.updated@example.com");
    updatedUser.setPassword("newpassword");

    // Assign
    when(userService.getUser(1L)).thenReturn(Optional.of(existingUser));
    when(userService.saveUser(any(User.class))).thenReturn(null);

    // Act & Assert
    mockMvc.perform(put("/users/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(updatedUser)))
        .andExpect(status().isOk())
        .andExpect(content().json(
            "{'id':1,'name':'John Doe Updated','email':'john.doe.updated@example.com','password':'newpassword'}"));
  }

  @Test
  public void testPutUserNotFound() throws Exception {
    // Arrange
    User updatedUser = new User();
    updatedUser.setId(1L);
    updatedUser.setName("John Doe Updated");
    updatedUser.setEmail("john.doe.updated@example.com");
    updatedUser.setPassword("newpassword");

    // Assign
    when(userService.getUser(1L)).thenReturn(Optional.empty());

    // Act & Assert
    mockMvc.perform(put("/users/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(updatedUser)))
        .andExpect(status().isBadRequest())
        .andExpect(content().string("Bad Request"));
  }

  @Test
  public void testPutUserInvalid() throws Exception {
    // Arrange
    User existingUser = new User();
    existingUser.setId(1L);
    existingUser.setName("John Doe");
    existingUser.setEmail("john.doe@example.com");
    existingUser.setPassword("password");

    User invalidUser = new User();
    invalidUser.setId(1L);
    invalidUser.setName(""); // Invalid name

    // Assign
    when(userService.getUser(1L)).thenReturn(Optional.of(existingUser));

    // Act & Assert
    mockMvc.perform(put("/users/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(invalidUser)))
        .andExpect(status().isBadRequest())
        .andExpect(content().string("Bad Request"));
  }

}
