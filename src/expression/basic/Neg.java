package expression.basic;

import expression.Constant;
import expression.Expression;
import expression.Variable;

import java.util.Map;

public class Neg extends Basic{
    public Neg(Expression e) {
        super(e);
    }

    @Override
    public Expression apply(Map<Variable, ? extends Expression> m) {
        return new Neg(e.apply(m));
    }

    @Override
    public double value() {
        return -e.value();
    }

    @Override
    public double directValue(Map<Variable, Double> m) {
        return -e.directValue(m);
    }

    @Override
    public Expression diff(Variable v) {
        return new Neg(e.diff(v));
    }

    @Override
    public Expression simplify() {
        Expression s = e.simplify();
        if(s instanceof Constant c && c.value() != 0) {
            return new Constant(-c.value());
        }
        return new Neg(s);
    }

    @Override
    public String toString() {
        return "-" + e;
    }
}
