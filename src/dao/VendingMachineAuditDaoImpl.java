package dao;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class VendingMachineAuditDaoImpl implements VendingMachineAuditDao {
    public static final String AUDIT_FILE = "audit.txt";
    @Override
    public void writeAuditEntry(String entry) throws ItemPersistenceException {
        PrintWriter out;
        try{
            out = new PrintWriter(new FileWriter(AUDIT_FILE, true));
        } catch (IOException e) {
            throw new ItemPersistenceException("Could not persist audit information");
        }

        LocalDateTime timing = LocalDateTime.now();
        out.println(timing + " : " + entry);
        out.flush();

    }
}