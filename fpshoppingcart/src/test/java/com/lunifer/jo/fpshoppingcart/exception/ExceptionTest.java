package com.lunifer.jo.fpshoppingcart.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
@ExtendWith(MockitoExtension.class)
class ExceptionTest {

    @Mock
    private WebRequest webRequest;

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;
    @Test
    void testErrorDetailConstructor() {

        Date timestamp = new Date();
        String message = "Test Message";
        String detail = "Test Detail";

        ErrorDetail errorDetail = new ErrorDetail(timestamp, message, detail);

        assertEquals(timestamp, errorDetail.getTimestamp());
        assertEquals(message, errorDetail.getMessage());
        assertEquals(detail, errorDetail.getDetail());
    }

    @Test
    void testErrorDetail() {

        Date timestamp = new Date();
        String message = "Test Message";
        String detail = "Test Detail";

        ErrorDetail mockErrorDetail = Mockito.mock(ErrorDetail.class);

        Mockito.when(mockErrorDetail.getTimestamp()).thenReturn(timestamp);
        Mockito.when(mockErrorDetail.getMessage()).thenReturn(message);
        Mockito.when(mockErrorDetail.getDetail()).thenReturn(detail);

        assertEquals(timestamp, mockErrorDetail.getTimestamp());
        assertEquals(message, mockErrorDetail.getMessage());
        assertEquals(detail, mockErrorDetail.getDetail());
    }

    @Test
    void testHandleResourceNotFoundException() {

        ResourceNotFoundException exception = new ResourceNotFoundException("Recurso", "campo", 123);

        ResponseEntity<ErrorDetail> responseEntity = globalExceptionHandler.handleResourceNotFoundException(exception, webRequest);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        ErrorDetail errorDetail = responseEntity.getBody();
        assertEquals("Recurso not found with " + exception.getFieldName() + " : "  + exception.getFieldValue(), errorDetail.getMessage() );
        assertEquals(webRequest.getDescription(false), errorDetail.getDetail());
    }

    @Test
    void testHandleGlobalException() {

        Exception exception = new Exception("Algo salió mal");

        ResponseEntity<ErrorDetail> responseEntity = globalExceptionHandler.handleGlobalException(exception, webRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        ErrorDetail errorDetail = responseEntity.getBody();
        assertEquals("Algo salió mal", errorDetail.getMessage());
        assertEquals(webRequest.getDescription(false), errorDetail.getDetail());
    }


}
