package hello;

import hello.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequestMapping(path="/demo")
public class MainController {
	@Autowired
	private UserService userService;
	
	@GetMapping(path="/add") // Map ONLY GET Requests
	public @ResponseBody String addNewUser (@RequestParam String name
			, @RequestParam String email) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request
		
		User n = new User();
		n.setName(name);
		n.setEmail(email);
		userService.save(n);
		return "Saved";
	}
	
	@RequestMapping(path = "/addPost",method = { RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody User addUserPost(@RequestBody User user) {
		userService.save(user);
		return user;
	}
	
	@GetMapping(path="/all")
	public @ResponseBody Iterable<User> getAllUsers() {
		return userService.getAll();
	}

	@RequestMapping({ "/user", "/me" })
	public Map<String, String> user(Principal principal) {
		Map<String, String> map = new LinkedHashMap<>();
		map.put("name", principal.getName());
		return map;
	}
}
