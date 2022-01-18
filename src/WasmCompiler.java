import org.apache.bcel.classfile.Attribute;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ConstantPoolGen;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class WasmCompiler {
    private String class_name;
    private ConstantPoolGen cp;
    private  DataOutputStream out;
    private JavaClass clazz;
    private Method[] methods;
    private Types types;

    public WasmCompiler(final JavaClass clazz, final FileOutputStream out) {
        this.clazz = clazz;
        this.out = new DataOutputStream(out);
        this.class_name = clazz.getClassName();
        this.cp = new ConstantPoolGen(clazz.getConstantPool());
        methods = clazz.getMethods();
    }

    public void compile(){
        transformData();
        addSections();
    }
    private void transformData(){

    }
    private void addSections(){
        try {
            addCustom();
            addTypes();
            addFunctions();
            addExports();
            addCode();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void addCustom() throws IOException {
        byte[] magicNumber = {0x00,0x61,0x73,0x6D};
        byte[] version = {0x01,0x00,0x00,0x00};
        out.write(magicNumber);
        out.write(version);
    }
    private void addTypes(){

    }
    private void addFunctions(){

    }
    private void addExports(){

    }
    private void addCode(){

    }
}
