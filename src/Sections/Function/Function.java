package Sections.Function;

import Sections.Type.IType;

public class Function implements IFunction {
    private IType type;

    public Function(IType type){
        this.type = type;
    }
    @Override
    public byte[] getBytesElement() {
        return new byte[0];
    }
}
