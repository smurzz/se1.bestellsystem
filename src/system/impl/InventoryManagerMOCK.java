package system.impl;

import datamodel.Order;
import system.InventoryManager;

class InventoryManagerMOCK implements InventoryManager {

	@Override
	public boolean isFillable(Order order) {
		// TODO Auto-generated method stub
		return true;
	}

}
