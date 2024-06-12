package expression.context;

import expression.context.states.Bound;
import expression.context.states.End;
import expression.context.states.Start;
import expression.context.states.State;

public class ExpressionAnalyser {
    public boolean analyseExpression(String text) {
        State state = Start.STR;
        int counter = 0;
        for(var c : text.toCharArray()) {
            state = state.matrix()[state.index()][state.position(c)];

            if(state == Bound.OPN) counter++;
            if(state == Bound.CLS) counter--;

            if (state == End.ERR || counter < 0) return false;
        }
        return counter == 0 && state.matrix()[state.index()][state.matrix()[0].length-1] == End.FIN;
    }
}
