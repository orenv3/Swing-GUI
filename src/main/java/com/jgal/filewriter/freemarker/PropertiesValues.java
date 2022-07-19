package com.jgal.filewriter.freemarker;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PropertiesValues {

	private static PropertiesValues properties = null;
	private PropertiesValues() {}

	private String onPremHubUrl;
	
	public static PropertiesValues getPropertiesValues() {
		if(properties==null)
			properties=new PropertiesValues();
		return properties;
	}
}
