package de.sonar.sonar.mqtt.base.reply;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@SpringBootTest
@Slf4j
public class AbstractReplyMqttConfigTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void loadAllMessageChannels() {
        log.info("=== MessageChannel Beans ===");
        for (String beanName : applicationContext.getBeanDefinitionNames()) {
            Object bean = applicationContext.getBean(beanName);
            if (bean instanceof MessageChannel) {
                log.info("Channel: {} - Type: {}", beanName, bean.getClass().getName());
            }
        }

    }

    @Test
    void loadAllIntegrationFlows() {
        log.info("=== IntegrationFlow Beans ===");
        for (String beanName : applicationContext.getBeanDefinitionNames()) {
            Object bean = applicationContext.getBean(beanName);
            if (bean instanceof IntegrationFlow) {
                log.info("Flow: {} - Type: {}", beanName, bean.getClass().getName());
            }
        }

    }

    @Test
    void loadAllMessageHandlers() {
        log.info("=== MessageHandler Beans ===");
        for (String beanName : applicationContext.getBeanDefinitionNames()) {
            Object bean = applicationContext.getBean(beanName);
            if (bean instanceof MessageHandler) {
                log.info("Handler: {} - Type: {}", beanName, bean.getClass().getName());
            }
        }

    }


}
