/*
 * @(#)Incrocio.java     Jan 19, 2011
 *
 * University of Tor Vergata, Faculty on Informatic Engeneering.
 * Examination of "Algoritmi e strutture dati".
 *
 */
package it.uniroma2.algoritimiestrutturedati.mccalv.model.graph.impl;

import it.uniroma2.algoritimiestrutturedati.mccalv.model.graph.InfoNodo;
import it.uniroma2.algoritimiestrutturedati.mccalv.model.graph.Nodo;

/**
 * 
 * @author mccalv
 * @since Jan 19, 2011
 * 
 */
public class Incrocio extends Nodo {

	/**
	 * @param infoNodo
	 */
	public Incrocio(InfoNodo infoNodo) {
		super(infoNodo);

	}

	/**
	 * @param string
	 */
	public Incrocio(String string) {
		super (new InfoNodo(string));	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Nodo arg0) {
		return this.getInfoNodo().getIdentificativo()
				.compareTo(arg0.getInfoNodo().getIdentificativo());
	}

}
