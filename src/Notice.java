import exceptions.EntryNotFoundException;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Notice {
    private static final ReadWriteLock lock = new ReentrantReadWriteLock();
    private static final int paramCountToStore = 5;
    private static final Database db = new Database("data", "notices.csv", paramCountToStore);

    public static void addOrUpdateNotices(int docId, String docName, String consultType, String daysAvailable, String StartTime, String endTime) {
        lock.writeLock().lock();
        try {
            if (db.isExisting(String.valueOf(docId), 0)) {
                db.update(0, docId, docName, consultType, daysAvailable, StartTime + ';' + endTime);
            } else {
                db.add(docId, docName, consultType, daysAvailable, StartTime + ';' + endTime);
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    public static String viewNotices() {
        lock.readLock().lock();
        try {
            return db.view();
        } finally {
            lock.readLock().unlock();
        }
    }

    public static String[] getDocData(int docId) {
        lock.readLock().lock();
        try {
            return db.get(0, String.valueOf(docId));
        } catch (EntryNotFoundException e) {
            throw new RuntimeException("Doctor ID not found");
        } finally {
            lock.readLock().unlock();
        }
    }
}
