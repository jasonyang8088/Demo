package sample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 类说明
 * 
 * @author yangqijie
 * @version 2016年3月13日
 */
@Controller
public class HomeController {

	@RequestMapping("/")
	public String home() {
		return "home";
	}
}