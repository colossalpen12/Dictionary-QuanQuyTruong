import java.sql.*;
import java.util.*;

public class Dictionary {
    public class Word {
        String word_explain;
        String word_target;

        Word (String word_explain, String word_target) {
            this.word_target = word_target;
            this.word_explain = word_explain;
        }
    }

    public ArrayList<Word> Dict = new ArrayList<Word>();
    TrieHash demo = new TrieHash();
    public Map<String, String> demo_map = new HashMap<String, String>();

    public void Retrieve() {
        String url = "jdbc:sqlite:C:/Users/quyhd/Downloads/AVIE.db";
        String sql = "SELECT word, description FROM av";
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url);
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