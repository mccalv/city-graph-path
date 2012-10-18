/*
 * @(#)Intervento.java     Jan 24, 2011
 *
 * University of Tor Vergata, Faculty on Computer Engineering.
 * Examination of "Algoritmi e strutture dati".
 *
 */
package it.uniroma2.algoritimiestrutturedati.mccalv.model;

import it.uniroma2.algoritimiestrutturedati.mccalv.model.graph.impl.ReteStradale;
import it.uniroma2.algoritimiestrutturedati.mccalv.model.graph.impl.Strada;

/**
 * Una classe che rappresenta un intervento.
 * <p>
 * Nella segnalazione dell'intervento devono essere indicati una strada ed un
 * codice di prorità. Internamente all'intervento verrà attribuito un codice
 * identificativo basato su un hash testuale realizzato attraverso la
 * contatenazione di alcuni caratteri dell'intervento stesso oltre ad alcune cifre casuali
 * 
 * del nome
 * 
 * @author mccalv
 * @since Jan 24, 2011
 * 
 */
public class Intervento {

	private ReteStradale reteStradale;
	private Strada strada;
	private int priorita;
	private String identificativo;

	/**
	 * Getter for priorita.
	 * 
	 * @return the priorita.
	 */
	public int getPriorita() {
		return priorita;
	}

	public Intervento(ReteStradale reteStradale, Strada strada, int priorita) {
		this.reteStradale = reteStradale;
		this.strada = strada;
		this.priorita = priorita;
		this.identificativo = createIdentificativo();
	}

	public Intervento(String identificativo) {

		this.identificativo = identificativo;
	}

	/**
	 * @param identificativo2
	 * @return
	 */
	private String createIdentificativo() {
		// Questo non rappresenta un hash perfetto in quanto potrebbero esserci
		// delle collisioni
		return reteStradale.getCitta().name().substring(0, 1)
				+ strada.getInfoArco().getNome().substring(0, 2)
				//Prende due cifre generiche da un numero random
				+ Double.toString(Math.random()).substring(4,6);
	}

	/**
	 * Getter for identificativo.
	 * 
	 * @return the identificativo.
	 */
	public String getIdentificativo() {
		return identificativo;
	}

	/**
	 * Getter for nodo.
	 * 
	 * @return the nodo.
	 */
	public Strada getNodo() {
		return strada;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Intervention " +identificativo + " [city=" + reteStradale.getCitta()
				+ ", street=" + strada + ", priority=" + priorita
				+ "]";
	}

}
