package expression.basic;

import expression.Expression;
import expression.Variable;

public abstract class Basic implements Expression {
    public Basic(Expression e) {
        this.e = e;
    }

    @Override
    public Variable[] variables() {
        return e.variables();
    }

    public Expression getExpression() {
        return e;
    }

    protected final Expression e;
}
