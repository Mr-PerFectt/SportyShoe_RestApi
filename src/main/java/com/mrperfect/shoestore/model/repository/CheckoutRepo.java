package com.mrperfect.shoestore.model.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mrperfect.shoestore.model.CheckoutCart;




@Repository
public interface CheckoutRepo  extends JpaRepository<CheckoutCart, Long> {
	@Query("Select checkCart  FROM CheckoutCart checkCart WHERE checkCart.user_id=:user_id")
	List<CheckoutCart> getByuserId(@Param("user_id")Long user_id);
	
	List<CheckoutCart> findByCategory(String category);

	List<CheckoutCart> findBypurchaseDate(Date date );
}
