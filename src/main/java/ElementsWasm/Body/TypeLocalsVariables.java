package ElementsWasm.Body;

public class TypeLocalsVariables {
    private int nDecl;
    private byte type;
    public TypeLocalsVariables(byte type){
        this.nDecl = 1;
        this.type = type;
    }
    public void typesPP(){
        nDecl++;
    }
    public byte getType() {
        return type;
    }

    public int getNDecl() {
        return nDecl;
    }
}
