package datamodel;

import java.util.*;
import java.util.stream.Stream;

/**
 * Class for entity type Order. Order represents a contractual relation between
 * a Customer and a selling business for purchasing (ordered) items.
 * 
 * @since "0.1.0"
 * @version {@value package_info#Version}
 * @author Sofya
 */
public class Order extends DataModel{

	/**
	 * id attribute, null: invalid, can be set only once
	 */
	private String id = null;

	/**
	 * reference to owning Customer, final, is never null
	 */
	private final Customer customer;

	/**
	 * date/time order was created
	 */
	private final Date creationDate = new Date();

	/**
	 * items ordered as part of this order
	 */
	private final List<OrderItem> items = new ArrayList<>();

	/**
	 * Constructor with customer owning the order as argument.
	 * 
	 * @param customer owner of order, customer who placed that order, throws
	 *                 IllegalArgumentException if customer is null
	 */
	public Order(Customer customer) {
		// TODO implement here
		if (customer != null) {
			this.customer = customer;
		} else {
			throw new IllegalArgumentException("Customer is null");
		}
	}

	/**
	 * Id getter. Id can only be set once since id are immutable after assignment.
	 * 
	 * @return order id, may be invalid (null) if id is still unassigned
	 */
	public String getId() {
		// TODO implement here
		return this.id;
	}

	/**
	 * Id setter. Id can only be set once since id is immutable after assignment.
	 * 
	 * @param id assign id, only valid id (not null or "") updates attribute
	 * @return chainable self-reference
	 */
	public Order setId(String id) {
		// TODO implement here
		if (this.id == null && id != "") {
			this.id = id;
		}
		return this;
	}

	/**
	 * Customer getter.
	 * 
	 * @return customer who owns the order, cannot be null
	 */
	public Customer getCustomer() {
		// TODO implement here
		return this.customer;
	}

	/**
	 * CreationDate getter, returns the time/date when the oder was created.
	 * 
	 * @return time/date when order was created
	 */
	public Date getCreationDate() {
		// TODO implement here
		return this.creationDate;
	}

	/**
	 * CreationDate setter.
	 * 
	 * @param dateAsLong dateAsLong time/date when order was created (provided as long value in milliseconds since 01/01/1970)
	 * @return chainable self-reference
	 */
	public Order setCreationDate(long dateAsLong) {
		// TODO implement here
		dateAsLong = System.currentTimeMillis();
		return this;
	}

	/**
	 * Return number of items that are part of the order.
	 * 
	 * @return number of ordered items
	 */
	public int itemsCount() {
		// TODO implement here
		return items.size();
	}

	/**
	 * Ordered items getter (as {@code Iterable<OrderItem>}).
	 * 
	 * @return ordered items as {@code Iterable<OrderItem>}
	 */
	public Iterable<OrderItem> getItems() {
		// TODO implement here
		return items;
	}

	/**
	 * Ordered items getter (as {@code OrderItem[]}).
	 * 
	 * @return ordered items as {@code OrderItem[]}
	 */
	public OrderItem[] getItemsAsArray() {
		// TODO implement here
		return items.toArray(new OrderItem[items.size()]);
	}

	/**
	 * Ordered items getter (as {@code Stream<OrderItem>}).
	 * 
	 * @return ordered items as {@code Stream<OrderItem>}
	 */
	public Stream<OrderItem> getItemsAsStream() {
		// TODO implement here
		return items.stream();
	}

	/**
	 * Create new item and add to order.
	 * 
	 * @param article ordered article, throws IllegalArgumentException if article is null
	 * @param units   number of articles ordered, throws IllegalArgumentException if not a positive number
	 * @return chainable self-reference
	 */
	public Order addItem(Article article, int units) {
		// TODO implement here
		if (article != null && units > 0) {
			items.add(new OrderItem(article, units));
		} else {
			throw new IllegalArgumentException("Article is null or units are negative");
		}
		return this;
	}

	/**
	 * Delete i-th item with constraint: {@code i >= 0 && i < items.size()},
	 * otherwise the method has no effect.
	 * 
	 * @param i index of item to delete, only valid index deletes item
	 */
	public void deleteItem(int i) {
		// TODO implement here
		if (i >= 0 && i < items.size()) {
			items.remove(i);
		}

	}

	/**
	 * Delete all ordered items.
	 */
	public void deleteAllItems() {
		// TODO implement here
		items.removeAll(items);
	}

}