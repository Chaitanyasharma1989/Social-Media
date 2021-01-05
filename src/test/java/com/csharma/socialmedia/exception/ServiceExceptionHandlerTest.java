package com.csharma.socialmedia.exception;

import com.csharma.socialmedia.model.Error;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@RunWith(MockitoJUnitRunner.class)
public class ServiceExceptionHandlerTest extends TestCase {

    private ServiceExceptionHandler serviceExceptionHandler;
    private static final int STATUS_CODE = 1100;
    private static final String RESPONSE_FAILURE = "FAILURE";
    private static final String MESSAGE = "User not found";

    @Before
    public void setUp() {
        serviceExceptionHandler = new ServiceExceptionHandler();
    }

    @Test
    public void shouldAbleToHandlerServiceException() {
        ServiceException serviceException = new ServiceException(MESSAGE);
        ResponseEntity<Object> responseEntity = serviceExceptionHandler.handleServiceException(serviceException);
        Error error = (Error) responseEntity.getBody();

        Assert.assertEquals(error.getCode(), STATUS_CODE);
        Assert.assertEquals(error.getMessage(), MESSAGE);
        Assert.assertEquals(error.getResponseStatus(), RESPONSE_FAILURE);
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}