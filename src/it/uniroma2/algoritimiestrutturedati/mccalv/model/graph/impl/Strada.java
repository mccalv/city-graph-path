/*
 * @(#)Strada.java     Jan 19, 2011
 *
 * University of Tor Vergata, Faculty on Informatic Engeneering.
 * Examination of "Algoritmi e strutture dati".
 *
 */
package it.uniroma2.algoritimiestrutturedati.mccalv.model.graph.impl;

import it.uniroma2.algoritimiestrutturedati.mccalv.model.graph.Arco;
import it.uniroma2.algoritimiestrutturedati.mccalv.model.graph.InfoArco;
import it.uniroma2.algoritimiestrutturedati.mccalv.model.graph.Nodo;

/**
 * 
 * @author mccalv
 * @since Jan 19, 2011
 * 
 */
public class Strada extends Arco {

	/**
	 * @param a
	 * @param b
	 * @param infoArco
	 */
	public Strada(Nodo a, Nodo b, InfoArco infoArco) {
		super(a, b, infoArco);

	}

}
