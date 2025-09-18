package com.finalproject.MultilinguialAiApp.service;

import org.springframework.stereotype.Service;

@Service
public class NoOpCacheService implements CacheService {
    @Override
    public String get(String key) {
        return null; // no caching yet
    }

    @Override
    public void put(String key, String value) {
        // noop
    }
}
