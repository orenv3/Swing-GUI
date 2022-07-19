package com.swing2script.hub.swing.utils;

import lombok.Getter;

@Getter
public class CmdCommandsOutput {

	//Including most of the cli output
	private StringBuilder debugOutput = new StringBuilder();
	
	//Generally including only the last line of cli output
	private StringBuilder infoOutput = new StringBuilder(); 
}
