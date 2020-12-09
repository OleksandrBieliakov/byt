package part2.exercise04;

import java.io.IOException;
import java.io.Writer;

public class Person {
    private final String last;

    private final String first;

    private final String middle;

    public Person(String last, String first, String middle) {
        this.last = last;
        this.first = first;
        this.middle = middle;
    }

    @Override
    public String toString() {
        return formatPerson();
    }

    public void display(Writer out) throws IOException {
        out.write(formatPerson());
    }

    public String formatPerson() {
        String result = this.last + ", " + this.first;
        if (this.middle != null)
            result += " " + this.middle;
        return result;
    }

    public void printPerson(Writer out) throws IOException {
        out.write(this.first);
        out.write(" ");
        if (this.middle != null) {
            out.write(this.middle);
            out.write(" ");
        }
        out.write(this.last);
    }
}