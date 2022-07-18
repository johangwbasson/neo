package net.johanbasson.neo.core.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.johanbasson.neo.core.Envelope;
import net.johanbasson.neo.core.EventDispatcher;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static net.johanbasson.neo.common.Constants.EVENTS;

@Component
@Slf4j
public class KafkaEventDispatcher implements EventDispatcher {

    private final ObjectMapper mapper;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaEventDispatcher(ObjectMapper mapper, KafkaTemplate<String, String> kafkaTemplate) {
        this.mapper = mapper;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publish(Object event) {
        try {
            String json = mapper.writeValueAsString(new Envelope(event.getClass().getName(), event));
            kafkaTemplate.send(EVENTS, json);
        } catch (Exception ex) {
            log.error("Error sending event", ex);
            throw new RuntimeException(ex);
        }
    }
}
