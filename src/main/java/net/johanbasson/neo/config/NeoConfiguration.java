package net.johanbasson.neo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.johanbasson.neo.core.bus.*;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@EnableKafka
@Configuration
public class NeoConfiguration {


    @Autowired
    private DataSource dataSource;

    @Bean
    public DataSourceConnectionProvider connectionProvider() {
        return new DataSourceConnectionProvider(new TransactionAwareDataSourceProxy(dataSource));
    }

    @Bean
    public DefaultDSLContext dsl() {
        return new DefaultDSLContext(jooqConfiguration());
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

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

    private DefaultConfiguration jooqConfiguration() {
        DefaultConfiguration jooqConfiguration = new DefaultConfiguration();
        jooqConfiguration.set(connectionProvider());
        return jooqConfiguration;
    }
}
