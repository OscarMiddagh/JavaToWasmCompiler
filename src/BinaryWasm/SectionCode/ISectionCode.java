package BinaryWasm.SectionCode;

import BinaryWasm.ISection;
import ElementsWasm.Body.IBody;

public interface ISectionCode extends ISection {
    void addBody(IBody body);
}
