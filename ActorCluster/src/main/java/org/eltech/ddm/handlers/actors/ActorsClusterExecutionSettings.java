package org.eltech.ddm.handlers.actors;

import org.eltech.ddm.handlers.ExecutionSettings;
import org.eltech.ddm.handlers.HandlerType;

public class ActorsClusterExecutionSettings extends ExecutionSettings {

    private static final long defaultTimeout = 1000 * 60 * 60;    // 1 hour by default

    private String host;
    private int port;
    private int actorsPerNode;
    private final long timeoutMillis;
    private String seedNode;

    public ActorsClusterExecutionSettings() {
        this(defaultTimeout);
    }

    public ActorsClusterExecutionSettings(long timeoutMillis) {
        super();
        this.systemType = HandlerType.ActorExecutionHandler;
        this.timeoutMillis = timeoutMillis;
    }

    public ActorsClusterExecutionSettings(String name, long timeoutMillis) {
        super();
        this.systemType = HandlerType.ActorExecutionHandler;
        this.nameHandler = name;
        this.timeoutMillis = timeoutMillis;
    }

    public ActorsClusterExecutionSettings(String name) {
        this(name, defaultTimeout);
    }

    public long getTimeout() {
        return timeoutMillis;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getTotalActors() {
        return numberHandlers;
    }

    public void setTotalActors(int totalActors) {
        numberHandlers = totalActors;
    }

    public int getActorsPerNode() {
        return actorsPerNode;
    }

    public void setActorsPerNode(int actorsPerNode) {
        this.actorsPerNode = actorsPerNode;
    }

    public String getSeedNode() {
        return seedNode;
    }

    public void setSeedNode(String seedNode) {
        this.seedNode = seedNode;
    }
}
