import java.util.HashMap;

/**
 * Created by Tingting on 10/7/16.
 */
public class TrieNode {
    String s;
    boolean isString;
    HashMap<Character, TrieNode> subtree;
    public TrieNode(){
        isString = false;
        subtree = new HashMap<Character, TrieNode>();
        s = "";
    }
}
