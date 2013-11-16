/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ml_project;

import JSci.maths.vectors.DoubleVector;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Collection;
import org.apache.mahout.cf.taste.common.Refreshable;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.RMSRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.CachingRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import sun.management.resources.agent;




class vector_similairtty implements ItemSimilarity{

    double feature[][] = new double[1682][28];
    double matrix2[][] = new double[1682][1682];
    public vector_similairtty() {
          try {
            double feature[][] = new double[1682][28];
            for (int i = 0; i < 1682; i++) {
                for (int j = 0; j < 28; j++) {
                    feature[i][j] = 0;
                }
            }

           

            BufferedReader buff = new BufferedReader(new FileReader("C:/Users/Ankur/Documents/NetBeansProjects/ML_Project/src/ml_project/ml-100k/u.item"));
            String line = null;
            String[] temp;
            int ft;
            int i = 0;
            while ((line = buff.readLine()) != null) {
                temp = line.split("\\|");

                //  System.out.println("i ="+i+" "+parse(temp[2]));
                ft = Integer.parseInt(parse(temp[2]));
                //System.out.println("i ="+i+" "+parse(temp[1]));
                // System.out.println("test1");
                if (ft <= 1960) {
                    feature[i][0] = 1;
                } else if (ft >= 1961 && ft <= 1965) {
                    feature[i][1] = 1;
                } else if (ft >= 1966 && ft <= 1970) {
                    feature[i][2] = 1;
                } else if (ft >= 1971 && ft <= 1975) {
                    feature[i][3] = 1;
                } else if (ft >= 1976 && ft <= 1980) {
                    feature[i][4] = 1;
                } else if (ft >= 1981 && ft <= 1985) {
                    feature[i][5] = 1;
                } else if (ft >= 1986 && ft <= 1990) {
                    feature[i][6] = 1;
                } else if (ft >= 1991 && ft <= 1995) {
                    feature[i][7] = 1;
                } else if (ft >= 1996 && ft <= 2000) {
                    feature[i][8] = 1;
                }
                int l = 9;
                // System.out.println("test2");
                for (int j = 4; j < 22; j++) {
                    feature[i][l] = Integer.parseInt(temp[j]);
                    l++;
                }
//                for (int j = 0; j < 28; j++) {
//                    System.out.print(feature[i][j] + "  ");
//                }
//                System.out.println();

                i++;
            }


            DoubleVector vector[] = new DoubleVector[1682];
            for (int n = 0; n < 1682; n++) {
                vector[n] = new DoubleVector(feature[n]);
            }


            for (int n = 0; n < 1682; n++) {
                for (int m = 0; m < 1682; m++) {
                    matrix2[n][m] = vector[n].scalarProduct(vector[m]);
                // ArrayList list=new ArrayList(new Collection( matrix[n]);
                //  List<double[]> wordList = Arrays.asList(matrix2[n]);
                //wordList.iterator();
                }
            }


//            for (int m = 0; m < 1682; m++) {
//                //System.out.println("i = "+m);
//                for (int j = 0; j < 1682; j++) {
//                    System.out.print(matrix2[m][j] + "  ");
//                }
//                System.out.println();
//                //
//            }
        } catch (Exception e) {
            System.out.println(e);
        }
        
    }
    
       String parse(String s) {
        String temp[] = s.split("-");
        return temp[2];
    }


    public double itemSimilarity(long arg0, long arg1) throws TasteException {
        System.out.println("Test");
        System.out.println("argo "+ arg0);System.out.println("arg1 "+ arg1);
       return matrix2[(int)arg0-1][(int)arg1-1];
    }

    public double[] itemSimilarities(long arg0, long[] arg1) throws TasteException {
        double result[]=new double[arg1.length];  
        //  System.out.println("Test");
        //System.out.println("argo "+ arg0);
        for(int i=0;i< arg1.length;i++)
        {
            result[i]=matrix2[(int)arg0-1][(int)arg1[i]-1];
        }
        
        return result;
    }

    public long[] allSimilarItemIDs(long arg0) throws TasteException {
        
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void refresh(Collection<Refreshable> arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
public class Content_Based {

    double matrix1[][] = new double[1682][1682];
    double matrix2[][] = new double[1682][1682];

    public Content_Based() {
        try {
//           long arr[]=new long[1682];
//           for(int i=0;i<1682;i++)
//               arr[i]=i+1;
//           
//           
//            File ratingsFile = new File("C:/Users/Ankur/Documents/NetBeansProjects/ML_Project/src/ml_project/ml-100k/u1.base");
//            DataModel model = new FileDataModel(ratingsFile);
//           System.out.println("ANkur"); 
//            
//            EuclideanDistanceSimilarity sim1=new EuclideanDistanceSimilarity(model);
//            
//            for(long i=0;i<1682;i++)
//                matrix1[(int)i]=sim1.itemSimilarities(i+1,arr);
//            
//            
//            
//            for(int i=0;i<1682;i++)
//            {
//                for(int j=0;j<1682;j++)
//                    System.out.print(matrix1[i][j]+"  ");
//                System.out.println();
//            }
//            
//            
//
            


            String name="C:/Users/Ankur/Desktop/ML Project/ml-100k/ua.base";
            File ratingsFile = new File(name);
            DataModel model = new FileDataModel(ratingsFile);

             RecommenderBuilder builder1 = new RecommenderBuilder() {
                public Recommender buildRecommender(DataModel model) throws TasteException {
                    vector_similairtty vec=new vector_similairtty();
                    return new CachingRecommender(new GenericItemBasedRecommender(model,vec));
                }
            };
            RecommenderEvaluator evaluator1 = new RMSRecommenderEvaluator();
            double score1 = evaluator1.evaluate(builder1, null,model,0.8,0.2);
       
            System.out.println("With VectorSimilarity = " + score1);
            


            
             String name1="C:/Users/Ankur/Desktop/ML Project/ml-100k/ua.base";
            File ratingsFile1 = new File(name1);
            DataModel model1 = new FileDataModel(ratingsFile1);

             RecommenderBuilder builder2 = new RecommenderBuilder() {
                public Recommender buildRecommender(DataModel model1) throws TasteException {
                   // vector_similairtty vec=new vector_similairtty();
                    return new CachingRecommender(new  GenericItemBasedRecommender(model1,new EuclideanDistanceSimilarity(model1)));
                }
            };
            RecommenderEvaluator evaluator2 = new RMSRecommenderEvaluator();
            double score2 = evaluator2.evaluate(builder1, null,model,0.8,0.2);
       
            System.out.println("With EuclideanSimilarity = " + score2);
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public static void main(String[] args) {
        new Content_Based();
    }
}
