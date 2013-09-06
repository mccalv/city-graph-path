package com.mccalv.citygraph.model.graph;

import java.util.List;
/*
 * @(#)Graph.java     Jan 19, 2011
 * 
 * Copyright 2009-2013 Mirko Calvaresi.
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
/**
 * A generic interface for a non oriented graph
 * 
 * @author mccalv
 * @since Jan 13, 2011
 * 
 */
public interface Graph {
	/**
	 * Returns the number of edges
	 * 
	 * @return
	 */
	int getNodesNumber();

	/**
	 * Returns the number of nodes
	 * 
	 * @return
	 */
	int getEdgesNumber();

	/**
	 * Returns the {@link Node}list
	 * 
	 * @return
	 */
	List<Node> getNodes();

	/**
	 * Returns the {@link Edge}list
	 * 
	 * @return
	 */
	List<Edge> getEdges();

	/**
	 * 
	 * 
	 * @param v
	 * @return
	 */
	NodeInfo getNodeInfo(Node v);

	/**
	 * Ritorna le informazioni associate ad un arco
	 * 
	 * @param v
	 * @return
	 */
	EdgeInfo getEdgeInfo(Edge v);

	/**
	 * Ritorna gli archi uscenti da un nodo
	 * 
	 * @param v
	 * @return
	 */
	List<Edge> nodeEdges(Node v);

	/**
	 * Ritorna l'acro che unisce due nodi o <code>null</code> se i due nodi non
	 * sono adiacenti
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	Edge areAdjacents(Node x, Node y);

	/**
	 * Fa un update delle informazioni del nodo
	 * @param v
	 * @param info
	 */
	void updateNodeInfo(Node v, NodeInfo info);

	/**
	 * 
	 * @param indice
	 * @return
	 */
	Node nodo(int indice);

	/**
	 * Ritorna l'indice del nodo
	 * 
	 * @param nodo
	 * @return
	 */
	int index(Node nodo);

	/**
	 * Aggiunge un nodo
	 * 
	 * @param n
	 * @return
	 */
	Node addNode(Node n);

	/**
	 * Costruisce un arco tra due nodi
	 * 
	 * @param x
	 * @param y
	 * @param infoArco
	 * @return
	 */
	Edge addEdge(Node x, Node y, EdgeInfo infoArco);

	/**
	 * Rimuove un noodo dalla lista dei nodi ed anche gli eventuali archi che lo
	 * connettono
	 * 
	 * @param v
	 */
	void removeNode(Node v);

	/**
	 * Rimuove un arco dalla lista degli archi
	 * 
	 * @param a
	 */
	void removeEdge(Edge a);

	/**
	 * Ritorna tutti i nodi che sono collegati direttamente ad un nodo dato
	 * 
	 * @return
	 */
	List<Node> getDestinations(Node nodo);

	/**
	 * Calcola la distanza dell'arco che unisce due nodi
	 * 
	 * @param u
	 * @param v
	 * @return
	 */
	Integer computeNodeDistance(Node u, Node v);

}
