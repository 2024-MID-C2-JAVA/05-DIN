package com.bank.management.usecase.logservice;

import com.bank.management.Transaction;
import com.bank.management.gateway.LogRepository;

public class SaveLogSucessUseCase {

    private final LogRepository logRepository;

    public SaveLogSucessUseCase(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public void apply(String trx) {
        logRepository.saveLogSucess(trx);
    }
}
