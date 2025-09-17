package model;

public class Task {
    private String name;
    private int energyRequired;
    private int cpuRequired;
    private int bandwidthRequired;
    private int priority;

    public Task(String name, int energyRequired, int cpuRequired, int bandwidthRequired, int priority) {
        this.name = name;
        this.energyRequired = energyRequired;
        this.cpuRequired = cpuRequired;
        this.bandwidthRequired = bandwidthRequired;
        this.priority = priority;
    }

    public String getName() { return name; }
    public int getEnergyRequired() { return energyRequired; }
    public int getCpuRequired() { return cpuRequired; }
    public int getBandwidthRequired() { return bandwidthRequired; }
    public int getPriority() { return priority; }

    @Override
    public String toString() {
        return name + " (Energy=" + energyRequired +
                ", CPU=" + cpuRequired +
                ", Bandwidth=" + bandwidthRequired +
                ", Priority=" + priority + ")";
    }
}
