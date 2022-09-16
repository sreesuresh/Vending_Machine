package service;

import dao.ItemPersistenceException;
import dao.VendingMachineAuditDao;
import dao.VendingMachineDao;
import dao.VendingMachineDaoException;
import dto.Item;

import java.math.BigDecimal;
import java.util.*;

public class Service {

    public Service(VendingMachineDao dao, VendingMachineAuditDao auditDao) {
        this.dao = dao;
        this.auditDao = auditDao;
    }

    private VendingMachineDao dao;
    private VendingMachineAuditDao auditDao;

    public List<Item> getAllItems() throws VendingMachineDaoException {
        return new ArrayList(dao.getAllItems().stream().filter((p) -> p.getStock() > 0).toList());

    }

    public void decrementStock(String selection) throws NoItemInInventoryException, VendingMachineDaoException {
        Item item = dao.getItem(selection);
        if (item.getStock() > 0) {
            dao.decrementStock(selection);
            try {
                auditDao.writeAuditEntry(selection + " stock reduced by 1");
            } catch (ItemPersistenceException e) {
                System.out.println("Audit failure");
            }
        } else {
            try {
                auditDao.writeAuditEntry("Insufficient stock for " + selection);
            } catch (ItemPersistenceException e) {
                System.out.println("Audit failure");
            }
            throw new NoItemInInventoryException("Insufficient inventory");
        }
        // if stock is above 0 then decrement else throw exception
    }

    public void checkExists(String selection) throws SelectionNotInListException, VendingMachineDaoException {
        Item item = dao.getItem(selection);
        if (item == null) {
            throw new SelectionNotInListException("No item with that name");
        }
        //check dao and throw invalid exception if not exists
    }

    public BigDecimal newMoney(BigDecimal money, String selection) throws VendingMachineDaoException {
        Item item = dao.getItem(selection);
        BigDecimal price = item.getPrice();
        try {
            auditDao.writeAuditEntry("Reducing balance by " + price);
        } catch (ItemPersistenceException e) {
            System.out.println("Audit failure");
        }
        return money.subtract(price);
    }

    public void checkFunds(String selection, BigDecimal money) throws InsufficientFundsException, VendingMachineDaoException {
        Item item = dao.getItem(selection);
        if (money.compareTo(item.getPrice()) < 0) {
            try {
                auditDao.writeAuditEntry("Insufficient balance for " + selection);
            } catch (ItemPersistenceException e) {
                System.out.println("Audit failure");
            }
            throw new InsufficientFundsException("Insufficient funds");
        }
    }

    public List<Integer> returnChange(BigDecimal money) {
        BigDecimal zero = new BigDecimal("0.00");
        List<Integer> coins = new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0));
        try {
            if (money.equals(zero)) {
                auditDao.writeAuditEntry("No change given ");
            } else {
                auditDao.writeAuditEntry("Returning change for " + money);
            }
        } catch (ItemPersistenceException e) {
            System.out.println("Audit failure");
        }
        while (money.compareTo(zero) != 0) {
            while (money.compareTo(Change.TWO_POUNDS.value) >= 0) {
                int num = coins.get(0);
                coins.set(0, num + 1);
                money = money.subtract(Change.TWO_POUNDS.value);
            }
            while (money.compareTo(Change.ONE_POUND.value) >= 0) {
                int num = coins.get(1);
                coins.set(1, num + 1);
                money = money.subtract(Change.ONE_POUND.value);
            }
            while (money.compareTo(Change.FIFTY_PENCE.value) >= 0) {
                int num = coins.get(2);
                coins.set(2, num + 1);
                money = money.subtract(Change.FIFTY_PENCE.value);
            }
            while (money.compareTo(Change.TWENTY_PENCE.value) >= 0) {
                int num = coins.get(3);
                coins.set(3, num + 1);
                money = money.subtract(Change.TWENTY_PENCE.value);
            }
            while (money.compareTo(Change.TEN_PENCE.value) >= 0) {
                int num = coins.get(4);
                coins.set(4, num + 1);
                money = money.subtract(Change.TEN_PENCE.value);
            }
            while (money.compareTo(Change.FIVE_PENCE.value) >= 0) {
                int num = coins.get(5);
                coins.set(5, num + 1);
                money = money.subtract(Change.FIVE_PENCE.value);
            }
            while (money.compareTo(Change.TWO_PENCE.value) >= 0) {
                int num = coins.get(6);
                coins.set(6, num + 1);
                money = money.subtract(Change.TWO_PENCE.value);
            }
            while (money.compareTo(Change.ONE_PENCE.value) >= 0) {
                int num = coins.get(7);
                coins.set(7, num + 1);
                money = money.subtract(Change.ONE_PENCE.value);
            }

        }

        return coins;
    }

    public void log(String string) {
        try {
            auditDao.writeAuditEntry(string);
        } catch (ItemPersistenceException e) {
            System.out.println("Audit failure");
        }
    }
}