package edu.yonsei.text_process;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class NaverAPIManager {

	final String key = "";
	public NaverAPIManager()
	{
	
	}
	
	public void collectData(String query) throws Exception
	{
		String initUrl = "http://openapi.naver.com/search?"
				+ "key=" + key
				+ "&query=" + query
				+ "&target=news&start=1&display=1";
		
		// RSS feed default parsing
		Document rss = Jsoup.connect(initUrl).get();
		String s = rss.select("total").text();
		int total = Integer.parseInt(s);
		
		System.out.println("Total results >> " + total);

		String fileDir = "./data/news/" + query + ".txt";
		
		for (int i=1; i<=total; i++) {
			try {
				String parseUrl = "http://openapi.naver.com/search?"
						+ "key=" + key
						+ "&query=" + query
						+ "&target=news&start=" + i
						+ "&display=1";
				rss = Jsoup.connect(parseUrl).get();
				String title = rss.select("item > title").text();
				String originallink = rss.select("item > originallink").text();
				String link = rss.select("item > link").text();
				String pubDate = rss.select("item > pubDate").text();
				
				// Matching keyword extraction from RSS description between triple dot (...)
				String desc = rss.select("item > description").text().replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
				desc = desc.replaceAll("&quot;", "\"");
				String desc2 = desc.replaceAll("[.]{2,}", "");
					
				System.out.println("=====================");
				System.out.println(desc2);
				
				Thread.sleep(3000);
				
				Document article = Jsoup.connect(originallink).get();
				Elements node = article.getElementsContainingOwnText(desc2);
				Element item = node.first();
				String text = item.parent().text().replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
				
				System.out.println("original link >> " + originallink);
				
				FileWriter fstream = new FileWriter(fileDir, true);
				BufferedWriter writer = new BufferedWriter(fstream);
					
				writer.write(title + "$$" + originallink + "$$" + link + "$$" + text + "$$" + pubDate + "$$" + query + "\n");
				writer.close();
					
				System.out.println("Completed!");	
				
				Thread.sleep(3000);
			} catch (Exception e) {
				e.printStackTrace();
				
			}
		}
	}
	
	public static void main(String[] args)
	{
		try {
			NaverAPIManager napim = new NaverAPIManager();
			
			String query = "빙상연맹";
			napim.collectData(query);
		
		} catch (Exception e) {
			//
		}
	}
}
