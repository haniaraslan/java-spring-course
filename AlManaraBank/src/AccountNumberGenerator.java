import java.util.HashSet;
import java.util.Set;

public class AccountNumberGenerator {

    private static long counter = 100000000000L;
    private static final Set<String> usedNumbers = new HashSet<>();

    public static synchronized String generate() {
        counter++;
        String acc = String.valueOf(counter);
        if (usedNumbers.contains(acc)) {
            return generate();
        }
        usedNumbers.add(acc);
        return acc;
    }
}
