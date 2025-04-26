package bgu.spl.net.BaseCommand;

import java.nio.charset.StandardCharsets;

public abstract class ServerCommand implements Command {

    protected String message = null;
    protected short opcode;
    protected short opcodeOfResponse;

    protected ServerCommand(short opcodeOfResponse){
        this.opcodeOfResponse = opcodeOfResponse;
    }

    protected ServerCommand(short opcodeOfResponse , String message){
        this.opcodeOfResponse = opcodeOfResponse;
        this.message = message;
    }

    public byte[] execute (){
        if (message == null)
            return enrollMessage(opcode,opcodeOfResponse);
        return enrollMessage(opcode,opcodeOfResponse,message);
    }

    protected static byte[] shortToBytes(short num) {
        byte[] bytesArr = new byte[2];
        bytesArr[0] = (byte) ((num >> 8) & 0xFF);
        bytesArr[1] = (byte) (num & 0xFF);
        return bytesArr;
    }

    protected static byte[] enrollMessage(short opcode,short opcodeOfResponse, String message) {
        byte[] both = enrollMessage(opcode ,opcodeOfResponse);
        byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
        byte[] output = new byte[both.length + messageBytes.length];
        System.arraycopy(both, 0, output, 0, both.length);
        System.arraycopy(messageBytes, 0, output, both.length, messageBytes.length);
        return output;
    }

    protected static byte[] enrollMessage(short opcode, short opcodeOfResponse) {
        byte[] opcodeByte = shortToBytes(opcode);
        byte[] opcodeOfResponseBytes = shortToBytes(opcodeOfResponse);
        byte[] output = new byte[opcodeByte.length + opcodeOfResponseBytes.length];
        System.arraycopy(opcodeByte, 0, output, 0, opcodeByte.length);
        System.arraycopy(opcodeOfResponseBytes, 0, output, opcodeByte.length, opcodeOfResponseBytes.length);
        return output;
    }


}
