package ca.graysonscott.musicloan;

import java.util.Objects;

public class Album {
    private long uniqueID;
    private String name;
    private String artist;
    private ca.graysonscott.musicloan.GenreType genre;
    private int publishedYear;
    private boolean available;

    public long getUniqueID() {
        return uniqueID;
    }

    public GenreType getGenre() {
        return genre;
    }

    public void setGenre(ca.graysonscott.musicloan.GenreType genre) {
        this.genre = genre;
    }

    public void setUniqueID(long uniqueID) {
        this.uniqueID = uniqueID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    @Override
    public String toString() {
        return "Album{" +
                "uniqueID=" + uniqueID +
                ", name='" + name + '\'' +
                ", artist='" + artist + '\'' +
                ", publishedYear=" + publishedYear +
                ", available=" + available +
                '}';
    }

    public int getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(int publishedYear) {
        this.publishedYear = publishedYear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Album album = (Album) o;
        return publishedYear == album.publishedYear &&
                name.equals(album.name) &&
                artist.equals(album.artist);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, artist, publishedYear);
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
