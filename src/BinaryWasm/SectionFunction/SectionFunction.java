package BinaryWasm.SectionFunction;
import BinaryWasm.SectionType.ISectionType;

public class SectionFunction implements ISectionFunction {

    private byte[] bytesSection;
    private int pos;

    public SectionFunction(int nFunc) {
        bytesSection = new byte[nFunc+3];
        bytesSection[0] = 0x03;
        bytesSection[1] = (byte)(nFunc+1);
        bytesSection[2] = (byte)(nFunc);
        pos = 3;
    }

    @Override
    public byte[] getSection(){
        return bytesSection;
    }


    @Override
    public void addFunction(int indexType){
        bytesSection[pos] = (byte)indexType;
        pos++;
    }
}
