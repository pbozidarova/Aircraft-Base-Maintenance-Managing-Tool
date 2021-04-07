package managing.tool.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

//@ControllerAdvice
public class GlobalUncaughtExceptionAdvice extends ResponseEntityExceptionHandler implements Thread.UncaughtExceptionHandler {

    private static Logger LOGGER = LoggerFactory.getLogger(GlobalUncaughtExceptionAdvice.class);


    @Override
    public void uncaughtException(Thread t, Throwable e) {
        LOGGER.error("Unhandled exception caught!", e);


    }
    @ExceptionHandler
    protected ResponseEntity<Object> handleConflict(
            Exception ex, WebRequest request) {
        Map<String, String> bodyOfResponse = new HashMap<>();
        bodyOfResponse.put("message", "Something is wrong! Please try again later or contact the support!");

//        String bodyOfResponse = "Something is wrong! Please try again later or contact the support!";

        if (ex instanceof FoundInDb ) {
            return new ResponseEntity(ex, HttpStatus.NOT_ACCEPTABLE);
        }
        if ( ex instanceof NotFoundInDb) {
            return new ResponseEntity(ex, HttpStatus.NOT_FOUND);
        }

        try {
            return super.handleException(ex, request);
        } catch (Exception e) {
            return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
        }



    }



}
