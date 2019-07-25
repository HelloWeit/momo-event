package cn.weit.service;

import akka.actor.*;
import akka.japi.Creator;
import akka.routing.ConsistentHashingPool;
import akka.routing.RoundRobinPool;
import akka.util.Timeout;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import javax.annotation.PreDestroy;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

/**
 * @author weitong
 */
@Service
@Slf4j
public class AkkaService implements InitializingBean {
    private ActorSystem system;

    private ActorRef actor;

    private static final String SYSTEM_NAME = "momo";

    private static final int TIME_OUT = 120;

    public static final int NUM = 128;

    private class DeadLetterActor extends UntypedActor {
        @Override
        public void onReceive(Object message) {
            if (message instanceof DeadLetter) {
                DeadLetter deadLetter = (DeadLetter) message;
                log.warn("deadLetter -> {}", deadLetter);
            } else {
                unhandled(message);
            }
        }

        Props props() {
            return Props.create(new DeadLetterActor.AnCreator());
        }

        private class AnCreator implements Creator<DeadLetterActor> {
            private static final long serialVersionUID = -7230234526212382194L;

            AnCreator() {
            }

            @Override
            public DeadLetterActor create() throws Exception {
                return new DeadLetterActor();
            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Config conf = ConfigFactory.parseReader(new InputStreamReader(this.getClass().getResourceAsStream("akka.conf")));
        system = ActorSystem.create(SYSTEM_NAME, conf);
        DeadLetterActor deadLetterActor = new DeadLetterActor();
        actor = system.actorOf(deadLetterActor.props());
        system.eventStream().subscribe(actor, DeadLetter.class);
    }

    @PreDestroy
    public void shutdown() {
        system.stop(actor);
    }

    public ActorRef roundRobinPool(Props props, String name, int size) {
        ActorPath actorPath = system.child(name);
        Timeout timeout = new Timeout(Duration.create(TIME_OUT, TimeUnit.SECONDS));
        Future<ActorRef> f = system.actorSelection(actorPath).resolveOne(timeout);
        ActorRef ref;
        try {
            ref = Await.result(f, timeout.duration());
        } catch (Exception e) {
            ref = system.actorOf(new RoundRobinPool(size).props(props), name);
        }
        return ref;
    }
}
