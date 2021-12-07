package datamodel;

/**
 * Class for entity type Article. An article is an entity that can be referenced
 * in order items.
 * 
 * @since "0.1.0"
 * @version {@value package_info#Version}
 * @author Sofya
 */
public class Article extends DataModel {

	/**
	 * id attribute; null and "" are invalid; can be set only once
	 */
	private String id = null;

	/**
	 * article description, never null, mapped to ""
	 */
	private String description = "";

	/**
	 * price in cent per article (unit); negative values are mapped to 0
	 */
	private long unitPrice = 0;

	/**
	 * currency in which price is quoted, EUR is the default currency
	 */
	private Currency currency = Currency.EUR;

	/**
	 * tax rate applicable to article, German regular MwSt is the default tax rate
	 */
	private TAX tax = TAX.GER_VAT;

	/**
	 * Default constructor.
	 */
	public Article() {
		// TODO implement here
	}

	/**
	 * Constructor with description and price arguments.
	 * 
	 * @param descr     descriptive text for article
	 * @param unitPrice unitPrice price (in cent) for one unit of the article
	 */
	public Article(String descr, long unitPrice) {
		// TODO implement here
		setDescription(descr);
		setUnitPrice(unitPrice);
	}

	/**
	 * Id getter. Id can only be set once since id are immutable after assignment.
	 * 
	 * @return article id, may be invalid (null) if still unassigned
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
	public Article setId(String id) {
		// TODO implement here
		if (id != null && id != "" && this.id == null) {
			this.id = id;
		}
		return this;
	}

	/**
	 * Description getter.
	 * 
	 * @return descriptive text for article
	 */
	public String getDescription() {
		// TODO implement here
		return this.description;
	}

	/**
	 * Description setter.
	 * 
	 * @param descr descriptive text for article, only valid description (not null or "") updates attribute
	 * @return chainable self-reference
	 */
	public Article setDescription(String descr) {
		// TODO implement here
		if (descr.length() > 0) {
			this.description = descr;
		}
		return this;
	}

	/**
	 * UnitPrice getter.
	 * 
	 * @return price in cent for one article unit
	 */
	public long getUnitPrice() {
		// TODO implement here
		return this.unitPrice;
	}

	/**
	 * UnitPrice setter.
	 * 
	 * @param unitPrice price in cent for one article, only valid price ({@code >= 0} ) updates attribute
	 * @return chainable self-reference
	 */
	public Article setUnitPrice(long unitPrice) {
		// TODO implement here
		if (unitPrice >= 0) {
			this.unitPrice = unitPrice;
		}
		return this;
	}

	/**
	 * Currency getter.
	 * 
	 * @return currency in which unitPrice is quoted
	 */
	public Currency getCurrency() {
		// TODO implement here
		return this.currency;
	}

	/**
	 * Currency setter.
	 * 
	 * @param currency currency currency in which unitPrice is quoted, only valid currency (not null) updates attribute
	 * @return chainable self-reference
	 */
	public Article setCurrency(Currency currency) {
		// TODO implement here
		if (currency != null) {
			this.currency = currency;
		}
		return this;
	}

	/**
	 * TAX getter.
	 * 
	 * @return tax rate applicable for article
	 */
	public TAX getTax() {
		// TODO implement here
		return this.tax;
	}

	/**
	 * TAX setter.
	 * 
	 * @param tax tax rate that applies to article, only valid tax rate (not null) updates attribute
	 * @return chainable self-reference
	 */
	public Article setTax(TAX tax) {
		// TODO implement here
		if (tax != null) {
			this.tax = tax;
		}
		return this;
	}

}