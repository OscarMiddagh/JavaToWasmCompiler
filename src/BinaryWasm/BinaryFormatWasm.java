package BinaryWasm;

import BinaryWasm.SectionCode.ISectionCode;
import BinaryWasm.SectionCode.SectionCode;
import BinaryWasm.SectionCustom.SectionCustom;
import BinaryWasm.SectionExport.ISectionExport;
import BinaryWasm.SectionExport.SectionExport;
import BinaryWasm.SectionFunction.SectionFunction;
import BinaryWasm.SectionGlobal.SectionGlobal;
import BinaryWasm.SectionType.SectionType;
import ElementsWasm.Function.Function;
import BinaryWasm.SectionFunction.ISectionFunction;
import BinaryWasm.SectionGlobal.Global;
import BinaryWasm.SectionGlobal.ISectionGlobal;
import BinaryWasm.SectionType.ISectionType;

import java.util.ArrayList;

public class BinaryFormatWasm implements IBinaryFormatWasm{
    private ISection sectionCustom;
    private ISectionType sectionType;
    private ISectionFunction sectionFunction;
    private ISectionGlobal sectionGlobal;
    private ISectionExport sectionExport;
    private ISectionCode sectionCode;

    private ISection[] sections;
    private byte[] sectionsBinary;

    public BinaryFormatWasm(ArrayList<Function> functions,ArrayList<Global> globals){
        sectionFunction = new SectionFunction(functions);
        sectionCustom = new SectionCustom();
        sectionType = new SectionType();
        sectionExport = new SectionExport();
        sectionCode = new SectionCode(functions.size());

        sectionGlobal = new SectionGlobal();

        sections = new ISection[]{  sectionCustom,
                                    sectionType,
                                    sectionFunction,
                                    sectionGlobal,
                                    sectionExport,
                                    sectionCode};
        sectionsBinary = new byte[0];
        constructBinaryFormatWasm();
    }
    private void constructBinaryFormatWasm(){
        for(int i = 0; i<sections.length; i++){
                sections[i].getSection();
        }
    }
    @Override
    public byte[] getBinaryFormatWasm() {
        return sectionsBinary;
    }
}
