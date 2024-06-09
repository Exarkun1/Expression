package expression.combination;

import expression.Expression;
import expression.Variable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class Combination implements Expression {
    public Combination(Expression e1, Expression e2) {
        this.e1 = e1;
        this.e2 = e2;
    }

    @Override
    public Variable[] variables() {
        Variable[] vs1 = e1.variables();
        Variable[] vs2 = e2.variables();
        Set<Variable> vs = new HashSet<>(List.of(vs1));
        vs.addAll(List.of(vs2));
        return vs.toArray(new Variable[0]);
    }

    public Expression getExpression1() {
        return e1;
    }

    public Expression getExpression2() {
        return e2;
    }

    protected final Expression e1;
    protected final Expression e2;
}
