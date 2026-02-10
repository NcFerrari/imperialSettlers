package cz.games.lp.backend.service;

import org.slf4j.Logger;

public interface LoggerService {

    <T> Logger log(Class<T> clazz);
}
