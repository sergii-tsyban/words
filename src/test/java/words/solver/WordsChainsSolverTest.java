package words.solver;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WordsChainsSolverTest {

    private String[] words = {
            "lead", "cog", "soon", "at", "goad", "rat", "rag", "dag",
            "gold", "dog", "query", "ruby", "rubs", "dog", "load",
            "robs", "rods", "rode", "code", "cat", "cot","rad"};
    private Set<String> dictionary = Stream.of(words).collect(Collectors.toSet());

    @Test
    public void testSolveSuccess(){
        WordsChainsSolver wordsChainsSolver = new WordsChainsSolver(dictionary);
        List<String> chain = wordsChainsSolver.solve("ruby", "code").firstEntry().getValue();
        Assert.assertEquals(Arrays.asList("ruby", "rubs", "robs", "rods", "rode", "code"), chain);
    }

    @Test
    public void testSolveSuccessOptimal(){
        //short "cat", "cot", "cog", "dog"
        //long "cat", "rat", "rad", "rag", "dag", "dog"
        WordsChainsSolver wordsChainsSolver = new WordsChainsSolver(dictionary);
        List<String> chain = wordsChainsSolver.solve("cat", "dog").firstEntry().getValue();
        Assert.assertEquals(Arrays.asList("cat", "cot", "cog", "dog"), chain);
    }

    @Test
    public void testSolveFailure(){
        WordsChainsSolver wordsChainsSolver = new WordsChainsSolver(dictionary);
        Assert.assertTrue(wordsChainsSolver.solve("ruby", "soon").isEmpty());
    }

    @Test
    public void testSolveSameWords(){
        WordsChainsSolver wordsChainsSolver = new WordsChainsSolver(dictionary);
        Assert.assertTrue(wordsChainsSolver.solve("ruby", "ruby").isEmpty());
    }

    @Test
    public void testSolveNonExistingWords(){
        WordsChainsSolver wordsChainsSolver = new WordsChainsSolver(dictionary);
        Assert.assertTrue(wordsChainsSolver.solve("robe", "cute").isEmpty());
    }

}
