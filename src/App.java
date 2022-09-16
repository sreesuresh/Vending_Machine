
import controller.VendingMachineController;
import dao.VendingMachineAuditDao;
import dao.VendingMachineAuditDaoImpl;
import dao.VendingMachineDao;
import dao.VendingMachineDaoFileImpl;
import service.Service;
import ui.UserIO;
import ui.UserIOConsoleImpl;
import ui.VendingMachineView;

public class App {
    public static void main(String[] args) {
        UserIO myIo = new UserIOConsoleImpl();
        VendingMachineView myView = new VendingMachineView(myIo);
        VendingMachineDao myDao = new VendingMachineDaoFileImpl();
        VendingMachineAuditDao auditDao = new VendingMachineAuditDaoImpl();
        Service myService = new Service(myDao, auditDao);
        VendingMachineController controller = new VendingMachineController(myView, myService);
        controller.run();
    }
}