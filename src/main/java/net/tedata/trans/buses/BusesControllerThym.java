package net.tedata.trans.buses;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.management.Query;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.*;

import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.tedata.trans.register.Register;
import net.tedata.trans.selectmodel.SelectModel;


@Controller
public class BusesControllerThym {

	@Autowired
	private BusesService busesService;

	@RequestMapping("/admin")
	public String getAllBuses(Model model) {
		List<Buses> busesdata = busesService.getAllBuses();
		model.addAttribute("buses", busesdata);
		BusesList busData = new BusesList();
		busData.setBusesList(busesdata);
		model.addAttribute("listbuses", busData);
		BusesList deleteList = new BusesList();
		model.addAttribute("listdelete", deleteList);

		return "admin";

	}

	@RequestMapping("/employee")
	public String getBuses(Model model) {
		List<Buses> busesdata = busesService.getAllBuses();
		model.addAttribute("buses", busesdata);

		return "employee";

	}

	@RequestMapping(method = RequestMethod.GET, value = "/addbus")
	public String addBusView(Model model)

	{
		Buses busData = new Buses();
		model.addAttribute("busdata", busData);

		return "addbus";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/addbus")
	public void addBus(Model model, @ModelAttribute("busdata") Buses busData)

	{
		Buses test = new Buses();

		test = busesService.getBusById(busData.getBusNo());
		if (test != null) {

			model.addAttribute("exception", "The Bus Number Is Already Added! ");

		} else {
			if (busData.getBusNo() > 0) {
				if(busData.getCapacity()>0){
				if (busData.getBusFrom().equals("Dokki"))
					busData.setBusTo("Smart");
				else
					busData.setBusTo("Dokki");

				busesService.addBus(busData);
				model.addAttribute("addresult", "Added Successfully!");}
				else
				{
					model.addAttribute("exception", "The Bus Capacity Can't be ZERO OR Negative Number! ");
					
				}
			} else {

				model.addAttribute("exception", "The Bus Number Can't be ZERO OR Negative Number! ");

			}

		}
	}

	@RequestMapping(method = RequestMethod.GET, value = { "/updatebus" })
	public String update(HttpServletRequest request, Model model) {
		String busNo = request.getParameter("id");
		int idint = Integer.parseInt(busNo);
		// System.out.println("el id"+busNo);
		Buses r = busesService.getBusById(idint);
		// System.out.println("BusName"+r.getBusName());
		model.addAttribute("gobus", r);
		return "updatebus";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/updatebus")
	public void updateBus(Model model, @ModelAttribute("gobus") Buses busData)

	{
		if (busData.getBusFrom().equals("Dokki"))
			busData.setBusTo("Smart");
		else
			busData.setBusTo("Dokki");

		busesService.updateBus(busData.getBusNo(), busData);
		model.addAttribute("updateresult", "Updated Successfully!");
	}

	@RequestMapping(method = RequestMethod.GET, value = { "/deletebus" })
	public String delete(HttpServletRequest request, Model model) {
		String busNo = request.getParameter("id");
		int idint = Integer.parseInt(busNo);
		
		 busesService.deleteBus(idint);
	
		 
		 return "redirect:/admin";
	}
	@RequestMapping(method=RequestMethod.GET,value={"/members"})
	public String selectByid(Model model)
	{
		List<Buses> buses=new ArrayList<>();
		Set <String> set = new HashSet<String>();
		SelectModel selectdata=new SelectModel();
		set=busesService.removeDuplicate();

		buses=busesService.getAllBuses();
	   model.addAttribute("listBuses", buses);
	   model.addAttribute("listBusesTime",set);
	   model.addAttribute("selectmodel",selectdata);
		return"members";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/members")
	public String selectForSearch(Model model, @ModelAttribute("selectmodel") SelectModel selected)
	{  Configuration con = new Configuration().configure().addAnnotatedClass(Register.class);
	   SessionFactory sf = con.buildSessionFactory();
	   Session session = sf.openSession();
	   Transaction tx= session.beginTransaction();
		session.beginTransaction();

		String qu="SELECT userId FROM Register WHERE 1 = 1 ";
		if(!selected.getSelectedBusNo().equals("-1"))
			qu+=("AND busNo ="+selected.getSelectedBusNo()+" ");
		if(!selected.getSelectedBranch().equals("-1"))
			qu+=("AND branch = \'"+selected.getSelectedBranch()+"\' ");
		if(!selected.getSelectedBusTime().equals("-1"))
			 qu+=("AND time = \'"+selected.getSelectedBusTime()+"\' ");
		qu+=";";
 
        Query query=(Query) session.createQuery(qu);
        List users=((org.hibernate.query.Query) query).list();
		
		
		
		tx.commit();
		session.close();
		System.out.println("kjadvbhjval"+users.size());
         
		return "members";
		
	}
	
	
	}