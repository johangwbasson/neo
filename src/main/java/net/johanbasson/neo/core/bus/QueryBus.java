package net.johanbasson.neo.core.bus;

import net.johanbasson.neo.users.LoginRequest;

import java.lang.reflect.InvocationTargetException;

public interface QueryBus {
    <T> T query(Object query) throws QueryException;
}
