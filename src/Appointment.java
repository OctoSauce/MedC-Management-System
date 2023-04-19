import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Appointment {
    private static final ReadWriteLock lock = new ReentrantReadWriteLock();
    private static final Database db = new Database("data", "appointments.csv", 5);

    public static void addAppointment(long timestamp, int userId, int docId, String date, String time) {
        String[] docData = Notice.getDocData(docId);
        if (!Arrays.asList(docData[docData.length - 2].split(";")).contains(date)) {
            throw new RuntimeException("Doctor not available on this date");
        }
        LocalTime startTime = LocalTime.parse(docData[docData.length - 1].split(";")[0], DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime endTime = LocalTime.parse(docData[docData.length - 1].split(";")[1], DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime appointmentTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));
        if (appointmentTime.isBefore(startTime) || appointmentTime.isAfter(endTime)) {
            throw new RuntimeException("Doctor not available at this time");
        }
        lock.writeLock().lock();
        try {
            if (db.appointmentExists(docId, date, appointmentTime)) {
                throw new RuntimeException("Appointment already exists");
            }
            db.add(timestamp, userId, docId, date, time);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public static String viewAppointments() {
        lock.readLock().lock();
        try {
            return db.view();
        } finally {
            lock.readLock().unlock();
        }
    }

    public static int getTotalAppointments() {
        lock.readLock().lock();
        try {
            return db.getLength();
        } finally {
            lock.readLock().unlock();
        }
    }
}
