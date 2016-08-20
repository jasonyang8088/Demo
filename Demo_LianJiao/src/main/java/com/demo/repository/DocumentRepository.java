package com.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.entity.Document;

public interface DocumentRepository extends JpaRepository<Document, Long>{

	List<Document> findByNodeId(Long nodeId);

}
