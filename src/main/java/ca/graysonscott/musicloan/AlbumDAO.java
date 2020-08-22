package ca.graysonscott.musicloan;

import java.util.List;

public interface AlbumDAO extends DAO {
    boolean insertAlbum(Album album);
    boolean updateAlbum(Album album);
    boolean deleteAlbum(Album album);

    List<Album> findAlbumByProperty(ca.graysonscott.musicloan.AlbumSearchType searchType, Object value);
    List<Album> findAll();
}
