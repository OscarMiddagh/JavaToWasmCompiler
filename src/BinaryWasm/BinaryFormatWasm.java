package BinaryWasm;

import BinaryWasm.SectionCode.ISectionCode;
import BinaryWasm.SectionCode.SectionCode;
import BinaryWasm.SectionCustom.SectionCustom;
import BinaryWasm.SectionExport.ISectionExport;
import BinaryWasm.SectionExport.SectionExport;
import BinaryWasm.SectionFunction.SectionFunction;
import BinaryWasm.SectionGlobal.SectionGlobal;
import ElementsWasm.Function.IFunction;
import ElementsWasm.Global.IGlobal;
import ElementsWasm.Type.IType;
import BinaryWasm.SectionType.SectionType;
import BinaryWasm.SectionFunction.ISectionFunction;
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
    private byte[] binaryFormatWasm;
    private ArrayList<IFunction> functions;
    private ArrayList<IGlobal> globals;

    public BinaryFormatWasm(ArrayList<IFunction> functions, ArrayList<IGlobal> globals){
        sectionCustom = new SectionCustom();
        sectionType = new SectionType();
        sectionFunction = new SectionFunction();
        sectionExport = new SectionExport();
        sectionGlobal = new SectionGlobal();
        sectionCode = new SectionCode();

        sections = new ISection[]{  sectionCustom,
                                    sectionType,
                                    sectionFunction,
                                    sectionGlobal,
                                    sectionExport,
                                    sectionCode};
        binaryFormatWasm = new byte[0];
        this.functions = functions;
        this.globals = globals;
        constructFunctionDependentSections();
        constructSectionGlobal();
        constructBinaryFormatWasm();
    }

    private void constructFunctionDependentSections() {
        IFunction function;
        IType type;
        for (int i = 0; i < functions.size(); i++) {
            function = functions.get(i);
            type = function.getType();
            sectionType.addType(type);
            sectionFunction.addFunction(sectionType.indexOfType(type));
            if(function.isPublic()){
                sectionExport.addExport(function.getExport());
            }
            sectionCode.addBody(function.getBody());
        }
    }

    private void constructSectionGlobal() {
        IGlobal global;
        for (int i = 0; i < globals.size(); i++) {
            global = globals.get(i);
            sectionGlobal.addGlobal(global);
            if(global.isPublic()){
                sectionExport.addExport(globals.get(i).getExport());
            }
        }
    }



    private void constructBinaryFormatWasm(){
        byte[] aux;
        byte[] section;
        for(int i = 0; i<sections.length; i++){
            aux = binaryFormatWasm;
            section = sections[i].getSection();
            binaryFormatWasm = new byte[aux.length+section.length];
            System.arraycopy(aux,0,binaryFormatWasm,0,aux.length);
            System.arraycopy(section,0,binaryFormatWasm,aux.length,section.length);
        }
    }
    @Override
    public byte[] getBinaryFormatWasm() {
        return binaryFormatWasm;
    }
}
