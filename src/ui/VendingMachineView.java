package ui;

import dto.Item;
import service.Change;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VendingMachineView {
    private UserIO io;
    private List<Change> coins;

    public VendingMachineView(UserIO io) {
        this.io = io;
        this.coins = new ArrayList<>(Arrays.asList(Change.TWO_POUNDS,
                Change.ONE_POUND,
                Change.FIFTY_PENCE,
                Change.TWENTY_PENCE,
                Change.TEN_PENCE,
                Change.FIVE_PENCE,
                Change.TWO_PENCE,
                Change.ONE_PENCE));
    }

    public void VMBanner() {
        io.print("________________________________________________________");
    }

    public void displayItems(List<Item> itemList) {
        for (Item currentItem : itemList) {
            io.print(currentItem.toString());
        }
    }

    public BigDecimal getUserCash() {

        return io.readBigDecimal(2); // takes scale argument

    }

    public String getSelection() {
        String selection;
        do {
            selection = io.readString("Please enter your choice");
            if (selection == null) {
                io.print("Invalid input");
            }
        } while (selection == null);
        return selection;
    }

    public void displayErrorMessage(String errorMsg) {
        io.print("=== ERROR ===");
        io.print(errorMsg);
    }

    public void displayExitMessage() {
        io.print("Goodbye :)");
    }

    public void display(String message) {
        io.print(message);
    }

    public void displayChange(List<Integer> change) {
        io.print("Change returned:");
        boolean changeGiven = false;
        for (int i = 0; i < change.size(); i++) {
            if (change.get(i) > 0) {
                io.print(change.get(i) + "*" + coins.get(i));
                changeGiven = true;
            }
        }
        if (!changeGiven) {
            io.print("No change given");
        }
    }
}