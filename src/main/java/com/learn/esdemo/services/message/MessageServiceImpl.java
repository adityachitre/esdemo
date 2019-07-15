package com.learn.esdemo.services.message;

import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {

    private static final String MESSAGE = "my message";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
