package cmd;

import java.util.Scanner;

public class CommandLine {
    private static void launch() {
        Dictionary demo = new Dictionary();
        demo.Retrieve();
        do {
            System.out.println("What do you wish to do?");
            System.out.println("1. Look up a word");
            System.out.println("2. Add a word");
            System.out.println("3. Remove a word");
            System.out.println("4. Edit a word");
            System.out.println("5. Show all words and their definition");
            System.out.println("6. Change dictionary");
            System.out.println("7. Try out advanced features");
            System.out.println("8. Exit");
            Scanner sc = new Scanner(System.in);
            int action = sc.nextInt();
            if (action == 1) {
                System.out.println("Enter the word you wish to look up");
                String word_to_lookup = sc.next();
                if (demo.demo.search(word_to_lookup))
                    System.out.println(demo.demo_map.get(word_to_lookup));
                else
                    System.out.println("Sorry we don't have this word in the database");
                System.out.println("Enter anything to continue");
                String tmp = sc.next();
            }
            else if (action == 2)
                demo.add_word();
            else if (action == 3)
                demo.remove_word();
            else if (action == 4)
                demo.edit_word();
            else if (action == 5) {
                int index = 1;
                for (Dictionary.Word tmp : demo.Dict) {
                    System.out.println(index + ". " + tmp.word_target + ": " + tmp.word_explain);
                    index++;
                }
                System.out.println("Enter anything to continue");
                String tmp = sc.next();
            } else if (action == 6)
                launch();
            else if (action == 7)
                demo.features();
            else break;
        } while (true);
    }

    public static void main(String[] args) {
        launch();
    }
}
