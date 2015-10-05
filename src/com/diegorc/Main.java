package com.diegorc;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Main {

    public static void readFile(Path path,List<State> estados,List<Simbolo> language){
        try {

            BufferedReader bf = Files.newBufferedReader(path);

            String data;
            String values[];
            int numberOfStates;

            //First header line - states
            data = bf.readLine();
            values=data.split("\\s+");

            numberOfStates=Integer.parseInt(values[0].replace("#",""));

            for(int i = 1;i <= numberOfStates;i++){
                estados.add(new State(values[i]));
            }

            //Second header line - final states
            data = bf.readLine();
            values=data.split("\\s+");

            numberOfStates=Integer.parseInt(values[0].replace("#",""));

            for(int i = 1;i <= numberOfStates;i++){
                for(Iterator iter = estados.iterator();iter.hasNext();) {
                    State state=(State)iter.next();
                    if (state.getStateName().matches(values[i]))
                        state.setFinal(true);
                }
            }

            //Third header line - Language
            data = bf.readLine();
            values=data.split("\\s+");

            numberOfStates=Integer.parseInt(values[0].replace("#",""));

            for(int i = 1;i <= numberOfStates;i++){
                language.add(new Simbolo(values[i]));
            }

            bf.readLine(); //reads the separator

            //read transitions for each state
            for(int i=0;i<estados.size();i++){
                String inputs[];

                data = bf.readLine();
                values=data.split("#");

                List<Transition> transitions = new LinkedList<>();

                for(int j=0;j<=language.size();j++){
                    inputs=values[j].trim().split("\\s");

                    int counter=0;

                    while(counter < inputs.length){
                        for (State state : estados) {
                            if(state.getStateName().matches(inputs[counter])){
                                if( j != language.size())
                                    transitions.add(new Transition(language.get(j),state));
                                else
                                    transitions.add(new Transition(state));
                            }
                        }
                        counter++;
                    }
                }

                estados.get(i).setTransitions(transitions);

            }

        }catch (Exception e){
            System.err.println("Error al leer el archivo");
            System.err.println(e.toString());
        }
    }

    //comprueba que la cadena que se
    public static boolean checkStringInLanguage(List<Simbolo> language, String cadena){
        boolean success=false;

        for (int i = 0; i < cadena.toCharArray().length; i++) {
            success=false;

            for (int j = 0; j < language.size(); j++) {
                if(String.valueOf(cadena.toCharArray()[i]).matches(language.get(j).get()))
                    success=true;
            }

            if(success == false)
                return success;

        }

        return success;
    }

    //Prints the states of a list
    public static void imprimirEstados(List<State> estados){
        System.out.println("Estados actuales: ");
        for (State estado : estados) {
            System.out.println("\t"+estado.getStateName());
        }
    }

    public static void main(String[] args) {

        List<State> estados= new LinkedList<>();    //supported state list
        List<Simbolo> language= new LinkedList<>(); //list with the simbols of the language

        if(args.length > 1) {
            Path path = Paths.get(args[0]);
            readFile(path,estados,language);

            String cadena=args[1];

            List<State> estadosActuales= new LinkedList<>();    //list with the current states of the automata
            List<State> estadosAuxiliares=new LinkedList<>();   //auxiliar list


            if(checkStringInLanguage(language,cadena) != true) {    //check if the input string forms part of the language
                System.err.println("La cadena introducida contiene elementos que no forman parte del lenguaje");
                return;
            }

            //add the clausure of the initial states to the current states of the automata to initialize the automata
            estadosActuales.addAll(estados.get(0).clausure());
            imprimirEstados(estadosActuales);

            for (char c : cadena.toCharArray()) { //apply the current entry for each state
                System.out.println("Entrada: "+c);  //prints
                for (State esta : estadosActuales) {

                    estadosAuxiliares.addAll(esta.newInput(new Simbolo(Character.toString(c))));

                    //clean repeated states
                    for (int i = 0; i < estadosAuxiliares.size(); i++) {
                        State t=estadosAuxiliares.get(i);
                        for (int j = i+1; j < estadosAuxiliares.size(); j++) {
                            if(t.getStateName().matches(estadosAuxiliares.get(j).getStateName())) {
                                estadosAuxiliares.remove(j);
                                j--;
                            }
                        }
                    }
                }

                //move the states from estadosAuxiliares to estadosActuales
                estadosActuales.removeAll(estadosActuales);

                estadosAuxiliares.forEach(estado ->{
                    estadosActuales.add(estado);
                });

                estadosAuxiliares.removeAll(estadosAuxiliares);

                imprimirEstados(estadosActuales);
            }

        boolean success=false;

        //check if at least one of the remaining states it's a final state
        for (int u = 0; u < estadosActuales.size() && !success; u++) {
            if(estadosActuales.get(u).isFinal())
                success=true;
        }

        if(success) //if at least one is final, the chain is aceppted
            System.out.println("CADENA ACEPTADA");
        else    //if any of the remaining states is final, the chain is rejected
            System.out.println("CADENA RECHAZADA");

        }
    }
}
