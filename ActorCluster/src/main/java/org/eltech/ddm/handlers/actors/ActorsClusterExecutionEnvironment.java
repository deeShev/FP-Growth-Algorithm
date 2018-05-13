package org.eltech.ddm.handlers.actors;

import akka.actor.ActorSystem;
import org.eltech.ddm.cluster.actors.ServiceMain;
import org.eltech.ddm.handlers.ExecutionEnvironment;
import org.eltech.ddm.handlers.ExecutionSettings;
import org.eltech.ddm.handlers.ParallelExecutionException;
import org.eltech.ddm.handlers.thread.ThreadExecutionHandler;
import org.eltech.ddm.handlers.thread.ThreadSettings;


public class ActorsClusterExecutionEnvironment extends ExecutionEnvironment {

    public ActorsClusterExecutionEnvironment(ExecutionSettings settings) throws ParallelExecutionException {
        super(settings);
    }

    @Override
    protected void initEnvironment() throws ParallelExecutionException {

        ActorsClusterExecutionSettings envs = (ActorsClusterExecutionSettings) settings;
        ActorSystem system = ServiceMain.initSystem(envs.getHost(), envs.getPort(), envs.getTotalActors(), envs.getActorsPerNode(), envs.getSeedNode());

        ActorExecutionHandlerFactory actorFac = new ActorExecutionHandlerFactory(system, envs);
        executionHandlerFactory = actorFac;

        mainHandler = new ThreadExecutionHandler(new ThreadSettings());
        templateHandler = actorFac.create(settings);

    }

    public void shutdown() {
        ((ActorExecutionHandlerFactory) executionHandlerFactory).shutdown();
    }
}

