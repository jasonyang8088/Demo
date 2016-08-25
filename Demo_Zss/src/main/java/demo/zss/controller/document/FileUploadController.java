package demo.zss.controller.document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import demo.zss.controller.form.BookNodeManagerSearchForm;
import demo.zss.entity.basic.BookNode;
import demo.zss.entity.basic.Document;
import demo.zss.entity.basic.DocumentSpecialSpec;
import demo.zss.entity.basic.Subject;
import demo.zss.entity.basic.TextBook;
import demo.zss.entity.basic.Version;
import demo.zss.entity.mongo.MDocument;
import demo.zss.entity.user.User;
import demo.zss.repository.UserRepository;
import demo.zss.repository.basic.BookNodeRepository;
import demo.zss.repository.basic.DocumentRepository;
import demo.zss.repository.basic.DocumentSpecialSpecRepository;
import demo.zss.repository.basic.SubjectRepository;
import demo.zss.repository.basic.TextBookRepository;
import demo.zss.repository.basic.VersionRepository;
import demo.zss.repository.mongo.MDocumentRepository;
import demo.zss.service.basic.DocumentSpecialSpecService;

@Controller
@RequestMapping("/document")
@SessionAttributes("bookNodeManagerSearchForm")
public class FileUploadController {
	@Autowired
	private VersionRepository versionRepository;

	@Autowired
	private SubjectRepository subjectRepository;

	@Autowired
	private TextBookRepository textBookRepository;

	@Autowired
	private BookNodeRepository bookNodeRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private DocumentRepository documentRepository;
	
	@Autowired
	private DocumentSpecialSpecService documentSpecialSpecService;
	
	@Autowired
	private DocumentSpecialSpecRepository documentSpecialSpecRepository;
	
	@Autowired
	private MDocumentRepository mDocumentRepository;	
	
	@RequestMapping(value="/uploadFileManager",method=RequestMethod.GET)
	public String toIndex(BookNodeManagerSearchForm form,Model model){
		model.addAttribute("navleft", 2);
		model.addAttribute("document", new Document());
		model.addAttribute("documentSpecialSpec", new DocumentSpecialSpec());
		return "/document/documentUploadManager";
	}
	
	@RequestMapping(value="/addRelation",method=RequestMethod.POST)
	public String addRelation(BookNodeManagerSearchForm form,DocumentSpecialSpec documentSpecialSpec,Model model){
		documentSpecialSpec = documentSpecialSpecService.returnSpecial(documentSpecialSpec);
		model.addAttribute("documentSpecialSpec", documentSpecialSpec);
		return "/document/documentUploadManager::special";
	}
	
	@RequestMapping(value="/addDocument",method=RequestMethod.POST)
	public String addDocument(Document document,Long[] specialId,Model model){
		List<DocumentSpecialSpec> specials=new ArrayList<DocumentSpecialSpec>();
		if(0<specialId.length){
			for(Long id :specialId){
				DocumentSpecialSpec spec=documentSpecialSpecRepository.findOne(id);
				specials.add(spec);
			}
		}
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username=userDetails.getUsername();
		User user = userRepository.findByUsername(username);
		document.setCreateUser(user);
		document.setCreateTime(new Date());
		document.setDocumentSpecialSpecs(specials);
		documentRepository.save(document);
		return "redirect:/document/uploadFileManager";
	}
	
	@RequestMapping(value="/uploadDocumentFile",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> uploadFile(MultipartFile file) {
		MDocument mdoc= new MDocument();
		Map<String,Object> map=new HashMap<String,Object>();
		try {
			String name=file.getOriginalFilename();
			Long size=file.getSize();
			String types[]=name.split("\\.");
			String type=types[types.length-1];
			mdoc.setFile(file.getBytes());
			mdoc.setName(name);
			mdoc.setType(type);
			mdoc.setSize(size);
			mDocumentRepository.save(mdoc);
			map.put("name", name);
			map.put("size", size);
			map.put("docSuffix", type);
			map.put("mongoFileId", mdoc.getId());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	@RequestMapping(value = "/changeTableForDocumentManager")
	public String changeTable(BookNodeManagerSearchForm form, Model model) {
		List<Subject> subjects = new ArrayList<Subject>();
		List<Version> versions = new ArrayList<Version>();
		List<TextBook> textbooks = new ArrayList<TextBook>();
		List<BookNode> booknodes = new ArrayList<BookNode>();
		subjects = subjectRepository.findByStage(form.getStage());
		if (form.getSubjectId() == 0) {
			if (subjects.size() > 0) {
				form.setSubjectId(subjects.get(0).getId());
			}
		}
		versions = versionRepository.findBySubjectId(form.getSubjectId());
		if (form.getVersionId() == 0) {
			if (versions.size() > 0) {
				form.setVersionId(versions.get(0).getId());
			}
		}
		textbooks = textBookRepository.findByVersionId(form.getVersionId());
		if (form.getTextBookId() == 0) {
			if (textbooks.size() > 0) {
				form.setTextBookId(textbooks.get(0).getId());
			}
		}
		booknodes = bookNodeRepository.findByBookId(form.getTextBookId());
		model.addAttribute("subjects", subjects);
		model.addAttribute("versions", versions);
		model.addAttribute("textbooks", textbooks);
		model.addAttribute("booknodes", booknodes);
		return "/document/documentUploadManager::#bookNodeManager";
	}
}
