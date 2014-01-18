package com.algorithm;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.preprocess.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;
public class NaiveBayes {
	
	public int wordsCountTechnology=0;
	public int wordsCountPolitics=0;
	public int wordsCountSports=0; 
	
	public double sportsPrioriProbTweets=0.0;
	public double technologyPrioriProbTweets=0.0;
	public double politicsPrioriProbTweets=0.0;
	
	public static Map <String, Integer> sportsHash = new HashMap <String, Integer>();
	public static Map <String, Integer> politicsHash = new HashMap <String, Integer>();
	public static Map <String, Integer> technologyHash = new HashMap <String, Integer>();
	
	
	public void callNaiveBayes() throws IOException
	{
		Preprocessor Tweet = new Preprocessor();
		Tweet.callPreprocessor();
		FileReader train = new FileReader("training.txt");
		BufferedReader br = new BufferedReader(train);
		String line;
		while( (line = br.readLine())!=null)
		{
			StringTokenizer token = new StringTokenizer(line);
			String str = (String)token.nextElement();
			
			/*
			 * 
			 * Words counting with Technology Labels
			 * Creating HashMap of Technology To store Unique words Count
			 */
			
			if(str.equals("technology"))
			{
				while(token.hasMoreElements())
					{
						wordsCountTechnology++;
						str = (String) token.nextElement();
						str = str.trim();
						str = str.toLowerCase();
						Integer count = technologyHash.get(str);
						if (count == null) {
						    technologyHash.put(str, 1);
						}
						else {
						    technologyHash.put(str, count + 1);
						}
					}
			}
			
			/*
			 * 
			 * Words counting with Politics Labels
			 * Creating HashMap of Politics To store Unique words Count
			 */
			
			if(str.equals("politics"))
			{
				while(token.hasMoreElements())
				{
					wordsCountPolitics++;
					str = (String) token.nextElement();
					str = str.trim();
					str = str.toLowerCase();
					Integer count = politicsHash.get(str);
					if (count == null) {
							politicsHash.put(str, 1);
					}
					else {
					    	politicsHash.put(str, count + 1);
					}
				}
			}

			/*
			 * 
			 * Words counting with Sports Labels
			 * Creating HashMap of Sports To store Unique words Count
			 * 
			 */
			
			if(str.equals("sports"))
			{
				while(token.hasMoreElements())
				{
					wordsCountSports++;
					str = (String) token.nextElement();
					str = str.trim();
					str = str.toLowerCase();
					Integer count = sportsHash.get(str);
					if (count == null) {
					    sportsHash.put(str, 1);
					}
					else {
					    sportsHash.put(str, count + 1);
					}
				}
			}
		}
		
		System.out.println("Technology: "+wordsCountTechnology);
		System.out.println("Sports: "+wordsCountSports);
		System.out.println("Politics: "+wordsCountPolitics);
		System.out.println("Tweets in Technology: "+Preprocessor.tweetCountTechnologyFinal);
		System.out.println("Tweets in Sports: "+Preprocessor.tweetCountSportsFinal);
		System.out.println("Tweets in Politics: "+Preprocessor.tweetCountPoliticsFinal);
		System.out.println("Total Tweets: "+Preprocessor.tweetCountTotalFinal);
		br.close();
		train.close();
	}
	
	public void calculatePrioriProb()
	{
		sportsPrioriProbTweets = (double) Preprocessor.tweetCountSportsFinal / Preprocessor.tweetCountTotalFinal;
		politicsPrioriProbTweets = (double) Preprocessor.tweetCountPoliticsFinal / Preprocessor.tweetCountTotalFinal;
		technologyPrioriProbTweets = (double) Preprocessor.tweetCountTechnologyFinal / Preprocessor.tweetCountTotalFinal;
		System.out.println("Priori Probability of Sports: "+sportsPrioriProbTweets);
		System.out.println("Priori Probability of Politics: "+politicsPrioriProbTweets);
		System.out.println("Priori Probability of Technology: "+technologyPrioriProbTweets);
	}
	
