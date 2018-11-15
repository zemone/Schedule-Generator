package model;

import java.util.ArrayList;
import java.util.HashMap;

public class RestriccioSubgrupLab extends Restriccions {

    /**
     * Crea una restricció on es comprova que en una sessió de laboratori d'un determinat subgrup no hi hagi solapaments
     */
    public RestriccioSubgrupLab() {
        super(5);
    }

    /**
     * Retorna si es possible realitzar una assignació de laboratori d'un determinat subgrup comprovant que no hi hagi solapaments per al bactracking cronològic
     *
     * @param horari horari que es comprova
     * @param hora   hora que es comprova
     * @param dia    dia que es comprova
     * @param assig  assignatura que es comprova
     * @param aules2 aules que es comproven
     * @return true si es pot realitzar l'assignació
     */
    public boolean isAble(Assignacio[][][] horari, int hora, int dia, SessioGrup assig, ArrayList<Aula> aules2) {
        if (assig.getSub() != null) {
            int grup = assig.getSub().getNum() / 10;
            for (int j = 0; j < aules2.size(); ++j) {
                Assignacio a = horari[hora][dia][j];
                if (a != null) {
                    if (a.getAssignatura().getNom() == assig.getAssig().getNom()) {
                        if (a.getGrup().getNum() == assig.getSub().getNum())
                            return false;                           //solapament de laboratoris
                        if (a.getGrup().getNum() / 10 == grup)
                            return false;                           //solapament teoria-laboratori
                    }
                }
            }
        }
        return true;
    }

    /**
     * Retorna si
     * @param check
     * @param assignat
     * @param pos
     * @param aula
     * @param dia
     * @param hora
     * @return
     */

    public boolean isAble2(SessioGrup check, SessioGrup assignat, HashMap<SessioGrup, ArrayList<ArrayList<ArrayList<Integer>>>> pos , int aula, int dia, int hora){
        if (pos.get(check).get(dia).get(hora).contains(aula)) {
            if (check.getAssig().getNom() == assignat.getAssig().getNom()) {
                if (assignat.getSub() != null) {
                    int grup = assignat.getSub().getNum() / 10;
                    if (check.getGrup().getNum() / 10 == grup) return false;
                    if (check.getSub() != null)
                        if (check.getSub().getNum() == assignat.getSub().getNum()) return false;
                }
            }
        }
        return true;
    }
}
