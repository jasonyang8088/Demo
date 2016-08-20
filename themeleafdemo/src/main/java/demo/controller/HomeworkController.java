package demo.controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import demo.entity.Sentence;

@Controller
public class HomeworkController {
	
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public String uploadFile(@RequestParam MultipartFile uploadFile,Model model) {
		int BUFFER_SIZE = 4096;
		List<String> names = new ArrayList<String>();
		String s ="";
		try {
			InputStream ins = uploadFile.getInputStream();
			BufferedInputStream bin = new BufferedInputStream(uploadFile.getInputStream());  
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
			s = new String(outStream.toByteArray(), code);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String s1[] = s.split("\r\n");
		List<Sentence> sentences = new ArrayList<Sentence>();
		for (String ss : s1) {
			names.add(ss);
			Sentence sentence = new Sentence();
			sentence.setEnglish(ss);
			sentences.add(sentence);
		}
		model.addAttribute("sentences", sentences);
		return "index::#import_listem";
	}

}
