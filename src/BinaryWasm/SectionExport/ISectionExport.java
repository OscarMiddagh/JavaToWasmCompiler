package BinaryWasm.SectionExport;

import BinaryWasm.ISection;
import ElementsWasm.Export.IExport;

public interface ISectionExport extends ISection {
    void addExport(IExport export);
}
