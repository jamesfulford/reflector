/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commons;

import listsandsorts.StringList;

/**
 *
 * @author James
 */
public class CommandLine {
    
    
            public void choice(){
            StringList options = new StringList();
            options.push("READ"); options.push("INVESTIGATE PYLON"); options.push("HELP"); options.push("EXIT");
            
            System.out.println("Please input a new command:");
            StringList command = AskFor.aCommand(options);
            System.out.println();
            
            boolean cont = true;
            switch((String)command.lookUp(0)){
                case "CASE1":
                    if(command.end <= 1) { //if there is only one word
                        System.out.println("Please specify" /*SOMETHING*/);
                        /*SOMETHING*/
                        command.push(AskFor.aString());
                    }
                    /*SOMETHING*/
                    break;
                    
                /*ADD MORE CASES*/
                    
                    
                case "EXIT":
                    System.out.println("Exiting program");
                    cont = false;
                    break;
                case "HELP":
                    if(command.end >= 1){
                        switch((String)command.lookUp(1)){
                            case "CASE1":
                                /*FOR EXAMPLE:*/
                                System.out.println("READ is used to read poems stored in memory.");
                                System.out.println("Enter the name of the file after READ to read the file.");
                                System.out.println("Example: $READ Green Eggs and Ham");
                                System.out.println("(NOTE: the 3rd word on are ignored.)");
                                System.out.println("(NOTE: a file is read if the name contains the entered string.");
                                break;
                                
                                /*MORE CASES ==> MORE COMMANDS ==> MORE FUNCTIONALITY*/
                                
                            case "EXIT":
                                System.out.println("EXIT allows a user to exit this command loop and program.");
                                System.out.println("Example: $EXIT");
                                break;
                            case "HELP":
                                System.out.println("HELP allows a user to learn commands. Try it!");
                                System.out.println("Example: $HELP EXIT \nwill detail what \"EXIT\" command does.");
                                break;
                            default:
                                System.out.println("I'm sorry, we can't help with that.");
                                break;
                        }
                    } else {
                        System.out.println("Below are your options for commands");
                        options.print();
                    }
                default:
                    System.out.println("\"" + (String)command.lookUp(0) + "\" is not an acceptable command.");
                    break;
                    
                    
            }
            if(cont){
                choice();
            }
            }
    
}
