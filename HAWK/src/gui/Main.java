package gui;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.JWindow;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import java.awt.Color;

public class Main {
	public static void main(String[] args) {

		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
		}
		/*
		JWindow window = new JWindow();
		window.setBackground(new Color(0,0,0,0));
		window.getContentPane().add(new JLabel(new ImageIcon("img/hawkLOGO.gif")));
		window.setBounds(380, 30, 622, 725);
		window.setVisible(true);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		window.setVisible(false);
		*/
		HawkFrame hawkFrame = new HawkFrame();
		hawkFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		hawkFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		hawkFrame.setUndecorated(true);
		hawkFrame.setVisible(true);
		hawkFrame.setLocationRelativeTo(null);
	}
}