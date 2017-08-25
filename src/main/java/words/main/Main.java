package words.main;

import words.app.WordsChainsApp;

public class Main {

    public static void main(String[] args) {
        WordsChainsApp solverApp = new WordsChainsApp(args.length > 0 ? args[0] : "dictionary.txt");
        solverApp.process();
    }
}
