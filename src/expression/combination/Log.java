package expression.combination;

import expression.Constant;
import expression.Expression;
import expression.Variable;

import java.util.Map;

public class Log extends Combination {
    public Log(Expression e1, Expression e2) {
        super(e1, e2);
    }

    @Override
    public Expression apply(Map<Variable, ? extends Expression> m) {
        return new Log(e1.apply(m), e2.apply(m));
    }

    @Override
    public double value() {
        return Math.log(e2.value()) / Math.log(e1.value());
    }

    @Override
    public double directValue(Map<Variable, Double> m) {
        return Math.log(e2.directValue(m)) / Math.log(e1.directValue(m));
    }

    @Override
    public Expression diff(Variable v) {
        if(e1 instanceof Constant c1) {
            return new Prod(new Div(new Constant( Math.log(Math.E) / Math.log(c1.value())), e2), e2.diff(v));
        } else if(e1 instanceof Variable v1 && !v1.equals(v)) {
            return new Prod(new Div(new Constant(1), new Prod(new Log(new Constant(Math.E), v1), e2)), e2.diff(v));
        } else throw new RuntimeException("diff");
    }

    @Override
    public Expression simplify() {
        Expression s1 = e1.simplify();
        Expression s2 = e2.simplify();
        if(s1 instanceof Constant c1 && s2 instanceof Constant c2) {
            return new Constant(Math.log(c2.value()) / Math.log(c1.value()));
        } else if(s2 instanceof Constant c2 && c2.value() == 1) {
            return new Constant(0);
        }
        return new Pow(s1, s2);
    }

    @Override
    public String toString() {
        return "log(" + e1 + "," + e2 + ")";
    }
}
