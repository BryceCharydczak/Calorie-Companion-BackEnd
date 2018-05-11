package CComp.Controller;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import CComp.Models.User;
import CComp.Services.UserService;


/** Using Spring MVC the user controller routes requests using the /users domain */
@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {
	
	/** getEncoder is a helper function to encrypt passwords */
	@Bean
	PasswordEncoder getEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	@Autowired
	UserService service;
	
	/** POST to /users a user json object to add new user. Duplicate user detection is taken care of in the service
	 * layer. When added the password is stored as a hash a value as a security measure.
	 * @Param Json User object
	 * @return User Object of the new user  */
	@PostMapping(produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	public User addUser(@Valid @RequestBody User user) {

		/* Bycrpt test */
		String pw_hash = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()); 
		user.setPassword(pw_hash);
		
		return service.addUser(user);
	}
	
	/** 
	 * The default route for /users. Hitting this endpoint will return the entire list of users
	 * in the database,
	 * @return Array of all users in User objects
	 */
	@GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
	public List<User> findAllUsers() {
		return service.findAllUsers();
	}

	/**
	 * Using the endpoint /users/{id} will return the User object of the matching id
	 * @param id of the user
	 * @return User object of the matching user
	 */
	@GetMapping(value="/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public User findUserById(@PathVariable("id") Long id) {
		return service.findUserById(id);
	}
	
	/**
	 * The endpoint /users/login will return a user object if the password matches the emailaddress. 
	 * This function decrypts and sent pass and compares it to the hash stored in the database. This 
	 * function will return null if validation does not match.
	 * @param user
	 * @return
	 */
	@PostMapping(value="/login", produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	public User loginUser(@RequestBody User user) {
		

		
		User cUser = service.findUserByEmail(user.getEmail());
		
		if (BCrypt.checkpw(user.getPassword(), cUser.getPassword()))
		    return cUser;
		else
		    return null;
		
		//return service.loginUser(user);
	}
	

}
