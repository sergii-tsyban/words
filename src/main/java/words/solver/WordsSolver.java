package words.solver;

import java.util.*;

public class WordsSolver {

    public static final int DEFAULT_MAX_STEPS = 10;

    private Map<Integer, Set<String>> lengthSeparatedDictionary = new HashMap<>();
    private int maxSteps  = DEFAULT_MAX_STEPS;

    public WordsSolver(String[] dictionary){
        separateByLength(dictionary);
    }

    public List<String>  solve(String from, String to){
        if(from.length() != to.length()){
            throw new IllegalArgumentException("Words should have same length");
        }
        if(from.length() < 2){
            throw new IllegalArgumentException("Words should have length >= 2");
        }

        LinkedList<String> wordsStack = new LinkedList<>();
        wordsStack.push(from);
        boolean solved = solve(wordsStack, to, lengthSeparatedDictionary.get(from.length()));
        return wordsStack;
    }

    private boolean solve(Deque<String> wordsStack, String to, Set<String> dictionary){
        if(wordsStack.size() >= DEFAULT_MAX_STEPS){
            return false;
        }
        Set<String> nextWords = findWordsDifferByOneLetter(wordsStack.getLast(), dictionary);
        if(nextWords.contains(to)){
            wordsStack.add(to);
            return true;
        }
        for (String nextWord : nextWords) {
            if(wordsStack.contains(nextWord)){
                continue;
            }
            wordsStack.add(nextWord);
            boolean solved = solve(wordsStack, to, dictionary);
            if(!solved){
                wordsStack.removeLast();
            } else {
                return true;
            }
        }
        return false;
    }

    private Set<String> findWordsDifferByOneLetter(String last, Set<String> dictionary) {
        Set<String> nextWords = new HashSet<>();
        for (String w : dictionary) {
            int diff = diffIgnoreCase(last, w);
            if(diff == 1){
                nextWords.add(w);
            }
        }
        return nextWords;
    }

    private int diffIgnoreCase(String word, String compareTo) {
        int diff = 0;
        for (int i = 0; i < word.length(); i++) {
            if(word.charAt(i) != compareTo.charAt(i)){
                diff++;
            }
        }
        return diff;
    }

    public void setMaxSteps(int maxSteps) {
        if(maxSteps < 1){
            throw new IllegalArgumentException("Max steps cannot be less then 1");
        }
        this.maxSteps = maxSteps;
    }

    private void separateByLength(String[] dictionary) {
        for (String word : dictionary) {
            Integer length = word.length();
            if(length < 2){
                continue;
            }
            lengthSeparatedDictionary.compute(length, (k, v) -> {
                if(v == null){
                    v = new HashSet<>();
                }
                v.add(word);
                return v;
            });
        }
    }

}
