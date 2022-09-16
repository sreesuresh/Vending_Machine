package dao;

import dto.Item;

import java.util.List;

public interface VendingMachineDao {
    List<Item> getAllItems() throws VendingMachineDaoException;

    void decrementStock(String selection) throws VendingMachineDaoException;

    void loadRoster() throws VendingMachineDaoException;

    Item getItem(String selection) throws VendingMachineDaoException;
}