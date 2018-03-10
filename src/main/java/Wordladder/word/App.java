package Wordladder.word;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.*;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Hello world!
 *
 */
public class App 
{
	static int letter_num = 26;     //the number of letters
	
    public static void main( String[] args )
    {
    	HashSet<String>words = read_words();	//read words in the dictionary
    	
    	String word1 = "", word2 = "";
    	int looptime = 0;
    	char ch = ' ';        //used for checking '\n' ,if user press "enter",then the program ends
    	while (true) {
    		if (looptime > 0) {
    			System.out.println();   //add a empty line after one inquiry
    		}
    		System.out.println("Word #1 (or Enter to quit): ");
    		Scanner scan = new Scanner(System.in);
    		
    		if(!scan.hasNext())
    		{
    			break;
    		}
    		word1 = scan.next();
    		scan.close();
    		System.out.println("Word #2 (or Enter to quit): ");
    		scan = new Scanner(System.in);
    		
    		if(!scan.hasNext())
    		{
    			break;
    		}
    		
    		word2 = scan.next();
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
    		}
    		looptime++;
    	}
    	System.out.println("Have a nice day.");    //program ends
    }
    
    
    static File get_file() {
    		String filename = "";
    		System.out.println("Dictionary file name? ");
    		Scanner sc = new Scanner(System.in);   
    		filename = sc.nextLine();
    		
    		File file=new File(filename); 
    
    		sc.close();
    
    		if(!file.exists())    
    		{    
    			return get_file();
    		}
    		else
    			return file;
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
	
    		while (scan.hasNext()) {  
        words_set.add(scan.next());
    		}  
	
    		scan.close();
    		return words_set;
    }		

    static void build_ladder(String word1, String word2, HashSet<String> words) {  
    		HashSet<String>used_words = new HashSet<String>();     //store those already used words
	
    		ConcurrentLinkedQueue<Stack<String>>ladders = new ConcurrentLinkedQueue<Stack<String>>();
    		Stack<String>word_ladder = new Stack<String>();
    		Stack<String>copy_wdladder = new Stack<String>();
    		String this_word = "", next_word = "";
    		char change_letter = ' ';
    		word_ladder.push(word1);     //put the word into the ladder
    		used_words.add(word1);
    		ladders.add(word_ladder);
    		while (!ladders.isEmpty()) {
    			word_ladder = ladders.poll();    //get the first ladder in the queue and continue building
    			this_word = word_ladder.peek();
    			for (int i = 0; i < this_word.length(); i++) {
    				next_word = this_word;               
    				for (int j = 0; j < letter_num; j++) {
    					next_word.charAt(i);
    					StringBuffer strbuffer=new StringBuffer(next_word);
    					strbuffer.setCharAt(i,(char)(next_word.charAt(i)+1)); //the letter in this positon is changed to the next letter (for example a turns to b) 
    					if (next_word.charAt(i) >= 'z') {       
    						strbuffer.setCharAt(i,(char)(next_word.charAt(i)-26)) ;    //control the character between a--z
    					}	
				
    					if (words.contains(next_word)) {       //check if the word is in the dictionary
    						if (!used_words.contains(next_word)) {   //to know if that word is used or not
    							word_ladder.push(next_word);
    							used_words.add(next_word);
    							if (next_word == word2) {               //find word2 and the loop ends
    								System.out.println("A ladder from " + word2 + " back to " + word1 + ':' );
    								while (!word_ladder.empty()) {
    									System.out.print( word_ladder.pop() + ' ');    //output of wordladder
    								}
    								return;
    							}	
    							else {
    								copy_wdladder = word_ladder;      //if next word is not word2,then put the ladder into the queue
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



