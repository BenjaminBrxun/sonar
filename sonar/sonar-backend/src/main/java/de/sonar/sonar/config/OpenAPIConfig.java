package de.sonar.sonar.config;

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
                        .title("Sonar Backend API")
                        .description("""
                                MQTT Topics and Communication Flow:
                                
                                Register Event
                                - request/register-event: Send or receive event registration request for a new event with status UNDER_EDITING.
                                - reply/register-event: Receive event registration confirmation (The newly registered event with ID and status UNDER_EDITING).
                                
                                Process Event
                                - request/process-event: Send or receive event processing request for an existing event in any state.
                                - reply/process-event: Send or receive event processing confirmation (The updated event with new status).
                                
                                Delete Event
                                - request/delete-event: Send or receive event deletion request for an existing event in any state.
                                - reply/delete-event: Send or receive event deletion confirmation (the update event with status DELETED). The event will be completed deleted after 30 days.
                                
                                Decline Event
                                - request/decline-event: Send or receive event decline request for an existing event with status IN_REVIEW.
                                - reply/decline-event: Send or receive event decline confirmation (the updated event with status DECLINED).
                                
                                Cancel Event
                                - request/cancel-event: Send or receive event cancellation request for an existing event with status DEPLOYED.
                                - reply/cancel-event: Send or receive cancellation confirmation (the updated event with status CANCELLED).
                                """
                        ));
    }
    
}
