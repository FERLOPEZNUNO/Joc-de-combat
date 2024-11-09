package joc.arena;

import java.io.FileNotFoundException;
import joc.arena.fitxers.Ranquing;
import joc.arena.regles.Combat;
import joc.arena.regles.Lluitador;
import joc.arena.interficie.EntradaTeclat;
import joc.arena.interficie.SortidaPantalla;
import joc.arena.fitxers.Bestiari;

public class JocArena {

    public static final int MAX_COMBAT = 10;

    private final EntradaTeclat entrada = new EntradaTeclat();
    private final SortidaPantalla sortida = new SortidaPantalla();
    private final Lluitador lluitador = new Lluitador();
    private final Combat combat = new Combat();
    private final Bestiari bestiari = new Bestiari();

    public static void main(String[] args) throws FileNotFoundException {
        JocArena programa = new JocArena();
        programa.inici();
    }

    public void inici() throws FileNotFoundException {
       
        sortida.mostarBenvinguda();
        int[] jugador = bestiari.generarJugador();

        int numCombat = 0;
        boolean jugar = true;

        while (jugar) {
            numCombat++;

            //Abans de cada combat es restaura al jugador
            lluitador.restaurar(jugador);

            //Inici d'un combat
            System.out.println("*** COMBAT " + numCombat);
            System.out.print("Estat actual del jugador: ");
            sortida.mostrarLluitador(jugador);
            System.out.println("**************************");

            //S'obte l'adversari
            int[] adversari = entrada.triarAdversari(lluitador.llegirNivell(jugador));

            //Combat
            combatre(jugador, adversari);

            //Fi?
            jugar = continuaCombat(jugador, adversari);
            if (jugar&&numCombat == MAX_COMBAT) {
                System.out.println("Has sobreviscut a tots els combats. Enhorabona!!");
                jugar=false;
            }

        }
        System.out.print("Estat final del jugador: ");
        sortida.mostrarLluitador(jugador);

        Ranquing rnk = new Ranquing();
        int punts = lluitador.llegirPunts(jugador);
        int pos = rnk.cercarRanking(punts);
        if (pos != -1) {

            String inicials = entrada.preguntarInicials();
            rnk.entrarPuntuacio(inicials, punts, pos);

            sortida.mostrarRanking();
        } else {
            System.out.println("La puntuació no està entre les 10 millors.");
        }

    }

    /**
     * Resol totes les rondes d'un combat
     *
     * @param jugador Estat del jugador
     * @param adversari Estat de l'adversari
     */
    public void combatre(int[] jugador, int[] adversari) {

        boolean combatre = true;
        int numRonda = 0;
        while (combatre) {
            numRonda++;
            if (numRonda % 5 == 0) {
                //Les rondes multiples de cinc es restaura l'atac i defensa
                lluitador.restaurar(jugador);
                lluitador.restaurar(adversari);
            }
            System.out.println("--- RONDA " + numRonda);
            sortida.mostrarVersus(jugador, adversari);
            System.out.println("--------------------------");
            int accioJug = entrada.preguntarEstrategia();
            int accioAdv = lluitador.triarEstrategiaAtzar(adversari);
            System.out.print("Has triat " + combat.estrategiaAText(accioJug));
            System.out.println(" i el teu enemic " + combat.estrategiaAText(accioAdv));
            combat.resoldreEstrategies(jugador, accioJug, adversari, accioAdv);
            if (lluitador.esMort(jugador) || lluitador.esMort(adversari)) {
                combatre = false;
            }
        }
    }

    /**
     * Resol la fi del combat.
     *
     * @param jugador Estat del jugador
     * @param adversari Estat de l'adversari
     * @return Si el jugador ha de seguir jugant (true) o no (false)
     */
    public boolean continuaCombat(int[] jugador, int[] adversari) {
        if (lluitador.esMort(jugador)) {
            //Has perdut (Nota: tambe inclou el cas que tots dos moren alhora)
            System.out.println("Has estat derrotat... :-(");
            return false;
        }
        System.out.println("Has guanyat el combat :-)");
        boolean pujarNivell = lluitador.atorgarPunts(jugador, adversari);
        if (pujarNivell) {
            System.out.println("Has pujat de nivell!!");
            lluitador.pujarNivell(jugador);
        }
        return true;
    }

}
