/*
 * @(#)CamminiMinimi.java     Jan 20, 2011
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
package com.mccalv.citygraph.model.route;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import com.mccalv.citygraph.graph.impl.RoadNetwork;
import com.mccalv.citygraph.model.graph.Graph;
import com.mccalv.citygraph.model.graph.Node;

/**
 * Central implementation of Dijkstra's shortest path algorithm.
 * <p>
 * . The central implementation uses {@link PriorityQueue}for the unset node and
 * a {@link Set} for set ones.
 * <P>
 * It takes a{@link RoadNetwork} as eargument
 * 
 * <pre>
 * shortestPath = new RoadNetwork(reteStradale);
 * 	shortestPath.calcolaDistanzaMinima(start, destination);
 * 
 * @author mccalv
 * @since Jan 20, 2011
 * 
 */
public class ShortestPath {

	private Node start;
	private Node end;

	/**
	 * Getter for start.
	 * 
	 * @return the start.
	 */
	public Node getStart() {
		return start;
	}

	/**
	 * @param start
	 *            the start to set
	 */
	public void setStart(Node start) {
		this.start = start;
	}

	/**
	 * Getter for end.
	 * 
	 * @return the end.
	 */
	public Node getEnd() {
		return end;
	}

	/**
	 * @param end
	 *            the end to set
	 */
	public void setEnd(Node end) {
		this.end = end;
	}

	/**
	 * Initial distance
	 */
	public static final int INFINITE_DISTANCE = Integer.MAX_VALUE;

	/**
	 * Initial queue capacity
	 */
	private static final int INITIAL_CAPACITY = 10;

	/**
	 * Semplice {@link Comparator} che ordina i nodi (Incroci) per distanza. In
	 * caso di distanza uguale, prevale il criterio lessicale
	 */
	private final Comparator<Node> shortestDistanceComparator = new Comparator<Node>() {
		public int compare(Node left, Node right) {

			int result = getMinDistance(left) - getMinDistance(right);

			return (result == 0) ? left.compareTo(right) : result;
		}
	};
	private final Graph grapth;

	public ShortestPath(Graph citta) {
		this.grapth = citta;
	}

	/**
	 * The graph.
	 */

	/**
	 * Priority Query for nodes with distance not already set
	 */
	private final PriorityQueue<Node> nodesNotSet = new PriorityQueue<Node>(
			INITIAL_CAPACITY, shortestDistanceComparator);

	/**
	 * A set for nodes with min distance already set
	 */
	private final Set<Node> nodesSet = new HashSet<Node>();

	/**
	 * La corrente distanza minore nota di tutti i nodi.
	 */
	private final Map<Node, Integer> shortestPaths = new LinkedHashMap<Node, Integer>();

	/**
	 * A map of node's ancestores
	 */
	private final Map<Node, Node> ancestors = new HashMap<Node, Node>();

	/**
	 * @return the minim distance from the current node an other or
	 *         {@link ShortestPath#INFINITE_DISTANCE} if there is no route
	 * 
	 */
	public int getMinDistance(Node nodo) {
		Integer d = shortestPaths.get(nodo);
		return (d == null) ? INFINITE_DISTANCE : d;
	}

	/**
	 * Initializes all data structures in order to prepare the relaxation
	 * 
	 * @param start
	 *            the source node
	 */
	private void init(Node start) {

		nodesSet.clear();
		nodesNotSet.clear();

		shortestPaths.clear();
		ancestors.clear();

		// add source
		setMinDistance(start, 0);
		nodesNotSet.add(start);
	}

	/**
	 * Dijkstra's algorithm implementation. Results are stored in
	 * {@link #getAncestor(Node)} e #getDistanzaMinima(City)}
	 * 
	 * 
	 * @param start
	 *            il nodo di partenza
	 * @param destination
	 *            il nodo di destinazione. Se questo argomento è nullo,
	 *            l'algoritmo scorre l'intero grafo
	 */
	public void computeShortestPath(Node start, Node destination) {
		this.start = start;
		this.end = destination;
		init(start);

		// the current node
		Node u;
		if (start.equals(end)) {
			setMinDistance(end, 0);
			return;
		}
		// estrae il nodo con la distanza minore
		while ((u = nodesNotSet.poll()) != null) {
			assert !isSet(u);

			// se la distanza è raggiunta si ferma
			if (u == destination)
				break;

			nodesSet.add(u);

			relaxNeighbors(u);
		}
	}

	/**
	 * Relax the neighbors node and update a shortest path if found
	 * 
	 * @param u
	 *            the node
	 */
	private void relaxNeighbors(Node u) {
		for (Node v : grapth.getDestinations(u)) {
			// skip the nodes already set
			if (isSet(v))
				continue;

			int shortDist = getMinDistance(u)
					+ grapth.computeNodeDistance(u, v);

			if (shortDist < getMinDistance(v)) {
				// assigns new distance and unset the node
				setMinDistance(v, shortDist);

				// set the ancestor
				setAncestor(v, u);
			}
		}
	}

	/**
	 * Set the min distance anche check for duplicate
	 * 
	 * 
	 * @param city
	 *            the node to set
	 * @param distance
	 *            new shortest distance value
	 */
	private void setMinDistance(Node node, int distance) {

		/*
		 * Ensures that there aren't duplicates in the queue
		 */
		nodesNotSet.remove(node);

		/*
		 * Update the shortest distance.
		 */
		shortestPaths.put(node, distance);

		/*
		 * Ribialncia la cosa attraverso la distanza minore trovata
		 */
		nodesNotSet.add(node);
	}

	/**
	 * @return il nodo che sul tragitto minore o <code>null</code> se non ci
	 *         sono
	 */
	public Node getAncestor(Node nodo) {
		return ancestors.get(nodo);
	}

	private void setAncestor(Node a, Node b) {
		ancestors.put(a, b);
	}

	/**
	 * Tests a Node
	 * 
	 * @param v
	 *            the node to consider
	 * 
	 * @return .
	 */
	private boolean isSet(Node v) {
		return nodesSet.contains(v);
	}

	public Map<Node, Integer> getOrderedMinDistances() {

		return orderShortestPaths();

	}

	public LinkedHashMap<Node, Integer> orderShortestPaths() {
		List<Node> mapKeys = new ArrayList<Node>(shortestPaths.keySet());
		List<Integer> mapValues = new ArrayList<Integer>(shortestPaths.values());
		Collections.sort(mapValues);
		Collections.sort(mapKeys);

		LinkedHashMap<Node, Integer> sortedMap = new LinkedHashMap<Node, Integer>();

		Iterator<Integer> valueIt = mapValues.iterator();
		while (valueIt.hasNext()) {
			Integer val = valueIt.next();
			Iterator<Node> keyIt = mapKeys.iterator();

			while (keyIt.hasNext()) {
				Node key = keyIt.next();
				String comp1 = shortestPaths.get(key).toString();
				String comp2 = val.toString();

				if (comp1.equals(comp2)) {
					shortestPaths.remove(key);
					mapKeys.remove(key);
					sortedMap.put(key, val);
					break;
				}

			}

		}
		return sortedMap;
	}

}
