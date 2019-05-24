package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

public class BackgroundPanel extends JPanel {
	
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