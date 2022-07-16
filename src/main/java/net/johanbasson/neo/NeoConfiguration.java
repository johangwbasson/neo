package net.johanbasson.neo;

import net.johanbasson.cqen.*;
import net.johanbasson.cqen.core.command.CommandBus;
import net.johanbasson.cqen.core.command.DefaultCommandBus;
import net.johanbasson.cqen.core.events.DefaultEventBus;
import net.johanbasson.cqen.core.events.EventBus;
import net.johanbasson.cqen.core.notification.DefaultNotificationBus;
import net.johanbasson.cqen.core.notification.NotificationBus;
import net.johanbasson.cqen.kafka.KafkaConfiguration;
import net.johanbasson.cqen.kafka.command.KafkaCommandDispatcher;
import net.johanbasson.cqen.kafka.command.KafkaCommandReceiver;
import net.johanbasson.cqen.kafka.events.KafkaEventDispatcher;
import net.johanbasson.cqen.kafka.events.KafkaEventReceiver;
import net.johanbasson.cqen.kafka.notification.KafkaNotificationDispatcher;
import net.johanbasson.cqen.kafka.notification.KafkaNotificationReceiver;
import net.johanbasson.cqen.rabbitmq.notification.NotificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NeoConfiguration {

    @Bean
    public KafkaConfiguration kafkaConfiguration(
            @Value("${kafka.servers}") String servers,
            @Value("${kafka.groupId}") String groupId) {
        return new KafkaConfiguration(servers, groupId);
    }

    @Bean
    public CommandDispatcher commandDispatcher(KafkaConfiguration cfg) {
        return new KafkaCommandDispatcher(cfg);
    }

    @Bean
    public EventDispatcher eventDispatcher(KafkaConfiguration cfg) {
        return new KafkaEventDispatcher(cfg);
    }

    @Bean
    public NotificationDispatcher notificationDispatcher(KafkaConfiguration cfg) {
        return new KafkaNotificationDispatcher(cfg);
    }

    @Bean
    public CommandReceiver commandReceiver(KafkaConfiguration cfg, CommandBus commandBus) throws CommandException {
        CommandReceiver receiver = new KafkaCommandReceiver(cfg, (DefaultCommandBus) commandBus);
        receiver.start();
        return receiver;
    }

    @Bean
    public EventReceiver eventReceiver(KafkaConfiguration cfg, EventBus eventBus) throws EventException {
        EventReceiver receiver = new KafkaEventReceiver(cfg, (DefaultEventBus) eventBus);
        receiver.start();
        return receiver;
    }

    @Bean
    public NotificationReceiver notificationReceiver(KafkaConfiguration cfg, NotificationBus bus) throws NotificationException {
        NotificationReceiver receiver = new KafkaNotificationReceiver(cfg, (DefaultNotificationBus) bus);
        receiver.start();
        return receiver;
    }
}
