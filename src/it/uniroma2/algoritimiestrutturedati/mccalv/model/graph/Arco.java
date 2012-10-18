package it.uniroma2.algoritimiestrutturedati.mccalv.model.graph;

/*
 * @(#)Arco.java     Jan 13, 2011
 *
 * University of Tor Vergata, Faculty on Computer Engineering.
 * Examination of "Algoritmi e strutture dati".
 *
 */

/**
 * Interfaccia generica per definire un arco che connette due {@link Nodo} A e B
 * 
 * @author mccalv
 * @since Jan 13, 2011
 * 
 */
public abstract class Arco {

	private Nodo a, b;
	private InfoArco infoArco;

	/**
	 * Getter for a.
	 * 
	 * @return the a.
	 */
	public Nodo getA() {
		return a;
	}

	/**
	 * @param a
	 *            the a to set
	 */
	public void setA(Nodo a) {
		this.a = a;
	}

	/**
	 * Getter for b.
	 * 
	 * @return the b.
	 */
	public Nodo getB() {
		return b;
	}

	/**
	 * @param b
	 *            the b to set
	 */
	public void setB(Nodo b) {
		this.b = b;
	}

	/**
	 * Costruttore generico per un arco che connetta due nodi a e b
	 * 
	 * @param orig
	 * @param dest
	 * @param info
	 */
	public Arco(Nodo a, Nodo b, InfoArco infoArco) {

		this.a = a;
		this.b = b;
		this.infoArco = infoArco;

	}

	/**
	 * Getter for infoArco.
	 * 
	 * @return the infoArco.
	 */
	public InfoArco getInfoArco() {
		return infoArco;
	}

	/**
	 * @param infoArco
	 *            the infoArco to set
	 */
	public void setInfoArco(InfoArco infoArco) {
		this.infoArco = infoArco;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return infoArco.getNome();
	}
}
