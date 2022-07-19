package com.swing2script.hub.swing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.springframework.util.StringUtils;

import com.swing2script.hub.swing.utils.CmdCommandsOutput;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class DropDownInfoCollect {

	public static String[] getAllProject() {
		
		log.info("DropDownInfoCollect.getAllProject() method executed. method collect all project via command: ol list projects");
		ProcessBuilder processBuilder = new ProcessBuilder();
				 
				 // Windows
			        processBuilder.command("cmd.exe", "/c", "ol list projects");
			        processBuilder.redirectErrorStream(true);

			        CmdCommandsOutput output = new CmdCommandsOutput();
			        String[] tmpArr = null;
			        ArrayList<String> listS = new ArrayList<String>();
			        
			        try {
			            Process process = processBuilder.start();
			            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			    		output.getInfoOutput().append(reader.readLine());
			    		while (!(output.getInfoOutput().toString().equals("null"))) {
			    			output.getDebugOutput().append(output.getInfoOutput().toString());
			    			output.getDebugOutput().append(System.getProperty("line.separator"));
			    			if(isProjectName(output.getInfoOutput().toString())) {
			                	tmpArr = output.getInfoOutput().toString().split("   ");
			                	listS.add(tmpArr[0].trim());
			                    
			                    tmpArr = null;
			                }
			    			//delete the old line
			    			output.getInfoOutput().delete(0, output.getInfoOutput().length());
			    			//take new line
			    			output.getInfoOutput().append(reader.readLine());

			    		}
			            log.debug("Debug logs of CLI output: \n{}",output.getDebugOutput().toString());
			            if(listS.get(0).contains("Exception") || listS.get(1).contains("Exception")) {
			            	log.error("ERROR in getAllProject() method. Error msg: {}\n{}", listS.get(0),listS.get(1));
			            	JOptionPane.showMessageDialog(null, "ERROR in getAllProject() method. Error msg: "+ listS.get(0) + "\n" + listS.get(1), "ERR-MSG", 0);
			            }else
			            log.info("The following projects been collected: {}", listS );
			            
			        } catch (IOException ex) {
			        	log.error("ERROR in getAllProject() method. Error msg: {}", ex.getMessage(),ex);
			        	JOptionPane.showMessageDialog(null, ex.getMessage(), "ERR-MSG", 0);
			        } 
			        
					return listToArray(listS);
			}
	
	
	
	//////////////////////////////////////////// PRIVATE METHODS //////////////////////////////////////////////////////////////

	private static String[] listToArray(List<String> list) {
		if(list==null) return null;
		String[] response = new String[list.size()];
		
		return (String[]) list.toArray(response);
	}
	
	private static boolean isProjectName(String tmp) {
		if((tmp.contains("[|]") || tmp.contains("[-]")) || tmp.contains("[/]") || tmp.contains("[\\]") ||
				(tmp.contains("DONE") ) || 
				(tmp.contains("project name") && tmp.contains("description")) || 
				tmp.contains("=========================="))
			return false;
		
		return true;
	}


}
