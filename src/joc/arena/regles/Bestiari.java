package joc.arena.regles;

import java.util.Random;

public class Bestiari {

    //Taula de traduccio d'identificadors a noms
    private final String[] noms = {"Aventurer",
        "Nan", "Kobold",
        "Orc", "Profund",
        "Bruixot maligne", "Ogre",
        "Guerrer del caos", "Troll",
        "Elemental terrestre", "Hidra"};

    //Jugador: ID = 0
    private final int[] jugador = {0, 1, 0, 10, 10, 3, 3, 3, 3};

    //Adversaris possibles al joc
    private final int[][] adversaris = {
        {1, 1, 25, 8, 8, 3, 3, 3, 3},
        {2, 1, 30, 10, 10, 4, 4, 2, 2},
        {3, 2, 35, 12, 12, 4, 4, 3, 3},
        {4, 2, 40, 14, 14, 3, 3, 4, 4},
        {5, 3, 45, 15, 15, 3, 3, 5, 5},
        {6, 3, 50, 16, 16, 5, 5, 2, 2},
        {7, 4, 55, 15, 15, 4, 4, 4, 4},
        {8, 4, 60, 18, 18, 3, 3, 5, 5},
        {9, 5, 70, 22, 22, 4, 4, 6, 6},
        {10, 5, 80, 30, 30, 8, 8, 2, 2}
    };

    private final Lluitador lluitador = new Lluitador();

    /**
     * Genera un nou jugador
     *
     * @return Un array amb les dades d'un jugador inicial
//     */
    public int[] generarJugador() {
        //lluitador.renovar(jugador);
        return jugador;
    }

    /**
     * Donat un nom, genera l'adversari corresponent. Si aquest nom no existeix,
     * es genera a l'atzar.
     *
     * @param nomAdv Nom de l'adversari a obtenir
     * @return El Lluitador amb aquest nom, o null si no existeix
     */
    public int[] cercarAdversari(String nomAdv) {
        for (int[] adversari : adversaris) {
            int id = lluitador.llegirId(adversari);
            String nom = traduirIDANom(id);
            if (nom.equalsIgnoreCase(nomAdv)) {
                lluitador.renovar(adversari);
                return adversari;
            }
        }
        return null;
    }

    /**
     * Donat un nivell, genera l'adversari corresponent a l'atzar. Es tracta d'un
     * adversari que sigui d'aquest nivell al menys.
     *
     * @param nivell nivell proper al de l'adversari a obtenir
     * @return Un adversari
     */
    public int[] triarAdversariAtzar(int nivell) {
        Random rnd = new Random();

        int[] adversari = null;
        boolean cercar = true;

        while (cercar) {
            int i = rnd.nextInt(adversaris.length);
            adversari = adversaris[i];
            int nivellAdv = lluitador.llegirNivell(adversari);
            int dif = nivell - nivellAdv;
            if ((dif >= -1) && (dif <= 1)) {
                cercar = false;
            }
        }

        //Es deixa a l'adversari nou de trinca, llest per lluitar
        lluitador.renovar(adversari);
        return adversari;
    }

    /**
     * Transforma un identificador de Lluitador al seu nom.
     *
     * @param id Identificador
     * @return La cadena de text amb el nom.
     */
    public String traduirIDANom(int id) {
        if ((id >= 0) && (id < noms.length)) {
            return noms[id];
        }
        return "DESCONEGUT";
    }

}
