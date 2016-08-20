package demo.zss.controller.basicData;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import demo.zss.entity.basic.Subject;
import demo.zss.repository.basic.SubjectRepository;

@Controller
@RequestMapping("/basic")
public class BasicDataSubjectController {
	
	@Autowired
	private SubjectRepository subjectRepository;

	public String toBasicDataIndex(Model model){
		return null;
	}
	
	@RequestMapping("/toBasicDataSubject")
	public String toBasicDataSubject(Model model){
		List<Subject> subjects=subjectRepository.findAll();
		model.addAttribute("subjects", subjects);
		return "/basic/basicDataIndex";
	}
	
	@RequestMapping(value="/addSubject",method=RequestMethod.POST)
	public String addSubject(Subject subject ,Model model){
		subjectRepository.save(subject);
		List<Subject> subjects=subjectRepository.findAll();
		model.addAttribute("subjects", subjects);
		return "/basic/basicDataIndex";
	}
	
	@RequestMapping(value="/deleteSubject",method=RequestMethod.DELETE)
	public String deleteSubject(Long id ,Model model){
		subjectRepository.delete(id);
		List<Subject> subjects=subjectRepository.findAll();
		model.addAttribute("subjects", subjects);
		return "/basic/basicDataIndex";
	}
	
	@RequestMapping(value="/toFileUpload")
	public String tofileUpload(Model model){
		return "/basic/fileUpload";
	}
	
	@RequestMapping(value="/uploadFile")
	public String uploadFile(MultipartFile file,Model model){
		final int BUFFER_SIZE = 4096;
		List<String> names = new ArrayList<String>();
		try {
			InputStream ins = file.getInputStream();
			BufferedInputStream bin = new BufferedInputStream(file.getInputStream());  
				    int p = (bin.read() << 8) + bin.read();  
				    String code = null;  
				    switch (p) {  
				        case 0xefbb:  
				            code = "UTF-8";  
				            break;  
				        case 0xfffe:  
				            code = "Unicode";  
				            break;  
				        case 0xfeff:  
				            code = "UTF-16BE";  
				            break;  
				        default:  
				        	 code = "GBK";
//				             code = "ISO-8859-1";  
				    }  
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			byte[] data = new byte[BUFFER_SIZE];
			int count = -1;
			while ((count = ins.read(data, 0, BUFFER_SIZE)) != -1)
				outStream.write(data, 0, count);
			data = null;
			System.out.println(code);
			String s = new String(outStream.toByteArray(), code);
			String s1[] = s.split("\r\n");

			for (String ss : s1) {
				names.add(ss);
				System.out.println(ss);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("names", names);
		return "/basic/fileUpload::#name";
	}
}
