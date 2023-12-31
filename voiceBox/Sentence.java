package voiceBox;


/**
 * Speaks a list of words/phrases - a sentence (plays a list of .wav audio files)
 *
 * @author J.Grace
 * @version 0.1
 */

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ListIterator;





//LineListener is the interface for receiving events from the audio mixer line (i.e. when a clip is finished playing)
public class Sentence implements LineListener 
{
    // instance variables
	
	String baseFilePath = "voiceBox/";		//path to folder containing .wavs
    
    ArrayList<String> wordsList;    //the list of words to be spoken in this sentence
    
    String wavFilePath = "";             //the path to the .wav word file to be played/spoken
    
    ListIterator<String> listIterator;      //iterator object for moving through wordsList
    
    AudioInputStream audioStream;   //reads .wav data from disc
    
    Clip clip;                      //an audio clip to act as an input to the audio mixer. (Holds a .wav recording. Read in from audioStream)
    
    

    
    /**
     * Constructor for Sentence
     * 
     * @param words list of .wav file paths containing words/phrases to bespoken
     */
    public Sentence(ArrayList<String> words)
    {
        // set wordsList to point to words ArrayList<String> passed into constructor...
        wordsList = words;
        
        /*
        addWord("1.wav");
        addWord("2.wav");
        addWord("3.wav");
        addWord("4.wav");
        addWord("5.wav");
        addWord("6.wav");
        addWord("7.wav");
        addWord("8.wav");
        addWord("9.wav");
        addWord("10.wav");
        */
        
        listIterator = wordsList.listIterator(0); //initialise list iterator at beginning of wordsList
        
        //start speaking wordsList...
        sayNextWord(); 
        
        

    }

    
    
    /**
     * Adds a word to the word list, (later to be spoken)
     *
     * @param word (String) file path to a .wav file containing a word to be spoken
     * @return      void
     */
    public void addWord(String word)
    {
       
        //add word to wordsList...
        wordsList.add(word);
        
    }
    
    
    
    
    /***
     * 
     * plays the next word to be spoken...
     * 
     */
    private void sayNextWord() {
        
            //NOTE: IF NEXT WORD DOES NOT EXIST, THEN EVERY WORD IN THE SENTENCE HAS BEEN SAID. NEED TO PASS BACK A MESSAGE THAT THE NEXT 
            //      ORDER NUMBER CAN NOW BE SENT TO BE SPOKEN. COULD BE A FLAG SET BACK IN OTHER CLASS,E.G. FINISHED=TRUE THAT HAS BUSY WAITING 
            //      (WHILE LOOP) THAT ENDLESSLY WAITS FOR FLAG TO CHANGE, THEN CREATES NEXT SENTENCE OBJECT
            //      
            //      AT PRESENT, WE NOW HAVE THE ABILITY TO SPEAK A SINGLE ORDER NUMBER. THIS COULD INCLUDE THOUSANDS, HUNDREDS, TENS, UNITS
            //      WHAT WE ARE NOW FACING IS CONTROLLING THE SPEAKING OF ONE ORDER AFTER ANOTHER, AFTER ANOTHER, ETC.
            
            //      GET THE THOUSANDS WORKING FIRST
            //      GET THE HUNDREDS WORKING FIRST
            //      THEN GO FOR INTER ORDER NUMBER CONTROL. I THINK BUSY WAITING ON A LIST TO BE DONE, COULD BE THE ANSWER.
            //      MIGHT NEED OWN THREAD SO THAT BASKET(S) CAN KEEP SENDING ORDER READY MESSAGES TO SPEAK BUFFER?
        
            //next word exists?
            if(listIterator.hasNext()) { //exists...
                
                System.out.println("sayNextWord() - next word exists...");
                
                //read next word file path...
                wavFilePath = listIterator.next();
                
                
                
                //System.out.println("wavFilePath: " + wavFilePath);
                System.out.println("wavFilePath: " + baseFilePath + wavFilePath);
                
                //play next word
                
                 try
                {
                    try
                    {
                        //the input stream for the audiofile stored on disc
                    //audioStream = AudioSystem.getAudioInputStream(new File(wavFilePath).getAbsoluteFile());
                    audioStream = AudioSystem.getAudioInputStream(new File(baseFilePath + wavFilePath).getAbsoluteFile());
                    }
                    catch (IOException ioe)
                    {
                        ioe.printStackTrace();
                    }
                }
                catch (UnsupportedAudioFileException uafe)
                {
                    uafe.printStackTrace();
                }
                
                
        
                //get a reference to the audio system clip object (does this create one? Or use one that exists?)
                try
                {
                    clip = AudioSystem.getClip();
                }
                catch (LineUnavailableException lue)
                {
                    lue.printStackTrace();
                }
                
                
                
                //get the clip object to open the file audioStream
                 try
                {
                    try
                    {
                        clip.open(audioStream);
                    }
                    catch (IOException ioe)
                    {
                    ioe.printStackTrace();
                    }
                }
                catch (LineUnavailableException lue)
                {
                    lue.printStackTrace();
                }
                        
                
                //set audio line event listener for catching Stop event, to this class. Triggered when .wav clip has finished playing...
                clip.addLineListener(this);

                //play clip/wav file
                clip.start();
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
            }//end if 
            
                
                
                
        
        
        
        
    }//end sayNextWord()
    
    
    
    
    //Start speaking the words in wordsList...
    public void speak() {
        
        
        sayNextWord();
        
   
    }
    
    
    /**
     * prints the words list to console
     * 
     */
    public void print() {
        
        System.out.println("========================");
        System.out.println("    S E N T E N C E");
        System.out.println("========================");
        
        for(int i=0; i<wordsList.size(); i++) {
            
            System.out.println(i + "| " + wordsList.get(i));
            
            
        }
        
        System.out.println(" ");
        
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    /***
     * Registered to receive audio mixer line events
     */
    
    public void update(LineEvent lineEvent) {
        
        System.out.println("*********************");
        System.out.println(" LINE EVENT RECEIVED ");
        System.out.println("*********************");
        System.out.println(lineEvent);
        
        System.out.println(lineEvent.getType());
        
        //Previous word.wav finished playing?...
        if(lineEvent.getType().toString() == "Stop") { //word finished...
            
            //say next word in the wordsList...
            sayNextWord();
            
            
        }
        
    }//end update(LineEvent)
    
    
    
    
    
}//end class Sentence_02

