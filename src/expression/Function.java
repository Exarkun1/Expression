package expression;

import java.util.*;

public class Function {
    public Function(String name, Expression e) {
        this.name = name;
        this.e = e;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(name + "(");
        for(var v : e.variables()) {
            sb.append(v.toString()).append(",");
        }
        sb.delete(sb.length()-1, sb.length());
        sb.append(")");
        return sb + "=" + e;
    }

    public double value(Map<Variable, Double> m) {
        return e.directValue(m);
    }

    public Expression expression() {
        return e;
    }

    public String name() { return name; }

    public Expression diff(Variable... v) {
        Expression e = this.e;
        for(var v1 : v) {
            e = e.diff(v1);
        }
        return e;
    }

    public Expression simplifyDiff(Variable... v) {
        return diff(v).simplify();
    }

    private final String name;
    private final Expression e;
}
