package managing.tool.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE)
public class FoundInDb extends CustomGlobalCaughtExp {

    public FoundInDb(String message, String causedBy) {
        super(message, causedBy);
    }
}


