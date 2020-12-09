package part2.exercise03;

public class Expression {

    @FunctionalInterface
    private interface Evaluator {
        int evaluate();
    }

    private final Evaluator evaluator;

    public Expression(int constant) {
        evaluator = () -> constant;
    }

    public Expression(char operation, Expression left, Expression right) {
        switch (operation) {
            case '+':
                evaluator = () -> left.evaluate() + right.evaluate();
                break;
            case '-':
                evaluator = () -> left.evaluate() - right.evaluate();
                break;
            case '*':
                evaluator = () -> left.evaluate() * right.evaluate();
                break;
            case '/':
                evaluator = () -> left.evaluate() / right.evaluate();
                break;
            default:
                throw new IllegalStateException();
        }
    }

    public int evaluate() {
        return evaluator.evaluate();
    }
}
