package com.finalproject.MultilinguialAiApp.service;

public interface CacheService {
    String get(String key);
    void put(String key, String value);
}
