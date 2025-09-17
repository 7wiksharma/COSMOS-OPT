package util;

import org.jfree.chart.*;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.*;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

public class ChartUtil {

    public static void showAndSaveEnergyPieChart(int before, int after) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Energy Used (Before)", before);
        dataset.setValue("Energy Used (After)", after);

        JFreeChart pieChart = ChartFactory.createPieChart(
                "Energy Usage Before vs After Optimization",
                dataset, true, true, false);


        PiePlot plot = (PiePlot) pieChart.getPlot();
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
                "{0}: {1} units ({2})",
                new DecimalFormat("0"),
                new DecimalFormat("0%")
        ));

        displayAndSave(pieChart, "Energy Pie Chart", "output/charts/energy_pie.png");
    }


    public static void showAndSaveResourceBarChart(int cpuBefore, int cpuAfter,
                                                   int bwBefore, int bwAfter) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        dataset.addValue(cpuBefore, "CPU Usage", "Before");
        dataset.addValue(cpuAfter, "CPU Usage", "After");
        dataset.addValue(bwBefore, "Bandwidth Usage", "Before");
        dataset.addValue(bwAfter, "Bandwidth Usage", "After");

        JFreeChart barChart = ChartFactory.createBarChart(
                "Resource Usage Before vs After",
                "Resource Type",
                "Units Consumed",
                dataset);

        displayAndSave(barChart, "Resource Bar Chart", "output/charts/resource_bar.png");
    }

    public static void showAndSaveFitnessLineChart(List<Double> fitnessHistory) {
        XYSeries series = new XYSeries("Best Fitness");
        for (int i = 0; i < fitnessHistory.size(); i++) {
            series.add(i + 1, fitnessHistory.get(i));
        }

        XYSeriesCollection dataset = new XYSeriesCollection(series);
        JFreeChart lineChart = ChartFactory.createXYLineChart(
                "Fitness Improvement Across Generations",
                "Generation",
                "Fitness Score",
                dataset
        );

        displayAndSave(lineChart, "Fitness Evolution", "output/charts/fitness_line.png");
    }

    private static void displayAndSave(JFreeChart chart, String title, String filePath) {
        // Display
        ChartFrame frame = new ChartFrame(title, chart);
        frame.pack();
        frame.setVisible(true);

        // Save as PNG
        try {
            File file = new File(filePath);
            file.getParentFile().mkdirs(); // create directories if missing
            ChartUtils.saveChartAsPNG(file, chart, 800, 600);
            System.out.println("Saved chart: " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
