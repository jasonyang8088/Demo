package demo.zss.controller.document;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/document")
public class DocumentManagerController {
	
	@RequestMapping(value="documentManagerIndex",method=RequestMethod.GET)
	public String toIndex(Model model){
		model.addAttribute("navmenu", 3);
		return "/document/documentManagerIndex";
	}

}
