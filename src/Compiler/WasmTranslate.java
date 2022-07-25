package Compiler;

import BinaryWasm.BinaryFormatWasm;
import BinaryWasm.IBinaryFormatWasm;
import ElementsWasm.Body.IBody;
import ElementsWasm.Export.Export;
import ElementsWasm.Export.IExport;
import ElementsWasm.Function.IFunction;
import ElementsWasm.Global.Global;
import ElementsWasm.Global.IGlobal;
import ElementsWasm.Type.IType;
import ElementsWasm.Type.Type;
import ElementsWasm.Function.Function;
import Visitors.BodyVisitor;
import Visitors.IBodyVisitor;
import org.apache.bcel.classfile.*;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.FieldGen;
import org.apache.bcel.generic.MethodGen;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class WasmTranslate implements WASMCompiler{
    private JavaClass javaClass;
    private String class_name;
    private ConstantPoolGen cp;
    private DataOutputStream out;
    private Method[] methods;
    private Field[] fields;
    private IBodyVisitor bodyVisitor;
    private IBinaryFormatWasm binaryFormatWasm;
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

        bodyVisitor = new BodyVisitor(methods,fields,cp);
    }
    @Override
    public void compile() throws IOException {
        binaryFormatWasm = new BinaryFormatWasm(extractDataMethods(),extractDataFields());
        out.write(binaryFormatWasm.getBinaryFormatWasm());
        out.close();
        System.out.println("Archivo " + name+" generado.");
    }


    private ArrayList<IFunction> extractDataMethods(){
        MethodGen mg;
        ArrayList<IFunction> functions = new ArrayList<>();
        IFunction function;
        IType type;
        IBody body;
        IExport export;
        String signature;
        for (int i = 1; i < methods.length; i++) {
            mg = new MethodGen(methods[i], class_name, cp);
            signature = mg.getSignature();
            type = new Type(transformParameters(signature),transformResult(signature.charAt(signature.length()-1)));
            body = bodyVisitor.createBody(mg);
            function = new Function(type,body);
            if(methods[i].isPublic()){
                export = new Export(methods[i].getName(),0,i-1);
                function.setExport(export);
            }
            functions.add(function);
        }
        return functions;
    }

    private ArrayList<IGlobal> extractDataFields() {
        FieldGen fieldGen;
        ArrayList<IGlobal> globals = new ArrayList<>();
        IExport export;
        IGlobal global;
        int mutability;
        char typeGlobal;
        for (int i = 0; i < fields.length; i++) {
            fieldGen = new FieldGen(fields[i],cp);
            mutability = (fieldGen.isStatic())?0:1;
            typeGlobal = fields[i].getType().getSignature().charAt(0);
            global = new Global(readChar(typeGlobal),mutability,fieldGen.getInitValue());
            if(fields[i].isPublic()){
                export = new Export(fields[i].getName(),3,i);
                global.setExport(export);
            }
            globals.add(global);
        }
        return globals;
    }
    private byte[] transformResult(char result) {
        byte res = readChar(result);
        return res==0x01?new byte[0]:new byte[]{res};
    }

    private byte[] transformParameters(String signature){
        char parameter;
        byte[] parameters = new byte[signature.length()-3];
        for (int i = 1; i < signature.length()-2; i++) {
            parameter = signature.charAt(i);
            parameters[i-1] = readChar(parameter);
        }
        return parameters;
    }
    private byte readChar(char c){
        byte result = 0x00;
        switch (c){
            case 'I': //int
            case 'Z': //boolean
                result= 0x7F;
                break;
            case 'V':
                result = 0x01; //Usaremos 1 por conveniencia, ya que no hay una traduccion en WASM
                break;
            case 'B': //byte
                break;
            case 'C': //char
                break;
            case 'D': //double
                result = 0x7C;
                break;
            case 'F': //float
                result = 0x7D;
                break;

            case 'J': //long
                result = 0x7E;
                break;
            case '[': //array
                break;
        }
        return result;
    }



}
