package net.nerdypuzzle.curios.element.types;

import net.mcreator.element.GeneratableElement;
import net.mcreator.workspace.elements.ModElement;

public class CuriosSlot extends GeneratableElement {

    public String texture;
    public String name;
    public int amount;
    public CuriosSlot(ModElement element) {
        super(element);
    }

}
