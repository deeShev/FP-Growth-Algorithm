package org.eltech.ddm.handlers.thread;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.eltech.ddm.handlers.ExecutionHandlerFactory;
import org.eltech.ddm.handlers.ExecutionSettings;
import org.eltech.ddm.handlers.HandlerSettings;
import org.eltech.ddm.handlers.ParallelExecutionException;
import org.eltech.ddm.miningcore.algorithms.ExecutionHandler;

public class ThreadExecutionHandlerFactory extends ExecutionHandlerFactory {
    ExecutorService service;


    public ThreadExecutionHandlerFactory(ExecutionSettings defaultSettings) {
        this.defaultSettings = defaultSettings;
        service = Executors.newCachedThreadPool();
    }

    @Override
    public ExecutionHandler create(ExecutionSettings settings) throws ParallelExecutionException {
        // TODO Auto-generated method stub
        if (settings instanceof ThreadSettings)
            return new ThreadExecutionHandler((ThreadSettings) settings, service);
        else
            throw new ParallelExecutionException("Handler settings type is not correct");
    }

    @Override
    public ArrayList<ExecutionHandler> create(ArrayList<ExecutionSettings> settings) throws ParallelExecutionException {
        // TODO Auto-generated method stub
        ArrayList<ExecutionHandler> handlers = new ArrayList<ExecutionHandler>();
        for (ExecutionSettings setting : settings) {
            handlers.add(create(setting));
        }

        return handlers;
    }
}
