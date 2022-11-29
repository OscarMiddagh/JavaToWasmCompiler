package BinaryWasm.SectionCode;

import ElementsWasm.Body.Body;
import ElementsWasm.Body.IBody;
import Leb128.Leb128;
import BinaryWasm.ISection;

public class SectionCode implements ISectionCode {

    private byte[] bytesSection;
    private byte[] aux;
    private int sizeSection;
    private Leb128 ul;

    public SectionCode() {
        sizeSection = 1;
        bytesSection = new byte[]{0x0a,(byte)sizeSection,0x00};
        ul = new Leb128();
    }

    @Override
    public byte[] getSection(){
        return bytesSection;
    }
    @Override
    public void addBody(IBody body) {
        byte[] sizeAct;
        int lengthArraySizePrev;
        lengthArraySizePrev = ul.writeUnsignedLeb128(sizeSection).length;
        sizeSection = sizeSection+body.getBytesElement().length;
        sizeAct = ul.writeUnsignedLeb128(sizeSection);
        aux = bytesSection;
        bytesSection = new byte[aux.length+body.getBytesElement().length+(sizeAct.length-lengthArraySizePrev)];
        bytesSection[0] = 0x0a;
        System.arraycopy(sizeAct,0,bytesSection,1,sizeAct.length);
        System.arraycopy(aux,lengthArraySizePrev+1,bytesSection,sizeAct.length+1,aux.length-(lengthArraySizePrev+1));
        System.arraycopy(body.getBytesElement(),0,bytesSection,aux.length+sizeAct.length-lengthArraySizePrev,body.getBytesElement().length);
        bytesSection[sizeAct.length+1]++;
    }

}
