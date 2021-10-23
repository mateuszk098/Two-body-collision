package collisions;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JColorChooser;
import javax.swing.JPanel;

public class AnimationPanel extends JPanel implements Runnable {
	static BallClass Kulka1 = new BallClass(50, 50, 25);
	static BallClass Kulka2 = new BallClass(50, 50, 25);

	double deltaX, deltaY, odleglosc;
	static int iloscZderzen = 0;

	String Zderzenie = "Spr�yste";
	Thread Animacja;
	boolean Aktywna = true;

	public AnimationPanel() {
		this.setBackground(Color.white);

		Kulka1.setX(100);
		Kulka1.setY(200);
		Kulka1.setVX(0);
		Kulka1.setVY(0);
		Kulka1.setM(0);

		Kulka2.setX(700);
		Kulka2.setY(200);
		Kulka2.setVX(0);
		Kulka2.setVY(0);
		Kulka2.setM(0);

		Animacja = new Thread(this);
	}

	public void ParametryZderzenia() {
		Kulka1.setX(Double.parseDouble(ParametersPanel.polozenieKulka1x.getText()));
		Kulka1.setY(Double.parseDouble(ParametersPanel.polozenieKulka1y.getText()));
		Kulka1.setVX(Double.parseDouble(ParametersPanel.predkoscKulka1x.getText()));
		Kulka1.setVY(Double.parseDouble(ParametersPanel.predkoscKulka1y.getText()));
		Kulka1.setM(Double.parseDouble(ParametersPanel.masaKulka1.getText()));

		Kulka2.setX(Double.parseDouble(ParametersPanel.polozenieKulka2x.getText()));
		Kulka2.setY(Double.parseDouble(ParametersPanel.polozenieKulka2y.getText()));
		Kulka2.setVX(Double.parseDouble(ParametersPanel.predkoscKulka2x.getText()));
		Kulka2.setVY(Double.parseDouble(ParametersPanel.predkoscKulka2y.getText()));
		Kulka2.setM(Double.parseDouble(ParametersPanel.masaKulka2.getText()));
	}

	public void PrzykladoweParametry() {
		ParametersPanel.polozenieKulka1x.setText("100.0");
		ParametersPanel.polozenieKulka1y.setText("200.0");
		ParametersPanel.predkoscKulka1x.setText("9.0");
		ParametersPanel.predkoscKulka1y.setText("0.0");
		ParametersPanel.masaKulka1.setText("10.0");

		ParametersPanel.polozenieKulka2x.setText("700.0");
		ParametersPanel.polozenieKulka2y.setText("200.0");
		ParametersPanel.predkoscKulka2x.setText("-7.0");
		ParametersPanel.predkoscKulka2y.setText("0.0");
		ParametersPanel.masaKulka2.setText("100.0");
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Kulka1.draw(g);
		Kulka2.draw(g);
	}

	public void ZderzenieSprezyste() {
		deltaX = Math.abs(Kulka1.getX() - Kulka2.getX());
		deltaY = Math.abs(Kulka1.getY() - Kulka2.getY());
		odleglosc = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

		if (odleglosc < Kulka1.getR() + Kulka2.getR()) {
			double L = 1 / Math.sqrt((Kulka2.getX() - Kulka1.getX()) * (Kulka2.getX() - Kulka1.getX())
					+ (Kulka2.getY() - Kulka1.getY()) * (Kulka2.getY() - Kulka1.getY()));

			// predkosci normalne przed zderzeniem
			double Vn1 = L * (Kulka1.getVX() * (Kulka2.getX() - Kulka1.getX())
					+ Kulka1.getVY() * (Kulka2.getY() - Kulka1.getY()));
			double Vn2 = L * (Kulka2.getVX() * (Kulka2.getX() - Kulka1.getX())
					+ Kulka2.getVY() * (Kulka2.getY() - Kulka1.getY()));

			// predkosci styczne przed zderzeniem
			double Vs1 = L * (Kulka1.getVX() * (Kulka1.getY() - Kulka2.getY())
					+ Kulka1.getVY() * (Kulka2.getX() - Kulka1.getX()));
			double Vs2 = L * (Kulka2.getVX() * (Kulka1.getY() - Kulka2.getY())
					+ Kulka2.getVY() * (Kulka2.getX() - Kulka1.getX()));

			// predkosci normalne po zderzeniu
			double Un1 = ((Kulka1.getM() - Kulka2.getM()) * Vn1 + 2 * Vn2 * Kulka2.getM())
					/ (Kulka1.getM() + Kulka2.getM());
			double Un2 = ((Kulka2.getM() - Kulka1.getM()) * Vn2 + 2 * Vn1 * Kulka1.getM())
					/ (Kulka1.getM() + Kulka2.getM());

			double newVX1 = L * ((Kulka2.getX() - Kulka1.getX()) * Un1 + (-Kulka2.getY() + Kulka1.getY()) * Vs1);
			double newVY1 = L * ((Kulka2.getY() - Kulka1.getY()) * Un1 + (Kulka2.getX() - Kulka1.getX()) * Vs1);

			double newVX2 = L * ((Kulka2.getX() - Kulka1.getX()) * Un2 + (-Kulka2.getY() + Kulka1.getY()) * Vs2);
			double newVY2 = L * ((Kulka2.getY() - Kulka1.getY()) * Un2 + (Kulka2.getX() - Kulka1.getX()) * Vs2);

			Kulka1.setVX(newVX1);
			Kulka1.setVY(newVY1);

			Kulka2.setVX(newVX2);
			Kulka2.setVY(newVY2);

			iloscZderzen++;
			Main.textfieldZderzenia.setText(getSumaZderzen());
		}
	}

