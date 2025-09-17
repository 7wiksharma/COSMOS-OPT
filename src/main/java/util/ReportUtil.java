package util;

import model.Task;

import java.io.*;
import java.util.List;

public class ReportUtil {

    public static void saveReport(String fileName, List<Task> tasks, boolean[] allocation,
                                  int totalEnergyBefore, int totalCpuBefore, int totalBandwidthBefore,
                                  int totalEnergyAfter, int totalCpuAfter, int totalBandwidthAfter,
                                  double survivability, int extraHours) {

        try {
            File dir = new File("output/reports/");
            dir.mkdirs();
            File reportFile = new File(dir, fileName);

            try (PrintWriter writer = new PrintWriter(new FileWriter(reportFile))) {
                writer.println("===== COSMOS-OPT Simulation Report =====");
                writer.println("\nTask Allocation:");
                for (int i = 0; i < tasks.size(); i++) {
                    if (allocation[i]) {
                        writer.println( tasks.get(i));
                    } else {
                        writer.println(  tasks.get(i).getName() + " skipped.");
                    }
                }

                writer.println("\n===== Resource Usage =====");
                writer.println("Before Optimization → Energy: " + totalEnergyBefore +
                        ", CPU: " + totalCpuBefore + ", Bandwidth: " + totalBandwidthBefore);
                writer.println("After Optimization  → Energy: " + totalEnergyAfter +
                        ", CPU: " + totalCpuAfter + ", Bandwidth: " + totalBandwidthAfter);

                writer.println("\nEnergy Saved: " + (totalEnergyBefore - totalEnergyAfter) + " units");
                writer.println("Survivability Score: " + String.format("%.2f", survivability) + "%");
                writer.println("Estimated Mission Life Extended by: " + extraHours + " hours");
            }

            System.out.println("Report saved: " + reportFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveFitnessCSV(String fileName, List<Double> fitnessHistory) {
        try {
            File dir = new File("output/reports/");
            dir.mkdirs();
            File csvFile = new File(dir, fileName);

            try (PrintWriter writer = new PrintWriter(new FileWriter(csvFile))) {
                writer.println("Generation,Fitness");
                for (int i = 0; i < fitnessHistory.size(); i++) {
                    writer.println((i + 1) + "," + fitnessHistory.get(i));
                }
            }

            System.out.println(" Fitness CSV saved: " + csvFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
