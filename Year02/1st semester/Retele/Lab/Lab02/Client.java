import java.net.*;
import java.io.*;
 
public class Client {
 
public static void main(String args[]) throws Exception {
  if (args.length < 2) {
      System.out.println("Nu ati intodus suficiente argumente");
      return;
  }

  Socket c = new Socket(args[0],Integer.parseInt(args[1]));
  BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
  int a, b, s;
  System.out.print("a = ");
  a = Integer.parseInt(reader.readLine());
  System.out.print("b = ");
  b = Integer.parseInt(reader.readLine());
  
  DataInputStream socketIn = new DataInputStream(c.getInputStream());
  DataOutputStream socketOut = new DataOutputStream(c.getOutputStream());
  
  socketOut.writeShort(a);
  socketOut.writeShort(b);
  socketOut.flush();
  
  s = socketIn.readUnsignedShort();
  System.out.println("s = " + s);
  
  reader.close();
  c.close();  
}
 
}
