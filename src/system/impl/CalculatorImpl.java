package system.impl;

import java.util.ArrayList;

import datamodel.Order;
import datamodel.OrderItem;
import datamodel.TAX;
import system.Calculator;

class CalculatorImpl implements Calculator {
	
	/**
	 * Calculates the compound value of all orders
	 * 
	 * @param orders list of orders
	 * @return compound value of all orders
	 */

	@Override
	public long calculateValue(Iterable<Order> orders) {
		ArrayList<Order> newOrders = new ArrayList<>();
		orders.forEach(newOrders::add);
		long value = 0;
		for (int i = 0; i < newOrders.size(); i++) {
			value += calculateValue(newOrders.get(i));
		}
		return value;
	}

	/**
	 * Calculates the value of one order
	 * 
	 * @param order order for which the value is calculated
	 * @return value of the order
	 */
	
	@Override
	public long calculateValue(Order order) {
		long value = 0;
		ArrayList<OrderItem> items = new ArrayList<>();
		order.getItems().forEach(items::add);
		for (int i = 0; i < items.size(); i++) {
			value += items.get(i).getUnitsOrdered() * items.get(i).getArticle().getUnitPrice();
		}
		return value;
	}
	
	/**
	 * Calculates the compound VAT of all orders
	 * 
	 * @param orders list of orders
	 * @return compound VAT of all orders
	 */

	@Override
	public long calculateIncludedVAT(Iterable<Order> orders) {
		ArrayList<Order> newOrders = new ArrayList<>();
		orders.forEach(newOrders::add);
		long vat = 0;
		for (int i = 0; i < newOrders.size(); i++) {
			vat += calculateIncludedVAT(newOrders.get(i));
		}
		return vat;
	}
	
	/**
	 * Calculates the VAT of one order. VAT is based on the rate that applies to the
	 * articles of each line item.
	 * 
	 * @param order order for which the included VAT is calculated
	 * @return compound VAT of all ordered items
	 */

	@Override
	public long calculateIncludedVAT(Order order) {
		long vat = 0;
		ArrayList<OrderItem> item = new ArrayList<>();
		order.getItems().forEach(item::add);
		for (int i = 0; i < item.size(); i++) {
			double p = calculateIncludedVAT(item.get(i).getArticle().getUnitPrice(), item.get(i).getArticle().getTax()) / 100.0;
			long tax = (long) (Math.round(p * item.get(i).getUnitsOrdered()));
			vat += tax;
		}
		return vat;
	}
	
	/**
	 * private helper to calculate included VAT for a price based on a TAX rate (as
	 * defined in Enum TAX).
	 * 
	 * @param price of one piece of order
	 * @param taxRate of one piece of order
	 * @return long tax value
	 */
	
	@Override
	public long calculateIncludedVAT(long price, TAX taxRate) {
		return (long) ((price - (price / ((taxRate.rate() / 100.0) + 1.0))) * 100.0);
	}

}
