package expression.basic;

import expression.Constant;
import expression.Expression;
import expression.Variable;
import expression.combination.Div;
import expression.combination.Pow;

import java.util.Map;

public class Tan extends Basic{
    public Tan(Expression e) {
        super(e);
    }

    @Override
    public Expression apply(Map<Variable, ? extends Expression> m) {
        return new Tan(e.apply(m));
    }

    @Override
    public double value() {
        return Math.tan(e.value());
    }

    @Override
    public double directValue(Map<Variable, Double> m) {
        return Math.tan(e.directValue(m));
    }

    @Override
    public Expression diff(Variable v) {
        return new Div(e.diff(v), new Pow(new Cos(e), new Constant(2)));
    }

    @Override
    public Expression simplify() {
        Expression s = e.simplify();
        if(s instanceof Constant c && c.value() != 0) {
            return new Constant(Math.tan(c.value()));
        }
        return new Tan(s);
    }

    @Override
    public String toString() {
        return "tan(" + e + ")";
    }
}
