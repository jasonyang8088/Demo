package demo.show1;

import org.springframework.stereotype.Service;

@Service
public class Show {

	private Integer age;
	
	public void Syso(Integer age){
		System.out.println("show age");
		this.age=age;
		System.out.println(this.age);
	}
}
