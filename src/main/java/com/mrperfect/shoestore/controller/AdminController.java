package com.mrperfect.shoestore.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mrperfect.shoestore.controller.requestPojo.ApiResponse;
import com.mrperfect.shoestore.exception.NotFound;
import com.mrperfect.shoestore.model.CheckoutCart;
import com.mrperfect.shoestore.model.Products;
import com.mrperfect.shoestore.model.User;
import com.mrperfect.shoestore.model.repository.CheckoutRepo;
import com.mrperfect.shoestore.model.repository.ProductRepo;
import com.mrperfect.shoestore.model.repository.UserRepository;
import com.mrperfect.shoestore.service.CartService.CartService;

@RestController
@RequestMapping("admin/")
public class AdminController {
	@Autowired
	ProductRepo productRepo;
	@Autowired
	UserRepository userRepo;
	@Autowired
	CheckoutRepo checkoutRepo;
	@Autowired
	CartService cartService;

	// GET all Shoe
	@GetMapping("/Shoes")
	public List<Products> getAllProduct() {
		return this.productRepo.findAll();

	}

	// create a Shoe
	@PostMapping("/addShoes")
	public Products addShoe(@RequestBody Products shoe) {
		return this.productRepo.save(shoe);
	}

	// update a Shoe
	@PutMapping("/Shoes/{id}")
	public Products updateShoe(@RequestBody Products shoe, @PathVariable(value = "id") long ShoeId) {
		// 1. find a record / Shoe
		Products fetchedShoe = this.productRepo.findById(ShoeId)
				.orElseThrow(() -> new NotFound("Shoe Not found wit id " + ShoeId));
		// 2 . set new values
		fetchedShoe.setName(shoe.getName());
		fetchedShoe.setPrice(shoe.getPrice());
		fetchedShoe.setCategory_id(shoe.getCategory_id());

		// 3.save a Shoe
		return this.productRepo.save(fetchedShoe);
	}

	// remove a Shoe
	@DeleteMapping("/Shoes/{id}")
	public void deleteShoe(@PathVariable(value = "id") long ShoeId) {
		// 1. find a record / Shoe
		Products fetchedShoe = this.productRepo.findById(ShoeId)
				.orElseThrow(() -> new NotFound("Shoe Not found wit id " + ShoeId));
		this.productRepo.delete(fetchedShoe);

	}

	// GET all User
	@GetMapping("/Users")
	public List<User> getAllUser() {
		return this.userRepo.findAll();

	}

	// Search User by Id
	@GetMapping("/UserById/{id}")
	public User getUserById(@PathVariable(value = "id") long userId) {
		return this.userRepo.findById(userId).orElseThrow(() -> new NotFound("Shoe Not found wit id " + userId));
	}

	// GET User by Name
	@GetMapping("/Users/{name}")
	public List<User> getUserContainingName(@RequestParam(value = "name") String name) {
		return this.userRepo.findByNameContaining(name);
	}
	
	// GET all CheckOut Order
		@GetMapping("/PurchaseReport")
		public List<CheckoutCart> getAllCheckout() {
			return this.checkoutRepo.findAll();
	
	
}
//		// GET Order Report By Date
//		@GetMapping("/PurchaseReport/{id}")
//		public List<CheckoutCart> getByCategoryId(@RequestParam(value = "id") String category) {
//			return (List<CheckoutCart>) this.checkoutRepo.findByCategory(category).orElseThrow(() -> new NotFound("Shoe Not found wit id " + category));
//		}
		
//		@RequestMapping("getOrdersByCategoryId")
//		public ResponseEntity<?> getOrdersByCategoryId(@RequestBody HashMap<String,String> ordersRequest) {
//		try {
//			String keys[] = {"categoryId"};	
//			List<CheckoutCart> obj = cartService.getAllCheckoutByCategoryId(ordersRequest.get("categoryId"));
//			return ResponseEntity.ok(obj);
//		}catch(Exception e) {
//			e.printStackTrace();
//			return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), ""));
//		}
//		
//	}
		
		
		//Search Filter By Category
	
		@PostMapping("/report/{filterByCategory}")
		public List <CheckoutCart> purchaseReportByCategoy(@PathVariable(value="filterByCategory") String filterBy, @RequestBody CheckoutCart checkoutCart) {
		if(filterBy != null && filterBy.equals("category")) {
		return checkoutRepo.findByCategory(checkoutCart.getCategory());
		}else return null;
		}
		
		@PostMapping("/date/{filterByDate}")
		public List <CheckoutCart> purchaseReport(@PathVariable(value="filterByDate") String filterBy, @RequestBody CheckoutCart checkoutCart) {
		if(filterBy != null && filterBy.equals("purchaseDate")) {
			System.out.println(checkoutCart.getPurchaseDate().toString());
		return checkoutRepo.findBypurchaseDate(checkoutCart.getPurchaseDate());
		}else return null;
		}



}
