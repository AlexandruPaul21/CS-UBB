import java.net.*;
import java.io.*;

public class Client {

    public static void main(String args[]) throws Exception {
        if (args.length < 2) {
            System.out.println("Nu ati introdus suficiente argumente");
            return;
        }

        Socket c = new Socket(args[0],Integer.parseInt(args[1]));
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int n, s;
        int list[];
        list = new int[100];
        System.out.print("n = ");
        n = Integer.parseInt(reader.readLine());

        System.out.println("Introduceti numerele (enter dupa fiecare): ");
        for (int i = 0; i < n; ++i) {
            list[i] = Integer.parseInt(reader.readLine());
        }

        DataInputStream socketIn = new DataInputStream(c.getInputStream());
        DataOutputStream socketOut = new DataOutputStream(c.getOutputStream());

        socketOut.writeShort(n);
        for (int i = 0; i < n; ++i) {
            socketOut.writeShort(list[i]);
        }

        socketOut.flush();
        s = socketIn.readUnsignedShort();

        System.out.println("s = " + s);

        reader.close();
        c.close();
    }
}
