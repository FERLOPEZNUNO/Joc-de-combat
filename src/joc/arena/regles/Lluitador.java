package joc.arena.regles;

import java.util.Random;

public class Lluitador {

    //Format
    //Nom:Nivell:XP:PV:Max PV:Atac:Max Atac:max Defensa
    public final static int IDENTIFICADOR = 0;
    public final static int NIVELL = 1;
    public final static int PUNTS = 2;
    public final static int VIDA = 3;
    public final static int VIDA_MAX = 4;
    public final static int ATAC = 5;
    public final static int ATAC_MAX = 6;
    public final static int DEFENSA = 7;
    public final static int DEFENSA_MAX = 8;

    /**
     * Infligeix dany a un lluitador, restant punts de vida, fins un mínim de 0.
     *
     * @param lluitador Lluitador que rep el dany
     * @param punts Punts de vida que perd
     */
    public void danyar(int[] lluitador, int punts) {
        if (lluitador[VIDA] > punts) {
            lluitador[VIDA] = lluitador[VIDA] - punts;
        } else {
            lluitador[VIDA] = 0;
        }
    }

    /**
     * Guareix un lluitador, que recupera punts de vida. Mai pot superar el
     * màxim possible
     *
     * @param lluitador Lluitador a guarir
     * @param punts Punts de vida recuperats
     */
    public void guarir(int[] lluitador, int punts) {
        lluitador[VIDA] = lluitador[VIDA] + punts;
        if (lluitador[VIDA] > lluitador[VIDA_MAX]) {
            lluitador[VIDA] = lluitador[VIDA_MAX];
        }
    }

    /**
     * Aplica una penalització al lluitador. Es fa al atzar entre el valor
     * d'atac i el de defensa. Se li resta un punt, fins un valor mínim d'1.
     *
     * @param lluitador Lluitador a penalitzar
     * @param grau Grau de penalització
     */
    public void penalitzar(int[] lluitador, int grau) {
        //Es tria quina habilitat penalitzar
        Random rnd = new Random();
        int penalitzar = ATAC;
        if (rnd.nextBoolean()) {
            penalitzar = DEFENSA;
        }
        //Es penalitza. Minim baixa fins a 1
        lluitador[penalitzar] -= grau;
        if (lluitador[penalitzar] < 1) {
            lluitador[penalitzar] = 1;
        }
    }

    /**
     * Renova un lluitador, anul·lant totes les penalitzacions i danys.
     *
     * @param lluitador Lluitador a renovar
     */
    public void renovar(int[] lluitador) {
        restaurar(lluitador);
        lluitador[VIDA] = lluitador[VIDA_MAX];
    }

    public void restaurar(int[] lluitador) {
        lluitador[ATAC] = lluitador[ATAC_MAX];
        lluitador[DEFENSA] = lluitador[DEFENSA_MAX];
    }

    /**
     * Resol l'atorgament de punts a l'aventurer al derrotar a un adversari. La
     * quantitat de punts depén de la diferència de nivells entrre els dos. Si
     * es guanyen prous punts, s'avisa si cal pujar de nivell.
     *
     * @param aventurer Aventurer
     * @param adversari Adversari derrotat
     * @return Si s'ha pujat de nivell (cada 100 punts)
     */
    public boolean atorgarPunts(int[] aventurer, int[] adversari) {
        //Es calcula el multiplicador
        float multiplicador = 0;
        int numMultiplicadors = adversari[NIVELL] - aventurer[NIVELL] + 2;
        for (int i = 0; i < numMultiplicadors; i++) {
            multiplicador += 0.5;
        }
        //Punts finals a atorgar
        int puntsAdversari = llegirPunts(adversari);
        int puntsAtorgats = Math.round(puntsAdversari * multiplicador);

        //Puja de nivell?
        aventurer[PUNTS] += puntsAtorgats;
        int nouNivell = 1 + aventurer[PUNTS] / 100;
        return nouNivell > aventurer[NIVELL];
    }

