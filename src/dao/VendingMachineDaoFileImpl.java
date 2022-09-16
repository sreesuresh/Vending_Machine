package dao;

import dto.Item;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

public class VendingMachineDaoFileImpl implements VendingMachineDao {
    private Map<String, Item> Items = new HashMap<>();

    public final String ROSTER_FILE;
    public static final String DELIMITER = "::";

    public VendingMachineDaoFileImpl(){
        ROSTER_FILE = "stock.txt";
    }
    public VendingMachineDaoFileImpl(String file){
        ROSTER_FILE = file;
    }
    public List<Item> getAllItems() throws VendingMachineDaoException {
        loadRoster();
        return new ArrayList(Items.values());
    }

    @Override
    public Item getItem(String itemName) throws VendingMachineDaoException {
        loadRoster();
        return Items.get(itemName);
    }

    @Override
    public void decrementStock(String selection) throws VendingMachineDaoException {
        Item item = Items.get(selection);//getItem(selection);
        item.setStock(item.getStock()-1);
        Items.put(selection, item);
        writeRoster();
    }

    private Item unmarshallDVD(String itemAsText){

        String[] itemTokens = itemAsText.split(DELIMITER);

        // Given the pattern above, the student Id is in index 0 of the array.
        String itemName = itemTokens[0];

        // Which we can then use to create a new Student object to satisfy
        // the requirements of the Student constructor.
        Item itemFromFile = new Item(itemName);

        // However, there are 3 remaining tokens that need to be set into the
        // new student object. Do this manually by using the appropriate setters.

        // Index 1 - Stock
        itemFromFile.setStock(Integer.parseInt(itemTokens[1]));

        // Index 2 - Price
        itemFromFile.setPrice(new BigDecimal(itemTokens[2]));


        // We have now created a student! Return it!
        return itemFromFile;
    }

    public void loadRoster() throws VendingMachineDaoException {
        Scanner scanner;

        try {
            // Create Scanner for reading the file
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(ROSTER_FILE)));
        } catch (FileNotFoundException e) {
            throw new VendingMachineDaoException(
                    "-_- Could not load roster data into memory.", e);
        }
        // currentLine holds the most recent line read from the file
        String currentLine;
        // currentStudent holds the most recent student unmarshalled
        Item currentItem;
        // Go through ROSTER_FILE line by line, decoding each line into a
        // Student object by calling the unmarshallStudent method.
        // Process while we have more lines in the file
        while (scanner.hasNextLine()) {
            // get the next line in the file
            currentLine = scanner.nextLine();
            // unmarshall the line into a Student
            currentItem = unmarshallDVD(currentLine);

            // We are going to use the student id as the map key for our student object.
            // Put currentStudent into the map using student id as the key
            Items.put(currentItem.getName(), currentItem);
        }
        // close scanner
        scanner.close();
    }

    private String marshallDVD(Item anItem){

        String itemAsText = anItem.getName() + DELIMITER;

        itemAsText += anItem.getStock() + DELIMITER;

        itemAsText += anItem.getPrice();

        return itemAsText;
    }

    private void writeRoster() throws VendingMachineDaoException {
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(ROSTER_FILE));
        } catch (IOException e) {
            throw new VendingMachineDaoException(
                    "Could not save student data.", e);
        }

        String itemAsText;
        List<Item> itemList = this.getAllItems();
        for (Item currentItem : itemList) {
            // turn a Student into a String
            itemAsText = marshallDVD(currentItem);
            // write the Student object to the file
            out.println(itemAsText);
            // force PrintWriter to write line to the file
            out.flush();
        }
        // Clean up
        out.close();
    }

}