package BinaryWasm.SectionType;

import BinaryWasm.ISection;

public interface ISectionType extends ISection {
    void addType(IType type);
    boolean containsType(IType type);
    int indexOfType(IType type);
    IType getType(int n);
}
