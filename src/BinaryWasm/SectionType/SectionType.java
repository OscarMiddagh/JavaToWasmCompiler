package BinaryWasm.SectionType;

import java.util.ArrayList;

public class SectionType implements ISectionType {
    private int nTypes;
    private ArrayList<IType> types;
    private byte[] bytesSection;
    private byte[] aux;

    public SectionType(){
        bytesSection = new byte[]{0x01,0x01,0x00};
        types = new ArrayList<>();
    }
    @Override
    public byte[] getSection(){
        return bytesSection;
    }

    @Override
    public void addType(IType type) {
        bytesSection[1] = (byte)(type.typeLength() + bytesSection[1]);
        bytesSection[2] = (byte)(bytesSection[2]+1);
        aux = bytesSection;
        bytesSection = new byte[aux.length+type.typeLength()];
        System.arraycopy(aux,0,bytesSection,0,aux.length);
        System.arraycopy(type.getBytesElement(),0,bytesSection,aux.length,type.getBytesElement().length);
        types.add(type);
    }

    @Override
    public boolean containsType(IType type) {
        return types.contains(type);
    }

    @Override
    public int indexOfType(IType type) {
        return types.indexOf(type);
    }

    @Override
    public IType getType(int n) {
        return types.get(n);
    }
}
