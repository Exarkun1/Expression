package expression.combination;

import expression.Constant;
import expression.Expression;
import expression.Variable;

import java.util.Map;

public class Prod extends Combination {
    public Prod(Expression e1, Expression e2) {
        super(e1, e2);
    }

    @Override
    public Expression apply(Map<Variable, ? extends Expression> m) {
        return new Prod(e1.apply(m), e2.apply(m));
    }

    @Override
    public double value() {
        return e1.value() * e2.value();
    }

    @Override
    public double directValue(Map<Variable, Double> m) {
        return e1.directValue(m) * e2.directValue(m);
    }

    @Override
    public Expression diff(Variable v) {
        return new Sum(new Prod(e1.diff(v), e2), new Prod(e1, e2.diff(v)));
    }

    @Override
    public Expression simplify() {
        Expression s1 = e1.simplify();
        Expression s2 = e2.simplify();
        if(s1 instanceof Constant c1 && s2 instanceof Constant c2) {
            return new Constant(c1.value() * c2.value());
        } else if(s1 instanceof Constant c1 && c1.value() == 0 || s2 instanceof Constant c2 && c2.value() == 0) {
            return new Constant(0);
        } else if(s1 instanceof Constant c1 && c1.value() == 1) {
            return s2;
        } else if(s2 instanceof Constant c2 && c2.value() == 1) {
            return s1;
        }
        return new Prod(s1, s2);
    }

    @Override
    public String toString() {
        return "(" + e1 + "*" + e2 + ")";
    }
}
