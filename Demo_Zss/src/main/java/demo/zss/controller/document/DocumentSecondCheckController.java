package demo.zss.controller.document;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import demo.zss.controller.form.DocumentListSearchForm;
import demo.zss.entity.basic.Document;
import demo.zss.entity.basic.DocumentSpecialSpec;
import demo.zss.entity.basic.Subject;
import demo.zss.entity.basic.TextBook;
import demo.zss.entity.basic.Version;
import demo.zss.entity.mongo.MDocument;
import demo.zss.repository.basic.DocumentRepository;
import demo.zss.repository.basic.SubjectRepository;
import demo.zss.repository.basic.TextBookRepository;
import demo.zss.repository.basic.VersionRepository;
import demo.zss.repository.mongo.MDocumentRepository;

@Controller
@RequestMapping("/document")
@SessionAttributes("documentListSearchForm")
public class DocumentSecondCheckController {

	@Autowired
	private SubjectRepository subjectRepository;

	@Autowired
	private VersionRepository versionRepository;

	@Autowired
	private TextBookRepository textBookRepository;

	@Autowired
	private DocumentRepository documentRepository;

	@Autowired
	private MDocumentRepository mDocumentRepository;

	@RequestMapping(value = "/documentSecondCheckManager", method = RequestMethod.GET)
	public String toIndex(DocumentListSearchForm form, Model model) {
		model.addAttribute("navmenu", 3);
		model.addAttribute("navleft", 3);
		form.setStatus((byte)2);
		List<Subject> subjects = subjectRepository.findByStage(form.getStage());
		model.addAttribute("subjects", subjects);
		List<Version> versions = versionRepository.findBySubjectId(form.getSubjectId());
		model.addAttribute("versions", versions);
		List<TextBook> textBooks = textBookRepository.findByVersionId(form.getVersionId());
		model.addAttribute("textBooks", textBooks);
		PageRequest page = new PageRequest(0, 10);
		Page<Document> documents = documentRepository.findAll(createSpecification(form), page);
		model.addAttribute("documents", documents);
		model.addAttribute("totalPage", documents.getTotalPages());
		model.addAttribute("totalNum", documents.getTotalElements());
		return "/document/documentSecondCheckManager";
	}


	@RequestMapping(value = "/downloadForSecondCheck", method = RequestMethod.GET)
	public ResponseEntity<byte[]> download(String mongoFileId){
		 HttpHeaders headers = new HttpHeaders(); 
		MDocument doc=mDocumentRepository.findOne(mongoFileId);
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);    
        try {
			headers.setContentDispositionFormData("attachment", new String(doc.getName().getBytes("UTF-8"), "ISO8859-1"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<byte[]>(doc.getFile(), headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/changeTableForDocumentSecondCheck")
	public String changeTable(DocumentListSearchForm form, Model model) {
		List<Subject> subjects = subjectRepository.findByStage(form.getStage());
		model.addAttribute("subjects", subjects);
		List<Version> versions = versionRepository.findBySubjectId(form.getSubjectId());
		model.addAttribute("versions", versions);
		List<TextBook> textBooks = textBookRepository.findByVersionId(form.getVersionId());
		model.addAttribute("textBooks", textBooks);
		PageRequest pageRequest = new PageRequest(0, 10);
		Page<Document> documents = documentRepository.findAll(createSpecification(form), pageRequest);
		model.addAttribute("documents", documents);
		model.addAttribute("totalPage", documents.getTotalPages());
		model.addAttribute("totalNum", documents.getTotalElements());
		return "/document/documentSecondCheckManager::#table";
	}

	@RequestMapping(value = "/changeTableDataForDocumentSecondCheck")
	public String changeTableData(DocumentListSearchForm form, Integer page, Model model) {
		List<Subject> subjects = subjectRepository.findByStage(form.getStage());
		model.addAttribute("subjects", subjects);
		List<Version> versions = versionRepository.findBySubjectId(form.getSubjectId());
		model.addAttribute("versions", versions);
		List<TextBook> textBooks = textBookRepository.findByVersionId(form.getVersionId());
		model.addAttribute("textBooks", textBooks);
		PageRequest pageRequest = new PageRequest(page - 1, 10);
		Page<Document> documents = documentRepository.findAll(createSpecification(form), pageRequest);
		model.addAttribute("documents", documents);
		model.addAttribute("totalPage", documents.getTotalPages());
		model.addAttribute("totalNum", documents.getTotalElements());
		return "/document/documentSecondCheckManager::#tableData";
	}

	public Specification<Document> createSpecification(DocumentListSearchForm form) {
		Specification<Document> spec = new Specification<Document>() {
			public Predicate toPredicate(Root<Document> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				ListJoin<Document, DocumentSpecialSpec> depJoin = root.join(
						root.getModel().getList("documentSpecialSpecs", DocumentSpecialSpec.class), JoinType.INNER);
				List<Predicate> list = new ArrayList<Predicate>();
				if (form.getStage() != null && form.getStage() != 0) {
					list.add(cb.equal(depJoin.get("subject").get("stage").as(Byte.class), form.getStage()));
				}
				if (form.getSubjectId() != null && form.getSubjectId() != 0) {
					list.add(cb.equal(depJoin.get("subject").get("id").as(Long.class), form.getSubjectId()));
				}
				if (form.getVersionId() != null && form.getVersionId() != 0) {
					list.add(cb.equal(depJoin.get("version").get("id").as(Long.class), form.getVersionId()));
				}
				if (form.getTextBookId() != null && form.getTextBookId() != 0) {
					list.add(cb.equal(depJoin.get("textBook").get("id").as(Long.class), form.getTextBookId()));
				}
				if (null != form.getStatus() && 0 != form.getStatus()) {
					list.add(cb.equal(root.get("status").as(Byte.class), form.getStatus()));
				}
				Predicate[] p = new Predicate[list.size()];
				query.distinct(true);
				return cb.and(list.toArray(p));
			}
		};
		return spec;
	}
}
