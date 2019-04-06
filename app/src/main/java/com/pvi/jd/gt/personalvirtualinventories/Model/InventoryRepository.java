package com.pvi.jd.gt.personalvirtualinventories.Model;

public class InventoryRepository {
    private static final InventoryRepository INVENTORY_REPOSITORY = new InventoryRepository();

    public static InventoryRepository getInventoryRepository() {
        return INVENTORY_REPOSITORY;
    }

    private Model model = Model.get_instance();

    public void addToInventory(String newItem) {


    }

    public void removeFromInventory(String itemToRemove) {

    }
}
