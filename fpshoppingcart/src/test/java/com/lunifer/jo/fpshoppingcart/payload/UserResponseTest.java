package com.lunifer.jo.fpshoppingcart.payload;

import com.lunifer.jo.fpshoppingcart.dto.UserDTO;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserResponseTest {
    @Test
    void testGettersAndSetters() {

        List<UserDTO> userDTOList = Mockito.mock(ArrayList.class);

        // Mocking
        when(userDTOList.size()).thenReturn(5);

        // Act
        UserResponse userResponse = new UserResponse();
        userResponse.setContent(userDTOList);
        userResponse.setPageNo(1);
        userResponse.setPageSize(10);
        userResponse.setTotalElements(30);
        userResponse.setTotalPages(3);
        userResponse.setLast(true);

        // Assert
        assertEquals(userDTOList, userResponse.getContent());
        assertEquals(1, userResponse.getPageNo());
        assertEquals(10, userResponse.getPageSize());
        assertEquals(30, userResponse.getTotalElements());
        assertEquals(3, userResponse.getTotalPages());
        assertTrue(userResponse.isLast());
        assertEquals(5, userResponse.getContent().size());
    }

    @Test
    void testDefaultConstructor() {
        // Arrange & Act
        UserResponse userResponse = new UserResponse();

        // Assert
        assertNull(userResponse.getContent());
        assertEquals(0, userResponse.getPageNo());
        assertEquals(0, userResponse.getPageSize());
        assertEquals(0, userResponse.getTotalElements());
        assertEquals(0, userResponse.getTotalPages());
        assertFalse(userResponse.isLast());
    }
}
