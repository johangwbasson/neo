package net.johanbasson.neo.core.bus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class DefaultQueryBus implements QueryBus {

    private final Map<Class<?>, HandlerMetaData> handlers = new HashMap<>();
    private static final Logger log = LoggerFactory.getLogger(DefaultQueryBus.class);

    private record HandlerMetaData(Object object, Method method) {}

    public void register(Object obj) {
        log.debug("Finding handler methods for {}", obj.getClass().getName());
        for (Method method : obj.getClass().getMethods()) {
            if (hasHandlerAnnotation(method) && method.getParameterCount() == 1) {
                Class<?> clazz = method.getParameters()[0].getType();
                log.debug("Found Query handler: {}.{}", obj.getClass().getName(), method.getName());
                handlers.put(clazz, new HandlerMetaData(obj, method));
            }
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T query(Object query)  {
        try {
            HandlerMetaData md = handlers.get(query.getClass());
            if (md == null) {
                throw new QueryException(String.format("There is no query handler registered for query: %s", query.getClass().getName()));
            }

            return (T) md.method.invoke(md.object, query);
        } catch (InvocationTargetException | IllegalAccessException ex) {
            throw new QueryException(ex);
        }
    }

    private boolean hasHandlerAnnotation(Method method) {
        return method.getAnnotation(QueryHandler.class) != null;
    }
}
