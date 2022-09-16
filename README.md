# Vending_Machine

The program will display all of the items and their respective prices when the program starts. The user can put in some amount of money before an item can be selected. 
Only one item can be vended at a time. If the user selects an item that costs more than the amount the user put into the vending machine, the program will display 
a message indicating insufficient funds and then redisplay the amount the user had put into the machine. If the user selects an item that costs equal to or less than 
the amount of money that the user put in the vending machine, the program will display the change returned to the user. Change will be displayed as the number of 
quarters, dimes, nickels, and pennies returned to the user. Vending machine items will be stored in a file. Inventory for the vending machine can be read from this 
file when the program starts and can be written out to this file just before the program exits. The program can track the following properties 
for each item: Item name, Item cost, Number of items in inventory. When an item is vended, the program will update the inventory level appropriately. 
If the machine runs out of an item, it will no longer be available as an option to the user. However, the items that have an inventory level of zero still be read 
from and written to the inventory file and stored in memory.
