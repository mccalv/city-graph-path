/*
 * @(#)Crossing.java     Jan 19, 2011
 *
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
package com.mccalv.citygraph.graph.impl;

import com.mccalv.citygraph.model.graph.NodeInfo;
import com.mccalv.citygraph.model.graph.Node;

/**
 * 
 * @author mccalv
 * @since Jan 19, 2011
 * 
 */
public class Crossing extends Node {

	/**
	 * @param infoNodo
	 */
	public Crossing(NodeInfo infoNodo) {
		super(infoNodo);

	}

	/**
	 * @param string
	 */
	public Crossing(String string) {
		super(new NodeInfo(string));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Node arg0) {
		return this.getNodeInfo().getIdentifier()
				.compareTo(arg0.getNodeInfo().getIdentifier());
	}

}
