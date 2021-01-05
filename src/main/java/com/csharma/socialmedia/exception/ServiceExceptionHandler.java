package com.csharma.socialmedia.exception;

import com.csharma.socialmedia.model.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ServiceExceptionHandler {

    private static final int STATUS_CODE = 1100;
    private static final String RESPONSE_FAILURE = "FAILURE";

    @ExceptionHandler
    public ResponseEntity<Object> handleServiceException(final ServiceException serviceException) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(buildServiceError(serviceException));
    }

    private Error buildServiceError(ServiceException serviceException) {
        StringBuilder exceptionMessage = new StringBuilder(serviceException.getMessage());

        if (null != serviceException.getCause()) {
            exceptionMessage.append(serviceException.getCause().getMessage());
        }
        return new Error(STATUS_CODE, RESPONSE_FAILURE, exceptionMessage.toString());
    }
}
