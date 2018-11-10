package model;

import exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Horari {
    private Assignacio[][][] horari;
    private ArrayList<AssignaturaMonosessio> mishmash;
    //ESTO DE ABAJO VA AQUÍ O NO??
    private ArrayList<Assignatura> assignatures2;
    private ArrayList<Aula> aules2;

    private String fromInt2dia(int dia) {
        if (dia == 0) return "Dilluns";
        else if (dia == 1) return "Dimarts";
        else if (dia == 2) return "Dimecres;";
        else if (dia == 3) return "Dijous";
        else return "Divendres";

    }

    public Assignacio[][][] getHorari() {
        return horari;
    }

    public Horari(HashMap<String, Assignatura> assignatures, HashMap<String, Aula> aules) {
        this.assignatures2 = new ArrayList<>(assignatures.values());
        this.aules2 = new ArrayList<>(aules.values());
        this.horari = new Assignacio[12][5][aules2.size()];
    }

    private void printarHorari_aula(Aula aula) {
        int numAula = assignatures2.indexOf(aula);
        for (int i = 0; i < 12; ++i) {
            for (int j = 0; j < 5; ++j) {
                Assignacio assignacio = horari[i][j][numAula]; //S HAURIA DE PRINTAR AIXO
            }

        }
    }

    public void printarHoraritot() {
        int count= 0;
        for (int i = 0; i < 12; ++i) {
            for (int j = 0; j < 5; ++j) {
                for(int k = 0; k<aules2.size();++k)
                    if(horari[i][j][k] == null) System.out.println("VACÍO");
                    else {
                        ++count;
                        System.out.println("Aula: "+ horari[i][j][k].getAula().getkey() +" es fa assignatura: "+horari[i][j][k].getAssignatura());
                    }
            }
        }
        System.out.println("Se han asignado "+ count+ (" sesiones."));
    }

    private void printarHorari_auladia(Aula aula, String dia) {
        int numAula = assignatures2.indexOf(aula);
        int numdia = fromdia2int(dia);
        for (int i = 0; i < 12; ++i) {
            System.out.println("Aula: "+ horari[i][numdia][numAula].getAula().getkey() +" es fa assignatura: "+horari[i][numdia][numAula].getAssignatura());
        }

    }

    private void printarHorari_aulahora(Aula aula, int hora) {
        int nhora = gethora(hora);
        int numAula = assignatures2.indexOf(aula);
        for (int i = 0; i < 5; ++i) {
            System.out.println("Aula: "+ horari[nhora][i][numAula].getAula().getkey() +" es fa assignatura: "+horari[nhora][i][numAula].getAssignatura());
        }

    }

    public void printarHorari_hora(int hora) {
        int nhora = gethora(hora);
        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < aules2.size(); ++j) {
                System.out.println(horari[nhora][i][j]); // S HAURIA DE PRINTAR AIXO
                System.out.println("Aula: "+ horari[nhora][i][j].getAula().getkey() +" es fa assignatura: "+horari[nhora][i][j].getAssignatura());

            }

        }

    }

    public void printarHorari_hora(String dia) {
        int numdia = fromdia2int(dia);
        for (int i = 0; i < 12; ++i) {
            for (int j = 0; j < aules2.size(); ++j) {
                System.out.println("Aula: "+ horari[i][numdia][j].getAula().getkey() +" es fa assignatura: "+horari[i][numdia][j].getAssignatura());
            }

        }

    }

    private int fromdia2int(String dia) {
        switch (dia) {
            case "Dilluns":
                return 0;
            case "Dimarts":
                return 1;
            case "Dimecres":
                return 2;
            case "Dijous":
                return 3;
            default:
                return 4;
        }
    }


    private int gethora(int hora) {
        if (hora == 0) return 8;
        else if (hora == 1) return 9;
        else if (hora == 2) return 10;
        else if (hora == 3) return 11;
        else if (hora == 4) return 12;
        else if (hora == 5) return 13;
        else if (hora == 6) return 14;
        else if (hora == 7) return 15;
        else if (hora == 8) return 16;
        else if (hora == 9) return 17;
        else if (hora == 10) return 18;
        else return 19;
    }

    private boolean comprovar_restricciones_teoria(int aula1, Grup grup, int dia, int hora, Assignatura assig, int duracio) {
        Aula aula = aules2.get(aula1);
        if (aula.getCapacitat() <= grup.getCapacitat()) {
            System.out.println("La capacitat és insuficient");
            return false;
        }
        for (int i = 0; i < duracio; ++i) {
            if ((hora + i) >= 12){
                System.out.println("Se pasa del horario");
                return false;
            }
            else if (horari[hora + i][dia][aula1] != null) {
                System.out.println("Con la assignatura "+assig.getNom()+" fallo.");
                System.out.println("Ya está puesta la hora "+ (hora+i) + ", el dia "+ fromInt2dia(dia));
                return false;
            }
        }
        return true;
    }


    private boolean comprovar_restricciones_lab(int aula1, Subgrup subgrup, int dia, int hora, Assignatura assig, int duracio) {
        Aula aula = aules2.get(aula1);
        if (aula.getCapacitat() <= subgrup.getCapacitat()) {
            System.out.println("La capacitat és insuficient");
            return false;
        }
        for (int i = 0; i < duracio; ++i) {
            if ((hora + i) >= 12){
                System.out.println("Se pasa del horario");
                return false;
            }
            else if (horari[hora + i][dia][aula1] != null) {
                System.out.println("Con la assignatura "+assig.getNom()+" fallo.");
                System.out.println("Ya está puesta la hora "+ (hora+i) + ", el dia "+ fromInt2dia(dia));
                return false;
            }
        }
        return true;
    }



    private boolean creaHorari(int i, Assignacio[][][] horari) {

        if (i == (mishmash.size())) return true;
        int duracio = mishmash.get(i).getSessio().getDuracioSessions();
        boolean teoria = (mishmash.get(i).getSessio().getClass() == Teoria.class);
        for (int l = 0; l < 5; ++l) {
            for (int m = 0; m < 12; ++m) {
                for (int k = 0; k < aules2.size(); ++k) {
                    if (horari[m][l][k] == null) {
                        if (teoria) {
                            if (comprovar_restricciones_teoria(k, mishmash.get(i).getGrup(), l, m, mishmash.get(i).getAssig(), duracio)) {
                                for (int z = 0; z < duracio; ++z) {
                                    horari[m + z][l][k] = new AssignacioT(fromInt2dia(l), m + z, aules2.get(k), mishmash.get(i).getSessio().gettAula(), mishmash.get(i).getAssig(), mishmash.get(i).getGrup());
                                    System.out.println(mishmash.get(i).getAssig().getNom() + " ficada a les " + gethora(m + z) + " el " + fromInt2dia(l));
                                }
                                if (creaHorari(i + 1, horari)) return true;
                                else {
                                    //no se ha podido hacer, borramos lo que hemos puesto
                                    for (int z = 0; z < duracio; ++z) {
                                        horari[m + z][l][k] = null;
                                    }
                                }
                            }
                        } else {
                            if (comprovar_restricciones_lab(k, mishmash.get(i).getSub(), l, m, mishmash.get(i).getAssig(), duracio)) {
                                for (int z = 0; z < duracio; ++z) {
                                    horari[m + z][l][k] = new AssignacioL(fromInt2dia(l), m + z, aules2.get(k), mishmash.get(i).getSessio().gettAula(), mishmash.get(i).getAssig(), mishmash.get(i).getSub());
                                    System.out.println(mishmash.get(i).getAssig().getNom() + " ficada a les " + gethora(m + z) + " el " + fromInt2dia(l));
                                }
                                if (creaHorari(i + 1, horari)) return true;
                                else {
                                    //no se ha podido hacer, borramos lo que hemos puesto
                                    for (int z = 0; z < duracio; ++z) {
                                        horari[m + z][l][k] = null;
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }

        System.out.println("no se ha podido hacer el horario de esta manera, let's backtrack");
        return false;

    }


    public boolean generaHorari() {
        try {
            mishmash = mishmash(assignatures2);
            ordena_mishamash();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return creaHorari(0, horari);

    }

    private void ordena_mishamash() {
        Collections.sort(mishmash);
    }

    private ArrayList<AssignaturaMonosessio> mishmash(ArrayList<Assignatura> assignatures2) throws NotFoundException {
        ArrayList<AssignaturaMonosessio> res = new ArrayList<>();
        Teoria auxteo;
        Laboratori auxlab = new Laboratori(0, 0, null, null);
        int sesteo, seslab, valor;
        Map<Integer, Grup> grups;
        Grup g;
        HashMap<Integer, Subgrup> subgrups;
        seslab = 0;             //si no comp se queja
        boolean lab;
        for (Assignatura a : assignatures2) {
            lab = false;
            try {
                auxlab = (Laboratori) a.getLaboratori();
                seslab = auxlab.getNumSessions();
                lab = true;
            } catch (NotFoundException e) {}
            auxteo = (Teoria) a.getTeoria();         //TODO: concretar que significa un valor de 1 a sessions i la possibilitat de un valor 0.
            sesteo = auxteo.getNumSessions();
            valor = 8;                      //TODO: heuristica a assignar
            for (int i = 0; lab && i < seslab; ++i) {
                grups = a.getGrups();
                for (int key : grups.keySet()){
                    g = grups.get(key);
                    subgrups = g.getSubgrups();
                    for (int subg : subgrups.keySet()){
                        res.add(new AssignaturaMonosessio(a, auxlab, g, subgrups.get(subg), valor));
                    }

                }

                valor /= 2;
            }
            valor = 8;
            for (int i = 0; i < sesteo; ++i) {
                grups = a.getGrups();
                for (int key : grups.keySet()){
                    res.add(new AssignaturaMonosessio(a, auxteo, grups.get(key), null, valor));
                }

                valor /= 2;
            }
        }
        return res;
    }



}
