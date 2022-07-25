package ElementsWasm.Body;

import ElementsWasm.IElement;

;import java.util.ArrayList;

public interface IBody extends IElement {

    ArrayList<TypeLocalsVariables> getLocalsVariables();
    ArrayList<IBlock> getBlocks();
    ArrayList<Instructions> getInstructions();
}
