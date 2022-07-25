package BinaryWasm.SectionCode;

import ElementsWasm.Body.Body;
import ElementsWasm.Body.IBody;
import Leb128.Leb128;
import BinaryWasm.ISection;

public class SectionCode implements ISection, ISectionCode {

    private byte[] byteSection;
    private byte[] aux;
    private int sizeSection;
    private Leb128 ul;

    public SectionCode() {
        byteSection = new byte[]{0x0a,0x01,0x00};
        ul = new Leb128();
        sizeSection = 1;
    }

    @Override
    public byte[] getSection(){
            byte[] size = ul.writeUnsignedLeb128(sizeSection);
            aux = byteSection;
            byteSection = new byte[aux.length-1+size.length];
            byteSection[0] = 0xa;
            System.arraycopy(size,0,byteSection,1,size.length);
            System.arraycopy(aux,2,byteSection,size.length+1,aux.length-2);
        return byteSection;
    }
    @Override
    public void addBody(IBody body) {
        aux = byteSection;
        byteSection = new byte[aux.length+body.getBytesElement().length];
        System.arraycopy(aux,0,byteSection,0,aux.length);
        System.arraycopy(body.getBytesElement(),0,byteSection,aux.length,body.getBytesElement().length);
        sizeSection = sizeSection+body.getBytesElement().length;
        byteSection[2] = (byte) (byteSection[2]+1);
    }
}
