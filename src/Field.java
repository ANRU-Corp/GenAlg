import java.util.ArrayList;
import java.util.List;

/**
 * Created by longman on 31.10.17.
 */
public class Field {
    private boolean constant;
    private int digit;
    private List<Integer> possibleDigits = new ArrayList<>();

    public Field(boolean constant, int digit) {
        this.constant = constant;
        this.digit = digit;

    }

    public Field(boolean constant, int digit, List<Integer> possibleDigits) {
        this(constant,digit);
        this.possibleDigits.addAll(possibleDigits);
    }

    public Field(Field field) {
        this(field.isConstant(),field.getDigit(),field.getPossibleDigits());
    }

    /**
     * interfejs
     *
     */
    @Override
    public String toString() {
        return ""+digit;
    }

    /**
     * chcemy zeby przechowywało mozliwe liczby do wstawienia
     *
     */
    public List<Integer> getPossibleDigits() {
        return possibleDigits;
    }

    public void setPossibleDigits(List<Integer> possibleDigits) {
        this.possibleDigits = possibleDigits;
    }

    public boolean isConstant() {

        return constant;
    }

    public void setConstant(boolean constant) {
        this.constant = constant;
    }

    public int getDigit() {
        return digit;
    }

    public void setDigit(int digit) { if (!this.isConstant()) this.digit = digit;   }
}
