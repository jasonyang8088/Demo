package demo.zss.repository.basic;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.zss.entity.basic.Document;

public interface DocumentRepository extends JpaRepository<Document, Long> {

}
