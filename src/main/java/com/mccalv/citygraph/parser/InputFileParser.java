/*
 * @(#)InputFileParser     Jan 19, 2011
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
package com.mccalv.citygraph.parser;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.mccalv.citygraph.graph.impl.Crossing;
import com.mccalv.citygraph.graph.impl.RoadNetwork;
import com.mccalv.citygraph.graph.impl.RoadNetwork.City;
import com.mccalv.citygraph.model.Intervention;
import com.mccalv.citygraph.model.Car;
import com.mccalv.citygraph.model.graph.Graph;
import com.mccalv.citygraph.model.graph.EdgeInfo;
import com.mccalv.citygraph.model.graph.NodeInfo;
import com.mccalv.citygraph.model.graph.Node;

/**
 * Utility class for I/O operations
 * 
 * @author mccalv
 * @since Jan 19, 2011
 * 
 */
public class InputFileParser {

	/**
	 * Combina i risultati di {@link #leggiGrafo(City)} e
	 * {@link #leggiVolanti(City)} restituendo una {@link RoadNetwork}
	 * popolata con le {@link Car} che vi insistono
	 * 
	 * @param citta
	 * @return
	 */
	public static RoadNetwork leggiGrafoStradaleCompleto(City citta) {

		RoadNetwork grafoStradale = leggiGrafo(citta);
		grafoStradale.setVolanti(leggiVolanti(citta));
		grafoStradale.setCity(citta);
		return grafoStradale;
	}

	/**
	 * Analyzes an ASCII file and converts its content into a {@link Graph}. 
	 * <p>
	 * The original file is a triangula adjacency matrix matrix presented in the following format:i
	 * 
	 * <pre>
	 * Crossing 1,0,via Cavour-10,0,via Leopardi-40,0,via Carducci-6
	 * Crossing 2,0,0,via Garibali-40,0,via Rivoli-6,0
	 * Crossing 3,0,0,0,via Mazzini-25,Via Medici-15,0
	 * Crossing 4,0,0,0,0,via Ungharetti-14,via Pascoli-2
	 * Crossing 5,0,0,0,0,0,via Witgnestein-8
	 * Crossing 6,0,0,0,0,0,0Incrocio 1,0,via Cavour-10,0,via Leopardi-40,0,via Carducci-6
	 * Crossing 2,0,0,via Garibali-40,0,via Rivoli-6,0
	 * Crossing 3,0,0,0,via Mazzini-25,Via Medici-15,0
	 * Incrocio 4,0,0,0,0,via Ungharetti-14,via Pascoli-2
	 * Crossing 5,0,0,0,0,0,via Witgnestein-8
	 * Crossing 6,0,0,0,0,0,0
	 * </pre>
	 * 
	 * @param roma
	 * @return
	 */
	public static RoadNetwork leggiGrafo(City roma) {
		BufferedReader input = null;
		RoadNetwork grafo = new RoadNetwork(roma);

		try {

			FileReader fileReader = new FileReader(pathFilesDaClassPath()
					+ "/files/" + roma + "/CITTA");

			input = new BufferedReader(fileReader);

			int numeroDiRiga = 0;

			while (input.ready()) {
				String l = input.readLine();
				String[] lines = l.split(",");
				if (numeroDiRiga == 0) {
					// Creo tutti i nodi e gli attribuisco un nome temporaneo
					for (int i = 1; i < lines.length; i++) {
						grafo.addNode(new Crossing("temp" + i));

					}
				}
				for (int k = 0; k < lines.length; k++) {

					// La prima riga rappresenta il nome dell'incrocio che devo
					// creare
					if (k == 0) {
						grafo.getNodes().get(numeroDiRiga)
								.setNodeInfo(new NodeInfo(lines[k]));
					}
					// Gli altri n-1 split rappresentano eventuali archi
					if (!lines[k].equals("0") && k != 0) {
						String infoArco[] = lines[k].split("-");
						EdgeInfo info = new EdgeInfo(infoArco[0],
								Integer.valueOf(infoArco[1]));
						Node a = grafo.getNodes().get(numeroDiRiga);
						Node b = grafo.getNodes().get(k - 1);
						grafo.addEdge(a, b, info);

					}

				}
				numeroDiRiga++;
			}

		} catch (IOException e1) {
			e1.printStackTrace();
			throw new RuntimeException("Errore opening the file");
		} finally {
			try {
				if (input != null)
					input.close();

			} catch (IOException e3) {
				e3.printStackTrace();
				throw new RuntimeException("Errore opening the file");

			}
		}

		return grafo;
	}

	/**
	 * Ritorna il path relativo alla user dir
	 * 
	 * @return
	 */
	private static String pathFilesDaClassPath() {

		String root = System.getProperty("user.dir");
		//sInputFileParser.class
		return root;
	}

	/**
	 * Ritorna una lista di volanti e delle relative posizioni da un file
	 * testuale formattato secondo il seguente standard
	 * 
	 * <pre>
	 * zebra1,Incrocio 1
	 * zebra2,Incrocio 2
	 * zebra3,Incrocio 3
	 * puma1,Incrocio 4
	 * puma2,Incrocio 5
	 * puma3,Incrocio 6
	 * puma4,Incrocio 2
	 * puma5,Incrocio 4
	 * puma6,Incrocio 3
	 * </pre>
	 * 
	 * @return
	 */
	public static List<Car> leggiVolanti(City roma) {
		BufferedReader input = null;

		List<Car> volanti = new ArrayList<Car>();
		try {
			FileReader fileReader = new FileReader(pathFilesDaClassPath()
					+ "/files/" + roma + "/VOLANTI");

			input = new BufferedReader(fileReader);

			while (input.ready()) {

				String line = input.readLine();
				String[] splits = line.split(",");
				Car volante = new Car(splits[0],
						new Crossing(splits[1]));

				if (splits.length > 2) {
					volante.setIntervento(new Intervention(splits[3]));
					volante.setPosPreIntevento(new Crossing(splits[2]));

				}
				volanti.add(volante);

			}

		} catch (IOException e1) {
			e1.printStackTrace();
			throw new RuntimeException("Errore opening the file");
		} finally {
			try {
				if (input != null)
					input.close();

			} catch (IOException e3) {
				e3.printStackTrace();
				throw new RuntimeException("Errore opening the file");

			}
		}
		Collections.sort(volanti);
		return volanti;
	}

	/**
	 * Sostirui
	 * 
	 * @param reteStradale
	 */
	public static void updateVolanti(RoadNetwork reteStradale) {
		BufferedWriter out = null;

		StringBuilder stringBuilder = new StringBuilder();
		for (Car v : reteStradale.getVolanti()) {
			stringBuilder.append(v.getIdentifier());
			stringBuilder.append(",");
			stringBuilder.append(v.getNodo());
			stringBuilder.append(",");
			if (v.getPosPreIntevento() != null) {
				stringBuilder.append(v.getPosPreIntevento());
			}
			stringBuilder.append(",");
			if (v.getIntervento() != null) {
				stringBuilder.append(v.getIntervento().getIdentificativo());
			}
			stringBuilder.append("\n");
		}

		try {

			FileWriter fileWriter = new FileWriter(pathFilesDaClassPath()
					+ "/files/" + reteStradale.getCity() + "/VOLANTI");

			// fileWriter = new FileWriter("VOLANTI");
			out = new BufferedWriter(fileWriter);
			out.write(stringBuilder.toString());

		} catch (IOException e) {
			e.printStackTrace();
		}

		finally {
			try {
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
}
