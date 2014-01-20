package com.algorithm;

import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

import com.preprocess.Preprocessor;

public class CallNaiveBayes {

	public static void main(String args[]) throws IOException
	{
		NaiveBayes NB = new NaiveBayes();
		NB.callNaiveBayes();
		NB.calculatePrioriProb();
		int sportsCnt = 0, technologyCnt = 0 , politicsCnt = 0;
		Preprocessor tweet = new Preprocessor();
		sportsCnt = NaiveBayes.sportsHash.size();
		technologyCnt = NaiveBayes.technologyHash.size();
		politicsCnt = NaiveBayes.politicsHash.size();
		
		/*
		 * 
	
		for(String Key : NaiveBayes.sportsHash.keySet())
		{
			System.out.println(Key+" -> "+NaiveBayes.sportsHash.get(Key)+"\n");
			sportsCnt++;
		}
		
		for(String Key : NaiveBayes.technologyHash.keySet())
		{
			System.out.println(Key+" -> "+NaiveBayes.technologyHash.get(Key)+"\n");
			technologyCnt++;
		}
		
		for(String Key : NaiveBayes.politicsHash.keySet())
		{
			System.out.println(Key+" -> "+NaiveBayes.politicsHash.get(Key)+"\n");
			politicsCnt++;
		}
		
		*
		*/
		
		System.out.println("Sports Unique Count: "+sportsCnt);
		System.out.println("Politics Unique Count: "+politicsCnt);
		System.out.println("Technology Unique Count: "+technologyCnt);
		
		double sportsProbablity = 0.0, politicsProbablity = 0.0, technologyProbablity = 0.0;
		Scanner sc = new Scanner(System.in);
		String inp = sc.nextLine();
		//tweet.callPreprocessor();
		
		inp = tweet.PreprocessLine(inp);
		StringTokenizer token = new StringTokenizer(inp);		
		while(token.hasMoreElements())
		{
			String str = (String) token.nextElement();
			str = str.trim();
			str = str.toLowerCase();
			sportsProbablity += NB.calculateLikelihoodProbSports(str);
			politicsProbablity += NB.calculateLikelihoodProbPolitics(str);
			technologyProbablity += NB.calculateLikelihoodProbTechnology(str);
			//System.out.println(sportsProbablity+" "+politicsProbablity+" "+technologyProbablity+"\n");
		}
		sportsProbablity += Math.log((double) (NB.sportsPrioriProbTweets));
		politicsProbablity += Math.log((double) (NB.politicsPrioriProbTweets));
		technologyProbablity += Math.log((double) (NB.technologyPrioriProbTweets));
		
		System.out.println("Sports Probablity: "+sportsProbablity);
		System.out.println("Politics Probablity: "+politicsProbablity);		
		System.out.println("Technology Probablity: "+technologyProbablity);
		
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
