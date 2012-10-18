/*
 * @(#)Citta.java     Jan 19, 2011
 *
 * University of Tor Vergata, Faculty on Informatic Engeneering.
 * Examination of "Algoritmi e strutture dati".
 *
 */
package it.uniroma2.algoritimiestrutturedati.mccalv.model.graph.impl;

import it.uniroma2.algoritimiestrutturedati.mccalv.model.Intervento;
import it.uniroma2.algoritimiestrutturedati.mccalv.model.Volante;
import it.uniroma2.algoritimiestrutturedati.mccalv.model.graph.Arco;
import it.uniroma2.algoritimiestrutturedati.mccalv.model.graph.Grafo;
import it.uniroma2.algoritimiestrutturedati.mccalv.model.graph.InfoArco;
import it.uniroma2.algoritimiestrutturedati.mccalv.model.graph.InfoNodo;
import it.uniroma2.algoritimiestrutturedati.mccalv.model.graph.Nodo;
import it.uniroma2.algoritimiestrutturedati.mccalv.model.route.CamminiMinimi;
import it.uniroma2.algoritimiestrutturedati.mccalv.parser.InputFileParser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * {@link Grafo} rapprensentante una Citta' come insieme di archi e nodi.
 * 
 * @author mccalv
 * @since Jan 19, 2011
 * 
 */
public class ReteStradale implements Grafo {

	private LinkedList<Nodo> nodi = new LinkedList<Nodo>();
	private LinkedList<Arco> archi = new LinkedList<Arco>();

	private List<Volante> volanti = new ArrayList<Volante>();

	/**
	 * 
	 * @param citta
	 */
	public ReteStradale(Citta citta) {
		this.citta = citta;
	}

	public static enum Citta {

		Roma, Milano, Napoli
	}

	/**
	 * Nome della citta corrispondente alla rete stradale
	 */
	private Citta citta;

	/**
	 * Getter for citta.
	 * 
	 * @return the citta.
	 */
	public Citta getCitta() {
		return citta;
	}

	/**
	 * @param citta
	 *            the citta to set
	 */
	public void setCitta(Citta citta) {
		this.citta = citta;
	}

