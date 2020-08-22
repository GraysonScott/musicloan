package ca.graysonscott.musicloan;

public interface DAO {
    public void setup() throws Exception;
    public void connect() throws Exception;
    public void close() throws Exception;
}
