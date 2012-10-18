/*
 * @(#)GrafoTest.java     Jan 20, 2011
 *
 * University of Tor Vergata, Faculty on Computer Engineering.
 * Examination of "Algoritmi e strutture dati".
 *
 */
package it.uniroma2.algoritimiestrutturedati.mccalv.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import it.uniroma2.algoritimiestrutturedati.mccalv.model.Volante;
import it.uniroma2.algoritimiestrutturedati.mccalv.model.graph.InfoArco;
import it.uniroma2.algoritimiestrutturedati.mccalv.model.graph.InfoNodo;
import it.uniroma2.algoritimiestrutturedati.mccalv.model.graph.Nodo;
import it.uniroma2.algoritimiestrutturedati.mccalv.model.graph.Percorso;
import it.uniroma2.algoritimiestrutturedati.mccalv.model.graph.impl.Incrocio;
import it.uniroma2.algoritimiestrutturedati.mccalv.model.graph.impl.ReteStradale;
import it.uniroma2.algoritimiestrutturedati.mccalv.model.graph.impl.ReteStradale.Citta;
import it.uniroma2.algoritimiestrutturedati.mccalv.model.route.CamminiMinimi;
import it.uniroma2.algoritimiestrutturedati.mccalv.parser.InputFileParser;

import java.util.List;

import org.junit.Test;

/**
 * 
 * @author mccalv
 * @since Jan 20, 2011
 * 
 */
public class GrafoTest {
	private final Citta CITTA_TEST = Citta.Roma;

	/**
	 * Test Generico per la creazione di un grafo.
	 * <p>
	 * I nodi sono rappresentati dagli incroci e gli archi dalle strade entrambe
	 * in doppio senso di percorrenza
	 */
	@Test
	public void testCreazioneGrafo() {

		ReteStradale c = new ReteStradale(Citta.Roma);
		Incrocio incrocio1 = new Incrocio(new InfoNodo("Incrocio1"));
		Incrocio incrocio2 = new Incrocio(new InfoNodo("Incrocio2"));
		c.aggiungiNodo(incrocio1);
		c.aggiungiNodo(incrocio1);
		c.aggiungiArcho(incrocio1, incrocio2, new InfoArco("via Roma", 20));

		assertNotNull(c.sonoAdiacenti(incrocio1, incrocio2));

		// La rimozione del nodo comporta anche la rimozione dell'arco/archi che
		// lo connettono
		c.rimuoviNodo(incrocio1);

		assertNull(c.sonoAdiacenti(incrocio1, incrocio2));

	}

	/**
	 * Test per il parsing di un grafo e del nodo volanti da un file testuale
	 */
	@Test
	public void testParsingGrafo() {
		ReteStradale citta = InputFileParser
				.leggiGrafoStradaleCompleto(CITTA_TEST);
		assertNotNull(citta.nodi());
		assertNotNull(citta.archi());

		List<Volante> volanti = citta.getVolanti();
		assertEquals(13, volanti.size());

	}

	/**
	 * Testa che un cambiamento delle volanti sia effettivamente persistito nel
	 * file
	 */
	@Test
	public void testWriteVolanti() {

		ReteStradale reteStradale = leggiReteStradaleDaFile(CITTA_TEST);
		reteStradale.getVolanti().get(0).setIdentifier("gazzella1");
		InputFileParser.updateVolanti(reteStradale);
		// Leggo di nuovo i dati dal file
		reteStradale = leggiReteStradaleDaFile(CITTA_TEST);
		assertEquals("gazzella1", reteStradale.getVolanti().get(0)
				.getIdentifier());

	}

	/**
	 * Test di integrazione per {@link CamminiMinimi}
	 */
	@Test
	public void testCamminiMinimi() {

		ReteStradale citta = leggiReteStradaleDaFile(CITTA_TEST);

		CamminiMinimi camminiMinimi = new CamminiMinimi(citta);
		// Incrocio 1
		Nodo start = citta.nodi().get(2);

		camminiMinimi.calcolaDistanzaMinima(start, null);
		// Incrocio 5
		Nodo end = citta.nodi().get(5);

		assertEquals(23, camminiMinimi.getDistanzaMinima(end));
		camminiMinimi.getPredecessore(end);

		Percorso percorso = new Percorso(citta, start, end);
		List<Nodo> percorsoMigliore = percorso.getIncrociPercorso();
		assertTrue(percorsoMigliore.contains(new Incrocio("Incrocio 3")));
		assertTrue(percorsoMigliore.contains(new Incrocio("Incrocio 5")));

	}

	/**
	 * Metodo di utilità per leggere una rete stradale da file
	 * 
	 * @param citta
	 * @return
	 */
	private ReteStradale leggiReteStradaleDaFile(Citta citta) {
		ReteStradale reteStradale = InputFileParser
				.leggiGrafoStradaleCompleto(citta);
		return reteStradale;
	}

}
