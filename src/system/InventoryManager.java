package system;

import datamodel.Order;

public interface InventoryManager {
	/**
	 * Order is fillable when all ordered items meet the condition:
	 * {@code orderItem.unitOrderd <= inventory(article).unitsInStore}.
	 * 
	 * @return order to validate.
	 * @return true if order is fillable from current inventory.
	 */
	public boolean isFillable( Order order );

}
