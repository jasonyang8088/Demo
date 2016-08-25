package demo.zss.controller.basicData;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import demo.zss.controller.form.TextBookManagerSearchForm;
import demo.zss.controller.form.VersionManagerSearchForm;
import demo.zss.entity.basic.Subject;
import demo.zss.entity.basic.TextBook;
import demo.zss.entity.basic.Version;
import demo.zss.repository.basic.SubjectRepository;
import demo.zss.repository.basic.TextBookRepository;
import demo.zss.repository.basic.VersionRepository;

@Controller
@RequestMapping("/basic")
@SessionAttributes("textBookManagerSearchForm")
public class TextBookManagerController {

	@Autowired
	private TextBookRepository textbookRepository;

	@Autowired
	private VersionRepository versionRepository;

	@Autowired
	private SubjectRepository subjectRepository;

	private Logger logger = LoggerFactory.getLogger(TextBookManagerController.class);
	
	@RequestMapping(value = "/textBookManagerIndex", method = RequestMethod.GET)
	public String toIndex(TextBookManagerSearchForm form, Model model) {
		List<Subject> subjects = subjectRepository.findByStage(form.getStage());
		List<Version> versions = versionRepository.findBySubjectId(form.getSubjectId());
		model.addAttribute("subjects", subjects);
		model.addAttribute("versions", versions);
		PageRequest page = new PageRequest(0, 10);
		Page<TextBook> t = textbookRepository.findAll(createSpecification(form), page);
		model.addAttribute("textbooks", t.getContent());
		model.addAttribute("totalPage", t.getTotalPages());
		model.addAttribute("textbook", new TextBook());
		model.addAttribute("leftmenu", 3);
		return "/basic/textBookManagerIndex";
	}


	@RequestMapping(value="/getSubjectsForTextBookManager")
	public String getSubject(@RequestParam Byte stage,Model model){
		List<Subject> subjects = subjectRepository.findByStage(stage);
		model.addAttribute("subjects", subjects);
		return "/basic/textBookManagerIndex::#subjectOption";
	}
	
	@RequestMapping(value="/getVersionsForTextBookManager")
	public String getVersion(@RequestParam Long subjectId,Model model){
		List<Version> versions = versionRepository.findBySubjectId(subjectId);
		model.addAttribute("versions", versions);
		return "/basic/textBookManagerIndex::#versionOption";
	}
	
	@RequestMapping(value="/addTextBook",method=RequestMethod.POST)
	@ResponseBody
	public String addVersion(TextBook TextBook,Model model){
		textbookRepository.save(TextBook);
		return "success";
	}
	
	@RequestMapping(value="/deleteTextBook")
	public String deleteVersion(Long id,Model model){
		textbookRepository.delete(id);
		return "redirect:/basic/textBookManagerIndex";
	}
	
	@RequestMapping(value="/changeTableForTextBookManager")
	public String changeTable(TextBookManagerSearchForm form,Model model){
		List<Subject> subjects = subjectRepository.findByStage(form.getStage());
		List<Version> versions = versionRepository.findBySubjectId(form.getSubjectId());
		model.addAttribute("subjects", subjects);
		model.addAttribute("versions", versions);
		PageRequest page=new PageRequest(0, 10);
		Page<TextBook> t =textbookRepository.findAll(createSpecification(form),page);
		model.addAttribute("textbooks", t.getContent());
		model.addAttribute("totalPage", t.getTotalPages());
		return "/basic/textBookManagerIndex::#table";
	}
	
	@RequestMapping(value="/changeTableDataForTextBookManager")
	public String changeTableData(TextBookManagerSearchForm form,Integer page,Model model){
		List<Subject> subjects = subjectRepository.findByStage(form.getStage());
		model.addAttribute("subjects", subjects);
		PageRequest pageRequest=new PageRequest(page-1, 10);
		Page<TextBook> v =textbookRepository.findAll(createSpecification(form),pageRequest);
		model.addAttribute("textbooks", v.getContent());
		model.addAttribute("totalPage", v.getTotalPages());
		return "/basic/textBookManagerIndex::#tableData";
	}

	public Specification<TextBook> createSpecification(TextBookManagerSearchForm form) {
		logger.info("form.stage="+form.getStage());
		logger.info("form.subjectId="+form.getSubjectId());
		logger.info("form.versionId="+form.getVersionId());
		Specification<TextBook> spec = new Specification<TextBook>() {
			public Predicate toPredicate(Root<TextBook> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				
				Join<TextBook, Subject> subjectJoin = root
						.join(root.getModel().getSingularAttribute("subject", Subject.class), JoinType.INNER);
				if (form.getStage() != null && form.getStage() != 0) {
					list.add(cb.equal(subjectJoin.get("stage").as(Byte.class), form.getStage()));
				}
				if (form.getSubjectId() != null && form.getSubjectId() != 0) {
					list.add(cb.equal(subjectJoin.get("id").as(Long.class), form.getSubjectId()));
				}
				Join<TextBook, Version> versionJoin = root
						.join(root.getModel().getSingularAttribute("version", Version.class), JoinType.INNER);
				if (form.getVersionId() != null && form.getVersionId() != 0) {
					list.add(cb.equal(versionJoin.get("id").as(Long.class), form.getVersionId()));
				}
				Predicate[] p = new Predicate[list.size()];
				return cb.and(list.toArray(p));
			}
		};
		return spec;
	}

}
