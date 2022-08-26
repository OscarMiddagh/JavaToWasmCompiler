import Compiler.WasmTranslate;
import Compiler.WASMCompiler;

public class Main {
    public static void main(String[] argv)  throws Exception  {
        WASMCompiler compiler;
        if (argv.length == 0) {
            System.err.println("Error: Ruta archivo .class no especificado");
        }
        else {
            for (final String arg : argv) {
                compiler =  new WasmTranslate(arg);
                compiler.compile();
            }
        }
    }
}
