package org.eltech.ddm.handlers.actors;

import akka.actor.ActorSystem;
import org.eltech.ddm.handlers.ExecutionHandlerFactory;
import org.eltech.ddm.handlers.ExecutionSettings;
import org.eltech.ddm.handlers.ParallelExecutionException;
import org.eltech.ddm.miningcore.algorithms.ExecutionHandler;

import java.util.ArrayList;

public class ActorExecutionHandlerFactory extends ExecutionHandlerFactory {

    private final ActorSystem actorSystem;

    public ActorExecutionHandlerFactory(ActorSystem actorSystem, ActorsClusterExecutionSettings settings) {
        this.actorSystem = actorSystem;
        this.defaultSettings = settings;
    }

    @Override
    public ExecutionHandler create(ExecutionSettings settings) throws ParallelExecutionException {
        return new ActorExecutionHandler((ActorsClusterExecutionSettings) settings, actorSystem);
    }

    @Override
    public ArrayList<ExecutionHandler> create(ArrayList<ExecutionSettings> settings) throws ParallelExecutionException {
        ArrayList<ExecutionHandler> executionHandlers = new ArrayList<ExecutionHandler>();
        for (ExecutionSettings handlerSettings : settings)
            executionHandlers.add(this.create(handlerSettings));
        return executionHandlers;
    }

    public void shutdown() {
        actorSystem.shutdown();
    }
}
