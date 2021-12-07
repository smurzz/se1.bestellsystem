package datamodel;

import java.util.*;

/**
 * Class for entity type Customer. Customer is an individual (not a business)
 * who creates and owns orders in the system.
 * 
 * @since "0.1.0"
 * @version {@value package_info#Version}
 * @author Sofya
 */
public class Customer extends DataModel{

	/**
	 * id attribute, {@code < 0} invalid, can be set only once
	 */
	private long id = -1;

	/**
	 * none-surname name parts, never null, mapped to ""
	 */
	private String firstName = "";

	/**
	 * surname, never null, mapped to ""
	 */
	private String lastName = "";

	/**
	 * contact information, multiple contacts are possible
	 */
	private final ArrayList<String> contacts = new ArrayList<String>();

	/**
	 * Default constructor.
	 */
	public Customer() {
		// TODO implement here
	}

	/**
	 * Constructor with single-String name argument.
	 * 
	 * @param name single-String Customer name, e.g. "Eric Meyer"
	 */
	public Customer(String name) {
		// TODO implement here
		setName(name);
	}

	/**
	 * Id getter.
	 * 
	 * @return customer id, may be invalid {@code < 0} if unassigned
	 */
	public Long getId() {
		// TODO implement here
		return this.id;
	}

	/**
	 * Id setter. Id can only be set once, id is immutable after assignment.
	 * 
	 * @param id assignment if id is valid {@code >= 0} and id attribute is still
	 *           unassigned {@code < 0}
	 * @return chainable self-reference
	 */
	public Customer setId(long id) {
		// TODO implement here
		if (id > 0 && this.id == -1) {
			this.id = id;
		}
		return this;
	}

	/**
	 * Getter that returns single-String name from lastName and firstName attributes
	 * in format: {@code "lastName, firstName"} or {@code "lastName"} if
	 * {@code firstName} is empty.
	 * 
	 * @return single-String name
	 */
	public String getName() {
		// TODO implement here
		if (this.firstName.isEmpty()) {
			return this.lastName;
		}
		return this.lastName + ", " + this.firstName;
	}

	/**
	 * FirstName getter.
	 * 
	 * @return value of firstName attribute, never null, mapped to ""
	 */
	public String getFirstName() {
		// TODO implement here
		return this.firstName;
	}

	/**
	 * LastName getter.
	 * 
	 * @return value of lastName attribute, never null, mapped to ""
	 */
	public String getLastName() {
		// TODO implement here
		return this.lastName;
	}

	/**
	 * Setter that splits single-String name (e.g. "Eric Meyer") into first- and
	 * lastName parts and assigns parts to corresponding attributes.
	 * 
	 * @param name single-String name to split into first- and lastName parts
	 * @return chainable self-reference
	 */
	public Customer setName(String name) {
		// TODO implement here
		if (name != null) {
			splitName(name);
		}
		return this;
	}

	/**
	 * Name setter for first- and lastName attributes. Method maintains that both
	 * attributes are never null; null-arguments are ignored and don't change
	 * attributes.
	 * 
	 * @param first assigned to firstName attribute
	 * @param last  assigned to lastName attribute
	 * @return chainable self-reference
	 */
	public Customer setName(String first, String last) {
		// TODO implement here
		if (first != null && last == null) {
			this.firstName = first;
		} else if (first == null && last != null) {
			this.lastName = last;
		}
		return this;
	}

	/**
	 * Return number of contacts.
	 * 
	 * @return number of contactsContacts getter (as {@code String[]}).
	 */
	public int contactsCount() {
		// TODO implement here
		return contacts.size();
	}

	/**
	 * Contacts getter (as {@code String[]}).
	 * 
	 * @return contacts (as {@code String[]})
	 */
	public String[] getContacts() {
		// TODO implement here
		return contacts.toArray(new String[contacts.size()]);
	}

	/**
	 * Add new contact for Customer. Only valid contacts (not null or "") are
	 * stored. Duplicate entries are ignored.
	 * 
	 * @param contact valid contact (not null or "") updates attribute, duplicate
	 *                entries are ignored.
	 * @return chainable self-reference
	 */
	public Customer addContact(String contact) {
		// TODO implement here
		if (!contacts.contains(contact) && contact != null && contact != "") {
			contacts.add(contact);
		}
		return this;
	}

	/**
	 * Delete i-th contact with constraint: {@code i >= 0} and
	 * {@code i < contacts.size()}, otherwise method has no effect.
	 * 
	 * @param i index in contacts
	 */
	public void deleteContact(int i) {
		// TODO implement here
		if (i > 0 && i < contacts.size()) {
			contacts.remove(i);
		}
	}

	/**
	 * Delete all contacts.
	 */
	public void deleteAllContacts() {
		// TODO implement here
		contacts.removeAll(contacts);
	}

	/**
	 * Split single-String name into first- and last name.
	 * 
	 * @param name single-String name split into first- and last name
	 */
	private void splitName(String name) {
		// TODO implement here
		if (name.contains(",")) {
			List<String> names = Arrays.asList(name.split(", "));
			this.lastName = names.get(0);
			this.firstName = names.get(1);
		} else {
			List<String> names = Arrays.asList(name.split(" "));
			this.lastName = names.get(names.size() - 1);
			this.firstName = String.join(" ", names.subList(0, names.size() - 1));
		}
	}
}