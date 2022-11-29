package Visitors;

import ElementsWasm.Body.IBody;
import org.apache.bcel.generic.MethodGen;

public interface IBodyVisitor {
    IBody createBody(MethodGen mg);
}
