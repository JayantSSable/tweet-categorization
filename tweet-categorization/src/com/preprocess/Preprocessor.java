package com.preprocess;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Preprocessor {
	
	public Map <String , Integer> tweetPolitcs = new HashMap <String , Integer>();
	public Map <String , Integer> tweetSports = new HashMap <String , Integer>();
	public Map <String , Integer> tweetTechnology = new HashMap <String , Integer>();
	
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
	 * Removing re-tweet.
	 */
	
	public String removeReTweet(String val)
	{
		String res = new String("");
			
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
	
	public static void main(String [] args) throws Exception
	{
		File file = new File("training.txt");
		if (!file.exists()) {
			file.createNewFile();
		}
		/*
		 * 
		 * Politics Preprocessing
		 */
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		int tweetCountPolitics = 0;
		Preprocessor p = new Preprocessor();
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
		
		br.close();

		bw.close();
	}
}
