
public class Main {
    public static void main(String[] args) {
//        User.registerUser("Anish",20200128,"f20200128@pilani.bits-pilani.ac.in","9090909090");
        User user1 = User.login("f20200128@pilani.bits-pilani.ac.in");
        User user2 = User.login("f20200129@pilani.bits-pilani.ac.in");
        Admin a = Admin.login("admin@pilani.bits-pilani.ac.in");
//        a.updateNoticeBoard(1, "doc1", "c", "W", "10:00", "11:00");
//        a.updateNoticeBoard(1, "doc1", "c", "M;Th;F", "10:00", "11:00");
//        user1.bookAppointment(1, "F", "10:00");
//        user1.bookAppointment(1, "F", "10:00");
//        user2.buyMedicine(1, 2, PayMode.LATER);
        a.viewUsers();
//        user1.buyMedicine(1, 1, PayMode.CASH);
//        user1.buyMedicine(1, 1, PayMode.CASH);

        Input.makeAppointments();
//        Input.loadUsers();


//        System.out.println("Appointment booked");
//        user2.bookAppointment(1, "F", "10:00");
//        System.out.println("Appointment booked");


//        a.viewNotices();
//        a.clearAllData();
    }
}