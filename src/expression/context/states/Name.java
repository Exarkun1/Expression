package expression.context.states;

public enum Name implements State{
    CHR(0), NUM(1);

    Name(int index) {
        this.index = index;
    }

    @Override
    public int position(char c) {
        if(c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z') return 0;
        else if(c >= '0' && c <= '9') return 1;
        else if(c == '+' || c == '-' || c == '*' || c == '/' || c == '^' || c == ',') return 2;
        else if(c == '(') return 3;
        else if(c == ')') return 4;
        else return 5;
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
            // char number operator ( ) else end
            {CHR,     NUM, Operator.OPR, Bound.OPN, Bound.CLS, End.ERR, End.FIN},
            {End.ERR, NUM, Operator.OPR, Bound.OPN, Bound.CLS, End.ERR, End.FIN}
    };
}
