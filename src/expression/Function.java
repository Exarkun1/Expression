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

    public double value(Variable[] vs, double[] ds) {
        Map<Variable, Double> m = new HashMap<>();
        for(int i = 0; i < vs.length; i++) {
            m.put(vs[i], ds[i]);
        }
        return value(m);
    }

    public double value(double... ds) {
        return value(e.variables(), ds);
    }

    public double valueByName(Map<String, Double> m) {
        Map<Variable, Double> mv = new HashMap<>();
        for(var v : m.entrySet()) {
            mv.put(new Variable(v.getKey()), v.getValue());
        }
        return value(mv);
    }

    public double valueByName(String[] vs, double[] ds) {
        Map<String, Double> m = new HashMap<>();
        for(int i = 0; i < vs.length; i++) {
            m.put(vs[i], ds[i]);
        }
        return valueByName(m);
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
