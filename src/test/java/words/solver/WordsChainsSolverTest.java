package words.solver;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
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
        WordsChainsSolver wordsChainsSolver = new WordsChainsSolver(dictionary);
        List<String> chain = wordsChainsSolver.solve("ruby", "code").firstEntry().getValue();
        Assert.assertEquals(Arrays.asList("ruby", "rubs", "robs", "rods", "rode", "code"), chain);
    }

    @Test
    public void testSolveSuccessMultiple() {
        //"cat", "cot", "cog", "dog"
        //"cat", "rat", "rag", "dag", "dog"
        //"cat", "rat", "rad", "rag", "dag", "dog"
        WordsChainsSolver wordsChainsSolver = new WordsChainsSolver(dictionary);
        TreeMap<Integer, List<String>> chains = wordsChainsSolver.solve("cat", "dog");
        Assert.assertEquals(3, chains.size());
        Assert.assertEquals(Arrays.asList("cat", "cot", "cog", "dog"), chains.pollFirstEntry().getValue());
        Assert.assertEquals(Arrays.asList("cat", "rat", "rag", "dag", "dog"), chains.pollFirstEntry().getValue());
        Assert.assertEquals(Arrays.asList("cat", "rat", "rad", "rag", "dag", "dog"), chains.pollFirstEntry().getValue());
    }

    @Test
    public void testSolveFailure() {
        WordsChainsSolver wordsChainsSolver = new WordsChainsSolver(dictionary);
        Assert.assertTrue(wordsChainsSolver.solve("ruby", "soon").isEmpty());
    }

    @Test
    public void testSolveSameWords() {
        WordsChainsSolver wordsChainsSolver = new WordsChainsSolver(dictionary);
        Assert.assertTrue(wordsChainsSolver.solve("ruby", "ruby").isEmpty());
    }

    @Test
    public void testSolveNonExistingWords() {
        WordsChainsSolver wordsChainsSolver = new WordsChainsSolver(dictionary);
        Assert.assertTrue(wordsChainsSolver.solve("robe", "cute").isEmpty());
    }

}
