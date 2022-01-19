package system;

import datamodel.Order;

/**
 * Public Interface of inventory of items managment (how many items are in the warehouse).
 * 
 * @since "0.1.0"
 * @version {@value package_info#Version}
 * @author Sofya
 */

public interface InventoryManager {
	/**
	 * Order is fillable when all ordered items meet the condition:
	 * {@code orderItem.unitOrderd <= inventory(article).unitsInStore}.
	 * 
	 * @param order to validate.
	 * @return true if order is fillable from current inventory.
	 */
	public boolean isFillable( Order order );

}
