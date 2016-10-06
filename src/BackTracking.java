import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Tingting on 10/6/16.
 */
public class BackTracking {
    /**
     * Letter Combination of a Phone Number: iven a digit string, return all possible letter combinations that the
     * number could represent.
     */
    public List<String> letterCombinations(String digits) {
        List<String> results = new ArrayList<>();
        if (digits == "" || digits.length() == 0) {
            return results;
        }
        HashMap<Character, String> map = new HashMap<>();
        map.put('1', "");
        map.put('2', "abc");
        map.put('3', "def");
        map.put('4', "ghi");
        map.put('5', "jkl");
        map.put('6', "mno");
        map.put('7', "pqrs");
        map.put('8', "tuv");
        map.put('9', "wxyz");
        map.put('0', "");
        ArrayList<Character> list = new ArrayList<>();
        letterCombinations(digits, map, list, results);
        return results;
    }
    private void letterCombinations(String digits, HashMap<Character, String> map, ArrayList<Character> list, List<String> results) {
        if (digits.length() == 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                sb.append(list.get(i));
            }
            results.add(sb.toString());
//            System.out.println(sb.toString());
            return;
        }
        String letters = map.get(digits.charAt(0));
        for (int i = 0; i < letters.length(); i++) {
            list.add(letters.charAt(i));
            letterCombinations(digits.substring(1), map, list, results);
            list.remove(list.size() - 1);
        }
    }

    /**
     * Word Search: Given a 2D board and a word, find if the word exists in the grid.

     The word can be constructed from letters of sequentially adjacent cell, where "adjacent" cells are those
     horizontally or vertically neighboring. The same letter cell may not be used more than once.
     * @param args
     */
    public boolean exist(char[][] board, String word) {
        if (word == null || word.length() == 0) {
            return true;
        }
        if (board == null || board.length == 0 || board[0].length == 0 || board.length * board[0].length < word.length()) {
            return false;
        }
        int m = board.length, n = board[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (existDFS(board, word, i, j, 0)) {
                    return true;
                }
            }
        }
        return false;
    }
    private boolean existDFS(char[][] board, String word, int i, int j, int k) {
        int m = board.length, n = board[0].length;
        if (i < 0 || i >= m || j < 0 || j >= n) {
            return false;
        }
        if (board[i][j] == word.charAt(k)) {
            char temp = board[i][j];
            board[i][j] = '#';
            if (k == word.length() - 1) {
                return true;
            } else if (existDFS(board, word, i + 1, j, k + 1) || existDFS(board, word, i - 1, j, k + 1) || existDFS(board, word, i, j + 1, k + 1) || existDFS(board, word, i, j - 1, k + 1)) {
                return true;
            }
            board[i][j] = temp;
        }
        return false;
    }


























    public static void main(String args[]) {
        BackTracking bt = new BackTracking();
        List<String> phoneString = bt.letterCombinations("23");
        for (String s : phoneString) {
            System.out.println(s);
        }
    }
}
