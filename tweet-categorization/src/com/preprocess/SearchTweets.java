package com.preprocess;


import twitter4j.*;

import java.io.*;
import java.util.List;

public class SearchTweets {
    public static void main(String[] args) {
        Twitter twitter = new TwitterFactory().getInstance();
        try {
            Query querypolitics = new Query("Politics");
            Query querysports = new Query("Sports");
            Query querytechnology = new Query("Technology");
            querypolitics.setLang("en");
            querysports.setLang("en");
            querytechnology.setLang("en");
            querypolitics.setCount(100);
            querysports.setCount(100);
            querytechnology.setCount(100);
            QueryResult resultpolitics,resultsports,resulttechnology;
            
            do {
            	resultpolitics = twitter.search(querypolitics);
                List<Status> tweets = resultpolitics.getTweets();
                File fp1 =  new File("politics.txt");
                FileWriter fw1 = new FileWriter(fp1,true);
                for (Status tweet : tweets) {
                    fw1.write("\n" + tweet.getText());
                    //System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
                }
                fw1.close();
            } while ((querypolitics = resultpolitics.nextQuery()) != null);
            do {
            	resultsports = twitter.search(querysports);
                List<Status> tweets = resultsports.getTweets();
                File fp2 =  new File("sports.txt");
                FileWriter fw2 = new FileWriter(fp2,true);
                for (Status tweet : tweets) {
                    fw2.write("\n" + tweet.getText());
                    //System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
                }
                fw2.close();
            } while ((querysports = resultsports.nextQuery()) != null);
            do {
            	resulttechnology = twitter.search(querytechnology);
                List<Status> tweets = resulttechnology.getTweets();
                File fp3 =  new File("technology.txt");
                FileWriter fw3 = new FileWriter(fp3,true);
                for (Status tweet : tweets) {
                    fw3.write("\n" + tweet.getText());
                    //System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
                }
                fw3.close();
            } while ((querytechnology = resulttechnology.nextQuery()) != null);
            System.exit(0);
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to search tweets: " + te.getMessage());
            System.exit(-1);
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
