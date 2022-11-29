package BinaryWasm.SectionGlobal;

import BinaryWasm.ISection;
import ElementsWasm.Global.IGlobal;

public interface ISectionGlobal extends ISection {
    void addGlobal(IGlobal global);
}
