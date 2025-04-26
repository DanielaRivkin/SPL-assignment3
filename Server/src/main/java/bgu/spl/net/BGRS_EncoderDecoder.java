package bgu.spl.net;

import bgu.spl.net.BaseCommand.ClientCommand;
import bgu.spl.net.BaseCommand.Command;
import bgu.spl.net.BaseCommand.ServerCommand;
import bgu.spl.net.Commands.*;
import bgu.spl.net.api.MessageEncoderDecoder;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class BGRS_EncoderDecoder implements MessageEncoderDecoder<Command> {

    private byte[] bytes = new byte[1 << 10]; //start with 1k
    private int len = 0;

    private short opcode = -1;
    private int courseNum = -1;
    private String userName = null;
    private String password = null;


    @Override
    public Command decodeNextByte(byte nextByte) {
        //find the opcode
        if (opcode == -1) {
            bytes[len++] = nextByte;
            if (len == 2) {
                opcode = bytesToShort(Arrays.copyOfRange(bytes, 0, 2));
                len = 0;
                //find if there is no more byte to come before wanting to the next byte
                return decodeLogoutAndCourseStat();
            }
            return null;
        } else {
            ClientCommand output = null;
            if (decodeByOpcode(nextByte)) {
                output = defineCommandByOpcode();
                resetData();
            }
            return output;
        }

    }

    @Override
    public byte[] encode(Command message) {
        return ((ServerCommand)message).execute();

    }

    private void pushByte(byte nextByte) {
        if (len >= bytes.length) {
            bytes = Arrays.copyOf(bytes, len * 2);
        }
        bytes[len++] = nextByte;
    }

    private boolean decodeByOpcode(byte nextByte) {
        switch (opcode){
            case 1:
            case 2:
            case 3:
                if (userName == null)
                    decodeUsername(nextByte);
                else if (password == null)
                    decodePassword(nextByte);
                return userName != null & password != null;

            case 5:
            case 6:
            case 7:
            case 9:
            case 10:
                if (courseNum == -1)
                    decodeCourseNumber(nextByte);
                return courseNum != -1;
            case 8:
                decodeUsername(nextByte);
                return userName != null;
            case 4:
            case 11:
                return true;
        }
        return false;
    }

    private void decodeUsername (byte nextByte){
        if (nextByte != '\0')
            pushByte(nextByte);
        else{
            userName = new String(bytes ,0 ,len  , StandardCharsets.UTF_8);
            len = 0;
        }

    }

    private void decodePassword (byte nextByte){
        if (nextByte != '\0')
            pushByte(nextByte);
        else{
            password = new String(bytes ,0 ,len , StandardCharsets.UTF_8);
            len = 0;
        }
    }

    private void decodeCourseNumber(byte nextByte){
        if (len == 0)
            pushByte(nextByte);
        else {
            pushByte(nextByte);
            courseNum = bytesToShort(bytes);
            len = 0;
        }

    }

    private ClientCommand defineCommandByOpcode() {
        if (opcode == -1)
            return null;
        switch (opcode) {
            case 1:
                return new AdminRegister(userName, password);
            case 2:
                return new StudentRegister(userName, password);
            case 3:
                return new LoginRequest(userName, password);
            case 4:
                return new LogoutRequest();
            case 5:
                return new RegisterToCourse(courseNum);
            case 6:
                return new CheckKdamCourse(courseNum);
            case 7:
                return new PrintCourseStatus(courseNum);
            case 8:
                return new PrintStudentStatus(userName);
            case 9:
                return new CheckIfRegistered(courseNum);
            case 10:
                return new UnregisterCourse(courseNum);
            case 11:
                return new CheckCurrentCourses();


        }
        return null;
    }

    public short bytesToShort(byte[] byteArr) {
        short result = (short) ((byteArr[0] & 0xff) << 8);
        result += (short) (byteArr[1] & 0xff);
        return result;
    }



    private Command decodeLogoutAndCourseStat(){
        switch (opcode){
            case 4:
                resetData();
                return new LogoutRequest();
            case 11:
                resetData();
                return new CheckCurrentCourses();
        }
        return null;
    }

    private void resetData() {
        len = 0;
        bytes = new byte[1 << 10];
        opcode = -1;
        courseNum = -1;
        userName = null;
        password = null;
    }

}
