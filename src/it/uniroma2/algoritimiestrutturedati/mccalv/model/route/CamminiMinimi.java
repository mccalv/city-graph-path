/*
 * @(#)CamminiMinimi.java     Jan 20, 2011
 *
 * University of Tor Vergata, Faculty on Informatic Engineering.
 * Examination of "Algoritmi e strutture dati".
 *
 */
package it.uniroma2.algoritimiestrutturedati.mccalv.model.route;

import it.uniroma2.algoritimiestrutturedati.mccalv.model.graph.Grafo;
import it.uniroma2.algoritimiestrutturedati.mccalv.model.graph.Nodo;
import it.uniroma2.algoritimiestrutturedati.mccalv.model.graph.impl.ReteStradale;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Implementazione del algoritmo di Dijkstra relativo ai cammini minimi.
 * L'implementazione usa una coda con prorità {@link PriorityQueue} per i nodi
 * non settati e un {@link Set} per quelli Settati.
 * <P>
 * L'utilizzo della classe prevede che venga passata una {@link ReteStradale}
 * 
 * <pre>
 * camminiMimini = new CamminiMinimi(reteStradale);
 * 	camminiMimini.calcolaDistanzaMinima(start, destination);
 * 
 * @author mccalv
 * @since Jan 20, 2011
 * 
 */
public class CamminiMinimi {

	private Nodo start;
	private Nodo end;

	/**
	 * Getter for start.
	 * 
	 * @return the start.
	 */
	public Nodo getStart() {
		return start;
	}

	/**
	 * @param start
	 *            the start to set
	 */
	public void setStart(Nodo start) {
		this.start = start;
	}

	/**
	 * Getter for end.
	 * 
	 * @return the end.
	 */
	public Nodo getEnd() {
		return end;
	}

	/**
	 * @param end
	 *            the end to set
	 */
	public void setEnd(Nodo end) {
		this.end = end;
	}

	/**
	 * Distanza iniziale
	 */
	public static final int INFINITE_DISTANCE = Integer.MAX_VALUE;

	/**
	 * Un valore arbitrario per inizializzare la coda
	 */
	private static final int INITIAL_CAPACITY = 10;

	/**
	 * Semplice {@link Comparator} che ordina i nodi (Incroci) per distanza. In
	 * caso di distanza uguale, prevale il criterio lessicale
	 */
	private final Comparator<Nodo> shortestDistanceComparator = new Comparator<Nodo>() {
		public int compare(Nodo left, Nodo right) {

			int result = getDistanzaMinima(left) - getDistanzaMinima(right);

			return (result == 0) ? left.compareTo(right) : result;
		}
	};
	private final Grafo grafo;

	public CamminiMinimi(Grafo citta) {
		this.grafo = citta;
	}

	/**
	 * The graph.
	 */

	/**
	 * Coda con priorità dei nodi che non sono stati settati e la cui distanza
	 * non è stata ancora trovata.
	 */
	private final PriorityQueue<Nodo> nodiNonSettati = new PriorityQueue<Nodo>(
			INITIAL_CAPACITY, shortestDistanceComparator);

	/**
	 * Il Set dei nodi per cui la distanza minima è stata trovata
	 */
	private final Set<Nodo> nodiSettati = new HashSet<Nodo>();

	/**
	 * La corrente distanza minore nota di tutti i nodi.
	 */
	private final Map<Nodo, Integer> distanzeMinime = new LinkedHashMap<Nodo, Integer>();

	/**
	 * Ritorna una mappa con i predecessori di un nodo nell'albero delle
	 * distanze minime
	 */
	private final Map<Nodo, Nodo> precedessori = new HashMap<Nodo, Nodo>();

	/**
	 * @return la distanza minore tra il nodo sorgente e il nodo in argomento o
	 *         {@link CamminiMinimi#INFINITE_DISTANCE} se non esiste un percorso
	 * 
	 */
	public int getDistanzaMinima(Nodo nodo) {
		Integer d = distanzeMinime.get(nodo);
		return (d == null) ? INFINITE_DISTANCE : d;
	}

	/**
	 * Inizializza le strutture dati usate dall'algoritmo
	 * 
	 * @param start
	 *            the source node
	 */
	private void init(Nodo start) {

		nodiSettati.clear();
		nodiNonSettati.clear();

		distanzeMinime.clear();
		precedessori.clear();

		// add source
		settaLaDistanzaMinima(start, 0);
		nodiNonSettati.add(start);
	}

