package Wordladder.word;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Stack;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
	
	static boolean get_file() {
		String filename = new String();
		  
		filename = "sdf12 12ef";
		
		File file=new File(filename); 

		if(!file.exists())    
		{   
			return false;
		}
		else
		{
			return true;
		}	
}
	
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
    		
        assertTrue( true );
    }
    
    /* 
     * test getfile()
     */
    public void testcase1()
    {
    		AppTest testinstance = new AppTest("123");
    		 assertEquals(false, testinstance.get_file());      
    }
    /*
     * test build_ladder
     */
    public void testEqualLength()
    {
    		HashSet<String> words_set = new HashSet<String>();
		
		File file=new File("src/main/java/Wordladder/word/EnglishWords.txt"); 

		Scanner scan = null;
		try {
			scan = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//从文件接收数据  

		while (scan.hasNext() ) {
				words_set.add(scan.next());
		}  
		
    		App testinstance = new App();
    		Stack<String> teststack = testinstance.build_ladder(new String("heaven"), new String("strong"), words_set);
    		int length = teststack.peek().length();
    		
    		while (!teststack.empty()) {
			String tmp = teststack.pop();    
			if(tmp.length() != length)
			{
				fail();
			}
		}
    		assertTrue(true);
    }
    
}
