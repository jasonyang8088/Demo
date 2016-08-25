package demo.repository.mongo;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import demo.zss.Application;
import demo.zss.entity.basic.Subject;
import demo.zss.entity.mongo.ZDocument;
import demo.zss.entity.mongo.ZSubject;
import demo.zss.repository.mongo.MDocumentRepository;



@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@Transactional
public class ZDocumentRepositoryTest {

	@Autowired
	private MDocumentRepository repository;
	
	@Test
	@Rollback(false)
	public void addDocument(){
		ZSubject sub = new ZSubject();
//		sub.setId((long)1);
		sub.setName("数学");
		List<ZSubject> list= new ArrayList<ZSubject>();
		ZDocument doc = new ZDocument();
		doc.setId((long)3);
		doc.setName("thish iss doc");
		list.add(sub);
		list.add(sub);
		doc.setSubs(list);
	}
}
