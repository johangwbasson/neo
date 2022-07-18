package net.johanbasson.neo.core;

public interface EventDispatcher {

    void publish(Object event);
}
