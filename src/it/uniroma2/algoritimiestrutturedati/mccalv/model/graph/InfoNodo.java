/*
 * @(#)InfoNodo.java     Jan 19, 2011
 *
 * University of Tor Vergata, Faculty on Computer Engineering.
 * Examination of "Algoritmi e strutture dati".
 *
 */
package it.uniroma2.algoritimiestrutturedati.mccalv.model.graph;

/**
 * 
 * @author mccalv
 * @since Jan 19, 2011
 * 
 */
public class InfoNodo {

	public InfoNodo(String identificativo) {
		
		this.identificativo = identificativo;
	}

	private String identificativo;

	/**
	 * Getter for identificativo.
	 * 
	 * @return the identificativo.
	 */
	public String getIdentificativo() {
		return identificativo;
	}

	/**
	 * @param identificativo
	 *            the identificativo to set
	 */
	public void setIdentificativo(String identificativo) {
		this.identificativo = identificativo;
	}

}
