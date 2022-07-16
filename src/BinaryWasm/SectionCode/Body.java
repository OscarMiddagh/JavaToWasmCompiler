package BinaryWasm.SectionCode;
import java.util.ArrayList;
public class Body implements IBody {
    private byte[] codeBody;
    private byte[] bodyAux;
    private ArrayList<TypeLocalsVariables> localsVariables;
    private ArrayList<IBlock> blocks;
    private ArrayList<Instructions> instructions;

    public Body(ArrayList<TypeLocalsVariables> localsVariables, ArrayList<Instructions> instructions, ArrayList<IBlock> blocks){
        this.localsVariables =localsVariables;
        this.instructions = instructions;
        this.blocks = blocks;
        codeBody = new byte[2+(2*localsVariables.size())];
        addBlocks();
        constructBody();
        bodyAux = codeBody;
        codeBody = new byte[codeBody.length+1];
        System.arraycopy(bodyAux,0,codeBody,0,bodyAux.length);
        codeBody[0] = (byte)(codeBody.length-1);
        codeBody[codeBody.length-1] = 0x0b;
    }
    @Override
    public byte[] getBytesElement() {
        return codeBody;
    }
    private void constructBody() {
        codeBody[1] = (byte)(localsVariables.size());
        for (int i = 1; i <= localsVariables.size(); i++) {
            codeBody[i*2] = (byte)(localsVariables.get(i-1).getnDecl());
            codeBody[i*2+1] = localsVariables.get(i-1).getType();
        }
        byte[] instrs;
        for (int i = 0; i < instructions.size(); i++) {
            bodyAux = codeBody;
            instrs = instructions.get(i).getByteInstructions();
            codeBody = new byte[bodyAux.length+instrs.length];
            System.arraycopy(bodyAux,0,codeBody,0,bodyAux.length);
            System.arraycopy(instrs,0,codeBody,bodyAux.length,instrs.length);
        }
    }

    private void addBlocks() {
        Instructions ins;
        byte[] instruction;
        byte[] aux;
        for (int i = 1; i < blocks.size(); i++) {
            ///ini
            ins = searchInstructions(blocks.get(i).getIni());
            instruction = ins.getByteInstructions();
            aux = new byte[instruction.length+2];
            if(blocks.get(i) instanceof Loop){
                aux[0] = 0x03;
            }else {
                aux[0] = 0x02;
            }
            aux[1] = 0x40;
            System.arraycopy(instruction,0,aux,2,instruction.length);
            ins.setByteInstructions(aux);
            ///end
            ins = searchInstructions(blocks.get(i).getEnd());
            instruction = ins.getByteInstructions();
            aux = new byte[instruction.length+1];
            System.arraycopy(instruction,0,aux,0,instruction.length);
            aux[instruction.length] = 0x0b;
            ins.setByteInstructions(aux);
        }
    }
    private Instructions searchInstructions(int instPos){
        for (int i = 0; i < instructions.size(); i++) {
            if(instructions.get(i).getPos()==instPos){
                return instructions.get(i);
            }
        }
        return null;
    }
}
