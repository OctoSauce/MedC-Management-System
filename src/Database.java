import exceptions.EntryNotFoundException;

import java.io.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Database {
    protected final File file;
    private final int paramCount;

    public Database(String dirname, String filename, int paramCount) {
        File dir = new File(dirname);
        file = new File(dir, filename);
        dir.mkdir();
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.paramCount = paramCount;
    }

    public void add(Object... data) {
        if (data.length != paramCount) {
            throw new RuntimeException("Invalid number of parameters");
        }
        try {
            StringBuilder sb = new StringBuilder();
            PrintWriter pw = new PrintWriter(new FileWriter(file, true), true);
            for (int i = 0; i < data.length; i++) {
                sb.append(data[i]);
                if (i != data.length - 1) sb.append(",");
            }
            pw.append(sb).append('\n');
            pw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public String view() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line).append('\n');
            }
            br.close();
            return sb.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String[] get(int compareIndex, String key) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[compareIndex].equals(key)) {
                    return data;
                }
            }
            throw new EntryNotFoundException();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(int compareIndex, Object... data) {
        if (data.length != paramCount) {
            throw new RuntimeException("Invalid number of parameters");
        }
        try {
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                String[] existing = line.split(",");
                if (existing[compareIndex].equals(data[compareIndex].toString())) {
                    for (int i = 0; i < data.length; i++) {
                        sb.append(data[i]);
                        if (i != data.length - 1) sb.append(",");
                    }
                } else {
                    sb.append(line);
                }
                sb.append('\n');
            }
            PrintWriter pw = new PrintWriter(new FileWriter(file));
            pw.write(sb.toString());
            pw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isExisting(String key, int compareIndex) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[compareIndex].equals(key)) {
                    return true;
                }
            }
            return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean appointmentExists(int docId, String date, LocalTime time) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[2].equals(String.valueOf(docId)) && data[3].equals(date)) {
                    LocalTime existing = LocalTime.parse(data[4], DateTimeFormatter.ofPattern("HH:mm"));
                    if (!(time.isBefore(existing) || time.isAfter(existing.plusMinutes(9)))) {
                        return true;
                    }
                }
            }
            return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public double getSum(int index) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            double sum = 0;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                sum += Double.parseDouble(data[index]);
            }
            return sum;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int getLength() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            int sum = 0;
            while (br.readLine() != null) {
                sum++;
            }
            return sum;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
