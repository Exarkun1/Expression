package expression;

import java.util.Map;
import java.util.Objects;

public class Variable implements Expression {
    public Variable(String name) {
        this.name = name;
    }

    @Override
    public Expression apply(Map<Variable, ? extends Expression> m) {
        return m.containsKey(this) ? m.get(this) : this;
    }

    @Override
    public Variable[] variables() {
        return new Variable[]{this};
    }

    @Override
    public double value() {
        throw new RuntimeException("value");
    }

    @Override
    public double directValue(Map<Variable, Double> m) {
        if(m.containsKey(this)) {
            return m.get(this);
        } else throw new RuntimeException("directValue");
    }

    @Override
    public Expression diff(Variable v) {
        return v.equals(this) ? new Constant(1) : new Constant(0);
    }

    @Override
    public Expression simplify() {
        return this;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Variable variable = (Variable) o;
        return Objects.equals(name, variable.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    public String name() {
        return name;
    }

    private final String name;
}
