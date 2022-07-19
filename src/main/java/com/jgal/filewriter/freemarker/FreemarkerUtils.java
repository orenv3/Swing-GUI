package com.jgal.filewriter.freemarker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.swing2script.hub.swing.utils.ProjectsFolderAndPath;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FreemarkerUtils {

	private final String DOCKER_COMPOSE = "docker-compose.yml";
	private final String NO_CODE = "noCode.json";
	private final String PROJECT_NAME = "projectName";
	private final String PORT = "port";
	private final String HUB_URL = "guiUrl";
	private final String DOCKER_COMPOSE_TMP = "docker_compose_tmp.ftlh";
	private final String NO_CODE_TMP = "noCode.ftlh";
	private final String HTTPS = "https://";
	private final String OL_HUB_URL = "api.ol-hub.com";
	
	private Map<String, Object> data = new HashMap<>();

	public void creatConfFileDockerCompose(String prjctName, String port) throws IOException, TemplateException {
		Map<String, Object> data = new HashMap<>();
		data.put(PROJECT_NAME, prjctName);
		if (!(port.equals(""))) {
			data.put(PORT, port);
		} else {
			data.put(PORT, "8080");
		}
		log.info("Creating docker-compose configuration file for noCode project. Project name: {} in port: {}",
				prjctName, data.get("port"));
		writeToWin(DOCKER_COMPOSE, DOCKER_COMPOSE_TMP,data);
		
	}

	
	public void creatConfFileJson(String prjName) throws Exception {
		
		StringBuilder setUrl = new StringBuilder(HTTPS);
		if(StringUtils.hasText(PropertiesValues.getPropertiesValues().getOnPremHubUrl())) {
			setUrl.append(PropertiesValues.getPropertiesValues().getOnPremHubUrl());
		}else {
			setUrl.append(OL_HUB_URL);
		}
		
		data.put(PROJECT_NAME, prjName);
		data.put(HUB_URL, setUrl.toString());

		log.info("Creating noCode.json configuration file for noCode project. Project name: {}",prjName);
		writeToWin(NO_CODE, NO_CODE_TMP, data);
		
	}
	
	/**
	 * Write(create) file in win operation system
	 * @param fileName file name to create
	 * @param givenTemplate freeMarker template
	 * @param params Map of parameters which we need to set them in he file
	 * @throws IOException
	 * @throws TemplateException
	 */
	private void writeToWin(String fileName, String givenTemplate, Map<String, Object> params) throws IOException, TemplateException {
		File directory = new File(ProjectsFolderAndPath.basePath);
		if (!(directory.exists())) {
			directory.mkdir();
		}
		StringBuilder pth = new StringBuilder();
		pth.append(ProjectsFolderAndPath.basePath);
		pth.append(File.separatorChar);
		pth.append(fileName);
		try (Writer file = new FileWriter(new File(pth.toString()))) {

			Template template = FreemarkerConfig.getConfig().getTemplate(givenTemplate);
			template.process(params, file);
			file.flush();
		}
	}

}

