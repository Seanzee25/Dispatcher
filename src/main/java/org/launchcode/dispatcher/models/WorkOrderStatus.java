package org.launchcode.dispatcher.models;

public enum WorkOrderStatus {
    CREATED("Created"),
    STARTED("Started"),
    FINISHED("Finished"),
    ASSIGNED("Assigned");

    private final String name;

    WorkOrderStatus(String name) {
        this.name = name;
    }

    public String getName() { return name; }
}
