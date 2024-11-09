package joc.arena.fitxers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Formatter;
import java.util.Scanner;

public class Ranquing {

    //Nom fitxer com constant
    public static final String RANQUING = "Ranquing.txt";

    /**
     * Crea el fitxer de puntuacions inicial
     *
     * 
     */
    public void generarFitxerInicial() throws FileNotFoundException {

        File ranquing = new File(RANQUING);
        if (!ranquing.exists()) {
            Formatter ps = new Formatter(ranquing);
            for (int i = 0; i < 10; i++) {
                ps.format("DAW " + (10 - i) + "\n");
            }
            ps.close();
        }
       
    }

    public static void main(String[] args) throws FileNotFoundException {
        Ranquing r = new Ranquing();
        r.generarFitxerInicial();
        r.imprimirRanquing();
    }

    /**
     * Donada una puntuacio, estableix la seva posicio al fitxer
     *
     * @param punts Punts que cal comprovar
     * @return posicio per la puntuacio. -1 Si error.
     */
    public int cercarRanking(int punts) throws FileNotFoundException {

        generarFitxerInicial();
        File ranquing = new File(RANQUING);
        int pos;
        boolean trobat = false;
        Scanner lector = new Scanner(ranquing);
        pos = 0;

        while (pos < 10 && !trobat) {
            String nom = lector.next();
            int ranqPts = lector.nextInt();
            if (punts > ranqPts) {
                trobat = true;
            }

            pos++;
        }
        lector.close();
        if (trobat) {
            return pos - 1;
        } else {
            return -1;
        }

    }

    /**
     * Insereix una puntuacio al ranking
     *
     * @param inicials Inicials del jugador
     * @param punts Puntuacio assolida
     * @param pos Posicio dins el ranking, o -1 si hi ha error * .
     * @throws java.io.FileNotFoundException
     */
    public void entrarPuntuacio(String inicials, int punts, int pos) throws FileNotFoundException {

        generarFitxerInicial();
        File ranquing = new File(RANQUING);
        File tmp;
        Scanner lector = new Scanner(ranquing);
        tmp = new File(RANQUING + ".tmp");
        //Reescriure anteriors a posicio
        Formatter ps = new Formatter(tmp);
        //Reescriure anteriors a posicio
        for (int i = 0; i < pos; i++) {
            String txt = lector.nextLine();
            ps.format(txt+" \n");
        }     //Escriure nova puntuacio
        ps.format(inicials + " " + punts+"\n");
        //Reescriure posteriors a posicio
        for (int i = pos + 1; i < 10; i++) {
            String txt = lector.nextLine();
            ps.format(txt+"\n");
        }
        lector.close();
        ps.close();
        //S'eborra fitxer antic i es posa el nou
        ranquing.delete();
        tmp.renameTo(ranquing);

    }

    /**
     * Llegeis les puntuacions i les formata com una cadena de text
     *
     * @return Cadena de text resultant. null si hi ha error
     */
    public String imprimirRanquing() throws FileNotFoundException {

        String txtRanquing = "Ranquing de puntuacions\n----------------------\n";
        File ranquing = new File(RANQUING);
        Scanner lector = new Scanner(ranquing);
        for (int i = 0; i < 10; i++) {
            //Llegir inicials
            txtRanquing = txtRanquing + lector.next();
            //Llegir punts
            if (lector.hasNextInt()) {
                txtRanquing = txtRanquing + "\t" + lector.nextInt() + "\n";
            }
        }

        System.out.println(txtRanquing);
        return txtRanquing;
    }

}
