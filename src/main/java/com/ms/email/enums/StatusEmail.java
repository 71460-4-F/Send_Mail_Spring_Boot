package com.ms.email.enums;

public enum StatusEmail {
    PROCESSING("Processing email"),
    SEND("Sended email"),
    ERROR("Error send email");

    private String status;

    StatusEmail(String status){
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    
}
