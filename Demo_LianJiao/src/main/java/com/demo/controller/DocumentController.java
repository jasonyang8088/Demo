package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.entity.Document;
import com.demo.repository.DocumentRepository;

@RestController
public class DocumentController {

	@Autowired
	private DocumentRepository documentRepository;
	
	@RequestMapping("booknode/{nodeId}/documents")
	public List<Document> findByNodeId(@PathVariable("nodeId") Long nodeId){
		return documentRepository.findByNodeId(nodeId);
		
	}
}
