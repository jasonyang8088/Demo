package demo.zss.controller.user;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.zss.entity.user.Authority;
import demo.zss.entity.user.Role;
import demo.zss.entity.user.User;
import demo.zss.repository.AuthorityRepository;
import demo.zss.repository.RoleRepository;
import demo.zss.repository.UserRepository;

@Controller
@RequestMapping("/user")
public class RoleManagerController {
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthorityRepository authorityRepository;

	@RequestMapping(value="/roleManager",method=RequestMethod.GET)
	public String toIndex(Model model){
		model.addAttribute("navmenu", 2);
		model.addAttribute("navleft", 2);
		model.addAttribute("role", new Role());
		PageRequest page=new PageRequest(0, 10);
		Page<Role> roles=roleRepository.findAll(page);
		model.addAttribute("roles", roles);
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();
		Set<Authority> authoritys=new HashSet<Authority>();
		if(username.equals("admin")){
			List<Authority> temp=authorityRepository.findByNameNot("admin");
			authoritys.addAll(temp);
			authoritys.remove("/admin");
		}else{
			User user = userRepository.findByUsername(username);
			authoritys= user.getAuthoritys();
		}
		model.addAttribute("authoritys", authoritys);
		model.addAttribute("totalPage", roles.getTotalPages());
		model.addAttribute("totalNum", roles.getTotalElements());
		return "/user/roleManager";
	}
	
	@RequestMapping(value="/addRole",method=RequestMethod.POST)
	@ResponseBody
	public String addRole(Role role){
		roleRepository.save(role);
		return "success";
	}
	
	@RequestMapping(value="/deleteRole",method=RequestMethod.GET)
	public String addRole(Long id){
		roleRepository.delete(id);
		return "redirect:/user/roleManager";
	}
	

	@RequestMapping(value="/getRole/{id}",method=RequestMethod.GET)
	public String getAuthoroty(@PathVariable("id")Long id,Model model){
		model.addAttribute("role", roleRepository.findOne(id));
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();
		Set<Authority> authoritys=new HashSet<Authority>();
		if(username.equals("admin")){
			List<Authority> temp=authorityRepository.findByNameNot("admin");
			authoritys.addAll(temp);
			authoritys.remove("/admin");
		}else{
			User user = userRepository.findByUsername(username);
			authoritys= user.getAuthoritys();
		}
		model.addAttribute("authoritys", authoritys);
		return "/user/roleManager::#roleForm";
	}
}
