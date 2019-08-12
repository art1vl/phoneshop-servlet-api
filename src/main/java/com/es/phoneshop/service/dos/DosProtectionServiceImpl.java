package com.es.phoneshop.service.dos;

import com.es.phoneshop.model.dos.SessionDosFilterStructure;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DosProtectionServiceImpl implements DosProtectionService{
    private static DosProtectionServiceImpl instance;

    private Map<String, SessionDosFilterStructure> countMap;
    private Long oneMinute;
    private final Long MAX_AMOUNT_OF_REQUESTS_PER_MINUTE = 20L;

    private DosProtectionServiceImpl() {
        countMap = Collections.synchronizedMap(new HashMap<>());
        oneMinute = 60000L;
    }

    synchronized public static DosProtectionServiceImpl getInstance() {
        if (instance == null){
            instance = new DosProtectionServiceImpl();
        }
        return instance;
    }

    @Override
    public boolean isAllowed(String ip) {
        SessionDosFilterStructure item = countMap.get(ip);
        if(item == null) {
            item = new SessionDosFilterStructure();
            countMap.put(ip, item);
        }
        if(new Date().getTime() - item.getDate().getTime() > oneMinute) {
            item.reset();
        }
        item.incrementLong();
        return item.getAmountOfRequests() - MAX_AMOUNT_OF_REQUESTS_PER_MINUTE <= 0;
    }
}
