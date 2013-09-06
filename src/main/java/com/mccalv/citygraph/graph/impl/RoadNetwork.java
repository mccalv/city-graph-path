/*
 * @(#)RoadNetwork.java     Jan 19, 2011
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import com.mccalv.citygraph.model.Intervention;
import com.mccalv.citygraph.model.Car;
import com.mccalv.citygraph.model.graph.Edge;
import com.mccalv.citygraph.model.graph.Graph;
import com.mccalv.citygraph.model.graph.EdgeInfo;
import com.mccalv.citygraph.model.graph.NodeInfo;
import com.mccalv.citygraph.model.graph.Node;
import com.mccalv.citygraph.model.route.ShortestPath;
import com.mccalv.citygraph.parser.InputFileParser;

/**
 * {@link Graph} representing a city with a list of nodes and edges
 * 
 * @author mccalv
 * @since Jan 19, 2011
 * 
 */
public class RoadNetwork implements Graph {

	private LinkedList<Node> nodes = new LinkedList<Node>();
	private LinkedList<Edge> edges = new LinkedList<Edge>();

	private List<Car> cars = new ArrayList<Car>();

	/**
	 * 
	 * @param citta
	 */
	public RoadNetwork(City city) {
		this.citta = city;
	}

	public static enum City {

		Roma, Milano, Napoli
	}

	/**
	 * Nome della citta corrispondente alla rete stradale
	 */
	private City citta;

	/**
	 * Getter for citta.
	 * 
	 * @return the citta.
	 */
	public City getCity() {
		return citta;
	}

	/**
	 * @param citta
	 *            the citta to set
	 */
	public void setCity(City citta) {
		this.citta = citta;
	}

	/*
	 * 
	 * 
	 * (non-Javadoc)
	 * 
	 * @see it.uniroma2.algoritimiestrutturedati.Grafo#numNodi()
	 */
	@Override
	public int getNodesNumber() {
		return nodes.size();

	}

	@Override
	public int getEdgesNumber() {
		return edges.size();
	}

	@Override
	public List<Node> getNodes() {

		return nodes;
	}

	@Override
	public List<Edge> getEdges() {
		return edges;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.uniroma2.algoritimiestrutturedati.Grafo#infoNodo(it.uniroma2.
	 * algoritimiestrutturedati.Nodo)
	 */
	@Override
	public NodeInfo getNodeInfo(Node v) {
		return v.getNodeInfo();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.uniroma2.algoritimiestrutturedati.Grafo#infoArco(it.uniroma2.
	 * algoritimiestrutturedati.Arco)
	 */
	@Override
	public EdgeInfo getEdgeInfo(Edge v) {
		return v.getInfoArco();
	}

