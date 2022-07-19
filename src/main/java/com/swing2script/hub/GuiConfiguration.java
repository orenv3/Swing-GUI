package com.swing2script.hub;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jgal.filewriter.freemarker.PropertiesValues;

@Configuration
public class GuiConfiguration {

	@Value("${hubUrl}")
	private String url;
	

	public PropertiesValues getPropertiesValues() {
		return PropertiesValues.getPropertiesValues();
	}
	
	@PostConstruct
	public void setValues() {
		getPropertiesValues().setOnPremHubUrl(url);
	}
}
