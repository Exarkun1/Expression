package expression.combination;

import expression.Constant;
import expression.Expression;
import expression.Variable;

import java.util.Map;

public class Pow extends Combination {
    public Pow(Expression e1, Expression e2) {
        super(e1, e2);
    }

    @Override
    public Expression apply(Map<Variable, ? extends Expression> m) {
        return new Pow(e1.apply(m), e2.apply(m));
    }

    @Override
    public double value() {
        return Math.pow(e1.value(), e2.value());
    }

    @Override
    public double directValue(Map<Variable, Double> m) {
        return Math.pow(e1.directValue(m), e2.directValue(m));
    }

    @Override
    public Expression diff(Variable v) {
        if(e2 instanceof Constant c2) {
            return new Prod(new Prod(c2, new Pow(e1, new Constant(c2.value()-1))), e1.diff(v));
        } else if (e2 instanceof Variable v2 && !v.equals(v2)) {
            return new Prod(new Prod(v2, new Pow(e1, new Sub(v2, new Constant(1)))), e1.diff(v));
        } else if(e1 instanceof Constant c1) {
            return new Prod(new Prod(this, new Constant(Math.log(c1.value()) / Math.log(Math.E))), e2.diff(v));
        } else if (e1 instanceof Variable v1 && !v.equals(v1)) {
            return new Prod(new Prod(this, new Log(new Constant(Math.E), v1)), e2.diff(v));
        } else throw new RuntimeException("diff");
    }

    @Override
    public Expression simplify() {
        Expression s1 = e1.simplify();
        Expression s2 = e2.simplify();
        if(s1 instanceof Constant c1 && s2 instanceof Constant c2) {
            return new Constant(Math.pow(c1.value(), c2.value()));
        } else if(s2 instanceof Constant c2 && c2.value() == 0) {
            return new Constant(1);
        } else if(s2 instanceof Constant c2 && c2.value() == 1) {
            return s1;
        } else if(s1 instanceof Constant c1 && (c1.value() == 0 || c1.value() == 1)) {
            return s1;
        }
        return new Pow(s1, s2);
    }

    @Override
    public String toString() {
        return "(" + e1 + "^" + e2 + ")";
    }
}