	/*
	 * 
	 * 
	 * (non-Javadoc)
	 * 
	 * @see it.uniroma2.algoritimiestrutturedati.Grafo#numNodi()
	 */
	@Override
	public int numNodi() {
		return nodi.size();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.uniroma2.algoritimiestrutturedati.Grafo#numArchi()
	 */
	@Override
	public int numArchi() {
		return archi.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.uniroma2.algoritimiestrutturedati.Grafo#nodi()
	 */
	@Override
	public List<Nodo> nodi() {

		return nodi;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.uniroma2.algoritimiestrutturedati.Grafo#archi()
	 */
	@Override
	public List<Arco> archi() {
		return archi;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.uniroma2.algoritimiestrutturedati.Grafo#infoNodo(it.uniroma2.
	 * algoritimiestrutturedati.Nodo)
	 */
	@Override
	public InfoNodo infoNodo(Nodo v) {
		return v.getInfoNodo();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.uniroma2.algoritimiestrutturedati.Grafo#infoArco(it.uniroma2.
	 * algoritimiestrutturedati.Arco)
	 */
	@Override
	public InfoArco infoArco(Arco v) {
		return v.getInfoArco();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.uniroma2.algoritimiestrutturedati.Grafo#archiUscenti(it.uniroma2.
	 * algoritimiestrutturedati.Nodo)
	 */
	@Override
	public List<Arco> archiUscenti(Nodo v) {
		List<Arco> arcoUscenti = new ArrayList<Arco>();

		for (Arco arco : archi) {
			if ((arco.getA().equals(v) || arco.getB().equals(v))
					&& !arcoUscenti.contains(arco)) {
				arcoUscenti.add(arco);
			}

		}
		return arcoUscenti;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.uniroma2.algoritimiestrutturedati.Grafo#sonoAdiacenti(it.uniroma2.
	 * algoritimiestrutturedati.Nodo, it.uniroma2.algoritimiestrutturedati.Nodo)
	 */
	@Override
	public Arco sonoAdiacenti(Nodo x, Nodo y) {
		// Due nodi sono adiacenti se un arco che li congiunge
		for (Arco a : archi) {

			if ((a.getA().equals(x) && a.getB().equals(y))
					|| (a.getA().equals(y) && a.getB().equals(x))) {
				return a;
			}

		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.uniroma2.algoritimiestrutturedati.Grafo#cambiaInfoNodo(it.uniroma2
	 * .algoritimiestrutturedati.Nodo, java.lang.Object)
	 */
	@Override
	public void cambiaInfoNodo(Nodo v, InfoNodo info) {
		v.setInfoNodo(info);
		for (Nodo nodo : nodi) {
			if (nodo.equals(v)) {
				nodo = v;
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.uniroma2.algoritimiestrutturedati.Grafo#nodo(int)
	 */
	@Override
	public Nodo nodo(int indice) {
		return nodi.get(indice);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.uniroma2.algoritimiestrutturedati.Grafo#indice(it.uniroma2.
	 * algoritimiestrutturedati.Nodo)
	 */
	@Override
	public int indice(Nodo nodo) {
		for (int i = 0; i < nodi().size(); i++) {

			if (nodo.equals(nodi().get(i))) {
				return i;
			}
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.uniroma2.algoritimiestrutturedati.Grafo#aggiungiNodo(java.lang.Object)
	 */
	@Override
	public Nodo aggiungiNodo(Nodo nodo) {
		// Controlla se il nodo non è già presente nella lista
		if (!nodi.contains(nodo)) {
			nodi.add(nodo);
		}
		return nodo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.uniroma2.algoritimiestrutturedati.Grafo#aggiungiArcho(it.uniroma2.
	 * algoritimiestrutturedati.Nodo, it.uniroma2.algoritimiestrutturedati.Nodo,
	 * java.lang.Object)
	 */
	@Override
	public Arco aggiungiArcho(Nodo x, Nodo y, InfoArco info) {
		Arco strada = new Strada(x, y, info);
		if (!archi.contains(strada)) {
			archi.add(strada);
		}
		return strada;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.uniroma2.algoritimiestrutturedati.Grafo#rimuoviNodo(it.uniroma2.
	 * algoritimiestrutturedati.Nodo)
	 */
	@Override
	public void rimuoviNodo(Nodo v) {
		nodi.remove(v);
		// Utilizzo un iteratore per evitare problemi di concurrent modification
		Iterator<Arco> it = archi.iterator();
		while (it.hasNext()) {
			Arco arco = it.next();
			if (arco.getA().equals(v) || arco.getB().equals(v)) {
				it.remove();
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.uniroma2.algoritimiestrutturedati.Grafo#rimuoviArco(it.uniroma2.
	 * algoritimiestrutturedati.Arco)
	 */
	@Override
	public void rimuoviArco(Arco a) {
		archi.remove(a);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.uniroma2.algoritimiestrutturedati.mccalv.model.graph.Grafo#getDestinazioni
	 * (it.uniroma2.algoritimiestrutturedati.mccalv.model.graph.Nodo)
	 */
	@Override
	public List<Nodo> getDestinazioni(Nodo nodo) {
		List<Arco> archi = archiUscenti(nodo);
		List<Nodo> nodi = new ArrayList<Nodo>();
		for (Arco arco : archi) {
			// Il nodo di destinazione è il terminale del grafo che non coincide
			// con il nodo uscente
			Nodo nodoUscente = arco.getA().equals(nodo) ? arco.getB() : arco
					.getA();
			nodi.add(nodoUscente);
		}
		return nodi;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.uniroma2.algoritimiestrutturedati.mccalv.model.graph.Grafo#
	 * calcolaDistanzaTraNodi
	 * (it.uniroma2.algoritimiestrutturedati.mccalv.model.graph.Nodo,
	 * it.uniroma2.algoritimiestrutturedati.mccalv.model.graph.Nodo)
	 */
	@Override
	public Integer calcolaDistanzaTraNodi(Nodo u, Nodo v) {
		List<Arco> archi = archiUscenti(v);
		for (Arco arco : archi) {
			if ((arco.getA().equals(v) && arco.getB().equals(u))
					|| (arco.getB().equals(v) && arco.getA().equals(u))) {

				return arco.getInfoArco().getTempiPercorrenza();
			}

		}
		return null;
	}

	/**
	 * Ritorna il numero di volanti che insistono sul nodo
	 * 
	 * @param n
	 * @return
	 */
	public List<Volante> volantisuNodo(Nodo n) {
		List<Volante> volantiSuNodo = new ArrayList<Volante>();
		for (Volante v : volanti) {

			if (v.getNodo().equals(n)) {
				volantiSuNodo.add(v);

			}

		}
		return volantiSuNodo;

	}

	/**
	 * Ritorna l'indice della volante se presente su nodo, zero altrimenti
	 * 
	 * @param n
	 * @return
	 */
	public int indiceVolante(Nodo n, Volante volante) {
		int index = 0;

		List<Volante> volantisuNodo = volantisuNodo(n);

		for (Volante v : volantisuNodo) {

			if (volante.equals(v)) {
				return index;

			}
			index++;
		}
		return index;

	}

	/**
	 * Getter for volanti.
	 * 
	 * @return the volanti.
	 */
	public List<Volante> getVolanti() {
		return volanti;
	}

	/**
	 * @param volanti
	 *            the volanti to set
	 */
	public void setVolanti(List<Volante> volanti) {
		this.volanti = volanti;
	}

	/**
	 * Persiste la posizione delle volanti sul file di testo associato
	 * 
	 */
	public void salvaVolantiSuFile() {

		InputFileParser.updateVolanti(this);
	}

	/**
	 * Ritorna il numero di Volanti disponibili più vicine all'intervento
	 */
	public List<Volante> aggiungiIntevento(Intervento intervento) {
		List<Volante> volantiDisponibili = volantiDisponibili(intervento);

		updateVolantiIntervento(volantiDisponibili);
		return volantiDisponibili;
		/* Sincronizza le volanti */

	}

	private void updateVolantiIntervento(List<Volante> volantiDisponibili) {

		ListIterator<Volante> it = volanti.listIterator();

		while (it.hasNext()) {
			Volante v = it.next();
			for (Volante volanteIntervento : volantiDisponibili) {

				if (volanteIntervento.equals(v)) {
					it.set(volanteIntervento);
				}
			}
		}
		salvaVolantiSuFile();

	}

	/**
	 * Ritorna una lista di Volanti disponibili ordinata per vicinanza rispetto
	 * al punto di intervento
	 * 
	 * @param intervento
	 * @return
	 */
	private List<Volante> volantiDisponibili(Intervento intervento) {

		int livelloProrita = intervento.getPriorita();
		int volantiAssegnate = 0;

		List<Volante> volantiOrdered = new ArrayList<Volante>();

		Nodo nodoIntervento = intervento.getNodo().getA();

		CamminiMinimi camminiMinimi = new CamminiMinimi(this);
		// Incrocio 1

		camminiMinimi.calcolaDistanzaMinima(nodoIntervento, null);

		Map<Nodo, Integer> distanzeMinimeOrdinate = camminiMinimi
				.getDistanzeMinimeOrdinate();

	
		Iterator<Nodo> it = distanzeMinimeOrdinate.keySet().iterator();

		while (it.hasNext()) {
			Nodo intermedio = it.next();

			List<Volante> volantisuNodo = volantisuNodo(intermedio);

			for (Volante v : volantisuNodo) {
				if (v.getPosPreIntevento() == null) {
					v.setIntervento(intervento);
					v.setPosPreIntevento(v.getNodo());
					v.setNodo(intervento.getNodo().getA());
					v.setDistanzaIntervento(distanzeMinimeOrdinate
							.get(intermedio));

					volantiAssegnate++;
					volantiOrdered.add(v);
				}

				if ((livelloProrita == volantiAssegnate)
						&& livelloProrita != 10) {

					return volantiOrdered;
				}
			}

		}

		return volantiOrdered;
	}

	/**
	 * Chiude l'intervento assegnato con l'identificato e riporta le volanti
	 * coinvolte nella posizione precedente l'intervento
	 * 
	 * @param identificativo
	 *            dell'intervento
	 */
	public void chiudiIntervento(String identificativo) {
		ListIterator<Volante> it = volanti.listIterator();

		while (it.hasNext()) {
			Volante v = it.next();

			if (v.getIntervento() != null
					&& v.getIntervento().getIdentificativo()
							.equals(identificativo)) {
				v.setNodo(v.getPosPreIntevento());
				v.setPosPreIntevento(null);
				v.setIntervento(null);
				v.setDistanzaIntervento(null);
				it.set(v);
			}
		}

		salvaVolantiSuFile();

	}

	/**
	 * Chiude tutti gli interventi e resetta la posizione della Volante nello
	 * stato precedente l'intervento
	 */
	public void chiudiTuttiInterventi() {
		ListIterator<Volante> it = volanti.listIterator();

		while (it.hasNext()) {
			Volante v = it.next();

			if (v.getIntervento() != null) {
				v.setNodo(v.getPosPreIntevento());
				v.setPosPreIntevento(null);
				v.setIntervento(null);
				it.set(v);
			}
		}

		salvaVolantiSuFile();

	}

}
