/*
 * @(#)EdgeInfo.java     Jan 19, 2011
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
 *
 */
package com.mccalv.citygraph.model.graph;

/**
 * A simple bean to represent the edge information (name and travelTime)
 * 
 * @author mccalv
 * @since Jan 19, 2011
 * 
 */
public class EdgeInfo {

	private String name;

	private int travelTime;

	/**
	 * Getter for identifier.
	 * 
	 * @return the identifier.
	 */

	public EdgeInfo(String name, int travelTime) {

		this.name = name;
		this.travelTime = travelTime;
	}

	/**
	 * Getter for nome.
	 * 
	 * @return the nome.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param nome
	 *            the nome to set
	 */
	public void setName(String nome) {
		this.name = nome;
	}

	/**
	 * Getter for tempiPercorrenza.
	 * 
	 * @return the tempiPercorrenza.
	 */
	public int getTraveltime() {
		return travelTime;
	}

	/**
	 * @param tempiPercorrenza
	 *            the tempiPercorrenza to set
	 */
	public void setTraveltime(int travelTime) {
		this.travelTime = travelTime;
	}

}
