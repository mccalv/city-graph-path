/*
 * @Car(#).java     Jan 19, 2011
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mccalv.citygraph.model;

import com.mccalv.citygraph.model.graph.Node;

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
public class Car implements Comparable<Car> {

	private Intervention intervento;

	private String identifier;

	private Integer distanzaIntervento = null;

	private Node nodo;

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
	private Node posPreIntevento;

	/**
	 * Getter for posPreIntevento.
	 * 
	 * @return the posPreIntevento.
	 */
	public Node getPosPreIntevento() {
		return posPreIntevento;
	}

	/**
	 * @param posPreIntevento
	 *            the posPreIntevento to set
	 */
	public void setPosPreIntevento(Node posPreIntevento) {
		this.posPreIntevento = posPreIntevento;
	}

	/**
	 * Getter for nodo.
	 * 
	 * @return the nodo.
	 */
	public Node getNodo() {
		return nodo;
	}

	/**
	 * @param nodo
	 *            the nodo to set
	 */
	public void setNodo(Node nodo) {
		this.nodo = nodo;
	}

	public Car(String identifier) {

		this.identifier = identifier;
	}

	public Car(String identifier, Node nodo) {

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
		String ap = (distanzaIntervento != null) ? " Dist. Intervento "
				+ Integer.toString(distanzaIntervento) : "";
		return identifier + " " + ap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object arg0) {
		return identifier.equals(((Car) arg0).getIdentifier());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Car arg0) {
		return identifier.compareTo(identifier);
	}

	/**
	 * Getter for intervento.
	 * 
	 * @return the intervento.
	 */
	public Intervention getIntervento() {
		return intervento;
	}

	/**
	 * @param intervento
	 *            the intervento to set
	 */
	public void setIntervento(Intervention intervento) {
		this.intervento = intervento;
	}

}
