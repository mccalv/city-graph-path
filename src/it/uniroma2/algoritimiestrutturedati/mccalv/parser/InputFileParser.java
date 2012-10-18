/*
 * @(#)InputFileParser     Jan 19, 2011
 *
 * University of Tor Vergata, Faculty on Computer Engineering.
 * Examination of "Algoritmi e strutture dati".
 *
 */
package it.uniroma2.algoritimiestrutturedati.mccalv.parser;

import it.uniroma2.algoritimiestrutturedati.mccalv.model.Intervento;
import it.uniroma2.algoritimiestrutturedati.mccalv.model.Volante;
import it.uniroma2.algoritimiestrutturedati.mccalv.model.graph.Grafo;
import it.uniroma2.algoritimiestrutturedati.mccalv.model.graph.InfoArco;
import it.uniroma2.algoritimiestrutturedati.mccalv.model.graph.InfoNodo;
import it.uniroma2.algoritimiestrutturedati.mccalv.model.graph.Nodo;
import it.uniroma2.algoritimiestrutturedati.mccalv.model.graph.impl.Incrocio;
import it.uniroma2.algoritimiestrutturedati.mccalv.model.graph.impl.ReteStradale;
import it.uniroma2.algoritimiestrutturedati.mccalv.model.graph.impl.ReteStradale.Citta;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class di utilità per le operazioni di I/O sui files testuali
 * 
 * @author mccalv
 * @since Jan 19, 2011
 * 
 */
public class InputFileParser {

	/**
	 * Combina i risultati di {@link #leggiGrafo(Citta)} e
	 * {@link #leggiVolanti(Citta)} restituendo una {@link ReteStradale}
	 * popolata con le {@link Volante} che vi insistono
	 * 
	 * @param citta
	 * @return
	 */
	public static ReteStradale leggiGrafoStradaleCompleto(Citta citta) {

		ReteStradale grafoStradale = leggiGrafo(citta);
		grafoStradale.setVolanti(leggiVolanti(citta));
		grafoStradale.setCitta(citta);
		return grafoStradale;
	}

	/**
	 * Questo metodo analizza un file testuale rappresentante un Grafo/Citta' e
	 * lo convert in una struttura di tipo {@link Grafo}. il formato del file in
	 * origine è una matrice di adiacenza triangolare superiore del tipo
	 * seguente
	 * 
	 * <pre>
	 * Incrocio 1,0,via Cavour-10,0,via Leopardi-40,0,via Carducci-6
	 * Incrocio 2,0,0,via Garibali-40,0,via Rivoli-6,0
	 * Incrocio 3,0,0,0,via Mazzini-25,Via Medici-15,0
	 * Incrocio 4,0,0,0,0,via Ungharetti-14,via Pascoli-2
	 * Incrocio 5,0,0,0,0,0,via Witgnestein-8
	 * Incrocio 6,0,0,0,0,0,0Incrocio 1,0,via Cavour-10,0,via Leopardi-40,0,via Carducci-6
	 * Incrocio 2,0,0,via Garibali-40,0,via Rivoli-6,0
	 * Incrocio 3,0,0,0,via Mazzini-25,Via Medici-15,0
	 * Incrocio 4,0,0,0,0,via Ungharetti-14,via Pascoli-2
	 * Incrocio 5,0,0,0,0,0,via Witgnestein-8
	 * Incrocio 6,0,0,0,0,0,0
	 * </pre>
	 * 
	 * @param roma
	 * @return
	 */
	public static ReteStradale leggiGrafo(Citta roma) {
		BufferedReader input = null;
		ReteStradale grafo = new ReteStradale(roma);

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
						grafo.aggiungiNodo(new Incrocio("temp" + i));

					}
				}
				for (int k = 0; k < lines.length; k++) {

					// La prima riga rappresenta il nome dell'incrocio che devo
					// creare
					if (k == 0) {
						grafo.nodi().get(numeroDiRiga)
								.setInfoNodo(new InfoNodo(lines[k]));
					}
					// Gli altri n-1 split rappresentano eventuali archi
					if (!lines[k].equals("0") && k != 0) {
						String infoArco[] = lines[k].split("-");
						InfoArco info = new InfoArco(infoArco[0],
								Integer.valueOf(infoArco[1]));
						Nodo a = grafo.nodi().get(numeroDiRiga);
						Nodo b = grafo.nodi().get(k - 1);
						grafo.aggiungiArcho(a, b, info);

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
		;
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
	public static List<Volante> leggiVolanti(Citta roma) {
		BufferedReader input = null;

		List<Volante> volanti = new ArrayList<Volante>();
		try {
			FileReader fileReader = new FileReader(pathFilesDaClassPath()
					+ "/files/" + roma + "/VOLANTI");

			input = new BufferedReader(fileReader);

			while (input.ready()) {

				String line = input.readLine();
				String[] splits = line.split(",");
				Volante volante = new Volante(splits[0],
						new Incrocio(splits[1]));

				if (splits.length > 2) {
					volante.setIntervento(new Intervento(splits[3]));
					volante.setPosPreIntevento(new Incrocio(splits[2]));

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
	public static void updateVolanti(ReteStradale reteStradale) {
		BufferedWriter out = null;

		StringBuilder stringBuilder = new StringBuilder();
		for (Volante v : reteStradale.getVolanti()) {
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
					+ "/files/" + reteStradale.getCitta() + "/VOLANTI");

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
