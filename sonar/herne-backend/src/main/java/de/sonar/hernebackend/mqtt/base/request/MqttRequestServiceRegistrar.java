package de.sonar.hernebackend.mqtt.base.request;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.NonNull;

import java.util.Map;

/**
 * Registrar for automatic topic configuration of implementations of the {@link AbstractMqttRequestService}.
 * <p>
 * Automatically registers {@link DynamicRequestMqttConfig}s based on {@link RegisterRequestMqttConfig} annotations.
 */
public class MqttRequestServiceRegistrar implements ImportBeanDefinitionRegistrar {

    /**
     * Creates and registers bean definitions for {@link DynamicRequestMqttConfig}s.
     * <p>
     * For each service annotated with {@link RegisterRequestMqttConfig}, creates a corresponding
     * {@link DynamicRequestMqttConfig} bean with the specified topic.
     *
     * @param metadata the metadata for the class being processed
     * @param registry the registry for registering bean definitions
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, @NonNull BeanDefinitionRegistry registry) {
        Map<String, Object> attributes = metadata.getAnnotationAttributes(RegisterRequestMqttConfig.class.getName());
        if (attributes == null) return;

        String topic = (String) attributes.get("topic");

        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(DynamicRequestMqttConfig.class);
        builder.addPropertyValue("topic", topic);

        registry.registerBeanDefinition(topic + "_requestMqttConfig", builder.getBeanDefinition());
    }

}
