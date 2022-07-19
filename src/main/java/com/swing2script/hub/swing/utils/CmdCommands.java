package com.swing2script.hub.swing.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JOptionPane;

import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CmdCommands {

	private final static String stopGradle = "gradle --stop ";
	private final static String enterBasePath = "cd "+ProjectsFolderAndPath.basePath;

	public static void doNoCodeDeployment() {

		// Windows
		String[] cmd = new String[] {"cmd.exe","/c", enterBasePath+" && docker-compose up"};
		log.info("The doNoCodeProcess method executed with the command: {}", cmd[2]);
		Process process=null;

		try {

			process = Runtime.getRuntime().exec(cmd);
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			CmdCommandsOutput output = getCliOutput(reader, "ol-general-container    |");
			log.info("The process of the command is done. The command is: {}. \nLast line from the CLI output: {}",cmd[2], output.getInfoOutput().toString());
			log.debug(output.getDebugOutput().toString());

			if(isError(output.getDebugOutput().toString()))
				JOptionPane.showMessageDialog(null, "Error occurred via the command: " +cmd[2], "ERR-MSG", 0);

			process.getInputStream().close();


		} catch (IOException ex) {
			log.error("Exception occurred on doNoCodeDeployment method. \n", ex);
			JOptionPane.showMessageDialog(null, "Exception occurred on doNoCodeDeployment method. \n" + ex.getMessage(), "ERR-MSG", 0);
		
		}finally {
			process.destroy();
		}

	}


	public static void doLowCodeDeployment(String projectName,String profile_noUse,String port_noUse) {

		if(!(StringUtils.hasText(projectName))) {
			JOptionPane.showMessageDialog(null, "Please choose project name and type to execute the process", "ERR-MSG", 0);
			return;
		}
		log.info("The doLowCodeDeployment method executed.");
		
		// Windows
		String[] cmd1 = {"cmd.exe","/c", enterBasePath + " && ol generate --project "+projectName+" --generator spring-java-rest "}; 
		String[] cmd2 = {"cmd.exe","/c", enterBasePath + " && cd "+projectName + " && gradle bootRun"};

		Process process=null;
		try {
			
			////////////  command number 1  cmd1 /////////////
			process = Runtime.getRuntime().exec(cmd1);
			process.waitFor();

			log.debug("The following command been executed: {}", cmd1[2]);
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			CmdCommandsOutput output = getCliOutput(reader, null);
			log.info("The process of the first command is done. The command is: {}. \nCli output: \n{}", cmd1[2], output.getDebugOutput().toString());
			
			if(isError(output.getDebugOutput().toString()))
				JOptionPane.showMessageDialog(null, "Error occurred via the command: " +cmd1[2], "ERR-MSG", 0);

			process.getInputStream().close();


			////////////  command number 2    cmd2/////////////
			process = Runtime.getRuntime().exec(cmd2);
			log.debug("The following command been executed: {}", cmd2[2]);
			reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			output = getCliOutput(reader, "*********************************************************************************");
			
			if(output.getInfoOutput().toString().contains("*********************************************************************************")) {
				output.getInfoOutput().delete(0, output.getInfoOutput().length());
				output.getInfoOutput().append("> Task :bootRun");
			}
			log.info("The process of the second command is done. The command is: {}. \nLast line from the CLI output: {}", cmd2[2], output.getInfoOutput().toString());
			log.debug(output.getDebugOutput().toString());
			
			if(isError(output.getDebugOutput().toString()))
				JOptionPane.showMessageDialog(null, "Error occurred via the command: " +cmd2[2], "ERR-MSG", 0);
			
			process.getInputStream().close();

		} catch (IOException ex) {
			log.error("Exception occurred on doLowCodeDeployment method. \n", ex);
			JOptionPane.showMessageDialog(null, "Exception occurred on doLowCodeDeployment method. \n" +ex.getMessage(), "ERR-MSG", 0);
		} catch (InterruptedException e) {
			log.error("Exception occurred on doLowCodeDeployment method. \n", e);
			JOptionPane.showMessageDialog(null, "Exception occurred on doLowCodeDeployment method. \n" +e.getMessage(), "ERR-MSG", 0);
		}finally {
			
			process.destroy();
		}

	}


	public static void stopTheLowCodeDeploy(boolean showPopupMsg) {

		log.info("The stopTheLowCodeDeploy method executed with the command: {}", stopGradle);
		// Windows
		String[] cmd = {"cmd.exe","/c", stopGradle}; 
		Process process = null;
		try {

			process = Runtime.getRuntime().exec(cmd);
			process.waitFor();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			CmdCommandsOutput output = getCliOutput(reader, null);
			log.info("cmd command results: {}",output.getDebugOutput().toString());

			if(showPopupMsg)
				JOptionPane.showMessageDialog(null, output.getDebugOutput().toString(), "TERMINAL OUTPUT", 1);

			process.getInputStream().close();

		} catch (IOException ex) {
			log.error("Exception occurred on stopTheLowCodeDeploy method. \n", ex);
			JOptionPane.showMessageDialog(null, "Exception occurred on stopTheLowCodeDeploy method. \n" + ex.getMessage(), "ERR-MSG", 0);
		} catch (InterruptedException e) {
			log.error("Exception occurred on stopTheLowCodeDeploy method. \n", e);
			JOptionPane.showMessageDialog(null, "Exception occurred on stopTheLowCodeDeploy method. \n" + e.getMessage(), "ERR-MSG", 0);
		} finally {
			process.destroy();
		}
	}


	public static void stopTheNoCodeDeploy(boolean showPopupMsg) {

		// Windows
		String[] cmd = {"cmd.exe","/c", enterBasePath+" && docker-compose down"}; 
		Process process = null;
		try {

			log.info("The stopTheNoCodeDeploy method executed. The command is: {}", cmd[2]);
			process = Runtime.getRuntime().exec(cmd);
			process.waitFor();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			CmdCommandsOutput output = getCliOutput(reader, null);

			if(output.getDebugOutput().toString().equals("")) {
			String clientMsg = "Process is done.";
				log.info("There is NO inputStream(output from the process) via docker-compose down command. {}",clientMsg);
				output.getDebugOutput().append(clientMsg);
			}else {
				log.info(output.getDebugOutput().toString());
				JOptionPane.showMessageDialog(null, output.getDebugOutput().toString(), "TERMINAL OUTPUT", 0);
			}

			if(showPopupMsg)
				JOptionPane.showMessageDialog(null, output.getDebugOutput().toString(), "TERMINAL OUTPUT", 1);

			process.getInputStream().close();
			
		} catch (IOException ex) {
			log.error("Exception occurred on stopTheNoCodeDeploy method. \n", ex);
			JOptionPane.showMessageDialog(null, "Exception occurred on stopTheNoCodeDeploy method. \n" + ex.getMessage(), "ERR-MSG", 0);
		} catch (InterruptedException e) {
			log.error("Exception occurred on stopTheNoCodeDeploy method. \n", e);
			JOptionPane.showMessageDialog(null, "Exception occurred on stopTheNoCodeDeploy method. \n" + e.getMessage(), "ERR-MSG", 0);
		} finally {
			process.destroy();
		}
	}

	private static CmdCommandsOutput getCliOutput(BufferedReader reader, String successOutput) throws IOException {
		
		CmdCommandsOutput output = new CmdCommandsOutput();
		try {
			
			output.getInfoOutput().append(reader.readLine());
		while (!(output.getInfoOutput().toString().equals("null"))) {
			output.getDebugOutput().append(output.getInfoOutput().toString()+"\n");
			if(StringUtils.hasText(successOutput))
				if(output.getInfoOutput().toString().contains(successOutput)) 
					break;
			output.getInfoOutput().delete(0, output.getInfoOutput().length());
			output.getInfoOutput().append(reader.readLine());
			}
		
		} catch (IOException e) {
			log.error("\nSomething went wrong in getCliOutput method. \n",e);
			JOptionPane.showMessageDialog(null, "Exception occurred on via read from CLI. \n" + e.getMessage(), "ERR-MSG", 0);
		}finally {
			reader.close();
		}
		return output;
	}


	private static boolean isError(String tmp) {
		if((tmp.contains("ERROR") || tmp.contains("Error")) || tmp.contains("error") || tmp.contains("Invalid") ||
				tmp.contains("invalid"))
			return true;

		return false;
	}

	
//	1)  try it instead of InputStreamReader/BufferedReader:
//	System.out.println(IOUtils.toString(reader)); need 
		
	
	
	//	String[] cmd = {"cmd.exe","/c", "cd "+ProjectsFolderAndPath.basePath + " && ol generate --project "+projectName+" --generator spring-java-rest " 
	//			+ "&& cd "+projectName,"gradle build","gradle bootRun"}; 

//	private static boolean isOutput(String tmp) {
//		if((tmp.contains("[|]") || tmp.contains("[-]")) || tmp.contains("[/]") || tmp.contains("[\\]") ||
//				tmp.contains("=========================="))
//			return false;
//
//		return true;
//	}
	/*
	 * 
				String line = "";
				String tmp= null;
				while ((tmp = reader.readLine()) != null) {
					line+= tmp+"\n";
					if(tmp.contains("ol-general-container    |")) 
						break;
					tmp= null;
					}
				
				log.info("\n"+"The process of the command is done. The command is: "+cmd[2]+". \nLast line from the CLI output: "+ tmp);
				log.debug("\n"+line);
	 */
	/*
	 * 
	 * 
if(tmp.contains(">")) {
//process.getInputStream().close();
//process.destroy();
System.out.println(line);
//JOptionPane.showMessageDialog(null, line, "TERMINAL OUTPUT", 1);
break;
}

	 */
}
