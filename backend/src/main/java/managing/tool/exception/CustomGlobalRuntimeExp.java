package managing.tool.exception;

public class CustomGlobalRuntimeExp extends RuntimeException{

    private String causedBy;

    public CustomGlobalRuntimeExp(String message, String causedBy){
        super(message);
        this.causedBy = causedBy;
    }

    public String getCausedBy() {
        return causedBy;
    }

    public CustomGlobalRuntimeExp setCausedBy(String causedBy) {
        this.causedBy = causedBy;
        return this;
    }
}

