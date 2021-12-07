package system;

import java.io.IOException;

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
	 * Print orders as table to a file.
	 *
	 * Conditions: - creates new file or overwrites an existing file. - not existing
	 * parts of the path are created, throws IOException if this is not possible.
	 *
	 * @param orders   list of orders to print.
	 * @param filepath path and name of the output file.
	 * @throws IOException for errors.
	 */
	void printOrdersToFile(Iterable<Order> orders, String filepath) throws IOException;

	/**
	 * Create new format.
	 * 
	 * @return interpreter for printf-style format strings
	 */

	public Formatter createFormatter();

}
