package BinaryWasm.SectionFunction;
public class SectionFunction implements ISectionFunction {

    private byte[] bytesSection;
    private byte[] aux;

    public SectionFunction() {
        bytesSection = new byte[]{0x03,0x01,0x00};
    }

    @Override
    public byte[] getSection(){
        return bytesSection;
    }


    @Override
    public void addFunction(int indexType){
        aux = bytesSection;
        bytesSection = new byte[aux.length+1];
        System.arraycopy(aux,0,bytesSection,0,aux.length);
        bytesSection[bytesSection.length-1] = (byte)indexType;
        bytesSection[1]++;
        bytesSection[2]++;
    }
}
