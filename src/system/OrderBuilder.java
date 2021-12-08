package system;

import datamodel.Order;

/**
 * Public Interface of OrderBuilder creates an OrderRepository
 * 
 * @since "0.1.0"
 * @version {@value package_info#Version}
 * @author Sofya
 */

public interface OrderBuilder {
	/**
	 * Validate and save order to OrderRepository, if order is accepted.
	 * 
	 * @param order order to accept.
	 * @return true if order was validated and saved to OrderRepcositry.
	 */
	
	public boolean accept( Order order );
	
	/**
	 * Build orders in PrderRepository.
	 * 
	 * @return chainable self-reference.
	 */
	public OrderBuilder build();
}
