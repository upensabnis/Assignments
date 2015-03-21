/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package marketomusic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * @author Upendra
 */
public class MarketoMusic {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String command = "";
        String commands[];
        ArrayList<String> MainMenuCommands = new ArrayList<String>();
        MainMenuCommands.add("create");
        MainMenuCommands.add("edit");
        MainMenuCommands.add("song");
        MainMenuCommands.add("playlist");
        MainMenuCommands.add("print");
        MainMenuCommands.add("search");
        MainMenuCommands.add("sort");
        MainMenuCommands.add("quit");
        
        ArrayList<String> PlayListMenuCommands = new ArrayList<String>();
        PlayListMenuCommands.add("insert");
        PlayListMenuCommands.add("delete");
        PlayListMenuCommands.add("print");
        PlayListMenuCommands.add("main");
        
        while(!command.equals("quit") && !command.equals("Quit")) {
            System.out.println("Enter your command : \n");
            try {
                command = in.readLine();
                commands = command.split(" ");

                if(commands.length > 1){
                    if(MainMenuCommands.contains(commands[0])){
                        switch(commands[0]){
                            case "create": System.out.println("In create");
                                        break;

                            case "edit": System.out.println("In edit");
                                        break;

                            case "song": System.out.println("In song");
                                        break;

                            case "playlist": System.out.println("In playlist");
                                        break;

                            case "print": System.out.println("In print");
                                        break;

                            case "search": System.out.println("In search");
                                        break;

                            case "sort": System.out.println("In sort");
                                        break;

                            case "quit": System.out.println("In quit");
                                        break;    

                            default :    System.out.println("In default");
                        } 
                    } else {
                        System.out.println("You are trying to perform wrong operation !!");
                    }
                } else {
                    System.out.println("{Usage} - command_name parameter");
                }
            } catch(IOException ex){
                ex.printStackTrace();
            }
        }
    }
}
