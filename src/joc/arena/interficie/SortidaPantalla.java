package joc.arena.interficie;


import java.io.FileNotFoundException;
import joc.arena.fitxers.Ranquing;
import joc.arena.regles.Bestiari;
import joc.arena.regles.Lluitador;

public class SortidaPantalla {

  private final Bestiari bestiari = new Bestiari();

  /** Mostra per pantalla el missatge d'inici del Joc
   *
   */
  public void mostarBenvinguda() {
    System.out.println("Benvingut al Joc de l'Arena");
    System.out.println("===========================");
    System.out.println("Escull amb astúcia la teva estratègia per sobreviure...");
  }

  /** Mostra l'estat d'un lluitador per pantalla.
   *
   * @param ll LLuitador a visualitzar
   */
  public void mostrarLluitador(int[] ll) {
    Lluitador lluitador = new Lluitador();    

    int id = lluitador.llegirId(ll);
    String nom = bestiari.traduirIDANom(id);

    System.out.print(nom);
    System.out.print("\tNivell: " + lluitador.llegirNivell(ll));
    System.out.print(" (punts: " + lluitador.llegirPunts(ll) + ")");
    System.out.print("\tVIDA: " + lluitador.llegirVida(ll));
    System.out.print(" (" + lluitador.llegirVidaMax(ll) + ")");
    System.out.print("\tATAC: " + lluitador.llegirAtac(ll));
    System.out.println("\tDEFENSA: " + lluitador.llegirDefensa(ll));
  }

  /** Mostra l'estat actual del jugador contra el seu adversari.
   *
   * @param jugador Jugador
   * @param adversari Adversari
   */
  public void mostrarVersus(int[] jugador, int[] adversari) {
    System.out.print("JUGADOR: ");
    mostrarLluitador(jugador);
    System.out.println("VS");
    System.out.print("ADVERSARI: ");
    mostrarLluitador(adversari);
  }
  /**
   * @see joc.arena.regles.Bestiari
   * @throws FileNotFoundException 
   */
  public void mostrarRanking() throws FileNotFoundException {
    Ranquing rnk = new Ranquing();
    rnk.imprimirRanquing();
    
  }
}
