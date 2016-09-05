package demo.zss.controller.user;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.zss.entity.user.Role;
import demo.zss.entity.user.User;
import demo.zss.repository.RoleRepository;
import demo.zss.repository.UserRepository;

@Controller
@RequestMapping("/user")
public class UserListManagerController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@RequestMapping(value = "/userListManager", method = RequestMethod.GET)
	public String toIndex(Model model) {
		model.addAttribute("navmenu", 2);
		model.addAttribute("navleft", 1);
		PageRequest page = new PageRequest(0, 10);
		Page<User> users = userRepository.findAll(page);
		model.addAttribute("users", users);
		model.addAttribute("totalPage", users.getTotalPages());
		model.addAttribute("totalNum", users.getTotalElements());
		model.addAttribute("user", new User());

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();
		List<Role> roles = new ArrayList<Role>();
		if (username.equals("admin")) {
			roles = roleRepository.findByNameNot("admin");
			roles.remove("/admin");
		} else {
			User user = userRepository.findByUsername(username);
			roles = user.getRoles();
		}
		model.addAttribute("roles", roles);
		return "/user/userListManager";
	}

	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	@ResponseBody
	public String addUser(User user) {
		if (user.getId() == null) {
			user.setPassword("123456");
		}
		user.setStatus(1);
		userRepository.save(user);
		return "success";
	}

	@RequestMapping(value = "/changeTableDataForUserListManager")
	public String changeTableData(Integer page, Model model) {
		PageRequest pageRequest = new PageRequest(page - 1, 10);
		Page<User> users = userRepository.findAll(pageRequest);
		model.addAttribute("totalPage", users.getTotalPages());
		model.addAttribute("users", users.getContent());
		model.addAttribute("totalNum", users.getTotalElements());
		return "/user/userListManager::#tableData";
	}

	@RequestMapping(value = "/getUser/{id}", method = RequestMethod.GET)
	public String getUser(@PathVariable("id") Long id, Model model) {
		model.addAttribute("user", userRepository.findOne(id));
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();
		List<Role> roles = new ArrayList<Role>();
		if (username.equals("admin")) {
			roles = roleRepository.findByNameNot("admin");
			roles.remove("/admin");
		} else {
			User user = userRepository.findByUsername(username);
			roles = user.getRoles();
		}
		model.addAttribute("roles", roles);
		return "/user/userListManager::#userForm";
	}
	
	@RequestMapping(value="/deleteUser",method=RequestMethod.GET)
	public String deleteUser(@RequestParam Long id){
		System.out.println(id);
		userRepository.deleteUser(id);
		return "redirect:/user/userListManager";
	}
	
	@RequestMapping(value = "/newUser", method = RequestMethod.GET)
	public String newUser(Model model) {
		model.addAttribute("user", new User());
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();
		List<Role> roles = new ArrayList<Role>();
		if (username.equals("admin")) {
			roles = roleRepository.findByNameNot("admin");
			roles.remove("/admin");
		} else {
			User user = userRepository.findByUsername(username);
			roles = user.getRoles();
		}
		model.addAttribute("roles", roles);
		return "/user/userListManager::#userForm";
	}
}
