package com.company;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Diego Reiriz Cores on 1/10/15.
 */
public class State {
    private List<Transition> transitions;

    public List<State> newInput(char input){
        List<State> newStates=new LinkedList<State>();
        transitions.forEach(transition -> {
            if (transition.isEmptyTransition() || transition.getInput() == input)
                newStates.add(transition.getTransition());
        });

        return newStates;
    }

    public State(List<State> successors, List<Transition> transitions) {
        this.successors = successors;
        this.transitions = transitions;
    }


}
