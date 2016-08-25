package demo.zss.controller.document;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import demo.zss.controller.form.CommonSearchForm;
import demo.zss.entity.basic.Document;
import demo.zss.repository.basic.DocumentRepository;

@Controller
@RequestMapping("/document")
@SessionAttributes("commonSearchForm")
public class DocumentListController {
	
	@Autowired
	private DocumentRepository documentRepository;
	
	@RequestMapping(value="/documentListManager",method=RequestMethod.GET)
	public String toIndex(CommonSearchForm form,Model model){
		List<Document> documents =documentRepository.findAll();
		model.addAttribute("documents", documents);
		return "/document/documentListManager";
	}
}
