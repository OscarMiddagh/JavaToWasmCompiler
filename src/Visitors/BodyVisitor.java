package Visitors;

import Leb128.SignedLeb128;
import Leb128.UnsignedLeb128;
import Sections.Code.*;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.LocalVariableTable;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.*;
import java.util.ArrayList;

public class BodyVisitor extends EmptyVisitor implements IVisitor {
    private ConstantPoolGen cpg;
    private Method[] methods;
    private Field[] fields;
    private ArrayList<Instructions> instructions;
    private ArrayList<IBlock> blocks;
    private IBody body;
    private int posInstruction;
    private MethodGen mg;


    public BodyVisitor(Method[] methods, Field[] fields, ConstantPoolGen cpg){
        this.cpg = cpg;
        this.methods = methods;
        this.fields = fields;
    }
    @Override
    public IBody createBody(MethodGen mg) {
        this.mg = mg;
        InstructionList ihs = mg.getInstructionList();
        InstructionHandle iHandle = ihs.getStart();
        InstructionHandle iHandleEnd = ihs.getEnd();
        int posFinal = iHandleEnd.getPosition();
        int posIni = iHandle.getPosition();
        instructions = new ArrayList<>();
        blocks = new BlocksCreator(ihs).getBlocks();
        posInstruction = iHandle.getPosition();
        while (posInstruction != posFinal){
            posInstruction = iHandle.getPosition();
            iHandle.accept(this);
            iHandle = iHandle.getNext();
        }
        return new Body(getLocalTypeLocalDeclarations(mg),instructions,blocks);
    }
    public void visitALOAD(ALOAD obj) {
        instructions.add(new Instructions(posInstruction, new  byte[]{}));
    }
    private ArrayList<TypeLocalsVariables> getLocalTypeLocalDeclarations(MethodGen mg) {
        LocalVariableGen[] lgv = mg.getLocalVariables();
        ArrayList<TypeLocalsVariables> res = new ArrayList<>();
        byte actual = 0;
        byte type = 0;
        for (int i = mg.getArgumentNames().length+1; i < lgv.length; i++) {
            if(actual != lgv[i].getType().getType()){
                actual = lgv[i].getType().getType();
                switch (actual){
                    case 4:
                    case 10:
                        type = 0x7f;
                        break;
                    case 6:
                        type = 0x7d;
                        break;
                    case 7:
                        type = 0x7c;
                        break;
                    case 11:
                        type = 0x7e;
                        break;
                    default:
                        break;
                }
                res.add(new TypeLocalsVariables(type));
            }
            else{
                res.get(res.size()-1).typesPP();
            }
        }
        return res;
    }
    private int calcularProfundidadSalto(int salto){
        int profundidad = -1;
        for (int i = 0; i < blocks.size(); i++) {
            if(blocks.get(i).getIni()<=posInstruction && blocks.get(i).getEnd()> posInstruction){
                if(blocks.get(i).getEnd()<=salto){
                    profundidad++;
                }
            }
        }
        return profundidad;
    }
    @Override
    public void visitBIPUSH(BIPUSH bipush){
        int x = bipush.getValue().intValue();
        byte[] aux;
        if(x<0){
            aux  =  new SignedLeb128().writeSignedLeb128(x);
        } else if(x>=64 && x<128) {
            aux = new byte[]{(byte)(x-128),0x00};
        }else {
            aux = new UnsignedLeb128().writeUnsignedLeb128(x);
        }
        byte[] val = new byte[aux.length+1];
        val[0] = 0x41;
        System.arraycopy(aux,0,val,1,aux.length);
        instructions.add(new Instructions(posInstruction, val));
    }
    @Override
    public void visitSIPUSH(SIPUSH sipush){
        int x = sipush.getValue().intValue();
        byte[] aux;
        if(x<0){
            aux  =  new SignedLeb128().writeSignedLeb128(x);
        } else if(x>=64 && x<128) {
            aux = new byte[]{(byte)(x-128),0x00};
        }else {
            aux = new UnsignedLeb128().writeUnsignedLeb128(x);
        }
        byte[] val = new byte[aux.length+1];
        val[0] = 0x41;
        System.arraycopy(aux,0,val,1,aux.length);
        instructions.add(new Instructions(posInstruction, val));
    }
    @Override
    public void visitReturnInstruction(ReturnInstruction returnInstruction) {
        instructions.add(new Instructions(posInstruction,new byte[]{0x0f}));
    }
    @Override
    public void visitIF_ICMPEQ(IF_ICMPEQ if_icmpeq) {

        instructions.add(new Instructions(posInstruction,new byte[]{0x46,0x0d,(byte)calcularProfundidadSalto(if_icmpeq.getTarget().getPosition())}));
    }
    @Override
    public void visitIF_ICMPNE(IF_ICMPNE if_icmpne) {
        instructions.add(new Instructions(posInstruction, new byte[]{0x47,0x0d,(byte)calcularProfundidadSalto(if_icmpne.getTarget().getPosition())}));
    }
    @Override
    public void visitIF_ICMPLT(IF_ICMPLT if_icmplt) {
        instructions.add(new Instructions(posInstruction, new byte[]{0x48,0x0d,(byte)calcularProfundidadSalto(if_icmplt.getTarget().getPosition())}));
    }
    @Override
    public void visitIF_ICMPLE(IF_ICMPLE if_icmple) {
        instructions.add(new Instructions(posInstruction, new byte[]{0x4c,0x0d,(byte)calcularProfundidadSalto(if_icmple.getTarget().getPosition())}));
    }
    @Override
    public void visitIF_ICMPGT(IF_ICMPGT if_icmpgt) {
        instructions.add(new Instructions(posInstruction, new byte[]{0x4a,0x0d,(byte)calcularProfundidadSalto(if_icmpgt.getTarget().getPosition())}));
    }
    @Override
    public void visitIF_ICMPGE(IF_ICMPGE if_icmpge) {
        instructions.add(new Instructions(posInstruction, new byte[]{0x4e,0x0d,(byte)calcularProfundidadSalto(if_icmpge.getTarget().getPosition())}));
    }
    @Override
    public void visitIFLT(IFLT iflt) {
        instructions.add(new Instructions(posInstruction, new byte[]{0x41,0x00,0x48,0x0d,(byte)calcularProfundidadSalto(iflt.getTarget().getPosition())}));
    }
    @Override
    public void visitIFLE(IFLE ifle) {
        instructions.add(new Instructions(posInstruction, new byte[]{0x41,0x00,0x4c,0x0d,(byte)calcularProfundidadSalto(ifle.getTarget().getPosition())}));
    }
    @Override
    public void visitIFGT(IFGT ifgt) {
        instructions.add(new Instructions(posInstruction, new byte[]{0x41,0x00,0x4a,0x0d,(byte)calcularProfundidadSalto(ifgt.getTarget().getPosition())}));
    }
    @Override
    public void visitIFGE(IFGE ifge) {
        instructions.add(new Instructions(posInstruction, new byte[]{0x41,0x00,0x4e,0x0d,(byte)calcularProfundidadSalto(ifge.getTarget().getPosition())}));
    }
    @Override
    public void visitIFEQ(IFEQ ifeq) {
        instructions.add(new Instructions(posInstruction, new byte[]{0x45,0x0d,(byte)calcularProfundidadSalto(ifeq.getTarget().getPosition())}));
    }
    @Override
    public void visitIFNE(IFNE ifne) {
        instructions.add(new Instructions(posInstruction,new byte[]{0x41,0x00,0x47,0x0d,(byte)calcularProfundidadSalto(ifne.getTarget().getPosition())}));
    }
    @Override
    public void visitISTORE(ISTORE istore) {

        if(istore.getIndex()+2>mg.getLocalVariables().length){
            mg.addLocalVariable("istore"+istore.getIndex(), BasicType.getType((byte) 10),null,null);
        }
        instructions.add(new Instructions(posInstruction, new byte[]{0x21,(byte)(istore.getIndex()-1)}));
    }
    @Override
    public void visitIAND(IAND iand) {
        instructions.add(new Instructions(posInstruction, new byte[]{0x71}));
    }
    @Override
    public void visitILOAD(ILOAD iload) {
        int index = iload.getIndex();
        instructions.add(new Instructions(posInstruction, new byte[]{0x20,(byte)(iload.getIndex()-1)}));
    }
    @Override
    public void visitIINC(IINC iinc) {
        instructions.add(new Instructions(posInstruction, new byte[]{0x20,(byte)(iinc.getIndex()-1),0x41,(byte)iinc.getIncrement(),0x6a,0x21,(byte)(iinc.getIndex()-1)}));
    }

