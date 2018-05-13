package org.eltech.ddm.handlers.actors;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Inbox;
import org.eltech.ddm.cluster.actors.ActorRouter;
import org.eltech.ddm.cluster.actors.messages.ExecuteJob;
import org.eltech.ddm.cluster.actors.messages.ExecuteResult;
import org.eltech.ddm.cluster.actors.messages.JobFailed;
import org.eltech.ddm.handlers.ParallelExecutionException;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.miningcore.algorithms.ExecutionHandler;
import org.eltech.ddm.miningcore.algorithms.Step;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.FiniteDuration;

import java.util.concurrent.TimeUnit;

public class ActorExecutionHandler extends ExecutionHandler {

    private final Inbox inbox;
    private final ActorRef actorRouter;
    private final FiniteDuration timeout;
    private final ActorSystem actorSystem;

    public ActorExecutionHandler(ActorsClusterExecutionSettings settings, ActorSystem actorSystem) throws ParallelExecutionException {
        super(settings);
        this.actorSystem = actorSystem;
        timeout = FiniteDuration.apply(settings.getTimeout(), TimeUnit.MILLISECONDS);
        ActorSelection selection = actorSystem.actorSelection(ActorRouter.ACTOR_PROXY);
        Future<ActorRef> actorRefFuture = selection.resolveOne(timeout);

        try {
            actorRouter = Await.result(actorRefFuture, timeout);
        } catch (Exception e) {
            throw new ParallelExecutionException(e.getLocalizedMessage());
        }
        this.inbox = Inbox.create(actorSystem);
    }

    @Override
    public void start(Step step, MiningInputStream inputData, EMiningModel model) throws ParallelExecutionException {
        inbox.send(actorRouter, new ExecuteJob(inputData, model, step));
    }

    @Override
    public EMiningModel getModel() throws ParallelExecutionException {
        Object message = inbox.receive(timeout);
        if (message instanceof ExecuteResult) {
            return ((ExecuteResult) message).getModel();
        } else if (message instanceof JobFailed) {
            ParallelExecutionException ex = new ParallelExecutionException("Actor get result failed.");
            ex.initCause(((JobFailed) message).getException());
            throw ex;
        } else {
            throw new ParallelExecutionException("Unsupported message received from the actor.");
        }
    }
}

