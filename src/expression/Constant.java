package expression;

import java.util.Map;
import java.util.Objects;

public class Constant implements Expression {
    public Constant(double value) {
        this.value = value;
    }

    @Override
    public Expression apply(Map<Variable, ? extends Expression> m) {
        return this;
    }

    @Override
    public Variable[] variables() {
        return new Variable[]{};
    }

    @Override
    public double value() {
        return value;
    }

    @Override
    public double directValue(Map<Variable, Double> m) {
        return value;
    }

    @Override
    public Expression diff(Variable v) {
        return value == 0 ? this : new Constant(0);
    }

    @Override
    public Expression simplify() {
        return this;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    private final double value;
}
