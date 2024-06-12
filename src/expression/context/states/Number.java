package expression.context.states;

public enum Number implements State {
    INT(0), FLT(1);

    Number(int index) {
        this.index = index;
    }

    @Override
    public int position(char c) {
        if(c >= '0' && c <= '9') return 0;
        else if(c == '+' || c == '-' || c == '*' || c == '/' || c == '^' || c == ',') return 1;
        else if(c == '.') return 2;
        else if(c == ')') return 3;
        else return 4;
    }

    @Override
    public State[][] matrix() {
        return matrix;
    }

    @Override
    public int index() {
        return index;
    }

    private final int index;

    private static final State[][] matrix = {
            // number operator . ) else end
            {INT, Operator.OPR,     FLT, Bound.CLS, End.ERR, End.FIN},
            {FLT, Operator.OPR, End.ERR, Bound.CLS, End.ERR, End.FIN}
    };
}
