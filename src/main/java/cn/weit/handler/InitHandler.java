package cn.weit.handler;

import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;

/**
 * @author weitong
 */
public class InitHandler implements ServletContextAware {
    @Override
    public void setServletContext(ServletContext servletContext) {
        //todo 启动时候加载一些默认的事件和handler集的映射关系，同时要开发事件注册 事件对于handler集的增删改
    }
}
