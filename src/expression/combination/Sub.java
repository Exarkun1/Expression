package expression.combination;

import expression.Constant;
import expression.Expression;
import expression.Variable;
import expression.basic.Neg;

import java.util.Map;

public class Sub extends Combination {
    public Sub(Expression e1, Expression e2) {
        super(e1, e2);
    }

    @Override
    public Expression apply(Map<Variable, ? extends Expression> m) {
        return new Sub(e1.apply(m), e2.apply(m));
    }

    @Override
    public double value() {
        return e1.value() - e2.value();
    }

    @Override
    public double directValue(Map<Variable, Double> m) {
        return e1.directValue(m) - e2.directValue(m);
    }

    @Override
    public Expression diff(Variable v) {
        return new Sub(e1.diff(v), e2.diff(v));
    }

    @Override
    public Expression simplify() {
        Expression s1 = e1.simplify();
        Expression s2 = e2.simplify();
        if(s1 instanceof Constant c1 && s2 instanceof Constant c2) {
            return new Constant(c1.value() - c2.value());
        } else if(s2 instanceof Constant c2 && c2.value() == 0) {
            return s1;
        } else if(s2 instanceof Neg n2) {
            return new Sum(s1, n2.getExpression());
        }
        return new Sub(s1, s2);
    }

    @Override
    public String toString() {
        return "(" + e1 + "-" + e2 + ")";
    }
}
