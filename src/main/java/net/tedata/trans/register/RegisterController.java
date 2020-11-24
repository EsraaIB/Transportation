package net.tedata.trans.register;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.tedata.trans.buses.Buses;
import net.tedata.trans.buses.BusesList;
import net.tedata.trans.users.Users;
import net.tedata.trans.users.UsersService;
import net.tedata.trans.buses.BusesService;
import net.tedata.trans.register.Register;
import net.tedata.trans.register.RegisterService;
import net.tedata.trans.register.Key;

@Controller
public class RegisterController {
	@Autowired
	private RegisterService registerService;
	@Autowired
	private BusesService busesService;
	@Autowired
	private UsersService usersService;
	public static String Saveuserid;
	@RequestMapping(method = RequestMethod.GET, value = { "/register" })
	public String register(HttpServletRequest request, Model model) {
		String registerresult="";
		String busNo = request.getParameter("id");
		Register registerdata=new Register();
		Users user=new Users();
		Key key=new Key();
		int idint = Integer.parseInt(busNo);
		Buses r = busesService.getBusById(idint);
		if(r.getCapacity()==r.getNoOfEmployee())
		{
			registerresult="The Bus Is Full You Can't Register!!";
			model.addAttribute("Rexception", registerresult);
			return "redirect:/employee";

		}
		else
		{
		
		 List<Register> ex=new ArrayList<>();
		ex=registerService.getByUserId(Saveuserid);
	
		for(int i=0 ; i<ex.size(); i++){
			if(r.getBusTime().equals(ex.get(i).getMykey().getTime()))
				{registerresult="You Already Registered in a Bus at Same Time!";
				model.addAttribute("Rexception", registerresult);
				return "redirect:/employee";
				  
				}
		
			
		}
		registerdata.setBusNo(idint);
		user=usersService.getUserById(Saveuserid);
		registerdata.setBranch(user.getBranch());
		key.setuId(Integer.parseInt(Saveuserid));
		key.setTime(r.getBusTime());
		registerdata.setMykey(key);
		registerService.makeRegister(registerdata);
		r.setNoOfEmployee(r.getNoOfEmployee()+1);
		busesService.updateBus(r.getBusNo(), r);
		model.addAttribute("registerresult", "Registered Successfully!");

		return "redirect:/employee";
			
	
		}
		
	
	}
	@RequestMapping(value={"/emprequests"})
	public String getAllMyRequests(Model model) {
		List<Register> registered = registerService.getByUserId(Saveuserid);
		List<Buses> regbus=new ArrayList<>();
        for(int i=0 ; i<registered.size(); i++)
        {
        	
        	regbus.add(busesService.getBusById(registered.get(i).getBusNo()));
        	
        }
        model.addAttribute("registerlist", regbus);
		return "emprequests";

	}
	@RequestMapping(value={"/adminrequests"})
	public String getAllEmpRequests(Model model) {
		List<Register> registered = registerService.getAllRegistered();

      
        model.addAttribute("requestslist", registered);
		return "adminrequests";

	}
	@RequestMapping(method = RequestMethod.GET, value = { "/cancel" })
	public String delete(HttpServletRequest request, Model model) {
		String busId = request.getParameter("id");
      
      registerService.deleteByBusNo(Saveuserid,Integer.parseInt(busId));
		Buses r = busesService.getBusById(Integer.parseInt(busId));

         r.setNoOfEmployee(r.getNoOfEmployee()-1);
	     busesService.updateBus(r.getBusNo(), r);
		return "redirect:/emprequests";

	}
	@RequestMapping(method = RequestMethod.GET, value = { "/decline" })
	public String declineRequest(HttpServletRequest request, Model model) {
		
		return "redirect:/adminrequests";

	}
	

}
