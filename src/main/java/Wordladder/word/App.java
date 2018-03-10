package Wordladder.word;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.*;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 作者： 徐天强
 * 学号： 516030910391
 * 日期： 2018.3.10
 * 功能： 实现一个wordladder 
 */
public class App 
{
	static int letter_num = 26;     //the number of letters
	
    public static void main( String[] args )
    {
    		HashSet<String>words = read_words();	//read words in the dictionary
    	
    		String word1 = "", word2 = "";
    		int looptime = 0;
    		
    		while (true) {
    			System.out.println();   //add a empty line after one inquiry
    			System.out.println("Word #1 (or q for quit): ");
    			Scanner scan = new Scanner(System.in);
    		
    			if(!scan.hasNext())
    			{
    				break;
    			}
    			word1 = scan.next();
    			if(word1.equals("q"))
    			{
    				break;
    			}
    			System.out.println("Word #2 (or Enter to quit): ");
    			scan = new Scanner(System.in);
    		
    			if(!scan.hasNext())
    			{
    				break;
    			}
    		
    			word2 = scan.next();
    			if(word2.equals("q"))
    			{
    				break;
    			}
    			word1 = word1.toLowerCase();
    			word2 = word2.toLowerCase();
    		
    			if (word1.length() != word2.length()) {            //length error
    				System.out.println("The two words must be the same length.");
    			}
    			else if ((!words.contains(word1)) || (!words.contains(word2))) {  //can't be found in the dictionary
    				System.out.println("The two words must be found in the dictionary.");
    			}
    			else if (word1.equals(word2)) {    //same words
    				System.out.println("The two words must be different.");
    			}
    			else {
    				build_ladder(word1, word2, words);
    				looptime =0;
    				continue;
    			}
    			looptime++;
    		}
    		System.out.println("Have a nice day.");    //program ends
    	}
    
    
    static File get_file() {
    		String filename = new String();
    		System.out.println("Dictionary file name? ");
    		Scanner sc = new Scanner(System.in);   
    		filename = sc.nextLine();
    		
    		File file=new File(filename); 
    
    		if(!file.exists())    
    		{   
    			return get_file();
    		}
    		else
    		{
    			return file;
    
    		}	
    }
    static HashSet<String> read_words() {       //read the words in the dictionary
    
    		HashSet<String> words_set = new HashSet<String>();
    		
    		File file=get_file(); 
    		
   		Scanner scan = null;
		try {
			scan = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//从文件接收数据  

    		String word = "";
	
    		while (scan.hasNext() ) { 
    				String str = scan.next();
    				words_set.add(str);
    		}  
    		return words_set;
    }		

    static void build_ladder(String word1, String word2, HashSet<String> words) {  
    		HashSet<String>used_words = new HashSet<String>();     //store those already used words
	
    		ConcurrentLinkedQueue<Stack<String>>ladders = new ConcurrentLinkedQueue<Stack<String>>();
    		Stack<String>word_ladder = new Stack<String>();
    		Stack<String>copy_wdladder = new Stack<String>();
    		String this_word = "", next_word = "";

    		word_ladder.push(word1);     //put the word into the ladder
    		used_words.add(word1);
    		ladders.add(word_ladder);
    		while (!ladders.isEmpty()) {
    			word_ladder = ladders.poll();    //get the first ladder in the queue and continue building
    			this_word = word_ladder.peek();
    			for (int i = 0; i < this_word.length(); i++) {
    				next_word = this_word;               
    				for (int j = 0; j < letter_num; j++) {
    					StringBuffer strbuffer=new StringBuffer(next_word);
    					strbuffer.setCharAt(i,(char)(next_word.charAt(i)+1)); //the letter in this positon is changed to the next letter (for example a turns to b) 
    					if (next_word.charAt(i) > 'z') {       
    						strbuffer.setCharAt(i,(char)(next_word.charAt(i)-26)) ;    //control the character between a--z
    					}	
    					next_word = strbuffer.toString();
    					if (words.contains(next_word)) {       //check if the word is in the dictionary
    						if (!used_words.contains(next_word)) {   //to know if that word is used or not
    							word_ladder.push(next_word);
    							used_words.add(next_word);
    							if (next_word.equals(word2)) {               //find word2 and the loop ends
    								System.out.println("A ladder from " + word2 + " back to " + word1 + ':' );
    								while (!word_ladder.empty()) {
    									System.out.print( word_ladder.pop() + ' ');    //output of wordladder
    								}
    								return;
    							}	
    							else {
    								copy_wdladder = (Stack<String>) word_ladder.clone();      //if next word is not word2,then put the ladder into the queue
    								ladders.add(copy_wdladder);
    								word_ladder.pop();      //the first ladder out 
    							}
    						}
    					}
    				}
    			}
    		}
    		System.out.println("No word ladder found from " + word2 + " back to " + word1+'.');

    }	
}



