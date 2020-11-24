package net.tedata.trans.register;


import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RegisterService {
	@Autowired
	private RegisterRepository registerRepository;
	public List<Register> getAllRegistered()
	{
		List<Register> reg=new ArrayList<>();
		registerRepository.findAll().forEach(reg::add);
		
		return reg;
	}
	
	public void makeRegister(Register register)
	{
		
		registerRepository.save(register);
		
	}
	
	public List<Register> getByUserId(String userid)
	{
		List<Register> reg=new ArrayList<>();
		List<Register> last=new ArrayList<>();
		reg=getAllRegistered();
		for(int i=0 ; i<reg.size(); i++)
		{
			if(reg.get(i).getMykey().getuId()==Integer.parseInt(userid))
				last.add(reg.get(i));
			
		}
		return last;
		
		
	}
	public void deleteByBusNo(String userid,int busno)
	{
		
		List<Register> temp=new ArrayList<>();
		temp=getByUserId(userid);
		for(int i=0 ; i<temp.size(); i++)
		{
			
			if(temp.get(i).getBusNo()== busno)
				registerRepository.delete(temp.get(i));
			
		}
		
	}

}
