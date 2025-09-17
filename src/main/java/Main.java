import ga.GeneticAlgorithm;
import ga.Chromosome;
import model.Task;
import model.Resource;
import util.CSVLoader;
import util.ChartUtil;
import util.ReportUtil;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Load tasks
        List<Task> tasks = CSVLoader.loadTasks("dataset/lunar_tasks.csv");

        // resources that avail
        Resource resource = new Resource(200, 150, 70);

        // our ga
        GeneticAlgorithm ga = new GeneticAlgorithm(tasks, resource);
        Chromosome best = ga.run();

        System.out.println("\n===== COSMOS-OPT Simulation Results =====");
        System.out.println("Best allocation fitness: " + best.getFitness());

        boolean[] allocation = best.getGene();

        // before the optimal approch
        int totalEnergyBefore = tasks.stream().mapToInt(Task::getEnergyRequired).sum();
        int totalCpuBefore = tasks.stream().mapToInt(Task::getCpuRequired).sum();
        int totalBandwidthBefore = tasks.stream().mapToInt(Task::getBandwidthRequired).sum();

        //  optimization
        int totalEnergyAfter = 0, totalCpuAfter = 0, totalBandwidthAfter = 0;

        System.out.println("\nOptimized Task Allocation:");
        for (int i = 0; i < allocation.length; i++) {
            if (allocation[i]) {
                Task t = tasks.get(i);
                totalEnergyAfter += t.getEnergyRequired();
                totalCpuAfter += t.getCpuRequired();
                totalBandwidthAfter += t.getBandwidthRequired();
                System.out.println(t);
            } else {
                System.out.println(tasks.get(i).getName() + " skipped.");
            }
        }


        double energySaved = totalEnergyBefore - totalEnergyAfter;
        double survivability = (energySaved / resource.getTotalEnergy()) * 100;
        int extraHours = (int) (survivability / 5);

        System.out.println("\n===== Resource Usage =====");
        System.out.println("Before Optimization → Energy: " + totalEnergyBefore +
                ", CPU: " + totalCpuBefore + ", Bandwidth: " + totalBandwidthBefore);
        System.out.println("After Optimization  → Energy: " + totalEnergyAfter +
                ", CPU: " + totalCpuAfter + ", Bandwidth: " + totalBandwidthAfter);

        System.out.println("\nEnergy Saved: " + energySaved + " units");
        System.out.println("Survivability Score: " + String.format("%.2f", survivability) + "%");
        System.out.println("Estimated Mission Life Extended by: " + extraHours + " hours");


        ReportUtil.saveReport("simulation_summary.txt", tasks, allocation,
                totalEnergyBefore, totalCpuBefore, totalBandwidthBefore,
                totalEnergyAfter, totalCpuAfter, totalBandwidthAfter,
                survivability, extraHours);

        ReportUtil.saveFitnessCSV("fitness_history.csv", ga.getBestFitnessHistory());


        ChartUtil.showAndSaveEnergyPieChart(totalEnergyBefore, totalEnergyAfter);
        ChartUtil.showAndSaveResourceBarChart(totalCpuBefore, totalCpuAfter, totalBandwidthBefore, totalBandwidthAfter);
        ChartUtil.showAndSaveFitnessLineChart(ga.getBestFitnessHistory());
    }
}
