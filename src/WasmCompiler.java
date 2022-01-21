import org.apache.bcel.classfile.Attribute;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ConstantPoolGen;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class WasmCompiler {
    private String class_name;
    private ConstantPoolGen cp;
    private  DataOutputStream out;
    private JavaClass clazz;
    private Method[] methods;
    private ArrayList<String> typeIdx;
    private ArrayList<Integer> signatureMethodsIdx;
    private int sectionTypeSize;

    public WasmCompiler(final JavaClass clazz, final FileOutputStream out) {
        this.clazz = clazz;
        this.out = new DataOutputStream(out);
        this.class_name = clazz.getClassName();
        this.cp = new ConstantPoolGen(clazz.getConstantPool());
        methods = clazz.getMethods();
        signatureMethodsIdx = new ArrayList<>();
        typeIdx = new ArrayList<>();
        sectionTypeSize =0;
        transformData();
    }

    public void compile(){
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
    private void transformData(){
        int idType = 0;
        int idxAct;
        int idxFin;
        for(int i= 1; i<methods.length;i++){//Iniciamos en 1 por que no estamos considerando a main todavia
            idxAct = methods[i].getSignatureIndex();
            idxFin  = signatureMethodsIdx.indexOf(idxAct);
            if(idxFin == -1){
                signatureMethodsIdx.add(idxAct);
                typeIdx.add(methods[i].getSignature());
                idType++;
                sectionTypeSize = sectionTypeSize + 1 + methods[i].getSignature().length();
            }
            methods[i].setSignatureIndex(idxFin);
        }
        sectionTypeSize++;
    }
    private void addCustom() throws IOException {
        byte[] magicNumber = {0x00,0x61,0x73,0x6D};
        byte[] version = {0x01,0x00,0x00,0x00};
        out.write(magicNumber);
        out.write(version);
    }
    private void addTypes() throws IOException {
        byte idSection = 0x01;
        byte sectionSize = (byte)sectionTypeSize;
        byte[] typesSections = new byte[sectionTypeSize];
        byte func = 0x60;
        byte[] type;
        String[] paraRes;
        char p = ')';
        byte numParams = 0;
        byte numResults = 0;
        byte[] parametros;
        byte[] results;
        out.write(idSection);
        out.write(sectionSize);
        out.write((byte)typeIdx.size());
        for (String t:typeIdx) {
            paraRes = t.split("[)]");
            numParams = (byte)(paraRes[0].length()-1);
            numResults = (byte)paraRes[1].length();
            parametros = new byte[numParams];
            for (int i = 0; i < numParams; i++) {
                parametros[i] = 0x7F;
            }
            results = new byte[numResults];
            for (int j = 0; j < numResults; j++) {
                results[j] = 0x7F;
            }
            out.write(func);
            out.write(numParams);
            out.write(parametros);
            out.write(numResults);
            out.write(results);
        }
    }
    private void addFunctions(){

    }
    private void addExports(){

    }
    private void addCode(){

    }
}
