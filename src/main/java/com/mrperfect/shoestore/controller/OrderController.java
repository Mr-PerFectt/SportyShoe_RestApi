package com.mrperfect.shoestore.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mrperfect.shoestore.JWTConfiguration.ShoppingConfiguration;
import com.mrperfect.shoestore.controller.requestPojo.ApiResponse;
import com.mrperfect.shoestore.model.AddtoCart;
import com.mrperfect.shoestore.model.CheckoutCart;
import com.mrperfect.shoestore.service.CartService.CartService;
import com.mrperfect.shoestore.service.ProductService.ProductServices;



@RestController
@RequestMapping("api/order")
public class OrderController {
	@Autowired
	CartService cartService;
	ProductServices proService;
	@RequestMapping("checkout_order")
  	public ResponseEntity<?> checkout_order(@RequestBody HashMap<String,String> addCartRequest) {
		try {
			String keys[] = {"userId","total_price","pay_type","deliveryAddress"};
			if(ShoppingConfiguration.validationWithHashMap(keys, addCartRequest)) {
				
				
			}
			long user_Id = Long.parseLong(addCartRequest.get("userId"));
			double total_amt = Double.parseDouble(addCartRequest.get("total_price"));
			if(cartService.checkTotalAmountAgainstCart(total_amt,user_Id)) {
				List<AddtoCart> cartItems = cartService.getCartByUserId(user_Id);
				List<CheckoutCart> tmp = new ArrayList<CheckoutCart>();
				for(AddtoCart addCart : cartItems) {
					String orderId = ""+getOrderId();
					CheckoutCart cart = new CheckoutCart();
					cart.setPayment_type(addCartRequest.get("pay_type"));
					cart.setPrice(total_amt);
					cart.setUser_id(user_Id);
					cart.setOrder_id(orderId);
					cart.setCategory(addCart.getProduct().getCategory_id());
					cart.setProduct(addCart.getProduct());
					cart.setQty(addCart.getQty());
					cart.setDelivery_address(addCartRequest.get("deliveryAddress"));
					tmp.add(cart);
				}
				cartService.saveProductsForCheckout(tmp);
				return ResponseEntity.ok(new ApiResponse("Order placed successfully", ""));
			}else {
				throw new Exception("Total amount is mismatch");
			}
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), ""));
		}
	}
	public int getOrderId() {
	    Random r = new Random( System.currentTimeMillis() );
	    return 10000 + r.nextInt(20000);
	}
	@RequestMapping("getOrdersByUserId")
		public ResponseEntity<?> getOrdersByUserId(@RequestBody HashMap<String,String> ordersRequest) {
		try {
			String keys[] = {"userId"};	
			List<CheckoutCart> obj = cartService.getAllCheckoutByUserId(Long.parseLong(ordersRequest.get("userId")));
			return ResponseEntity.ok(obj);
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), ""));
		}
		
	}
}
