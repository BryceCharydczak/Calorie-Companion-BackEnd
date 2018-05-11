package CComp.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import CComp.Models.Food;
import CComp.Repositories.FoodRepo;


/**
 * Food Service Implimentation used to find and add Items to the database
 * @author me
 *
 */
@Service
@Transactional
public class FoodServiceImpl implements FoodService {

	@Autowired
	FoodRepo repo;
	
	
	/**
	 * Saves food using id to the food table
	 * @Param food object to be added to table
	 * @return the object that was saved
	 */
	@Override
	public Food addFood(Food newFood) {
		
		return repo.save(newFood);
	}
	
	/**
	 * Returns all foods in the database regardless of user
	 */

	@Override
	public List<Food> findAllFoods() {
		
		return repo.findAll();
	}
	
	/**
	 * Finds the foods added by certain users
	 * @Param the user id
	 * @returns list of foods by user
	 */
	
	public List<Food> findFoodsByUserid(Long id){
		return repo.findFoodsByUserid(id);
	}

}
