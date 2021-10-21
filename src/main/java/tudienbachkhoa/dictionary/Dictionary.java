package tudienbachkhoa.dictionary;

import java.sql.*;
import java.util.*;

public class Dictionary {
    static class Word {
        String word_explain;
        String word_target;

        Word(String word_explain, String word_target) {
            this.word_target = word_target;
            this.word_explain = word_explain;
        }
    }

    public ArrayList<String> Dict;
    public HashTrie ListOfWord;
    public Map<String, String> WordMap;


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
        // change url to "jbdc:sqlite:path to database"
        String url = "jdbc:sqlite:/Users/garynguyen95/IdeaProjects/Dictionary/src/main/resources/AVIE.db";  /** sửa lại theo đường dẫn absolute của mỗi máy */
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

    public static void main(String[] args) {
        Dictionary demo = new Dictionary();
        demo.Retrieve("English to Vietnamese");
        Scanner sc = new Scanner(System.in);
        String wordToFind = sc.next();
        if (demo.ListOfWord.search(wordToFind)) {
            System.out.println("Word found!");
            System.out.println(demo.WordMap.get(wordToFind));
        } else
            System.out.println("Word not found");
        List<String> rs = demo.ListOfWord.prefixMatching("app");
        for (String s : rs) {
            System.out.println(s);
        }
    }

    /**
     * database manipulation
     **/

    private final String url = "jdbc:sqlite:/Users/garynguyen95/IdeaProjects/Dictionary/src/main/resources/AVIE.db";

    public void insert(String newWord, String definition, String dictionary) {
        /** nho kiem tra xem da co newWord hay ko. */
        String sql = "INSERT INTO " + chooseMode(dictionary);
        sql += " VALUES (null, '" + newWord + "', null, '" + definition + "', null)";
        try {
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // return true;
    }

    public void delete(String oldWord, String dictionary) {
        /** mac dinh phai co oldWord, chua co thi hien thong bao. */
        String sql = "DELETE FROM " + chooseMode(dictionary) + " WHERE description = ?";
        String definition = WordMap.get((oldWord));
        try {
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, definition);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void apply(String thisWord, String definition, String dictionary) {
        String sql = "UPDATE " + chooseMode(dictionary) + " SET description = '" + definition + "' WHERE word = '" + thisWord + "'";
        try {
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement ps = conn.prepareStatement(sql);
   //         ps.setString(1, thisWord);
    //        ps.setString(2, definition);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}