package tudienbachkhoa.dictionary;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Dictionary {
    public ArrayList<String> Dict;
    public HashTrie ListOfWord;
    public Map<String, String> WordMap;
    private final String url = "jdbc:sqlite:src/main/resources/AVIE.db";  //sửa lại theo đường dẫn absolute của mỗi máy

    /**
     * choose modes for dictionary (av, va, aa)
     *
     * @return preferred mode
     */
    static String chooseMode(String dictionary) {
        if (dictionary.equals("English to English"))
            return "aa";
        else if (dictionary.equals("Vietnamese to English"))
            return "va";
        return "av";
    }

    /**
     * Retrieve selected data from sqlite database
     */
    public void Retrieve(String dictionary) {
        String sql = "SELECT word, description FROM " + chooseMode(dictionary);
        Dict = new ArrayList<>();
        ListOfWord = new HashTrie();
        WordMap = new HashMap<>();
        try {
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Dict.add(rs.getString("word"));
                ListOfWord.insert(rs.getString("word"));
                WordMap.put(rs.getString("word"), rs.getString("description"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * database manipulation
     **/
    public void insert(String newWord, String definition, String dictionary) {
        String sql = "INSERT INTO " + chooseMode(dictionary) + " VALUES (null, '" + newWord + "', null, '" + definition + "', null)";
        try {
            Connection conn = DriverManager.getConnection(url);
            Statement ps = conn.createStatement();
            ps.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        WordMap.put(newWord, definition);
        ListOfWord.insert(newWord);
    }

    public void delete(String oldWord, String dictionary) {
        String sql = "DELETE FROM " + chooseMode(dictionary) + " WHERE word = '" + oldWord +"'";
        try {
            Connection conn = DriverManager.getConnection(url);
            Statement ps = conn.createStatement();
            ps.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        WordMap.remove(oldWord);
        ListOfWord.remove(oldWord);
    }

    public void apply(String thisWord, String definition, String dictionary) {
        String sql = "UPDATE " + chooseMode(dictionary) + " SET description = '" + definition + "' WHERE word = '" + thisWord + "'";
        try {
            Connection conn = DriverManager.getConnection(url);
            Statement ps = conn.createStatement();
            ps.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        WordMap.put(thisWord, definition);
    }
}