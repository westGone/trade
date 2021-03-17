package com.trade.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class main {
	@RequestMapping(value = "/index")
	public String index() {
		return "index";
	}
	
	@RequestMapping(value = "/accordion")
	public String accordion() {
		return "accordion";
	}
	
	@RequestMapping(value = "/auth-nomal-sign-in")
	public String authNormalSignIn() {
		return "auth-nomal-sign-in";
	}
	
	@RequestMapping(value = "/auth-sign-up")
	public String authSignUp() {
		return "auth-sign-up";
	}
	
	@RequestMapping(value = "/breadcrumb")
	public String breadcrumb() {
		return "breadcrumb";
	}
	
	@RequestMapping(value = "/bs-basic-table")
	public String bsBasicTable() {
		return "bs-basic-table";
	}
	
	@RequestMapping(value = "/button")
	public String button() {
		return "button";
	}
	
	@RequestMapping(value = "/chart-morris")
	public String chartMorris() {
		return "chart-morris";
	}
	
	@RequestMapping(value = "/color")
	public String color() {
		return "color";
	}
	
	@RequestMapping(value = "/form-elements-component")
	public String formElementsComponent() {
		return "form-elements-component";
	}
	
	@RequestMapping(value = "/label-badge")
	public String labelBadge() {
		return "label-badge";
	}
	
	@RequestMapping(value = "/map-google")
	public String mapGoogle() {
		return "map-google";
	}
	
	@RequestMapping(value = "/notification")
	public String notification() {
		return "notification";
	}
	
	@RequestMapping(value = "/sample-page")
	public String samplePage() {
		return "sample-page";
	}
	
	@RequestMapping(value = "/tabs")
	public String tabs() {
		return "tabs";
	}
	
	@RequestMapping(value = "/tooltip")
	public String tooltip() {
		return "tooltip";
	}
	
	@RequestMapping(value = "/typography")
	public String typography() {
		return "typography";
	}
}
