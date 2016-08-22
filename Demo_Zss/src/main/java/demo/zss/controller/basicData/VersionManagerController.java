package demo.zss.controller.basicData;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import demo.zss.controller.basicData.form.VersionManagerSearchForm;
import demo.zss.entity.basic.Subject;
import demo.zss.entity.basic.Version;
import demo.zss.repository.basic.SubjectRepository;
import demo.zss.repository.basic.VersionRepository;

@Controller
@RequestMapping("/basic")
@SessionAttributes("versionManagerSearchForm")
public class VersionManagerController {
	
	@Autowired
	private VersionRepository versionRepository;
	
	@Autowired
	private SubjectRepository subjectRepository;
	
	@RequestMapping(value="/versionManagerIndex",method=RequestMethod.GET)
	public String toIndex(VersionManagerSearchForm form,Model model){
		List<Subject> subjects = subjectRepository.findByStage(form.getStage());
		model.addAttribute("subjects", subjects);
		PageRequest page=new PageRequest(0, 10);
		Page<Version> v =versionRepository.findAll(createSpecification(form),page);
		model.addAttribute("versions", v.getContent());
		model.addAttribute("totalPage", v.getTotalPages());
		model.addAttribute("version", new Version());
		model.addAttribute("leftmenu", 2);
		return "/basic/versionManagerIndex";
	}
	
	@RequestMapping(value="/addVersion",method=RequestMethod.POST)
	@ResponseBody
	public String addVersion(Version version,Model model){
		versionRepository.save(version);
		model.addAttribute("version", new Version());
		return "success";
	}
	
	@RequestMapping(value="/deleteVersion")
	public String deleteVersion(Long id,Model model){
		versionRepository.delete(id);
		model.addAttribute("versions", versionRepository.findAll());
		return "redirect:/basic/versionManagerIndex";
	}
	
	@RequestMapping(value="/getSubjects")
	public String getSubject(@RequestParam Byte stage,Model model){
		List<Subject> subjects = subjectRepository.findByStage(stage);
		model.addAttribute("subjects", subjects);
		return "/basic/versionManagerIndex::#subjectOption";
	}
	
	@RequestMapping(value="/changeTable")
	public String changeTable(VersionManagerSearchForm form,Model model){
		List<Subject> subjects = subjectRepository.findByStage(form.getStage());
		model.addAttribute("subjects", subjects);
		model.addAttribute("versionManagerSearchForm", form);
		PageRequest page=new PageRequest(0, 10);
		Page<Version> v =versionRepository.findAll(createSpecification(form),page);
		model.addAttribute("versions", v.getContent());
		model.addAttribute("totalPage", v.getTotalPages());
		return "/basic/versionManagerIndex::#table";
	}
	
	@RequestMapping(value="/changeTableData")
	public String changeTableData(VersionManagerSearchForm form,Integer page,Model model){
		List<Subject> subjects = subjectRepository.findByStage(form.getStage());
		model.addAttribute("subjects", subjects);
		PageRequest pageRequest=new PageRequest(page-1, 10);
		Page<Version> v =versionRepository.findAll(createSpecification(form),pageRequest);
		model.addAttribute("versions", v.getContent());
		model.addAttribute("totalPage", v.getTotalPages());
		return "/basic/versionManagerIndex::#tableData";
	}
	
	public Specification<Version> createSpecification(VersionManagerSearchForm form){
		 Specification<Version> spec = new Specification<Version>() {
			 public Predicate toPredicate(Root<Version> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
			 Join<Version,Subject> depJoin = root.join(root.getModel().getSingularAttribute("subject",Subject.class) , JoinType.LEFT);
			 List<Predicate> list = new ArrayList<Predicate>();  
			 if(form.getStage()!=null&&form.getStage()!=0){
				 list.add(cb.equal(depJoin.get("stage").as(Byte.class), form.getStage()));
			 }
			 if(form.getSubjectId()!=null&&form.getSubjectId()!=0){
				 list.add(cb.equal(depJoin.get("id").as(Long.class), form.getSubjectId()));
			}
			 Predicate[] p = new Predicate[list.size()];  
			    return cb.and(list.toArray(p));  
			 }}; 
		return spec;
	}

}
