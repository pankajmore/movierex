
import java.awt.Color;
import java.awt.GradientPaint;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.data.basic.BasicNeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.training.Train;
import org.encog.neural.networks.training.propagation.back.Backpropagation;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

public class Neural_Network {

    public Neural_Network() {
        try {


            double input_data[][] = new double[43468][7];
            double input_class[][] = new double[43468][1];

            double val_data[][] = new double[12818][7];
            double val_class[][] = new double[12818][1];

            double test_data[][] = new double[23714][7];
            double test_class[][] = new double[23714][1];


            String temp[], line;

            FileReader input_reader = new FileReader("C:/Users/Ankur/Desktop/ML Project/ml-100k/WrittenFilel_Normalized1.arff");
            BufferedReader br1 = new BufferedReader(input_reader);
            FileReader val_reader = new FileReader("C:/Users/Ankur/Desktop/ML Project/ml-100k/WrittenFilel_Normalized_Val.arff");
            BufferedReader br2 = new BufferedReader(val_reader);
            FileReader test_reader = new FileReader("C:/Users/Ankur/Desktop/ML Project/ml-100k/WrittenFilel_Normalized_Test.arff");
            BufferedReader br3 = new BufferedReader(test_reader);

            int index = 0, index1 = 0;
            while ((line = br1.readLine()) != null) {
                temp = line.split(",");
                index1 = 0;
                //  System.out.println(temp.length);
                for (int i = 0; i < temp.length; i++) {

                    if (i == 6) {
                        input_class[index][0] = Double.parseDouble(temp[i]);
                    //   System.out.print(" calss i = "+i);
                    } else {
                        //System.out.println("Index1 "+index1);
                        input_data[index][index1] = Double.parseDouble(temp[i]);
                        // System.out.print(" i = "+i+" index1 "+index1);
                        index1++;
                    }


                }
                // System.out.println();
                index++;
            }

//            System.out.println("Input Data");
//            System.out.println();
//            System.out.println();
//            System.out.println();
//
//            for (int j = 0; j < input_data.length; j++) {
//                for (int l = 0; l <7; l++) {
//                    System.out.print(input_data[j][l] + " ");
//                }
//                System.out.println(input_class[j][0]);
//            }


            index = 0;
            index1 = 0;
            while ((line = br2.readLine()) != null) {
                index1 = 0;
                temp = line.split(",");
                // System.out.println(temp.length);
                for (int i = 0; i < temp.length; i++) {

                    if (i == 6) {
                        val_class[index][0] = Double.parseDouble(temp[i]);
                    } else {
                        val_data[index][index1] = Double.parseDouble(temp[i]);
                        index1++;
                    }
                }


                index++;
            }



//            
//            
//
//            System.out.println("Val Data");
//            System.out.println();
//            System.out.println();
//            System.out.println();
//            for (int j = 0; j < val_data.length; j++) {
//                for (int l = 0; l < 7; l++) {
//                    System.out.print(val_data[j][l] + " ");
//                }
//                System.out.println(val_class[j][0]);
//            }




            index = 0;
            index1 = 0;
            while ((line = br3.readLine()) != null) {
                index1 = 0;
                temp = line.split(",");
                // System.out.println(temp.length);
                for (int i = 0; i < temp.length; i++) {

                    if (i == 6) {
                        test_class[index][0] = Double.parseDouble(temp[i]);
                    } else {
                        test_data[index][index1] = Double.parseDouble(temp[i]);
                        index1++;
                    }


                }
                index++;
            // System.out.println("i = "+i);
            }


//
//            System.out.println("Test Data");
//            System.out.println();
//            System.out.println();
//            System.out.println();
//            for (int j = 0; j < test_data.length; j++) {
//                for (int l = 0; l < 7; l++) {
//                    System.out.print(test_data[j][l] + " ");
//                }
//                System.out.println(test_class[j][0]);
//            }
            NeuralDataSet trainingSet = new BasicNeuralDataSet(input_data, input_class);
            NeuralDataSet valiDataSet = new BasicNeuralDataSet(val_data, val_class);
            NeuralDataSet testDataSet = new BasicNeuralDataSet(test_data, test_class);
            BasicNetwork network = null;
            Train train;

            double global_testseterror = 0.0, local_testseterror = 0.0;
            Double error[][] = new Double[50][2];
            int nueron = 1, optimal_nueron = 1;
            index = 0;
//            while (nueron != 50) {

            System.out.println("NUERON == == " + nueron);
            network = new BasicNetwork();
            network.addLayer(new BasicLayer(new ActivationSigmoid(), true, 7));
            network.addLayer(new BasicLayer(new ActivationSigmoid(), true, 4));
            network.addLayer(new BasicLayer(new ActivationSigmoid(), true, 1));
            network.getStructure().finalizeStructure();
            network.reset();
            train = new ResilientPropagation(network, trainingSet);
//
//                double val_local_error, val_global_error;
//
//                int epoch = 1;
//                train.iteration();
//                val_local_error = val_global_error = network.calculateError(valiDataSet);
//                while (true) {
//                    train.iteration();
//                    val_local_error = network.calculateError(valiDataSet);
//                    if (val_local_error >= val_global_error || val_local_error < 0.001) 
//                    {
//                        break;
//                    } else {
//                        val_global_error = val_local_error;
//                    }
//                    System.out.println("val_local_error =" + val_local_error);
//                    epoch++;
//                }
//
//                local_testseterror = network.calculateError(testDataSet);
//                System.out.println("neuron = " + nueron);
//                System.out.println("globalerror = " + global_testseterror);
//                System.out.println("localerror = " + local_testseterror);
//
//                error[index][0] = (double) nueron;
//                error[index][1] = local_testseterror;
//
//                if ((global_testseterror != 0.0 && global_testseterror < local_testseterror)) {
//                    break;
//                } else {
//                    global_testseterror = local_testseterror;
//                    optimal_nueron = nueron;
//                }
//
//
//                nueron++;
//                index++;
//            }

            int epoch = 1;

            do {

                train.iteration();
                System.out.println("Epoch #" + epoch +
                        " Error:" + network.calculateError(valiDataSet));
                epoch++;

            } while (train.getError() > 0.01 && epoch<=200);


            for (MLDataPair pair : testDataSet) {

                MLData output = network.compute(pair.getInput());
//                System.out.println(pair.getInput().getData(0) +
//                        "," + pair.getInput().getData(1) +
//                        ", actual=" + output.getData(0) +
//                        ",ideal=" + pair.getIdeal().getData(0));


                System.out.println(
                        " actual=" + output.getData(0) +
                        ",ideal=" + pair.getIdeal().getData(0));

            }






            System.out.println("Number of Neurons " + optimal_nueron);
            System.out.println("Error Rate " + global_testseterror);

            System.out.println("Neural Network Results:");

        //   CreateImage(error);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    void CreateImage(Object arr[][]) {
        try {

            // System.out.println("ANkur");

            DefaultCategoryDataset line_chart_dataset = new DefaultCategoryDataset();
            for (int i = 0; i < arr.length - 1; i++) {
                System.out.println(arr[i][0] + "  " + arr[i][1]);
                line_chart_dataset.addValue(Double.parseDouble(arr[i][1].toString()), "Error_Rate", arr[i][0].toString());

            }


            JFreeChart chart = ChartFactory.createLineChart("Nuerons vs Error ", "Number of Nueron", "Error", line_chart_dataset, PlotOrientation.VERTICAL, true, true, true);

            chart.getCategoryPlot().getRangeAxis().setRange(getLowestLow(arr) * 0.50, getHighestHigh(arr) * 1.25);


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





            int width = 1024; /* Width of the image */
            int height = 640;
            File lineChart = new File(new String("Nueral_Network.png"));
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

    public static void main(String[] args) {
        new Neural_Network();
    }
}
