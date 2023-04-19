import exceptions.EntryNotFoundException;
import exceptions.InsufficientQuantityException;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Medicine {
    private final String name;
    private final int id;
    private int quantity;
    private final double price;
    private static final int paramCountToStore = 4;
    private static final ReadWriteLock lock = new ReentrantReadWriteLock();
    private static final Database db = new Database("data", "meds.csv", paramCountToStore);

    public Medicine(String[] data) {
        if (data.length != paramCountToStore) {
            throw new RuntimeException("Invalid number of parameters");
        }
        this.name = data[0];
        this.id = Integer.parseInt(data[1]);
        this.price = Double.parseDouble(data[2]);
        this.quantity = Integer.parseInt(data[3]);
    }

    public static void addOrUpdateMedicine(String name, int id, double price, int quantity) {
        lock.writeLock().lock();
        try {
            if (db.isExisting(String.valueOf(id), 1)) {
                db.update(1, id, name, price, quantity);
            } else {
                db.add(name, id, price, quantity);
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    public static Medicine getMedicine(int id) {
        lock.readLock().lock();
        try {
            return new Medicine(db.get(1, String.valueOf(id)));
        } catch (EntryNotFoundException e) {
            throw new RuntimeException("Medicine not in database");
        } finally {
            lock.readLock().unlock();
        }
    }

    public static String viewMedicines() {
        lock.readLock().lock();
        try {
            return db.view();
        } finally {
            lock.readLock().unlock();
        }
    }

    public static int getTotalMedicines() {
        lock.readLock().lock();
        try {
            return (int) (db.getSum(3));
        } finally {
            lock.readLock().unlock();
        }
    }

    public void decrement(int quantity) {
        lock.writeLock().lock();
        try {
            this.quantity = Integer.parseInt(db.get(1, String.valueOf(id))[3]) - quantity;
            if (this.quantity < 0) {
                throw new InsufficientQuantityException();
            }
            db.update(1, name, id, price, this.quantity);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public double getPrice() {
        return price;
    }
}
