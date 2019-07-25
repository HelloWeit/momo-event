package cn.weit.handler;

import cn.weit.constant.EventName;
import cn.weit.handler.impl.AccountEventHandler;
import cn.weit.handler.impl.EmailEventHandler;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.util.List;

/**
 * @author weitong
 */
public class InitHandler implements ServletContextAware {

    @Autowired
    private HandlerManager handlerManager;

    @Autowired
    private AccountEventHandler accountEventHandler;

    @Autowired
    private EmailEventHandler emailEventHandler;


    @Override
    public void setServletContext(ServletContext servletContext) {
        loadHandler();
    }

    private void loadHandler() {
        //todo 建议从数据库进行加载
        List<EventHandler> eventHandlerList = Lists.newArrayList();
        eventHandlerList.add(accountEventHandler);
        eventHandlerList.add(emailEventHandler);
        handlerManager.putHandlers(EventName.SSO_MY_CHANGE_PASS, eventHandlerList);
    }
}
