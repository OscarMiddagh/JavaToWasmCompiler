package ElementsWasm;

import ElementsWasm.Export.IExport;

public interface ExportableElement {
    IExport getExport();
    boolean isPublic();
    void setExport(IExport export);
}
