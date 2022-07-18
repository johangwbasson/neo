package net.johanbasson.neo.core.bus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DefaultEventBus implements EventBus {

    private final Map<Class<?>, Set<HandlerMetaData>> handlerMapping = new HashMap<>();
    private static final Logger log = LoggerFactory.getLogger(DefaultEventBus.class);

    private record HandlerMetaData(
            Object object,
            Method method) {
    }

    public void register(Object obj) {
        for (Method method : obj.getClass().getMethods()) {
            if (hasHandlerAnnotation(method) && method.getParameterCount() == 1) {
                Class<?> clazz = method.getParameters()[0].getType();
                Set<HandlerMetaData> handlers = handlerMapping.get(clazz);
                if (handlers != null) {
                    handlers = new HashSet<>();
                }
                handlers.add(new HandlerMetaData(obj, method));
                handlerMapping.remove(clazz);
                handlerMapping.put(clazz, handlers);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void publish(Object event) {
        Set<HandlerMetaData> handlers = handlerMapping.get(event.getClass());
        handlers.forEach(md -> {
            try {
                md.method.invoke(md.object, event);
            } catch (Exception ex) {
                log.error("Error invoking event. Class: {}, Method: {}", md.object.getClass().getName(), md.method.getName());
            }
        });
    }

    private boolean hasHandlerAnnotation(Method method) {
        return method.getAnnotation(CommandHandler.class) != null;
    }
}
