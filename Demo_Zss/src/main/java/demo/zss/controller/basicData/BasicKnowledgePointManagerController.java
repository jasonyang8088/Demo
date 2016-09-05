package demo.zss.controller.basicData;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import demo.zss.controller.form.BasicKnowledgePointManagerSearchForm;
import demo.zss.entity.basic.BasicKnowledgePoint;
import demo.zss.entity.basic.Subject;
import demo.zss.repository.basic.BasicKnowledgePointRepository;
import demo.zss.repository.basic.SubjectRepository;

@Controller
@RequestMapping("/basic")
@SessionAttributes("basicKnowledgePointManagerSearchForm")
public class BasicKnowledgePointManagerController {

	@Autowired
	private SubjectRepository subjectRepository;

	@Autowired
	private BasicKnowledgePointRepository basicKnowledgePointRepository;

	private Logger logger = LoggerFactory.getLogger(BasicKnowledgePointManagerController.class);

	@RequestMapping(value = "/basicKnowledgePointManagerIndex", method = RequestMethod.GET)
	public String toIndex(BasicKnowledgePointManagerSearchForm form, Model model) {
		logger.info("跳转到基础知识点管理页面");
		model.addAttribute("navmenu", 1);
		model.addAttribute("navleft", 6);
		List<Subject> subjects = new ArrayList<Subject>();
		List<BasicKnowledgePoint> basicKnowledgePoints=new ArrayList<BasicKnowledgePoint>();
		if (form.getStage() == null || form.getStage() == 0) {
			form.setStage((byte) 2);
		}
		subjects = subjectRepository.findByStage(form.getStage());
		basicKnowledgePoints = basicKnowledgePointRepository.findAll();
		model.addAttribute("subjects", subjects);
		model.addAttribute("basicKnowledgePoints", basicKnowledgePoints);
		return "/basic/basicKnowledgePointManagerIndex";
	}

	@RequestMapping(value = "/changeTableForBasicKnowledgePointManager")
	public String changeTable(BasicKnowledgePointManagerSearchForm form, Model model) {
		List<Subject> subjects = new ArrayList<Subject>();
		subjects = subjectRepository.findByStage(form.getStage());
		List<BasicKnowledgePoint> basicKnowledgePoints=new ArrayList<BasicKnowledgePoint>();
		if (form.getSubjectId() == 0) {
			if (subjects.size() > 0) {
				form.setSubjectId(subjects.get(0).getId());
			}
		}
		basicKnowledgePoints = basicKnowledgePointRepository.findBySubjectId(form.getSubjectId());
		model.addAttribute("subjects", subjects);
		model.addAttribute("basicKnowledgePoints", basicKnowledgePoints);
		return "/basic/basicKnowledgePointManagerIndex::#bookNodeManager";
	}
}
