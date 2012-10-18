package it.uniroma2.algoritimiestrutturedati.mccalv.model.graph;

/*
 * @(#)Nodo     Jan 13, 2011
 *
 * University of Tor Vergata, Faculty on Computer Engineering.
 * Examination of "Algoritmi e strutture dati".
 *
 */

public abstract class Nodo implements Comparable<Nodo> {
	private InfoNodo infoNodo;

	public Nodo(InfoNodo infoNodo) {
		this.infoNodo = infoNodo;
	}

	/**
	 * Getter for infoNodo.
	 * 
	 * @return the infoNodo.
	 */
	public InfoNodo getInfoNodo() {
		return infoNodo;
	}

	/**
	 * @param infoNodo
	 *            the infoNodo to set
	 */
	public void setInfoNodo(InfoNodo infoNodo) {
		this.infoNodo = infoNodo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		// * Due nodi sono uguali se sono uguali gli identificativi */
		Nodo obj2 = (Nodo) obj;
		if (obj2 != null) {
			return infoNodo.getIdentificativo().equals(
					obj2.getInfoNodo().getIdentificativo());
		} else {
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return infoNodo.getIdentificativo();
	}
}