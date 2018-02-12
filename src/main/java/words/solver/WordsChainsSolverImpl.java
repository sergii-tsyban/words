package words.solver;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

public class WordsChainsSolverImpl implements WordsChainsSolver{

    private static final int MAX_STEPS = 6;
    private static final int MAX_RESULTS = 3;
    private static final int MIN_WORD_LENGTH = 2;

    private Map<Integer, Set<String>> lengthSeparatedDictionary = new HashMap<>();
    private int maxSteps = MAX_STEPS;
    private int maxResults = MAX_RESULTS;

    public WordsChainsSolverImpl(Set<String> dictionary) {
        separateByLength(dictionary);
    }

    public WordsChainsSolverImpl(Set<String> dictionary, int maxSteps, int maxResults) {
        this(dictionary);
        this.maxSteps = maxSteps;
        this.maxResults = maxResults;
    }

    @Override
    public Map<Integer, List<String>> solve(String from, String to) {
        WordsChainTask wordsChainTask = new WordsChainTask(from, to, lengthSeparatedDictionary.get(from.length()));
        wordsChainTask.solve();
        return wordsChainTask.stacks;
    }

    private void separateByLength(Set<String> dictionary) {
        dictionary.stream()
                .map(String::trim)
                .filter(v -> v.length() >= MIN_WORD_LENGTH)
                .forEach(this::addWordToDictionary);
    }

    private void addWordToDictionary(String word) {
        lengthSeparatedDictionary.compute(word.length(), (k, v) -> {
            v = ofNullable(v).orElse(new HashSet<>());
            v.add(word);
            return v;
        });
    }

    private int wordsDiff(String word, String compareTo) {
        int diff = 0;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) != compareTo.charAt(i)) {
                diff++;
            }
        }
        return diff;
    }

    private class WordsChainTask {

        TreeMap<Integer, List<String>> stacks = new TreeMap<>();
        String from;
        String to;
        Set<String> dictionary;

        public WordsChainTask(String from, String to, Set<String> dictionary) {
            this.from = from;
            this.to = to;
            this.dictionary = dictionary;
        }

        void solve() {
            LinkedList<String> wordsStack = new LinkedList<>();
            wordsStack.add(from);
            solveRecur(wordsStack);
        }

        boolean solveRecur(LinkedList<String> wordsStack) {
            if (wordsStack.size() >= maxSteps) {
                // maximum number of steps reached
                return false;
            }
            List<String> nextWords = findWordsDifferByOneLetter(wordsStack.getLast());
            nextWords.removeAll(wordsStack);
            for (String nextWord : nextWords) {
                wordsStack.add(nextWord);
                if (nextWord.equals(to)) {
                    stacks.put(wordsStack.size(), new LinkedList<>(wordsStack));
                    wordsStack.removeLast();
                    return stacks.size() == maxResults;
                }
                if (solveRecur(wordsStack)) {
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
