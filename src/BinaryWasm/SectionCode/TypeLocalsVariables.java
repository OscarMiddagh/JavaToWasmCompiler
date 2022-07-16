package BinaryWasm.SectionCode;

public class TypeLocalsVariables {
    private int countTypes;
    private byte type;
    public TypeLocalsVariables(byte type){
        this.countTypes = 1;
        this.type = type;
    }
    public void typesPP(){
        countTypes++;
    }
    public byte getType() {
        return type;
    }

    public int getnDecl() {
        return countTypes;
    }
}
