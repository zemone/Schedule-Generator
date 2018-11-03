package model;

import java.util.ArrayList;
import java.util.Scanner;

public class DriverAula {

    public static void mostraopcions(){
        System.out.println("Escull una opcio:");
        System.out.println("1: Crear Aula");
        System.out.println("2: Consultar atributs");
        System.out.println("3: Modificar atributs");
        System.out.println("4: Sortir");
    }


    public static void opcioinavalida(){
        System.out.println("Has escollit una opcio incorrecta.");
        System.out.println("");
    }

    public static Aula creador(Scanner s){
        String edifici,tipusaula;
        int planta, aula;
        System.out.print("Introdueix el nom de l'edifici");
        edifici = s.next();
        System.out.print("Introdueix el la planta en la que es situa l'aula");
        planta = s.nextInt();
        System.out.print("Introdueix el numero de l'aula");
        aula = s.nextInt();
        System.out.print("Introdueix el tipus d'aula");
        tipusaula = s.next();
        ArrayList<Assignacio> assig = crearAssigs(s, aula, tipusaula);
        return new Aula(edifici, planta, aula, tipusaula, assig);
    }

    public static ArrayList<Assignacio> crearAssigs(Scanner s, int aulaG, String tipusaulaG){
        int opt = 1;
        ArrayList<Assignacio> res = new ArrayList<Assignacio>();
        Assignacio assig;
        String dia,tipusaula;
        int hora, aula;
        tipusaula = tipusaulaG;
        aula = aulaG;
        while (opt == 1){
            System.out.print("Introdueix el dia de la assigancio");
            dia = s.next();
            System.out.print("Introdueix la hora de la assigancio");
            hora = s.nextInt();
            System.out.print("Indica amb un 1 si la assignacio es de teoria o 0 si es de laboratori");
            opt = s.nextInt();
            if(opt == 1){
                res.add(new AssignacioT(dia, hora, aula, tipusaula, null, null));
            }
            else {
                res.add(new AssignacioL(dia, hora, aula, tipusaula, null, null));
            }
            System.out.print("Introdueix un 1 si vols afegir una altra assignacio");
            opt = s.nextInt();
        }
        return res;
    }

    public static void mostra(Aula a, Scanner s){
        int opt = 0;
        int aux;
        while(opt != 6){
            System.out.println("Escull que vols consultar.");
            System.out.println("1: Per el nom de l'edifici");
            System.out.println("2: Per la planta");
            System.out.println("3: Per la aula");
            System.out.println("4: Pel tipus d'aula");
            System.out.println("5: Per consultar les assignacions");
            System.out.println("6: per sortir");
            opt = s.nextInt();
            switch(opt){
                case 1:
                    System.out.println(a.getEdifici());
                    break;

                case 2:
                    System.out.println(a.getPlanta());
                    break;

                case 3:
                    System.out.println(a.getAula());
                    break;

                case 4:
                    System.out.println(a.getTipusAula());
                    break;
                case 5:
                    System.out.println("Indica si vols veure totes les assignatures prement 0 o una en concret prement 1.");
                    aux = s.nextInt();
                    ArrayList<Assignacio> assig = a.getAssignacions();
                    switch(aux){
                        case 0:
                            for (int i = 0; i<assig.size() ; ++i){
                                if(AssignacioL.class.equals(assig.get(i))){
                                    System.out.println("Assignació de laboratori en la posicio " + i );
                                }
                                else {
                                    System.out.println("Assignació de teoria en la posicio " + i);
                                }
                                System.out.println("Programada pel dia " + assig.get(i).getDiaSetmana() + " a la hora " + assig.get(i).getHora() );
                            }
                            break;
                        case 1:
                            System.out.println("hi han " + assig.size() + " assignacions, quina vols consultar? (posa el numero)");
                            aux = s.nextInt();
                            if(AssignacioL.class.equals(assig.get(aux))){
                                System.out.println("Assignació de laboratori");
                            }
                            else {
                                System.out.println("Assignació de teoria");
                            }
                            System.out.println("Programada pel dia " + assig.get(aux).getDiaSetmana() + " a la hora " + assig.get(aux).getHora() );
                            break;
                        default:
                            opcioinavalida();
                            break;
                    }
                    break;

                case 6: //sortim
                    break;

                default:
                    opcioinavalida();
                    break;
            }
        }
    }

