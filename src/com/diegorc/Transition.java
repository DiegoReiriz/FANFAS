package com.diegorc;

/**
 * Created by Diego Reiriz Cores on 1/10/15.
 */
public class Transition {
    private boolean emptyTransition; //It's an empty transition
    private Simbolo input;          //input needed for the transition
    private State transition;       //state obtained from the transition

    public Simbolo getInput() {
        return this.input;
    }

    public State getTransition() {
        return this.transition;
    }

    public boolean isEmptyTransition() {
        return this.emptyTransition;
    }

    public Transition(State transition) {
        this.emptyTransition = true;
        this.transition = transition;
    }

    public Transition(Simbolo input, State transition) {
        this.emptyTransition = false;
        this.input = input;
        this.transition = transition;
    }

}
