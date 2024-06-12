package expression.context;

import expression.*;
import expression.Expression;
import expression.combination.*;

import java.util.*;

public class ExpressionBuilder {
    public ExpressionBuilder(FunctionContext functionContext) {
        this.functionContext = functionContext;
    }

    public Expression buildExpression(String text) {
        text = removeExternalBrackets(text);
        if(isName(text)) {
            if(functionContext.containsVariable(text)) {
                return functionContext.getVariable(text);
            } else {
                return functionContext.putVariable(text);
            }
        } else if(isCount(text)) {
            return new Constant(Double.parseDouble(text));
        } else if(isFunction(text)) {
            Pair p = findStartBodyAndCommas(text);
            return buildExpressionByFunction(text, p.startPos, p.commas);
        } else {
            Expression e = buildSumOrSub(text);
            if(e == null) e = buildProdOrDiv(text);
            if(e == null) e = buildPow(text);
            if(e != null) return e;
        }
        throw new RuntimeException("buildExpression");
    }

    private String removeExternalBrackets(String text) {
        if(text.isEmpty()) return text;
        while (text.charAt(0) == '(' && text.charAt(text.length()-1) == ')') {
            String tempText = text.substring(1, text.length()-1);
            if(!checkSequenceBrackets(tempText)) {
                return text;
            }
            text = tempText;
        }
        return text;
    }

    private boolean checkSequenceBrackets(String text) {
        int counter = 0;
        for(var c : text.toCharArray()) {
            if(c == '(') counter++;
            if(c == ')') counter--;
            if(counter < 0) return false;
        }
        return counter == 0;
    }

    private boolean isFunction(String text) {
        if(isName(text)) return false;
        int counter = 0;
        int startPos = -1;
        char[] symbols = text.toCharArray();
        for(int i = symbols.length-1; i >= 0; i--) {
            if(symbols[i] == ')') counter++;
            if(symbols[i] == '(') counter--;
            if(counter < 0) return false;
            if(counter == 0) {
                startPos = i;
                break;
            }
        }
        return startPos != -1 && isName(text.substring(0, startPos));
    }

    private boolean isName(String text) {
        return text.matches("[A-Za-z]+([0-9]+)?");
    }

    private boolean isCount(String text) {
        return text.matches("[0-9]+((\\.)|(\\.[0-9]+))?");
    }

    private int findOperator(String text, char... operators) {
        int counter = 0;
        char[] symbols = text.toCharArray();
        for(int i = symbols.length-1; i >= 0; i--) {
            if(symbols[i] == ')') counter++;
            if(symbols[i] == '(') counter--;
            if(counter < 0) throw new RuntimeException("findOperator");
            if(counter == 0 && containsOperator(operators, symbols[i])) {
                return i;
            }
        }
        return -1;
    }

    private boolean containsOperator(char[] operators, char operator) {
        for (var op : operators) {
            if(op == operator) {
                return true;
            }
        }
        return false;
    }

    private Pair findStartBodyAndCommas(String text) {
        int counter = 0;
        int startPos = -1;
        List<Integer> commas = new ArrayList<>();
        char[] symbols = text.toCharArray();
        for(int i = symbols.length-1; i >= 0; i--) {
            if(symbols[i] == ')') counter++;
            if(symbols[i] == '(') counter--;
            if(counter < 0) throw new RuntimeException("buildExpression (function)");
            if(counter == 1 && symbols[i] == ',') {
                commas.add(i);
            }
            if(counter == 0) {
                startPos = i;
                break;
            }
        }
        return new Pair(startPos, commas);
    }

    private Expression buildExpressionByFunction(String text, int startPos, List<Integer> commas) {
        Function f = functionContext.getFunction(text.substring(0, startPos));
        if(f == null) {
            throw new RuntimeException("buildExpressionByFunction");
        }
        Map<Variable, Expression> m = new HashMap<>();
        Variable[] vs = f.expression().variables();
        int prevIndex = startPos+1;
        int i = 0;
        for(var index : commas) {
            m.put(vs[i++], buildExpression(text.substring(prevIndex, index)));
            prevIndex = index+1;
        }
        m.put(vs[i], buildExpression(text.substring(prevIndex, text.length()-1)));
        return f.expression().apply(m);
    }

    private Expression buildSumOrSub(String text) {
        int index = findOperator(text, '+', '-');
        if(index != -1) {
            Expression e1 = buildExpression(text.substring(0, index));
            Expression e2 = buildExpression(text.substring(index+1));
            if(text.charAt(index) == '+') {
                return new Sum(e1, e2);
            } else {
                return new Sub(e1, e2);
            }
        }
        return null;
    }

    private Expression buildProdOrDiv(String text) {
        int index = findOperator(text, '*', '/');
        if(index != -1) {
            Expression e1 = buildExpression(text.substring(0, index));
            Expression e2 = buildExpression(text.substring(index+1));
            if(text.charAt(index) == '*') {
                return new Prod(e1, e2);
            } else {
                return new Div(e1, e2);
            }
        }
        return null;
    }

    private Expression buildPow(String text) {
        int index = findOperator(text, '^');
        if(index != -1) {
            Expression e1 = buildExpression(text.substring(0, index));
            Expression e2 = buildExpression(text.substring(index+1));
            return new Pow(e1, e2);
        }
        return null;
    }

    private final FunctionContext functionContext;

    private static class Pair {
        public Pair(int startPos, List<Integer> commas) {
            this.startPos = startPos;
            this.commas = commas;
        }

        public Pair() {}

        public int startPos;

        public List<Integer> commas;
    }
}
