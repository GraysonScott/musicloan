package ca.graysonscott.musicloan;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLAlbumDAO implements AlbumDAO {
    private Connection connection = null;
    private final QueryRunner dbAccess = new QueryRunner();
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/javabase?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "root";
    private static final String ALBUM_INSERT =
            "INSERT INTO Albums (name, artist, genre, publishedYear, available) VALUES (?, ?, ?, ?, ?)";
    private static final String ALBUM_UPDATE =
            "UPDATE Albums SET name=?, artist=?, genre=?, publishedYear=?, available=? WHERE uniqueID=?";
    private static final List<Album> EMPTY = new ArrayList<>();

    @Override
    public void setup() throws Exception {
        connection = DriverManager.getConnection(DB_URL, USER, PASS);

        Statement statement = connection.createStatement();
        statement.executeUpdate("CREATE TABLE Albums ("
                + "uniqueID BIGINT NOT NULL AUTO_INCREMENT,"
                + "name VARCHAR(30) NOT NULL, artist VARCHAR(30),"
                + "genre ENUM('ROCK', 'COUNTRY', 'ELECTRONIC', 'POP', 'PUNK', 'METAL', 'HIPHOP'),"
                + "publishedYear YEAR, available BOOLEAN,"
                + "PRIMARY KEY (uniqueID)"
                + ")");
    }

    @Override
    public void connect() throws Exception {
        connection = DriverManager.getConnection(DB_URL, USER, PASS);
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }

    @Override
    public boolean insertAlbum(Album album) {
        try {
            PreparedStatement statement = connection.prepareStatement(ALBUM_INSERT, Statement.RETURN_GENERATED_KEYS);
            prepareAlbum(statement, album);


            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Adding album failed, no rows affected");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if(generatedKeys.next()) {
                    album.setUniqueID(generatedKeys.getLong(1));
                    return true;
                } else {
                    throw new SQLException("Adding album failed, no uniqueID generated");
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean updateAlbum(Album album) {
        try {
            PreparedStatement statement = connection.prepareStatement(ALBUM_UPDATE);
            prepareAlbum(statement, album);
            statement.setLong(6, album.getUniqueID());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Adding album failed, no rows affected");
            } else {
                return true;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean deleteAlbum(Album album) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM Albums WHERE uniqueID=?");
            statement.setLong(1, album.getUniqueID());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Deleting album failed, no rows affected");
            } else {
                return true;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Album> findAlbumByProperty(AlbumSearchType searchType, Object value) {
        String whereClause = "";
        String valueClause = "";

        switch (searchType) {
            case ARTIST:
                whereClause = "artist LIKE ?";
                valueClause = "%" + value.toString() + "%";
                break;
            case AVAILABLE:
                whereClause = "available = ?";
                valueClause = value.toString();
                break;
            case ID:
                whereClause = "uniqueID = ?";
                valueClause = value.toString();
                break;
            case NAME:
                whereClause = "name LIKE ?";
                valueClause = "%" + value.toString() + "%";
                break;
            case PUBLISHED_YEAR:
                whereClause = "publishedYear = ?";
                valueClause = value.toString();
                break;
            case GENRE:
                whereClause = "genre = ?";
                valueClause = "%" + value.toString() + "%";
                break;
            default:
                System.out.println("Unknown search type");
                break;
        }

        try {
            return dbAccess.query(connection, "SELECT * FROM Albums WHERE " + whereClause, new BeanListHandler<>(Album.class), valueClause);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return EMPTY;
    }

    @Override
    public List<Album> findAll() {
        try {
            return dbAccess.query(connection, "SELECT * FROM Albums", new BeanListHandler<>(Album.class));
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return EMPTY;
    }

    private void prepareAlbum(PreparedStatement statement, Album album) throws SQLException{
        try {
            statement.setString(1, album.getName());
            statement.setString(2, album.getArtist());
            statement.setString(3, album.getGenre().name());
            statement.setInt(4, album.getPublishedYear());
            statement.setBoolean(5, album.isAvailable());
        } catch(Exception e){
            throw new SQLException("Unable to set PreparedStatement");
        }
    }
}