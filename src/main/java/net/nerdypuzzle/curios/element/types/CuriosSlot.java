package net.nerdypuzzle.curios.element.types;

import net.mcreator.element.GeneratableElement;
import net.mcreator.workspace.elements.ModElement;

public class CuriosSlot extends GeneratableElement {

    public String texture;
    public String name;
    public int amount;
    public boolean modelToggling;
    public boolean changeOrder;
    public int slotOrder;

    public CuriosSlot(ModElement element) {
        super(element);
    }

}
