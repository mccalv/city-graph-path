/*
 * @(#)GraphicData.java     Jan 20, 2011
 *
 * University of Tor Vergata, Faculty on Informatic Engeneering.
 * Examination of "Algoritmi e strutture dati".
 *
 */
package it.uniroma2.algoritimiestrutturedati.mccalv.app;

import it.uniroma2.algoritimiestrutturedati.mccalv.model.Intervento;
import it.uniroma2.algoritimiestrutturedati.mccalv.model.Volante;
import it.uniroma2.algoritimiestrutturedati.mccalv.model.graph.Arco;
import it.uniroma2.algoritimiestrutturedati.mccalv.model.graph.Nodo;
import it.uniroma2.algoritimiestrutturedati.mccalv.model.graph.Percorso;
import it.uniroma2.algoritimiestrutturedati.mccalv.model.graph.impl.ReteStradale;
import it.uniroma2.algoritimiestrutturedati.mccalv.model.graph.impl.ReteStradale.Citta;
import it.uniroma2.algoritimiestrutturedati.mccalv.model.graph.impl.Strada;
import it.uniroma2.algoritimiestrutturedati.mccalv.parser.InputFileParser;

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

/**
 * Visualizzatore Grafico del progetto Volanti
 * 
 * @author mccalv
 * @since Jan 20, 2011
 * 
 */
