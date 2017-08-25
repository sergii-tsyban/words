package words.solver;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class WordsSolverTest {

    @Test
    public void testSimpleChain(){
        String[] dictionary = {"lead", "cog", "soon", "at", "goad", "gold", "dog", "query", "ruby", "rubs", "dog", "load", "robs", "rods", "rode", "code", "cat"};

        WordsSolver wordsSolver = new WordsSolver(dictionary);
        List<String> chain = wordsSolver.solve("ruby", "code");

        Assert.assertEquals(Arrays.asList("ruby", "rubs", "robs", "rods", "rode", "code"), chain);
    }

}
