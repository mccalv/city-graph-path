package it.uniroma2.algoritimiestrutturedati.mccalv.model.graph;

import java.util.List;

/*
 * @(#)Grafo.java     Jan 13, 2011
 *
 * University of Tor Vergata, Faculty on Computer Engineering.
 * Examination of "Algoritmi e strutture dati".
 *
 */

/**
 * Interfaccia di un grafo non orientato
 * 
 * @author mccalv
 * @since Jan 13, 2011
 * 
 */
public interface Grafo {
	/**
	 * Ritorna il numero di nodi
	 * 
	 * @return
	 */
	int numNodi();

	/**
	 * Ritorna il numero di archi
	 * 
	 * @return
	 */
	int numArchi();

	/**
	 * Ritorna la lista dei nodi presenti
	 * 
	 * @return
	 */
	List<Nodo> nodi();

	/**
	 * Ritorna la lista dei Archi
	 * 
	 * @return
	 */
	List<Arco> archi();

	/**
	 * Ritorna le informazioni associate ad un singolo nodo
	 * 
	 * @param v
	 * @return
	 */
	InfoNodo infoNodo(Nodo v);

	/**
	 * Ritorna le informazioni associate ad un arco
	 * 
	 * @param v
	 * @return
	 */
	InfoArco infoArco(Arco v);

	/**
	 * Ritorna gli archi uscenti da un nodo
	 * 
	 * @param v
	 * @return
	 */
	List<Arco> archiUscenti(Nodo v);

	/**
	 * Ritorna l'acro che unisce due nodi o <code>null</code> se i due nodi non
	 * sono adiacenti
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	Arco sonoAdiacenti(Nodo x, Nodo y);

	/**
	 * Fa un update delle informazioni del nodo
	 * @param v
	 * @param info
	 */
	void cambiaInfoNodo(Nodo v, InfoNodo info);

	/**
	 * 
	 * @param indice
	 * @return
	 */
	Nodo nodo(int indice);

	/**
	 * Ritorna l'indice del nodo
	 * 
	 * @param nodo
	 * @return
	 */
	int indice(Nodo nodo);

	/**
	 * Aggiunge un nodo
	 * 
	 * @param n
	 * @return
	 */
	Nodo aggiungiNodo(Nodo n);

	/**
	 * Costruisce un arco tra due nodi
	 * 
	 * @param x
	 * @param y
	 * @param infoArco
	 * @return
	 */
	Arco aggiungiArcho(Nodo x, Nodo y, InfoArco infoArco);

	/**
	 * Rimuove un noodo dalla lista dei nodi ed anche gli eventuali archi che lo
	 * connettono
	 * 
	 * @param v
	 */
	void rimuoviNodo(Nodo v);

	/**
	 * Rimuove un arco dalla lista degli archi
	 * 
	 * @param a
	 */
	void rimuoviArco(Arco a);

	/**
	 * Ritorna tutti i nodi che sono collegati direttamente ad un nodo dato
	 * 
	 * @return
	 */
	List<Nodo> getDestinazioni(Nodo nodo);

	/**
	 * Calcola la distanza dell'arco che unisce due nodi
	 * 
	 * @param u
	 * @param v
	 * @return
	 */
	Integer calcolaDistanzaTraNodi(Nodo u, Nodo v);

}
