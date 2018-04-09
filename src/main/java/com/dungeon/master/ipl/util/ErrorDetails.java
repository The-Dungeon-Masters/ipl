package com.dungeon.master.ipl.util;

import javax.ws.rs.core.Response.Status;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class ErrorDetails {
    
    private String message;
    private Status status;

    public ErrorDetails(String message, Status status) {
      super();
      this.message = message;
      this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}
