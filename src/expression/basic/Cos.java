package expression.basic;

import expression.Constant;
import expression.Expression;
import expression.Variable;
import expression.combination.Prod;

import java.util.Map;

public class Cos extends Basic{
    public Cos(Expression e) {
        super(e);
    }

    @Override
    public Expression apply(Map<Variable, ? extends Expression> m) {
        return new Cos(e.apply(m));
    }

    @Override
    public double value() {
        return Math.cos(e.value());
    }

    @Override
    public double directValue(Map<Variable, Double> m) {
        return Math.cos(e.directValue(m));
    }

    @Override
    public Expression diff(Variable v) {
        return new Neg(new Prod(new Sin(e), e.diff(v)));
    }

    @Override
    public Expression simplify() {
        Expression s = e.simplify();
        if(s instanceof Constant c && c.value() != 0) {
            return new Constant(Math.cos(c.value()));
        }
        return new Cos(s);
    }

    @Override
    public String toString() {
        return "cos(" + e + ")";
    }
}
