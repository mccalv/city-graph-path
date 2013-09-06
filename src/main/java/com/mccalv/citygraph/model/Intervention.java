/*
 * @(#)Intervention.java     Jan 24, 2011
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
 *
 */
package com.mccalv.citygraph.model;

import com.mccalv.citygraph.graph.impl.RoadNetwork;
import com.mccalv.citygraph.graph.impl.Street;

/**
 * A class representing an intervention
 * <p>
 * Opening a intervention: it is necessary to indicate the street and a priority code
 * Internally an identification code will be given based to an textual hash with some random chars
 * 
 * 
 * @author mccalv
 * @since Jan 24, 2011
 * 
 */
public class Intervention {

	private RoadNetwork roadNetwork;
	private Street street;
	private int prority;
	private String identifier;

	/**
	 * Getter for priorita.
	 * 
	 * @return the priorita.
	 */
	public int getPrority() {
		return prority;
	}

	public Intervention(RoadNetwork roadNetwork, Street street, int prority) {
		this.roadNetwork = roadNetwork;
		this.street = street;
		this.prority = prority;
		this.identifier = createIdentifier();
	}

	public Intervention(String identificativo) {

		this.identifier = identificativo;
	}

	/**
	 * @param identificativo2
	 * @return
	 */
	private String createIdentifier() {
		//This could have collision
		return roadNetwork.getCity().name().substring(0, 1)
				+ street.getInfoArco().getName().substring(0, 2)
				// Prende due cifre generiche da un numero random
				+ Double.toString(Math.random()).substring(4, 6);
	}

	/**
	 * Getter for identificativo.
	 * 
	 * @return the identificativo.
	 */
	public String getIdentificativo() {
		return identifier;
	}

	/**
	 * Getter for nodo.
	 * 
	 * @return the nodo.
	 */
	public Street getNodo() {
		return street;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Intervention " + identifier + " [city="
				+ roadNetwork.getCity() + ", street=" + street
				+ ", priority=" + prority + "]";
	}

}