public class VolantiApp extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3723440965220509541L;

	private ReteStradale cittaCorrente;

	/**
	 * Getter for cittaCorrente.
	 * 
	 * @return the cittaCorrente.
	 */
	public ReteStradale getCittaCorrente() {
		return cittaCorrente;
	}

	/**
	 * @param cittaCorrente
	 *            the cittaCorrente to set
	 */
	public void setCittaCorrente(Citta cittaCorrente) {
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

			JCheckBox chiudi_tutti_interventi = new JCheckBox(
					"Close all");
			panel.add(codiceIntervento);

			panel.add(chiudi_tutti_interventi);
			int result = JOptionPane.showConfirmDialog(null, panel,
					"Edit intervention",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
			if (result == JOptionPane.OK_OPTION) {

				if (chiudi_tutti_interventi.isSelected()) {
					percorsoEvidenziato.clear();
					log.append("All interventions are close\n");
					cittaCorrente.chiudiTuttiInterventi();
					repaint();
				} else {
					cittaCorrente.chiudiIntervento(codiceIntervento.getText());
					log.append("Intervention "
							+ codiceIntervento.getText() + " closed\n");

					repaint();
				}
			} else {

			}

		}

	}

	private class InterventoActionListener implements ActionListener {

		public void actionPerformed(ActionEvent ae) {

			String[] items = new String[cittaCorrente.archi().size()];
			for (int i = 0; i < cittaCorrente.archi().size(); i++) {
				items[i] = cittaCorrente.archi().get(i).getInfoArco().getNome();
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
					"Edit intervention",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
			if (result == JOptionPane.OK_OPTION) {
				percorsoEvidenziato.clear();
				int prorita = Integer.valueOf("" + combo2.getSelectedItem());

				int selectedIndex = combo.getSelectedIndex();

				Strada arco = (Strada) cittaCorrente.archi().get(selectedIndex);

				Intervento intervento = new Intervento(cittaCorrente, arco,
						prorita);
				List<Volante> volantiCoinvolte = cittaCorrente
						.aggiungiIntevento(intervento);

				log.append(intervento + "- Car assigned:" + volantiCoinvolte + "\n");
				repaint();

			} else {
				System.out.println("Cancelled");
			}

		}
	}

	/**
	 * Listener per l'azione sposta volante. Aggiorna la posizione della singola
	 * volante e disegna a video il percorso in termini di archi da visualizzare
	 * 
	 * @author mccalv
	 * @since Jan 27, 2011
	 * 
	 */
	private class SpostaVolanteActionListener implements ActionListener {

		public void actionPerformed(ActionEvent ae) {

			String[] strade = new String[cittaCorrente.archi().size() + 1];
			strade[0] = "";
			for (int i = 1; i <= cittaCorrente.archi().size(); i++) {
				strade[i] = cittaCorrente.archi().get(i - 1).getInfoArco()
						.getNome();
			}

			String[] incroci = new String[cittaCorrente.nodi().size() + 1];
			incroci[0] = "";
			for (int i = 1; i <= cittaCorrente.nodi().size(); i++) {
				incroci[i] = cittaCorrente.nodi().get(i - 1).toString();
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
					"Move police car",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
			if (result == JOptionPane.OK_OPTION) {
				Nodo a = null;
				int volante = volantiCombo.getSelectedIndex() - 1;

				int stradeValue = stradeCombo.getSelectedIndex() - 1;

				int incrocioValue = nodiCombo.getSelectedIndex() - 1;

				if (incrocioValue >= 0) {
					a = cittaCorrente.nodi().get(incrocioValue);
				} else {
					if (stradeValue >= 0) {
						Arco arco = cittaCorrente.archi().get(stradeValue);
						a = arco.getA();
					}
				}

				if (a != null) {
					Nodo from = cittaCorrente.getVolanti().get(volante)
							.getNodo();

					Percorso percorso = new Percorso(cittaCorrente, from, a);
					List<Arco> route = percorso.getListaStrade();
					percorsoEvidenziato.clear();
					percorsoEvidenziato.addAll(route);
					log.append("Car moved "
							+ cittaCorrente.getVolanti().get(volante)
							+ " from node "
							+ cittaCorrente.getVolanti().get(volante).getNodo()
							+ " to node " + a + ". Route :" + route + "[distance:"
							+ percorso.getDistanza(a) + "] \n");
					cittaCorrente.getVolanti().get(volante).setNodo(a);
					// Persiste le volanti su file
					cittaCorrente.salvaVolantiSuFile();
					repaint();
				}

				// addVolante(10, coordinata, "" + volante);

			} else {

			}

		}
	}

	/**
	 * Cambia la corrente città e ne carica il relativo grafico.
	 * 
	 * @see{@link ReteStradale.Citta}
	 * @author mccalv
	 * @since Jan 27, 2011
	 * 
	 */
	private class CittaActionListener implements ActionListener {

		public void actionPerformed(ActionEvent ae) {

			String[] items = new String[Citta.values().length + 1];

			items[0] = "";
			for (int i = 1; i <= Citta.values().length; i++) {
				items[i] = Citta.values()[i - 1].name();
			}

			JComboBox combo = new JComboBox(items);

			JPanel panel = new JPanel(new GridLayout(0, 1));

			panel.add(new JLabel("City"));
			panel.add(combo);

			int result = JOptionPane.showConfirmDialog(null, panel,
					"Select city",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
			if (result == JOptionPane.OK_OPTION) {
				String value = (String) combo.getSelectedItem();
				if (!value.equals("")) {
					Citta citta = Citta.valueOf(value);
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
	private final List<Arco> percorsoEvidenziato = new ArrayList<Arco>();
	private final JTextArea log = new JTextArea("", 5, 60);

	int[] x_array;
	int[] y_array;

	final int PAD = 5;

	public void disegnaVolante(int x, int y, String identifier,
			Intervento intervento) {

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
		int numeroIncroci = cittaCorrente.nodi().size();
		x_array = new int[numeroIncroci];
		y_array = new int[numeroIncroci];
		int w = getWidth() - 40;
		int h = getHeight() - 20;
		int x_center = w / 2;
		int y_center = h / 2;
		int radius = 180;
		for (int i = 0; i < numeroIncroci; i++) {
			Nodo n = cittaCorrente.nodi().get(i);
			double angle = (2 * Math.PI / numeroIncroci) * i;
			double randomFactor = radius;
			// Aggiunge un fattore random Math.random() * 1.7;
			double x = x_center - (Math.cos(angle) * randomFactor);
			double y = y_center - (Math.sin(angle) * randomFactor);

			String lbl = n.getInfoNodo().getIdentificativo();

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
	 * Disegna un rettangolo rappresentante l'incrocio
	 * 
	 * @param g2
	 * @param n
	 * @param i
	 * @param numeroDiIncroci
	 */
	public void aggiungiIncrocio(Graphics2D g2, int i) {

		g2.setPaint(Color.gray);

		int x_rectangle_int = x_array[i];
		int y_rectangle_int = y_array[i];
		String lbl = cittaCorrente.nodi().get(i).getInfoNodo()
				.getIdentificativo();
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
	 * Metodo per fare un refresh della schermata e ridisegnare il tutto
	 */
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) getGraphics();
		g2.clearRect(0, 0, 800, 600);

		setBackground(Color.white);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		calcolaPosizioneIncroci(g2);
		disegnaArchi(g2);

		disegnaIncroci(g2);

		int x = 0;
		int y = 0;
		for (Volante v : cittaCorrente.getVolanti()) {

			Nodo n = v.getNodo();
			int indice = cittaCorrente.indice(n);
			int nAltreVolanti = cittaCorrente.indiceVolante(n, v);

			x = x_array[indice] + nAltreVolanti * 20;
			// Calcola il numero di volanti presenti sul nodo - la volante
			// stessa
			y = y_array[indice] + 20;
			disegnaVolante(x, y, v.getIdentifier(), v.getIntervento());

		}

	}

	/**
	 * Disegna i rettangoli rappresentanti gli incroci sulla mappa
	 * 
	 * @param g2
	 */
	private void disegnaIncroci(Graphics2D g2) {
		for (int i = 0; i < cittaCorrente.nodi().size(); i++) {

			aggiungiIncrocio(g2, i);
		}

	}

	/**
	 * Disegna gli archi (strade) che connettono gli incroci. Calcola anche la
	 * pendenza dell'arco e disegna l'etichetta adagiata sulla pendenza
	 * calcolata
	 * 
	 * @param g2
	 */
	private void disegnaArchi(Graphics2D g2) {
		for (int i = 0; i < cittaCorrente.archi().size(); i++) {
			g2.setColor(Color.GRAY);
			Arco arco = cittaCorrente.archi().get(i);
			String lbl = arco.getInfoArco().getNome() + " ("
					+ arco.getInfoArco().getTempiPercorrenza() + ")";

			int indiceNodoA = cittaCorrente.indice(arco.getA());
			int indiceNodoB = cittaCorrente.indice(arco.getB());
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
		VolantiApp graphicData = new VolantiApp();

		graphicData.setCittaCorrente(Citta.Roma);
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
				.addActionListener(graphicData.new CittaActionListener());

		graphicData.btn_Volante
				.addActionListener(graphicData.new SpostaVolanteActionListener());
		graphicData.btn_ChiudiIntervento
				.addActionListener(graphicData.new ChiudiInterventoActionListener());

		f.setSize(800, 600);
		f.setLocation(200, 200);
		f.setVisible(true);

	}
}
