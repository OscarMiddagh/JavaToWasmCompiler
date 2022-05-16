package Visitors;

import Sections.Code.IBody;
import org.apache.bcel.generic.MethodGen;

public interface IVisitor {
    IBody createBody(MethodGen mg);
}
