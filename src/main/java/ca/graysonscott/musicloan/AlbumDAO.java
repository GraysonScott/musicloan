package ca.graysonscott.musicloan;

import java.util.List;

public interface AlbumDAO extends DAO {
    public boolean insertAlbum(Album album);
    public boolean updateAlbum(Album album);
    public boolean deleteAlbum(Album album);

    public List<Album> findAlbumByProperty(ca.graysonscott.musicloan.AlbumSearchType searchType, Object value);
    public List<Album> findAll();
}
