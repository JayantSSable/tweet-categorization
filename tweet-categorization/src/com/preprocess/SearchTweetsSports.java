package com.preprocess;
import twitter4j.*;

import java.io.*;
import java.util.List;

public class SearchTweetsSports{
    public static void search() {
        Twitter twitter = new TwitterFactory().getInstance();
  try{
            Query querysports = new Query("Sports");
         
            
            querysports.setLang("en");
         
            
            querysports.setCount(100);
            
            int j=0,flag = 0;
            FileWriter fw2;
            QueryResult resultsports;
          
//streaming sports tweets
          do {
            	resultsports = twitter.search(querysports);
                List<Status> tweets = resultsports.getTweets();
                File fp2 =  new File("sports.txt");
                fw2 = new FileWriter(fp2,true);
                for (Status tweet : tweets) 
                {
                    j++;
                    if(j>500)
                    {
                    	flag= 1;
                    	break;
                    }
                	fw2.write("\n" + tweet.getText());
                    //System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
                }
                if(flag == 1)
                	break;
                
            } while ((querysports = resultsports.nextQuery()) != null);
            fw2.close();
   } catch (TwitterException te) {
       te.printStackTrace();
       System.out.println("Failed to search tweets: " + te.getMessage());
       System.exit(-1);
   } catch (IOException e){
		e.printStackTrace();
	}
}
}

 



