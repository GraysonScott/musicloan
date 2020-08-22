package ca.graysonscott.musicloan;

import java.util.List;

public class MusicCatalogue {

    private final AlbumDAO albumDAO;

    public MusicCatalogue(AlbumDAO albumDAO) {
        this.albumDAO = albumDAO;

        try{
            albumDAO.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addAlbum(String name, String artist, GenreType genre, int year) {
        Album album = new Album();
        album.setAvailable(true);
        album.setName(name);
        album.setArtist(artist);
        album.setGenre(genre);
        album.setPublishedYear(year);

        if(!albumDAO.insertAlbum(album)) {
            System.out.println("Error adding album");
        }
    }

    public void loanAlbum(long uniqueID) {
        List<Album> albums = albumDAO.findAlbumByProperty(AlbumSearchType.ID, uniqueID);
        if (albums.size() > 0) {
            albums.get(0).setAvailable(false);
            if(!albumDAO.updateAlbum(albums.get(0))) {
                System.out.println("Error loaning album");
            }
        }
    }

    public void returnAlbum(long uniqueID) {
        List<Album> albums = albumDAO.findAlbumByProperty(AlbumSearchType.ID, uniqueID);
        if (albums.size() > 0) {
            albums.get(0).setAvailable(true);
            if(!albumDAO.updateAlbum(albums.get(0))) {
                System.out.println("Error returning album");
            }
        }
    }

    public List<Album> search(AlbumSearchType searchType, String value) {
        return albumDAO.findAlbumByProperty(searchType, value);
    }

    public void close() {
        try {
            albumDAO.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Album> findAll() {
        return albumDAO.findAll();
    }

    public void deleteAlbum(long uniqueID) {
        List<Album> albums = albumDAO.findAlbumByProperty(AlbumSearchType.ID, uniqueID);
        if (albums.size() > 0) {
            if(!albumDAO.deleteAlbum(albums.get(0))) {
                System.out.println("Error deleting album");
            }
        } else {
            System.out.println("Could not find album to delete");
        }
    }
}
