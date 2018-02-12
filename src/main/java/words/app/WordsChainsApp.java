package words.app;

import words.solver.WordsChainsSolver;

import java.io.Console;
import java.util.Set;

public class WordsChainsApp {

    private static final String EXIT_CHAR = "y";
    private static final String SAME_LENGTH_VIOLATION_MSG = "Words should have same length";
    private static final String WORDS_LENGTH_VIOLATION_MSG = "Words should have length >= 2";
    private static final String WORD_NOT_FOUND_MSG = "Word '%s' not found in the dictionary";

    private Set<String> dictionary;
    private WordsChainsSolver wordsChainsSolver;

    public WordsChainsApp(Set<String> dictionary, WordsChainsSolver wordsChainsSolver){
        this.dictionary = dictionary;
        this.wordsChainsSolver = wordsChainsSolver;
    }

    public void run() {
        Console console;
        String from, to;
        console = System.console();
        boolean exit = false;
        while (!exit){
            from = console.readLine("Start word: ").trim();
            to = console.readLine("Target word: ").trim();
            if(validInput(from, to)){
                printToConsole(wordsChainsSolver.solve(from, to).toString());
            }
            exit = EXIT_CHAR.equalsIgnoreCase(console.readLine("Exit ? (y/n) : ").trim());
        }
    }

    private final boolean validInput(String from, String to){
        if(from.length() != to.length()){
            printToConsole(SAME_LENGTH_VIOLATION_MSG);
            return false;
        }
        if(from.length() < 2){
            printToConsole(WORDS_LENGTH_VIOLATION_MSG);
            return false;
        }
        if(!dictionary.contains(from)){
            printToConsole(String.format(WORD_NOT_FOUND_MSG, from));
            return false;
        }
        if(!dictionary.contains(to)){
            printToConsole(String.format(WORD_NOT_FOUND_MSG, to));
            return false;
        }
        return true;
    }

    private void printToConsole(String msg){
        System.out.println(msg);
    }
}
