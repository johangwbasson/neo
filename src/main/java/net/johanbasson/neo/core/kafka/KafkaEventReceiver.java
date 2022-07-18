package net.johanbasson.neo.core.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import net.johanbasson.neo.common.Constants;
import net.johanbasson.neo.core.Envelope;
import net.johanbasson.neo.core.bus.EventBus;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class KafkaEventReceiver {

    @Autowired
    private final ObjectMapper objectMapper;
    @Autowired
    private final EventBus eventBus;

    @KafkaListener(topics = Constants.EVENTS, groupId = "neo")
    public void consume(ConsumerRecord<String, String> payload) {
        try {
            Envelope envelope = objectMapper.readValue(payload.value(), Envelope.class);
            eventBus.publish(envelope.payload());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
