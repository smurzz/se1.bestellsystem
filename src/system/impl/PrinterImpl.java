package system.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import datamodel.Currency;
import datamodel.Order;
import datamodel.OrderItem;
import datamodel.Customer;
import system.Calculator;
import system.Formatter;
import system.Printer;

class PrinterImpl implements Printer {
	Calculator calc;
	Map<Customer, Integer> hm = new HashMap<>();
	Formatter formatter = createFormatter();

	OrderTableFormatterImpl otfmt = new OrderTableFormatterImpl(formatter, new Object[][] {
			// five column table with column specs: width and alignment ('[' left, ']' right)
			{ 12, '[' }, { 20, '[' }, { 36, '[' }, { 10, ']' }, { 10, ']' } });

	PrinterImpl(Calculator calculator) { // im Konstruktor injizierte Abhängigkeit
		this.calc = calculator; // zur Calculator Komponente
	}

	/**
	 * Print list of orders using OrderTableFormatter.
	 * 
	 * @param orders list of orders to print
	 * @return string buffer with representation of all orders in a table
	 */

	@Override
	public StringBuffer printOrders(Iterable<Order> orders) {

		List<Order> ordersList = new ArrayList<>();
		orders.forEach(ordersList::add);

		OrderTableFormatterImpl otfmt = new OrderTableFormatterImpl(formatter, new Object[][] {
				// five column table with column specs: width and alignment ('[' left, ']'
				// right)
				{ 12, '[' }, { 20, '[' }, { 36, '[' }, { 10, ']' }, { 10, ']' } });
		otfmt.liner("+-+-+-+-+-+") // print table header
				.hdr("||", "Order-Id", "Customer", "Ordered Items", "Order", "MwSt.")
				.hdr("||", "", "", "", "Value", "incl.").liner("+-+-+-+-+-+").liner("||");

		for (int i = 0; i < ordersList.size(); i++) {
			printOrder(ordersList.get(i)); // print first order in table
		}

		long totalAllOrders = calc.calculateValue(orders); // calculate value of all orders
		long totalVAT = calc.calculateIncludedVAT(orders); // calculate compound VAT (MwSt.) for all orders

		otfmt // finalize table with compound value and VAT (MwSt.) of all orders
				.lineTotal(totalAllOrders, totalVAT, Currency.EUR); // output table

		return formatter.getBuffer();
	}

	/**
	 * Print one order.
	 * 
	 * @param order to print
	 * @return string buffer with representation of order
	 */

	@Override
	public StringBuffer printOrder(Order order) {
		String idOrder = order.getId();

		// Count number of orders from one customer
		String ownerOrder;
		if (!hm.containsKey(order.getCustomer())) {
			hm.put(order.getCustomer(), 1);
		} else {
			int orderNum = hm.get(order.getCustomer()) + 1;
			hm.replace(order.getCustomer(), orderNum);
		}

		if (hm.get(order.getCustomer()) == 1) {
			ownerOrder = order.getCustomer().getFirstName() + "'s order";
		} else if (hm.get(order.getCustomer()) == 2) {
			ownerOrder = order.getCustomer().getFirstName() + "'s " + hm.get(order.getCustomer()) + "nd" + " order";
		} else if (hm.get(order.getCustomer()) == 3) {
			ownerOrder = order.getCustomer().getFirstName() + "'s " + hm.get(order.getCustomer()) + "rd" + " order";
		} else {
			ownerOrder = order.getCustomer().getFirstName() + "'s " + hm.get(order.getCustomer()) + "th" + " order";

		}
		// Get information about Articles, calculate taxes and sums
		String itemInfo = "";
		long totalPrice = 0;
		long totalTax = 0;
		OrderItem[] items = order.getItemsAsArray();
		for (int i = 0; i < items.length; i++) {
			StringBuffer pricePiece = formatter.fmtPrice(items[i].getArticle().getUnitPrice(),
					items[i].getArticle().getCurrency());
			totalPrice = items[i].getArticle().getUnitPrice() * items[i].getUnitsOrdered();
			double taxPrice = calc.calculateIncludedVAT(items[i].getArticle().getUnitPrice(),
					items[i].getArticle().getTax()) / 100.0;
			totalTax = (long) (Math.round(taxPrice * items[i].getUnitsOrdered()));
			if (items[i].getUnitsOrdered() == 1) {
				itemInfo = items[i].getUnitsOrdered() + " " + items[i].getArticle().getDescription() + " ("
						+ items[i].getArticle().getId() + ") " + pricePiece;
			} else {
				itemInfo = items[i].getUnitsOrdered() + " " + items[i].getArticle().getDescription() + " ("
						+ items[i].getArticle().getId() + ") " + items[i].getUnitsOrdered() + "x " + pricePiece;
			}
			if (i == 0) {
				otfmt.line(idOrder, ownerOrder, itemInfo, totalPrice, totalTax);
			} else if (i > 0) {
				otfmt.line("", "", itemInfo, totalPrice, totalTax);
			}
		}
		if (order.itemsCount() > 1) {
			otfmt.liner("+ + +-+-+-+").line("", "", "total:", calc.calculateValue(order),
					calc.calculateIncludedVAT(order));
		}
		otfmt.liner("| | | |=|=|").liner("| | | | | |");
		return formatter.getBuffer();
	}

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

	public void printOrdersToFile(Iterable<Order> orders, String filepath) throws IOException {
		File file = new File(filepath);
		file.getParentFile().mkdirs();
		FileWriter fw = new FileWriter (file);	
		fw.write(printOrders(orders).toString());
		fw.close();
//		throw new IOException( "not implemented." );
	}

	/**
	 * Create new format.
	 * 
	 * @return interpreter for printf-style format strings
	 */

	@Override
	public Formatter createFormatter() {
		return new FormatterImpl();
	}

}
