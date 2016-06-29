package com.zxxk.zss.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.zxxk.zss.entity.Question;
import com.zxxk.zss.service.SplitService;
import com.zxxk.zss.utils.SplitFactory;
import com.zxxk.zss.utils.WordConverter;

@Controller
@RequestMapping("/fileupload")
public class FileUploadController {

	@Autowired
	private SplitFactory splitFactory;

	@RequestMapping(method = RequestMethod.GET)
	public void fileUploadForm() {
	}

	@RequestMapping(method = RequestMethod.POST)
	public String processUpload(@RequestParam MultipartFile file, Model model, String subjectName,HttpSession session) throws IOException {
		List<Question> lstQuestionFirst = new ArrayList<Question>();

		String content = WordConverter.wordToHtml(file);
		content = content.replaceAll("\r|\n", "");
//		content = content.replaceAll("<font[^>]*>([\\s\\S]*?)", "");
//		content = content.replaceAll("</font>", "");
//		content = content.replaceAll("<span[^>]*>([\\s\\S]*?)", "");
//		content = content.replaceAll("</span>", "");
		content = content.replaceAll("<b[^>]*>([\\s\\S]*?)", "");
		content = content.replaceAll("</b>", "");

		String regex = "((<p[^>]*>([\\s\\S]*?)</p>)|(<table[^>]*>([\\s\\S]*?)</table>))";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(content);
		List<String> lstContent = new ArrayList<String>();
		while (m.find()) {
			lstContent.add(m.group());
		}

		SplitService splitService = splitFactory.createSplitService(subjectName);
		lstQuestionFirst = splitService.splitQuestion(lstContent);
		model.addAttribute("lstQuestion", lstQuestionFirst);
		session.setAttribute("lstQuestion", lstQuestionFirst);
		return "showQuestion";
	}

	

}
