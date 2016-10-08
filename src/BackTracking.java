import java.lang.reflect.Array;
import java.util.*;

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

    /** Word Search II: Given a 2D board and a list of words from the dictionary, find all words in the board.

     Each word must be constructed from letters of sequentially adjacent cell, where "adjacent" cells are those
     horizontally or vertically neighboring. The same letter cell may not be used more than once in a word.

     For example,
     Given words = ["oath","pea","eat","rain"] and board =

     [
     ['o','a','a','n'],
     ['e','t','a','e'],
     ['i','h','k','r'],
     ['i','f','l','v']
     ]
     Return ["eat","oath"].
     */
    public List<String> findWords(char[][] board, String[] words) {
        List<String> result = new ArrayList<>();
        if (board == null || board.length == 0 || board[0].length == 0 || words == null || words.length == 0) {
            return result;
        }
        TrieTree tree = new TrieTree();
        for (String s : words) {
            tree.insert(s);
        }
        int m = board.length, n = board[0].length;
        boolean[][] visited = new boolean[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                findWordsHelper(board, i, j, tree, "", result, visited);
            }
        }
        return result;
    }

    private void findWordsHelper(char[][] board, int i, int j, TrieTree tree, String s, List<String> result, boolean[][] visited) {

        int[] dx = {0, 0, -1, 1};
        int[] dy = {-1, 1, 0, 0};
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length || visited[i][j]) {
            return;
        }
        System.out.print(i);
        System.out.println(j);
        String curr = s + board[i][j];
        if (!tree.startWith(curr)) {
            return;
        }
        if (tree.find(curr) && !result.contains(curr)) {
            result.add(curr);
        }
        visited[i][j] = true;
        for (int x = 0; x < 4; x++) {
                findWordsHelper(board, i + dx[x], j + dy[x], tree, curr, result, visited);
        }
        visited[i][j] = false;
    }

    /**
     * Permutations: Given a collection of distinct numbers, return all possible permutations.
     * @param args
     */
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> results = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return results;
        }
        List<Integer> list = new ArrayList<>();
        permuteDFS(nums, list, results);
        return results;
    }
    private void permuteDFS(int[] nums, List<Integer> list, List<List<Integer>> results) {
        if (list.size() == nums.length) {
            results.add(new ArrayList<>(list));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (list.contains(nums[i])) {
                continue;
            }
            list.add(nums[i]);
            permuteDFS(nums, list, results);
            list.remove(list.size() - 1);
        }
    }

    /**
     * Permutations II: Given a collection of numbers that might contain duplicates, return all possible unique
     * permutations.

     For example,
     [1,1,2] have the following unique permutations:
     [
     [1,1,2],
     [1,2,1],
     [2,1,1]
     ]
     */

    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> results = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return results;
        }
        Arrays.sort(nums);
        List<Integer> list = new ArrayList<>();
        boolean[] visited = new boolean[nums.length];
        permuteIIDFS(nums, list, results, visited);
        return results;
    }
    private void permuteIIDFS(int[] nums, List<Integer> list, List<List<Integer>> results, boolean[] visited) {
        if (list.size() == nums.length) {
            results.add(new ArrayList<>(list));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (visited[i] || (i != 0 && nums[i] == nums[i - 1] && !visited[i - 1])) {
                continue;
            }
            list.add(nums[i]);
            visited[i] = true;
            permuteIIDFS(nums, list, results, visited);
            list.remove(list.size() - 1);
            visited[i] = false;
        }
    }

    /**
     * Permutation Sequence: The set [1,2,3,…,n] contains a total of n! unique permutations.

     By listing and labeling all of the permutations in order,
     We get the following sequence (ie, for n = 3):

     "123"
     "132"
     "213"
     "231"
     "312"
     "321"
     Given n and k, return the kth permutation sequence.
     */
    public String getPermutation(int n, int k) {
        if (n == 0) {
            return "";
        }
        boolean[] visited = new boolean[n + 1];
        int[] factorial = new int[n + 1];
        factorial[0] = 1;
        for (int i = 1; i <= n; i++) {
            factorial[i] = factorial[i - 1] * i;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = n; i >= 1; i--) {
            //最高位可以取{1, 2, 3, 4}，而每个数重复3! = 6次。所以第k=9个permutation的s[0]为{1, 2, 3, 4}中的第8/6+1 = 2个数字s[0] = 2。
            int index =  (k - 1) / factorial[i - 1] + 1;
            int value = 0, count = 0;
            for (int j = 1; j <= n; j++) {
                if (!visited[j]) {
                    count++;
                    if (count == index) {
                        value = j;
                        break;
                    }
                }
            }
            sb.append(String.valueOf(value));
            visited[value] = true;
            // 第六个数字仍是第二位排第六所以如果k刚好等于factorial【i- 1】不应该置0，而应该维持原来的k
            k = k % factorial[i - 1] == 0 ? factorial[i - 1] : (k % factorial[i - 1]);
        }
        return sb.toString();
    }

    /**
     * Generate Parentheses: Given n pairs of parentheses, write a function to generate all combinations of well-formed
     * parentheses.
     * @param n
     * @return
     */

    public List<String> generateParenthesis(int n) {
        List<String> results = new ArrayList<>();
        if (n <= 0) {
            return results;
        }
        generateParenthesisHelper(results, "", n, n);
        return results;
    }
    private void generateParenthesisHelper(List<String> results, String s, int left, int right) {
        if (left == 0 && right == 0) {
            results.add(s);
            return;
        }
        if (left > 0) {
            generateParenthesisHelper(results, s + "(", left - 1, right);

        }
        if (right > 0 && right > left) {
            generateParenthesisHelper(results, s + ")", left, right - 1);
        }
    }

    /**
     * Word Break II: Given a string s and a dictionary of words dict, add spaces in s to construct a sentence where
     * each word is a valid dictionary word.

     Return all such possible sentences.

     For example, given
     s = "catsanddog",
     dict = ["cat", "cats", "and", "sand", "dog"].

     A solution is ["cats and dog", "cat sand dog"].
     * @param args
     */
     // DP + BackTracking: First use DP to find each breaking point and attach an ArrayList of string to then end
    //position, then do the back tracking from the end of the DP result;
    public List<String> wordBreak(String s, Set<String> wordDict) {
        List<String> result = new ArrayList<>();
        if (s == null || s.length() == 0 || wordDict == null || wordDict.size() == 0) {
            return result;
        }
        ArrayList<String> dp[] = new ArrayList[s.length() + 1];
        dp[0] = new ArrayList<String>();
        for (int i = 0; i < s.length(); i++) {
            if (dp[i] == null) {
                continue;
            }
            for (String str : wordDict) {
                int len = str.length();
                if (i + len > s.length()) {
                    continue;
                }
                if (s.substring(i, i + len).equals(str)) {
                    if (dp[i + len] == null) {
                        dp[i + len] = new ArrayList<String>();
                    }
                    dp[i + len].add(str);
                }
            }
        }
        if (dp[s.length()] == null) {
            return result;
        }
        ArrayList<String> temp = new ArrayList<String>();
        wordBreakBT(result, dp, temp, s.length());
        return result;
    }
    private void wordBreakBT(List<String> result, ArrayList<String>[] dp, ArrayList<String> temp, int end) {
        if (end == 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = temp.size() - 1; i > 0; i--) {
                sb.append(temp.get(i));
                sb.append(" ");
            }
            sb.append(temp.get(0));
            result.add(sb.toString());
        }
        for (String str : dp[end]) {
            temp.add(str);
            wordBreakBT(result, dp, temp, end - str.length());
            temp.remove(temp.size() - 1);
        }
    }

    /**
     * Combination sum: Given a set of candidate numbers (C) and a target number (T), find all unique combinations in C
     * where the candidate numbers sums to T.

     The same repeated number may be chosen from C unlimited number of times.
     * @param candidates
     * @param target
     * @return
     */

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        if (candidates == null || candidates.length == 0) {
            return result;
        }
        List<Integer> temp = new ArrayList<>();
        combinationSumBT(candidates, target, 0, temp, result);
        return result;
    }
    private void combinationSumBT(int[] candidates, int target, int index, List<Integer> temp, List<List<Integer>> result) {
        if (target == 0) {
            result.add(new ArrayList<Integer>(temp));
        }
        if (target < 0) {
            return;
        }
        for (int i = index; i < candidates.length; i++) {
            temp.add(candidates[i]);
            combinationSumBT(candidates, target - candidates[i], i, temp, result);
            temp.remove(temp.size() - 1);
        }
    }

    /**
     * Combination Sum II: Given a collection of candidate numbers (C) and a target number (T), find all unique combinations in C where the candidate numbers sums to T.

     Each number in C may only be used once in the combination.
     */
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        if (candidates == null || candidates.length == 0) {
            return result;
        }
        Arrays.sort(candidates);
        List<Integer> temp = new ArrayList<>();
        combinationSum2Helper(candidates, target, 0, temp, result);
        return result;
    }
    private void combinationSum2Helper(int[] candidates, int target, int index, List<Integer> temp, List<List<Integer>> result) {
        if (target == 0) {
            result.add(new ArrayList<Integer>(temp));
            return;
        } else if (target < 0) {
            return;
        }
        for (int i = index; i < candidates.length; i++) {
            if (target < candidates[i] || (i != index && candidates[i] == candidates[i - 1])) {
                continue;
            } else {
                temp.add(candidates[i]);
                combinationSum2Helper(candidates, target - candidates[i], i + 1, temp, result);
                temp.remove(temp.size() - 1);
            }
        }
    }

    /**
     * Combination Sum III: Find all possible combinations of k numbers that add up to a number n, given that only
     * numbers from 1 to 9 can be used and each combination should be a unique set of numbers.
     * @param k
     * @param n
     * @return
     */

    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> result = new ArrayList<>();
        if (k > n) {
            return result;
        }
        List<Integer> temp = new ArrayList<>();
        combinationSum3Helper(result, temp, k, n, 1);
        return result;
    }
    private void combinationSum3Helper(List<List<Integer>> result, List<Integer> temp, int k, int n, int index) {
        if (temp.size() == k && n == 0) {
            result.add(new ArrayList<Integer>(temp));
            return;
        } else if (temp.size() > k || n < 0) {
            return;
        }
        for (int i = index; i <= 9; i++) {
            temp.add(i);
            combinationSum3Helper(result, temp, k, n - i, i + 1);
            temp.remove(temp.size() - 1);
        }
    }




    /**
     * subsets: Given a set of distinct integers, nums, return all possible subsets.
     * @param args
     */
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return result;
        }
        List<Integer> list = new ArrayList<>();
        subsetsHelper(nums, result, list, 0);
        return result;
    }
    private void subsetsHelper(int[] nums, List<List<Integer>> result, List<Integer> list, int index) {
        result.add(new ArrayList<>(list));
        for (int i = index; i < nums.length; i++) {
            list.add(nums[i]);
            subsetsHelper(nums, result, list, i + 1);
            list.remove(list.size() - 1);
        }
    }

    /**
     * Subsets II: Given a collection of integers that might contain duplicates, nums, return all possible subsets.

     For example,
     If nums = [1,2,2], a solution is:

     [
     [2],
     [1],
     [1,2,2],
     [2,2],
     [1,2],
     []
     ]
     */
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return result;
        }
        Arrays.sort(nums);
        List<Integer> list = new ArrayList<>();
        subsetsII(nums, result, list, 0);
        return result;
    }
    private void subsetsII(int[] nums, List<List<Integer>> result, List<Integer> list, int index) {
        result.add(new ArrayList<Integer>(list));
        for (int i = index; i < nums.length; i++) {
            if (i != index && nums[i] == nums[i - 1]) {
                continue;
            }
            list.add(nums[i]);
            subsetsII(nums, result, list, i + 1);
            list.remove(list.size() - 1);
        }
    }


    /**
     * Word Ladder II: Given two words (start and end), and a dictionary, find all shortest transformation sequence(s)
     * from start to end, such that:

     Only one letter can be changed at a time
     Each intermediate word must exist in the dictionary
     Example
     Given:
     start = "hit"
     end = "cog"
     dict = ["hot","dot","dog","lot","log"]
     Return
     [
     ["hit","hot","dot","dog","cog"],
     ["hit","hot","lot","log","cog"]
     ]
     * @param start
     * @param end
     * @param dict
     * @return
     */

    public List<List<String>> findLadders(String start, String end, Set<String> dict) {
        // write your code here
        List<List<String>> result = new ArrayList<>();
        if (dict == null || dict.size() == 0 || start == null || end == null || start.length() == 0 || start.length() != end.length()) {
            return result;
        }
        // a map between current and the corresponding list of previous String
        HashMap<String, List<String>> map = new HashMap<>();
        HashMap<String, Integer> distance = new HashMap<>();
        dict.add(start);
        dict.add(end);
        // bfs construct map from curr to previous list of string and map from string to distance
        wordLadderBfs(map, distance, start, end, dict);
        List<String> path = new ArrayList<>();
        // dfs construct path
        wordLadderDfs(result, map, distance, start, end, path);
        return result;
    }
    private void wordLadderDfs(List<List<String>> result, HashMap<String, List<String>> map, HashMap<String, Integer> distance, String start, String curr, List<String> path) {
        path.add(curr);
        // curr is the current string that we are deal with. our search started from end.
        if (curr.equals(start)) {
            Collections.reverse(path);
            result.add(new ArrayList<String>(path));
            Collections.reverse(path);
        } else {
            for (String s : map.get(curr)) {
                if (distance.containsKey(s) && distance.get(s) + 1 == distance.get(curr)) {
                    wordLadderDfs(result, map, distance, start, s, path);
                }
            }
        }
        path.remove(path.size() - 1);
    }
    private void wordLadderBfs(HashMap<String, List<String>> map, HashMap<String, Integer> distance, String start, String end, Set<String> dict) {
        Queue<String> queue = new LinkedList<>();
        queue.offer(start);
        distance.put(start, 0);
        for (String s : dict) {
            map.put(s, new ArrayList<String>());
        }
        while (!queue.isEmpty()) {
            String s = queue.poll();
            List<String> list = expand(s, dict);
            for (String next : list) {
                map.get(next).add(s);
                if (!distance.containsKey(next)) {
                    distance.put(next, distance.get(s) + 1);
                    queue.offer(next);
                }
            }
        }
    }
    private List<String> expand(String s, Set<String> dict) {
        List<String> list = new ArrayList<>();
        char[] arr = s.toCharArray();
        for (int i = 0; i < arr.length; i++) {
            char temp = arr[i];
            for (char ch = 'a'; ch <= 'z'; ch++) {
                arr[i] = ch;
                String str = new String(arr);
                if (dict.contains(str)) {
                    list.add(str);
                }
            }
            arr[i] = temp;
        }
        return list;
    }

    /**
     * Gray Code: when n = 2: 00   01   11   10  (10   11   01  00) second part is the reverse of the first part
     *            when n = 3 000  001  011  010  110  111  101 100
     * @param args
     */
    public List<Integer> grayCode(int n) {
        List<Integer> code = new ArrayList<Integer>();
        if (n == 0) {
            code.add(0);
            return code;
        }
        code = grayCode(n - 1);
        int size = code.size();
        for (int i = size - 1; i >= 0; i--) {
            code.add(code.get(i) + (1 << (n - 1)));
        }
        return code;
    }

    /**
     * Combinations: Given two integers n and k, return all possible combinations of k numbers out of 1 ... n.

     For example,
     If n = 4 and k = 2, a solution is:

     [
     [2,4],
     [3,4],
     [2,3],
     [1,2],
     [1,3],
     [1,4],
     ]
     * @param args
     */
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();
        if (n == 0 || k == 0 || k > n) {
            return result;
        }
        List<Integer> list = new ArrayList<>();
        combineDfs(n, k, result, list, 1);
        return result;
    }
    private void combineDfs(int n, int k, List<List<Integer>> result, List<Integer> list, int index) {
        if (list.size() == k) {
            result.add(new ArrayList<Integer>(list));
            return;
        }
        for (int i = index; i <= n; i++) {
            if (list.contains(i)) {
                continue;
            }
            list.add(i);
            combineDfs(n, k, result, list, i + 1);
            list.remove(list.size() - 1);
        }
    }

    /**
     * Restore IP Addresses: Given a string containing only digits, restore it by returning all possible valid IP
     * address combinations.

     For example:
     Given "25525511135",

     return ["255.255.11.135", "255.255.111.35"]. (Order does not matter)
     * @param s
     * @return
     */

    public List<String> restoreIpAddresses(String s) {
        List<String> ipList = new ArrayList<>();
        if (s == null || s.length() < 4) {
            return ipList;
        }
        List<String> temp = new ArrayList<>();
        restoreIP(s, ipList, temp);
        return ipList;
    }
    private void restoreIP(String s, List<String> ipList, List<String> temp) {
        if (temp.size() == 4 && s.length() == 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(temp.get(0));
            for (int i = 1; i < temp.size(); i++) {
                sb.append(".");
                sb.append(temp.get(i));
            }
            ipList.add(sb.toString());
            return;
        } else if (temp.size() < 4 && s.length() == 0) {
            return;
        } else if (temp.size() == 4 && s.length() != 0) {
            return;
        }
        for (int i = 1; i <= 3 && i <= s.length(); i++) {
            String curr = s.substring(0,i);
            if (curr.charAt(0) == '0' && !curr.equals("0") || Integer.parseInt(curr) > 255) {
                return;
            }
            temp.add(curr);
            restoreIP(s.substring(i), ipList, temp);
            temp.remove(temp.size() - 1);
        }
    }

    /**
     * N Queens:  QuestionEditorial Solution  My Submissions
     Total Accepted: 64107
     Total Submissions: 229079
     Difficulty: Hard
     The n-queens puzzle is the problem of placing n queens on an n×n chessboard such that no two queens attack
     each other. (既不在一条直线上也不在一条对角线上)
     Given an integer n, return all distinct solutions to the n-queens puzzle.

     Each solution contains a distinct board configuration of the n-queens' placement, where 'Q' and '.' both indicate a queen and an empty space respectively.

     For example,
     There exist two distinct solutions to the 4-queens puzzle:

     [
     [".Q..",  // Solution 1
     "...Q",
     "Q...",
     "..Q."],

     ["..Q.",  // Solution 2
     "Q...",
     "...Q",
     ".Q.."]
     ]
     * @param args
     */
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> result = new ArrayList<>();
        int[] position = new int[n];
        generatePosition(position, 0, result);
        return result;
    }
    private void generatePosition(int[] position, int index, List<List<String>> result) {
        if (index == position.length) {
            ArrayList<String> temp = translate(position);
            result.add(temp);
            return;
        }
        // index是行标也是Queen计数， position[index]是列标, 针对每一行, 从左到右扫描
        for (int i = 0; i < position.length; i++) {
            if (isValid(position, index, i)) {
                position[index] = i;
                generatePosition(position, index + 1, result);
                position[index] = 0;
            }
        }
    }
    private ArrayList<String> translate(int[] position) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < position.length; i++) {
            char[] array = new char[position.length];
            for (int j = 0; j < position.length; j++) {
                if (j == position[i]) {
                    array[j] = 'Q';
                } else {
                    array[j] = '.';
                }
            }
            list.add(new String(array));
        }
        return list;
    }
    private boolean isValid(int[] position, int rowNum, int colNum) {
        for (int i = 0; i < rowNum; i++) {
            if (position[i] == colNum) {
                return false;
            }
            if (Math.abs(i - rowNum) == Math.abs(position[i] - colNum)) {
                return false;
            }
        }
        return true;
    }
    /**
     * N-Queens II: Follow up for N-Queens problem.

     Now, instead outputting board configurations, return the total number of distinct solutions.

     */

    public int totalNQueens(int n) {
        if (n == 0) {
            return 0;
        }
        int[] position = new int[n];
        int[] totalCount = new int[1];
        generateNQueens(position, 0, totalCount);
        return totalCount[0];
    }
    private void generateNQueens(int[] position, int num, int[] totalCount) {
        if (num == position.length) {
            totalCount[0]++;
            return;
        }
        for (int i = 0; i < position.length; i++) {
            if (isValid(position, num, i)) {
                position[num] = i;
                generateNQueens(position, num + 1, totalCount);
                position[num] = 0;
            }
        }
    }
    /**
     * Palindrome Partitioning: Given a string s, partition s such that every substring of the partition is a palindrome.

     Return all possible palindrome partitioning of s.
     * @param args
     */

    public List<List<String>> partition(String s) {
        // write your code here
        List<List<String>> result = new ArrayList<>();
        if (s == null || s.length() == 0) {
            return result;
        }
        List<String> list = new ArrayList<>();
        partitionHelper(s, result, list, 0);
        return result;
    }
    private void partitionHelper(String s, List<List<String>> result, List<String> list, int index) {
        if (index == s.length()) {
            result.add(new ArrayList<String>(list));
            return;
        }
        for (int i = index + 1; i <= s.length(); i++) {
            if (!isPalindrome(s.substring(index, i))) {
                continue;
            }
            list.add(s.substring(index,i));
            partitionHelper(s, result, list, i);
            list.remove(list.size() - 1);
        }
    }
    private boolean isPalindrome(String s) {
        int start = 0, end = s.length() - 1;
        while (start <= end) {
            if (s.charAt(start) != s.charAt(end)) {
                return false;
            }
            start++;
            end--;
        }
        return true;
    }

    /**
     * Sudoku Solver: Write a program to solve a Sudoku puzzle by filling the empty cells.

     Empty cells are indicated by the character '.'
     * @param args
     */

    public void solveSudoku(char[][] board) {
        if (board == null || board.length != 9 || board[0].length != 9) {
            return;
        }
        solveSudokuHelper(board);
    }
    private boolean solveSudokuHelper(char[][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == '.') {
                    for (char k = '1'; k <= '9'; k++) {
                        if (isValidSudoku(board, i, j, k)) {
                            board[i][j] = k;
                            if (solveSudokuHelper(board)) {
                                return true;
                            } else {
                                board[i][j] = '.';
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }
    private boolean isValidSudoku(char[][] board, int row, int col, char k) {
        for (int i = 0; i < board.length; i++) {
            if (board[i][col] == k || board[row][i] == k) {
                return false;
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[row/3*3+ i][col/3*3 + j] == k) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Generalized Abbreviation: Write a function to generate the generalized abbreviations of a word.

     Example:
     Given word = "word", return the following list (order does not matter):
     ["word", "1ord", "w1rd", "wo1d", "wor1", "2rd", "w2d", "wo2", "1o1d", "1or1", "w1r1", "1o2", "2r1", "3d", "w3", "4"]
     * @param args
     */
    public List<String> generateAbbreviations(String word) {
        List<String> result = new ArrayList<>();
        if (word == null) {
            return result;
        }
        result.add(word);
        generateHelper(word, result, 0);
        return result;
    }
    private void generateHelper(String word, List<String> result, int index) {
        if (index >= word.length()) {
            return;
        }
        for (int i = index; i < word.length(); i++) {
            for (int j = 1; i + j <= word.length(); j++) {
                String num = String.valueOf(j);
                StringBuilder abbv = new StringBuilder();
                abbv.append(word.substring(0, i));
                abbv.append(num);
                abbv.append(word.substring(i + j, word.length()));
                result.add(abbv.toString());
                generateHelper(abbv.toString(), result, i + num.length() + 1);
            }
        }
    }

    /**
     * Word Pattern II: Given a pattern and a string str, find if str follows the same pattern.

     Here follow means a full match, such that there is a bijection between a letter in pattern and a non-empty
     substring in str.

     Examples:
     pattern = "abab", str = "redblueredblue" should return true.
     pattern = "aaaa", str = "asdasdasdasd" should return true.
     pattern = "aabb", str = "xyzabcxzyabc" should return false.
     * @param pattern
     * @param str
     * @return
     */
    public boolean wordPatternMatch(String pattern, String str) {
        if (str.length() < pattern.length()) {
            return false;
        }
        HashMap<Character, String> map = new HashMap<>();
        HashSet<String> set = new HashSet<>();
        return matchHelper(pattern, str, map, set, 0, 0);
    }
    private boolean matchHelper(String pattern, String str, HashMap<Character, String> map, HashSet<String> set, int pindex, int sindex) {
        if (pindex == pattern.length() && sindex == str.length()) {
            return true;
        }
        if (pindex >= pattern.length() || sindex >= str.length()) {
            return false;
        }
        char c = pattern.charAt(pindex);
        for (int j = sindex + 1; j <= str.length(); j++) {
            String sub = str.substring(sindex, j);
            if (!set.contains(sub) && !map.containsKey(c)) {
                set.add(sub);
                map.put(c, sub);
                if (matchHelper(pattern, str, map, set, pindex + 1, j)) {
                    return true;
                }
                map.remove(c);
                set.remove(sub);
            } else if (map.containsKey(c) && map.get(c).equals(sub)) {
                if (matchHelper(pattern, str, map, set, pindex + 1, j)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Factor Combinations: Numbers can be regarded as product of its factors. For example,

     8 = 2 x 2 x 2;
     = 2 x 4.
     Write a function that takes an integer n and return all possible combinations of its factors.

     Note:
     You may assume that n is always positive.
     Factors should be greater than 1 and less than n.
     * @param args
     */
    public List<List<Integer>> getFactors(int n) {
        List<List<Integer>> factors = new ArrayList<>();
        if (n <= 1) {
            return factors;
        }
        List<Integer> temp = new ArrayList<>();
        factorHelper(n, temp, factors, 1, 2);
        return factors;
    }
    private void factorHelper(int n, List<Integer> temp, List<List<Integer>> factors, int num, int index) {
        if (num == n) {
            factors.add(new ArrayList<Integer>(temp));
            return;
        }
        for (int i = index; i <= n / index; i++) {
            if (n % i != 0) {
                continue;
            }
            if (i * num <= n) {
                temp.add(i);
                factorHelper(n, temp, factors, i * num, i);
                temp.remove(temp.size() - 1);
            } else {
                return;
            }
        }
    }


    /**
     * Binary Watch: A binary watch has 4 LEDs on the top which represent the hours (0-11), and the 6 LEDs on the bottom represent the minutes (0-59).

     Each LED represents a zero or one, with the least significant bit on the right.
     * @param num
     * @return
     */



    public List<String> readBinaryWatch(int num) {
        List<String> time = new ArrayList<>();
        if (num < 0 || num > 8) {
            return time;
        }
        for (int i = 0; i < 12; i++) {
            int hour = getCount(i);
            for (int j = 0; j < 60; j++) {
                int minute = getCount(j);
                if (hour + minute == num) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(String.valueOf(i)).append(":");
                    if (j < 10) {
                        sb.append("0");
                    }
                    sb.append(String.valueOf(j));
                    time.add(sb.toString());
                }
            }
        }
        return time;
    }

    private int getCount(int i) {
        int count = 0;
        while (i > 0) {
            if ((i & 1) == 1) {
                count++;
            }
            i >>= 1;
        }
        return count;
    }

    /**
     * Flip Game II: You are playing the following Flip Game with your friend: Given a string that contains only these two characters: + and -, you and your friend take turns to flip two consecutive "++" into "--". The game ends when a person can no longer make a move and therefore the other person will be the winner.

     Write a function to determine if the starting player can guarantee a win.

     For example, given s = "++++", return true. The starting player can guarantee a win by flipping the middle "++" to become "+--+".
     * @param s
     * @return
     */

    public boolean canWin(String s) {
        if (s == null || s.length() < 2) {
            return false;
        }
        return winHelper(s.toCharArray());
    }
    private boolean winHelper(char[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] == '+' && array[i + 1] == '+') {
                array[i] = '-';
                array[i + 1] = '-';
                boolean lose = winHelper(array);
                array[i] = '+';
                array[i + 1] = '+';
                if (!lose) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Palindrome Permutation II: Given a string s, return all the palindromic permutations (without duplicates) of it. Return an empty list if no palindromic permutation could be form.

     For example:

     Given s = "aabb", return ["abba", "baab"].

     Given s = "abc", return [].
     * @param s
     * @return
     */

    public List<String> generatePalindromes(String s) {
        List<String> palindrome = new ArrayList<>();
        if (s == null || s.length() == 0) {
            return palindrome;
        }
        int odd = 0;
        HashMap<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            map.put(c, map.containsKey(c) ? map.get(c) + 1 : 1);
            odd += map.get(c) % 2 != 0 ? 1 : -1;
        }
        if (odd > 1) {
            return palindrome;
        }
        List<Character> list = new ArrayList<>();
        String mid = "";
        for (Map.Entry<Character,Integer> entry : map.entrySet()) {
            char key = entry.getKey();
            int val = entry.getValue();
            if (val % 2 != 0) {
                mid += key;
            }
            for (int i = 0; i < val / 2; i++) {
                list.add(key);
            }
        }
        boolean[] visited = new boolean[list.size()];
        StringBuilder sb = new StringBuilder();
        generatePalindromeHelper(list, palindrome, sb, visited, mid);
        return palindrome;
    }
    public void generatePalindromeHelper(List<Character> list, List<String> palindrome, StringBuilder sb, boolean[] visited, String single) {
        if (sb.length() == list.size()) {
            palindrome.add(sb.toString() + single + sb.reverse().toString());
            sb.reverse();
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            if (visited[i] || (i != 0 && list.get(i) == list.get(i - 1) && !visited[i - 1])) {
                continue;
            }
            visited[i] = true;
            sb.append(list.get(i));
            generatePalindromeHelper(list, palindrome, sb, visited, single);
            sb.deleteCharAt(sb.length() - 1);
            visited[i] = false;
        }
    }

    /**
     * Android Unlock Pattern: Given an Android 3x3 key lock screen and two integers m and n, where 1 ≤ m ≤ n ≤ 9, count the total number of unlock patterns of the Android lock screen, which consist of minimum of m keys and maximum n keys.

     Rules for a valid pattern:
     Each pattern must connect at least m keys and at most n keys.
     All the keys must be distinct.
     If the line connecting two consecutive keys in the pattern passes through any other keys, the other keys must have previously selected in the pattern. No jumps through non selected key is allowed.
     The order of keys used matters.
     * @param args
     */
    public int numberOfPatterns(int m, int n) {
        int[][] skip = new int[10][10];
        skip[1][3] = skip[3][1] = 2;
        skip[1][7] = skip[7][1] = 4;
        skip[3][9] = skip[9][3] = 6;
        skip[7][9] = skip[9][7] = 8;
        skip[1][9] = skip[9][1] = skip[2][8] = skip[8][2] = skip[3][7] = skip[7][3] = skip[4][6] = skip[6][4] = 5;
        boolean[] visited = new boolean[10];
        int result = 0;
        // DFS search each length from m to n
        for (int i = m; i <= n; i++) {
            result += DFS(visited, skip, 1, i - 1) * 4;//1,3,7,9 symmetric
            result += DFS(visited, skip, 2, i - 1) * 4;//2,4,6,8 symmetric
            result += DFS(visited, skip, 5, i - 1);
        }
        return result;
    }
    private int DFS(boolean[] visited, int[][] skip, int curr, int step) {
        if (step < 0) {
            return 0;
        }
        if (step == 0) {
            return 1;
        }
        visited[curr] = true;
        int result = 0;
        for (int i = 1; i <= 9; i++) {
            if (!visited[i] && (skip[curr][i] == 0 || visited[skip[curr][i]])) {
                result += DFS(visited, skip, i, step - 1);
            }
        }
        visited[curr] = false;
        return result;
    }



    public static void main(String args[]) {
        BackTracking bt = new BackTracking();
//        List<String> phoneString = bt.letterCombinations("23");
//        for (String s : phoneString) {
//            System.out.println(s);
//        }
//        List<String> IP = bt.restoreIpAddresses("010010");
//        char[][] board = {{'a', 'b'}, {'c', 'd'}};
//        String[] words = {"abcd"};
//        List<String> word = bt.findWords(board, words);
        List<List<Integer>> list = bt.getFactors(12);
        List<String> time = bt.readBinaryWatch(1);
        List<String> permutation = bt.generatePalindromes("a");
        System.out.print(permutation.size());
        for (int i = 0; i < permutation.size(); i++) {
            System.out.println(permutation.get(i));
        }

    }
}
