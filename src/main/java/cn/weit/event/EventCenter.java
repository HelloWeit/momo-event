package cn.weit.event;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.japi.Creator;
import cn.weit.dto.MoEvent;
import cn.weit.handler.HandlerManager;
import cn.weit.service.AkkaService;
import cn.weit.handler.EventHandler;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * @author weitong
 */
@Service
public class EventCenter implements InitializingBean {
    private ActorRef actorRef;
    @Autowired
    private AkkaService akkaService;
    @Autowired
    private HandlerManager handlerManager;

    public void publishEvent(final MoEvent event) {
        if (!event.isSync()) {
            actorRef.tell(event, ActorRef.noSender());
        } else {
            final Collection<EventHandler> handlers = handlerManager.getHandlers(event.getName());
            for (EventHandler handler : handlers) {
                handler.handle(event);
            }
        }
    }



    @Override
    public void afterPropertiesSet() throws Exception {
        EventActor eventActor = new EventActor(this);
        actorRef = akkaService.roundRobinPool(eventActor.props(this), "newEventActor", AkkaService.NUM);

    }

    private class EventActor extends UntypedActor {

        private EventCenter eventCenter;


        public EventActor(EventCenter eventCenter) {
            this.eventCenter = eventCenter;
        }

        public Props props(final EventCenter eventCenter) {
            return Props.create(new AnCreator(eventCenter));
        }

        @Override
        public void onReceive(Object o) {
            if (o instanceof MoEvent) {
                MoEvent event = (MoEvent) o;
                handle(event);
            } else {
                unhandled(o);
            }
        }

        private void handle(MoEvent event) {

        }

        private class AnCreator implements Creator<EventActor> {

            private static final long serialVersionUID = -1712824021270008385L;
            private EventCenter eventCenter;


            public AnCreator(EventCenter eventCenter) {
                this.eventCenter = eventCenter;
            }


            @Override
            public EventActor create() {
                return new EventActor(eventCenter);
            }
        }
    }
}
