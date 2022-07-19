package com.swing2script.hub.swing.utils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProjectsFolderAndPath {
	
	public static final String basePath = ".\\OpenlegacyHubProjects";
	
//	private final String dockerCompose = "\\docker-compose.yml";
	private File directory = new File(basePath);
	private final Path path = Paths.get(getYmlPath());
	
	public ProjectsFolderAndPath() {
		//The folder is for low_code process. no_code have is own file creation
		if(!(directory.exists())) {
			directory.mkdir();
		}
	}
	
	public boolean isFile() {
		log.debug("Executing the method isFile(). Checking if the path exists: path: {}, exists: {}",path,Files.exists(path));
		return Files.exists(path);
	}
	
	private String getYmlPath() {
		StringBuilder pth = new StringBuilder();
		pth.append(basePath);
		pth.append("\\\\docker-compose.yml");
		return pth.toString();
	}
	
}
