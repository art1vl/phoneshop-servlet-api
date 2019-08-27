package com.es.phoneshop.model.dos;

import java.util.Date;

public class SessionDosFilterStructure {
    private Long amountOfRequests;
    private Date date;

    final private Long MAX_AMOUNT_OF_REQUESTS_PER_MINUTE;
    final private Long ONE_MINUTE = 60000L;

    public SessionDosFilterStructure(Long max_amount) {
        amountOfRequests = 0L;
        date = new Date();
        MAX_AMOUNT_OF_REQUESTS_PER_MINUTE = max_amount;
    }

    private void reset() {
        amountOfRequests = 0L;
        date = new Date();
    }

    synchronized public boolean isAllowed() {
        if (new Date().getTime() - date.getTime() > ONE_MINUTE) {
            reset();
        }
        amountOfRequests++;
        return amountOfRequests - MAX_AMOUNT_OF_REQUESTS_PER_MINUTE <= 0;
    }
}
