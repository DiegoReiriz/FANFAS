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
            System.err.println(e.toString());
        }
    }

    public static void main(String[] args) {

        List<State> estados= new LinkedList<>();
        List<Simbolo> language= new LinkedList<>();

        //TODO: if(args.length > 1) {
            //TODO: Path path = Paths.get(args[0]);
            Path path = Paths.get("./assets/Definicion.txt");
            readFile(path,estados,language);

            //TODO: String cadena=args[1]
            String cadena= "01";

            List<State> estadosActuales= new LinkedList<>();
            List<State> estadosAuxiliares=new LinkedList<>();

            estadosActuales.addAll(estados.get(0).clausure());

            for (char c : cadena.toCharArray()) {
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

                estadosActuales.removeAll(estadosActuales);
                estadosAuxiliares.forEach(estado ->{
                    estadosActuales.add(estado);
                });
                estadosAuxiliares.removeAll(estadosAuxiliares);
            }

            boolean success=false;

        for (int u = 0; u < estadosActuales.size() && !success; u++) {
            if(estadosActuales.get(u).isFinal())
                success=true;
        }

        if(success)
            System.out.println("CADENA ACEPTADA");
        else
            System.out.println("CADENA RECHAZADA");

            System.out.println("LISTO");
        //TODO:}
    }
}
