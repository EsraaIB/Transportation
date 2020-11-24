package net.tedata.trans.buses;

import java.util.*;

import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusesService {
	@Autowired
	private BusesRepository busesRepository;

	public List<Buses> getAllBuses() {
		List<Buses> buses = new ArrayList<>();

		busesRepository.findAll().forEach(buses::add);
		return buses;

	}

	public Buses getBusById(int id) {

		Buses bus = new Buses();
		if (busesRepository.findById(id).isPresent()) {
			bus = busesRepository.findById(id).get();
			return bus;
		} else {
			return null;
		}
	}

	public void addBus(Buses bus) {

		busesRepository.save(bus);
	}

	public void updateBus(int id, Buses NBus) {

		busesRepository.save(NBus);

	}

	public void deleteBus(int id) {

		busesRepository.deleteById(id);
		

	}
	public Set <String> removeDuplicate()
	{
		Set <String> set = new HashSet<String>();
		
		List<Buses> buses=new ArrayList<>();
		buses=getAllBuses();
		for(int i=0 ; i<buses.size(); i++)
		  set.add(buses.get(i).getBusTime());
		
         return set;
	}

}
