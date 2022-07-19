package com.swing2script.hub.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import com.jgal.filewriter.freemarker.FreemarkerUtils;
import com.swing2script.hub.swing.utils.CmdCommands;
import com.swing2script.hub.swing.utils.ProjectsFolderAndPath;

import ch.qos.logback.core.recovery.ResilientSyslogOutputStream;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GUI implements ActionListener {

//	private String ips[]={"8.8.8.8","walla.co.il","192.168.1.1"};
//	private final JComboBox<?> comboAllProjects=new JComboBox<Object>(ips); 
	
	private JFrame frame = new JFrame("OL HUB GUI");
	private GuiPanel topPanel = new GuiPanel(Color.lightGray,new Dimension(500,100),new BorderLayout());
	private JButton buttonDeploy=new JButton("Deploy"); 
	private JButton buttonStop=new JButton("Stop");
	private JLabel labelComboAllProjects = new JLabel("Please choose a project from the below list: ");   
	public final JComboBox<?> comboAllProjects=new JComboBox<Object>(DropDownInfoCollect.getAllProject());  
	private  ProjectsFolderAndPath prjFolder = new ProjectsFolderAndPath();
	MidPanel midPanel = new MidPanel();
	public final boolean showPopupMsg = true; 

	public GUI() {
		this.initUI();
	}

	private void initUI() {

		//		  Border border = BorderFactory.createLineBorder(Color.green,3);

		/*
		 * TOP PANEL    
		 */
		comboAllProjects.setBounds(150,30,200,20);
		labelComboAllProjects.setVerticalAlignment(JLabel.TOP);

		comboAllProjects.addActionListener(this);
		labelComboAllProjects.add(comboAllProjects);
		topPanel.add(labelComboAllProjects);


		/*
		 * CENTER PANEL
		 */
		midPanel.setPreferredSize(new Dimension(500,200));


		/*
		 * BOTTOM PANEL
		 */
		GuiPanel bottomPanel = new GuiPanel(Color.lightGray,new Dimension(500,100), null);

		buttonDeploy.setBounds(110,30, 90, 30);
		buttonStop.setBounds(290, 30, 90, 30);
		buttonStop.setBackground(Color.red);
		buttonDeploy.setBackground(Color.green);
		
		bottomPanel.add(buttonDeploy);
		bottomPanel.add(buttonStop);


		/*
		 * The Main Frame properties
		 */
		ImageIcon icon = new ImageIcon("OL_Icone.png"); 

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout(2,2));  
		frame.setSize(500,500); 
		frame.setResizable(false);
		frame.setVisible(true);       
		frame.setIconImage(icon.getImage()); 
		frame.setLocationRelativeTo(null);

		frame.add(topPanel,BorderLayout.NORTH);
		frame.add(midPanel,BorderLayout.CENTER);
		frame.add(bottomPanel,BorderLayout.SOUTH);
		frame.setGlassPane(SpinnerPanel.getInstance());
		
		
		///////////////////		**********		ActionListener part		***********		//////////////////////////
		/*
		 * COMBO ActionListener
		 */
		comboAllProjects.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				labelComboAllProjects.setText("The selected project is: " + comboAllProjects.getItemAt(comboAllProjects.getSelectedIndex())); 
			}
		});


		/*
		 * DEPLOY BUTTON  ActionListener
		 */
		buttonDeploy.addActionListener(new ActionListener() {  
			public void actionPerformed(ActionEvent e) { 

				buttonStop.setEnabled(false);
				buttonDeploy.setEnabled(false);
				SpinnerPanel.getInstance().setVisible(true);
				startDeployment();
				
			}  
		}); 


		/*
		 * STOP BUTTON ActionListener
		 */
		buttonStop.addActionListener(new ActionListener() {  
			public void actionPerformed(ActionEvent e) {       
				buttonStop.setEnabled(false);
				buttonDeploy.setEnabled(false);
				SpinnerPanel.getInstance().setVisible(true);
				stopDeployment();
			}  
		}); 

	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}

	private void startDeployment()
	{
		/*
		 * Created in order that the spinner will work
		 */
		SwingWorker backgroundJob = new SwingWorker() {
			
			FreemarkerUtils freemarkerUtils = new FreemarkerUtils();
			String res = "Finished Execution";

			@Override
			protected String doInBackground() throws Exception {

				if(comboAllProjects.getItemAt(comboAllProjects.getSelectedIndex()).equals("")) {
					JOptionPane.showMessageDialog(null, "Please choose project name", "INFO-MSG", 1);
					return res;
				}

				if(prjFolder.isFile()) {
					CmdCommands.stopTheNoCodeDeploy(!showPopupMsg);
				}
				CmdCommands.stopTheLowCodeDeploy(!showPopupMsg);

				if(MidPanel.deploymentType.equals("low-code")) {
					CmdCommands.doLowCodeDeployment(comboAllProjects.getItemAt(comboAllProjects.getSelectedIndex()).toString(),"test","test");
					JOptionPane.showMessageDialog(null, "Deployment process is done.", "INFO-MSG", 1);

				}else if(MidPanel.deploymentType.equals("no-code")) {
					try {
						freemarkerUtils.creatConfFileDockerCompose(comboAllProjects.getItemAt(comboAllProjects.getSelectedIndex()).toString(),midPanel.getPortField());
						freemarkerUtils.creatConfFileJson(comboAllProjects.getItemAt(comboAllProjects.getSelectedIndex()).toString());
						CmdCommands.doNoCodeDeployment();
						JOptionPane.showMessageDialog(null, "Deployment process is done.", "INFO-MSG", 1);
					}catch (IOException | TemplateException e) {
						JOptionPane.showMessageDialog(null, "Freemarker issue: \n"+e.getMessage(), "ERR-MSG", 0);
						log.error("Freemarker issue: \n {}",e.getMessage(),e);
					}
					catch (Exception e1) {
						JOptionPane.showMessageDialog(null, "Freemarker issue: \n"+e1.getMessage(), "ERR-MSG", 0);
						log.error("Freemarker issue: \n {}",e1.getMessage(),e1);
					}

				}else {
					JOptionPane.showMessageDialog(null, "Please choose deployment type: no-code/low-code", "INFO-MSG", 1);
				}
				try {
					midPanel.setUrl();
				} catch (URISyntaxException e1) {
					JOptionPane.showMessageDialog(null, "URI Syntax issue: \n"+e1.getMessage(), "ERR-MSG", 0);
					log.error("URI Syntax issue: \n {}",e1.getMessage(),e1);
				}
				return res;
			}

			// Method
			@Override protected void process(List chunks) {}
			
			@Override protected void done()
			{
				buttonStop.setEnabled(true);
				buttonDeploy.setEnabled(true);
				SpinnerPanel.getInstance().setVisible(false);
			}
		};

		backgroundJob.execute();
	}

	private void stopDeployment()
	{
		/*
		 * Created in order that the spinner will work
		 */
		SwingWorker backgroundJob = new SwingWorker() {
			// Method
			
			@Override
			protected String doInBackground()
					throws Exception
			{
//				List<Integer> l = new ArrayList<Integer>();
//				l.add(1);
//				l.add(2);
//				l.add(3);
//				l.add(4);
//				publish(l); //this method calls process(List chunks) if there is no impl there so do nothing
				log.info("STOP BUTTON ActionListener() method executed");
String ss= "Oren expressions"; 
				if(MidPanel.deploymentType.equals("low-code")) {
					CmdCommands.stopTheLowCodeDeploy(showPopupMsg);
				}else if(MidPanel.deploymentType.equals("no-code")) {
					if(prjFolder.isFile()) {
						CmdCommands.stopTheNoCodeDeploy(showPopupMsg);
					}else {
						JOptionPane.showMessageDialog(null, "No docker file to stop. Process done", "INFO-MSG", 1);
					}
				}else {
					JOptionPane.showMessageDialog(null, "Please choose deployment type: no-code/low-code", "INFO-MSG", 1);
				}

				String res = "Finished Execution";
				
//				List<Integer> l = new ArrayList<Integer>();
//				l.add(1);
//				l.add(2);
//				l.add(3);
//				l.add(4);
//				publish(l); //this method calls process(List chunks) if there is no impl there so do nothing
				return res;
			}
			
			@Override protected void process(List chunks) {
				//THis is execute on a different THREAD
//				for(int i =0; i<1000;i++) {
//					System.out.println(Thread.class.getName()+" this is Thread name");
//					System.out.println("This is in the process");
//				}
//				System.out.println("This is in the process");
//				System.out.println("This is in the process");
				}

			@Override protected void done()
			{
				buttonStop.setEnabled(true);
				buttonDeploy.setEnabled(true);
				SpinnerPanel.getInstance().setVisible(false);
			}
		};

		backgroundJob.execute();
	}

}


//private String ips[]={"8.8.8.8","walla.co.il","192.168.1.1"};
//		private final JComboBox<?> comboAllProjects=new JComboBox<Object>(ips);  
