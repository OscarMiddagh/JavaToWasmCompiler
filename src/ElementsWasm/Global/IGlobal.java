package ElementsWasm.Global;

import ElementsWasm.IElement;
import ElementsWasm.ExportableElement;

public interface IGlobal extends IElement, ExportableElement {
    byte getValue();
    byte getTypeGlobal();
    byte getConstantType();
    byte getMutability();
}
