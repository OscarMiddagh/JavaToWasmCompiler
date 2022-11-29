package BinaryWasm.SectionExport;

import ElementsWasm.Export.IExport;
import Leb128.Leb128;

public class SectionExport implements ISectionExport {

    private byte[] bytesSection;
    private byte[] aux;
    private int sizeSection;
    private Leb128 ul;
    public SectionExport() {
        sizeSection =1;
        bytesSection = new byte[]{0x07,0x01,0x00};
        ul =  new Leb128();
    }
    @Override
    public byte[] getSection(){
        return bytesSection;
    }



    @Override
    public void addExport(IExport export) {
        byte[] sizeAct;
        int lengthArraySizePrev;
        lengthArraySizePrev = ul.writeUnsignedLeb128(sizeSection).length;
        sizeSection = sizeSection+export.getBytesElement().length;
        sizeAct = ul.writeUnsignedLeb128(sizeSection);
        aux = bytesSection;
        bytesSection = new byte[aux.length+export.getBytesElement().length+(sizeAct.length-lengthArraySizePrev)];
        bytesSection[0] = 0x07;
        System.arraycopy(sizeAct,0,bytesSection,1,sizeAct.length);
        System.arraycopy(aux,lengthArraySizePrev+1,bytesSection,sizeAct.length+1,aux.length-(lengthArraySizePrev+1));
        System.arraycopy(export.getBytesElement(),0,bytesSection,aux.length+sizeAct.length-lengthArraySizePrev,export.getBytesElement().length);
        bytesSection[sizeAct.length+1]++;
    }
}
