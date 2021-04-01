import java.net.*;
import java.io.*;
import java.util.zip.CRC32;

public class Server {
    private static int min = 1;
    private static int max = 9;
    private static CRC32 checkSum;
    private static String textFileName = "server-data.txt";

    public static void main(String args[]) throws Exception { // establishing the connection with the server

        FileWriter fileWriter = new FileWriter(textFileName, true);
        StringBuilder sb = new StringBuilder();
        checkSum = new CRC32();
        for (int i = 0; i < 10; i++) {
            int temp = min + (int) (Math.random() * ((max - min) + 1));
            // System.out.print(temp);
            // data = data + ((String) temp);
            sb.append(temp);
        }
        String strI = sb.toString();
        fileWriter.write(strI);
        checkSum.update(strI.getBytes());
        System.out
                .println("\nSERVER: Data to be sent: " + "(Data) " + strI + " " + "(CheckSum) " + checkSum.getValue());
        fileWriter.write(" " + checkSum.getValue());
        fileWriter.close();

        ServerSocket sersock = new ServerSocket(4000);
        System.out.println("\nSERVER: Ready for connection");
        Socket sock = sersock.accept(); // binding with port: 4000
        System.out.println("\nSERVER: Connection is successful and waiting for client to respond");

        // reading the file name from client
        InputStream istream = sock.getInputStream();
        BufferedReader fileRead = new BufferedReader(new InputStreamReader(istream));
        String fname = fileRead.readLine();
        // reading file contents
        BufferedReader contentRead = new BufferedReader(new FileReader(fname));

        // keeping output stream ready to send the contents
        OutputStream ostream = sock.getOutputStream();
        PrintWriter pwrite = new PrintWriter(ostream, true);

        String str;
        while ((str = contentRead.readLine()) != null) // reading line-by-line from file
        {
            pwrite.println(str); // sending each line to client
        }

        sock.close();
        sersock.close(); // closing network sockets
        pwrite.close();
        fileRead.close();
        contentRead.close();
    }
}