    /**
     * Resol un increment d'un nivell, augmentant a l'atzar atac o defensa i dos
     * punts de vida màxims. A més a més, el lluitador es guareix totalment.
     *
     * @param lluitador Lluitador que fa la tirada
     */
    public void pujarNivell(int[] lluitador) {
        lluitador[NIVELL]++;
        Random rnd = new Random();
        if (rnd.nextBoolean()) {
            //S'incrementa atac
            lluitador[ATAC_MAX]++;
        } else {
            //S'incrementa defensa
            lluitador[DEFENSA_MAX]++;
        }
        lluitador[VIDA_MAX] += 2;
        //Es deixa nou de trinca
        renovar(lluitador);
    }

    /**
     * Resol una tirada d'atac d'un lluitador. Es llencen tantes monedes com el
     * seu valor d'atac.
     *
     * @param lluitador Lluitador que fa la tirada
     * @return El nombre de cares obtingudes
     */
    public int tirarAtac(int[] lluitador) {
        Monedes monedes = new Monedes();
        return monedes.ferTirada(lluitador[ATAC]);
    }

    /**
     * Resol una tirada de defensa d'un lluitador. Es llencen tantes monedes com
     * el seu valor de defensa.
     *
     * @param lluitador Lluitador que fa la tirada
     * @return El nombre de cares obtingudes
     */
    public int tirarDefensa(int[] lluitador) {
        Monedes monedes = new Monedes();
        return monedes.ferTirada(lluitador[DEFENSA]);
    }

    /**
     * Donat un lluitador, tria a l'atzar quina estrategia usar en una ronda de
     * combat.
     *
     * @param lluitador Lluitador que tria l'acció
     * @return Acció triada
     */
    public int triarEstrategiaAtzar(int[] lluitador) {
        Random rnd = new Random();
        int limitDefensa = 3;

        //Si li queda poca vida, defensa el 50% dels cops
        if (lluitador[VIDA] < 2) {
            limitDefensa = 1;
        }

        int accio = rnd.nextInt(10);
        if ((accio >= 0) && (accio < limitDefensa)) {
            return Combat.ATAC;
        } else if ((limitDefensa >= 3) && (accio < 6)) {
            return Combat.DEFENSA;
        } else if ((accio >= 6) && (accio < 8)) {
            return Combat.ENGANY;
        } else {
            return Combat.MANIOBRA;
        }
    }

    //METODES PER FACILITAR LA LECTURA DE DADES
    /**
     * Diu l'identificador d'un lluitador. 
     *
     * @param lluitador Lluitador de qui es vol llegir l'identificador
     * @return Identificador del lluitador
     */
    public int llegirId(int[] lluitador) {
        return lluitador[IDENTIFICADOR];
    }

    /**
     * Diu quin és el nivell de lluitador. 
     *
     * @param lluitador Lluitador de qui es vol llegir el nivell
     * @return nivell del lluitador
     */
    public int llegirNivell(int[] lluitador) {
        return lluitador[NIVELL];
    }

    /**
     * Diu quins punts val el lluitador. 
     *
     * @param lluitador Lluitador de qui es volen llegir els punts
     * @return punts del jugador
     */
    public int llegirPunts(int[] lluitador) {
        return lluitador[PUNTS];
    }

    /**
     * Diu quina vida te el lluitador. 
     *
     * @param lluitador Lluitador de qui es vol llegir la vida
     * @return Vida
     */
    public int llegirVida(int[] lluitador) {
        return lluitador[VIDA];
    }

    /**
     * Diu quina vida maxima te el lluitador.
     *
     * @param lluitador Lluitador de qui es vol llegir la vida maxima
     * @return Vida
     */
    public int llegirVidaMax(int[] lluitador) {
        return lluitador[VIDA_MAX];
    }

    /**
     * Diu quin atac te el lluitador. 
     *
     * @param lluitador Lluitador de qui es vol llegir l'atac
     * @return Grau d'atac
     */
    public int llegirAtac(int[] lluitador) {
        return lluitador[ATAC];
    }

    /**
     * Diu quina defensa te el lluitador. 
     *
     * @param lluitador Lluitador de qui es vol llegir la defensa
     * @return Grau de defensa
     */
    public int llegirDefensa(int[] lluitador) {
        return lluitador[DEFENSA];
    }

    /**
     * Diu si un lluitador es mort o no. O sigui, si els seus punts de vida son
     * 0 ara mateix. 
     *
     * @param lluitador Lluitador a comprovar
     * @return Si es considera mort (true) o no (false)
     */
    public boolean esMort(int[] lluitador) {
        return (lluitador[VIDA] == 0);
    }

}
