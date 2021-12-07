package system;

import java.util.Optional;

import datamodel.Article;
import datamodel.Customer;
import datamodel.Order;


/**
 * Public interface of data repositories that store objects (entities)
 * of data model classes.
 *
 * @since "0.1.0"
 * @version {@value package_info#Version}
 * @author Sofya
 *
 */

public interface DataRepository {

	/**
	 * Public interface of the Customer repository.
	 *
	 */
	interface CustomerRepository {
		/**
		 * Find a customer by id
		 * 
		 * @param id of customer
		 * @return optional object with customer value
		 */
		Optional<Customer> findById( long id );
		
		/**
		 * Find all customers as a iterable list
		 * 
		 * @return iterable list of all customers
		 */
		Iterable<Customer> findAll();
		
		/**
		 * Count all customers
		 * 
		 * @return number of customers
		 */
		long count();
		
		/**
		 * Enter a new customer
		 * 
		 * @param entity of a customer
		 * @return new customer
		 */
		Customer save( Customer entity );
	}

	/**
	 * Public interface of the Article repository.
	 *
	 */

	interface ArticleRepository {
		
		/**
		 * Find an article by id
		 * 
		 * @param id of article
		 * @return optional object with article value
		 */
		Optional<Article> findById( String id );
		
		/**
		 * Find all articles as a iterable list
		 * 
		 * @return iterable list of all articles
		 */
		Iterable<Article> findAll();
		
		/**
		 * Count all articles
		 * 
		 * @return number of articles
		 */
		long count();
		
		/**
		 * Enter a new article
		 * 
		 * @param entity of a article
		 * @return new article
		 */
		Article save( Article entity );
	}

	/**
	 * Public interface of the Order repository.
	 *
	 */
	interface OrderRepository {
		
		/**
		 * Find an order by id
		 * 
		 * @param id of order
		 * @return optional object with order value
		 */
		Optional<Order> findById( String id );
		
		/**
		 * Find all orders as a iterable list
		 * 
		 * @return iterable list of all orders
		 */
		Iterable<Order> findAll();
		
		/**
		 * Count all orders
		 * 
		 * @return number of orders
		 */
		long count();
		
		/**
		 * Enter a new order
		 * 
		 * @param entity of a order
		 * @return new order
		 */
		Order save( Order entity );
	}

}

//interface Repository<T> {
//		
//		/**
//		 * Find an order by id
//		 * 
//		 * @param id of order
//		 * @return optional object with order value
//		 */
//		Optional<T> findById( long id );
//		
//		Optional<T> findById( String id );
//		
//		/**
//		 * Find all orders as a iterable list
//		 * 
//		 * @return iterable list of all orders
//		 */
//		Iterable<T> findAll();
//		
//		/**
//		 * Count all orders
//		 * 
//		 * @return number of orders
//		 */
//		long count();
//		
//		/**
//		 * Enter a new order
//		 * 
//		 * @param entity of a order
//		 * @return new order
//		 */
//		T save( T entity );
//	}