    public static void modifica (Aula a, Scanner s){
        int opt = 0;
        int aux;
        String auxs;
        while(opt != 6){
            System.out.println("Escull que vols modificar:");
            System.out.println("1: Per el nom de l'edifici");
            System.out.println("2: Per la planta");
            System.out.println("3: Per la aula");
            System.out.println("4: Pel tipus d'aula");
            System.out.println("5: Per modificar les assignacions");
            System.out.println("6: per sortir");
            opt = s.nextInt();
            switch(opt) {
                case 1:
                    System.out.println("Introdueix el nou edifici");
                    auxs = s.next();
                    a.setEdifici(auxs);
                    break;

                case 2:
                    System.out.println("Introdueix la nova planta");
                    aux = s.nextInt();
                    a.setPlanta(aux);
                    break;

                case 3:
                    System.out.println("Introdueix la nova aula");
                    aux = s.nextInt();
                    a.setAula(aux);
                    break;

                case 4:
                    System.out.println("Introdueix el nou tipus d'aula");
                    auxs = s.next();
                    a.setTipusAula(auxs);
                    break;

                case 5:
                    System.out.println("Hi han " + a.getAssignacions().size() + " assignacions");
                    System.out.println("Vols treure una assignacio (0) o modificar (1)");
                    aux = s.nextInt();
                    if (aux == 0) {
                        System.out.println("Indica el numero de la assignacio que vols treure");
                        aux = s.nextInt();
                        if(aux > a.getAssignacions().size() || aux < 0){
                            System.out.println("Posicio invalida");
                        }
                        else{
                            a.getAssignacions().remove(aux);
                        }
                    }
                    else{
                        System.out.println("Indica el numero de la assignacio que vols modificar");
                        aux = s.nextInt();
                        if(aux > a.getAssignacions().size() || aux < 0){
                            System.out.println("Posicio invalida");
                        }
                        else{
                            Assignacio ass = a.getAssignacions().get(aux);
                            a.getAssignacions().remove(aux);
                            aux = 0;
                            int num;
                            while(aux != 4){
                                System.out.println("Indica l'atribut que vols modificar:");
                                System.out.println("1: modifica el dia");
                                System.out.println("2: modifica la hora");
                                System.out.println("3: sortir");
                                aux = s.nextInt();
                                switch (aux){
                                    case 1:
                                        System.out.println("Introdueix el nou dia");
                                        auxs = s.next();
                                        ass.setDiaSetmana(auxs);
                                        break;
                                    case 2:
                                        System.out.println("Introdueix la nova hora");
                                        num = s.nextInt();
                                        ass.setHora(num);
                                        break;
                                    case 3:
                                        break;
                                    default:
                                        opcioinavalida();
                                        break;
                                }
                            }
                            a.getAssignacions().add(ass);
                        }
                    }
                    break;

                case 6:
                    break;
            }

        }
    }

    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        int option = 0;
        boolean creat = false;
        Aula aula;
        while(option != 4){
            mostraopcions();
            option = scan.nextInt();
            switch(option){
                case 1: //creem una aula
                    aula = creador(scan);
                    creat = true;

                case 2: //consultem els atributs
                    if(!creat){
                        System.out.println("Error: no hi ha una Aula creada");
                        System.out.println("");
                    }
                    else{
                        mostra(aula,scan);
                    }
                    break;

                case 3: //modifiquem
                    if(!creat){
                        System.out.println("Error: no hi ha una aula creada");
                        System.out.println("");
                    }
                    else{
                        modifica(aula,scan);
                    }
                    break;

                case 4: //sortim
                    break;

                default:
                    opcioinavalida();
                    break;
            }

        }
    }
}