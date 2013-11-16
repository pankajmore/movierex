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
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.CachingRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.PreferenceInferrer;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

/**
 *
 * @author Ankur
 */
class User_Vector_Similarity implements UserSimilarity {

    double feature[][] = new double[943][38];
    double matrix2[][] = new double[943][943];
    double matrix3[][] = new double[943][943];
    int itemRated[] = new int[943];
    int threshold;

    public User_Vector_Similarity(DataModel model, int threshold) {

        this.threshold = threshold;
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

//System.out.println(temp[1]);
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
             
            DoubleVector vector[] = new DoubleVector[943];
            for (int n = 0; n < 943; n++) {
                vector[n] = new DoubleVector(feature[n]);
            }



            double fx = 0, local_sum = 0;
            double weigth[] = new double[38];

            for (int n = 0; n < 38; n++) {
                local_sum = 0;
                for (int m = 0; m < 943; m++) {
                    local_sum += feature[m][n];

                }

                weigth[n] = local_sum;

                if (local_sum > fx) {
                    fx = local_sum;
                }

            }



            double weigted_sum = 0;
            for (int n = 0; n < 943; n++) {
                for (int m = 0; m < 943; m++) {
                    weigted_sum = 0;
                    for (int l = 0; l < 38; l++) {
                        weigted_sum += (vector[n].getComponent(l) * vector[m].getComponent(l) * Math.log((fx + .00000001) / (weigth[l] + .00000001)));


                    }
                    matrix2[n][m] = weigted_sum;
                }
            }


//            for (int m = 0; m < 943; m++) {
//                //System.out.println("i = "+m);
//                for (int j = 0; j < 943; j++) {
//                    System.out.print(matrix2[m][j] + "  ");
//                }
//                System.out.println();
//                //
//            }





            for (int l = 0; l < 943; l++) {
                // System.out.println("l = "+l);
                itemRated[l] = model.getItemIDsFromUser(l + 1).size();
            }


//               for (int l = 0; l < 943; l++) {
//                System.out.println(" l = "+l+" = "+itemRated[l]);
//            }


            EuclideanDistanceSimilarity e = new EuclideanDistanceSimilarity(model);
            for (int n = 0; n < 943; n++) {
                for (int l = 0; l < 943; l++) {
                    matrix3[n][l] = e.userSimilarity(n + 1, l + 1);
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public double userSimilarity(long arg0, long arg1) throws TasteException {
        double sim = 0.0;
        double lamda = 0.0;
        if (itemRated[(int) arg0 - 1] <= (1.0 * threshold)) {
            lamda = itemRated[(int) arg0 - 1] / (1.0 * threshold);
        }


        double simnew = matrix3[(int) arg0 - 1][(int) arg1 - 1];
        double simcol = matrix2[(int) arg0 - 1][(int) arg1 - 1];
        sim = ((lamda * simnew) + ((1 - lamda) * simcol)) / (simnew + simcol);
        // System.out.println("arg0 = " + arg0 + " ,itemRated  "+ itemRated[(int)arg0-1] + " threshold = " +threshold+ " lamda = "+lamda+" ,simnew = "+simnew+",simcol = "+simcol+" sim = "+sim);
        return sim;
    }

    public void setPreferenceInferrer(PreferenceInferrer arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void refresh(Collection<Refreshable> arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

public class User_ContentBased {

    public User_ContentBased() {
        try {
            File ratingsFile = new File("C:/Users/Ankur/Desktop/ml-100k/Merged_usera1.txt");
            DataModel model = new FileDataModel(ratingsFile);
           



            RecommenderBuilder builder = new RecommenderBuilder() {

                public Recommender buildRecommender(DataModel model) throws TasteException {
                    User_Vector_Similarity userSimilarity = new User_Vector_Similarity(model, 100);
                    UserNeighborhood neighborhood =
                            new NearestNUserNeighborhood(10, userSimilarity, model);
                    return new CachingRecommender(new GenericUserBasedRecommender(model, neighborhood, userSimilarity));
                }
            };
            RecommenderEvaluator evaluator = new RMSRecommenderEvaluator();
            double score = evaluator.evaluate(builder, null, model, 0.8, 1);
            System.out.println("UserVectorSimilarity =  " + score);


        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] arsg) {
        new User_ContentBased();
    }
}
