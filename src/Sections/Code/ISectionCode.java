package Sections.Code;

import Sections.ISection;

public interface ISectionCode extends ISection {
    void addBody(IBody body);
    boolean containsBody(IBody body);
    int indexOfElement(IBody body);
}
