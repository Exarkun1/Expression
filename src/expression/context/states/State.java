package expression.context.states;

public interface State {
    int position(char c);
    State[][] matrix();
    int index();
}
