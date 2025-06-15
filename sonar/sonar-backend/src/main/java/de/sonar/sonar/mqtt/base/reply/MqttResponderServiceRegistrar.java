package de.sonar.sonar.mqtt.base.reply;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.NonNull;

import java.util.Map;

public class MqttResponderServiceRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, @NonNull BeanDefinitionRegistry registry) {
        Map<String, Object> attributes = metadata.getAnnotationAttributes(RegisterResponderMqttConfig.class.getName());
        if (attributes == null) return;

        String topic = (String) attributes.get("topic");

        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(DynamicResponderMqttConfig.class);
        builder.addPropertyValue("topic", topic);

        registry.registerBeanDefinition(topic + "_responderMqttConfig", builder.getBeanDefinition());
    }
}
