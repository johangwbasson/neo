package net.johanbasson.neo.core;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface CommandDispatcher {

    void dispatch(Object command) throws JsonProcessingException;

}
