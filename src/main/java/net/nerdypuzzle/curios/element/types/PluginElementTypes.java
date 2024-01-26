package net.nerdypuzzle.curios.element.types;

import net.mcreator.element.ModElementType;
import net.nerdypuzzle.curios.ui.modgui.CuriosBaubleGUI;
import net.nerdypuzzle.curios.ui.modgui.CuriosSlotGUI;

import static net.mcreator.element.ModElementTypeLoader.register;

public class PluginElementTypes {
    public static ModElementType<?> CURIOSBAUBLE;
    public static ModElementType<?> CURIOSSLOT;

    public static void load() {

        CURIOSBAUBLE = register(
                new ModElementType<>("curiosbauble", (Character) 'B', CuriosBaubleGUI::new, CuriosBauble.class)
        );

        CURIOSSLOT = register(
                new ModElementType<>("curiosslot", (Character) 'S', CuriosSlotGUI::new, CuriosSlot.class)
        );

    }
}
