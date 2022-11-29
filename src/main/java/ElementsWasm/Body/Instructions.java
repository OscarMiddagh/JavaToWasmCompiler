package ElementsWasm.Body;
public class Instructions implements InterfaceInstructions{
    private int pos;
    private byte[] instr;

    public Instructions(int pos, byte[] instr){
        this.pos = pos;
        this.instr = instr;
    }
    @Override
    public int getPos(){
        return pos;
    }
    @Override
    public void setByteInstructions(byte[] instr){
        this.instr = instr;
    }
    @Override
    public byte[] getByteInstructions(){
        return instr;
    }
}
