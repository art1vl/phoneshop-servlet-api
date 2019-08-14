package com.es.phoneshop.service.dos;

import com.es.phoneshop.model.dos.SessionDosFilterStructure;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class DosProtectionServiceImpl implements DosProtectionService{
    private static DosProtectionServiceImpl instance;

    private ConcurrentMap<String, SessionDosFilterStructure> countMap;
    private final Long MAX_AMOUNT_OF_REQUESTS_PER_MINUTE = 20L;
    private volatile Date lastCleanDate;
    private final Long FIVE_MINUTES = 300000L;

    private DosProtectionServiceImpl() {
        countMap = new ConcurrentHashMap<>();
        lastCleanDate = new Date();
    }

    synchronized public static DosProtectionServiceImpl getInstance() {
        if (instance == null){
            instance = new DosProtectionServiceImpl();
        }
        return instance;
    }

    @Override
    public boolean isAllowed(String ip) {
        if (new Date().getTime() - lastCleanDate.getTime() > FIVE_MINUTES) {
            lastCleanDate = new Date();
            countMap.clear();
        }
        SessionDosFilterStructure item = countMap.computeIfAbsent(ip, (p)->new SessionDosFilterStructure(MAX_AMOUNT_OF_REQUESTS_PER_MINUTE));
        return item.isAllowed();
    }
}
