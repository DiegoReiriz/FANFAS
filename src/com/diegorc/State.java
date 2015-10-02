package com.diegorc;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Diego Reiriz Cores on 1/10/15.
 */
public class State {
    private String stateName;
    private List<Transition> transitions;
    private boolean end;

    public String getStateName() {
        return stateName;
    }

    public void setFinal(boolean end){
        this.end = end;
    }

    public boolean isFinal(){
        return this.end;
    }

    public void setTransitions(List<Transition> transitions){
        this.transitions=transitions;
    }

    public List<State> newInput(Simbolo input){
        List<State> newStates=new LinkedList<>();
        transitions.forEach(transition -> {
            if (transition.isEmptyTransition() || transition.getInput().get().matches(input.get()))
                newStates.add(transition.getTransition());
        });

        return newStates;
    }

    public State(String name){
        this.stateName = name;
    }

    public State(String name,boolean end){
        this.stateName = name;
        this.end = end;
    }

    public State(String name,boolean end,List<Transition> transitions) {
        this.stateName = name;
        this.end = end;
        this.transitions = transitions;
    }



}
