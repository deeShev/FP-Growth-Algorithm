package org.eltech.ddm.cluster.actors;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import org.eltech.ddm.cluster.actors.messages.ExecuteJob;
import org.eltech.ddm.cluster.actors.messages.ExecuteResult;
import org.eltech.ddm.cluster.actors.messages.JobFailed;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.Step;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

public class ActorWorker extends UntypedActor {

    private ActorRef caller;

    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public void preStart() throws Exception {
        log.info("Starting " + this.getClass().getCanonicalName());
    }

    @Override
    public void onReceive(Object o) throws Exception {
        if (o instanceof ExecuteJob) {
            this.caller = sender();
            ExecuteJob msg = (ExecuteJob) o;
            MiningInputStream inputStream = msg.getMiningInputStream();
            EMiningModel model = msg.getMiningModel();
            Step step = msg.getStep();
            try {
                EMiningModel resultModel = step.runStep(model);
                caller.tell(new ExecuteResult(resultModel), self());
            } catch (MiningException e) {
                caller.tell(new JobFailed(e), self());
            }
        } else
            unhandled(o);
    }
}


