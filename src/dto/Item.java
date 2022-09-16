package dto;

import java.math.BigDecimal;

public class Item {
    private String itemName;
    private BigDecimal price;
    private int stock;

    public Item(String name) { // added void here
        this.itemName = name;
    }

    public String getName() {
        return itemName;
    }

    public void setName(String itemName) {
        this.itemName = itemName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return itemName + "[" + stock + "] : £" + price;
    }
}