	@Override
	public List<Edge> nodeEdges(Node v) {
		List<Edge> arcoUscenti = new ArrayList<Edge>();

		for (Edge arco : edges) {
			if ((arco.getA().equals(v) || arco.getB().equals(v))
					&& !arcoUscenti.contains(arco)) {
				arcoUscenti.add(arco);
			}

		}
		return arcoUscenti;
	}

	
	@Override
	public Edge areAdjacents(Node x, Node y) {
		// Due nodi sono adiacenti se un arco che li congiunge
		for (Edge a : edges) {

			if ((a.getA().equals(x) && a.getB().equals(y))
					|| (a.getA().equals(y) && a.getB().equals(x))) {
				return a;
			}

		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.mccalv.citygraph.model.graph.Graph#updateNodeInfo(com.mccalv.citygraph.model.graph.Node, com.mccalv.citygraph.model.graph.NodeInfo)
	 */
	@Override
	public void updateNodeInfo(Node v, NodeInfo info) {
		v.setNodeInfo(info);
		for (Node nodo : nodes) {
			if (nodo.equals(v)) {
				nodo = v;
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.uniroma2.algoritimiestrutturedati.Grafo#nodo(int)
	 */
	@Override
	public Node nodo(int indice) {
		return nodes.get(indice);
	}

	/*
	 * (non-Javadoc)
	 * @see com.mccalv.citygraph.model.graph.Graph#index(com.mccalv.citygraph.model.graph.Node)
	 */
	@Override
	public int index(Node nodo) {
		for (int i = 0; i < getNodes().size(); i++) {

			if (nodo.equals(getNodes().get(i))) {
				return i;
			}
		}
		return 0;
	}

/*
 * (non-Javadoc)
 * @see com.mccalv.citygraph.model.graph.Graph#addNode(com.mccalv.citygraph.model.graph.Node)
 */
	@Override
	public Node addNode(Node nodo) {
		// Controlla se il nodo non è già presente nella lista
		if (!nodes.contains(nodo)) {
			nodes.add(nodo);
		}
		return nodo;
	}

	/*
	 * (non-Javadoc)
	 * @see com.mccalv.citygraph.model.graph.Graph#addEdge(com.mccalv.citygraph.model.graph.Node, com.mccalv.citygraph.model.graph.Node, com.mccalv.citygraph.model.graph.EdgeInfo)
	 */
	@Override
	public Edge addEdge(Node x, Node y, EdgeInfo info) {
		Edge strada = new Street(x, y, info);
		if (!edges.contains(strada)) {
			edges.add(strada);
		}
		return strada;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.uniroma2.algoritimiestrutturedati.Grafo#rimuoviNodo(it.uniroma2.
	 * algoritimiestrutturedati.Nodo)
	 */
	@Override
	public void removeNode(Node v) {
		nodes.remove(v);
		// Utilizzo un iteratore per evitare problemi di concurrent modification
		Iterator<Edge> it = edges.iterator();
		while (it.hasNext()) {
			Edge arco = it.next();
			if (arco.getA().equals(v) || arco.getB().equals(v)) {
				it.remove();
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.uniroma2.algoritimiestrutturedati.Grafo#rimuoviArco(it.uniroma2.
	 * algoritimiestrutturedati.Arco)
	 */
	@Override
	public void removeEdge(Edge a) {
		edges.remove(a);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.uniroma2.algoritimiestrutturedati.mccalv.model.graph.Grafo#getDestinazioni
	 * (it.uniroma2.algoritimiestrutturedati.mccalv.model.graph.Nodo)
	 */
	@Override
	public List<Node> getDestinations(Node nodo) {
		List<Edge> archi = nodeEdges(nodo);
		List<Node> nodi = new ArrayList<Node>();
		for (Edge arco : archi) {
			// Il nodo di destinazione è il terminale del grafo che non
			// coincide
			// con il nodo uscente
			Node nodoUscente = arco.getA().equals(nodo) ? arco.getB() : arco
					.getA();
			nodi.add(nodoUscente);
		}
		return nodi;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.uniroma2.algoritimiestrutturedati.mccalv.model.graph.Grafo#
	 * calcolaDistanzaTraNodi
	 * (it.uniroma2.algoritimiestrutturedati.mccalv.model.graph.Nodo,
	 * it.uniroma2.algoritimiestrutturedati.mccalv.model.graph.Nodo)
	 */
	@Override
	public Integer computeNodeDistance(Node u, Node v) {
		List<Edge> archi = nodeEdges(v);
		for (Edge arco : archi) {
			if ((arco.getA().equals(v) && arco.getB().equals(u))
					|| (arco.getB().equals(v) && arco.getA().equals(u))) {

				return arco.getInfoArco().getTraveltime();
			}

		}
		return null;
	}

	/**
	 * Number of cars for crossing
	 * 
	 * @param n
	 * @return
	 */
	public List<Car> carsForNode(Node n) {
		List<Car> volantiSuNodo = new ArrayList<Car>();
		for (Car v : cars) {

			if (v.getNodo().equals(n)) {
				volantiSuNodo.add(v);

			}

		}
		return volantiSuNodo;

	}

	/**
	 * Ritorna l'indice della volante se presente su nodo, zero altrimenti
	 * 
	 * @param n
	 * @return
	 */
	public int indiceVolante(Node n, Car volante) {
		int index = 0;

		List<Car> volantisuNodo = carsForNode(n);

		for (Car v : volantisuNodo) {

			if (volante.equals(v)) {
				return index;

			}
			index++;
		}
		return index;

	}

	/**
	 * Getter for volanti.
	 * 
	 * @return the volanti.
	 */
	public List<Car> getVolanti() {
		return cars;
	}

	/**
	 * @param volanti
	 *            the volanti to set
	 */
	public void setVolanti(List<Car> volanti) {
		this.cars = volanti;
	}

	/**
	 * Saves cars position to the file
	 * 
	 */
	public void saveCarsPositions() {

		InputFileParser.updateVolanti(this);
	}

	/**
	 * Returns the cars available
	 */
	public List<Car> getAvailableCars(Intervention intervento) {
		List<Car> volantiDisponibili = carsAvalaiable(intervento);

		updateCarsPositions(volantiDisponibili);
		return volantiDisponibili;
		/* Sincronizza le volanti */

	}

	private void updateCarsPositions(List<Car> volantiDisponibili) {

		ListIterator<Car> it = cars.listIterator();

		while (it.hasNext()) {
			Car v = it.next();
			for (Car volanteIntervento : volantiDisponibili) {

				if (volanteIntervento.equals(v)) {
					it.set(volanteIntervento);
				}
			}
		}
		saveCarsPositions();

	}

	/**
	 * Ritorna una lista di Volanti disponibili ordinata per vicinanza rispetto
	 * al punto di intervento
	 * 
	 * @param intervento
	 * @return
	 */
	private List<Car> carsAvalaiable(Intervention intervento) {

		int livelloProrita = intervento.getPrority();
		int volantiAssegnate = 0;

		List<Car> volantiOrdered = new ArrayList<Car>();

		Node nodoIntervento = intervento.getNodo().getA();

		ShortestPath camminiMinimi = new ShortestPath(this);
		// Incrocio 1

		camminiMinimi.computeShortestPath(nodoIntervento, null);

		Map<Node, Integer> distanzeMinimeOrdinate = camminiMinimi
				.getOrderedMinDistances();

		Iterator<Node> it = distanzeMinimeOrdinate.keySet().iterator();

		while (it.hasNext()) {
			Node intermedio = it.next();

			List<Car> volantisuNodo = carsForNode(intermedio);

			for (Car v : volantisuNodo) {
				if (v.getPosPreIntevento() == null) {
					v.setIntervento(intervento);
					v.setPosPreIntevento(v.getNodo());
					v.setNodo(intervento.getNodo().getA());
					v.setDistanzaIntervento(distanzeMinimeOrdinate
							.get(intermedio));

					volantiAssegnate++;
					volantiOrdered.add(v);
				}

				if ((livelloProrita == volantiAssegnate)
						&& livelloProrita != 10) {

					return volantiOrdered;
				}
			}

		}

		return volantiOrdered;
	}

	/**
	 * Close a single intervention an reset involved car position
	 * 
	 * @param identificativo
	 *            dell'intervento
	 */
	public void closeIntervention(String identificativo) {
		ListIterator<Car> it = cars.listIterator();

		while (it.hasNext()) {
			Car v = it.next();

			if (v.getIntervento() != null
					&& v.getIntervento().getIdentificativo()
							.equals(identificativo)) {
				v.setNodo(v.getPosPreIntevento());
				v.setPosPreIntevento(null);
				v.setIntervento(null);
				v.setDistanzaIntervento(null);
				it.set(v);
			}
		}

		saveCarsPositions();

	}

	/**
	 * Close all interventions and reset car positions
	 */
	public void closeAllInteventions() {
		ListIterator<Car> it = cars.listIterator();

		while (it.hasNext()) {
			Car v = it.next();

			if (v.getIntervento() != null) {
				v.setNodo(v.getPosPreIntevento());
				v.setPosPreIntevento(null);
				v.setIntervento(null);
				it.set(v);
			}
		}

		saveCarsPositions();

	}

}
