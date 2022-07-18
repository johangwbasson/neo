package net.johanbasson.neo.core.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.johanbasson.neo.core.CommandDispatcher;
import net.johanbasson.neo.core.Envelope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaCommandDispatcher implements CommandDispatcher {

    @Value("${topic.command.producer}")
    private String topicName;

    private final ObjectMapper mapper;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaCommandDispatcher(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper mapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.mapper = mapper;
    }

    @Override
    public void dispatch(Object command) {
        try {
            String json = mapper.writeValueAsString(new Envelope(command.getClass().getName(), command));
            kafkaTemplate.send(topicName, json);
        } catch (Exception ex) {
            log.error("Error sending command", ex);
            throw new RuntimeException(ex);
        }
    }
}
