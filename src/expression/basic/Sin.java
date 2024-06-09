package expression.basic;

import expression.Constant;
import expression.Expression;
import expression.Variable;
import expression.combination.Prod;

import java.util.Map;

public class Sin extends Basic{
    public Sin(Expression e) {
        super(e);
    }

    @Override
    public Expression apply(Map<Variable, ? extends Expression> m) {
        return new Sin(e.apply(m));
    }

    @Override
    public double value() {
        return Math.sin(e.value());
    }

    @Override
    public double directValue(Map<Variable, Double> m) {
        return Math.sin(e.directValue(m));
    }

    @Override
    public Expression diff(Variable v) {
        return new Prod(new Cos(e), e.diff(v));
    }

    @Override
    public Expression simplify() {
        Expression s = e.simplify();
        if(s instanceof Constant c && c.value() != 0) {
            return new Constant(Math.sin(c.value()));
        }
        return new Sin(s);
    }

    @Override
    public String toString() {
        return "sin(" + e + ")";
    }
}
