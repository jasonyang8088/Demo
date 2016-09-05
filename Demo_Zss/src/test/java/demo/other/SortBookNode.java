package demo.other;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import demo.zss.Application;
import demo.zss.entity.basic.BookNode;
import demo.zss.entity.basic.TextBook;
import demo.zss.repository.basic.BookNodeRepository;
import demo.zss.repository.basic.TextBookRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@Transactional
public class SortBookNode {
	
	@Autowired
	private BookNodeRepository bookNodeRepository;
	
	@Autowired
	private TextBookRepository textBookRepository;

	@Test
	@Rollback(false)
	public void sortBookNodeTest(){
		List<TextBook> books = textBookRepository.findAll();
		for(TextBook book:books){
			List<BookNode> bookNodes =bookNodeRepository.findByBookIdAndDepth(book.getId(),(byte)1);
			for(int i=0;i<bookNodes.size();i++){
				String order1="";
				if(i<9){order1="0"+(i+1);}
				else order1=i+1+"";
				BookNode node1=bookNodes.get(i);
				node1.setOrderNo(order1+"0000");
				List<BookNode> b2=node1.getChildrens();
					for(int j=0;j<b2.size();j++){
						String order2="";
						if(j<9){order2="0"+(j+1);}
						else order2=j+1+"";
						BookNode node2=b2.get(j);
						node2.setOrderNo(order1+order2+"00");
					}
			}
		}
	}
}
