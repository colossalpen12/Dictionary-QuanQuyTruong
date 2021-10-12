import java.sql.*;
import java.util.*;

public class Dictionary {
    static class Word {
        String word_explain;
        String word_target;

        Word (String word_explain, String word_target) {
            this.word_target = word_target;
            this.word_explain = word_explain;
        }
    }

    public ArrayList<Word> Dict = new ArrayList<>();
    HashTrie demo = new HashTrie();
    public Map<String, String> demo_map = new HashMap<>();

    /**
     * choose modes for dictionary (av, va, aa)
     * @return preferred mode
     */
    static String chooseMode() {
        Scanner sc = new Scanner(System.in);
        return sc.next();
    }

    /**
     * Retrieve selected data from sqlite database
     */
    public void Retrieve() {
        // change url to "jbdc:sqlite:path to database"
        String url = "jdbc:sqlite:C:/Users/quyhd/Downloads/AVIE.db";
        String sql = "SELECT word, description FROM " + chooseMode();
        try {
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Dict.add(new Word(rs.getString("description"), rs.getString("word")));
                demo.insert(rs.getString("word"));
                demo_map.put(rs.getString("word"), rs.getString("description"));
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        Dictionary demo = new Dictionary();
        demo.Retrieve();
        Scanner sc = new Scanner(System.in);
        String wordToFind = sc.next();
        if (demo.demo.search(wordToFind)) {
            System.out.println("Word found!");
            System.out.println(demo.demo_map.get(wordToFind));
        } else
            System.out.println("Word not found");
        List<String> rs = demo.demo.prefixMatching(wordToFind);
        for (String s : rs) {
            System.out.println(s);
        }
    }
}