package com.bank.management;

import com.bank.management.gateway.MessageListenerGateway;
import com.bank.management.usecase.logservice.SaveLogSucessUseCase;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitlistenerImpl implements MessageListenerGateway {

    private final SaveLogSucessUseCase saveLog;

    public RabbitlistenerImpl(SaveLogSucessUseCase saveLog) {
        this.saveLog = saveLog;
    }

    @Override
    @RabbitListener(queues = "logs.error")
    public void receiveMessage(String message) {
        saveLog.apply(message);
    }
}
