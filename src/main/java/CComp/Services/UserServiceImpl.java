package CComp.Services;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import CComp.Models.User;
import CComp.Repositories.UserRepo;

/**
 * The user Service creates new accounts and validates login credentials
 * @author me
 *
 */
@Service
@Transactional
public class UserServiceImpl implements UserService{


	@Autowired
	UserRepo repo;

	/**
	 * Adduser function added new accounts if the email is not already exisits.
	 */
	public User addUser(User newUser) {
		
		for(User u : findAllUsers()) {
			if (u.getEmail().equals(newUser.getEmail())) {
				return null;
			}
		}
		
		
		
		return repo.save(newUser);
	}

	/**
	 * finds all the users in the database
	 * @return all users
	 */
	public List<User> findAllUsers() {
		return repo.findAll();
	}

	public User findUserById(Long id) {
		return repo.getOne(id);
	}

	public User findUserByEmail(String email) {
		return repo.findUserByEmail(email);
	}
	
	/**
	 * Log ins user by testing the hash value
	 * @param User to be logged in
	 */
	public User loginUser(User u){
	
		return repo.findUserByEmailAndPassword(u.getEmail(), u.getPassword());
	}
	
}
