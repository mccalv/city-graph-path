/*
 * @(#)CityGraphApp.java     Jan 20, 2011
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
package com.mccalv.citygraph.app;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.mccalv.citygraph.graph.impl.RoadNetwork;
import com.mccalv.citygraph.graph.impl.Street;
import com.mccalv.citygraph.graph.impl.RoadNetwork.City;
import com.mccalv.citygraph.model.Intervention;
import com.mccalv.citygraph.model.Car;
import com.mccalv.citygraph.model.graph.Edge;
import com.mccalv.citygraph.model.graph.Node;
import com.mccalv.citygraph.model.graph.Path;
import com.mccalv.citygraph.parser.InputFileParser;

/**
 * A Graph Visualizer for the Application.
 * <p>
 * It uses swing library to draws the Network road. According to the adjacency
 * matrix of the city, renders is as regular geometric shape, with the street
 * representing the streets as lines between vertexes.
 * 
 * @author mccalv
 * @since Jan 20, 2011
 * 
 */
public class CityGraphApp extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3723440965220509541L;

	private RoadNetwork cittaCorrente;

	/**
	 * Getter for cittaCorrente.
	 * 
	 * @return the cittaCorrente.
	 */
	public RoadNetwork getCittaCorrente() {
		return cittaCorrente;
	}

	/**
	 * @param cittaCorrente
	 *            the cittaCorrente to set
	 */
	public void setCittaCorrente(City cittaCorrente) {
		this.cittaCorrente = InputFileParser
				.leggiGrafoStradaleCompleto(cittaCorrente);

	}

	private class ChiudiInterventoActionListener implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
		 * )
		 */
		@Override
		public void actionPerformed(ActionEvent arg0) {
			JPanel panel = new JPanel(new GridLayout(0, 1));

			panel.add(new JLabel("Intervention	Code"));
			JTextField codiceIntervento = new JTextField();

			JCheckBox chiudi_tutti_interventi = new JCheckBox("Close all");
			panel.add(codiceIntervento);

			panel.add(chiudi_tutti_interventi);
			int result = JOptionPane.showConfirmDialog(null, panel,
					"Edit intervention", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.PLAIN_MESSAGE);
			if (result == JOptionPane.OK_OPTION) {

				if (chiudi_tutti_interventi.isSelected()) {
					percorsoEvidenziato.clear();
					log.append("All interventions are close\n");
					cittaCorrente.closeAllInteventions();
					repaint();
				} else {
					cittaCorrente.closeIntervention(codiceIntervento.getText());
					log.append("Intervention " + codiceIntervento.getText()
							+ " closed\n");

					repaint();
				}
			} else {

			}

		}

	}

	private class InterventoActionListener implements ActionListener {

		public void actionPerformed(ActionEvent ae) {

			String[] items = new String[cittaCorrente.getEdges().size()];
			for (int i = 0; i < cittaCorrente.getEdges().size(); i++) {
				items[i] = cittaCorrente.getEdges().get(i).getInfoArco()
						.getName();
			}
			JComboBox combo = new JComboBox(items);

			String[] items_1 = { "1", "2", "3", "4", "5", "6", "7", "8", "9",
					"10" };
			JComboBox combo2 = new JComboBox(items_1);

			JPanel panel = new JPanel(new GridLayout(0, 1));

			panel.add(new JLabel("Street"));
			panel.add(combo);

			panel.add(new JLabel("Prority'"));
			panel.add(combo2);
			int result = JOptionPane.showConfirmDialog(null, panel,
					"Edit intervention", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.PLAIN_MESSAGE);
			if (result == JOptionPane.OK_OPTION) {
				percorsoEvidenziato.clear();
				int prorita = Integer.valueOf("" + combo2.getSelectedItem());

				int selectedIndex = combo.getSelectedIndex();

				Street arco = (Street) cittaCorrente.getEdges().get(
						selectedIndex);

				Intervention intervento = new Intervention(cittaCorrente, arco,
						prorita);
				List<Car> volantiCoinvolte = cittaCorrente
						.getAvailableCars(intervento);

				log.append(intervento + "- Car assigned:" + volantiCoinvolte
						+ "\n");
				repaint();

			} else {
				System.out.println("Cancelled");
			}

		}
	}

	/**
	 * Specific Listener to get manage the car movement.
	 * <p>
	 * Updates the position of single car and draws the paths (number of edges
	 * to visualize)
	 * 
	 * @author mccalv
	 * @since Jan 27, 2011
	 * 
	 */
	private class MoveCarActionListener implements ActionListener {

		public void actionPerformed(ActionEvent ae) {

			String[] strade = new String[cittaCorrente.getEdges().size() + 1];
			strade[0] = "";
			for (int i = 1; i <= cittaCorrente.getEdges().size(); i++) {
				strade[i] = cittaCorrente.getEdges().get(i - 1).getInfoArco()
						.getName();
			}

			String[] incroci = new String[cittaCorrente.getNodes().size() + 1];
			incroci[0] = "";
			for (int i = 1; i <= cittaCorrente.getNodes().size(); i++) {
				incroci[i] = cittaCorrente.getNodes().get(i - 1).toString();
			}
			JComboBox stradeCombo = new JComboBox(strade);

			String[] volanti = new String[cittaCorrente.getVolanti().size() + 1];
			volanti[0] = "";
			for (int i = 1; i <= cittaCorrente.getVolanti().size(); i++) {
				volanti[i] = cittaCorrente.getVolanti().get(i - 1)
						.getIdentifier();
			}
			JComboBox volantiCombo = new JComboBox(volanti);
			JComboBox nodiCombo = new JComboBox(incroci);

			JPanel panel = new JPanel(new GridLayout(0, 1));

			panel.add(new JLabel("Car"));
			panel.add(volantiCombo);
			panel.add(new JLabel("Street"));
			panel.add(stradeCombo);
			panel.add(new JLabel("Crossing"));
			panel.add(nodiCombo);
			int result = JOptionPane.showConfirmDialog(null, panel,
					"Move police car", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.PLAIN_MESSAGE);
			if (result == JOptionPane.OK_OPTION) {
				Node a = null;
				int volante = volantiCombo.getSelectedIndex() - 1;

				int stradeValue = stradeCombo.getSelectedIndex() - 1;

				int incrocioValue = nodiCombo.getSelectedIndex() - 1;

				if (incrocioValue >= 0) {
					a = cittaCorrente.getNodes().get(incrocioValue);
				} else {
					if (stradeValue >= 0) {
						Edge arco = cittaCorrente.getEdges().get(stradeValue);
						a = arco.getA();
					}
				}

				if (a != null) {
					Node from = cittaCorrente.getVolanti().get(volante)
							.getNodo();

					Path percorso = new Path(cittaCorrente, from, a);
					List<Edge> route = percorso.getRouteEdges();
					percorsoEvidenziato.clear();
					percorsoEvidenziato.addAll(route);
					log.append("Car moved "
							+ cittaCorrente.getVolanti().get(volante)
							+ " from node "
							+ cittaCorrente.getVolanti().get(volante).getNodo()
							+ " to node " + a + ". Route :" + route
							+ "[distance:" + percorso.getDistance(a) + "] \n");
					cittaCorrente.getVolanti().get(volante).setNodo(a);
					// Persiste le volanti su file
					cittaCorrente.saveCarsPositions();
					repaint();
				}

				// addVolante(10, coordinata, "" + volante);

			} else {

			}

		}
	}

	/**
	 * Switch the city and loads the relative graph
	 * 
	 * @see{@link ReteStradale.Citta}
	 * @author mccalv
	 * @since Jan 27, 2011
	 * 
	 */
	private class CityActionListener implements ActionListener {

		public void actionPerformed(ActionEvent ae) {

			String[] items = new String[City.values().length + 1];

			items[0] = "";
			for (int i = 1; i <= City.values().length; i++) {
				items[i] = City.values()[i - 1].name();
			}

			JComboBox combo = new JComboBox(items);

			JPanel panel = new JPanel(new GridLayout(0, 1));

			panel.add(new JLabel("City"));
			panel.add(combo);

			int result = JOptionPane.showConfirmDialog(null, panel,
					"Select city", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.PLAIN_MESSAGE);
			if (result == JOptionPane.OK_OPTION) {
				String value = (String) combo.getSelectedItem();
				if (!value.equals("")) {
					City citta = City.valueOf(value);
					setCittaCorrente(citta);
					repaint();
				}

			} else {

			}

		}
	}

	private final JButton btn_Intervento = new JButton("Intervention");
	private final JButton btn_Citta = new JButton("Change city");
	private final JButton btn_Volante = new JButton("Move Car");
	private final JButton btn_ChiudiIntervento = new JButton(
			"Close Intervention");
	private final List<Edge> percorsoEvidenziato = new ArrayList<Edge>();
	private final JTextArea log = new JTextArea("", 5, 60);

	int[] x_array;
	int[] y_array;

	final int PAD = 5;

	public void drawCar(int x, int y, String identifier, Intervention intervento) {

		Graphics2D g2 = (Graphics2D) getGraphics();

		g2.setColor(Color.CYAN);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setStroke(new BasicStroke(2));
		if (intervento == null) {
			g2.setColor(Color.BLUE);
		} else {
			g2.setColor(Color.RED);
			identifier += "-" + intervento.getIdentificativo();
		}
		Ellipse2D.Double box = new Ellipse2D.Double(x, y, 10, 10);

		// Ellipse2D.Double circle = new Ellipse2D.Double(x, y, 20, 20);
		g2.fill(box);

		g2.setPaint(Color.ORANGE);
		g2.draw(box);
		g2.setPaint(Color.BLACK);
		g2.translate(x + 5, y + 15);
		g2.rotate(Math.PI / 6);
		Font previous = getFont();
		g2.setFont(new Font("Arial", Font.PLAIN, 10));
		g2.drawString(identifier, 0, 0);

		g2.setFont(previous);
		g2.translate(-x - 5, -y - 15);
		g2.rotate(-Math.PI / 6);

	}

	/**
	 * Calcola la posizione del centro del rettangoli che rappresenta l'incrocio
	 * 
	 * @param g2
	 */
	public void calcolaPosizioneIncroci(Graphics2D g2) {
		int numeroIncroci = cittaCorrente.getNodes().size();
		x_array = new int[numeroIncroci];
		y_array = new int[numeroIncroci];
		int w = getWidth() - 40;
		int h = getHeight() - 20;
		int x_center = w / 2;
		int y_center = h / 2;
		int radius = 180;
		for (int i = 0; i < numeroIncroci; i++) {
			Node n = cittaCorrente.getNodes().get(i);
			double angle = (2 * Math.PI / numeroIncroci) * i;
			double randomFactor = radius;
			// Aggiunge un fattore random Math.random() * 1.7;
			double x = x_center - (Math.cos(angle) * randomFactor);
			double y = y_center - (Math.sin(angle) * randomFactor);

			String lbl = n.getNodeInfo().getIdentifier();

			int width = g2.getFontMetrics().stringWidth(lbl) + 10;
			int heigh = g2.getFontMetrics().getHeight() + 4;

			double x_rectangle = x - width / 2;
			double y_rectangle = y - heigh / 2;

			int x_rectangle_int = Math.round((float) x_rectangle);
			int y_rectangle_int = Math.round((float) y_rectangle);
			x_array[i] = x_rectangle_int;
			y_array[i] = y_rectangle_int;
		}
	}

	/**
	 * Draw a Rectangle representing a Crossing with its label
	 * 
	 * @param g2
	 * @param n
	 * @param i
	 * @param numeroDiIncroci
	 */
	public void addCrossing(Graphics2D g2, int i) {

		g2.setPaint(Color.gray);

		int x_rectangle_int = x_array[i];
		int y_rectangle_int = y_array[i];
		String lbl = cittaCorrente.getNodes().get(i).getNodeInfo()
				.getIdentifier();
		int width = g2.getFontMetrics().stringWidth(lbl) + 10;
		int heigh = g2.getFontMetrics().getHeight() + 4;
		g2.setColor(Color.CYAN);
		g2.fillRect(x_rectangle_int, y_rectangle_int, width, heigh);
		g2.setColor(Color.black);
		g2.drawRect(x_rectangle_int, y_rectangle_int, width - 1, heigh - 1);
		g2.drawString(lbl, x_rectangle_int + 1, (y_rectangle_int)
				+ g2.getFontMetrics().getAscent());

	}

	/**
	 * Refreshes and repaints the canvas
	 * 
	 */
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) getGraphics();
		g2.clearRect(0, 0, 800, 600);

		setBackground(Color.white);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		calcolaPosizioneIncroci(g2);
		drawEdges(g2);

		drawCrossings(g2);

		int x = 0;
		int y = 0;
		for (Car v : cittaCorrente.getVolanti()) {

			Node n = v.getNodo();
			int indice = cittaCorrente.index(n);
			int nAltreVolanti = cittaCorrente.indiceVolante(n, v);

			x = x_array[indice] + nAltreVolanti * 20;
			// Calcola il numero di volanti presenti sul nodo - la volante
			// stessa
			y = y_array[indice] + 20;
			drawCar(x, y, v.getIdentifier(), v.getIntervento());

		}

	}

	/**
	 * Draws all the crossing for the street
	 * 
	 * @param g2s
	 */
	private void drawCrossings(Graphics2D g2) {
		for (int i = 0; i < cittaCorrente.getNodes().size(); i++) {

			addCrossing(g2, i);
		}

	}

	/**
	 * Draw the edges(streets) that connect the crossings.
	 * Calculare the angle of the line and the relative label with the same rotation.
	 * 
	 * @param g2
	 */
	private void drawEdges(Graphics2D g2) {
		for (int i = 0; i < cittaCorrente.getEdges().size(); i++) {
			g2.setColor(Color.GRAY);
			Edge arco = cittaCorrente.getEdges().get(i);
			String lbl = arco.getInfoArco().getName() + " ("
					+ arco.getInfoArco().getTraveltime() + ")";

			int indiceNodoA = cittaCorrente.index(arco.getA());
			int indiceNodoB = cittaCorrente.index(arco.getB());
			Font previous = getFont();
			g2.setFont(new Font("Arial", Font.PLAIN, 10));
			int width = g2.getFontMetrics().stringWidth(lbl);
			int heigh = g2.getFontMetrics().getHeight();

			int line_x1 = x_array[indiceNodoA] + width / 2;
			int line_y1 = y_array[indiceNodoA] + heigh / 2;
			int line_x2 = x_array[indiceNodoB] + width / 2;
			int line_y2 = y_array[indiceNodoB] + heigh / 2;
			if (percorsoEvidenziato.contains(arco)) {
				g2.setColor(Color.MAGENTA);
			} else {
				g2.setColor(Color.DARK_GRAY);
			}

			g2.drawLine(line_x1, line_y1, line_x2, line_y2);

			double angle = 0;

			if (Math.abs(line_y2 - line_y1) > 0) {
				double d = (line_x2 - line_x1) * 1d / (line_y2 - line_y1) * 1d;
				angle = Math.PI / 2 - Math.atan(d);

				if (angle > Math.PI / 2) {
					// Rende tutte le inclinazioni omogenee
					angle = angle - Math.PI;
				}

			}
			float abs2 = line_y1 + (line_y2 - line_y1) / 2;
			float abs = line_x1 + (line_x2 - line_x1) / 2;

			g2.translate(abs, abs2);
			g2.rotate(angle);
			g2.drawString(lbl, 0, 0);
			g2.rotate(-angle);
			g2.translate(-abs, -abs2);
			g2.setFont(previous);

		}
	}

	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		CityGraphApp graphicData = new CityGraphApp();

		graphicData.setCittaCorrente(City.Roma);
		// graphicData.paint();
		f.add(graphicData);
		JPanel lowerPanel = new JPanel();
		JPanel upperPanel = new JPanel();
		upperPanel.setBackground(Color.LIGHT_GRAY);

		f.getContentPane().add(lowerPanel, "South");
		f.getContentPane().add(upperPanel, "North");

		JTextArea jTextArea = new JTextArea(
				"ASD:Mirko Calvaresi a Dijkstra's algorithm demonstrator");

		jTextArea.setBackground(Color.LIGHT_GRAY);

		jTextArea.setEditable(false);
		lowerPanel.add(new JScrollPane(graphicData.log));
		upperPanel.add(jTextArea);
		upperPanel.add(graphicData.btn_Intervento);
		upperPanel.add(graphicData.btn_ChiudiIntervento);
		upperPanel.add(graphicData.btn_Citta);
		upperPanel.add(graphicData.btn_Volante);

		graphicData.btn_Intervento
				.addActionListener(graphicData.new InterventoActionListener());
		graphicData.btn_Citta
				.addActionListener(graphicData.new CityActionListener());

		graphicData.btn_Volante
				.addActionListener(graphicData.new MoveCarActionListener());
		graphicData.btn_ChiudiIntervento
				.addActionListener(graphicData.new ChiudiInterventoActionListener());

		f.setSize(800, 600);
		f.setLocation(200, 200);
		f.setVisible(true);

	}
}
