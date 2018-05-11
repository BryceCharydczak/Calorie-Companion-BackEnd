package CComp.Controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import CComp.Models.Food;
import CComp.Models.User;
import CComp.Services.FoodService;


/**
 * The food controller is used to access the food database. The routing is mapped to
 * /foods.
 * @author Philip Escobedo
 *
 */
@CrossOrigin
@RestController
@RequestMapping("/foods")
public class FoodController {
	
	@Autowired
	FoodService service;
	
	/**
	 * By POSTing an array of food to the /food endpoint, the array of food objects get added to the database.
	 * If you are sending just one item, put it in an array;
	 * @param newFoods 
	 * @return List of added foods
	 */
	@PostMapping(produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	public List<Food> addFoods(@Valid @RequestBody List<Food> newFoods) {
		
		List<Food> foods = new ArrayList<Food>();
		for (Food f : newFoods) {
			service.addFood(f);
			foods.add(f);
		}
		
		
		
		return foods;
	}
	
	/**
	 * By going to the /foods route this function will be called which will display the entire
	 * list of foods.
	 * @return List of all foods
	 */
	@GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
	public List<Food> findAllFoods() {
		return service.findAllFoods();
	}
	
	
	
	/**
	 * By using the endpoint /user/id spring will return every food item added by a certain user
	 * 
	 * @param id
	 * @return users by id
	 */
	@GetMapping(value="/user/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<Food> findUserById(@PathVariable("id") Long id) {
		return service.findFoodsByUserid(id);
	}
}