	/**
	 * Implementazione dell'algoritmo di Dijkstra sui cammini minimi I risultati
	 * sono memorizzati nella {@link #getPredecessore(Nodo)} e
	 * #getDistanzaMinima(City)}
	 * 
	 * 
	 * @param start
	 *            il nodo di partenza
	 * @param destination
	 *            il nodo di destinazione. Se questo argomento è nullo,
	 *            l'algoritmo scorre l'intero grafo
	 */
	public void calcolaDistanzaMinima(Nodo start, Nodo destination) {
		this.start = start;
		this.end = destination;
		init(start);

		// the current node
		Nodo u;
		if (start.equals(end)) {
			settaLaDistanzaMinima(end, 0);
			return;
		}
		// estrae il nodo con la distanza minore
		while ((u = nodiNonSettati.poll()) != null) {
			assert !settato(u);

			// se la distanza è raggiunta si ferma
			if (u == destination)
				break;

			nodiSettati.add(u);

			rilassaVicini(u);
		}
	}

	/**
	 * Calcola la distanza minima per i nodi vicini and fa un update se una
	 * distanza minore è trovata
	 * 
	 * @param u
	 *            the node
	 */
	private void rilassaVicini(Nodo u) {
		for (Nodo v : grafo.getDestinazioni(u)) {
			// salta i nodi già settati
			if (settato(v))
				continue;

			int shortDist = getDistanzaMinima(u)
					+ grafo.calcolaDistanzaTraNodi(u, v);

			if (shortDist < getDistanzaMinima(v)) {
				// assegna la nuova distanza minima e la marca come non settato
				settaLaDistanzaMinima(v, shortDist);

				// assegna il predecessore
				setPredecessore(v, u);
			}
		}
	}

	/**
	 * Setta la nuova distanza minima per il nodo and bilancia la cosa di
	 * conseguenza
	 * 
	 * 
	 * @param city
	 *            the node to set
	 * @param distance
	 *            new shortest distance value
	 */
	private void settaLaDistanzaMinima(Nodo node, int distance) {

		/*
		 * Questo step assicura che non non ci siano duplicati nella coda quando
		 * un nodo non settato viene updatato con la distanza minore
		 */
		nodiNonSettati.remove(node);

		/*
		 * Update the shortest distance.
		 */
		distanzeMinime.put(node, distance);

		/*
		 * Ribialncia la cosa attraverso la distanza minore trovata
		 */
		nodiNonSettati.add(node);
	}

	/**
	 * @return il nodo che sul tragitto minore o <code>null</code> se non ci
	 *         sono
	 */
	public Nodo getPredecessore(Nodo nodo) {
		return precedessori.get(nodo);
	}

	private void setPredecessore(Nodo a, Nodo b) {
		precedessori.put(a,b);
	}

	/**
	 * Testa un nodo.
	 * 
	 * @param v
	 *            the node to consider
	 * 
	 * @return se un nodo è settato la sua distanza minima è stata creata.
	 */
	private boolean settato(Nodo v) {
		return nodiSettati.contains(v);
	}

	public Map<Nodo, Integer> getDistanzeMinimeOrdinate() {

		return ordinaDistanzeMinime();

	}

	public LinkedHashMap<Nodo,Integer> ordinaDistanzeMinime() {
		List<Nodo> mapKeys = new ArrayList<Nodo>(distanzeMinime.keySet());
		List<Integer> mapValues = new ArrayList<Integer>(
				distanzeMinime.values());
		Collections.sort(mapValues);
		Collections.sort(mapKeys);

		LinkedHashMap<Nodo, Integer> sortedMap = new LinkedHashMap<Nodo, Integer>();

		Iterator<Integer> valueIt = mapValues.iterator();
		while (valueIt.hasNext()) {
			Integer val = valueIt.next();
			Iterator<Nodo> keyIt = mapKeys.iterator();

			while (keyIt.hasNext()) {
				Nodo key = keyIt.next();
				String comp1 = distanzeMinime.get(key).toString();
				String comp2 = val.toString();

				if (comp1.equals(comp2)) {
					distanzeMinime.remove(key);
					mapKeys.remove(key);
					sortedMap.put(key, val);
					break;
				}

			}

		}
		return sortedMap;
	}

	
	
}
