/*
 * @(#)InfoArco.java     Jan 19, 2011
 *
 * University of Tor Vergata, Faculty on Computer Engineering.
 * Examination of "Algoritmi e strutture dati".
 *
 */
package it.uniroma2.algoritimiestrutturedati.mccalv.model.graph;

/**
 * Un semplice Java Bean che rappresenta le informazioni associate ad un arco,
 * quali identificativo (nome della strada) e tempi di percorrenza
 * 
 * @author mccalv
 * @since Jan 19, 2011
 * 
 */
public class InfoArco {

	private String nome;

	private int tempiPercorrenza;

	/**
	 * Getter for identifier.
	 * 
	 * @return the identifier.
	 */

	public InfoArco(String nome, int tempiPercorrenza) {

		this.nome = nome;
		this.tempiPercorrenza = tempiPercorrenza;
	}

	/**
	 * Getter for nome.
	 * 
	 * @return the nome.
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome
	 *            the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Getter for tempiPercorrenza.
	 * 
	 * @return the tempiPercorrenza.
	 */
	public int getTempiPercorrenza() {
		return tempiPercorrenza;
	}

	/**
	 * @param tempiPercorrenza
	 *            the tempiPercorrenza to set
	 */
	public void setTempiPercorrenza(int tempiPercorrenza) {
		this.tempiPercorrenza = tempiPercorrenza;
	}

}
