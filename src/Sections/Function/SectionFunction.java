package Sections.Function;

import Sections.Type.IType;
import Sections.Type.Type;
import Sections.Export.ISectionExport;
import Sections.Type.ISectionType;

import java.util.ArrayList;

public class SectionFunction implements ISectionFunction {

    private ISectionType sectionType;
    private byte[] bytesSection;
    private int pos;

    public SectionFunction(ISectionType sectionType,int nFunc) {
        this.sectionType = sectionType;
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
    public void addFunction(String signature) {
        IType type = new Type(signature);
        if(!sectionType.containsType(type)){
            sectionType.addType(type);
        }
        bytesSection[pos] = (byte)sectionType.indexOfType(type);
        pos++;
    }

}
