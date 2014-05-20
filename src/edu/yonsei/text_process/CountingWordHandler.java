package edu.yonsei.text_process;

import com.aliasi.corpus.ObjectHandler;

import com.aliasi.tokenizer.IndoEuropeanTokenizerFactory;
import com.aliasi.tokenizer.LowerCaseTokenizerFactory;
import com.aliasi.tokenizer.RegExTokenizerFactory;
import com.aliasi.tokenizer.Tokenizer;
import com.aliasi.tokenizer.TokenizerFactory;
import com.aliasi.util.ObjectToCounterMap;
import com.aliasi.tokenizer.EnglishStopTokenizerFactory;
import com.aliasi.tokenizer.PorterStemmerTokenizerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import java.util.List;
import java.util.Scanner;

public class CountingWordHandler {
	final static Charset ENCODING = StandardCharsets.UTF_8;
	static final TokenizerFactory SPACE_TOKENIZER_FACTORY = new RegExTokenizerFactory("\\S+");
	
    long mRecordCount = 0L;
    ObjectToCounterMap<String> mCounter = new ObjectToCounterMap<String>();


    public CountingWordHandler()
    {	
    }
    
    public void handle(String aFileName) {
        ++mRecordCount;

        addFile(aFileName);

    }

    public void addFile(String aFileName) {
        try {
          Scanner scanner =  new Scanner(new FileInputStream(aFileName), ENCODING.name());

          while (scanner.hasNextLine()){
            //process each line in some way
            String text = scanner.nextLine();

            System.out.println("Text: " + text);
	        char[] cs = text.toCharArray();
	        //TokenizerFactory factory = IndoEuropeanTokenizerFactory.INSTANCE;
	        TokenizerFactory factory = SPACE_TOKENIZER_FACTORY;
	        factory = new LowerCaseTokenizerFactory(factory);
	        //factory = new EnglishStopTokenizerFactory(factory);
	        //factory = new PorterStemmerTokenizerFactory(factory);
	        
	        Tokenizer tokenizer = factory.tokenizer(cs,0,cs.length);
	        for (String token : tokenizer) {
	            mCounter.increment(token);
	        }
          }      
        } catch (Exception e) {
        	
        }
    }
    
    public void report() {
        System.out.println("\nRecord Count=" + mRecordCount);
        System.out.println("\nWord Counts");
        List<String> keysByCount = mCounter.keysOrderedByCountList();
        for (String key : keysByCount) {
            int count = mCounter.getCount(key);
            if (count < 10) break;
            System.out.printf("%9d %s\n",count,key);
        }
        
    }
    
    public static void main(String[] args) throws IOException
    {

        CountingWordHandler handler = new CountingWordHandler();

        String file_name = "./data/output.txt";
        File file = new File(file_name);
        handler.handle(file_name);
        handler.report();
    }


}