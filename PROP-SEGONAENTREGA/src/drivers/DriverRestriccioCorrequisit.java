/**
 * @Author Adria Cabeza
 */
package drivers;

import exceptions.NotFoundException;
import exceptions.RestriccioIntegritatException;
import model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class DriverRestriccioCorrequisit {
    public static void mostraopcions() {
        System.out.println("Escull una opcio:");
        System.out.println("1: Crear una restriccio");
        System.out.println("2: consultar els atributs actuals");
        System.out.println("3: comprobar restriccio");
        System.out.println("4: Sortir");
    }
    public static RestriccioCorrequisit creador (Scanner s){
        return new RestriccioCorrequisit();
    }

    public static SessioGrup creaAssig(Scanner s){
        String nom,tipusaula;
        int aux,num;
        Aula.TipusAula tAula;
        InfoSessions ses = null;
        Assignatura ass;
        Grup grup = null;
        Subgrup sub = null;
        System.out.println("Introdueix el nom i el quadrimestre de la assignatura");
        nom = s.next();
        aux = s.nextInt();
        ass = new Assignatura(nom, "abr", "sample descr", aux);
        System.out.println("Introdueix el numero del grup");
        num = s.nextInt();
        System.out.println("Ara afegirem correquisits a la assignatura");
        afegeixCo(ass,s);
        tAula = Aula.TipusAula.NORMAL;
        ses = new Teoria(0,0,tAula);
        return new SessioGrup(ass,ses,new Grup(num,1,1),null,0);
    }

    public static void creaOpcions(SessioGrup check, HashMap<SessioGrup, ArrayList<ArrayList<ArrayList<Integer>>>> pos, Scanner s){
        System.out.println("per a simplificar nomes tindrem 2 dies i 2 hores amb com a molt una possible aula en cada hora");
        int aux;
        ArrayList<ArrayList<ArrayList<Integer>>> diahoraaules = new ArrayList<>(2);
        for(int d =0; d<2; ++d) {
            ArrayList<ArrayList<Integer>> horaaules = new ArrayList<>(2);
            for (int h = 0; h < 2; ++h) {
                ArrayList<Integer> aulesHora = new ArrayList<>();
                System.out.println("Dia: " + d + " Hora: " + h);
                System.out.println("Introdueix el numero de aula que vols que hi hagi, un -1 si no vols cap");
                aux = s.nextInt();
                if(aux >= 0 ) aulesHora.add(aux);
                horaaules.add(h,aulesHora);
            }
            diahoraaules.add(d,horaaules);
        }
        pos.put(check,diahoraaules);
    }
    public static void afegeixCo(Assignatura ass ,Scanner s){
        int opt = 0;
        String corr ;
        while(opt == 0){
            System.out.println("prem un 0 per a afegir correquisits, qualsevol altre numero per a sortir");
            opt = s.nextInt();
            switch (opt){
                case 0:
                    System.out.println("Introdueix el nom i el quadrimestre al qual pertany");
                    corr = s.next();
                    try {
                        ass.afegeixCorrequisit(new Assignatura(corr, "abr", "sample descr", s.nextInt()));
                    } catch (RestriccioIntegritatException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        int aula, dia, hora;
        RestriccioCorrequisit rest = null;
        SessioGrup assignat = null;
        SessioGrup check = null;
        HashMap<SessioGrup, ArrayList<ArrayList<ArrayList<Integer>>>> pos = new HashMap<>();
        int opt = 0;
        while(opt != 4){
            mostraopcions();
            opt = scan.nextInt();
            switch(opt){

                case 1:
                    pos = new HashMap<>();
                    rest = creador(scan);
                    System.out.println("creem la assignatura que simulara la que acabem d'assignar");
                    assignat =creaAssig(scan);
                    System.out.println("creem la assignatura que fara de la que mirem per treure possibilitats");
                    check = creaAssig(scan);
                    System.out.println("Ara crearem el mapa amb les aules possibles que pot anar la sessio que volem mirar");
                    creaOpcions(check,pos,scan);

                    break;

                case 2:
                    if(rest == null) System.out.println("Error no s'ha creat una restriccio");
                    else{
                        try {
                            System.out.println("Els correquisits de la assignatura ssignada son: " + assignat.getAssig().getCorrequisits());
                        } catch (NotFoundException e) {
                            System.out.println("La assignatura que hem assignat no te correquisits");
                        }
                        try {
                            System.out.println("Els correquisits de la assignatura que este mirant son: " + check.getAssig().getCorrequisits());
                        } catch (NotFoundException e) {
                            System.out.println("La assignatura que estem mirant no te correquisits");
                        }
                    }
                    break;


                case 3:
                    if(rest == null) System.out.println("Error no s'ha creat una restriccio");
                    else {
                        System.out.println("Introdueix el numero de aula, dia i hora que es vol mirar");
                        aula = scan.nextInt();
                        dia = scan.nextInt();
                        hora = scan.nextInt();
                        if(dia < 0 || hora < 0 || dia >=2 || hora >= 2) System.out.println("Error: recorda que nomes tenim dos dies i dos hores(0 i 1)");
                        try {
                            if(rest.isAble2(check,assignat,pos,aula,dia,hora)) System.out.println("es pot seguir usant aquesta aula en aquest dia");
                            else System.out.println("error: no es pot seguir usant aquesta aula en aquest dia");
                        } catch (NotFoundException e) {
                            System.out.println("es pot seguir usant aquesta aula en aquest dia");
                        }
                    }

                    break;

                case 4:
                    break;

                default:
                    System.out.println("Opció incorrecte. Introdueix una opcio vàlida");
                    break;
            }
        }
    }

}
// 1 A 1 1 0 EC 1 1 EC 1 1 0 A 1 0 AR 1 1 1 1 1 1 2 3 1 1 1