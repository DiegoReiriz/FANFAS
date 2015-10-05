package com.diegorc;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Diego Reiriz Cores on 1/10/15.
 */
public class State {
    private String stateName;               //Name of the state
    private List<Transition> transitions;   //Transitions from this state
    private boolean end;                    //It's a final state?

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

    public List<State> clausure(){  //gets a list with the states forming the closure of the state
        List clausura=new LinkedList<>();
        clausura.add(this);

        transitions.forEach(transition -> {
            if(transition.isEmptyTransition())
                clausura.add(transition.getTransition());
        });

        return clausura;
    }

    public List<State> newInput(Simbolo input){         //uses the current symbol to transit to other states
        List<State> newStates=new LinkedList<>();
                                                        //verifies that the transition is not an empty transition and
                                                        // that the entry of the transition coincides with the transition received
        transitions.forEach(transition -> {
            if (!transition.isEmptyTransition() && transition.getInput().get().matches(input.get()))
                newStates.add(transition.getTransition());  //add the state obtained from the transition to a list
        });



        for (int i = 0; i < newStates.size(); i++) {
            State t=newStates.get(i);
            newStates.addAll(t.clausure()); //add the closure of each new state to the list

            //remove the repeated states
            boolean first = false;
            for (int j = 0; j < newStates.size(); j++) {
                if(t.getStateName().matches(newStates.get(j).getStateName())) {
                    if(!first)
                        first=true;
                    else {
                        newStates.remove(j);
                        j--;
                    }
                }
            }
        }

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
