import java.util.*;

public class TrieHash {
    public class Node{
        Map<Character, Node> children;
        char c;
        boolean endOfWord;

        Node() {
            children = new HashMap<>();
        }
    }

    Node root = new Node();

    public void insert(String key) {
        Node current = root;
        for (char c : key.toCharArray()) {
            if (!current.children.containsKey(c)) {
                current.children.put(c, new Node());
                current.c = c;
            }
            current = current.children.get(c);
        }
        current.endOfWord = true;
    }

    public boolean search(String key) {
        Node current = root;
        for (char c : key.toCharArray()) {
            if (current.children.containsKey(c))
                current = current.children.get(c);
            else
                return false;
        }
        return true;
    }

    public void prefixSearch(Node root, List<String> list, StringBuffer curr) {
        if (root.endOfWord)
            list.add(curr.toString());
        if (root.children.isEmpty())
            return;
        for (Node child : root.children.values()) {
            prefixSearch(child, list, curr.append(child.c));
            curr.setLength(curr.length()-1);
        }
    }

    public List<String> prefixMatching(String prefix) {
        List<String> list = new ArrayList<>();
        Node current = root;
        StringBuffer curr = new StringBuffer();
        for (char c : prefix.toCharArray()) {
            current = current.children.get(c);
            if (current == null)
                return list;
            curr.append(c);
        }
        prefixSearch(current, list, curr);
        return list;
    }
}
