package ml_project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.CachingRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.SpearmanCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

public class Recommend {

    public Recommend() {


        try {
            File ratingsFile = new File("C:/Users/Ankur/Desktop/ml-100k/ua.base");
            DataModel model = new FileDataModel(ratingsFile);

            System.out.println("User Based ");
          //  UserSimilarity userSimilarity = new PearsonCorrelationSimilarity(model);           //correleation method
            
             UserSimilarity userSimilarity = new SpearmanCorrelationSimilarity(model);           
            
            //userSimilarity.setPreferenceInferrer(new AveragingPreferenceInferrer(model));      //when their is no rating for an item

           UserNeighborhood neighborhood =
                    new NearestNUserNeighborhood(1, userSimilarity, model);       //10 most simsilar user

            //get the recommendations for the user
            Recommender recommender =
                    new GenericUserBasedRecommender(model, neighborhood,userSimilarity);
             
           //  Recommender recommender=new RandomRecommender(model);

//            for(int i=1;i<3;i++)
//            {
//                for(int j=i+1;j<=3;j++)
//                    System.out.println(userSimilarity.userSimilarity(i, j));
//            }
            
          //  System.out.println(userSimilarity.userSimilarity(1, 2));
            
            //System.out.println(recommender.estimatePreference(3,3));
            
            
            
            
            // printUserPrefences(model, recommender);

            meanSquare(recommender);


        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void printUserPrefences(DataModel model, Recommender recommender) {
        try {

            for (LongPrimitiveIterator it = model.getUserIDs(); it.hasNext();) {
                long userId = it.nextLong();

                // Recommender cachingRecommender = new CachingRecommender(recommender);
                CachingRecommender cachingRecommender = new CachingRecommender(recommender);
                List<RecommendedItem> recommendations = cachingRecommender.recommend(userId, 10);
                

                //recommendations
                //if empty write something
                if (recommendations.size() == 0) {
                    System.out.print("User ");
                    System.out.print(userId);
                    System.out.println(": no recommendations");
                }

                // print the list of recommendations for each 
                for (RecommendedItem recommendedItem : recommendations) {

                    System.out.print("User ");
                    System.out.print(userId);
                    System.out.print(": ");
                    System.out.println(recommendedItem.getItemID() + "   " + recommendedItem.getValue());
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }
    void meanSquare(Recommender recommend) {
        try {

            File ratingsFile = new File("C:/Users/Ankur/Desktop/ml-100k/ua.test");
            BufferedReader buff = new BufferedReader(new FileReader(ratingsFile));
            //DataModel model = new FileDataModel(ratingsFile);

            float mean = 0;
            float value = 0;
            float count =0;
            String line = null;
            String[] temp;
            while ((line = buff.readLine()) != null) {
                count++;

                temp = line.split("\t");
                System.out.println(temp[0] + "  " + temp[1] + "  " + temp[2]);
                value = recommend.estimatePreference(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]));
               // value = Math.round(value);
                System.out.println("Value   " + value);
                mean += Math.pow((value - Double.parseDouble(temp[2])), 2);
            }

            double ans=mean/count;
             System.out.println("Mean Squre Error ="+ ans);

            


        } catch (Exception e) {

            
        System.out.println(e);
        }

    }

    public static void main(String[] args) {
        new Recommend();
    }
}
