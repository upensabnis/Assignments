/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package marketomusic.beans;

/**
 *
 * @author Upendra
 */
public class SongFile {
    
    private int songId;
    private String title;
    private String artist;
    
    public SongFile(int songId, String artist, String title){
        this.songId = songId;
        this.title = title;
        this.artist = artist;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }   
    
}
