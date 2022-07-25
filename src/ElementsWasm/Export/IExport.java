package ElementsWasm.Export;

import ElementsWasm.IElement;

public interface IExport extends IElement {
    String getNameExport();
    int getKind();
    int getIdElement();
}
