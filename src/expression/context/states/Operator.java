package expression.context.states;

public enum Operator implements State {
    OPR(0);

    Operator(int index) {
        this.index = index;
    }

    @Override
    public int position(char c) {
        if(c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z') return 0;
        else if(c >= '0' && c <= '9') return 1;
        else if(c == '(') return 2;
        else return 3;
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
            // char number ( else end
            {Name.CHR, Number.INT, Bound.OPN, End.ERR, End.ERR}
    };
}
