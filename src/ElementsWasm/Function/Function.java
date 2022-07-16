package ElementsWasm.Function;

import BinaryWasm.SectionCode.IBody;
import BinaryWasm.SectionType.IType;

public class Function implements IFunction {
    private IType type;
    private IBody body;


    public Function(IType type, IBody body){
        this.body = body;
        this.type = type;
    }
    @Override
    public byte[] getBytesElement() {
        return new byte[0];
    }
}
