

package ml_project;

import java.awt.Color;
import java.awt.GradientPaint;
import org.jfree.chart.ChartUtilities; 
import java.io.File;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.example.grouplens.GroupLensRecommender;
import org.apache.mahout.cf.taste.impl.eval.RMSRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.GenericBooleanPrefDataModel;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.CachingRecommender;
import org.apache.mahout.cf.taste.impl.recommender.ItemAverageRecommender;
import org.apache.mahout.cf.taste.impl.recommender.ItemUserAverageRecommender;
import org.apache.mahout.cf.taste.impl.recommender.RandomRecommender;
import org.apache.mahout.cf.taste.impl.recommender.slopeone.SlopeOneRecommender;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

public class Comparison {

    public Comparison() {
        try
        {
            Object arr[][]=new Object[6][2];
            
            
            File ratingsFile = new File("C:/Users/Ankur/Desktop/ml-100k/Merged_usera1.txt");
            
            DataModel model = new FileDataModel(ratingsFile);
            
           
                   
            //1.SlopeOneRecommendor
            RecommenderBuilder builder0 = new RecommenderBuilder() {
                public Recommender buildRecommender(DataModel model) throws TasteException {
                    return new CachingRecommender(new SlopeOneRecommender(model));
                }
            };
            RecommenderEvaluator evaluator0 = new RMSRecommenderEvaluator();
            double score0 = evaluator0.evaluate(builder0, null,model,0.6,0.4);
            arr[1][0]=new String("SlopeOneRecommendor");arr[1][1]=new Double(score0);
            System.out.println("With SlopeOneRecommendor = " + score0);

            //1.end SlopeOneRecommnedr
            
            
            //2.GroupLensRecommendor
              RecommenderBuilder builder1 = new RecommenderBuilder() {
                public Recommender buildRecommender(DataModel model) throws TasteException {
                    return new CachingRecommender(new GroupLensRecommender(model));
                }
            };
            RecommenderEvaluator evaluator1 = new RMSRecommenderEvaluator();
            double score1 = evaluator1.evaluate(builder1, null,model,0.6,0.4);
            arr[2][0]=new String("GroupLensRecommendor");arr[2][1]=new Double(score1);
            System.out.println("With GroupLensRecommendor = " + score1);
            
            
            //3.ItemAverageRecommendor
               RecommenderBuilder builder2 = new RecommenderBuilder() {
                public Recommender buildRecommender(DataModel model) throws TasteException {
                    return new CachingRecommender(new ItemAverageRecommender(model));
                }
            };
            RecommenderEvaluator evaluator2 = new RMSRecommenderEvaluator();
            double score2 = evaluator2.evaluate(builder2, null,model,0.6,0.4);
             arr[3][0]=new String("ItemAverageRecommendor");arr[3][1]=new Double(score2);
            System.out.println("With ItemAverageRecommender = " + score2);
            
            
            //4.ItemUserAverageRecommender
               RecommenderBuilder builder3 = new RecommenderBuilder() {
                public Recommender buildRecommender(DataModel model) throws TasteException {
                    return new CachingRecommender(new ItemUserAverageRecommender(model));
                }
            };
            RecommenderEvaluator evaluator3 = new RMSRecommenderEvaluator();
            double score3 = evaluator3.evaluate(builder3, null,model,0.6,0.4);
            arr[4][0]=new String("ItemUserAverageRecommendor");arr[4][1]=new Double(score3);
            System.out.println("With ItemUserAverageRecommender = " + score3);
            
            
            //5.Random Recommendor
             RecommenderBuilder builder4 = new RecommenderBuilder() {
                public Recommender buildRecommender(DataModel model) throws TasteException {
                    return new CachingRecommender(new RandomRecommender(model));
                }
            };
            RecommenderEvaluator evaluator4 = new RMSRecommenderEvaluator();
            double score4 = evaluator4.evaluate(builder4, null,model,0.6,0.4);
            arr[5][0]=new String("RandomRecommendor");arr[5][1]=new Double(score4);
            System.out.println("With Random Recommendor = " + score4);
  
            CreateImage(arr);
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        
    }
    
    void CreateImage(Object arr[][])
    {
         try {
                
                /* Step - 1: Define the data for the line chart  */
                DefaultCategoryDataset line_chart_dataset = new DefaultCategoryDataset();

                for(int i=1;i<arr.length;i++)
                {
                   //System.out.print("ANkur");
                        line_chart_dataset.addValue(Double.parseDouble(arr[i][1].toString()), "", arr[i][0].toString());
                    
                }
                
                /* Step -2:Define the JFreeChart object to create line chart */
               // JFreeChart lineChartObject=ChartFactory.createLineChart("Classifier vs Error","Classifier","RMS Error",line_chart_dataset,PlotOrientation.VERTICAL,true,true,true);                
                       final JFreeChart chart = ChartFactory.createBarChart(
                    "Classifier vs Error", // chart title
                    "Algorithn Used", // domain axis label
                    "Rms_Error", // range axis label
                    line_chart_dataset, // data
                    PlotOrientation.VERTICAL, // orientation
                    true, // include legend
                    true, // tooltips?
                    false // URLs?
                    );   
                       
                       
                       
                       
                       
                       
                       
                       chart.setBackgroundPaint(Color.white);

        // get a reference to the plot for further customisation...
        final CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);

        // set the range axis to display integers only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        // disable bar outlines...
        final BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        
        // set up gradient paints for series...
        final GradientPaint gp0 = new GradientPaint(
            0.0f, 0.0f, Color.blue, 
            0.0f, 0.0f, Color.lightGray
        );
        final GradientPaint gp1 = new GradientPaint(
            0.0f, 0.0f, Color.green, 
            0.0f, 0.0f, Color.lightGray
        );
        final GradientPaint gp2 = new GradientPaint(
            0.0f, 0.0f, Color.red, 
            0.0f, 0.0f, Color.lightGray
        );
        renderer.setSeriesPaint(0, gp0);
        renderer.setSeriesPaint(1, gp1);
        renderer.setSeriesPaint(2, gp2);

        final CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(
            CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0)
        );
                       
                       
                       
                       
                       
                       
                       
                       
                       
                       
                       
                /* Step -3 : Write line chart to a file */               
                 int width=640; /* Width of the image */
                 int height=480; /* Height of the image */                
                 File lineChart=new File("C:/Users/Ankur/Desktop/Comparison.png");              
                 ChartUtilities.saveChartAsPNG(lineChart,chart,width,height); 
         }
         catch (Exception i)
         {
             System.out.println(i);
         }
    }

    public static void main(String [] args)
    {
        new Comparison();
    }
    
}
