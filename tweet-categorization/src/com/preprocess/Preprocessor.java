package com.preprocess;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
public class Preprocessor {
	
	public static int tweetCountPoliticsFinal;
	public static int tweetCountSportsFinal;
	public static int tweetCountTechnologyFinal;
	public static int tweetCountTotalFinal;
	public Map <String , Integer> tweetPolitcs = new HashMap <String , Integer>();
	public Map <String , Integer> tweetSports = new HashMap <String , Integer>();
	public Map <String , Integer> tweetTechnology = new HashMap <String , Integer>();
	public Map <String , Integer> stopword = new HashMap <String , Integer>();
	//public Map <String , Integer> alphaNumeral = new HashMap <String , Integer>();
	/*
	 * 
	 * Removes links, url's etc.
	 */
	
	public String removeLinks(String val)
	{
		String res = new String("");
		StringTokenizer token = new StringTokenizer(val);
		while(token.hasMoreElements())
		{
			String str = (String) token.nextElement();
			CharSequence ch = "http://";
			CharSequence ch1 = "www.";
			CharSequence ch2 = "https://";
			CharSequence ch3 = "ssh://";
			CharSequence ch4 = "ftp://";
			if( !str.contains(ch) )
			{
				if( !str.contains(ch1) )
				{
					if( !str.contains(ch2) )
					{
						if( !str.contains(ch3) )
						{
							if( !str.contains(ch4) )
							{
								res = res + str + " ";
							}
						}
					}
				}
			}
	
	/* 
	 * 		if( !str.contains(ch) || !str.contains(ch1) || !str.contains(ch2) || !str.contains(ch3) || !str.contains(ch4))
	 *		res = res + str + " ";
	 */
		}
		return res;
	}
	
	/* 
	 * removes twitter user name.
	 */
	
	public String removeUsername(String val)
	{
		String res = new String("");
		StringTokenizer token = new StringTokenizer(val);
		while(token.hasMoreElements())
			{
				String str = (String) token.nextElement();
				CharSequence ch = "@";
				if(!str.contains(ch))
					res = res + str + " ";
	   /*	
		* if(!str.startsWith("@"))
		* 	{
		*		if(!str.startsWith("\"@"))
		*			res = res + str + " ";
		*	}
		*
		* System.out.println(token.nextElement()+"\n");
		*/
			}
		return res;
	}
	
	/*
	 * 
	 * Removing stopwords.
	 */
	
	public String removeStopwords(String val)
	{
		String res = new String("");
		StringTokenizer token = new StringTokenizer(val);
		while(token.hasMoreElements())
		{
			String str = (String) token.nextElement();
			if(!(stopword.containsKey(str)))
			{
				res = res + str + " ";
			}
				
		}
		return res;
	}
	
	/*
	 * 
	 * Remove # from hash tag.
	 */
	
	public String removeHash(String val)
	{
		String res = new String("");
		StringTokenizer token = new StringTokenizer(val);
		while(token.hasMoreElements())
		{
			String str = (String) token.nextElement();
			if(str.startsWith("#"))
			{
				res = res + str.substring(1,(int)str.length()) + " ";
			}
			else
				res = res + str + " ";
		}
		return res;
	}
	
	/*
	 * 
	 * Remove Emoticons from tweets
	 */
	
	public String removeEmoticons(String result)
	{
		result = result.replaceAll(":[)]", "");
		result = result.replaceAll(";[)]", "");
		result = result.replaceAll(":-[)]", "");
		result = result.replaceAll(";-[)]", "");
		result = result.replaceAll(":d", "");
		result = result.replaceAll(";d", "");
		result = result.replaceAll("=[)]", "");
		result = result.replaceAll("\\^_\\^", "");
		result = result.replaceAll(":[(]", "");
		result = result.replaceAll(":-[(]", "");
		return result;
	}
	
	/*
	 * 
	 * maintaining alpha numerals.
	 */
	public String removeExtra(String val)
	{
		String res = new String("");
		for(int i=0;i<val.length();i++)
		{
			char ch = val.charAt(i);
			int c = (int) ch;
			if((c>=65 && c<=90) || (c>=97 && c<=122) || (c==32))
				res = res + ch;
		}
		return res;
	}
	
