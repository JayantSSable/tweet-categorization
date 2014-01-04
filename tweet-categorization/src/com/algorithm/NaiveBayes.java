package com.algorithm;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import com.preprocess.*;
import java.util.StringTokenizer;
public class NaiveBayes {
	
	public int wordsCountTechnology=0;
	public int wordsCountPolitics=0;
	public int wordsCountSports=0; 
	
	public double sportsPrioriProbTweets=0.0;
	public double technologyPrioriProbTweets=0.0;
	public double politicsPrioriProbTweets=0.0;
	
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
			 */
			
			if(str.equals("technology"))
			{
				while(token.hasMoreElements())
					{
						wordsCountTechnology++;
						str = (String) token.nextElement();
					}
			}
			
			/*
			 * 
			 * Words counting with Politics Labels
			 */
			
			if(str.equals("politics"))
			{
				while(token.hasMoreElements())
				{
					wordsCountPolitics++;
					str = (String) token.nextElement();
				}
			}

			/*
			 * 
			 * Words counting with Sports Labels
			 */
			
			if(str.equals("sports"))
			{
				while(token.hasMoreElements())
				{
					wordsCountSports++;
					str = (String) token.nextElement();
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
	
	
	
	public static void main(String args[]) throws IOException
	{
		NaiveBayes NB = new NaiveBayes();
		NB.callNaiveBayes();
		NB.calculatePrioriProb();
	}
}
