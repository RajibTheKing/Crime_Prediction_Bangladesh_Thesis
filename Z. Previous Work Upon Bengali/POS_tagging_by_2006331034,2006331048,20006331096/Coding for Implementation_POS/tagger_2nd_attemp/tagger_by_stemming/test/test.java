
import test_potter.GuessPoS;

public class test {

    public static void main(String[] args) {
        GuessPoS p = new GuessPoS();
        if (p.init()) {
            String s = p.guessPoS("বাজানো");
            System.out.println("+++" +s);
        }

    }
}
