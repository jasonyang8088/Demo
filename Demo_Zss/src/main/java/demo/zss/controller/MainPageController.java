package demo.zss.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainPageController {
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String toInde(Model model){
		return "/index";
	}
	
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public String toIndex(Model model){
		return "/index";
	}

}
