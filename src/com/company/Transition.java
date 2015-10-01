package com.company;

/**
 * Created by Diego Reiriz Cores on 1/10/15.
 */
public class Transition {
    private boolean emptyTransition;
    private char input;
    private State transition;

    public char getInput() {
        return input;
    }

    public State getTransition() {
        return transition;
    }

    public boolean isEmptyTransition() {
        return emptyTransition;
    }

    public Transition(State transition) {
        this.emptyTransition = true;
        this.transition = transition;
    }

    public Transition(char input, State transition) {
        this.emptyTransition = false;
        this.input = input;
        this.transition = transition;
    }


}
