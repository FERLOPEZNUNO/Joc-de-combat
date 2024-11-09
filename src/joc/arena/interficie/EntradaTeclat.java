package joc.arena.interficie;

import java.util.Scanner;
import joc.arena.regles.Bestiari;

import joc.arena.regles.Combat;

public class EntradaTeclat {

  private Bestiari bestiari = new Bestiari();

  /** Tria l'adversari del jugador en base a la seva resposta.
   * @param nivell del jugador
   * @return Cadena de text amb la resposta
   */
  public int[] triarAdversari(int nivell) {
    System.out.print("Contra quin adversari vols lluitar en aquest combat? ");
    Scanner lector = new Scanner(System.in);
    String resposta = lector.nextLine();    
    int[] adversari = bestiari.cercarAdversari(resposta);
    if (adversari == null) {
      System.out.println("Aquest enemic no existeix. Es tria a l'aztar.");
      adversari = bestiari.triarAdversariAtzar(nivell);
    }
    return adversari;
  }



  /** Pregunta a l'usuari quina estrategia vol usar, d'entre
   * les quatre possibles
   *
   * @return Accio a dur a terme, d'acord a les constants de la classe Combat, 
   */
  public int preguntarEstrategia() {
    Scanner lector = new Scanner(System.in);
    System.out.println("Quina estrategia vols seguir aquesta ronda?");
    System.out.println("[A]tacar\t[D]efensar\t[E]ngany\t[M]aniobra");
    System.out.println("--------");
    boolean preguntar = true;
    int accio = -1;
    while (preguntar) {
      System.out.print("Accio: ");
      String resposta = lector.nextLine();
      if ("A".equalsIgnoreCase(resposta)) {
        accio =  Combat.ATAC;
        preguntar = false;
      } else if ("D".equalsIgnoreCase(resposta)) {
        accio = Combat.DEFENSA;
        preguntar = false;
      } else if ("E".equalsIgnoreCase(resposta)) {
        accio = Combat.ENGANY;
        preguntar = false;
      } else if ("M".equalsIgnoreCase(resposta)) {
        accio = Combat.MANIOBRA;
        preguntar = false;
      } else {
        System.out.print("Accio incorrecta...");
      }
    }
    return accio;
  }

  //NOUS METODES

  /** Pregunta les inicials del jugador si assoleix una maxima puntuacio.
   *
   * @return Inicials (cadena de texta amb 3 caracters)
   */
  public String preguntarInicials() {
    Scanner lector = new Scanner(System.in);
    System.out.println("has assolit una maxima puntuacio!!");
    String inicials = "";
    boolean llegir = true;
    while (llegir) {
      System.out.print("Escriu les teves inicials: ");
      inicials = lector.nextLine();
      //Aquest metode elimina espais laterals
      inicials = inicials.trim();
      if (inicials.length() == 3) {
        llegir = false;
      } else {
        System.out.print("Entrada incorrecta. ");
      }
    }
    //Sempre es posaran en majuscula
    return inicials.toUpperCase();
  }

}
