package ml_project;

import JSci.maths.vectors.DoubleVector;
import java.io.BufferedReader;
import java.io.File;

import java.io.FileReader;
import java.util.Collection;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.RMSRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.ClusterSimilarity;
import org.apache.mahout.cf.taste.impl.recommender.FarthestNeighborClusterSimilarity;
import org.apache.mahout.cf.taste.impl.recommender.NearestNeighborClusterSimilarity;
import org.apache.mahout.cf.taste.impl.recommender.TreeClusteringRecommender;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.PreferenceInferrer;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

class User_Vector_Similarity implements UserSimilarity {

    double feature[][] = new double[943][38];
    double matrix2[][] = new double[943][943];

    public User_Vector_Similarity(DataModel model, int sim) {

        double feature[][] = new double[943][38];
        try {
            for (int i = 0; i < 943; i++) {
                for (int j = 0; j < 38; j++) {
                    feature[i][j] = 0;
                }
            }
            BufferedReader buff = new BufferedReader(new FileReader("C:/Users/Ankur/Desktop/ML Project/ml-100k/u.user"));
            String line = null;
            String[] temp;
            int ft;
            int i = 0;
            while ((line = buff.readLine()) != null) {
                temp = line.split(",");
                int age = Integer.parseInt(temp[1]);
                if (age <= 18) {
                    feature[i][0] = 1;
                } else if (age > 18 && age <= 29) {
                    feature[i][1] = 1;
                } else if (age > 29 && age <= 49) {
                    feature[i][2] = 1;
                } else {
                    feature[i][3] = 1;
                }
                if (temp[2].equals("M")) {
                    feature[i][4] = 1;
                } else {
                    feature[i][5] = 1;
                }
                String occ = temp[3];
                if (occ.equals("administrator")) {
                    feature[i][6] = 1;
                } else if (occ.equals("artist")) {
                    feature[i][7] = 1;
                } else if (occ.equals("doctor")) {
                    feature[i][8] = 1;
                } else if (occ.equals("educator")) {
                    feature[i][9] = 1;
                } else if (occ.equals("engineer")) {
                    feature[i][10] = 1;
                } else if (occ.equals("entertainment")) {
                    feature[i][11] = 1;
                } else if (occ.equals("executive")) {
                    feature[i][12] = 1;
                } else if (occ.equals("healthcare")) {
                    feature[i][13] = 1;
                } else if (occ.equals("homemaker")) {
                    feature[i][14] = 1;
                } else if (occ.equals("lawyer")) {
                    feature[i][15] = 1;
                } else if (occ.equals("librarian")) {
                    feature[i][16] = 1;
                } else if (occ.equals("marketing")) {
                    feature[i][17] = 1;
                } else if (occ.equals("none")) {
                    feature[i][18] = 1;
                } else if (occ.equals("other")) {
                    feature[i][19] = 1;
                } else if (occ.equals("programmer")) {
                    feature[i][20] = 1;
                } else if (occ.equals("retired")) {
                    feature[i][21] = 1;
                } else if (occ.equals("salesman")) {
                    feature[i][22] = 1;
                } else if (occ.equals("scientist")) {
                    feature[i][23] = 1;
                } else if (occ.equals("student")) {
                    feature[i][24] = 1;
                } else if (occ.equals("technician")) {
                    feature[i][25] = 1;
                } else if (occ.equals("writer")) {
                    feature[i][26] = 1;
                }
                if (temp[4].charAt(0) >= 65 && temp[4].charAt(0) <= 90) {
                    feature[i][37] = 1;
                } else {
                    int code = Integer.parseInt(temp[4]);
                    if (code > 0 && code <= 100000) {
                        feature[i][27] = 1;
                    } else if (code > 100001 && code <= 200000) {
                        feature[i][28] = 1;
                    } else if (code > 200001 && code <= 300000) {
                        feature[i][29] = 1;
                    } else if (code > 300001 && code <= 400000) {
                        feature[i][30] = 1;
                    } else if (code > 400001 && code <= 500000) {
                        feature[i][31] = 1;
                    } else if (code > 500001 && code <= 600000) {
                        feature[i][32] = 1;
                    } else if (code > 600001 && code <= 700000) {
                        feature[i][33] = 1;
                    } else if (code > 700001 && code <= 800000) {
                        feature[i][34] = 1;
                    } else if (code > 800001 && code <= 900000) {
                        feature[i][35] = 1;
                    } else if (code > 900001) {
                        feature[i][36] = 1;
                    }
                }
                i++;
            }

            DoubleVector cVector[] = new DoubleVector[943];
            double array[][];

            if (sim == 1) {
                array = new double[943][4];

                for (i = 0; i < 943; i++) {
                    for (int j = 0; j < 4; j++) {

                        array[i][j] = feature[i][j];
                    }
                }
            } else if (sim == 2) {
                array = new double[943][2];
                int l = 0;
                for (i = 0; i < 943; i++) {
                    l = 0;
                    for (int j = 4; j < 6; j++) {

                        array[i][l] = feature[i][j];
                        l++;
                    }
                }
            } else if(sim==3)
            {

                array = new double[943][21];
                int l = 0;
                for (i = 0; i < 943; i++) {
                    l = 0;
                    for (int j = 6; j <= 26; j++) {

                        array[i][l] = feature[i][j];
                        l++;
                    }
                }
            }
            else if(sim==4)
            {
                
                array = new double[943][6];
                int l = 0;
                for (i = 0; i < 943; i++) {
                    l = 0;
                    for (int j = 0; j < 6; j++) {

                        array[i][l] = feature[i][j];
                        l++;
                    }
                }
            }
            else if(sim==5)
            {
              array = new double[943][25];
                int l = 0;
                for (i = 0; i < 943; i++) {
                    l = 0;
                    for (int j = 0; j < 4; j++) {

                        array[i][l] = feature[i][j];
                        l++;
                    }
                }
                
                for (i = 0; i < 943; i++) {
                    l = 4;
                    for (int j = 6; j <=26; j++) {

                        array[i][l] = feature[i][j];
                        l++;
                    }
                }
                
            }
            else
            {
                array = new double[943][23];
                int l = 0;
                for (i = 0; i < 943; i++) {
                    l = 0;
                    for (int j = 4; j < 6; j++) {

                        array[i][l] = feature[i][j];
                        l++;
                    }
                }
                
                for (i = 0; i < 943; i++) {
                    l = 2;
                    for (int j = 6; j <=26; j++) {

                        array[i][l] = feature[i][j];
                        l++;
                    }
                }
                
                
            }


            for (int n = 0; n < 943; n++) {
                cVector[n] = new DoubleVector(array[n]);
            }

            for (int n = 0; n < 943; n++) {
                for (int m = 0; m < 943; m++) {

                    matrix2[n][m] = cVector[n].scalarProduct(cVector[m]);
                }
            }


        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public double userSimilarity(long arg0, long arg1) throws TasteException {
        double simcol = matrix2[(int) arg0 - 1][(int) arg1 - 1];
        return simcol;
    }

    public void setPreferenceInferrer(PreferenceInferrer arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void refresh(Collection<org.apache.mahout.cf.taste.common.Refreshable> arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

public class User_Content_Based2 {

    public User_Content_Based2() {
        try {


            File ratingsFile = new File("C:/Users/Ankur/Desktop/ml-100k/Merged_usera1.txt");
            DataModel model = new FileDataModel(ratingsFile);

            //Based on age
            RecommenderBuilder recBuilder1 = new RecommenderBuilder() {

                public Recommender buildRecommender(DataModel model)
                        throws TasteException {
                    User_Vector_Similarity similarity = new User_Vector_Similarity(model, 1);
                    ClusterSimilarity clusterSimilarity = new FarthestNeighborClusterSimilarity(similarity);
                    Recommender recommender = new TreeClusteringRecommender(model, clusterSimilarity, 4);
                    return recommender;
                }
            };
            RecommenderEvaluator evaluator1 = new RMSRecommenderEvaluator();
            double score1 = evaluator1.evaluate(recBuilder1, null, model, 0.8, 0.01);
            System.out.println("Based on Age =  " + score1);


            RecommenderBuilder recBuilder2 = new RecommenderBuilder() {

                public Recommender buildRecommender(DataModel model)
                        throws TasteException {
                    User_Vector_Similarity similarity = new User_Vector_Similarity(model, 2);
                    ClusterSimilarity clusterSimilarity = new FarthestNeighborClusterSimilarity(similarity);
                    Recommender recommender = new TreeClusteringRecommender(model, clusterSimilarity, 2);
                    return recommender;
                }
            };

            RecommenderEvaluator evaluator2 = new RMSRecommenderEvaluator();
            double score2 = evaluator2.evaluate(recBuilder2, null, model, 0.8, 0.01);
            System.out.println("Based on Gender =  " + score2);



            RecommenderBuilder recBuilder3 = new RecommenderBuilder() {

                public Recommender buildRecommender(DataModel model)
                        throws TasteException {
                    User_Vector_Similarity similarity = new User_Vector_Similarity(model, 3);
                    ClusterSimilarity clusterSimilarity = new NearestNeighborClusterSimilarity(similarity);
                    Recommender recommender = new TreeClusteringRecommender(model, clusterSimilarity, 5);
                    return recommender;
                }
            };

            RecommenderEvaluator evaluator3 = new RMSRecommenderEvaluator();
            double score3 = evaluator3.evaluate(recBuilder3, null, model, 0.8, 0.01);
            System.out.println("Based on Occupation =  " + score3);
            
            
            
            RecommenderBuilder recBuilder4 = new RecommenderBuilder() {

                public Recommender buildRecommender(DataModel model)
                        throws TasteException {
                    User_Vector_Similarity similarity = new User_Vector_Similarity(model, 4);
                    ClusterSimilarity clusterSimilarity = new NearestNeighborClusterSimilarity(similarity);
                    Recommender recommender = new TreeClusteringRecommender(model, clusterSimilarity, 5);
                    return recommender;
                }
            };

            RecommenderEvaluator evaluator4 = new RMSRecommenderEvaluator();
            double score4 = evaluator4.evaluate(recBuilder4, null, model, 0.8, 0.01);
            System.out.println("Based on Age and Gender =  " + score4);
            
            
              RecommenderBuilder recBuilder5 = new RecommenderBuilder() {

                public Recommender buildRecommender(DataModel model)
                        throws TasteException {
                    User_Vector_Similarity similarity = new User_Vector_Similarity(model,5 );
                    ClusterSimilarity clusterSimilarity = new NearestNeighborClusterSimilarity(similarity);;
                    Recommender recommender = new TreeClusteringRecommender(model, clusterSimilarity, 5);
                    return recommender;
                }
            };

            RecommenderEvaluator evaluator5 = new RMSRecommenderEvaluator();
            double score5 = evaluator5.evaluate(recBuilder5, null, model, 0.8, 0.01);
            System.out.println("Based on Age and Occupation =  " + score5);
            
            
              RecommenderBuilder recBuilder6 = new RecommenderBuilder() {

                public Recommender buildRecommender(DataModel model)
                        throws TasteException {
                    User_Vector_Similarity similarity = new User_Vector_Similarity(model, 6);
                    ClusterSimilarity clusterSimilarity = new NearestNeighborClusterSimilarity(similarity);
                    Recommender recommender = new TreeClusteringRecommender(model, clusterSimilarity, 5);
                    return recommender;
                }
            };

            RecommenderEvaluator evaluator6 = new RMSRecommenderEvaluator();
            double score6 = evaluator6.evaluate(recBuilder6, null, model, 0.8, 0.01);
            System.out.println("Based on Gender and Occupation =  " + score6);


        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] srga) {
        new User_Content_Based2();
    }
}
