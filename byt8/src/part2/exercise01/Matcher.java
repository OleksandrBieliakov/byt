package part2.exercise01;

public class Matcher {

    public boolean match(int[] expected, int[] actual, int clipLimit, int delta) {
        clipTooLargeValues(actual, clipLimit);
        return sameLength(expected, actual)
                && eachEntryWithinRange(expected, actual, delta);
    }

    private boolean eachEntryWithinRange(int[] expected, int[] actual, int delta) {
        for (int i = 0; i < actual.length; i++)
            if (Math.abs(expected[i] - actual[i]) > delta)
                return false;
        return true;
    }

    private boolean sameLength(int[] expected, int[] actual) {
        return actual.length == expected.length;
    }

    private void clipTooLargeValues(int[] actual, int clipLimit) {
        for (int i = 0; i < actual.length; i++)
            if (actual[i] > clipLimit)
                actual[i] = clipLimit;
    }

}