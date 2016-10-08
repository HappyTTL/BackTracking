/**
 * Created by Tingting on 10/7/16.
 */
public class TrieTree {
    TrieNode root;
    public TrieTree() {
        root = new TrieNode();
    }
    public void insert(String s) {
        TrieNode now = root;
        for (int i = 0; i < s.length(); i++) {
            if (!now.subtree.containsKey(s.charAt(i))) {
                now.subtree.put(s.charAt(i), new TrieNode());
            }
            now = now.subtree.get(s.charAt(i));
        }
        now.isString = true;
        now.s = s;
    }
    public boolean find(String s) {
        TrieNode now = root;
        for (int i = 0; i < s.length(); i++) {
            if (!now.subtree.containsKey(s.charAt(i))) {
                return false;
            }
            now = now.subtree.get(s.charAt(i));
        }
        return now.isString;
    }
    public boolean startWith(String prefix) {
        TrieNode now = root;
        for (int i = 0; i < prefix.length(); i++) {
            if (!now.subtree.containsKey(prefix.charAt(i))) {
                return false;
            }
            now = now.subtree.get(prefix.charAt(i));
        }
        return true;
    }
}
