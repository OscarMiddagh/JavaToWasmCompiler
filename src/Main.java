import org.apache.bcel.Repository;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;

import java.io.File;
import java.io.FileOutputStream;

public class Main {
    public static void main(String[] argv)  throws Exception  {
        JavaClass java_class;
        argv = new String[1];
        argv[0] = "Ejemplos/Recursividad";
        if (argv.length == 0) {
            System.err.println("disassemble: No input files specified");
        }
        else {
            for (final String arg : argv) {
                if ((java_class = Repository.lookupClass(arg)) == null) {
                    java_class = new ClassParser(arg).parse();
                }
                String class_name = java_class.getClassName();
                final int index = class_name.lastIndexOf('.');
                final String path = class_name.substring(0, index + 1).replace('.', File.separatorChar);
                class_name = class_name.substring(index + 1);
                if (!path.equals("")) {
                    final File f = new File(path);
                    f.mkdirs();
                }
                final String name = path + class_name + ".wasm";
                final FileOutputStream out = new FileOutputStream(name);
                new WasmTranslate(java_class, out).compile();
                System.out.println("File dumped to: " + name);
            }
        }
    }
}