    @Override
    public void visitIDIV(IDIV idiv) {
        instructions.add(new Instructions(posInstruction, new byte[]{0x6d}));
    }

    @Override
    public void visitIOR(IOR ior) {
        instructions.add(new Instructions(posInstruction,new byte[]{0x72}));
        //instructions[instPos] = new byte[]{0x72};
    }

    @Override
    public void visitIREM(IREM irem) {
        instructions.add(new Instructions(posInstruction, new byte[]{0x6f}));
    }

    @Override
    public void visitINVOKEVIRTUAL(INVOKEVIRTUAL invokevirtual) {
        instructions.add(new Instructions(posInstruction, new byte[]{0x10,(byte)searchIndexMethod(invokevirtual.getName(cpg)+invokevirtual.getSignature(cpg))}));
    }

    @Override
    public void visitGETFIELD(GETFIELD obj){

        int i = 0;
        String met = fields[0].getName()+fields[0].getSignature();
        String met0 = obj.getName(cpg)+obj.getSignature(cpg);
        while (i<fields.length&&met.equals(met0)){
            met = fields[i].getName()+fields[i].getSignature();
            i++;
        }
        instructions.add(new Instructions(posInstruction, new byte[]{0x23,(byte)i}));
    }

    @Override
    public void visitAALOAD(AALOAD aaload) {
        instructions.add(new Instructions(posInstruction, new  byte[]{}));
    }
    @Override
    public void visitGOTO(GOTO aGoto) {
        byte salto;
        if(posInstruction<aGoto.getTarget().getPosition()){
            salto = (byte)calcularProfundidadSalto(aGoto.getTarget().getPosition());
        }else {
            salto = 0x00;
        }
        instructions.add(new Instructions(posInstruction,new byte[]{0x0c,salto}));
    }

    @Override
    public void visitISUB(ISUB isub) {
        instructions.add(new Instructions(posInstruction, new byte[]{0x6b}));
    }
    @Override
    public void visitICONST(ICONST iconst) {
        instructions.add(new Instructions(posInstruction, new byte[]{0x41,(byte)iconst.getValue().intValue()}));
    }
    @Override
    public void visitIMUL(IMUL imul) {
        instructions.add(new Instructions(posInstruction, new byte[]{0x6c}));
    }

    @Override
    public void visitIADD(IADD iadd) {
        instructions.add(new Instructions(posInstruction, new byte[]{0x6a}));
    }
    public int searchIndexMethod(String met){
        for (int i = 1; i < methods.length; i++) {
            if(met.equals(methods[i].getName()+methods[i].getSignature())){
                return i-1;
            }
        }
        return -1;
    }

}
