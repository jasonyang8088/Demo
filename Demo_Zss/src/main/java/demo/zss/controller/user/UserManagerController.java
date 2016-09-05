package demo.zss.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/user")
public class UserManagerController {

	@RequestMapping(value="userManagerIndex",method=RequestMethod.GET)
	public String toIndex(Model model){
		model.addAttribute("navmenu", 2);
		return "/user/userManagerIndex";
	}
}
