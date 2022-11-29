package ElementsWasm.Function;

import ElementsWasm.Body.IBody;
import ElementsWasm.Export.IExport;
import ElementsWasm.Type.IType;

public class Function implements IFunction {
    private IType type;
    private IBody body;
    private IExport export;

    private boolean isPublic;


    public Function(IType type, IBody body){
        this.body = body;
        this.type = type;
        isPublic = false;
    }
    @Override
    public IType getType(){
        return type;
    }

    @Override
    public IBody getBody(){
        return body;
    }

    @Override
    public IExport getExport(){
        return export;
    }
    @Override
    public boolean isPublic(){
        return isPublic;
    }

    @Override
    public void setExport(IExport export) {
        this.export = export;
        isPublic = true;
    }
}
