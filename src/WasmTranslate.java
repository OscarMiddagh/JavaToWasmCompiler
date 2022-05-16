import Sections.*;
import Sections.Code.ISectionCode;
import Sections.Code.SectionCode;
import Sections.Export.Export;
import Sections.Export.ISectionExport;
import Sections.Export.SectionExport;
import Sections.Function.ISectionFunction;
import Sections.Function.SectionFunction;
import Sections.Global.Global;
import Sections.Global.ISectionGlobal;
import Sections.Global.SectionGlobal;
import Sections.Type.ISectionType;
import Sections.Type.SectionType;
import Visitors.BodyVisitor;
import Visitors.IVisitor;
import org.apache.bcel.classfile.*;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.FieldGen;
import org.apache.bcel.generic.MethodGen;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class WasmTranslate {
    private String class_name;
    private ConstantPoolGen cp;
    private DataOutputStream out;
    private Method[] methods;
    private Field[] fields;
    private ISection sectionCustom;
    private ISectionType sectionType;
    private ISectionFunction sectionFunction;
    private ISectionGlobal sectionGlobal;
    private ISectionExport sectionExport;
    private ISectionCode sectionCode;
    private IVisitor bodyVisitor;

    public WasmTranslate(JavaClass clazz, FileOutputStream out){
        this.out = new DataOutputStream(out);
        class_name = clazz.getClassName();
        cp = new ConstantPoolGen(clazz.getConstantPool());
        methods = clazz.getMethods();
        fields = clazz.getFields();
        sectionCustom = new SectionCustom();
        sectionType =  new SectionType();
        sectionGlobal = new SectionGlobal();
        sectionExport = new SectionExport();
        sectionFunction = new SectionFunction(sectionType,methods.length-1);
        sectionCode = new SectionCode(methods.length-1);
        bodyVisitor = new BodyVisitor(methods,fields,cp);
    }
    public void compile() throws IOException {
        extractDataMethods();
        extractDataFields();
        printCode();

    }
    private void extractDataMethods(){
        MethodGen mg;
        LocalVariableTable lv;
        for (int i = 1; i < methods.length; i++) {

            lv = methods[i].getLocalVariableTable();
            sectionFunction.addFunction(methods[i].getSignature().replaceAll("Z","I"));
            if(methods[i].isPublic()){
                sectionExport.addExport(new Export(methods[i].getName(),0,i-1));
            }
            mg = new MethodGen(methods[i], class_name, cp);
            sectionCode.addBody(bodyVisitor.createBody(mg));
        }
    }
    private void extractDataFields(){
        FieldGen fieldGen;
        int mutability;

        for (int i = 0; i < fields.length; i++) {
            fieldGen = new FieldGen(fields[i],cp);
            mutability = (fieldGen.isStatic())?0:1;
            sectionGlobal.addGlobal(new Global(fields[i].getType().getSignature(),mutability,fieldGen.getInitValue()));
            if(fields[i].isPublic()){
                sectionExport.addExport(new Export(fields[i].getName(),3,i));
            }
            System.out.println(fields[i].getType().getSignature());
            System.out.println(fieldGen.getInitValue());

        }
    }
    private void printCode() throws IOException {
        out.write(sectionCustom.getSection());
        out.write(sectionType.getSection());
        out.write(sectionFunction.getSection());
        //out.write(sectionGlobal.getSection());
        out.write(sectionExport.getSection());
        out.write(sectionCode.getSection());
        out.close();
    }
}
