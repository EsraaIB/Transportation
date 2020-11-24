package net.tedata.trans.users;


import java.util.List;
import javax.servlet.http.HttpServletRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.tedata.trans.logindata.LoginData;
import net.tedata.trans.register.RegisterController;



@Controller
public class UsersControllerThym {
	@Autowired
	private UsersService usersService;
	private  RegisterController x=new RegisterController();



	@RequestMapping(method = RequestMethod.GET, value = "/login")
	public String Loginfun(Model model)

	{
		LoginData loginData = new LoginData();
		model.addAttribute("logindata", loginData);
		return "index";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/login")
	public String Login(Model model, @ModelAttribute("logindata") LoginData logindata)

	{
		

		if (!logindata.getuId().matches("[0-9]+")) {
			LoginData loginData = new LoginData();
			loginData.setResult("Invalid User ID");
			model.addAttribute("logindata", loginData);
			return "index";
		} else {
			String result = usersService.validateUser(logindata.getuId(), logindata.getPassword());
			if (result.equals("1")) {
				model.addAttribute("userid", logindata.getuId());

				return "redirect:/admin";

			} else if (result.equals("2")) {
				model.addAttribute("userid", logindata.getuId());
                 x.Saveuserid=logindata.getuId();
				return "redirect:/employee";

			} else {
				LoginData loginData = new LoginData();
				loginData.setResult(result);
				model.addAttribute("logindata", loginData);
				return "index";
			}
		}

	}

	@RequestMapping("/members")
	public String Members(Model model)

	{
		List<Users> usersdata = usersService.getAllUsers();
		model.addAttribute("users", usersdata);

		return "Members";
	}

	@RequestMapping(method = RequestMethod.GET, value = { "/updatemember" })
	public String updateMember(HttpServletRequest request, Model model) {
		String userId = request.getParameter("id");
		Users user = new Users();
		user = usersService.getUserById(userId);
		model.addAttribute("myinfo", user);

		return "updatemember";

	}
	
	



}
