package Sections.Code;

import Leb128.UnsignedLeb128;
import Sections.ISection;

import java.io.ByteArrayOutputStream;

public class SectionCode implements ISection, ISectionCode {

    private Body[] bodies;

    private byte[] byteSection;
    private byte[] aux;
    private int sizeSection;
    private UnsignedLeb128 ul;
    public SectionCode(int nBodies) {
        bodies = new Body[nBodies];
        byteSection = new byte[]{0x0a,0x01,(byte)(nBodies)};
        ul = new UnsignedLeb128();
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
        sizeSection = (int)(byteSection[1])+body.getBytesElement().length;
        byteSection[1] =(byte)sizeSection;
    }

    @Override
    public boolean containsBody(IBody body) {
        return false;
    }
    @Override
    public int indexOfElement(IBody body) {
        return 0;
    }
}
