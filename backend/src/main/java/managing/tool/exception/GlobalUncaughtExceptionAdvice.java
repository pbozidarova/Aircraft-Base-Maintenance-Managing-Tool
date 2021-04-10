package managing.tool.exception;

import org.apache.http.protocol.HTTP;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
//@Aspect
//@Component
public class GlobalUncaughtExceptionAdvice extends ResponseEntityExceptionHandler  {

    private static Logger LOGGER = LoggerFactory.getLogger(GlobalUncaughtExceptionAdvice.class);

    @ExceptionHandler
    protected ResponseEntity<Object> handleConflict(
            Exception ex, WebRequest request) throws Exception {
        Map<String, String> bodyOfResponse = new HashMap<>();
        bodyOfResponse.put("message", "Something is wrong! Please try again later or contact the support!");

        if (ex instanceof FoundInDb ) {
            throw ex;
        }
        if ( ex instanceof NotFoundInDb) {
            throw ex;
        }

        try {
            return super.handleException(ex, request);
        } catch (Exception e) {
            return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
        }

    }

//
//    @Pointcut("execution(* managing.tool.e_notification.service.NotificationService.createNotification())")
//    public void catchExceptionGlobally() {}
//
//    @AfterThrowing(pointcut = "within(*.*)", throwing = "caughtError")
//    protected void handleConflict(Throwable caughtError) {
//        LOGGER.error("Unhandled exception caught!", caughtError);
//    }

//
//
//    @Override
//    public void uncaughtException(Thread t, Throwable e) {
//        LOGGER.error("Unhandled exception caught!", e);
//    }
}
