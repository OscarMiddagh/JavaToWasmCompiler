package ElementsWasm.Function;

import ElementsWasm.Body.IBody;
import ElementsWasm.IElement;
import ElementsWasm.ExportableElement;
import ElementsWasm.Type.IType;

public interface IFunction extends ExportableElement {
    IBody getBody();
    IType getType();
}
