package com.tonyleisurecentre.bookingsystem.controlleradvice;

import com.tonyleisurecentre.bookingsystem.exception.*;
import com.tonyleisurecentre.bookingsystem.response.ErrMsg;
import com.tonyleisurecentre.bookingsystem.response.GenericResponse;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.ServletException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * It is the Exception handler to construct a readable response to the client.
 */

@ControllerAdvice
public class ControllerExceptionsHandler {

    /*
    The Handler to handle the validations
    It will handle the all the validations for any request
     */
    @ExceptionHandler({MethodArgumentNotValidException .class})
    public ResponseEntity<GenericResponse> validationHandler(MethodArgumentNotValidException ex)  {
        List<ErrMsg> errorList = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> {
                    var errMsg = new ErrMsg();
                    var fieldName = fieldError.getObjectName().concat(".").concat(fieldError.getField());
                    errMsg.setCode("60400");
                    errMsg.setDescription(fieldName.concat(":").concat(fieldError.getDefaultMessage()));
                    return errMsg;
                        })
                .collect(Collectors.toList());
        GenericResponse genericResponse = new GenericResponse<>();
        genericResponse.setErrorMessages(List.of(errorList));
        genericResponse.setStatus(ex.getStatusCode().value());
        var responseEntity = new ResponseEntity<GenericResponse>(genericResponse, ex.getStatusCode());
        return responseEntity;
    }

    //Handler the request with the method not supported
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<GenericResponse> methodNotSupportedHandler(
            HttpRequestMethodNotSupportedException ex,
            WebRequest request
    ) {
        var errMsg = new ErrMsg();
        errMsg.setDescription("The supported method: "
                .concat(Arrays.stream(ex.getSupportedMethods()).findFirst().get()));
        errMsg.setCode("60405");
        var response = new GenericResponse<Object>();
        response.setErrorMessages(List.of(errMsg));
        return new ResponseEntity<GenericResponse>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler({ BaseException.class,
            SaveException.class,
            NotFoundException.class,
            UserAlreadyExistedException.class,
            SectionAlreadyExistedException.class})
    public ResponseEntity<GenericResponse> exceptionHandler(BaseException ex, WebRequest request) {
        if (ex instanceof SaveException) {
            SaveException unfe = (SaveException) ex;
            System.out.println("ControllerExceptionsHandler::exceptionHandler:SaveException: " + ex.getErrorMessages());
            var response = new GenericResponse<Object>();
            response.setErrorMessages(unfe.getErrorMessages());
            var responseEntity = new ResponseEntity<GenericResponse>(response, HttpStatusCode.valueOf(200));
            return responseEntity;
        } else if (ex instanceof NotFoundException) {
            NotFoundException unfe = (NotFoundException) ex;
            System.out.println("ControllerExceptionsHandler::exceptionHandler:NotFoundException: " + ex.getErrorMessages());
            var response = new GenericResponse<Object>();
            response.setErrorMessages(unfe.getErrorMessages());
            var responseEntity = new ResponseEntity<GenericResponse>(response, HttpStatus.NOT_FOUND);
            return responseEntity;
        } else if (ex instanceof UserAlreadyExistedException) {
            UserAlreadyExistedException unfe = (UserAlreadyExistedException) ex;
            System.out.println("ControllerExceptionsHandler::exceptionHandler:UserAlreadyExistedException: " + ex.getErrorMessages());
            var response = new GenericResponse<Object>();
            response.setErrorMessages(unfe.getErrorMessages());
            var responseEntity = new ResponseEntity<GenericResponse>(response, HttpStatus.CONFLICT);
            return responseEntity;
        } else if (ex instanceof SectionAlreadyExistedException) {
            SectionAlreadyExistedException unfe = (SectionAlreadyExistedException) ex;
            System.out.println("ControllerExceptionsHandler::exceptionHandler:SectionAlreadyExistedException: " + ex.getErrorMessages());
            var response = new GenericResponse<Object>();
            response.setErrorMessages(unfe.getErrorMessages());
            var responseEntity = new ResponseEntity<GenericResponse>(response, HttpStatus.CONFLICT);
            return responseEntity;
        }else if (ex instanceof BaseException) {
            HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
            BaseException unfe = (BaseException) ex;
            List<ErrMsg> errorMessages = unfe.getErrorMessages();
            var response = new GenericResponse<Object>();
            response.setStatus(status.value());
            response.setErrorMessages(errorMessages);
            System.out.println("ControllerExceptionsHandler::exceptionHandler:response: " + response);
            var responseEntity = new ResponseEntity<GenericResponse>(response, HttpStatus.NOT_ACCEPTABLE);
            return responseEntity;
        }
        var responseEntity = new ResponseEntity<GenericResponse>(new GenericResponse<Object>(), HttpStatus.ACCEPTED);
        return responseEntity;
    }

}
