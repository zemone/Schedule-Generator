package drivers;


import controllers.CtrlDomini;
import exceptions.NotFoundException;
import exceptions.RestriccioIntegritatException;
import model.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;


public class DriverCtrDomini {

    public static void menu() {
        System.out.println("Escull una opció");
        System.out.println("1: crea assignatures");
        System.out.println("2: crea pla d'estudis");
        System.out.println("3: crea aules");
        System.out.println("4: consulta atributs");
        System.out.println("5: modifica atributs");
        System.out.println("6: genera horari");
        System.out.println("7: consulta horari");
        System.out.println("8: sortir");

    }

    public static void creaAssignatures(Scanner s, CtrlDomini c) {
        String nom, tipusAula;
        Aula.TipusAula Atipus, Atipusl;
        int opcio, opciol, quadrimestre, nsest, nsesl, dsest, dsesl, gnum, gcap, gsub;
        nsesl = dsesl = 0;
        Atipusl = Aula.TipusAula.NORMAL;
        opcio = 0;
        while (opcio != 1) {
            System.out.println("introdueix el nom de la assignatura i el quadrimestre al qual pertany");
            nom = s.next();
            quadrimestre = s.nextInt();
            System.out.println("Introdueix el numero de sessions, la duracio de sessions, el tipus d'aula de les sessions de teoria(si no s'introdueix un tipus valid es fara per defecte normal), el numero de grups de la assignatura, la capacitat d'aquests grups, i el numero de subgrups");
            nsest = s.nextInt();
            dsest = s.nextInt();
            tipusAula = s.next();
            gnum = s.nextInt();
            gcap = s.nextInt();
            gsub = s.nextInt();
            Atipus = Aula.stringToTipusAula(tipusAula);
            if (Atipus == null) {
                System.out.println("Tipus d'aula incorrecte, recorda que pot ser \"pcs, normal, laboratori\"");
                Atipus = Aula.TipusAula.NORMAL;
            }
            System.out.println("introdueix un 0 si la assigantura tindra sessions de laboratori, qualsevol altre numero en cas contrari");
            opciol = s.nextInt();
            if (opciol == 0) {
                System.out.println("Introdueix el numero de sessions, la duracio de sessions i el tipus d'aula de les sessions de laboratori , si no s'introdueix un tipus valid es fara per defecte normal");
                nsesl = s.nextInt();
                dsesl = s.nextInt();
                tipusAula = s.next();
                Atipusl = Aula.stringToTipusAula(tipusAula);
                if (Atipusl == null) {
                    System.out.println("Tipus d'aula incorrecte, recorda que pot ser \"pcs, normal, laboratori\"");
                    Atipusl = Aula.TipusAula.NORMAL;
                }
            }
            try {
                c.crearAssignatura(nom, quadrimestre);
                c.modificaInformacioTeoria(nom, dsest, nsest, Atipus);
                if(opciol == 0) c.modificaInformacioLaboratori(nom, nsesl, dsesl , Atipusl);
                c.modificarGrups(nom, gnum, gcap, gsub);
            } catch (RestriccioIntegritatException e) {
                e.printStackTrace();
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
            System.out.println("introdueix un 1 per a deixar de crear plans, qualsevol altre numero per a seguir");
            opcio = s.nextInt();
        }
    }

    public static void crearPlaEstudis(Scanner s, CtrlDomini c) {
        String aux;
        int any;
        int opt = 0;
        while (opt != 1) {
            System.out.println("introdueix el nom i l'any");
            aux = s.next();
            any = s.nextInt();
            try {
                c.crearPlaEstudis(aux, any);
            } catch (RestriccioIntegritatException e) {
                e.printStackTrace();
            }
            System.out.println("introdueix un 1 per a deixar de crear plans, qualsevol altre numero per a seguir");
            opt = s.nextInt();
        }
    }

    public static void crearAules(Scanner s, CtrlDomini c) {
        String edifici, tipusaula;
        int opt, planta, aula, capacitat;
        Aula.TipusAula tAula;
        opt = 0;
        while (opt != 1) {
            System.out.println("Introdueix el nom, la planta, el numero, el tipus de l'aula(en cas d'input erroni sera una aula normal) i la capacitat");
            edifici = s.next();
            planta = s.nextInt();
            aula = s.nextInt();
            tipusaula = s.next();
            if (tipusaula.equalsIgnoreCase("pcs")) {
                tAula = Aula.TipusAula.PCS;
            } else if (tipusaula.equalsIgnoreCase("laboratori")) {
                tAula = Aula.TipusAula.LABORATORI;
            } else
                tAula = Aula.TipusAula.NORMAL;
            capacitat = s.nextInt();
            try {
                c.creaAula(edifici, planta, aula, capacitat, tAula);
            } catch (RestriccioIntegritatException e) {
                e.printStackTrace();
            }
            System.out.println("introdueix un 1 per a deixar de crear aules, qualsevol altre numero per a seguir");
            opt = s.nextInt();
        }
    }


    public static void main(String[] args) throws NotFoundException {
        Scanner scan = new Scanner(System.in);
        int option = 0;
        CtrlDomini ctrl = CtrlDomini.getInstance();
        while (option != 8) {
            menu();
            option = scan.nextInt();
            switch (option) {
                case 1:
                    creaAssignatures(scan, ctrl);
                    break;
                case 2:
                    crearPlaEstudis(scan, ctrl);
                    break;
                case 3:
                    crearAules(scan, ctrl);
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    ctrl.generaHorari();
                    break;
                case 7:
                    ctrl.printarHoraritot();
                    break;
                case 8:
                    break;
                default:
                    System.out.println("Opció incorrecte. Introdueix una opcio vàlida");
                    break;

            }

        }
    }
}