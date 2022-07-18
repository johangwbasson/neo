package net.johanbasson.neo.core.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.johanbasson.neo.core.Envelope;
import net.johanbasson.neo.core.bus.CommandBus;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaCommandReceiver {

    private final ObjectMapper objectMapper;
    private final CommandBus commandBus;

    public KafkaCommandReceiver(ObjectMapper objectMapper, CommandBus commandBus) {
        this.objectMapper = objectMapper;
        this.commandBus = commandBus;
    }

    @Value("${topic.command.consumer")
    private String topicName;

    @KafkaListener(topics = "${topic.command.consumer}", groupId = "neo")
    public void consume(ConsumerRecord<String, String> payload) {
        try {
            Envelope envelope = objectMapper.readValue(payload.value(), Envelope.class);
            commandBus.send(envelope.payload());
        } catch (JsonProcessingException e) {
            log.error("Error processing command", e);
            throw new RuntimeException(e);
        }
    }
}
