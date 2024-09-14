package com.hcl.doconnect;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.hcl.doconnect.model.User;
import com.hcl.doconnect.repository.UserRepository;
import com.hcl.doconnect.service.UserService;
 
@SpringBootTest
public class UserServiceTest {
 
    @MockBean
    private UserRepository userRepository;
 
    @Autowired
    private UserService userService;
 
    @Test
    void testFindById_UserExists() {
        // Arrange
        Long userId = 1L;
        User mockUser = new User(userId, "testuser", "password", "USER");
 
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
 
        // Act
        User foundUser = userService.findById(userId);
 
        // Assert
        assertNotNull(foundUser);
        assertEquals(mockUser, foundUser);
    }
 
    @Test
    void testFindById_UserNotExists() {
        // Arrange
        Long userId = 1L;
 
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
 
        // Act
        User foundUser = userService.findById(userId);
 
        // Assert
        assertNull(foundUser);
    }
 
    @Test
    void testFindByUsername_UserExists() {
        // Arrange
        String username = "testuser";
        User mockUser = new User(1L, username, "password","USER2");
 
        when(userRepository.findByUsername(username)).thenReturn(mockUser);
 
        // Act
        User foundUser = userService.findByUsername(username);
 
        // Assert
        assertNotNull(foundUser);
        assertEquals(mockUser, foundUser);
    }
 
    @Test
    void testFindByUsername_UserNotExists() {
        // Arrange
        String username = "testuser";
 
        when(userRepository.findByUsername(username)).thenReturn(null);
 
        // Act
        User foundUser = userService.findByUsername(username);
 
        // Assert
        assertNull(foundUser);
    }
 
    @Test
    void testDeleteUser() {
        // Arrange
        User userToDelete = new User(1L, "testuser", "password","USER");
 
        // Act
        userService.delete(userToDelete);
 
        // Assert
        verify(userRepository, times(1)).delete(userToDelete);
    }
    
    @Test
    void testRegisterUser() {
        // Arrange
        User userToRegister = new User(1L, "testuser", "password","USER");
 
        when(userRepository.save(userToRegister)).thenReturn(userToRegister);
 
        // Act
        User registeredUser = userService.registerUser(userToRegister);
 
        // Assert
        assertNotNull(registeredUser);
        assertEquals("USER", registeredUser.getRole());
        assertEquals(0, registeredUser.getQuestions().size());
        assertEquals(0, registeredUser.getAnswers().size());
    }
 
    @Test
    void testLoginUser_UserExists() {
        // Arrange
        String username = "testuser";
        User mockUser = new User(1L, "testuser", "password","USER");
 
        when(userRepository.findByUsername(username)).thenReturn(mockUser);
 
        // Act
        User loggedInUser = userService.loginUser(username);
 
        // Assert
        assertNotNull(loggedInUser);
        assertEquals(mockUser, loggedInUser);
    }
 
    @Test
    void testLoginUser_UserNotExists() {
        // Arrange
        String username = "testuser";
 
        when(userRepository.findByUsername(username)).thenReturn(null);
 
        // Act
        User loggedInUser = userService.loginUser(username);
 
        // Assert
        assertNull(loggedInUser);
    }
 
    @Test
    void testLogoutUser() {
        // Arrange
        String username = "testuser";
 
        // Act
        userService.logoutUser(username);
 
        // Assert: No specific assertions for logout method
    }
 
    @Test
    void testRegisterAdmin_UserNotExists() {
        // Arrange
        String username = "adminuser";
        User adminToRegister = new User(1L, username, "password","ADMIN");
 
        when(userRepository.findByUsername(username)).thenReturn(null);
        when(userRepository.save(adminToRegister)).thenReturn(adminToRegister);
 
        // Act
        User registeredAdmin = userService.registerAdmin(adminToRegister);
 
        // Assert
        assertNotNull(registeredAdmin);
        assertEquals("ADMIN", registeredAdmin.getRole());
        assertEquals(0, registeredAdmin.getQuestions().size());
        assertEquals(0, registeredAdmin.getAnswers().size());
    }
 
    @Test
    void testRegisterAdmin_UserExists() {
        // Arrange
        String username = "adminuser";
        User existingUser = new User(1L, username, "password","ADMIN");
 
        when(userRepository.findByUsername(username)).thenReturn(existingUser);
 
        // Act
        User registeredAdmin = userService.registerAdmin(existingUser);
 
        // Assert
        assertNull(registeredAdmin);
        verify(userRepository, never()).save(existingUser);
    }
    
    @Test
    void testUpdateUser_UserExists() {
        // Arrange
        Long userId = 1L;
        User existingUser = new User(userId, "existinguser", "password", "USER");
        User updatedUser = new User(userId, "updateduser", "updatedpassword", "ADMIN");
 
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(updatedUser);
       
        // Act
        User result = userService.updateUser(userId, updatedUser);
        
        // Assert
        assertNotNull(result);
        assertEquals(updatedUser.getUsername(), result.getUsername());
        assertEquals(updatedUser.getPassword(), result.getPassword());
        assertEquals(updatedUser.getRole(), result.getRole());
    }
 
    @Test
    void testUpdateUser_UserNotExists() {
        // Arrange
        Long userId = 1L;
        User updatedUser = new User(userId, "updateduser", "updatedpassword", "ADMIN");
 
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
 
        // Act
        User result = userService.updateUser(userId, updatedUser);
 
        // Assert
        assertNull(result);
    }
    
    @Test
    void testGetAllUsers() {
        // Arrange
        List<User> userList = new ArrayList<>();
        userList.add(new User(1L, "user1", "password1", "USER"));
        userList.add(new User(2L, "user2", "password2", "USER"));
 
        when(userRepository.findByRole("USER")).thenReturn(userList);
 
        // Act
        List<User> result = userService.getAllUsers();
 
        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(userList, result);
    }
    
}