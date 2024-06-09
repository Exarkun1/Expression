package expression.context;

import expression.Expression;
import expression.Function;
import expression.Variable;
import expression.basic.Cos;
import expression.basic.Neg;
import expression.basic.Sin;
import expression.basic.Tan;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FunctionContext {
    public FunctionContext() {
        putAllVariables(basicVariableNames.toArray(new String[0]));
        functionTable.put("neg", new Function("neg", new Neg(getVariable("x"))));
        functionTable.put("sin", new Function("sin", new Sin(getVariable("x"))));
        functionTable.put("cos", new Function("cos", new Cos(getVariable("x"))));
        functionTable.put("tan", new Function("tan", new Tan(getVariable("x"))));
    }

    public void putFunction(Function f) {
        if(basicFunctionNames.contains(f.name())) {
            throw new RuntimeException("putFunction");
        }
        functionTable.put(f.name(), f);
    }

    public Function getFunction(String name) {
        return functionTable.get(name);
    }

    public boolean containsFunction(String name) {
        return functionTable.containsKey(name);
    }

    public void removeFunction(String name) {
        if(basicFunctionNames.contains(name)) {
            throw new RuntimeException("removeFunction");
        }
        functionTable.remove(name);
    }

    public void putVariable(Variable v) {
        variableTable.put(v.name(), v);
    }

    public void putAllVariables(Variable... vs) {
        for(var v : vs) {
            putVariable(v);
        }
    }

    public Variable putVariable(String name) {
        variableTable.put(name, new Variable(name));
        return variableTable.get(name);
    }

    public void putAllVariables(String... names) {
        for(var name : names) {
            putVariable(name);
        }
    }

    public Variable getVariable(String name) {
        return variableTable.get(name);
    }

    public boolean containsVariable(String name) {
        return variableTable.containsKey(name);
    }

    public void removeVariable(String name) {
        if(basicVariableNames.contains(name)) {
            throw new RuntimeException("removeVariable");
        }
        variableTable.remove(name);
    }

    public Expression of(String expression) {
        return expressionBuilder.buildExpression(expression);
    }

    public Function of(String name, Expression e) {
        Function f = new Function(name, e);
        putFunction(f);
        putAllVariables(f.expression().variables());
        return f;
    }

    public Function of(String name, String expression) {
        return of(name, of(expression));
    }

    private final Map<String, Function> functionTable = new HashMap<>();
    private final Map<String, Variable> variableTable = new HashMap<>();
    private final ExpressionBuilder expressionBuilder = new ExpressionBuilder(this);
    private final Set<String> basicFunctionNames = Set.of("neg", "sin", "cos", "tan");
    private final Set<String> basicVariableNames = Set.of("x", "y", "z");
}
