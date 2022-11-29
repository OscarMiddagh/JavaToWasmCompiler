package Compiler;

import ElementsWasm.Function.IFunction;
import ElementsWasm.Global.IGlobal;

import java.io.IOException;
import java.util.ArrayList;

public interface WASMCompiler{
    void compile()throws IOException;

}
