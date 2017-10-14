package me.angelovdejan.billsmanager.api.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChangeStateRequest {
    private String state;

    public ChangeStateRequest(@JsonProperty("state") String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
