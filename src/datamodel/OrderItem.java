package datamodel;

/**
 * Class that describes an ordered item as part of an Order. Orders may have
 * multiple order items.
 * 
 * @since "0.1.0"
 * @version {@value package_info#Version}
 * @author Sofya
 */
public class OrderItem {

	/**
	 * ordered article, throws IllegalArgumentException if article is null
	 */
	private final Article article;

	/**
	 * number of articles ordered, throws IllegalArgumentException if not a positive number
	 */
	private int unitsOrdered;

	/**
	 * Constructor with article and units arguments.
	 * 
	 * @param article ordered article, throws IllegalArgumentException if article is null
	 * @param unitsOrdered number of articles ordered
	 */
	public OrderItem(Article article, int unitsOrdered) {
		// TODO implement here
		if (article != null) {
			this.article = article;
			setUnitsOrdered(unitsOrdered);
		} else {
			throw new IllegalArgumentException("Article is null!");
		}
	}

	/**
	 * Article getter. Attribute article cannot be changed has therefore has no setter 
	 * (immutable attribute).
	 * 
	 * @return reference to the ordered article
	 */
	public Article getArticle() {
		// TODO implement here
		return this.article;
	}

	/**
	 * UnitsOrdered getter.
	 * 
	 * @return number of article ordered
	 */
	public int getUnitsOrdered() {
		// TODO implement here
		return this.unitsOrdered;
	}

	/**
	 * UnitsOrdered setter with constraint: {@code units >= 0}, otherwise the method
	 * has no effect.
	 * 
	 * @param units updated number of articles ordered
	 */
	public void setUnitsOrdered(int units) {
		// TODO implement here
		if (units >= 0) {
			this.unitsOrdered = units;
		}
	}

}