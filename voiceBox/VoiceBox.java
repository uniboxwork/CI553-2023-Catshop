package voiceBox;


/**
 * VoiceBox - Audibly speaks announcement of order ready for collection.
 *
 * @author J.Grace
 * @version v0.1
 */

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;




public class VoiceBox
{

    
    ArrayList<String> words;   //container for words (.wav file paths) to be spoken
    
    /**
     * Constructor for VoiceBox
     */
    public VoiceBox() 
    {
        
        /*
        
        //live testing using scanner...
        
        boolean finished = false;
        Scanner myInput = new Scanner(System.in);
        int number;
        
        
        
        while(!finished) {
            
            System.out.println("==================");
            System.out.println("VOICEBOX LIVE TEST");
            System.out.println("==================");
            System.out.println("Enter a number between 0 - 999,999:   (-1 to quit) ");
    
             //read input
            number = myInput.nextInt();
            
            if(number == -1) { //quit
                break;
            }else {
                
                speakOrder(number);  //speak number
                
            }
            
        }    
        
        //TESTING speakNumber - values 0-99
        /*
        for(int i=0; i<5; i++) {
            
            speakNumber(i);
            
            
        }//end for
        
       */

          //speakOrder(909199);
          
          
      
      
        
    }//end constructor()
    
    



public void speakOrder(int number) {

    //initialise blank words list...
    words = new ArrayList<String>();
    
    String alertSound = "bell.wav";                           //path to recorded alert sound
    String openingPhrase = "orderNumber.wav";           //path to recorded oppening phrase of announcement (intro)
    String closingPhrase = "readyForCollection.wav";    //path to recorded closing phrase of announcement   (outro)
    
    int remaining = number; //running total/remainder after each unit of magnitude removed. Initialised to number argument.
    
    int hundredThousands;   //the number of hundred thousands present in the number
    int thousands;          //the number of thousands present in the number
    int hundreds;           //the number of hundreds present in the number
    int tens_and_units;     //the number of tens and units (0-99)
    
    
    words.add(alertSound);         //add alert sound sound to words
    words.add(openingPhrase);      //add openning announcement phrase
    
    
    hundredThousands = remaining / 100000;  //calculate number of 100,000s present
    
    if(hundredThousands != 0) {//hundred thousands exist... 1-9
        
        numberToWords(hundredThousands);    //convert the number of hundreds to words
        words.add("hundred.wav");           //add the unit word "hundred" to words
        remaining -= (hundredThousands * 100000);  //subtract 100,000s from remaining total
        
    }
    
    
    thousands = remaining / 1000;   //calculate number of 1000s present

    if(thousands != 0) { //thousands exist...
        
        if(hundredThousands !=0) {  //100,000s exist... join with 1,000s
            words.add("and.wav");   //add joining word "and" e.g 157,000: one hundred AND fifty seven thousand
        }
        numberToWords(thousands);   //convert number of thousands to words
        words.add("thousand.wav");  //add the unit word "thousands"
        remaining -= (thousands * 1000);    //subtract 1000s from remaining total
        
    }else {//thousands does NOT exist...
            if(hundredThousands !=0) { //but hundredThousands does exist...
                words.add("thousand.wav");  //cap off with "thousand"
            }
        
        }
    
    hundreds = remaining / 100;  //calculate number of hundreds present
    
    if(hundreds != 0) {//hundreds exist...
        
        numberToWords(hundreds);        //convert the number of hundreds to words
        words.add("hundred.wav");       //add the unit word "hundred" to words
        //words.add("and.wav");           //add the conjunctive word "and" to words
        remaining -= (hundreds * 100);  //subtract 100s from remaining total
        
    }
    
    
    
   
    //(only tens and units 0-99 should be remaining...)
    //if the remaining is 0, we do not want to speak '0' e.g. 100 "One hundred and zero". Rather we want 0 to remain silent... e.g. 100 "One hundred"
    //if there are no: 100,000s, 1000s, 100s and the remainder is zero, then the order number is 0 and the system must speak "Zero"
    
    if(remaining != 0) { //remainder in range 1-99 exist. 
        
        if(hundredThousands !=0 || thousands !=0 || hundreds !=0) { //hundredThousands, thousands, or hundreds exist. Append "and" before remainder...
            words.add("and.wav");           //add conjunctive word "and" to words
        }
        numberToWords(remaining);           //convert and add remaining number (1-99) to words
        
    } else {    //remaining is 0
        
            //is the order number 0?
            
            //if hundred thousands AND thousands AND hundreds is 0, THEN SPEAK "Zero".
            if((hundredThousands == 0) && (thousands == 0) && (hundreds == 0)) { //number is 0. Speak "Zero"...
                numberToWords(remaining);
            } else {
                    
                    //Order number is NOT 0. Do not speak remaining 0. 

                    //stay silent
                    
                }
            
            
    
        }
    
 
    
    words.add(closingPhrase);       //add closing phrase to announcement sentence (outro)
    
    //speak sentence of words...
    Sentence sentence = new Sentence(words);
        
    //sentence.print();
    
    
    
    

}//end speakOrder




//converts an integer between 0-99 into file paths to .wav spoken words. 
//Adds .wav file paths to words list
//can "speak" numbers 0-99
public void numberToWords(int number) {
    
    System.out.println("");
    System.out.println("==============================");
    System.out.println("numberToWavs(" + number + ")");
        
    int remaining = number;
    int tens;
    int units;

    
    
    //===========
    //number is 0
    //===========
    if(number == 0) {
        //0
        //play "ZERO"
        System.out.println("=ZERO=");
        words.add("0.wav");
        
    } else {
        
            //System.out.println("Number not 0");
 
        
                tens = (remaining / 10);
                
                System.out.println("tens: " + tens);
            
                //==============
                //tens exist?...
                //==============
                if(tens > 0) {
                
        
                //==============
                //DECODE 10s...        
                //==============
        
                //=====================================
                //TWEENS 10-19? special case detected...
                //=====================================
                if(tens == 1) {
        
                    switch(remaining) {
        
                        case 19:    //19
                            //play "NINETEEN"
                            System.out.println("=NINETEEN=");
                            words.add("19.wav");
        
                             break;
        
                        case 18:    //18
                            //play "EIGHTEEN"
                            System.out.println("=EIGHTEEN=");
                            words.add("18.wav");        
                             break;
        
                        case 17:    //17
                            //play "SEVENTEEN"
                            System.out.println("=SEVENTEEN=");
                            words.add("17.wav");
                             break;
        
                        case 16:    //16
                            //play "SIXTEEN"
                            System.out.println("=SIXTEEN=");
                            words.add("16.wav");
                             break;
        
                        case 15:    //15
                            //play "FIFTEEN"
                            System.out.println("=FIFTEEN=");
                            words.add("15.wav");
                             break;
        
                        case 14:    //14
                            //play "FOURTEEN"
                            System.out.println("=FOURTEEN=");
                            words.add("14.wav");
                             break;
                        case 13:    //13
                            //play "THIRTEEN"
                            System.out.println("=THIRTEEN=");
                            words.add("13.wav");
                             break;
        
                        case 12:    //12
                            //play "TWELVE"
                            System.out.println("=TWELVE=");
                            words.add("12.wav");
                             break;
                        case 11:    //11
                            //play "ELEVEN"
                            System.out.println("=ELEVEN=");
                            words.add("11.wav");
                             break;
        
                        case 10:    //10
                            //play "TEN"
                            System.out.println("=TEN=");
                            words.add("10.wav");
                             break;
                        default:     //Error
                            System.out.println("*Error* speakNumber() - tweens DECODE not recognised");
                            break;
        
        
                    }//end switch()
        
        
                    //=====================
                    //update remaining
                    //===================== 
                    remaining -= remaining;     //note: should go to zero if all went well - teens/tweens leave nothing left to be said
        
                    
                }else {    //TENS > 1 and NOT 0 (should be between 2 to 9   i.e. 20 to 90)
        
                        switch(tens) {
        
                            case 9:    //90
                                //play "NINETY"
                                System.out.println("=NINETY=");
                                words.add("90.wav");
                                 break;
        
                            case 8:    //80
                                //play "EIGHTY"
                                System.out.println("=EIGHTY=");
                                words.add("80.wav");
                                 break;
        
                            case 7:    //70
                                //play "SEVENTY"
                                System.out.println("=SEVENTY=");
                                words.add("70.wav");
                                 break;
        
                            case 6:    //60
                                //play "SIXTY"
                                System.out.println("=SIXTY=");
                                words.add("60.wav");
                                 break;
        
                            case 5:    //50
                                //play "FIFTY"
                                System.out.println("=FIFTY=");
                                words.add("50.wav");
                                 break;
        
        
                            case 4:    //40
                                //play "FOURTY"
                                System.out.println("=FOURTY=");
                                words.add("40.wav");
                                 break;
        
                            case 3:    //30
                                //play "THIRTY"
                                System.out.println("=THIRTY=");
                                words.add("30.wav");
                                 break;
        
                            case 2:    //20
                                //play "TWENTY"
                                System.out.println("=TWENTY=");
                                words.add("20.wav");
                                 break;
        
                            default://Error
                                System.out.println("=*ERROR* speakNumber() - TENS DECODE NOT RECOGNISED=");
                                 break;
        
                        
        
                        }//end switch()
        
                        //=========================
                        // UPDATE remaining
                        //=========================
                        //subtract tens value from remaining...
                        remaining -= (tens * 10);
        
        
                    }//end else
        
        
        
            }//end if
        
        
        
        
            //==============================
            //units (1's)
            //==============================
            units = remaining;
            
            System.out.println("units: " + units);
        
            //units remain?  should be in range 1 - 9
            if(units != 0) {
        
                    switch(units) {
                        case 9:        //9
                            //play "NINE"
                            System.out.println("=NINE=");
                            words.add("9.wav");
                            break;
        
                        case 8:        //8
                            //play "EIGHT"
                            System.out.println("=EIGHT=");
                            words.add("8.wav");
                            
                            break;
        
                        case 7:        //7
                            //play "SEVEN"
                            System.out.println("=SEVEN=");
                            words.add("7.wav");
                            
                            break;
        
                        case 6:        //6
                            //play "SIX"
                            System.out.println("=SIX=");
                            words.add("6.wav");
                            
                            break;
        
                        case 5:        //5
                            //play "FIVE"
                            System.out.println("=FIVE=");
                            words.add("5.wav");
                            
                            break;
        
                        case 4:        //4
                            //play "FOUR"
                            System.out.println("=FOUR=");
                            words.add("4.wav");
                            
                            break;
        
                        case 3:        //3
                            //play "THREE"
                            System.out.println("=THREE=");
                            words.add("3.wav");
                            
                            break;
        
                        case 2:        //2
                            //play "TWO"
                            System.out.println("=TWO=");
                            words.add("2.wav");
                            break;
        
                        case 1:        //1
                            //play "ONE"
                            System.out.println("=ONE=");
                            words.add("1.wav");
                            break;
        
                        default: 
                                //Error
                            System.out.println("*Error* - speakNumber() - units NOT recognised"); 
        
        
                    
                    }//end switch()
        
        
            }//end if
        
        
        

        }//end if 

        
        
        //sentence.speak();
       
        
        System.out.println("==============================");
        
        


}//end numberToWords()

    
    
    
    
    
    
    
    
    
    
    
    
    
}
