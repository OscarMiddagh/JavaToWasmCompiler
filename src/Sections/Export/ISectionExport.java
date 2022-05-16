package Sections.Export;

import Sections.ISection;

public interface ISectionExport extends ISection {
    void addExport(IExport export);
    boolean containsElement(IExport export);
    int indexOfElement(IExport export);
}
