package words.main;

import words.app.WordsChainsApp;
import words.solver.WordsChainsSolverImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Main {

    private static final String EMPTY_DICTIONARY_MSG = "Dictionary file is empty.";
    private static final String NON_EMPTY_DICTIONARY_MSG = "Found '%d' words in dictionary";
    private static final String LOADING_DICTIONARY_MSG = "Loading dictionary ...";
    private static final String CANNOT_READ_DICTIONARY_MSG = "Cannot read dictionary file: ";
    private static final String DICTIONARY_FILE_PATH_REQUIRED_MSG = "Dictionary file path required.";

    public static void main(String[] args) {
        if(args.length < 1){
            printToConsole(DICTIONARY_FILE_PATH_REQUIRED_MSG);
            return;
        }
        Set<String> dictionary = loadDictionary(args[0]);
        if(dictionary.isEmpty()){
            printToConsole(EMPTY_DICTIONARY_MSG);
            return;
        }
        printToConsole(String.format(NON_EMPTY_DICTIONARY_MSG, dictionary.size()));
        WordsChainsApp solverApp = new WordsChainsApp(dictionary, new WordsChainsSolverImpl(dictionary));
        solverApp.run();
    }

    private static Set<String> loadDictionary(String path) {
        printToConsole(LOADING_DICTIONARY_MSG);
        try(Stream<String> stream = Files.lines(Paths.get(path), UTF_8)) {
            return stream.filter(line -> !line.trim().isEmpty())
                    .filter(line -> line.matches("^[^A-Z].*$")) //omit own names
                    .collect(Collectors.toSet());
        } catch (IOException e){
            printToConsole(String.format(CANNOT_READ_DICTIONARY_MSG, e.getMessage()));
        }
        return Collections.EMPTY_SET;
    }

    private static void printToConsole(String msg){
        System.out.println(msg);
    }

}
