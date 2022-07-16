package BinaryWasm.SectionCode;
public class Instructions {
    private int pos;
    private byte[] instr;

    public Instructions(int pos, byte[] instr){
        this.pos = pos;
        this.instr = instr;
    }
    public int getPos(){
        return pos;
    }
    public void setByteInstructions(byte[] instr){
        this.instr = instr;
    }
    public byte[] getByteInstructions(){
        return instr;
    }
}
