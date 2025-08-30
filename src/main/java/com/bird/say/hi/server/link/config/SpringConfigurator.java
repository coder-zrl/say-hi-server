package com.bird.say.hi.server.link.config;

import jakarta.websocket.server.ServerEndpointConfig;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author Bird <coder-zrl@qq.com>
 * Created on 2025-08-30
 */
@Component
public class SpringConfigurator extends ServerEndpointConfig.Configurator implements ApplicationContextAware {

    private static volatile ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringConfigurator.context = applicationContext;
    }

    @Override
    public <T> T getEndpointInstance(Class<T> clazz) throws InstantiationException {
        if (context == null) {
            throw new InstantiationException("Spring context is not initialized");
        }
        return context.getBean(clazz);
    }
}
