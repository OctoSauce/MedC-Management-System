import exceptions.InvalidCredentialsException;

import java.io.File;


public class Admin extends User {
    private Admin() {
        super("Admin", 0, "admin@pilani.bits-pilani.ac.in", "0000000000");
    }

    public static Admin login(String email) {
        if (email.equals("admin@pilani.bits-pilani.ac.in")) return new Admin();
        else throw new InvalidCredentialsException();
    }

    public void updateNoticeBoard(int docId, String docName, String consultType, String daysAvailable, String startTime, String endTime) {
        if (startTime.length() == 4) startTime = "0" + startTime;
        if (endTime.length() == 4) endTime = "0" + endTime;
        Notice.addOrUpdateNotices(docId, docName, consultType, daysAvailable, startTime, endTime);
    }

    public void addMedicine(String name, int id, double price, int quantity) {
        Medicine.addOrUpdateMedicine(name, id, price, quantity);
    }

    public void viewMedicines() {
        System.out.println("****************** Medicines ******************");
        System.out.format("%15s%10s%10s%12s\n", "Name", "ID", "Price", "Quantity");
        System.out.println("***********************************************");
        for (String medicine : Medicine.viewMedicines().split("\n")) {
            System.out.format("%15s%10s%10s%12s\n", medicine.split(","));
        }
        System.out.println("***********************************************");
    }

    public void viewAppointments() {
        System.out.println("********************* Appointments *********************");
        System.out.format("%12s%14s%10s%10s%10s\n", "Timestamp", "ID", "DocID", "Day", "Time");
        System.out.println("********************************************************");
        for (String app : Appointment.viewAppointments().split("\n")) {
            System.out.format("%12s%14s%10s%10s%10s\n", app.split(","));
        }
        System.out.println("********************************************************");
    }
    public void viewUsers() {
        System.out.print(new String(new char[61]).replace('\u0000', '*'));
        System.out.print(" Users ");
        System.out.println(new String(new char[61]).replace('\u0000', '*'));
        System.out.format("%20s%20s%40s%14s%10s%10s%15s\n", "Name", "ID", "Email", "Mobile", "Paid", "Due", "MedsPurchased");
        System.out.println(new String(new char[129]).replace('\u0000', '*'));
        for (String user : db.view().split("\n")) {
            System.out.format("%20s%20s%40s%14s%10s%10s%15s\n", user.split(","));
        }
        System.out.println(new String(new char[129]).replace('\u0000', '*'));
    }

    public void showSummary() {
        System.out.println("Total Appointments: " + Appointment.getTotalAppointments());
        System.out.println("Total Users: " + db.getLength());
        System.out.println("Medicines in stock: " + Medicine.getTotalMedicines());
        System.out.println("Revenue generated: " + db.getSum(4));
        System.out.println("Amount due: " + db.getSum(5));
        System.out.println("Medicines sold: " + (int)db.getSum(6));
    }

    public void clearAllData() {
        File dir = new File("data");
        for (File subfile : dir.listFiles()) {
            subfile.delete();
        }
    }

}
