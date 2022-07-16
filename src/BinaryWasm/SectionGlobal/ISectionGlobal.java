package BinaryWasm.SectionGlobal;

import BinaryWasm.ISection;

public interface ISectionGlobal extends ISection {
    void addGlobal(IGlobal global);
}
