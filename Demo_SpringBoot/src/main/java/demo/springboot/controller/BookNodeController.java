package demo.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import demo.springboot.entity.BookNode;
import demo.springboot.repository.BookNodeRepository;

@Controller
public class BookNodeController {
	
	@Autowired
	private BookNodeRepository bookNodeRepository;

	@RequestMapping("/")
	public String index(Model model){
		List<BookNode> bookNodes =bookNodeRepository.findAll();
		model.addAttribute("bookNodes", bookNodes);
		return "index";
	}
}
