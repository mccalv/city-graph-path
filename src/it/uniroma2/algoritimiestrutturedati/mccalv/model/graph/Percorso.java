/*
 * @(#)Percorso.java     Jan 26, 2011
 *
 * University of Tor Vergata, Faculty on Computer Engineering.
 * Examination of "Algoritmi e strutture dati".
 *
 */
package it.uniroma2.algoritimiestrutturedati.mccalv.model.graph;

import it.uniroma2.algoritimiestrutturedati.mccalv.model.graph.impl.ReteStradale;
import it.uniroma2.algoritimiestrutturedati.mccalv.model.route.CamminiMinimi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Generica classe che inizializza e calcola il percorso ottimale secondo
 * l'algoritmo dei cammini minimi. Esegue i metodi accessori solo alla richiesta
 * 
 * @author mccalv
 * @since Jan 26, 2011
 * 
 */
public class Percorso {

	private final CamminiMinimi camminiMimini;

	private final ReteStradale reteStradale;

	public Percorso(ReteStradale reteStradale, Nodo start, Nodo destination) {

		camminiMimini = new CamminiMinimi(reteStradale);
		camminiMimini.calcolaDistanzaMinima(start, destination);

		this.reteStradale = reteStradale;

	}

	/**
	 * Ritorna la lista degli archi che caratterizzano il percorso
	 * 
	 * @param reteStradale
	 * @param from
	 * @param to
	 * @return
	 */
	public List<Arco> getListaStrade() {

		List<Arco> stradePercorse = new ArrayList<Arco>();

		Nodo previous = camminiMimini.getEnd();
		Nodo next = camminiMimini.getPredecessore(previous);

		while (next != null) {
			stradePercorse.add(reteStradale.sonoAdiacenti(previous, next));
			previous = next;
			next = camminiMimini.getPredecessore(next);

		}
		//Metodo di utilità usato solo per la presentazione
		Collections.reverse(stradePercorse);
		return stradePercorse;

	}

	/**
	 * Ritorna la lista dei nodi sul percorso
	 * 
	 * @return
	 */
	public List<Nodo> getIncrociPercorso() {

		List<Nodo> nodi = new ArrayList<Nodo>();

		Nodo intermedio = camminiMimini.getPredecessore(camminiMimini.getEnd());

		while (intermedio != null) {
			nodi.add(intermedio);
			intermedio = camminiMimini.getPredecessore(intermedio);

		}

		return nodi;
	}

	
	

	/**
	 * Ritorna la distanza minima ad un nodo start
	 * 
	 * @param reteStradale
	 * @param from
	 * @param to
	 * @return
	 */
	public int getDistanza(Nodo n) {

		return camminiMimini.getDistanzaMinima(n);
	}

	/**
	 * Ritorna la distanza minima tra il nodo start e quello end
	 * 
	 * @param reteStradale
	 * @param from
	 * @param to
	 * @return
	 */
	public int getDistanzaMinima() {

		return camminiMimini.getDistanzaMinima(camminiMimini.getEnd());
	}

}
