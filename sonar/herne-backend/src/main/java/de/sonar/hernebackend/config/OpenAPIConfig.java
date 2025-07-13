package de.sonar.hernebackend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Herne Backend API Documentation")
                        .description("""
                                REST and MQTT are used for communication in the project.
                                
                                <h2>MQTT API Documentation</h2>
                                
                                The following documentation describes the available MQTT topics for the event lifecycle management.
                                Each operation consists of a pair of two topics. One topic is used for sending a request and the other for receiving a response.
                                
                                The MQTT topics are triggered via the Herne Backend using the REST API for event management (see ‘Event’ tag in Herne Backend API documentation).
                                The Sonar Backend subscribes to the corresponding request topics, processes the payload accordingly and sends a suitable response to the corresponding reply topic.
                                
                                <h3>Register Event</h3>
                                <ul>
                                    <li><strong>request/register-event:</strong> Send or receive event registration request for a new event with status <code>UNDER_EDITING</code>.</li>
                                    <li><strong>reply/register-event:</strong> Receive event registration confirmation (The newly registered event with ID and status <code>UNDER_EDITING</code>).</li>
                                </ul>
                                
                                <h3>Process Event</h3>
                                <ul>
                                    <li><strong>request/process-event:</strong> Send or receive event processing request for an existing event in any state.</li>
                                    <li><strong>reply/process-event:</strong> Send or receive event processing confirmation (The updated event with new status).</li>
                                </ul>
                                
                                <h3>Delete Event</h3>
                                <ul>
                                    <li><strong>request/delete-event:</strong> Send or receive event deletion request for an existing event in any state.</li>
                                    <li><strong>reply/delete-event:</strong> Send or receive event deletion confirmation (the update event with status <code>DELETED</code>).</li>
                                    <li><em>Note: The event will be completely deleted after 30 days.</em></li>
                                </ul>
                                
                                <h3>Decline Event</h3>
                                <ul>
                                    <li><strong>request/decline-event:</strong> Send or receive event decline request for an existing event with status <code>IN_REVIEW</code>.</li>
                                    <li><strong>reply/decline-event:</strong> Send or receive event decline confirmation (the updated event with status <code>DECLINED</code>).</li>
                                </ul>
                                
                                <h3>Cancel Event</h3>
                                <ul>
                                    <li><strong>request/cancel-event:</strong> Send or receive event cancellation request for an existing event with status <code>DEPLOYED</code>.</li>
                                    <li><strong>reply/cancel-event:</strong> Send or receive cancellation confirmation (the updated event with status <code>CANCELLED</code>).</li>
                                </ul>
                                
                                <h2>REST API Documentation</h2>
                                
                                The following documentation describes the available REST APIs.
                                """
                        ));
    }

}
