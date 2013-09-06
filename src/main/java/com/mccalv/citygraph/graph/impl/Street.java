/*
 * @(#)Strada.java     Jan 19, 2011
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
package com.mccalv.citygraph.graph.impl;

import com.mccalv.citygraph.model.graph.Edge;
import com.mccalv.citygraph.model.graph.EdgeInfo;
import com.mccalv.citygraph.model.graph.Node;

/**
 * A representation of a street
 * 
 * @author mccalv
 * @since Jan 19, 2011
 * 
 */
public class Street extends Edge {

	/**
	 * @param a
	 * @param b
	 * @param infoArco
	 */
	public Street(Node a, Node b, EdgeInfo infoArco) {
		super(a, b, infoArco);

	}

}
