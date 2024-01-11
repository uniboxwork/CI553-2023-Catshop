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






public class Sentence implements LineListener 		//LineListener is the interface for receiving events from the audio mixer line (i.e. when a clip is finished playing)
{
    // instance variables
	
	private ArrayList<String> wordsList;    		//the list of words (.wav files) to be spoken in this sentence
	
	private String baseFilePath = "voiceBox/";		//base path to the folder containing .wavs
	private String wavFilePath;             		//path to the .wav word file to be played/spoken
    private ListIterator<String> listIterator;      //iterator object for moving through wordsList
    
    AudioInputStream audioStream;   				//reads .wav data from disc
    
    Clip clip;                      				//an audio clip to act as an input to the audio mixer. (Holds a .wav recording. Read in from audioStream)
    
    

    
    /**
     * Constructor for Sentence
     * 
     * @param words list of .wav file paths containing words/phrases to be spoken
     */
    public Sentence(ArrayList<String> words)
    {
        
    	
        wordsList = words;							// set wordsList to point to words ArrayList<String> passed into constructor...
        
        wavFilePath = "";             				//initialise path to word .wav file to be played/spoken
        
        listIterator = wordsList.listIterator(0); 	//initialise list iterator at beginning of wordsList
        
        sayNextWord(); 								//start speaking wordsList...
        
        

    }

    
    
   
    
    /***
     * 
     * plays the next word to be spoken...
     * 
     */
    private void sayNextWord() {
        
           
            if(listIterator.hasNext()) {  //next word exists...
                
                  
                wavFilePath = listIterator.next();  	//read next word file path...
                
                
                //play next word
                
                 try
                {
                    try
                    {
                    //the input stream for the audiofile stored on disc
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
                
                
        
                //get a reference to the audio system clip object
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
                        
                
                
                clip.addLineListener(this);     //set audio line event listener for catching Stop event, to this class. Triggered when .wav clip has finished playing...

                
                clip.start();					//play clip/wav file
                
                
                
                
                
                           
                
                
            }//end if 
            
                
                
                
        
        
        
        
    }//end sayNextWord()
    
    
    
    
    
    
    
    
    /***
     * Registered to receive audio mixer line events
     */
    public void update(LineEvent lineEvent) {
        //debug
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
    
    
    
    
    
    
    
    
    
    
    /**
     * prints the words list to console
     * 
     */
    public void print() {
        //debug
        System.out.println("========================");
        System.out.println("    S E N T E N C E");
        System.out.println("========================");
        
        for(int i=0; i<wordsList.size(); i++) {
            
            System.out.println(i + "| " + wordsList.get(i));
            
            
        }
        
        System.out.println(" ");
        
        
    }
    
    
    
    
    
    
    
    
    
    
    
   
    
    
    
    
    
}//end class Sentence

