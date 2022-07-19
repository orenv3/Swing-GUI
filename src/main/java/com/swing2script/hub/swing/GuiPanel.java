package com.swing2script.hub.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public class GuiPanel extends JPanel{
	
	public GuiPanel(Color color,Dimension dim, BorderLayout lyout ) {
		this.setBackground(color);
		this.setPreferredSize(dim);
		this.setLayout(lyout);
	}

}
