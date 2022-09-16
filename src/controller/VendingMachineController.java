package controller;

import dao.VendingMachineDaoException;
import service.InsufficientFundsException;
import service.NoItemInInventoryException;
import service.SelectionNotInListException;
import service.Service;
import ui.VendingMachineView;

import java.math.BigDecimal;

public class VendingMachineController { // controller now can't talk to dao - must be done through service layer
    private VendingMachineView view;
    private Service service;
    private BigDecimal money = new BigDecimal("0.00");
    public void run() {
        boolean keepLooping = true;
        String selection;

        try {
            while (keepLooping) {
                displayItems(); // Display the vending machine contents with current balance
                getCashInserted();

                selection = getItemSelection();
                if(selection.equals("change") || selection.equals("Change")){
                    giveChange();
                    continue;
                }

                if (selection.equals("exit") || selection.equals("Exit")) {
                    keepLooping = false;
                    continue;
                }

                try{
                    checks(selection); // check selection exists, has stock, and affordable
                } catch(VendingMachineDaoException | InsufficientFundsException | NoItemInInventoryException | SelectionNotInListException e){
                    view.displayErrorMessage(e.getMessage());
                    continue;
                }

                money = service.newMoney(money, selection);
                dispensePurchase(selection); // prints selection
                giveChange();
            }
            exitMessage();
        } catch (VendingMachineDaoException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private void dispensePurchase(String selection) {
        view.display("Dispensing " + selection);
    }

    private void checks(String selection) throws VendingMachineDaoException, SelectionNotInListException, InsufficientFundsException, NoItemInInventoryException {
        service.checkExists(selection);
        service.checkFunds(selection, money);
        service.decrementStock(selection);
    }

    private void giveChange() {
        view.displayChange(service.returnChange(money));// This must always be followed by setting money to 0.00 as change has been returned
        money = new BigDecimal("0.00");
    }

    private void displayItems() throws VendingMachineDaoException {
        view.VMBanner();
        view.display("Balance: £"+money);
        view.VMBanner();
        view.displayItems(service.getAllItems());
        view.VMBanner();
    }
    private String getItemSelection(){
        return view.getSelection();
    }
    private void getCashInserted(){
        money = money.add(view.getUserCash());
        service.log("added " + money + " to balance");
    }
    private void exitMessage() {
        view.displayExitMessage();
    }
    public VendingMachineController(VendingMachineView view, Service service) {
        this.view = view;
        this.service = service;
    }
}