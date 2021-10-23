package collisions;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import org.jfree.ui.RefineryUtilities;

public class Main extends JFrame {
	JMenu menu = new JMenu("Menu");
	JMenuItem menuZakoncz = new JMenuItem("Zakoncz");
	JMenuItem menuNowe = new JMenuItem("Nowe Zderzenie");
	JMenuBar menuBar = new JMenuBar();

	JButton buttonTlo = new JButton("Kolor Tla");
	JButton buttonKulka1 = new JButton("Kolor Kulki 1");
	JButton buttonKulka2 = new JButton("Kolor Kulki 2");
	JButton buttonRozpocznij = new JButton("Rozpocznij");
	JButton buttonStart = new JButton("Start");
	JButton buttonStop = new JButton("Stop");
	JButton buttonWykres = new JButton("Wykres V(t)");
	JButton buttonAkceptuj = new JButton("Akceptuj");
	JButton buttonPrzyklad = new JButton("Przyklad");

	JRadioButton rbSprezyste = new JRadioButton("Sprezyste");
	JRadioButton rbNiesprezyste = new JRadioButton("Niesprezyste");
	ButtonGroup buttonGroup = new ButtonGroup();

	JPanel panelKolory = new JPanel();
	JPanel panelCentralny = new JPanel();
	JPanel panelStartStop = new JPanel(new GridLayout(9, 1));

	ParametersPanel panelParametry = new ParametersPanel();
	static AnimationPanel panelAnimacja = new AnimationPanel();

	String Aktualny = "Sprzyste";
	static JTextField textfieldZderzenia = new JTextField("0");

	public Main() {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(990, 560);
		this.setTitle("Symulator Zderzez Sprezystych i Niesprezystych");
		this.setJMenuBar(CreateMenu());

		buttonTlo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color Kolor = JColorChooser.showDialog(null, "Wybierz Kolor", Color.BLACK);
				panelAnimacja.setBackground(Kolor);
			}
		});

		buttonKulka1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelAnimacja.KolorKulki1();
			}
		});

		buttonKulka2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelAnimacja.KolorKulki2();
			}
		});

		buttonWykres.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Wykres();
			}
		});

		buttonAkceptuj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Akceptuj();
			}
		});

		buttonPrzyklad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelAnimacja.PrzykladoweParametry();
			}
		});

		buttonRozpocznij.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelAnimacja.RozpocznijZderzenie();
			}
		});

		buttonStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelAnimacja.StartZderzenia();
			}
		});

		buttonStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelAnimacja.StopZderzenia();
			}
		});

		panelKolory.add(buttonTlo);
		panelKolory.add(buttonKulka1);
		panelKolory.add(buttonKulka2);
		this.add(panelKolory, BorderLayout.PAGE_END);

		buttonGroup.add(rbSprezyste);
		buttonGroup.add(rbNiesprezyste);
		buttonGroup.clearSelection();
		rbSprezyste.setSelected(true);

		rbSprezyste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelAnimacja.setZderzenie("Sprezyste");
			}
		});

		rbNiesprezyste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelAnimacja.setZderzenie("Niesprezyste");
			}
		});

		panelStartStop.add(new JLabel("Rodzaj Zderzenia"));
		panelStartStop.add(rbSprezyste);
		panelStartStop.add(rbNiesprezyste);
		panelStartStop.add(buttonRozpocznij);
		panelStartStop.add(buttonStart);
		panelStartStop.add(buttonStop);
		panelStartStop.add(buttonWykres);
		panelStartStop.add(new JLabel("  Liczba Zderzen"));
		panelStartStop.add(textfieldZderzenia);

		textfieldZderzenia.setHorizontalAlignment(JLabel.CENTER);
		textfieldZderzenia.setHorizontalAlignment(JTextField.CENTER);

		panelStartStop.add(textfieldZderzenia);
		this.add(panelStartStop, BorderLayout.LINE_START);

		panelParametry.add(new JLabel(""));
		panelParametry.add(buttonPrzyklad);
		panelParametry.add(new JLabel(""));
		panelParametry.add(buttonAkceptuj);
		this.add(panelParametry, BorderLayout.LINE_END);

		this.add(panelAnimacja, BorderLayout.CENTER);
	}

	public JMenuBar CreateMenu() {
		menuBar.add(menu);

		menuNowe.setActionCommand("Nowe Zderzenie");
		menuNowe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelAnimacja.NoweZderzenie();
			}
		});
		menu.add(menuNowe);

		menuZakoncz.setActionCommand("Zakoncz");
		menuZakoncz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		menu.add(menuZakoncz);

		return menuBar;
	}

	public void Akceptuj() {
		if (ParametersPanel.predkoscKulka1x.getText().isEmpty() || ParametersPanel.predkoscKulka1y.getText().isEmpty()
				|| ParametersPanel.predkoscKulka2x.getText().isEmpty()
				|| ParametersPanel.predkoscKulka2y.getText().isEmpty()
				|| ParametersPanel.polozenieKulka1x.getText().isEmpty()
				|| ParametersPanel.polozenieKulka1y.getText().isEmpty()
				|| ParametersPanel.polozenieKulka2x.getText().isEmpty()
				|| ParametersPanel.polozenieKulka2y.getText().isEmpty()
				|| ParametersPanel.masaKulka1.getText().isEmpty() || ParametersPanel.masaKulka2.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Nalezy Uzupelnic Wartosci");
		} else {
			panelAnimacja.ParametryZderzenia();
		}
	}

	public static int AnimacjaWidth() {
		return panelAnimacja.getWidth();
	}

	public static int AnimacjaHeight() {
		return panelAnimacja.getHeight();
	}

	public void Wykres() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				ChartFrame Chart = new ChartFrame("Wykres");
				Chart.pack();
				RefineryUtilities.centerFrameOnScreen(Chart);
				Chart.setVisible(true);
				Chart.Start();
			}
		});
	}

	public static void main(String[] args) throws Exception {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Main main = new Main();
				main.setVisible(true);
			}
		});
	}
}