	public void callPreprocessor() throws IOException
	{
		
		
		/* gathering Politics tweets*/
		//SearchTweetsPolitics.search();
		
		/* gathering Sports tweets*/

		//SearchTweetsSports.search();
		
		/* gathering Technology tweets*/
		//SearchTweetsTechnology.search();
		
		File file = new File("training.txt");
		if (!file.exists()) {
			file.createNewFile();
		}
		
		/*
		 * Stopword Preprocessing
		 */
		
		Preprocessor p = new Preprocessor();
		File stop = new File("stop.txt");
		FileReader st = new FileReader(stop);
		BufferedReader br1 = new BufferedReader(st);
		String word;
		while((word = br1.readLine()) != null)
		{
			p.stopword.put(word, 1);
		}
		br1.close();
		
		/*
		 * 
		 * Politics Preprocessing
		 */
		
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		int tweetCountPolitics = 0;
		FileReader politics = new FileReader("politics.txt");
		BufferedReader br = new BufferedReader(politics);
		String line;
		while((line = br.readLine()) != null)
		{
			tweetCountPolitics += 1;
			line = p.removeUsername(line);
			line = p.removeLinks(line);
			line = p.removeHash(line);
			line = p.removeEmoticons(line);
			line = p.removeStopwords(line);
			line = p.removeExtra(line);
			if(!line.startsWith("RT"))
			{
				p.tweetPolitcs.put(line,1);
			}
			
		/* System.out.println(line);
		 *	
		 * System.out.println(line+"\n");
		 */
			
		}
		System.out.println("*****************************************************************************************");
		for(String key : p.tweetPolitcs.keySet())
		{
			String str = "politics\t"+key+"\n";
			bw.write(str);
			System.out.println("politics"+"\t"+key);
		}
		System.out.println("*****************************************************************************************");
		
		br.close();
		/*
		 * 
		 * Sports Preprocessing
		 */
		int tweetCountSports = 0;
		FileReader sports = new FileReader("sports.txt");
		br = new BufferedReader(sports);
		while((line = br.readLine()) != null)
		{
			tweetCountSports += 1;
			line = p.removeUsername(line);
			line = p.removeLinks(line);
			line = p.removeHash(line);
			line = p.removeEmoticons(line);
			line = p.removeStopwords(line);
			line = p.removeExtra(line);
			if(!line.startsWith("RT"))
			{
				p.tweetSports.put(line,1);
			}
			
		/* System.out.println(line);
		 *	
		 * System.out.println(line+"\n");
		 */
			
		}
		System.out.println("*****************************************************************************************");
		for(String key : p.tweetSports.keySet())
		{
			String str = "sports\t\t"+key+"\n";
			bw.write(str);
			System.out.println("sports"+"\t\t"+key);
		}
		System.out.println("*****************************************************************************************");
		
		br.close();
		
		/*
		 * 
		 * 
		 * Technology Preprocessing
		 */
		
		int tweetCountTechnology = 0;
		FileReader technology = new FileReader("technology.txt");
		br = new BufferedReader(technology);
		while((line = br.readLine()) != null)
		{
			tweetCountTechnology += 1;
			line = p.removeUsername(line);
			line = p.removeLinks(line);
			line = p.removeHash(line);
			line = p.removeEmoticons(line);
			line = p.removeStopwords(line);
			line = p.removeExtra(line);
			if(!line.startsWith("RT"))
			{
				p.tweetTechnology.put(line,1);
			}
			
		/* System.out.println(line);
		 *	
		 * System.out.println(line+"\n");
		 */
			
		}
		System.out.println("*****************************************************************************************");
		for(String key : p.tweetTechnology.keySet())
		{
			String str = "technology\t"+key+"\n";
			bw.write(str);
			System.out.println("technology"+"\t"+key);
		}
		System.out.println("*****************************************************************************************");
		
		System.out.println("Politics TweetCount: "+tweetCountPolitics+" Hashcount: "+p.tweetPolitcs.size());
		System.out.println("Sports TweetCount: "+tweetCountTechnology+" Hashcount: "+p.tweetTechnology.size());
		System.out.println("Technology TweetCount: "+tweetCountSports+" Hashcount: "+p.tweetSports.size());
		tweetCountPoliticsFinal = p.tweetPolitcs.size();
		tweetCountSportsFinal = p.tweetSports.size();
		tweetCountTechnologyFinal = p.tweetTechnology.size();
		tweetCountTotalFinal = tweetCountPoliticsFinal+tweetCountSportsFinal+tweetCountTechnologyFinal;
		br.close();

		bw.close();
	}
}
