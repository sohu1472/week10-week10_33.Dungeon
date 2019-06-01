package dungeon;

import java.util.ArrayList;
import java.util.List;

public class Utils {


    public static List commandToArray(String command) {
        List<Character> moveSet = new ArrayList<Character>();
        for (char c : command.toCharArray()) {
            moveSet.add(c);
        }
        return moveSet;
    }
}
