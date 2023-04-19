import exceptions.*;
import java.util.regex.Pattern;

public class User {
    private final String name, email, mobile;
    private final int id;
    private int medsPurchased;
    private double paid, due;
    private static final int paramCountToStore = 7;

    protected static final Database db = new Database("data", "users.csv", paramCountToStore);

    protected User(String name, int id, String email, String mobile) {
        this.name = name;
        this.email = email;
        this.id = id;
        this.mobile = mobile;
    }

    private User(String name, int id, String email, String mobile, double paid, double due) {
        this.name = name;
        this.email = email;
        this.id = id;
        this.mobile = mobile;
        this.paid = paid;
        this.due = due;
    }

    private User(String[] data) {
        if (data.length != paramCountToStore) {
            throw new RuntimeException("Invalid number of parameters");
        }
        this.name = data[0];
        this.id = Integer.parseInt(data[1]);
        this.email = data[2];
        this.mobile = data[3];
        this.paid = Double.parseDouble(data[4]);
        this.due = Double.parseDouble(data[5]);
        this.medsPurchased = Integer.parseInt(data[6]);
    }

    public static User registerUser(String name, int id, String email, String mobile) {
        try {
            Long.parseLong(mobile);
        } catch (NumberFormatException e) {
            throw new NumberNotValidException();
        }
        if (mobile.length() != 10) {
            throw new NumberNotValidException();
        }
        if (String.valueOf(id).length() != 8) {
            throw new IdNotValidException();
        }
        if (!Pattern.matches("^[p|f](2018|2019|2020|2021|2022)[0-9]{4}(@pilani\\.bits-pilani\\.ac\\.in)$", email)) {
            throw new InvalidCredentialsException();
        }
        if (db.isExisting(email, 2)) {
            throw new UserAlreadyExistsException();
        }
        db.add(name, id, email, mobile, 0.0, 0.0, 0);
        return new User(name, id, email, mobile);
    }

    public static User login(String email) {
        if (!db.isExisting(email, 2)) {
            throw new UserNotRegisteredException();
        }
        return new User(db.get(2, email));
    }

    public void bookAppointment(int docId, String date, String time) {
        String finalTime = (time.length() == 4) ? "0" + time : time;
        new Thread(() -> {
            Appointment.addAppointment(System.currentTimeMillis() / 1000L, id, docId, date, finalTime);
        }).start();
    }

    public void buyMedicine(int medId, int quantity, PayMode payMode) {
        new Thread(() -> {
            Medicine medicine = Medicine.getMedicine(medId);
            double price = medicine.getPrice() * quantity;
            medsPurchased += quantity;
            if (payMode == PayMode.CASH) {
                paid += price;
            } else {
                due += price;
            }
            medicine.decrement(quantity);
            updateUserDatabase();
        }).start();
    }

    public void viewNotices() {
        System.out.println("************************************** Notices **************************************");
        System.out.format("%5s%20s%20s%20s%20s\n", "DocID", "DocName", "ConsultType", "DaysAvailable", "TimeAvailable");
        System.out.println("*************************************************************************************");
        for (String notice : Notice.viewNotices().split("\n")) {
            System.out.format("%5s%20s%20s%20s%20s\n", notice.split(","));
        }
        System.out.println("*************************************************************************************");
    }

    private void updateUserDatabase() {
        db.update(2, name, id, email, mobile, paid, due, medsPurchased);
    }
}

