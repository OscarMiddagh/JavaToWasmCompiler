package BinaryWasm.SectionExport;

import BinaryWasm.ISection;

public interface ISectionExport extends ISection {
    void addExport(IExport export);
}
