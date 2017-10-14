package me.angelovdejan.billsmanager.api.responses;

public abstract class Response {
    private String message;

    Response(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
