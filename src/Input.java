import java.io.*;

public class Input {
    private static final File inp = new File("input");
    private static final File data = new File("data");

    public static void loadUsers() {
        File inFile = new File(inp, "users.csv");
        File outFile = new File(data, "users.csv");
        try {
            BufferedReader br = new BufferedReader(new FileReader(inFile));
            PrintWriter pw = new PrintWriter(new FileWriter(outFile), true);
            String line;
            while ((line = br.readLine()) != null) {
                pw.append(line).append(",0.0,0.0,0\n");
            }
            pw.close();
            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void makeAppointments() {
        File inFile = new File(inp, "appointments.csv");
        try {
            BufferedReader br = new BufferedReader(new FileReader(inFile));
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                long timestamp = Long.parseLong(data[0]);
                int id = Integer.parseInt(data[1]);
                int docId = Integer.parseInt(data[2]);
                String day = data[3];
                String time = (data[4].length() == 4) ? "0" + data[4] : data[4];
                User.login("f" + id + "@pilani.bits-pilani.ac.in").bookAppointment(docId, day, time);
            }
            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void makeOrders() {
        File inFile = new File(inp, "orders.csv");
        try {
            BufferedReader br = new BufferedReader(new FileReader(inFile));
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                int id = Integer.parseInt(data[0]);
                int medId = Integer.parseInt(data[1]);
                int quantity = Integer.parseInt(data[2]);
                PayMode payMode = (data[3].equals("Cash")) ? PayMode.CASH : PayMode.LATER;
                User.login("f" + id + "@pilani.bits-pilani.ac.in").buyMedicine(medId, quantity, payMode);
            }
            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
