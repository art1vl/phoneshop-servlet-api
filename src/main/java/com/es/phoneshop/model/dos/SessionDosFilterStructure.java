package com.es.phoneshop.model.dos;

import java.util.Date;

public class SessionDosFilterStructure {
    private Long amountOfRequests;
    private Date date;

    public SessionDosFilterStructure() {
        amountOfRequests = 0L;
        date = new Date();
    }

    public Long getAmountOfRequests() {
        return amountOfRequests;
    }

    public Date getDate() {
        return date;
    }

    public void setAountOfRequests(Long amountOfRequests) {
        this.amountOfRequests = amountOfRequests;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void incrementLong() {
        amountOfRequests++;
    }

    public void reset() {
        amountOfRequests = 0L;
        date = new Date();
    }
}
