package joc.arena.fitxers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import joc.arena.regles.*;
import java.util.Random;

public class Bestiari {

    //Taula de traduccio d'identificadors a noms
//    private final String[] noms = {"Aventurer",
//        "Nan", "Kobold",
//        "Orc", "Profund",
//        "Bruixot maligne", "Ogre",
//        "Guerrer del caos", "Troll",
//        "Elemental terrestre", "Hidra"};
    //Jugador: ID = 0
    private final int[] jugador = {0, 1, 0, 10, 10, 3, 3, 3, 3};

    //Adversaris possibles al joc
//    private final int[][] adversaris = {
//        {1, 1, 25, 8, 8, 3, 3, 3, 3},
//        {2, 1, 30, 10, 10, 4, 4, 2, 2},
//        {3, 2, 35, 12, 12, 4, 4, 3, 3},
//        {4, 2, 40, 14, 14, 3, 3, 4, 4},
//        {5, 3, 45, 15, 15, 3, 3, 5, 5},
//        {6, 3, 50, 16, 16, 5, 5, 2, 2},
//        {7, 4, 55, 15, 15, 4, 4, 4, 4},
//        {8, 4, 60, 18, 18, 3, 3, 5, 5},
//        {9, 5, 70, 22, 22, 4, 4, 6, 6},
//        {10, 5, 80, 30, 30, 8, 8, 2, 2}
//    };
    private final Lluitador lluitador = new Lluitador();

    public int[] crearAdversari(RandomAccessFile raf, int pos) throws FileNotFoundException, IOException {

//        File f = new File("Adversaris.bin");
//        raf = new RandomAccessFile(f, "r");
        int enemigo[] = new int[9];
        int valor = 0;

        raf.seek(pos * 50 + 30);

        enemigo[0] = pos + 1;

        valor = raf.readInt();
        enemigo[1] = valor;

        valor = raf.readInt();
        enemigo[2] = valor;

        valor = raf.readInt();
        enemigo[3] = valor;
        enemigo[4] = valor;

        valor = raf.readInt();
        enemigo[5] = valor;
        enemigo[6] = valor;

        valor = raf.readInt();
        enemigo[7] = valor;
        enemigo[8] = valor;

        return enemigo;
    }

    public String llegirNom(RandomAccessFile raf) throws IOException {

        String nombre = " ";
        int tamañoNombre = 0;

        while (tamañoNombre != 15) {

            char letra = raf.readChar();
            nombre = nombre + letra;

            tamañoNombre++;
            
        }

        return nombre;
    }

    /**
     * Genera un nou jugador
     *
     * @return Un array amb les dades d'un jugador inicial //
     */
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
    public int[] cercarAdversari(String nomAdv) throws FileNotFoundException, IOException {
                
        RandomAccessFile raf = new RandomAccessFile("Adversaris", "r");

        long tamaño = raf.length() / 50;
        String nombre = "";
        int contador = 0;
        int [] enemigo = new int[9];
        
        while (!nomAdv.equals(nombre) && contador < tamaño) {
            nombre = llegirNom(raf);
            contador++;
        }

        if (nomAdv.equals(nombre)) {
            enemigo= crearAdversari(raf, contador + 1);
        } 
        
        else {
            enemigo= null;
        }

        raf.close();
        return enemigo;
        
    }

    /**
     * Donat un nivell, genera l'adversari corresponent a l'atzar.Es tracta d'un
     * adversari que sigui d'aquest nivell al menys.
     *
     * @param nivell nivell proper al de l'adversari a obtenir
     * @return Un adversari
     * @throws java.io.IOException
     */
    public int[] triarAdversariAtzar(int nivell) throws IOException {

        File f = new File("Adversaris");
        RandomAccessFile raf = new RandomAccessFile(f, "r");
        Random rnd = new Random();

        int numAdv = (int) raf.length() / 50;
        int[] adversari = null;
        boolean cercar = true;

        while (cercar) {
            int i = rnd.nextInt(numAdv);
            adversari = crearAdversari(raf, i);
            int nivellAdv = lluitador.llegirNivell(adversari);
            int dif = nivell - nivellAdv;
            if ((dif >= -1) && (dif <= 1)) {
                cercar = false;
            }
        }

        raf.close();
        return adversari;
    }

    /**
     * Transforma un identificador de Lluitador al seu nom.
     *
     * @param id Identificador
     * @return La cadena de text amb el nom.
     */
    public String traduirIDANom(int id) throws FileNotFoundException, IOException {

        RandomAccessFile raf = new RandomAccessFile("Adversaris", "r");
        raf.seek(id - 1);
        String traduccion = "";
        long tamaño = raf.length() / 50;

        if (id == 0) {
            traduccion= "Aventurer";
        } 
        
        else if (tamaño > id && id > 0) {
            traduccion= llegirNom(raf);
        } 
        
        else {
            traduccion= "DESCONEGUT";
        }
       
        raf.close();
        return traduccion;
        
    }
}




