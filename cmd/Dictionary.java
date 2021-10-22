package cmd;

import java.sql.*;
import java.util.*;

public class Dictionary {
    public static class Word {
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
    private final String url = "jdbc:sqlite:C:/Users/quyhd/Downloads/AVIE.db";
    private final String[] Available_Databases = {"aa", "va", "av"};
    private String db;
    private final Scanner sc = new Scanner(System.in);
    WebScraping internet = new WebScraping();

    /**
     * choose modes for dictionary (av, va, aa)
     */
    private void chooseMode() {
        System.out.println("Which dictionary do you wish to use?");
        System.out.println("1. English-English");
        System.out.println("2. Vietnamese-English");
        System.out.println("3. English-Vietnamese");
        db = Available_Databases[sc.nextInt()-1];
    }

    /**
     * Retrieve selected data from sqlite database
     */
    public void Retrieve() {
        chooseMode();
        // change url to "jdbc:sqlite:path to database"
        String sql = "SELECT word, description FROM " + db;
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

    public void add_word() {
        System.out.println("Enter the word you wish to add");
        String word = sc.next().toLowerCase();
        System.out.println("Enter its description");
        String description = sc.next().toLowerCase();
        String sql = "INSERT INTO " + db + " VALUES (null, '" + word + "', null, '" + description + "', null)";
        try {
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Word added successfully");
        System.out.println("Enter anything to continue");
        String tmp = sc.next();
    }

    public void remove_word() {
        System.out.println("Enter the word you wish to remove");
        String word = sc.next().toLowerCase();
        String sql = "DELETE FROM " + db + " WHERE word = '" + word + "'";
        try {
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Word removed successfully");
        System.out.println("Enter anything to continue");
        String tmp = sc.next();
    }

    public void edit_word() {
        System.out.println("Enter the word you wish to edit");
        String word = sc.next().toLowerCase();
        System.out.println("Enter its new description");
        String new_description = sc.next().toLowerCase();
        String sql = "UPDATE " + db + " SET description = '" + new_description + "' WHERE word = '" + word + "'";
        try {
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Word edited successfully");
        System.out.println("Enter anything to continue");
        String tmp = sc.next();
    }

    public void features() {
        System.out.println("Which features do you wish to use?");
        System.out.println("1. Online database");
        System.out.println("2. Prefix matching");
        System.out.println("3. Online prefix matching");
        int action = sc.nextInt();
        if (action == 1) {
            System.out.println("Enter the word you wish to look up");
            String word_to_lookup = sc.next();
            System.out.println(internet.getDescription(word_to_lookup));
            System.out.println("Enter anything to continue");
            String tmp = sc.next();
        } else if (action == 2) {
            System.out.println("Enter the word you wish to try");
            String word_to_try = sc.next();
            for (String s : demo.prefixMatching(word_to_try)) {
                System.out.println(s);
            }
            System.out.println("Enter anything to continue");
            String tmp = sc.next();
        } else {
            System.out.println("Enter the word you wish to try");
            String word_to_try = sc.next();
            for (String s : internet.getSuggestion(word_to_try)) {
                System.out.println(s);
            }
            System.out.println("Enter anything to continue");
            String tmp = sc.next();
        }

    }
}