package expression;

import java.util.Map;

public interface Expression {
    Expression apply(Map<Variable, ? extends Expression> m);
    Variable[] variables();
    double value();
    double directValue(Map<Variable, Double> m);
    Expression diff(Variable v);
    Expression simplify();
}
