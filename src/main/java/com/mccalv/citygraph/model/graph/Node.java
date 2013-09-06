package com.mccalv.citygraph.model.graph;

/*
 * @(#)Nodo     Jan 13, 2011
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

public abstract class Node implements Comparable<Node> {
	private NodeInfo nodeInfo;

	public Node(NodeInfo infoNodo) {
		this.nodeInfo = infoNodo;
	}

	/**
	 * Getter for infoNodo.
	 * 
	 * @return the infoNodo.
	 */
	public NodeInfo getNodeInfo() {
		return nodeInfo;
	}

	/**
	 * @param infoNodo
	 *            the infoNodo to set
	 */
	public void setNodeInfo(NodeInfo infoNodo) {
		this.nodeInfo = infoNodo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		// * Due nodi sono uguali se sono uguali gli identificativi */
		Node obj2 = (Node) obj;
		if (obj2 != null) {
			return nodeInfo.getIdentifier().equals(
					obj2.getNodeInfo().getIdentifier());
		} else {
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return nodeInfo.getIdentifier();
	}
}