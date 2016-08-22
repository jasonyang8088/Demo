package demo.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import demo.zss.Application;
import demo.zss.entity.basic.Subject;
import demo.zss.entity.basic.Version;
import demo.zss.repository.basic.VersionRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@Transactional
public class VersionRepositoryTest {
	
	private VersionRepository versionRepository;
	
	@Test
	@Rollback(false)
	public void addVersionTest(){
		Version v = new Version();
		Subject s = new Subject();
		s.setId((long)312);
		v.setSubject(s);
		v.setVersionName("test");
		versionRepository.save(v);
	}

}
