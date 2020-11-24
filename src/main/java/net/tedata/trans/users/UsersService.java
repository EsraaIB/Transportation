package net.tedata.trans.users;

import java.util.ArrayList;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
	@Autowired
	private UsersRepository usersRepository;

	public List<Users> getAllUsers() {
		List<Users> users = new ArrayList<>();

		usersRepository.findAll().forEach(users::add);
		return users;

	}

	public Users getUserById(String id) {
		Users user = new Users();
		if (usersRepository.findById(Integer.parseInt(id)).isPresent()) {
			user = usersRepository.findById(Integer.parseInt(id)).get();
			return user;
		} else {
			return null;
		}
		// return usersRepository.findById(id);
	}

	public String validateUser(String id, String password) {
		Users selected = new Users();
		String result;
		selected = getUserById(id);

		if (selected != null) {
			//System.out.println("on");
			if (selected.getUserpassword().equals(password)) {
				if (selected.getMark() == 1) {
					result = "1";
				} else if (selected.getMark() == 2) {
					result = "2";
				} else {
					result = "type error";
				}

			} else {
				result = "Invalid Password";
			}

		} else {

			result = "User Not Found";
		}
		return result;
	}

	public void addUser(Users user) {

		usersRepository.save(user);
	}

	public void updateUser(int id, Users NUser) {

		usersRepository.save(NUser);

	}

	public void deleteUser(int id) {

		usersRepository.deleteById(id);

	}


}
