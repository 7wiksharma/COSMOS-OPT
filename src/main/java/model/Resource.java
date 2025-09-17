package model;

public class Resource {
    private int totalEnergy;
    private int totalCpu;
    private int totalBandwidth;

    public Resource(int totalEnergy, int totalCpu, int totalBandwidth) {
        this.totalEnergy = totalEnergy;
        this.totalCpu = totalCpu;
        this.totalBandwidth = totalBandwidth;
    }

    public int getTotalEnergy() { return totalEnergy; }
    public int getTotalCpu() { return totalCpu; }
    public int getTotalBandwidth() { return totalBandwidth; }
}
