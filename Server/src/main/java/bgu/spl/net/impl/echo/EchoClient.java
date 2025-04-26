package bgu.spl.net.impl.echo;




import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;


public class EchoClient {
    public static byte[] shortToBytes(short num) {
        byte[] bytesArr = new byte[2];
        bytesArr[0] = (byte) ((num >> 8) & 0xFF);
        bytesArr[1] = (byte) (num & 0xFF);
        return bytesArr;
    }

    public static byte[] enrollMessage(short opcode, String message) {
        byte[] op = shortToBytes(opcode);
        byte[] me = message.getBytes(StandardCharsets.UTF_8);
        byte[] output = new byte[op.length + me.length];
        for (int i = 0; i < op.length; i++)
            output[i] = op[i];
        for (int i = op.length; i < output.length; i++)
            output[i] = me[i - op.length];
        return output;
    }

    public static byte[] enrollMessage(short opcode, short courseNum) {
        byte[] op = shortToBytes(opcode);
        byte[] me = shortToBytes(courseNum);
        byte[] output = new byte[op.length + me.length];
        System.arraycopy(op, 0, output, 0, op.length);
        System.arraycopy(me, 0, output, op.length, me.length);
        //output = ArrayUtils.addAll()
        /*for (int i = 0; i < op.length; i++)
            output[i] = op[i];
        for (int i = op.length; i < output.length - 1; i++)
            output[i] = me[i - op.length];*/
        return output;
    }

    public static void main(String[] args) throws IOException {
        args = new String[]{};
        if (args.length == 0) {
            args = new String[]{"localhost", "hello"};
        }

        if (args.length < 2) {
            System.out.println("you must supply two arguments: host, message");
            System.exit(1);
        }

        //BufferedReader and BufferedWriter automatically using UTF-8 encoding
        try (Socket sock = new Socket(args[0], 7777);
             BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()))) {

            System.out.println("sending message to server");
            byte[] adminReg = enrollMessage((short) 1, "orif\0ori\0");
            byte[] userReg = enrollMessage((short) 2, "ori\0ori\0");
            byte[] courseReg2 = enrollMessage((short) 5, (short) 102);
            byte[] login = enrollMessage((short) 3, "orif\0ori\0");
            byte[] logout = shortToBytes((short) 4);
            byte[] regToCourse = enrollMessage((short) 5, (short) 104);
            byte[] kdamCheck = enrollMessage((short) 6, (short) 112);
            byte[] printCourseStatus = enrollMessage((short) 7, (short) 667);
            byte[] printStudenStatus = enrollMessage((short) 8, "ori\0");
            byte[] checkIfReg = enrollMessage((short) 9, (short) 104);
            byte[] unregCourse = enrollMessage((short) 10, (short) 104);
            byte[] checkMyCurrCourses = shortToBytes((short) 11);

            sock.getOutputStream().write(adminReg);
            out.newLine();
            sock.getOutputStream().flush();
            System.out.println("awaiting response");
            String line = in.readLine();
            System.out.println("message from server: " + line);

            sock.getOutputStream().write(login);
            out.newLine();
            sock.getOutputStream().flush();

            System.out.println("awaiting response");
            String line1 = in.readLine();
            System.out.println("message from server: " + line1);

            sock.getOutputStream().write(printCourseStatus);
            sock.getOutputStream().flush();

            System.out.println("awaiting response");
            String line8 = in.readLine();
            System.out.println("message from server: " + line8);

            sock.getOutputStream().write(printCourseStatus);
            sock.getOutputStream().flush();

            System.out.println("awaiting response");
            String line2 = in.readLine();
            System.out.println("message from server: " + line2);

            sock.getOutputStream().write(printCourseStatus);
            out.newLine();
            sock.getOutputStream().flush();

            System.out.println("awaiting response");
            String line4 = in.readLine();
            System.out.println("message from server: " + line4);

            sock.getOutputStream().write(checkIfReg);
            out.newLine();
            sock.getOutputStream().flush();

            System.out.println("awaiting response");
            String line222 = in.readLine();
            System.out.println("message from server: " + line222);
            System.out.println(in.readLine());
            System.out.println(in.readLine());
            System.out.println(in.readLine());
            System.out.println(in.readLine());



        }

    }

}
