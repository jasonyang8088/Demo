package demo.zss.controller.basicData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.zss.entity.basic.Subject;
import demo.zss.repository.basic.SubjectRepository;

@Controller
@RequestMapping("/basic")
public class SubjectManagerController {
	
	@Autowired
	private SubjectRepository subjectRepository;
	
	@RequestMapping(value="subjectManagerIndex",method=RequestMethod.GET)
	public String toIndex(Model model){
		model.addAttribute("navmenu", 1);
		model.addAttribute("navleft", 1);
		PageRequest page=new PageRequest(0, 10);
		Page<Subject> subjects=subjectRepository.findAll(page);
		model.addAttribute("totalPage", subjects.getTotalPages());
		model.addAttribute("subjects", subjects.getContent());
		model.addAttribute("totalNum", subjects.getTotalElements());
		return "/basic/subjectManagerIndex";
	}
	
	@RequestMapping(value="addSubject",method=RequestMethod.POST)
	@ResponseBody
	public String addSubject(Subject subject,Model model){
		subjectRepository.save(subject);
		model.addAttribute("subject", new Subject());
		return "success";
	}
	
	@RequestMapping(value="/deleteSubject")
	public String deleteSubject(Long id,Model model){
		System.out.println(id);
		subjectRepository.delete(id);
		model.addAttribute("subjects", subjectRepository.findAll());
		return "/basic/subjectManagerIndex";
	}
	
	@RequestMapping(value="/changeTableDataForSubject")
	public String changeTableData(Integer page,Model model){
		PageRequest pageRequest=new PageRequest(page-1, 10);
		Page<Subject> subjects=subjectRepository.findAll(pageRequest);
		model.addAttribute("totalPage", subjects.getTotalPages());
		model.addAttribute("subjects", subjects.getContent());
		model.addAttribute("totalNum", subjects.getTotalElements());
		return "/basic/subjectManagerIndex::#tableData";
	}

}
