import java.net.*;
import java.io.*;
import java.util.zip.CRC32;
import java.util.*;
//import java.util.regex.Pattern;

public class Client {

    private static String str;
    private static String stringArray[];
    private static String dataArray[];
    private static String data;
    private static CRC32 checkSum;
    private static InetAddress host;
    private static String textFileName = "server-data.txt";

    public static void main(String args[]) throws Exception {
        checkSum = new CRC32();
        host = InetAddress.getLocalHost();
        Socket sock = new Socket(host.getHostName(), 4000);

        // sending the file name to server. Uses PrintWriter
        OutputStream ostream = sock.getOutputStream();
        PrintWriter pwrite = new PrintWriter(ostream, true);
        pwrite.println(textFileName);
        // receiving the contents from server. Uses input stream
        InputStream istream = sock.getInputStream();
        BufferedReader socketRead = new BufferedReader(new InputStreamReader(istream));

        while ((str = socketRead.readLine()) != null) // reading line-by-line
        {
            stringArray = str.split(" ");
        }

        dataArray = stringArray[0].split("");
        System.out.println("\nCLIENT: Recieved Data: " + Arrays.toString(stringArray));
        data = stringArray[0];
        checkSum.update(data.getBytes());
        String stringCheckSum = String.valueOf(checkSum.getValue());

        System.out
                .print("\nCLIENT: Received Checksum: " + stringArray[1] + " | Calculated Checksum: " + stringCheckSum);
        if (stringCheckSum.equals(stringArray[1])) {
            System.out.println("\n\nCLIENT: CheckSum is correct!");
        } else {
            System.out.println("\nCLIENT: CheckSum Failed:\nData is not correct!");
        }

        try {
            FileWriter writer = new FileWriter("client-data.txt", true);
            for (int i = 0; i < dataArray.length; i++) {
                writer.write(dataArray[i]);
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        pwrite.close();
        socketRead.close();
        // keyRead.close();
    }
}