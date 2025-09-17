package ga;

import model.Task;
import model.Resource;
import java.util.*;

public class Chromosome {
    private List<Task> tasks;
    private boolean[] gene;
    private double fitness;

    public Chromosome(List<Task> tasks) {
        this.tasks = tasks;
        this.gene = new boolean[tasks.size()];
        Random random = new Random();
        for (int i = 0; i < gene.length; i++) {
            gene[i] = random.nextBoolean();
        }
    }

    public double calculateFitness(Resource resource) {
        int usedEnergy = 0, usedCpu = 0, usedBandwidth = 0;
        int score = 0;

        for (int i = 0; i < tasks.size(); i++) {
            if (gene[i]) {
                Task t = tasks.get(i);
                usedEnergy += t.getEnergyRequired();
                usedCpu += t.getCpuRequired();
                usedBandwidth += t.getBandwidthRequired();

                if (usedEnergy <= resource.getTotalEnergy() &&
                        usedCpu <= resource.getTotalCpu() &&
                        usedBandwidth <= resource.getTotalBandwidth()) {
                    score += (10 - t.getPriority()) * 10;
                } else {
                    score -= 20;
                }
            }
        }
        this.fitness = Math.max(score, 0);
        return this.fitness;
    }

    public boolean[] getGene() { return gene; }
    public double getFitness() { return fitness; }
    public void setGene(boolean[] newGene) { this.gene = newGene; }
}
