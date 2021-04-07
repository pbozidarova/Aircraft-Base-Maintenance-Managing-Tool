package managing.tool.exception;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;

public class CustomGlobalCaughtExp extends RuntimeException {

    private String causedBy;

    public CustomGlobalCaughtExp(String message, String causedBy){
        super(message);
//        this.message = message;
        this.causedBy = causedBy;
    }

    public String getCausedBy() {
        return causedBy;
    }

    public CustomGlobalCaughtExp setCausedBy(String causedBy) {
        this.causedBy = causedBy;
        return this;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}

