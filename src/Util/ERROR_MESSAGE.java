package Util;

public enum ERROR_MESSAGE {
    DUPLICATE("Duplicate declaration of the identifier:");

    private String message;

    ERROR_MESSAGE(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
