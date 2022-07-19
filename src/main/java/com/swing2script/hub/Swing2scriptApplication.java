package com.swing2script.hub;

import java.awt.EventQueue;

import javax.swing.JFrame;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import com.swing2script.hub.swing.GUI;

import lombok.var;


@SpringBootApplication
public class Swing2scriptApplication extends JFrame{

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {

	        var ctx = new SpringApplicationBuilder(Swing2scriptApplication.class)
	                .headless(false).run(args);

	        EventQueue.invokeLater(() -> {
	          ctx.getBean(Swing2scriptApplication.class);
	        });
	    }
	
	  public Swing2scriptApplication() {
		  new GUI();
	    }

}

