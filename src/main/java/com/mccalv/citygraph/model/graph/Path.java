/*
 * @(#)Percorso.java     Jan 26, 2011
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
 *
 */
package com.mccalv.citygraph.model.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.mccalv.citygraph.graph.impl.RoadNetwork;
import com.mccalv.citygraph.model.route.ShortestPath;

/**
 * Class which calculates the best path between nodes, and returns it as list of
 * {@link Edge}s
 * 
 * @author mccalv
 * @since Jan 26, 2011
 * 
 */
public class Path {

	private final ShortestPath shortestPaths
	;

	private final RoadNetwork roadNetwork;

	public Path(RoadNetwork reteStradale, Node start, Node destination) {

		shortestPaths = new ShortestPath(reteStradale);
		shortestPaths.computeShortestPath(start, destination);

		this.roadNetwork = reteStradale;

	}

	/**
	 * Returns the edge lists on the route
	 * 
	 * @param roadNetwork
	 * @param from
	 * @param to
	 * @return
	 */
	public List<Edge> getRouteEdges() {

		List<Edge> stradePercorse = new ArrayList<Edge>();

		Node previous = shortestPaths.getEnd();
		Node next = shortestPaths.getAncestor(previous);

		while (next != null) {
			stradePercorse.add(roadNetwork.areAdjacents(previous, next));
			previous = next;
			next = shortestPaths.getAncestor(next);

		}

		Collections.reverse(stradePercorse);
		return stradePercorse;

	}

	/**
	 * Returns the nodes list on the route
	 * 
	 * @return
	 */
	public List<Node> getRouteNodes() {

		List<Node> nodi = new ArrayList<Node>();

		Node intermedio = shortestPaths.getAncestor(shortestPaths.getEnd());

		while (intermedio != null) {
			nodi.add(intermedio);
			intermedio = shortestPaths.getAncestor(intermedio);

		}

		return nodi;
	}

	/**
	 * Returns the min distance between the start nod and other node
	 * 
	 * @param roadNetwork
	 * @param from
	 * @param to
	 * @return
	 */
	public int getDistance(Node n) {

		return shortestPaths.getMinDistance(n);
	}

	/**
	 * 
	 * 
	 * @param roadNetwork
	 * @param from
	 * @param to
	 * @return
	 */
	public int getMinDistance() {

		return shortestPaths.getMinDistance(shortestPaths.getEnd());
	}

}
