

import java.awt.Color;
import java.awt.GradientPaint;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.LibSVM;
import weka.core.Instances;
import weka.core.SelectedTag;

public class SVM {

    public SVM() {
        try {




            BufferedReader reader = new BufferedReader(
                    new FileReader("C:/Users/Ankur/Desktop/ML Project/ml-100k/WrittenFilel_Normalized1.arff"));
            BufferedReader reader1 = new BufferedReader(
                    new FileReader("C:/Users/Ankur/Desktop/ML Project/ml-100k/WrittenFilel_Normalized_Test.arff"));
            Instances trainData = new Instances(reader);
            Instances testData = new Instances(reader1);
            trainData.setClassIndex(trainData.numAttributes() - 2);
            testData.setClassIndex(testData.numAttributes() - 2);

            LibSVM svm = new LibSVM();
            svm.setKernelType(new SelectedTag(LibSVM.KERNELTYPE_POLYNOMIAL, LibSVM.TAGS_KERNELTYPE));
            Double error[][] = new Double[20][2];
            Double error1[][]=new Double[20][2];

            Evaluation eval = new Evaluation(testData);
            int count = 0;

            svm.setDoNotReplaceMissingValues(false);
            svm.setCacheSize(2000);
            svm.setShrinking(false);
            


            //  double prev_error=0.0,current_error=0.0;
            for (int degree = 0; degree < 3; degree++) {
                int index = 0;
                for (int init = 0; init < 20; init++) {
                    error[init][0] = 0.0;
                    error[init][1] = 0.0;
                      error1[init][0] = 0.0;
                    error1[init][1] = 0.0;
                }
                svm.setDegree(degree + 1);
                for (int cost = -8; cost < 11; cost = cost + 1) {
                    svm.setCost(Math.pow(2, cost * 1.0));
                    System.out.println("Count " + count);
                    count++;
                    svm.buildClassifier(trainData);
                    eval = new Evaluation(trainData);
                    eval.crossValidateModel(svm, trainData, 5, new Random(1));
                    error[index][0] = 1.0 * (Math.pow(2, cost * 1.0));
                    error[index][1] = eval.errorRate();
                    eval = new Evaluation(testData);
                    eval.evaluateModel(svm,testData);
                    error1[index][0] = 1.0 * (Math.pow(2, cost * 1.0));
                    error1[index][1] = eval.errorRate();
                    System.out.println(eval.errorRate());
                    index++;

                }
                CreateImage(error, degree + 1,0);
                CreateImage(error1, degree + 1,1);
                save(error, degree + 1);
                
                
                
                
                
            }


            double c = 2048;
            String line;
            double local_error = 0.0, global_error = 0.0;
            for (int i = 0; i < 3; i++) {
                FileReader reader2 = new FileReader("output.txt".concat(String.valueOf(i + 1)));
                BufferedReader br = new BufferedReader(reader2);
                count = 1;
                line = br.readLine();
                local_error = global_error = Double.parseDouble(line.split(" ")[1]);
                while ((line = br.readLine()) != null && count < 19) {
                    local_error = Double.parseDouble(line.split(" ")[1]);
                    if (global_error > local_error) {
                        global_error = local_error;
                        c = Double.parseDouble(line.split(" ")[0]);
                    }

                    count++;

                }

                System.out.println("Best Value of c for degree = " + (i + 1) + " is " + c+" with error "+global_error);


            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void CreateImage(Object arr[][], int degree,int bool) {
        try {

            // System.out.println("ANkur");

            DefaultCategoryDataset line_chart_dataset = new DefaultCategoryDataset();
            for (int i = 0; i < 20 - 1; i++) {
                // System.out.print("ANkur");
                line_chart_dataset.addValue(Double.parseDouble(arr[i][1].toString()), "Error_Rate", arr[i][0].toString());

            }

           int number=degree*10+bool;
            JFreeChart chart = ChartFactory.createLineChart("For Polynomial Degree ".concat(String.valueOf(number)), "Cost Penalty", "Cross-Validation Error", line_chart_dataset, PlotOrientation.VERTICAL, true, true, true);

            chart.getCategoryPlot().getRangeAxis().setRange(getLowestLow(arr) * 0.95, getHighestHigh(arr) * 1.05);


            chart.setBackgroundPaint(Color.white);

            // get a reference to the plot for further customisation...
            final CategoryPlot plot = chart.getCategoryPlot();
            plot.setBackgroundPaint(Color.lightGray);
            plot.setDomainGridlinePaint(Color.white);
            plot.setRangeGridlinePaint(Color.white);

            // set the range axis to display integers only...
            //  final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
            //rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

            // disable bar outlines...
            final LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
            renderer.setDrawOutlines(false);

            //set up gradient paints for series...
            final GradientPaint gp0 = new GradientPaint(
                    0.0f, 0.0f, Color.blue,
                    0.0f, 0.0f, Color.lightGray);
            final GradientPaint gp1 = new GradientPaint(
                    0.0f, 0.0f, Color.green,
                    0.0f, 0.0f, Color.lightGray);
            final GradientPaint gp2 = new GradientPaint(
                    0.0f, 0.0f, Color.red,
                    0.0f, 0.0f, Color.lightGray);
            renderer.setSeriesPaint(0, gp0);
            renderer.setSeriesPaint(1, gp1);
            renderer.setSeriesPaint(2, gp2);

            final CategoryAxis domainAxis = plot.getDomainAxis();
            domainAxis.setCategoryLabelPositions(
                    CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0));





            int width = 640; /* Width of the image */
            int height = 480;
            File lineChart = new File(new String("Degree").concat(String.valueOf(number)) + ".png");
            ChartUtilities.saveChartAsPNG(lineChart, chart, width, height);


        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private double getLowestLow(Object arr[][]) {
        double lowest;
        lowest = Double.parseDouble(arr[0][1].toString());
        for (int i = 1; i < arr.length - 1; i++) {
            if (Double.parseDouble(arr[i][1].toString()) < lowest) {
                lowest = Double.parseDouble(arr[i][1].toString());
            }
        }

        return lowest;
    }

    private double getHighestHigh(Object arr[][]) {
        double highest;
        highest = Double.parseDouble(arr[0][1].toString());
        for (int i = 1; i < arr.length - 1; i++) {
            if (Double.parseDouble(arr[i][1].toString()) > highest) {
                highest = Double.parseDouble(arr[i][1].toString());
            }
        }
        return highest;
    }

    public void save(Object arr[][], int j) throws FileNotFoundException {

        try {

            FileWriter writer = new FileWriter("output.txt".concat(String.valueOf(j)), true);
            for (int i = 0; i < arr.length; i++) {
                writer.write(arr[i][0] + " " + arr[i][1] + "\n");
            }
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new SVM();
    }
}
