package tudienbachkhoa.dictionary;

import java.util.*;

public class HashTrie {
    static class Node{
        Map<Character, Node> children;
        char c;
        boolean endOfWord;

        Node() {
            children = new HashMap<>();
        }

        Node(char c) {
            children = new HashMap<>();
            this.c = c;
        }
    }

    Node root = new Node();

    public void remove(String key) {
        Node current = root;
        for (char c : key.toLowerCase().toCharArray()) {
            current = current.children.get(c);
        }
        current.endOfWord = false;
    }

    public void insert(String key) {
        Node current = root;
        for (char c : key.toLowerCase().toCharArray()) {
            if (!current.children.containsKey(c)) {
                current.children.put(c, new Node(c));
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
        return current.endOfWord;
    }

    /**
     * from prefix's node go through all its child to find words with matched prefix
     */
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

    /**
     * try to get to prefix's node if possible
     * @return prefix's node
     */
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
