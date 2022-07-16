package net.johanbasson.neo.core;

import net.johanbasson.cqen.core.command.CommandBus;
import net.johanbasson.cqen.core.command.DefaultCommandBus;
import net.johanbasson.cqen.core.events.DefaultEventBus;
import net.johanbasson.cqen.core.events.EventBus;
import net.johanbasson.cqen.core.notification.DefaultNotificationBus;
import net.johanbasson.cqen.core.notification.NotificationBus;
import net.johanbasson.cqen.core.query.DefaultQueryBus;
import net.johanbasson.cqen.core.query.QueryBus;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Configuration
public class BusConfiguration {

    @Bean
    public CommandBus commandBus(ApplicationContext context) {
        DefaultCommandBus commandBus = new DefaultCommandBus();
        context.getBeansWithAnnotation(Service.class)
                .forEach((s, o) -> commandBus.register(o));
        return commandBus;
    }

    @Bean
    public EventBus eventBus(ApplicationContext context) {
        DefaultEventBus eventBus = new DefaultEventBus();
        context.getBeansWithAnnotation(Service.class)
                .forEach((s, o) -> eventBus.register(o));
        return eventBus;
    }

    @Bean
    public QueryBus queryBus(ApplicationContext context) {
        DefaultQueryBus queryBus = new DefaultQueryBus();
        context.getBeansWithAnnotation(Service.class)
                .forEach((s, o) -> queryBus.register(o));
        return queryBus;
    }

    @Bean
    public NotificationBus notificationBus(ApplicationContext context) {
        DefaultNotificationBus notificationBus = new DefaultNotificationBus();
        context.getBeansWithAnnotation(Service.class)
                .forEach((s, o) -> notificationBus.register(o));
        return notificationBus;
    }
}
