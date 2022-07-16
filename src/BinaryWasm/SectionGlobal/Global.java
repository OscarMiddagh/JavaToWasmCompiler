package BinaryWasm.SectionGlobal;
public class Global implements IGlobal {
    private byte[] global;
    private byte value;
    public Global(String type, int mut, String value){
        this.value = (value != null)?value.getBytes()[0]:0x00;
        global = new byte[]{readChar(type),(byte)mut,constType(type),this.value,0x0b};
    }
    private byte constType(String type){
        byte result = 0x00;
        switch (type){
            case "B": //byte
                break;
            case "C": //char
                break;
            case "D": //double
                result = 0x44;
                break;
            case "F": //float
                result = 0x43;
                break;
            case "I": //int
                result= 0x41;
                break;
            case "J": //long
                result = 0x42;
                break;
            case "Z": //boolean
                break;
            case "[": //array
                break;
        }
        return result;
    }
    private byte readChar(String c){
        byte result = 0x00;
        switch (c){
            case "B": //byte
                break;
            case "C": //char
                break;
            case "D": //double
                result = 0x7C;
                break;
            case "F": //float
                result = 0x7D;
                break;
            case "I": //int
                result= 0x7F;
                break;
            case "J": //long
                result = 0x7E;
                break;
            case "Z": //boolean
                break;
            case "[": //array
                break;
        }
        return result;
    }
    @Override
    public byte[] getBytesElement() {
        return global;
    }
}
