package ga;

import model.Task;
import model.Resource;
import java.util.*;

public class GeneticAlgorithm {
    private List<Task> tasks;
    private Resource resource;
    private int populationSize = 20;
    private int generations = 50;
    private double mutationRate = 0.1;
    private List<Double> bestFitnessHistory = new ArrayList<>();

    public GeneticAlgorithm(List<Task> tasks, Resource resource) {
        this.tasks = tasks;
        this.resource = resource;
    }

    public Chromosome run() {
        List<Chromosome> population = initPopulation();

        for (int g = 0; g < generations; g++) {
            population.sort(Comparator.comparingDouble(c -> -c.calculateFitness(resource)));
            Chromosome bestGen = population.get(0);

            bestFitnessHistory.add(bestGen.getFitness());

            List<Chromosome> newPopulation = new ArrayList<>();
            newPopulation.add(bestGen);

            while (newPopulation.size() < populationSize) {
                Chromosome parent1 = select(population);
                Chromosome parent2 = select(population);
                Chromosome child = crossover(parent1, parent2);
                mutate(child);
                child.calculateFitness(resource);
                newPopulation.add(child);
            }
            population = newPopulation;
        }
        return population.get(0);
    }

    private List<Chromosome> initPopulation() {
        List<Chromosome> pop = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            Chromosome c = new Chromosome(tasks);
            c.calculateFitness(resource);
            pop.add(c);
        }
        return pop;
    }

    private Chromosome select(List<Chromosome> population) {
        Random rand = new Random();
        return population.get(rand.nextInt(population.size() / 2));
    }

    private Chromosome crossover(Chromosome p1, Chromosome p2) {
        boolean[] gene1 = p1.getGene();
        boolean[] gene2 = p2.getGene();
        boolean[] newGene = new boolean[gene1.length];
        Random rand = new Random();
        for (int i = 0; i < gene1.length; i++) {
            newGene[i] = rand.nextBoolean() ? gene1[i] : gene2[i];
        }
        Chromosome child = new Chromosome(tasks);
        child.setGene(newGene);
        return child;
    }

    private void mutate(Chromosome c) {
        Random rand = new Random();
        boolean[] gene = c.getGene();
        for (int i = 0; i < gene.length; i++) {
            if (rand.nextDouble() < mutationRate) {
                gene[i] = !gene[i];
            }
        }
        c.setGene(gene);
    }

    public List<Double> getBestFitnessHistory() { return bestFitnessHistory; }
}
