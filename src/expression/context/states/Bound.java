package expression.context.states;

public enum Bound implements State{
    OPN(0), CLS(1);

    Bound(int index) {
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
            {Name.CHR, Number.INT, End.ERR,      OPN,     End.ERR, End.ERR, End.ERR},
            {End.ERR,  End.ERR,    Operator.OPR, End.ERR, CLS,     End.ERR, End.FIN}
    };
}
