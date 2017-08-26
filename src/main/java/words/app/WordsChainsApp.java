package words.app;

import words.solver.WordsChainsSolver;

import java.io.Console;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.UTF_8;

public class WordsChainsApp {

    private static final String EXIT_CHAR = "y";
    private static final String SAME_LENGTH_VIOLATION_MSG = "Words should have same length";
    private static final String WORDS_LENGTH_VIOLATION_MSG = "Words should have length >= 2";
    private static final String WORD_NOT_FOUND_MSG = "Word '%s' not found in the dictionary";

    private Set<String> dictionary;
    private WordsChainsSolver wordsChainsSolver;

    public WordsChainsApp(Set<String> dictionary){
        this.dictionary = dictionary;
        this.wordsChainsSolver = new WordsChainsSolver(dictionary);
    }

    public void run() {
        Console console;
        String from, to;
        console = System.console();
        boolean exit = false;
        while (!exit){
            from = console.readLine("Start word: ").trim();
            to = console.readLine("Target word: ").trim();
            if(validateInput(from, to)){
                print(wordsChainsSolver.solve(from, to).toString());
            }
            exit = EXIT_CHAR.equalsIgnoreCase(console.readLine("Exit ? (y/n) : ").trim());
        }
    }

    private final boolean validateInput(String from, String to){
        if(from.length() != to.length()){
            print(SAME_LENGTH_VIOLATION_MSG);
            return false;
        }
        if(from.length() < 2){
            print(WORDS_LENGTH_VIOLATION_MSG);
            return false;
        }
        if(!dictionary.contains(from)){
            print(String.format(WORD_NOT_FOUND_MSG, from));
            return false;
        }
        if(!dictionary.contains(to)){
            print(String.format(WORD_NOT_FOUND_MSG, to));
            return false;
        }
        return true;
    }

    private void print(String msg){
        System.out.println(msg);
    }
}
