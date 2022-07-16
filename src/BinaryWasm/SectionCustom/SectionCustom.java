package BinaryWasm.SectionCustom;

import BinaryWasm.ISection;

public class SectionCustom implements ISection {
    @Override
    public byte[] getSection() {
        return new byte[]{0x00,0x61,0x73,0x6D,   //magicNumber
                          0x01,0x00,0x00,0x00}; //version
    }
}
