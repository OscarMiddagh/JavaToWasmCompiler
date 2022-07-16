package Visitors;

import BinaryWasm.SectionCode.Block;
import BinaryWasm.SectionCode.IBlock;
import BinaryWasm.SectionCode.Loop;
import org.apache.bcel.generic.*;

import java.util.ArrayList;

public class BlocksCreator {

    private ArrayList<IBlock> blocks;
    private InstructionList ihs;

    public BlocksCreator(InstructionList ihs){
        this.ihs = ihs;
        blocks = new ArrayList<>();
        blocks.add(new Block(ihs.getStart().getPosition(),ihs.getEnd().getPosition()));//Anadiendo el bloque inicial
        createBlocks();
    }

    private void createBlocks() {
        //InstructionHandle iHandle = ihs.getStart();
        IBlock block;
        Instruction inst;
        int inicio;
        int dest;
        for(InstructionHandle iHandle : ihs) {
            inst = iHandle.getInstruction();
            if (inst instanceof IfInstruction) {
                dest = ((IfInstruction) inst).getTarget().getPrev().getPosition();

                if(inst instanceof IFEQ || inst instanceof IFNE || inst instanceof IFLT || inst instanceof IFGE
                        || inst instanceof IFGT || inst instanceof IFLE || inst instanceof IFNONNULL || inst instanceof IFNULL){

                    inicio = calcularInicioBloque(iHandle,dest,1);
                }else {
                    inicio = calcularInicioBloque(iHandle,dest,2);
                }
                block = new Block(inicio,dest);
                if(!blocks.contains(block)){
                    blocks.add(block);
                }
            }else if(inst instanceof GOTO){
                if(((GOTO) inst).getTarget().getPosition()<iHandle.getPosition() ){
                    int salto = ((GOTO) inst).getTarget().getPosition();
                    block = new Block(salto,iHandle.getPosition());
                    int n = blocks.indexOf(block);
                    blocks.set(blocks.indexOf(block),new Loop(salto,iHandle.getPosition()));
                    blocks.add(block);
                }else {
                    int salto = ((GOTO) inst).getTarget().getPosition();
                    block = buscarBloque(salto);
                    block = new Block(block.getIni(),((GOTO) inst).getTarget().getPrev().getPosition());
                    if(!blocks.contains(block)){
                        blocks.add(block);
                    }
                }
            }
        }
    }

    public ArrayList<IBlock> getBlocks(){
        return blocks;
    }

    private int calcularInicioBloque(InstructionHandle iHandle, int dest, int n) {
        Instruction instruction;
        int pos;
        while (n!=0){
            iHandle = iHandle.getPrev();
            instruction = iHandle.getInstruction();
            if (instruction instanceof ArithmeticInstruction || instruction instanceof INVOKEVIRTUAL|| instruction instanceof GETFIELD){
                n = n +2;
            }
            n--;
        }
        pos = iHandle.getPosition();
        for (int i = 0; i < blocks.size(); i++) {
            if(pos<blocks.get(i).getEnd() &&  dest> blocks.get(i).getEnd()){
                pos = blocks.get(i).getIni();
            }
        }
        return  pos;
    }
    private IBlock buscarBloque(int salto) {
        int dif = 0;
        int nuevaDif = 0;
        int element = 0;
        for(int i = 0; i<blocks.size();i++){
            if(blocks.get(i).getEnd()>salto){
                nuevaDif = blocks.get(i).getEnd() - salto;
                if(dif<=nuevaDif){
                    element = i;
                    dif = nuevaDif;
                }
            }
        }
        return blocks.get(element);
    }

}
