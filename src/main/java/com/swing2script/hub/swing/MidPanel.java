package com.swing2script.hub.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;

import org.springframework.util.StringUtils;

import lombok.Getter;

@Getter
public class MidPanel extends JPanel implements ActionListener{
	
	private  URI uri;
	private JLabel profilesLabel;
	private JLabel portLabel = new JLabel("port: ");
	private JLabel radioBtnLabel;
	private JRadioButton noCode = new JRadioButton("no-code");
	private JRadioButton lowCode = new JRadioButton("low-Code");
	public static String deploymentType = "";

	private JTextField txtFieldProfile = new JTextField();
	private String profileName = "";
	
	private JTextField txtFieldPort = new JTextField();

	private static final String SWAGGER_TITLE = "swagger link: ";
	private static final String SWAGGER_LINK_PART1 = "http://localhost:";
	private static final String SWAGGER_LINK_PART2 = "/openapi";
	private static final String DEAULT_PORT = "8080";
	
	private JLabel swaggerLinkLabel = new JLabel();
	
	public MidPanel() {
//		Border border = BorderFactory.createLineBorder(Color.green);
		 
		radioBtnLabel = new JLabel("Please choose project type from the following:");
		profilesLabel = new JLabel("profiles: ");
		
		this.setLayout(new BorderLayout());
		ButtonGroup btnGroup = new ButtonGroup();
		btnGroup.add(noCode);
		btnGroup.add(lowCode);
//		
//		
		/*
		 * TOP
		 */
		GuiPanel inPanelTop = new GuiPanel(Color.lightGray, new Dimension(500,80),null);
		radioBtnLabel.setBounds(5,5,350,20);
		inPanelTop.add(radioBtnLabel);
		noCode.setBounds(150, 40, 80, 25);
		lowCode.setBounds(270, 40, 80, 25);
		
		inPanelTop.add(lowCode);
		inPanelTop.add(noCode);
		
		/*
		 * CENTER
		 */
		GuiPanel inPanelCenter=  new GuiPanel(Color.lightGray, new Dimension(500,60),null);
		
		txtFieldProfile.setHorizontalAlignment(JTextField.LEFT);
		txtFieldProfile.setBounds(140,30,250,20);
		profilesLabel.setBounds(80,30, 60, 20);
		
		txtFieldPort.setBounds(140, 80, 250, 20);
		portLabel.setBounds(80,80, 60, 20);
		
		inPanelCenter.add(portLabel);
		inPanelCenter.add(txtFieldPort);
		inPanelCenter.add(profilesLabel);
		inPanelCenter.add(txtFieldProfile);
		txtFieldProfile.setEditable(false);
//		
//		
		/*
		 * BOTTOM
		 */
		GuiPanel inPanelBottom= new GuiPanel(Color.lightGray, new Dimension(500,50),null);
		
		swaggerLinkLabel.setBounds(110,15,300,20);
		inPanelBottom.add(swaggerLinkLabel);
//		
//		
		this.add(inPanelCenter,BorderLayout.CENTER);
		this.add(inPanelTop,BorderLayout.NORTH);
		this.add(inPanelBottom,BorderLayout.SOUTH);
		
		lowCode.addActionListener(this);
		noCode.addActionListener(this);
		swaggerLinkLabel.addMouseListener(new MouseAdapter() {
			   public void mouseClicked(MouseEvent e) {
				      if (e.getClickCount() > 0) {
				          if (Desktop.isDesktopSupported()) {
				                Desktop desktop = Desktop.getDesktop();
				                try {
				                    desktop.browse(uri);
				                } catch (IOException ex) {
				                    ex.printStackTrace();
				                } 
				        }
				      }
				   }
				});
	}

	public void setProfileName(String prf) {
		this.profileName = prf;
	}
	public void clearDeploymentType() {
		deploymentType = "";
	}
	
	public String getPortField() {
		return this.txtFieldPort.getText();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
	if(e.getSource()== noCode) {
		deploymentType = "no-code";
		txtFieldPort.setEditable(true);
		
	}
	else if(e.getSource()== lowCode){
		deploymentType = "low-code";
		txtFieldPort.setText("");
		txtFieldPort.setEditable(false);
	}
		this.setProfileName( txtFieldProfile.getText());
	}  
	
	public void setUrl() throws URISyntaxException {
         
		StringBuilder eiUrlHub = new StringBuilder();
		if(StringUtils.hasText(txtFieldPort.getText())){
    		eiUrlHub.append(SWAGGER_LINK_PART1);
    		eiUrlHub.append(txtFieldPort.getText());
    		eiUrlHub.append(SWAGGER_LINK_PART2);
    		uri = new URI(eiUrlHub.toString());
    		eiUrlHub.insert(0,SWAGGER_TITLE);
    		swaggerLinkLabel.setText(eiUrlHub.toString());
    	}else {
    		//default port 8080
    		eiUrlHub.append(SWAGGER_LINK_PART1);
    		eiUrlHub.append(DEAULT_PORT);
    		eiUrlHub.append(SWAGGER_LINK_PART2);
    		uri = new URI(eiUrlHub.toString());
    		eiUrlHub.insert(0,SWAGGER_TITLE);
    		swaggerLinkLabel.setText(eiUrlHub.toString());
    	}
		eiUrlHub=null;
	}
	
	
}


