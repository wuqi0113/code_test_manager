package com.chainfuture.code.service;

public interface OrginMessageService {
    void saveCode(String proAddress, String originMessage, long codeId, String signMessage);
}
