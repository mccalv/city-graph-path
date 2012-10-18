/*
 * @(#)Volante.java     Jan 19, 2011
 *
 * University of Tor Vergata, Faculty on Computer Engineering.
 * Examination of "Algoritmi e strutture dati".
 *
 */
package it.uniroma2.algoritimiestrutturedati.mccalv.model;

import it.uniroma2.algoritimiestrutturedati.mccalv.model.graph.Nodo;

/**
 * Classe rappresentante la volante. Nella formalizzazione, la volante
 * rappresenta il vettore che può spostarsi su tutti i nodi e gli archi del
 * grafo rappresentante la città
 * 
 * 
 * @author mccalv
 * @since Jan 19, 2011
 * 
 */
public class Volante implements Comparable<Volante> {

	private Intervento intervento;

	private String identifier;

	private Integer distanzaIntervento = null;

	private Nodo nodo;

	/**
	 * Getter for distanzaIntervento.
	 * 
	 * @return the distanzaIntervento.
	 */
	public Integer getDistanzaIntervento() {
		return distanzaIntervento;
	}

	/**
	 * @param distanzaIntervento
	 *            the distanzaIntervento to set
	 */
	public void setDistanzaIntervento(Integer distanzaIntervento) {
		this.distanzaIntervento = distanzaIntervento;
	}

	/**
	 * La posizione precedente indica se una volante è attualmente impegnata in
	 * un intervento. Il valore <code>null</code> significa che la Volante è
	 * disponibile
	 */
	private Nodo posPreIntevento;

	/**
	 * Getter for posPreIntevento.
	 * 
	 * @return the posPreIntevento.
	 */
	public Nodo getPosPreIntevento() {
		return posPreIntevento;
	}

	/**
	 * @param posPreIntevento
	 *            the posPreIntevento to set
	 */
	public void setPosPreIntevento(Nodo posPreIntevento) {
		this.posPreIntevento = posPreIntevento;
	}

	/**
	 * Getter for nodo.
	 * 
	 * @return the nodo.
	 */
	public Nodo getNodo() {
		return nodo;
	}

	/**
	 * @param nodo
	 *            the nodo to set
	 */
	public void setNodo(Nodo nodo) {
		this.nodo = nodo;
	}

	public Volante(String identifier) {

		this.identifier = identifier;
	}

	public Volante(String identifier, Nodo nodo) {

		this.identifier = identifier;
		this.nodo = nodo;
	}

	/**
	 * Getter for identifier.
	 * 
	 * @return the identifier.
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * @param identifier
	 *            the identifier to set
	 */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String ap = (distanzaIntervento != null) ? " Dist. Intervento "  + Integer
				.toString(distanzaIntervento) : "";
		return identifier + " " + ap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object arg0) {
		return identifier.equals(((Volante) arg0).getIdentifier());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Volante arg0) {
		return identifier.compareTo(identifier);
	}

	/**
	 * Getter for intervento.
	 * 
	 * @return the intervento.
	 */
	public Intervento getIntervento() {
		return intervento;
	}

	/**
	 * @param intervento
	 *            the intervento to set
	 */
	public void setIntervento(Intervento intervento) {
		this.intervento = intervento;
	}

}
