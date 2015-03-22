/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package marketomusic;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import marketomusic.beans.SongFile;

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
        String command = "", sCurrentLine = "";
        String commands[], sCurrentLines[];
        ArrayList<String> main_menu_commands = new ArrayList<String>();
        main_menu_commands.add("create");
        main_menu_commands.add("edit");
        main_menu_commands.add("song");
        main_menu_commands.add("playlist");
        main_menu_commands.add("print");
        main_menu_commands.add("search");
        main_menu_commands.add("sort");
        main_menu_commands.add("quit");

        ArrayList<String> playlists = new ArrayList<String>();  // List for maintaing the names of the list
        HashMap<String, ArrayList<SongFile>> playlist_songs = new HashMap<String, ArrayList<SongFile>>();

        String pathToTextFile = "C:\\Upendra\\Personal\\Internshps\\Marketo\\Intern Coding Test\\SongList.txt";
        BufferedReader br = null;
        SongFile songFile = null;
        int songId = 0;
        ArrayList<SongFile> central_store = new ArrayList<SongFile>();

        try {
            br = new BufferedReader(new FileReader(pathToTextFile));

            while ((sCurrentLine = br.readLine()) != null) {
                sCurrentLines = sCurrentLine.split("\t");
                songFile = new SongFile(songId, sCurrentLines[0], sCurrentLines[1]);
                central_store.add(songFile);
                songId++;
                //System.out.println("Separated word : 1-"+sCurrentLines[0]+" and 2-"+sCurrentLines[1]);
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        while (!command.equals("quit") && !command.equals("Quit")) {
            System.out.println("1.Create : create <playlist name>");
            System.out.println("2.Edit : edit <playlistId>");
            System.out.println("3.Print song : song <songId>");
            System.out.println("4.Print playlist : playlist <playlistId>");
            System.out.println("5.Print All Songs or Playlists : print <print option>");
            System.out.println("6.Search : search <search option> <string of words>");
            System.out.println("7.Sort : sort <sort option>");
            System.out.println("8.Quit : quit");

            System.out.print("\nEnter your command >>> ");
            try {
                command = in.readLine();
                commands = command.split(" ");

                if (commands.length > 1 && commands.length < 3) {
                    if (main_menu_commands.contains(commands[0])) {
                        switch (commands[0]) {
                            case "create":
                                if (playlists.contains(commands[1])) {
                                    System.out.println("Playlist with this name already exists !!");
                                } else {
                                    playlists.add(commands[1]);
                                    ArrayList<SongFile> songs = new ArrayList<SongFile>();
                                    playlist_songs.put(commands[1], songs);
                                    showPlayListMenu(commands[1], playlist_songs, central_store);
                                }
                                break;

                            case "edit":
                                System.out.println("In edit");
                                break;

                            case "song":
                                System.out.println("In song");
                                break;

                            case "playlist":
                                System.out.println("In playlist");
                                break;

                            case "print":
                                System.out.println("Printing names of playlists : ");
                                Iterator<String> itr = playlists.iterator();
                                while (itr.hasNext()) {
                                    System.out.println(itr.next());
                                }
                                break;

                            case "search":
                                System.out.println("In search");
                                break;

                            case "sort":
                                System.out.println("In sort");
                                break;

                            case "quit":
                                System.out.println("In quit");
                                break;

                            default:
                                System.out.println("In default");
                        }
                    } else {
                        System.out.println("You are trying to perform wrong operation !!");
                    }
                } else {
                    System.out.println("{Usage} - command_name parameter");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static void showPlayListMenu(String current_playlist_name, HashMap playlist_songs, ArrayList<SongFile> central_store) {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String commands[];
        String command = "";
        //String playlist_menu_commands[] = {"insert","delete","print","main"};

        ArrayList<String> playlist_menu_commands = new ArrayList<String>();
        playlist_menu_commands.add("insert");
        playlist_menu_commands.add("delete");
        playlist_menu_commands.add("print");
        playlist_menu_commands.add("main");

        while (!command.equals("main")) {
            System.out.println("\nMenu for operations on playlist :");
            System.out.println("1.Delete : delete <songId>");
            System.out.println("2.Insert : insert <songId>");
            System.out.println("3.Print : print");
            System.out.println("4.Main Menu : main");

            System.out.print("\nEnter Operation >>> ");
            try {
                command = in.readLine();
                commands = command.split(" ");

                if (commands.length >= 1 && commands.length < 3) {
                    if (playlist_menu_commands.contains(commands[0])) {
                        switch (commands[0]) {
                            case "delete":
                                if (playlist_songs.containsKey(current_playlist_name)) {
                                    ArrayList<SongFile> songs = (ArrayList<SongFile>) playlist_songs.get(current_playlist_name);
                                    try {
                                        int id = Integer.parseInt(commands[1]);
                                        int size = central_store.size();
                                        SongFile songFile = null;
                                        if (id > size || id < 0) {
                                            System.out.println("There is no song with this id center store !!");
                                        } else {
                                            songFile = central_store.get(id);

                                            if (songFile != null) {
                                                if (songs.contains(songFile)) {
                                                    System.out.println("Song exists !! Deleting it ....");
                                                    songs.remove(songFile);
                                                } else {
                                                    System.out.println("The song with this songId does not exist in this playlist !!");
                                                }
                                            }
                                        }
                                    } catch (NumberFormatException ex) {
                                        System.out.println("Wrong songId format !!");
                                    }

                                } else {
                                    System.out.println("This playlist does not exist !!");
                                }
                                break;

                            case "insert":
                                if (playlist_songs.containsKey(current_playlist_name)) {
                                    ArrayList<SongFile> songs = (ArrayList<SongFile>) playlist_songs.get(current_playlist_name);
                                    try {
                                        int id = Integer.parseInt(commands[1]);
                                        int size = central_store.size();
                                        SongFile songFile = null;

                                        if (id > size || id < 0) {
                                            System.out.println("There is no song with this id center store !!");
                                        } else {
                                            songFile = central_store.get(id);
                                            if (songFile != null) {
                                                if (songs.contains(songFile)) {
                                                    System.out.println("Song already exists in this playlist");
                                                } else {
                                                    System.out.println("Adding the song in the playlist ...");
                                                    songs.add(songFile);
                                                }
                                            }
                                        }
                                    } catch (NumberFormatException ex) {
                                        System.out.println("Wrong songId format !!");
                                    }

                                } else {
                                    System.out.println("This playlist does not exist !!");
                                }
                                break;

                            case "print":
                                if (playlist_songs.containsKey(current_playlist_name)) {
                                    ArrayList<SongFile> songs = (ArrayList<SongFile>) playlist_songs.get(current_playlist_name);
                                    System.out.println("Songs in " + current_playlist_name + " playlist are : ");
                                    Iterator<SongFile> itr = songs.iterator();
                                    while (itr.hasNext()) {
                                        SongFile sFile = itr.next();
                                        System.out.println("Artist : " + sFile.getArtist() + " Title : " + sFile.getTitle());
                                    }
                                }

                                break;

                            default:
                                System.out.println("This operation not allowed on the playlist !!");
                        }
                    } else {
                        System.out.println("This operation not allowed on the playlist !!");
                    }
                } else {
                    System.out.println("{Usage} - opration_name [<songID>]");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    /*
     * private static String getPropValues(String pathToTextFile) throws
     * IOException { Properties prop = new Properties(); String propFileName =
     * "config.properties";
     *
     * InputStream inputStream =
     * getClass().getClassLoader().getResourceAsStream(propFileName);
     *
     * if (inputStream != null) { try{ prop.load(inputStream); } catch
     * (FileNotFoundException ex){ ex.printStackTrace(); } }
     *
     * String path = prop.getProperty(pathToTextFile);
     *
     * return path; }
     *
     */
}
