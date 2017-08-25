package words.solver;

import java.util.*;

public class WordsChainsSolver {

    public static final int MAX_STEPS = 10;

    private Map<Integer, Set<String>> lengthSeparatedDictionary = new HashMap<>();

    public WordsChainsSolver(List<String> dictionary){
        separateByLength(dictionary);
    }

    public List<String> solve(String from, String to){
        TaskContext taskToSolve = new TaskContext(from, to, lengthSeparatedDictionary.get(from.length()));
        taskToSolve.solve();
        return taskToSolve.wordsStack;
    }

    private static int wordsDiff(String word, String compareTo) {
        int diff = 0;
        for (int i = 0; i < word.length(); i++) {
            if(word.charAt(i) != compareTo.charAt(i)){
                diff++;
            }
        }
        return diff;
    }

    private void separateByLength(List<String> dictionary) {
        for (String word : dictionary) {
            String trimmed = word.trim();
            Integer length = trimmed.length();
            if(length < 2){
                continue;
            }
            lengthSeparatedDictionary.compute(length, (k, v) -> {
                if(v == null){
                    v = new HashSet<>();
                }
                v.add(trimmed);
                return v;
            });
        }
    }

    private static class TaskContext{

        String from;
        String to;
        Set<String> dictionary;
        LinkedList<String> wordsStack = new LinkedList<>();
        boolean solved;

        public TaskContext(String from, String to, Set<String> dictionary) {
            this.from = from;
            this.to = to;
            this.dictionary = dictionary;
            this.wordsStack = new LinkedList<>();
        }

        void solve(){
            wordsStack.add(from);
            solved = solve(wordsStack);
            if(!solved){
                wordsStack.clear();
            }
        }

        boolean solve(LinkedList<String> wordsStack){
            if(wordsStack.size() >= MAX_STEPS){
                // maximum number of steps reached
                return false;
            }
            Set<String> nextWords = findWordsDifferByOneLetter(wordsStack.getLast());
            nextWords.removeAll(wordsStack);
            // check we reach the goal
            if(nextWords.contains(to)){
                wordsStack.add(to);
                return true;
            }
            for (String nextWord : nextWords) {
                wordsStack.add(nextWord);
                if(solve(wordsStack)){
                    return true;
                }
                wordsStack.removeLast();
            }
            // didn't found anything
            return false;
        }

        Set<String> findWordsDifferByOneLetter(String last) {
            Set<String> nextWords = new HashSet<>();
            for (String w : dictionary) {
                int diff = wordsDiff(last, w);
                if(diff == 1){
                    nextWords.add(w);
                }
            }
            return nextWords;
        }
    }

}
