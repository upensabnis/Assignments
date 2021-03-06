/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package marketomusic;

import java.io.*;
import java.util.*;
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

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in)); // For reading commands entered by user from console
        String command = "", sCurrentLine = "";
        String commands[], sCurrentLines[];
        BufferedReader br = null;
        SongFile songFile = null;
        int songId = 0;
        boolean flag = false;

        ArrayList<String> playlists = new ArrayList<>();  // List for maintaing the names of the list
        HashMap<String, ArrayList<SongFile>> playlist_songs = new HashMap<>();  //Hashmap for maintaining the mapping between playlist name and its songs
        ArrayList<SongFile> central_store = new ArrayList<>();   //List containing all the contents read from text file

        /**
         * ***************************** Reading the path of text file from
         * properties file ****************************
         */
        ReadConfigs rConfigs = new ReadConfigs("properties/config.properties");
        String pathToTextFile = "";

        try {
            pathToTextFile = rConfigs.getPropValues("pathToTextFile");

            if (!pathToTextFile.equals("")) {
                br = new BufferedReader(new FileReader(pathToTextFile));
                flag = true;
                while ((sCurrentLine = br.readLine()) != null) {
                    sCurrentLines = sCurrentLine.split("\t");
                    songFile = new SongFile(songId, sCurrentLines[0], sCurrentLines[1]);
                    central_store.add(songFile);
                    songId++;
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Text file not found !! Check the path of text file in config file !!");
        } catch (IOException ex) {
            System.out.println("Error occured while reading from text file !!");
        }

        if (flag) {

            /**
             * ***************************** ArraList containing commands
             * allowed in Main Menu ****************************
             */
            ArrayList<String> main_menu_commands = new ArrayList<>();
            main_menu_commands.add("create");
            main_menu_commands.add("edit");
            main_menu_commands.add("song");
            main_menu_commands.add("playlist");
            main_menu_commands.add("print");
            main_menu_commands.add("search");
            main_menu_commands.add("sort");
            main_menu_commands.add("quit");

            /**
             * ***************************** Prompting the user for input
             * ****************************
             */
            while (!command.equals("quit")) { // Check if user has entered quit

                showMainMenu(); // Function for showing main menu

                try {
                    command = in.readLine();
                    commands = command.split(" ");

                    if (main_menu_commands.contains(commands[0])) { // Checking if command entered by user is valid or not

                        switch (commands[0]) {
                            case "create":
                                /**
                                 * ***************************** Create a list
                                 * with multiple words
                                 * ****************************
                                 */
                                if (commands.length < 2) { // No upper bound checking on length of commands as <playlist name> can be multiple words
                                    System.out.println("Wrong number of parameters !! {Usage} - create <playlist name>");
                                } else {
                                    String playlist_name = "";

                                    // Handling of multiple word playlist name
                                    for (int i = 1; i < commands.length; i++) {
                                        if (i == commands.length - 1) {
                                            playlist_name = playlist_name + commands[i];
                                        } else {
                                            playlist_name = playlist_name + commands[i] + " ";
                                        }
                                    }

                                    //Checking if playlist alread contains the playlist with same name otherwise create it
                                    if (playlists.contains(playlist_name)) {
                                        System.out.println("Playlist with this name already exists !!");
                                    } else {
                                        playlists.add(playlist_name);
                                        ArrayList<SongFile> songs = new ArrayList<SongFile>();
                                        playlist_songs.put(playlist_name, songs);
                                        showPlayListMenu(playlist_name, playlist_songs, central_store);
                                    }
                                }
                                break;

                            case "edit":
                                /**
                                 * ***************************** Edit a already
                                 * created list ****************************
                                 */
                                if (commands.length < 2 || commands.length > 2) { // Command length should be exactly 2
                                    System.out.println("Wrong number of parameters !! {Usage} - edit <playlistId>");
                                } else {
                                    try {
                                        int playlistId = Integer.parseInt(commands[1]);
                                        int size = playlists.size();

                                        //Checking if the playlistid is present in the list of playlist
                                        if (playlistId < size && playlistId >= 0) {
                                            String current_playlist_name = playlists.get(playlistId);
                                            showPlayListMenu(current_playlist_name, playlist_songs, central_store);
                                        } else {
                                            System.out.println("Playlist with this id does not exists !!");
                                        }
                                    } catch (NumberFormatException ex) {
                                        System.out.println("The <playlistId> must be an integer !!");
                                    }
                                }
                                break;

                            case "song":
                                /**
                                 * ***************************** Find out
                                 * details about particular song from central
                                 * store ****************************
                                 */
                                if (commands.length < 2 || commands.length > 2) { // Command length should be exactly 2
                                    System.out.println("Wrong number of parameters !! {Usage} - song <songId>");
                                } else {
                                    try {
                                        int entered_songid = Integer.parseInt(commands[1]);
                                        int size = central_store.size();

                                        //Checking if the playlistid is present in the central store and printing it if present
                                        if (entered_songid < size && entered_songid >= 0) {
                                            SongFile sFile = central_store.get(entered_songid);
                                            System.out.println("\nDetails about song : ");

                                            sFile.printSongFile();
                                        } else {
                                            System.out.println("Song with this id does not exists !!");
                                        }
                                    } catch (NumberFormatException ex) {
                                        System.out.println("The <songId> must be an integer !!");
                                    }
                                }
                                break;

                            case "playlist":
                                /**
                                 * ***************************** Get the
                                 * details about already created playlist
                                 * ****************************
                                 */
                                if (commands.length < 2 || commands.length > 2) { // Command length should be exactly 2
                                    System.out.println("Wrong number of parameters !! {Usage} - playlist <playlistId>");
                                } else {
                                    try {
                                        int playlistId = Integer.parseInt(commands[1]);
                                        int size = playlists.size();

                                        //Checking if the playlistid is present in the list of playlist and printing it if present
                                        if (playlistId < size && playlistId >= 0) {
                                            String current_playlist_name = playlists.get(playlistId);
                                            if (playlist_songs.containsKey(current_playlist_name)) {
                                                ArrayList<SongFile> songs = (ArrayList<SongFile>) playlist_songs.get(current_playlist_name);
                                                System.out.println("Songs in '" + current_playlist_name + "' playlist are : ");
                                                Iterator<SongFile> itr = songs.iterator();
                                                while (itr.hasNext()) {
                                                    SongFile sFile = itr.next();
                                                    sFile.printSongFile();
                                                }
                                                System.out.println();
                                            } else {
                                                System.out.println("Playlist with this id does not exists !!");
                                            }
                                        } else {
                                            System.out.println("Playlist with this id does not exists !!");
                                        }
                                    } catch (NumberFormatException ex) {
                                        System.out.println("The <playlistId> must be an integer !!");
                                    }
                                }
                                break;

                            case "print":
                                /**
                                 * ***************************** Print the
                                 * contents of SongFile for either song or
                                 * playlist ****************************
                                 */
                                if (commands.length < 2 || commands.length > 2) { // Command length should be exactly 2
                                    System.out.println("Wrong number of parameters !! {Usage} - print <\"song\"> or <\"playlist\">");
                                } else {
                                    if (commands[1].equals("song")) {
                                        if (central_store != null) {
                                            Iterator<SongFile> itr = central_store.iterator();
                                            while (itr.hasNext()) {
                                                SongFile sFile = itr.next();
                                                sFile.printSongFile();
                                            }
                                        }

                                    } else if (commands[1].equals("playlist")) {
                                        Iterator<String> itr = playlists.iterator();
                                        if (playlists.size() > 0) {
                                            System.out.println("Printing names and contents of each playlist : ");
                                            while (itr.hasNext()) {
                                                String current_playlist_name = itr.next();
                                                ArrayList<SongFile> songs = (ArrayList<SongFile>) playlist_songs.get(current_playlist_name);
                                                System.out.println("Songs in '" + current_playlist_name + "' playlist are : ");
                                                Iterator<SongFile> songitr = songs.iterator();
                                                while (songitr.hasNext()) {
                                                    SongFile sFile = songitr.next();
                                                    sFile.printSongFile();
                                                }
                                            }
                                        } else {
                                            System.out.println("No playlist created !!");
                                        }
                                    } else {
                                        System.out.println("Wrong parameter : Either song or playlist is expected !!");
                                    }
                                }
                                break;

                            case "search":
                                /**
                                 * ***************************** Search for
                                 * artist or title ****************************
                                 */
                                if (commands.length < 3 || commands.length > 3) { // Command length should be exactly 3
                                    System.out.println("Wrong number of parameters !! {Usage} - search <\"artist\"> <\"string of words\"> or <\"title\"> <\"string of words\">");
                                } else {
                                    String[] search_elements = command.split(" ", 3);
                                    Iterator<SongFile> itr = central_store.iterator();

                                    if (search_elements[2].split("\"").length == 2) {
                                        //Iterating over central store to find matching artist or author
                                        while (itr.hasNext()) {
                                            SongFile sFile = itr.next();
                                            if (search_elements[1].equals("artist")) {
                                                if (sFile.getArtist().toLowerCase().contains(search_elements[2].toLowerCase().split("\"")[1])) {
                                                    sFile.printSongFile();
                                                }
                                            } else if (search_elements[1].equals("title")) {
                                                if (sFile.getTitle().toLowerCase().contains(search_elements[2].toLowerCase().split("\"")[1])) {
                                                    sFile.printSongFile();
                                                }
                                            } else {
                                                System.out.println("Wrong first parameter : Either title or artist is expected !!");
                                                break;
                                            }
                                        }
                                    } else {
                                        System.out.println("Search string should be entered in double quotes !!");
                                    }
                                }
                                break;

                            case "sort":
                                /**
                                 * ***************************** Sort the
                                 * central store by artist or title
                                 * ****************************
                                 */
                                if (commands.length < 2 || commands.length > 2) { // Command length should be exactly 2
                                    System.out.println("Wrong number of parameters !! {Usage} - sort <\"artist\"> or <\"title\">");
                                } else {
                                    ArrayList<SongFile> sorted_central_store = new ArrayList<>(central_store);

                                    if (commands[1].equals("artist")) {

                                        //Sorting the central store by artist
                                        Collections.sort(sorted_central_store, new Comparator<SongFile>() {

                                            @Override
                                            public int compare(SongFile o1, SongFile o2) {
                                                return o1.getArtist().compareTo(o2.getArtist());
                                            }
                                        });

                                        System.out.println("Sorted on Artist : ");
                                        for (SongFile sFile : sorted_central_store) {
                                            sFile.printSongFile();
                                        }
                                    } else if (commands[1].equals("title")) {

                                        //Sorting the central store by title
                                        Collections.sort(sorted_central_store, new Comparator<SongFile>() {

                                            @Override
                                            public int compare(SongFile o1, SongFile o2) {
                                                return o1.getTitle().compareTo(o2.getTitle());
                                            }
                                        });

                                        System.out.println("Sorted on Title : ");
                                        for (SongFile sFile : sorted_central_store) {
                                            sFile.printSongFile();
                                        }
                                    } else {
                                        System.out.println("Wrong parameter : Either title or artist is expected !!");
                                    }
                                }
                                break;

                            case "quit":
                                if (commands.length > 1 || commands.length < 1) { // Command length should be exactly 1
                                    System.out.println("Wrong number of parameters !! {Usage} - quit");
                                } else {
                                    break;
                                }

                            default:
                                System.out.println("Invalid command !!");
                        }
                    } else {
                        if (commands.length == 0) {
                        } else {
                            System.out.println("Invalid command !!" + commands.length);
                        }
                    }
                } catch (IOException ex) {
                    System.out.println("Error occured while handling the standard input ouput !!");
                }
            }
        }
    }

    private static void showPlayListMenu(String current_playlist_name, HashMap playlist_songs, ArrayList<SongFile> central_store) {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in)); // Reading in playlist operations from user
        String commands[];
        String command = "";
        int size = central_store.size();

        /**
         * ***************************** Operations allowed on playlist
         * ****************************
         */
        ArrayList<String> playlist_menu_commands = new ArrayList<>();
        playlist_menu_commands.add("insert");
        playlist_menu_commands.add("delete");
        playlist_menu_commands.add("insert_search");
        playlist_menu_commands.add("print");
        playlist_menu_commands.add("search");
        playlist_menu_commands.add("sort");
        playlist_menu_commands.add("main");

        /**
         * ***************************** Prompt user for operations on playlist
         * ****************************
         */
        while (!command.equals("main")) {

            showPlaylistOps(); //Function for showing playlist operations to user

            try {
                command = in.readLine();
                commands = command.split(" ");

                if (playlist_menu_commands.contains(commands[0])) {
                    switch (commands[0]) {
                        case "delete":
                            /**
                             * ***************************** Delete a song from
                             * current playlist ****************************
                             */
                            if (commands.length < 2 || commands.length > 2) { // Command length should be exactly 2
                                System.out.println("Wrong number of parameters !! {Usage} - delete <songId>");
                            } else {
                                if (playlist_songs.containsKey(current_playlist_name)) {
                                    ArrayList<SongFile> songs = (ArrayList<SongFile>) playlist_songs.get(current_playlist_name);
                                    try {
                                        int id = Integer.parseInt(commands[1]);

                                        SongFile songFile = null;
                                        if (id >= size || id < 0) {
                                            System.out.println("The song with this songId does not exist in this playlist !!");
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
                                        System.out.println("The <songId> must be an integer !!");
                                    }

                                } else {
                                    System.out.println("Playlist with this name does not exist !!");
                                }
                            }
                            break;

                        case "insert":
                            /**
                             * ***************************** Insert a song into
                             * current playlist ****************************
                             */
                            if (commands.length < 2 || commands.length > 2) { // Command length should be exactly 2
                                System.out.println("Wrong number of parameters !! {Usage} - insert <songId>");
                            } else {
                                if (playlist_songs.containsKey(current_playlist_name)) {
                                    ArrayList<SongFile> songs = (ArrayList<SongFile>) playlist_songs.get(current_playlist_name);
                                    try {
                                        int id = Integer.parseInt(commands[1]);

                                        SongFile songFile = null;

                                        //Checking if songId is within the size of central store
                                        if (id >= size || id < 0) {
                                            System.out.println("There is no song with this id in the central store !!");
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
                                        System.out.println("The <songId> must be an integer !!");
                                    }

                                } else {
                                    System.out.println("Playlist with this name does not exist !!");
                                }
                            }
                            break;

                        case "insert_search":
                            /**
                             * ***************************** Bonus -- search
                             * for artist or title and add to current playlist
                             * if found ****************************
                             */
                            if (commands.length < 3 || commands.length > 3) { // Command length should be exactly 3
                                System.out.println("Wrong number of parameters !! {Usage} - insert_search <\"artist\"> <\"string of words\"> or <\"title\"> <\"string of words\">");
                            } else {
                                ArrayList<SongFile> songs = (ArrayList<SongFile>) playlist_songs.get(current_playlist_name);
                                if (playlist_songs.containsKey(current_playlist_name)) {
                                    String[] search_elements = command.split(" ", 3);
                                    Iterator<SongFile> itr = central_store.iterator();
                                    int flag = 0;

                                    if (search_elements[2].split("\"").length == 2) {
                                        while (itr.hasNext()) {
                                            SongFile sFile = itr.next();
                                            flag = 0;
                                            if (search_elements[1].equals("artist")) {
                                                if (sFile.getArtist().toLowerCase().contains(search_elements[2].toLowerCase().split("\"")[1])) {
                                                    for (SongFile s : songs) {
                                                        if (s.getSongId() == sFile.getSongId()) {
                                                            flag = 1;
                                                            break;
                                                        }
                                                    }
                                                    if (flag == 0) {
                                                        System.out.println("Artist found !! Appending it to playlist......");
                                                        songs.add(sFile);
                                                    } else {
                                                        System.out.println("Artist found !! But song already exists in playlist......");
                                                    }
                                                }
                                            } else if (search_elements[1].equals("title")) {
                                                if (sFile.getTitle().toLowerCase().contains(search_elements[2].toLowerCase().split("\"")[1])) {
                                                    for (SongFile s : songs) {
                                                        if (s.getSongId() == sFile.getSongId()) {
                                                            flag = 1;
                                                            break;
                                                        }
                                                    }
                                                    if (flag == 0) {
                                                        System.out.println("Title found !! Appending it to playlist......");
                                                        songs.add(sFile);
                                                    } else {
                                                        System.out.println("Title found !! But song already exists in playlist......");
                                                    }
                                                }
                                            } else {
                                                System.out.println("Wrong first parameter : Either title or artist is expected !!");
                                                break;
                                            }
                                        }
                                    } else {
                                        System.out.println("Search string should be entered in double quotes !!");
                                    }
                                } else {
                                    System.out.println("Playlist with this name does not exist !!");
                                }
                            }
                            break;

                        case "print":
                            /**
                             * ***************************** Print the contents
                             * of each song for current playlist
                             * ****************************
                             */
                            if (commands.length > 1 || commands.length < 1) { // Command length should be exactly 1
                                System.out.println("Wrong number of parameters !! {Usage} - print");
                            } else {
                                if (playlist_songs.containsKey(current_playlist_name)) {
                                    ArrayList<SongFile> printsongs = (ArrayList<SongFile>) playlist_songs.get(current_playlist_name);
                                    System.out.println("Songs in '" + current_playlist_name + "' playlist are : ");
                                    Iterator<SongFile> songitr = printsongs.iterator();
                                    while (songitr.hasNext()) {
                                        SongFile sFile = songitr.next();
                                        sFile.printSongFile();
                                    }
                                } else {
                                    System.out.println("Playlist with this name does not exist !!");
                                }
                            }
                            break;

                        case "search":
                            /**
                             * ***************************** Search a artist or
                             * title in current playlist
                             * ****************************
                             */
                            if (commands.length < 3 || commands.length > 3) { // Command length should be exactly 3
                                System.out.println("Wrong number of parameters !! {Usage} - search <\"artist\"> <\"string of words\"> or <\"title\"> <\"string of words\">");
                            } else {
                                if (playlist_songs.containsKey(current_playlist_name)) {
                                    ArrayList<SongFile> searchsongs = (ArrayList<SongFile>) playlist_songs.get(current_playlist_name);
                                    String[] search_elements = command.split(" ", 3);
                                    Iterator<SongFile> itr = searchsongs.iterator();
                                    if (search_elements[2].split("\"").length == 2) {
                                        while (itr.hasNext()) {
                                            SongFile sFile = itr.next();
                                            if (search_elements[1].equals("artist")) {
                                                if (sFile.getArtist().toLowerCase().contains(search_elements[2].toLowerCase().split("\"")[1])) {
                                                    sFile.printSongFile();
                                                }
                                            } else if (search_elements[1].equals("title")) {
                                                if (sFile.getTitle().toLowerCase().contains(search_elements[2].toLowerCase().split("\"")[1])) {
                                                    sFile.printSongFile();
                                                }
                                            } else {
                                                System.out.println("Wrong first parameter : Either title or artist is expected !!");
                                                break;
                                            }
                                        }
                                    } else {
                                        System.out.println("Search string should be entered in double quotes !!");
                                    }
                                } else {
                                    System.out.println("Playlist with this name does not exist !!");
                                }
                            }
                            break;

                        case "sort":
                            /**
                             * ***************************** Sort current
                             * playlist on the basis of either artist or title
                             * ****************************
                             */
                            if (commands.length < 2 || commands.length > 2) { // Command length should be exactly 2
                                System.out.println("Wrong number of parameters !! {Usage} - sort <\"artist\"> or <\"title\">");
                            } else {
                                if (playlist_songs.containsKey(current_playlist_name)) {
                                    ArrayList<SongFile> tempsortsongs = (ArrayList<SongFile>) playlist_songs.get(current_playlist_name);
                                    ArrayList<SongFile> sortsongs = new ArrayList<>(tempsortsongs);

                                    if (commands[1].equals("artist")) {
                                        Collections.sort(sortsongs, new Comparator<SongFile>() {

                                            @Override
                                            public int compare(SongFile o1, SongFile o2) {
                                                return o1.getArtist().compareTo(o2.getArtist());
                                            }
                                        });

                                        System.out.println("Sorted on Artist : ");
                                        for (SongFile sFile : sortsongs) {
                                            sFile.printSongFile();
                                        }
                                    } else if (commands[1].equals("title")) {
                                        Collections.sort(sortsongs, new Comparator<SongFile>() {

                                            @Override
                                            public int compare(SongFile o1, SongFile o2) {
                                                return o1.getTitle().compareTo(o2.getTitle());
                                            }
                                        });

                                        System.out.println("Sorted on Title : ");
                                        for (SongFile sFile : sortsongs) {
                                            sFile.printSongFile();
                                        }
                                    } else {
                                        System.out.println("Wrong parameter : Either title or artist is expected !!");
                                    }
                                } else {
                                    System.out.println("Playlist with this name does not exist !!");
                                }
                            }
                            break;

                        case "main":
                            if (commands.length > 1 || commands.length < 1) { // Command length should be exactly 1
                                System.out.println("Wrong number of parameters !! {Usage} - main");
                            } else {
                                break;
                            }
                            break;

                        default:
                            System.out.println("Invalid operation !!");
                    }
                } else {
                    System.out.println("Invalid operation !!");
                }

            } catch (IOException ex) {
                System.out.println("Error occured while handling the standard input ouput !!");
            }
        }
    }

    private static void showMainMenu() {
        System.out.println("\nMain Menu :-");
        System.out.println("1.Create : create <playlist name>");
        System.out.println("2.Edit : edit <playlistId>");
        System.out.println("3.Print song : song <songId>");
        System.out.println("4.Print playlist : playlist <playlistId>");
        System.out.println("5.Print All Songs or Playlists : print <print option>");
        System.out.println("6.Search : search <search option> <\"string of words\">");
        System.out.println("7.Sort : sort <sort option>");
        System.out.println("8.Quit : quit");

        System.out.print("\nEnter command >>> ");
    }

    private static void showPlaylistOps() {
        System.out.println("\nMenu for operations on playlist :-");
        System.out.println("1.Delete : delete <songId>");
        System.out.println("2.Insert : insert <songId>");
        System.out.println("3.Insert Search : insert_search <search option> <\"string of words\">");
        System.out.println("4.Print : print");
        System.out.println("5.Search : search <search option> <\"string of words\">");
        System.out.println("6.Sort : sort <sort option>");
        System.out.println("7.Main Menu : main");

        System.out.print("\nEnter Operation >>> ");
    }
}

class ReadConfigs {
    // Class for reading property files

    String propFileName;

    public ReadConfigs(String propFileName) {
        this.propFileName = propFileName;
    }

    // Read particular property from config file
    public String getPropValues(String key) throws IOException {

        Properties prop = new Properties();
        String path = "";

        InputStream inputStream = getClass().getResourceAsStream(this.propFileName);

        if (inputStream != null) {
            try {
                prop.load(inputStream);
                path = prop.getProperty(key);
            } catch (FileNotFoundException ex) {
                System.out.println("Config file not found !!");
            }
        } else {
            System.out.println("Config file not found !!");
        }

        // Return path of .text file 
        return path;
    }
}
