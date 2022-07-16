import Compiler.WasmTranslate;
public class Main {
    public static void main(String[] argv)  throws Exception  {
        if (argv.length == 0) {
            System.err.println("Error: Ruta archivo .class no especificado");
        }
        else {
            for (final String arg : argv) {
                new WasmTranslate(arg).compile();
            }
        }
    }
}
