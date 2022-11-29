package ElementsWasm.Type;

import ElementsWasm.IElement;

public interface IType extends IElement {
    int typeLength();
    byte[] getParameters();
    byte[] getResults();
}
