package gui;

import javax.swing.*;
import java.awt.*;

public class BackgroundPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String string;
	public BackgroundPanel(String s){
	  	this.string = s;
		setLayout(null);
	}
    public void paintComponent(Graphics page){
      super.paintComponent(page);
      page.drawImage((new ImageIcon(string)).getImage(), 0, 0, null);
    }
}