package cz.games.lp.backend.service_impl;

import cz.games.lp.backend.service.LoggerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerServiceImpl implements LoggerService {

    private static LoggerService loggerService;

    public static LoggerService getInstance() {
        if (loggerService == null) {
            loggerService = new LoggerServiceImpl();
        }
        return loggerService;
    }

    private LoggerServiceImpl() {

    }

    @Override
    public <T> Logger log(Class<T> clazz) {
        return LoggerFactory.getLogger(clazz);
    }
}
