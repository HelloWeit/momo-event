package cn.weit.handler;

import cn.weit.constant.EventName;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author weitong
 *  事件和handler的映射关系建议放在数据库中，启动时候记载到内存
 */
@Component
public class HandlerManager {
    private final  Map<EventName, List<EventHandler>> handlerListMap = Maps.newConcurrentMap();

    public List<EventHandler> getHandlers(EventName eventName) {
        return handlerListMap.get(eventName);
    }

    public void putHandle(EventName eventName, EventHandler handler) {
        List<EventHandler> eventHandlers;
        if (handlerListMap.containsKey(eventName)) {
            eventHandlers = handlerListMap.get(eventName);
        } else {
            eventHandlers = Lists.newArrayList();
            handlerListMap.put(eventName, eventHandlers);
        }
        eventHandlers.add(handler);
    }

    public void putHandlers(EventName eventName, List<EventHandler> handlers) {
        List<EventHandler> eventHandlers;
        if (handlerListMap.containsKey(eventName)) {
            eventHandlers = handlerListMap.get(eventName);
        } else {
            eventHandlers = Lists.newArrayList();
            handlerListMap.put(eventName, eventHandlers);
        }
        eventHandlers.addAll(handlers);
    }

}
