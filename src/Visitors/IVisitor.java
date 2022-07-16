package Visitors;

import BinaryWasm.SectionCode.IBody;
import org.apache.bcel.generic.MethodGen;

public interface IVisitor {
    IBody createBody(MethodGen mg);
}
