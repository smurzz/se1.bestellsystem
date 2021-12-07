package system;

import datamodel.Order;

/**
 * Public interface of print Orders in specified format
 * 
 * @since "0.1.0"
 * @version {@value package_info#Version}
 * @author Sofya
 * 
 */

public interface Printer {
	
	/**
	 * Print list of orders using OrderTableFormatter.
	 * 
	 * @param orders list of orders to print
	 * @return string buffer with representation of all orders in a table
	 */
	
	public StringBuffer printOrders(Iterable<Order> orders);
	
	/**
	 * Print one order.
	 * 
	 * @param order to print
	 * @return string buffer with representation of order
	 */
	
	public StringBuffer printOrder(Order order);

	/**
	 * Create new format.
	 * 
	 * @return interpreter for printf-style format strings
	 */
	
	public Formatter createFormatter();

}
