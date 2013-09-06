/*
 * @(#)GrafoTest.java     Jan 20, 2011
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
 *
 *
 */
package com.mccalv.citygrah.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.mccalv.citygraph.graph.impl.Crossing;
import com.mccalv.citygraph.graph.impl.RoadNetwork;
import com.mccalv.citygraph.graph.impl.RoadNetwork.City;
import com.mccalv.citygraph.model.Car;
import com.mccalv.citygraph.model.graph.EdgeInfo;
import com.mccalv.citygraph.model.graph.NodeInfo;
import com.mccalv.citygraph.model.graph.Node;
import com.mccalv.citygraph.model.graph.Path;
import com.mccalv.citygraph.model.route.ShortestPath;
import com.mccalv.citygraph.parser.InputFileParser;

/**
 * 
 * @author mccalv
 * @since Jan 20, 2011
 * 
 */
public class GraphTest {
	private final City CITTA_TEST = City.Roma;

	/**
	 * Test Generico per la creazione di un grafo.
	 * <p>
	 * I nodi sono rappresentati dagli incroci e gli archi dalle strade entrambe
	 * in doppio senso di percorrenza
	 */
	@Test
	public void testCreazioneGrafo() {

		RoadNetwork c = new RoadNetwork(City.Roma);
		Crossing incrocio1 = new Crossing(new NodeInfo("Incrocio1"));
		Crossing incrocio2 = new Crossing(new NodeInfo("Incrocio2"));
		c.addNode(incrocio1);
		c.addNode(incrocio1);
		c.addEdge(incrocio1, incrocio2, new EdgeInfo("via Roma", 20));

		assertNotNull(c.areAdjacents(incrocio1, incrocio2));

		// La rimozione del nodo comporta anche la rimozione dell'arco/archi che
		// lo connettono
		c.removeNode(incrocio1);

		assertNull(c.areAdjacents(incrocio1, incrocio2));

	}

	/**
	 * Test per il parsing di un grafo e del nodo volanti da un file testuale
	 */
	@Test
	public void testParsingGrafo() {
		RoadNetwork citta = InputFileParser
				.leggiGrafoStradaleCompleto(CITTA_TEST);
		assertNotNull(citta.getNodes());
		assertNotNull(citta.getEdges());

		List<Car> volanti = citta.getVolanti();
		assertEquals(13, volanti.size());

	}

	/**
	 * Testa che un cambiamento delle volanti sia effettivamente persistito nel
	 * file
	 */
	@Test
	public void testWriteVolanti() {

		RoadNetwork reteStradale = leggiReteStradaleDaFile(CITTA_TEST);
		reteStradale.getVolanti().get(0).setIdentifier("gazzella1");
		InputFileParser.updateVolanti(reteStradale);
		// Leggo di nuovo i dati dal file
		reteStradale = leggiReteStradaleDaFile(CITTA_TEST);
		assertEquals("gazzella1", reteStradale.getVolanti().get(0)
				.getIdentifier());

	}

	/**
	 * Test di integrazione per {@link ShortestPath}
	 */
	@Test
	public void testShortestPath() {

		RoadNetwork citta = leggiReteStradaleDaFile(CITTA_TEST);

		ShortestPath camminiMinimi = new ShortestPath(citta);
		// Incrocio 1
		Node start = citta.getNodes().get(2);

		camminiMinimi.computeShortestPath(start, null);
		// Incrocio 5
		Node end = citta.getNodes().get(5);
System.out.println(camminiMinimi.getMinDistance(end));
		assertEquals(23, camminiMinimi.getMinDistance(end));
		camminiMinimi.getAncestor(end);

		Path percorso = new Path(citta, start, end);
		List<Node> percorsoMigliore = percorso.getRouteNodes();
		assertTrue(percorsoMigliore.contains(new Crossing("Incrocio 3")));
		assertTrue(percorsoMigliore.contains(new Crossing("Incrocio 5")));

	}

	/**
	 * Metodo di utilità per leggere una rete stradale da file
	 * 
	 * @param citta
	 * @return
	 */
	private RoadNetwork leggiReteStradaleDaFile(City citta) {
		RoadNetwork reteStradale = InputFileParser
				.leggiGrafoStradaleCompleto(citta);
		return reteStradale;
	}

}
