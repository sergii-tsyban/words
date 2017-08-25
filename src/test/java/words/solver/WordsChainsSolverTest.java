package words.solver;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class WordsChainsSolverTest {

    private List<String> dictionary = Arrays.asList("lead", "cog", "soon", "at", "goad", "gold", "dog", "query", "ruby", "rubs", "dog", "load", "robs", "rods", "rode", "code", "cat");

    @Test
    public void testSolveSuccess(){
        WordsChainsSolver wordsChainsSolver = new WordsChainsSolver(dictionary);
        List<String> chain = wordsChainsSolver.solve("ruby", "code");
        Assert.assertEquals(Arrays.asList("ruby", "rubs", "robs", "rods", "rode", "code"), chain);
    }

    @Test
    public void testSolveFailure(){
        WordsChainsSolver wordsChainsSolver = new WordsChainsSolver(dictionary);
        List<String> chain = wordsChainsSolver.solve("ruby", "soon");
        Assert.assertTrue(chain.isEmpty());
    }

}
