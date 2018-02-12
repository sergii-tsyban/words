package words.solver;

import java.util.List;
import java.util.Map;

public interface WordsChainsSolver {

    Map<Integer, List<String>> solve(String from, String to);

}
