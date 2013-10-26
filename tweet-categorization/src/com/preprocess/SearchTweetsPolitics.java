package com.preprocess;


import twitter4j.*;

import java.io.*;
import java.util.List;

public class SearchTweetsPolitics {
    public static void search() {
        Twitter twitter = new TwitterFactory().getInstance();
   try {
            Query querypolitics = new Query("Politics");
         
            
            querypolitics.setLang("en");
         
            
            querypolitics.setCount(100);
            
            int i = 0,flag = 0;
            FileWriter fw1;
            QueryResult resultpolitics;
          
            //streaming politics tweets
            do 
            {
            	resultpolitics = twitter.search(querypolitics);
                List<Status> tweets = resultpolitics.getTweets();
                File fp1 =  new File("politics.txt");
                fw1 = new FileWriter(fp1,true);
                for (Status tweet : tweets) 
                {
                	i++;
                	if(i >500)
                	{
                		flag = 1;
                		break;
                	}
                    fw1.write("\n" + tweet.getText());
                    //System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
                }
                if(flag == 1)
                	break;
                
            } while ((querypolitics = resultpolitics.nextQuery()) != null);
            fw1.close();
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to search tweets: " + te.getMessage());
            System.exit(-1);
        } catch (IOException e){
			e.printStackTrace();
		}
    }
}
