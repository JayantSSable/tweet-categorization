package com.preprocess;
import twitter4j.*;

import java.io.*;
import java.util.List;

	public class SearchTweetsTechnology 
	{
	    public static void search() {
	    Twitter twitter = new TwitterFactory().getInstance();
	  try{
	            Query querytechnology = new Query("Technology");
	         
	            
	            querytechnology.setLang("en");
	         
	            
	            querytechnology.setCount(100);
	            
	            int k=0,flag = 0;
	            FileWriter fw3;
	            QueryResult resulttechnology;

	

    //streaming technology tweets
    do {
    	resulttechnology = twitter.search(querytechnology);
        List<Status> tweets = resulttechnology.getTweets();
        File fp3 =  new File("technology.txt");
        fw3 = new FileWriter(fp3,true);
        for (Status tweet : tweets)
        {
           k++;
           if(k >500)
           {
        	   flag = 1;
        	   break;
           }
        	fw3.write("\n" + tweet.getText());
            //System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
        }
        if(flag == 1)
        	break;
    } while ((querytechnology = resulttechnology.nextQuery()) != null);
    fw3.close();
} catch (TwitterException te) {
    te.printStackTrace();
    System.out.println("Failed to search tweets: " + te.getMessage());
    System.exit(-1);
} catch (IOException e){
		e.printStackTrace();
	}
}

}
