package demo.zss.controller.basicData;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import demo.zss.controller.form.BookNodeManagerSearchForm;
import demo.zss.entity.basic.BookNode;
import demo.zss.entity.basic.Subject;
import demo.zss.entity.basic.TextBook;
import demo.zss.entity.basic.Version;
import demo.zss.repository.basic.BookNodeRepository;
import demo.zss.repository.basic.SubjectRepository;
import demo.zss.repository.basic.TextBookRepository;
import demo.zss.repository.basic.VersionRepository;

@Controller
@RequestMapping("/basic")
@SessionAttributes("bookNodeManagerSearchForm")
public class BookNodeManagerController {

	@Autowired
	private VersionRepository versionRepository;

	@Autowired
	private SubjectRepository subjectRepository;

	@Autowired
	private TextBookRepository textBookRepository;

	@Autowired
	private BookNodeRepository bookNodeRepository;

	private Logger logger = LoggerFactory.getLogger(BookNodeManagerController.class);

	@RequestMapping(value = "/bookNodeManagerIndex", method = RequestMethod.GET)
	public String toIndex(BookNodeManagerSearchForm form, Model model) {
		System.out.println(form);
		List<Subject> subjects = new ArrayList<Subject>();
		List<Version> versions = new ArrayList<Version>();
		List<TextBook> textbooks = new ArrayList<TextBook>();
		List<BookNode> booknodes = new ArrayList<BookNode>();
		if(form.getStage()==null||form.getStage()==0){
			form.setStage((byte)2);
		}
		subjects = subjectRepository.findByStage(form.getStage());
		if (form.getSubjectId() == null||form.getSubjectId()==0) {
			if (subjects.size() > 0) {
				form.setSubjectId(subjects.get(0).getId());
			}
		}
		versions = versionRepository.findBySubjectId(form.getSubjectId());
		if (form.getVersionId() == null||form.getVersionId()==0) {
			if (versions.size() > 0) {
				form.setVersionId(versions.get(0).getId());
			}
		}
		textbooks = textBookRepository.findByVersionId(form.getVersionId());
		if (form.getTextBookId() == null||form.getTextBookId()==0) {
			if (textbooks.size() > 0) {
				form.setTextBookId(textbooks.get(0).getId());
			}
		}
		booknodes = bookNodeRepository.findByBookId(form.getTextBookId());
		model.addAttribute("subjects", subjects);
		model.addAttribute("versions", versions);
		model.addAttribute("textbooks", textbooks);
		model.addAttribute("booknodes", booknodes);
		return "/basic/bookNodeManagerIndex";
	}

	@RequestMapping(value = "/changeTableForBookNodeManager")
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
		return "/basic/bookNodeManagerIndex::#bookNodeManager";
	}

	@RequestMapping(value = "uploadBookNodeFile", method = RequestMethod.POST)
	@ResponseBody
	public String uploadFile(MultipartFile file, Long textBookId) {
		logger.info("上传章节文档：" + file.getOriginalFilename()+";id="+textBookId);
		TextBook book = textBookRepository.findOne(textBookId);
		try {
			InputStream inp = file.getInputStream();
			Workbook wb = WorkbookFactory.create(inp);
			Sheet sheet = wb.getSheetAt(0);
			BookNode[] array = new BookNode[3];
			for (int rowNum = 0; rowNum <= sheet.getLastRowNum(); rowNum++) {
				Row row = sheet.getRow(rowNum);
				if (row == null) {
					continue;
				}
				for (int cellNum = 0; cellNum <= row.getLastCellNum(); cellNum++) {
					Cell cell = row.getCell(cellNum);
					if (cell == null) {
						continue;
					}
					if (cellNum == 0) {
						BookNode booknode = bookNodeRepository.findByBookIdAndNameAndDepth(book.getId(),
								cell.getStringCellValue(), (byte) 1);
						if (booknode == null) {
							booknode = new BookNode();
							booknode.setName(cell.getStringCellValue());
							booknode.setDepth((byte) 1);
							booknode.setBook(book);
							booknode.setStatus((byte) 1);
							bookNodeRepository.save(booknode);
						}else {
							booknode.setStatus((byte) 1);
							bookNodeRepository.save(booknode);
						}
						array[0] = booknode;
					}
					if (cellNum == 1) {
						BookNode booknode = bookNodeRepository.findByBookIdAndNameAndDepth(book.getId(),
								cell.getStringCellValue(), (byte) 2);
						if (booknode == null) {
							booknode = new BookNode();
							booknode.setName(cell.getStringCellValue());
							booknode.setDepth((byte) 2);
							booknode.setParent(array[0]);
							booknode.setBook(book);
							booknode.setStatus((byte) 1);
							bookNodeRepository.save(booknode);
						}else{
							booknode.setStatus((byte) 1);
							bookNodeRepository.save(booknode);
						}
						array[1] = booknode;
					}
					if (cellNum == 2) {
						BookNode booknode = bookNodeRepository.findByBookIdAndNameAndDepth(book.getId(),
								cell.getStringCellValue(), (byte) 2);
						if (booknode == null) {
							booknode = new BookNode();
							booknode.setName(cell.getStringCellValue());
							booknode.setDepth((byte) 3);
							booknode.setBook(book);
							booknode.setParent(array[1]);
							booknode.setStatus((byte) 1);
							bookNodeRepository.save(booknode);
						}else{
							booknode.setStatus((byte) 1);
							bookNodeRepository.save(booknode);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	public Specification<BookNode> createSpecification(BookNodeManagerSearchForm form) {
		Specification<BookNode> spec = new Specification<BookNode>() {
			public Predicate toPredicate(Root<BookNode> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Join<BookNode, Subject> depJoin = root
						.join(root.getModel().getSingularAttribute("subject", Subject.class), JoinType.LEFT);
				List<Predicate> list = new ArrayList<Predicate>();
				if (form.getStage() != null && form.getStage() != 0) {
					list.add(cb.equal(depJoin.get("stage").as(Byte.class), form.getStage()));
				}
				if (form.getSubjectId() != null && form.getSubjectId() != 0) {
					list.add(cb.equal(depJoin.get("id").as(Long.class), form.getSubjectId()));
				}
				Predicate[] p = new Predicate[list.size()];
				return cb.and(list.toArray(p));
			}
		};
		return spec;
	}

}
