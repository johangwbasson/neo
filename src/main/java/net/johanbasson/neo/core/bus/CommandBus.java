package net.johanbasson.neo.core.bus;

public interface CommandBus {

    void send(Object command);
}
