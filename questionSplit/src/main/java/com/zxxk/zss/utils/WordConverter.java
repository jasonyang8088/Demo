package com.zxxk.zss.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.springframework.web.multipart.MultipartFile;

import com.artofsolving.jodconverter.DefaultDocumentFormatRegistry;
import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.DocumentFormat;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

public class WordConverter {
	
	public static String wordToHtml(MultipartFile file) throws IOException{
		InputStream ins = new ByteArrayInputStream(file.getBytes());
		InputStreamReader isr = new InputStreamReader(ins);
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		System.out.println(isr.getEncoding());
		DefaultDocumentFormatRegistry formatReg = new DefaultDocumentFormatRegistry();
		DocumentFormat doc = formatReg.getFormatByFileExtension("doc");
		DocumentFormat html = formatReg.getFormatByFileExtension("html");
		OpenOfficeConnection connection = new SocketOpenOfficeConnection("localhost", Integer.parseInt("8100"));
		connection.connect();
		DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
		converter.convert(ins, doc, output, html);

		connection.disconnect();
		String osName = System.getProperties().getProperty("os.name");
		String content = null;
		if (osName.contains("Windows")) {
			content = new String(output.toByteArray(), "gb2312");
		} else {
			content = new String(output.toByteArray(), "utf8");
		}
		ins.close();
		output.close();
		return content;
	}
	
	public static String wordToTxt(MultipartFile file) throws IOException{
		InputStream ins = new ByteArrayInputStream(file.getBytes());
		InputStreamReader isr = new InputStreamReader(ins);
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		System.out.println(isr.getEncoding());
		DefaultDocumentFormatRegistry formatReg = new DefaultDocumentFormatRegistry();
		DocumentFormat doc = formatReg.getFormatByFileExtension("doc");
		DocumentFormat txt = formatReg.getFormatByFileExtension("txt");
		OpenOfficeConnection connection = new SocketOpenOfficeConnection("localhost", Integer.parseInt("8100"));
		connection.connect();
		DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
		converter.convert(ins, doc, output, txt);

		connection.disconnect();
		String osName = System.getProperties().getProperty("os.name");
		String content = null;
		if (osName.contains("Windows")) {
			content = new String(output.toByteArray(), "gb2312");
		} else {
			content = new String(output.toByteArray(), "utf8");
		}
		ins.close();
		output.close();
		return content;
	}
	
}
