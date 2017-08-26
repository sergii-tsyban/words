package words.solver;

import java.util.*;
import java.util.stream.Collectors;

public class WordsChainsSolver {

    public static final int MAX_STEPS = 6;
    public static final int MAX_RESULTS = 3;

    private Map<Integer, Set<String>> lengthSeparatedDictionary = new HashMap<>();

    public WordsChainsSolver(Set<String> dictionary){
        separateByLength(dictionary);
    }

    public TreeMap<Integer, List<String>> solve(String from, String to){
        TaskToSolve taskToSolve = new TaskToSolve(from, to, lengthSeparatedDictionary.get(from.length()));
        taskToSolve.solve();
        return taskToSolve.stacks;
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

    private void separateByLength(Set<String> dictionary) {
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

    private static class TaskToSolve {

        TreeMap<Integer, List<String>> stacks = new TreeMap<>();
        String from;
        String to;
        Set<String> dictionary;

        public TaskToSolve(String from, String to, Set<String> dictionary) {
            this.from = from;
            this.to = to;
            this.dictionary = dictionary;
        }

        void solve(){
            LinkedList<String> wordsStack = new LinkedList<>();
            wordsStack.add(from);
            solveRecur(wordsStack);
        }

        boolean solveRecur(LinkedList<String> wordsStack){
            if(wordsStack.size() >= MAX_STEPS){
                // maximum number of steps reached
                return false;
            }
            List<String> nextWords = findWordsDifferByOneLetter(wordsStack.getLast());
            nextWords.removeAll(wordsStack);
            for (String nextWord : nextWords) {
                wordsStack.add(nextWord);
                if(nextWord.equals(to)){
                    stacks.put(wordsStack.size(), new LinkedList<>(wordsStack));
                    wordsStack.removeLast();
                    return stacks.size() == MAX_RESULTS;
                }
                if(solveRecur(wordsStack)){
                    return true;
                }
                wordsStack.removeLast();
            }
            return false;
        }

        List<String> findWordsDifferByOneLetter(String last) {
            return dictionary.stream()
                    .filter(w -> wordsDiff(last, w) == 1)
                    .collect(Collectors.toList());
        }
    }
}
