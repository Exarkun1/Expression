package expression.context.states;

public enum End implements State{
    ERR(0), FIN(1);

    End(int index) {
        this.index = index;
    }

    @Override
    public int position(char c) {
        return 0;
    }

    @Override
    public State[][] matrix() {
        return null;
    }

    @Override
    public int index() {
        return index;
    }

    private final int index;
}
