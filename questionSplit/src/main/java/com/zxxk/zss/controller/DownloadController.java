package com.zxxk.zss.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.artofsolving.jodconverter.DefaultDocumentFormatRegistry;
import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.DocumentFormat;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.zxxk.zss.entity.Question;

@Controller
@RequestMapping("/download")
public class DownloadController {

	@RequestMapping
	// @ResponseBody
	public String download(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		
		List<Question> lstQuestion = (List<Question>) session.getAttribute("lstQuestion");
		 String charset="UTF-8";
		// Question question =
		// questionService.getPQuestionByQuestionId(Integer.parseInt(questionId),
		// Integer.parseInt(subjectId));
		 if(System.getProperties().getProperty("os.name").toUpperCase().indexOf("WINDOWS") != -1){
			 charset="gb2312";
		 }
		String fileName = "试题.doc";
		try {
			String outputName = URLEncoder.encode(fileName, charset);
			response.setCharacterEncoding(charset);
			response.setContentType("multipart/form-data");
			response.setHeader("Content-Disposition", "attachment;fileName=" + outputName);
			// Document doc = new Document();
			// DocumentBuilder builder = new DocumentBuilder(doc);
			//
			StringBuilder htmlsb = new StringBuilder();
			htmlsb.append("<html>");
			htmlsb.append("<meta http-equiv=\"content-type\" content=\"text/html; charset="+charset+"\"/>");
			htmlsb.append("<body>");
			for (Question q : lstQuestion) {
				htmlsb.append("【题文】");
				if(q.getStem()!=null)htmlsb.append(q.getStem());
				htmlsb.append("【选项】");
				if(q.getOptions()!=null)htmlsb.append(q.getOptions());
				htmlsb.append("</p>");
				htmlsb.append("【答案】");
				if(q.getAnswer()!=null)htmlsb.append(q.getAnswer());
				htmlsb.append("</p>");
				htmlsb.append("【解析】");
				if(q.getAnalyse()!=null)htmlsb.append(q.getAnalyse());
				htmlsb.append("</p>");
				htmlsb.append("【题型】");
				if(q.getType()!=null)htmlsb.append(q.getType());
				htmlsb.append("</p>");
				htmlsb.append("【难度】");
				if(q.getDifficult()!=null)htmlsb.append(q.getDifficult());
				htmlsb.append("</p>");
				htmlsb.append("【分值】");
				if(q.getFenzhi()!=null)htmlsb.append(q.getFenzhi());
				htmlsb.append("</p>");
				htmlsb.append("【说明】");
//				htmlsb.append(q.getType());
				htmlsb.append("</p>");
				htmlsb.append("【结束】");
				htmlsb.append("</p>");
				htmlsb.append("<br>");
				
			}
			htmlsb.append("</body>");
			htmlsb.append("</html>");
			
			InputStream ins = new ByteArrayInputStream(htmlsb.toString().getBytes());
			DefaultDocumentFormatRegistry formatReg = new DefaultDocumentFormatRegistry();
			DocumentFormat doc = formatReg.getFormatByFileExtension("doc");
			DocumentFormat html = formatReg.getFormatByFileExtension("html");
			OpenOfficeConnection connection = new SocketOpenOfficeConnection("localhost", Integer.parseInt("8100"));
			connection.connect();
			DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			converter.convert(ins, html, output, doc);
			connection.disconnect();
			ins.close();
			output.close();
			OutputStream os = response.getOutputStream();
			os.write(output.toByteArray());
			os.close();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// // 返回值要注意，要不然就出现下面这句错误！
		// // java+getOutputStream() has already been called for this response
		return null;
	}
}
