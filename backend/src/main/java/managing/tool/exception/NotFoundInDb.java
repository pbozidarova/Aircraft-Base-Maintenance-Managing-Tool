package managing.tool.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NotFoundInDb extends CustomGlobalRuntimeExp {

    public NotFoundInDb(String message, String causedBy) {
        super(message, causedBy);
    }
}


