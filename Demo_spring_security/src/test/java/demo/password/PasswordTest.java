package demo.password;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class PasswordTest {

	@Test
	public void geneTest(){
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String result = encoder.encode("myPassword");
		System.out.println(result);
		assertTrue(encoder.matches("myPassword", result));
	}
}
