import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class spellPredic<String> implements Predicate<String> {

    /**
     * Evaluates this predicate on the given argument.
     *
     * @param s the input argument
     *
     * @return {@code true} if the input argument matches the predicate, otherwise {@code false}
     */

    @Override
    public boolean test(String s) {

        return false;
    }

    public List<String> rs = new ArrayList<>();


    public List<String> filter(List<String> list, Predicate<String> predicate) {


        List<String> result = list;

        for (String word : result) {
            word = backSpace(word);

            if (predicate.test(word)) {
                rs.add(word);
            }
        }

        return rs;
    }


    public void setRs(List<String> rs) {
        this.rs = rs;
    }


    private String backSpace(String wo) {
        return (String) wo.toString().replaceAll(" ", "");
    }
}
