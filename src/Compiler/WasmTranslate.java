package Compiler;

import BinaryWasm.BinaryFormatWasm;
import BinaryWasm.SectionCode.Body;
import BinaryWasm.SectionCode.IBody;
import BinaryWasm.SectionCode.ISectionCode;
import BinaryWasm.SectionCode.SectionCode;
import BinaryWasm.ISection;
import BinaryWasm.SectionCustom.SectionCustom;
import BinaryWasm.SectionExport.Export;
import BinaryWasm.SectionExport.ISectionExport;
import BinaryWasm.SectionExport.SectionExport;
import BinaryWasm.SectionFunction.ISectionFunction;
import BinaryWasm.SectionFunction.SectionFunction;
import BinaryWasm.SectionGlobal.Global;
import BinaryWasm.SectionGlobal.ISectionGlobal;
import BinaryWasm.SectionGlobal.SectionGlobal;
import BinaryWasm.SectionType.ISectionType;
import BinaryWasm.SectionType.SectionType;
import BinaryWasm.SectionType.Type;
import ElementsWasm.Function.Function;
import Visitors.BodyVisitor;
import Visitors.IVisitor;
import org.apache.bcel.classfile.*;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.FieldGen;
import org.apache.bcel.generic.MethodGen;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class WasmTranslate implements Compiler{
    private JavaClass javaClass;
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

    private String name;

    public WasmTranslate(String arg) throws IOException {
        javaClass = new ClassParser(arg).parse();
        class_name = javaClass.getClassName();
        int index = class_name.lastIndexOf('.');
        String path = class_name.substring(0, index + 1).replace('.', File.separatorChar);
        class_name = class_name.substring(index + 1);
        if (!path.equals("")) {
            File f = new File(path);
            f.mkdirs();
        }
        name = path + class_name + ".wasm";
        out = new DataOutputStream(new FileOutputStream(name));

        cp = new ConstantPoolGen(javaClass.getConstantPool());
        methods = javaClass.getMethods();
        fields = javaClass.getFields();
        sectionCustom = new SectionCustom();
        sectionType =  new SectionType();
        sectionGlobal = new SectionGlobal();
        sectionExport = new SectionExport();
        sectionFunction = new SectionFunction(sectionType,methods.length-1);
        sectionCode = new SectionCode(methods.length-1);
        bodyVisitor = new BodyVisitor(methods,fields,cp);
    }
    @Override
    public void compile() throws IOException {
        out.write(new BinaryFormatWasm(extractDataMethods1(),extractDataFields1()).getBinaryFormatWasm());
        extractDataMethods();
        extractDataFields();
        printCode();

    }

    private ArrayList<Global> extractDataFields1() {
        return null;
    }

    private ArrayList<Function> extractDataMethods1(){

        MethodGen mg;
        ArrayList<Function> functions = new ArrayList<>();
        Function function;
        Type type;
        IBody body;
        for (int i = 1; i < methods.length; i++) {
            mg = new MethodGen(methods[i], class_name, cp);
            type = new Type(new byte[0],new byte[0]);
            body = bodyVisitor.createBody(mg);
            function = new Function(type,body);
            functions.add(function);
        }



        for (int i = 1; i < methods.length; i++) {
            sectionFunction.addFunction(methods[i].getSignature().replaceAll("Z","I"));
            if(methods[i].isPublic()){
                sectionExport.addExport(new Export(methods[i].getName(),0,i-1));
            }
            mg = new MethodGen(methods[i], class_name, cp);

            sectionCode.addBody(bodyVisitor.createBody(mg));
        }
        return null;
    }
    private void extractDataMethods(){
        MethodGen mg;
        for (int i = 1; i < methods.length; i++) {
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
        }
    }
    private void printCode() throws IOException {

        out.write(sectionCustom.getSection());
        out.write(sectionType.getSection());
        out.write(sectionFunction.getSection());
        out.write(sectionGlobal.getSection());
        out.write(sectionExport.getSection());
        out.write(sectionCode.getSection());
        out.close();
        System.out.println("Archivo " + name+" generado.");
    }
}
