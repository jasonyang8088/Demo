package demo.zss.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.zss.entity.user.Authority;
import demo.zss.repository.AuthorityRepository;

@Controller
@RequestMapping("/user")
public class AuthorityManagerController {
	
	@Autowired
	private AuthorityRepository authorityRepository;

	@RequestMapping(value="authorityManager",method=RequestMethod.GET)
	public String toIndex(Model model){
		model.addAttribute("navmenu", 2);
		model.addAttribute("navleft", 4);
		PageRequest page=new PageRequest(0, 10);
		Page<Authority> authoritys=authorityRepository.findAll(page);
		model.addAttribute("totalPage", authoritys.getTotalPages());
		model.addAttribute("authoritys", authoritys.getContent());
		model.addAttribute("totalNum", authoritys.getTotalElements());
		model.addAttribute("authority", new Authority());
		return "/user/authorityManager";
	}
	
	@RequestMapping(value="/addAuthority",method=RequestMethod.POST)
	@ResponseBody
	public String addAuthoroty(Authority auth){
		authorityRepository.save(auth);
		return "success";
	}
	
	@RequestMapping(value="/getAuthority/{id}",method=RequestMethod.GET)
	public String getAuthoroty(@PathVariable("id")Long id,Model model){
		model.addAttribute("authority", authorityRepository.findOne(id));
		return "/user/authorityManager::#authorityForm";
	}
}
