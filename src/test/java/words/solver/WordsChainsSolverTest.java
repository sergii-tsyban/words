package words.solver;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WordsChainsSolverTest {

    private Set<String> dictionary = Stream.of(
            "lead", "cog", "soon", "at", "goad", "rat", "rag", "dag",
            "gold", "dog", "query", "ruby", "rubs", "dog", "load",
            "robs", "rods", "rode", "code", "cat", "cot", "rad")
            .collect(Collectors.toSet());

    @Test
    public void testSolveSuccess() {
        WordsChainsSolver wordsChainsSolver = new WordsChainsSolverImpl(dictionary);
        Map<Integer, List<String>> chain = wordsChainsSolver.solve("ruby", "code");
        Assert.assertTrue(chain.size() == 1);
        //get chain with size 6
        Assert.assertEquals(Arrays.asList("ruby", "rubs", "robs", "rods", "rode", "code"), chain.get(6));
    }

    @Test
    public void testSolveSuccessMultiple() {
        //"cat", "cot", "cog", "dog"
        //"cat", "rat", "rag", "dag", "dog"
        //"cat", "rat", "rad", "rag", "dag", "dog"
        WordsChainsSolver wordsChainsSolver = new WordsChainsSolverImpl(dictionary);
        Map<Integer, List<String>> chains = wordsChainsSolver.solve("cat", "dog");
        Assert.assertEquals(3, chains.size());
        //get chain with size 4
        Assert.assertEquals(Arrays.asList("cat", "cot", "cog", "dog"), chains.get(4));
        //get chain with size 5
        Assert.assertEquals(Arrays.asList("cat", "rat", "rag", "dag", "dog"), chains.get(5));
        //get chain with size 6
        Assert.assertEquals(Arrays.asList("cat", "rat", "rad", "rag", "dag", "dog"), chains.get(6));
    }

    @Test
    public void testSolveFailure() {
        WordsChainsSolver wordsChainsSolver = new WordsChainsSolverImpl(dictionary);
        Assert.assertTrue(wordsChainsSolver.solve("ruby", "soon").isEmpty());
    }

    @Test
    public void testSolveSameWords() {
        WordsChainsSolver wordsChainsSolver = new WordsChainsSolverImpl(dictionary);
        Assert.assertTrue(wordsChainsSolver.solve("ruby", "ruby").isEmpty());
    }

    @Test
    public void testSolveNonExistingWords() {
        WordsChainsSolver wordsChainsSolver = new WordsChainsSolverImpl(dictionary);
        Assert.assertTrue(wordsChainsSolver.solve("robe", "cute").isEmpty());
    }

}
