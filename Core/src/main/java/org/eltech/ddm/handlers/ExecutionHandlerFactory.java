package org.eltech.ddm.handlers;

import java.util.ArrayList;

import org.eltech.ddm.miningcore.algorithms.ExecutionHandler;

public abstract class ExecutionHandlerFactory {

    protected ExecutionSettings defaultSettings;

    public ExecutionHandler create() throws ParallelExecutionException {
        return create(getDefaultSettings());
    }

    public abstract ExecutionHandler create(ExecutionSettings settings)
            throws ParallelExecutionException;

    public abstract ArrayList<ExecutionHandler> create(ArrayList<ExecutionSettings> settings)
            throws ParallelExecutionException;

    public ExecutionSettings getDefaultSettings() {
        return defaultSettings;
    }
}