	public void ZderzenieNiesprezyste() {
		deltaX = Math.abs(Kulka1.getX() - Kulka2.getX());
		deltaY = Math.abs(Kulka1.getY() - Kulka2.getY());
		odleglosc = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

		if (odleglosc < Kulka1.getR() + Kulka2.getR()) {

			double newVX = (Kulka1.getVX() * Kulka1.getM() + Kulka2.getVX() * Kulka2.getM())
					/ (Kulka1.getM() + Kulka2.getM());
			double newVY = (Kulka1.getVY() * Kulka1.getM() + Kulka2.getVY() * Kulka2.getM())
					/ (Kulka1.getM() + Kulka2.getM());

			Kulka1.setVX(newVX);
			Kulka1.setVY(newVY);
			Kulka2.setVX(newVX);
			Kulka2.setVY(newVY);

			iloscZderzen = 1;
			Main.textfieldZderzenia.setText(getSumaZderzen());

			/*
			 * if(Kulka1.getVY()==0 && Kulka2.getVY()==0) { double
			 * newVX1=(Kulka1.getVX()*Kulka1.getM()+Kulka2.getVX()*Kulka2.getM())/(Kulka1.
			 * getM()+Kulka2.getM()); double
			 * newVX2=(Kulka1.getVX()*Kulka1.getM()+Kulka2.getVX()*Kulka2.getM())/(Kulka1.
			 * getM()+Kulka2.getM());
			 * 
			 * Kulka1.setVX(newVX1); Kulka2.setVX(newVX2); }
			 * 
			 * else if(Kulka1.getVX()==0 && Kulka2.getVX()==0) { double
			 * newVY1=(Kulka1.getVY()*Kulka1.getM()+Kulka2.getVY()*Kulka2.getM())/(Kulka1.
			 * getM()+Kulka2.getM()); double
			 * newVY2=(Kulka1.getVY()*Kulka1.getM()+Kulka2.getVY()*Kulka2.getM())/(Kulka1.
			 * getM()+Kulka2.getM());
			 * 
			 * Kulka1.setVY(newVY1); Kulka2.setVY(newVY2); }
			 */
		}
	}

	public void run() {
		while (Aktywna) {
			if (getZderzenie() == "Spr�yste") {
				Kulka1.WarunkiSprezyste();
				Kulka2.WarunkiSprezyste();
			} else if (getZderzenie() == "Niespr�yste") {
				Kulka1.WarunkiNiesprezyste();
				Kulka2.WarunkiNiesprezyste();
			}

			ParametersPanel.predkoscKulka1x.setText(Double.toString(Math.round(Kulka1.getVX())));
			ParametersPanel.predkoscKulka1y.setText(Double.toString(Math.round(Kulka1.getVY())));
			ParametersPanel.polozenieKulka1x.setText(Double.toString(Math.round(Kulka1.getX())));
			ParametersPanel.polozenieKulka1y.setText(Double.toString(Math.round(Kulka1.getY())));

			ParametersPanel.predkoscKulka2x.setText(Double.toString(Math.round(Kulka2.getVX())));
			ParametersPanel.predkoscKulka2y.setText(Double.toString(Math.round(Kulka2.getVY())));
			ParametersPanel.polozenieKulka2x.setText(Double.toString(Math.round(Kulka2.getX())));
			ParametersPanel.polozenieKulka2y.setText(Double.toString(Math.round(Kulka2.getY())));

			WyborZderzenia();
			repaint();

			try {
				Thread.sleep(20);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void NoweZderzenie() {
		Kulka1.setX(100.0);
		Kulka1.setY(200.0);
		Kulka1.setVX(0.0);
		Kulka1.setVY(0.0);
		Kulka1.setM(0.0);

		Kulka2.setX(700.0);
		Kulka2.setY(200.0);
		Kulka2.setVX(0.0);
		Kulka2.setVY(0.0);
		Kulka2.setM(0.0);

		iloscZderzen = 0;
		Main.textfieldZderzenia.setText("0");
	}

	public void RozpocznijZderzenie() {
		Animacja.start();
	}

	public void StopZderzenia() {
		if (!Aktywna)
			return;
		Aktywna = false;

		try {
			Animacja.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void StartZderzenia() {
		if (Aktywna)
			return;
		Aktywna = true;
		Animacja = new Thread(this);
		Animacja.start();
	}

	public void setZderzenie(String zderzenie) {
		this.Zderzenie = zderzenie;
	}

	public String getZderzenie() {
		return Zderzenie;
	}

	public void WyborZderzenia() {
		if (getZderzenie() == "Spr�yste") {
			ZderzenieSprezyste();
		} else if (getZderzenie() == "Niespr�yste") {
			ZderzenieNiesprezyste();
		}
	}

	public void KolorKulki1() {
		Color Kolor = JColorChooser.showDialog(null, "Wybierz Kolor", Color.BLACK);
		Kulka1.setBallColor(Kolor);
	}

	public void KolorKulki2() {
		Color Kolor = JColorChooser.showDialog(null, "Wybierz Kolor", Color.BLACK);
		Kulka2.setBallColor(Kolor);
	}

	public String getSumaZderzen() {
		return Integer.toString(iloscZderzen);
	}
}