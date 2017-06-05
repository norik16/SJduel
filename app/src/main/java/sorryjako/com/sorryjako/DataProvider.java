package sorryjako.com.sorryjako;

/**
 * Created by Veronika and Ron on 03.06.2017.
 */

public class DataProvider {

    private int id;
    private String line;
    private String person;
    private int score;

    public DataProvider(int id, String line, String person, int score) {
        this.id = id;
        this.line = line;
        this.person = person;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
