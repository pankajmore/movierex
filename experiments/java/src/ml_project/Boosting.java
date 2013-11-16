/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ml_project;


import java.io.BufferedReader;
import java.io.FileReader;
import weka.classifiers.Classifier;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.functions.SimpleLinearRegression;
import weka.classifiers.meta.Stacking;
import weka.core.Instances;
import weka.classifiers.Evaluation;


public class Boosting {

    public Boosting() {
        try
        {
            
            BufferedReader reader = new BufferedReader(
                    new FileReader("C:/Users/Ankur/Desktop/ML Project/ml-100k/WrittenFilel_Normalized1.arff"));
            BufferedReader reader1 = new BufferedReader(
                    new FileReader("C:/Users/Ankur/Desktop/ML Project/ml-100k/WrittenFilel_Normalized_Test.arff"));
            Instances trainData = new Instances(reader);
            Instances testData = new Instances(reader1);
            trainData.setClassIndex(trainData.numAttributes() - 2);
            testData.setClassIndex(testData.numAttributes() - 2);
            
            SimpleLinearRegression reg=new SimpleLinearRegression();
            MultilayerPerceptron per=new MultilayerPerceptron();
            Stacking stac=new Stacking();
            Classifier clas[]=new Classifier[2];
            clas[0]=reg;
            clas[1]=per;
            stac.setClassifiers(clas);
            reg.buildClassifier(trainData);
            stac.buildClassifier(trainData);
            stac.buildClassifier(trainData);
            
            System.out.println("Linear Regression");
                Evaluation  eval1 = new Evaluation(trainData);
                eval1.evaluateModel(reg,testData);
                System.out.println(eval1.rootMeanSquaredError());
            
                
                System.out.println("Nueral Network");
                Evaluation  eval2 = new Evaluation(trainData);
                eval2.evaluateModel(per,testData);
                System.out.println(eval2.rootMeanSquaredError());
                
                
                System.out.println("Linear Regression + Nueral Network");
                Evaluation  eval = new Evaluation(trainData);
                eval.evaluateModel(stac,testData);
                System.out.println(eval.rootMeanSquaredError());
                    
                
            
            
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    
    public static void main(String [] arsg)
    {
        new Boosting();
    }
}
