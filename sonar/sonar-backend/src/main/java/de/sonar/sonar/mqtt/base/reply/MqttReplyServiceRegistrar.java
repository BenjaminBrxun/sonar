package de.sonar.sonar.mqtt.base.reply;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.NonNull;

import java.util.Map;

/**
 * Registrar for automatic topic configuration of implementations of the {@link AbstractMqttReplyService}.
 * <p>
 * Automatically registers {@link DynamicReplyMqttConfig}s based on {@link RegisterReplyMqttConfig} annotations.
 */
public class MqttReplyServiceRegistrar implements ImportBeanDefinitionRegistrar {

    /**
     * Creates and registers bean definitions for {@link DynamicReplyMqttConfig}s.
     * <p>
     * For each service annotated with {@link RegisterReplyMqttConfig}, creates a corresponding
     * {@link DynamicReplyMqttConfig} bean with the specified topic.
     *
     * @param metadata the metadata for the class being processed
     * @param registry the registry for registering bean definitions
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, @NonNull BeanDefinitionRegistry registry) {
        Map<String, Object> attributes = metadata.getAnnotationAttributes(RegisterReplyMqttConfig.class.getName());
        if (attributes == null) return;

        String topic = (String) attributes.get("topic");

        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(DynamicReplyMqttConfig.class);
        builder.addPropertyValue("topic", topic);

        registry.registerBeanDefinition(topic + "_replyMqttConfig", builder.getBeanDefinition());
    }

}
