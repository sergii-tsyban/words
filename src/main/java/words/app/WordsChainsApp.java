package words.app;

import words.solver.WordsChainsSolver;

import java.io.Console;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.UTF_8;

public class WordsChainsApp {

    private static final String EXIT_CHAR = "y";
    private static final String CANNOT_READ_DICTIONARY_MSG = "Cannot read dictionary from file '%s'";
    private static final String EMPTY_DICTIONARY_MSG = "Cannot proceed, dictionary is empty";
    private static final String NON_EMPTY_DICTIONARY_MSG = "Found '%d' words in dictionary";
    private static final String LOADING_DICTIONARY_MSG = "Loading dictionary ...";
    private static final String SAME_LENGTH_VIOLATION_MSG = "Words should have same length";
    private static final String WORDS_LENGTH_VIOLATION_MSG = "Words should have length >= 2";

    private String dictionaryPath;

    public WordsChainsApp(String dictionaryPath){
        this.dictionaryPath = dictionaryPath;
    }

    public void process(){
        List<String> dictionary = loadDictionary(dictionaryPath);
        if(dictionary.isEmpty()){
            print(EMPTY_DICTIONARY_MSG);
            return;
        }
        print(String.format(NON_EMPTY_DICTIONARY_MSG, dictionary.size()));
        process(dictionary);
    }

    private void process(List<String> dictionary) {
        WordsChainsSolver wordsChainsSolver = new WordsChainsSolver(dictionary);
        Console console;
        String from, to;
        console = System.console();
        boolean exit = false;
        while (!exit){
            from = console.readLine("Start word: ");
            to = console.readLine("Target word: ");

            if(validateInput(from, to)){
                print(wordsChainsSolver.solve(from, to).toString());
            }

            exit = EXIT_CHAR.equalsIgnoreCase(console.readLine("Exit ? (y/n) : "));
        }
    }

    private List<String> loadDictionary(String filePath) {
        print(LOADING_DICTIONARY_MSG);
        try(Stream<String> stream = Files.lines(Paths.get(filePath), UTF_8)) {
            return stream.filter(line -> !line.trim().isEmpty()).collect(Collectors.toList());
        } catch (IOException e){
            print(String.format(CANNOT_READ_DICTIONARY_MSG, filePath));
        }
        return Collections.EMPTY_LIST;
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
        return true;
    }

    private void print(String msg){
        System.out.println(msg);
    }
}
