package words.solver;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;

public class WordsSolverTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private String[] dictionary = {"lead", "cog", "soon", "at", "goad", "gold", "dog", "query", "ruby", "rubs", "dog", "load", "robs", "rods", "rode", "code", "cat"};

    @Test
    public void testSolveSuccess(){
        WordsSolver wordsSolver = new WordsSolver(dictionary);
        List<String> chain = wordsSolver.solve("ruby", "code");
        Assert.assertEquals(Arrays.asList("ruby", "rubs", "robs", "rods", "rode", "code"), chain);
    }

    @Test
    public void testSolveFailure(){
        WordsSolver wordsSolver = new WordsSolver(dictionary);
        List<String> chain = wordsSolver.solve("ruby", "soon");
        Assert.assertTrue(chain.isEmpty());
    }

    @Test
    public void testSolveDifferentWordsLength(){
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Words should have same length");
        WordsSolver wordsSolver = new WordsSolver(dictionary);
        wordsSolver.solve("ruby", "query");
    }

    @Test
    public void testSolveFromTooSmaLL(){
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Words should have length >= 2");
        WordsSolver wordsSolver = new WordsSolver(dictionary);
        wordsSolver.solve("r", "r");
    }

}
