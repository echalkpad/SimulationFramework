package smart.campus.simulation.simulator;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.*;
import smart.campus.simulation.messages.InitParking;
import smart.campus.simulation.messages.StartParkingSimulation;
import smart.campus.simulation.messages.StartSimulation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by foerster on 14/01/14.
 */
public class ParkingLot extends UntypedActor {

    private Router router;
    private float value;

    public ParkingLot(int numberOfSensors){
        List<Routee> routees = new ArrayList<Routee>();
        for(int i = 0; i < numberOfSensors ; i++){
            ActorRef r = getContext().actorOf(Props.create(ParkingSensor.class),getSelf().path().name()+"-"+i);
            getContext().watch(r);
            routees.add(new ActorRefRoutee(r));
        }
        router = new Router(new BroadcastRoutingLogic(), routees);
    }

    @Override
    public void onReceive(Object o) throws Exception {
        if(o instanceof InitParking){
            value = ((InitParking)o).getInitVal();
        }
        if(o instanceof StartSimulation){
            StartSimulation message = (StartSimulation)o;
            router.route(new StartParkingSimulation(message.getBegin(),message.getDuration(),message.getInterval(),value),getSelf());
        }
    }
}
