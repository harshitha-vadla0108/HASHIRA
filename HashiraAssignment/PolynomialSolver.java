import java.io.*;
import java.util.*;

public class PolynomialSolver {
    public static void main(String[] args) {
        try {
            // Read the file into a string
            BufferedReader br = new BufferedReader(new FileReader("input.json"));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line.trim());
            }
            br.close();
            String json = sb.toString();

            // Extract n and k
            int n = Integer.parseInt(json.replaceAll(".*\"n\"\\s*:\\s*(\\d+).*", "$1"));
            int k = Integer.parseInt(json.replaceAll(".*\"k\"\\s*:\\s*(\\d+).*", "$1"));

            List<Long> numbers = new ArrayList<>();

            // Extract each base/value pair
            for (int i = 1; i <= n; i++) {
                String pattern = "\"" + i + "\"\\s*:\\s*\\{[^}]*\"base\"\\s*:\\s*\"?(\\w+)\"?,\\s*\"value\"\\s*:\\s*\"?(\\w+)\"?[^}]*\\}";
                String entry = json.replaceAll("(?s).*" + pattern + ".*", "$1,$2");

                if (!entry.contains(",")) continue; // skip if not found
                String[] parts = entry.split(",");
                int base = Integer.parseInt(parts[0]);
                String value = parts[1];

                // Convert from given base to decimal
                long decimalValue = Long.parseLong(value, base);
                numbers.add(decimalValue);
            }

            // Sort in descending order
            numbers.sort(Collections.reverseOrder());

            if (k <= numbers.size()) {
                System.out.println(numbers.get(k - 1));
            } else {
                System.out.println("Not enough values to find k-th largest.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
