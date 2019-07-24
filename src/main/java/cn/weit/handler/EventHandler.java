package cn.weit.handler;

import cn.weit.dto.MoEvent;

/**
 * @author weitong
 */
public interface EventHandler {
    void handle(MoEvent event);
}
