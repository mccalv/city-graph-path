package com.mccalv.citygraph.model.graph;

/*
 * @(#)Arco.java     Jan 13, 2011
 * 
 * Copyright 2009-2013 Mirko Calvaresi.
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

/**
 * Generic interface to connect two node {@link Node} A e B
 * 
 * @author mccalv
 * @since Jan 13, 2011
 * 
 */
public abstract class Edge {

	private Node a, b;
	private EdgeInfo infoArco;

	/**
	 * Getter for a.
	 * 
	 * @return the a.
	 */
	public Node getA() {
		return a;
	}

	/**
	 * @param a
	 *            the a to set
	 */
	public void setA(Node a) {
		this.a = a;
	}

	/**
	 * Getter for b.
	 * 
	 * @return the b.
	 */
	public Node getB() {
		return b;
	}

	/**
	 * @param b
	 *            the b to set
	 */
	public void setB(Node b) {
		this.b = b;
	}

	/**
	 * Costruttore generico per un arco che connetta due nodi a e b
	 * 
	 * @param orig
	 * @param dest
	 * @param info
	 */
	public Edge(Node a, Node b, EdgeInfo infoArco) {

		this.a = a;
		this.b = b;
		this.infoArco = infoArco;

	}

	/**
	 * Getter for infoArco.
	 * 
	 * @return the infoArco.
	 */
	public EdgeInfo getInfoArco() {
		return infoArco;
	}

	/**
	 * @param infoArco
	 *            the infoArco to set
	 */
	public void setInfoArco(EdgeInfo infoArco) {
		this.infoArco = infoArco;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return infoArco.getName();
	}
}