	public double calculateLikelihoodProbSports(String str)
	{
		Integer cnt = sportsHash.get(str);
		if(cnt == null)
			return Math.log(0.0001);
		else
			{
				double ret = 0.0;
				ret = (double)(cnt.doubleValue()/wordsCountSports);
				ret = (double)Math.round(ret * 100000) / 100000;
				ret = Math.log(ret);
				return ret;
			}
	}

	public double calculateLikelihoodProbTechnology(String str)
	{
		Integer cnt = technologyHash.get(str);
		if(cnt == null)
			return Math.log(0.0001);
		else
			{
			double ret = 0.0;
			ret = (double)(cnt.doubleValue()/wordsCountTechnology);
			ret = (double)Math.round(ret * 100000) / 100000;
			ret = Math.log(ret);
			return ret;
			}
	}

	public double calculateLikelihoodProbPolitics(String str)
	{
		Integer cnt = politicsHash.get(str);
		if(cnt == null)
			return Math.log(0.0001);
		else
			{
			double ret = 0.0;
			ret = (double)(cnt.doubleValue()/wordsCountPolitics);
			ret = (double)Math.round(ret * 100000) / 100000;
			ret = Math.log(ret);
			return ret;
			}
	}

	
	
	public static void main(String args[]) throws IOException
	{
		NaiveBayes NB = new NaiveBayes();
		NB.callNaiveBayes();
		NB.calculatePrioriProb();
		int sportsCnt = 0, technologyCnt = 0 , politicsCnt = 0;
		
		for(String Key : sportsHash.keySet())
		{
			System.out.println(Key+" -> "+sportsHash.get(Key)+"\n");
			sportsCnt++;
		}
		
		for(String Key : technologyHash.keySet())
		{
			System.out.println(Key+" -> "+technologyHash.get(Key)+"\n");
			technologyCnt++;
		}
		
		for(String Key : politicsHash.keySet())
		{
			System.out.println(Key+" -> "+politicsHash.get(Key)+"\n");
			politicsCnt++;
		}
		
		System.out.println("Sports Unique Count: "+sportsCnt+"\n");
		System.out.println("Politics Unique Count: "+politicsCnt+"\n");
		System.out.println("Technology Unique Count: "+technologyCnt+"\n");
		
		double sportsProbablity = 0.0, politicsProbablity = 0.0, technologyProbablity = 0.0;
		Scanner sc = new Scanner(System.in);
		String inp = sc.nextLine();
		StringTokenizer token = new StringTokenizer(inp);		
		while(token.hasMoreElements())
		{
			String str = (String) token.nextElement();
			str = str.trim();
			str = str.toLowerCase();
			sportsProbablity += NB.calculateLikelihoodProbSports(str);
			politicsProbablity += NB.calculateLikelihoodProbPolitics(str);
			technologyProbablity += NB.calculateLikelihoodProbTechnology(str);
			System.out.println(sportsProbablity+" "+politicsProbablity+" "+technologyProbablity+"\n");
		}
		sportsProbablity += Math.log((double) (NB.sportsPrioriProbTweets));
		politicsProbablity += Math.log((double) (NB.politicsPrioriProbTweets));
		technologyProbablity += Math.log((double) (NB.technologyPrioriProbTweets));
		
		System.out.println("Sports Probablity: "+sportsProbablity+"\n");
		System.out.println("Politics Probablity: "+politicsProbablity+"\n");		
		System.out.println("Technology Probablity: "+technologyProbablity+"\n");
		
		String ANSWER = "";
		
		if(sportsProbablity > politicsProbablity)
		{
			if(sportsProbablity > technologyProbablity)
			{
				ANSWER = "SPORTS!!!!!";
			}
			else
			{
				ANSWER = "TECHNOLOGY!!!!!";
			}
		}
		else
		{
			if(politicsProbablity > technologyProbablity)
			{
				ANSWER = "POLITICS!!!!";
			}
			else
			{
				ANSWER = "TECHNOLOGY!!!!";
			}
		}
		System.out.println("Tweet Belongs to "+ANSWER+" Category! Happy New Year!");
		sc.close();	
	}
}
