package demo.zss.repository.mongo;

import org.springframework.data.repository.CrudRepository;

import demo.zss.entity.mongo.MDocument;

public interface MDocumentRepository extends CrudRepository<MDocument, String>{

}
