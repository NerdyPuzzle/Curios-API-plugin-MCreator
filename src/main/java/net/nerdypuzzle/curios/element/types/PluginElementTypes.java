package net.nerdypuzzle.curios.element.types;

import net.mcreator.element.ModElementType;
import net.nerdypuzzle.curios.ui.modgui.CuriosBaubleGUI;

import static net.mcreator.element.ModElementTypeLoader.register;

public class PluginElementTypes {
    public static ModElementType<?> CURIOSBAUBLE;

    public static void load() {

        CURIOSBAUBLE = register(
                new ModElementType<>("curiosbauble", (Character) 'B', CuriosBaubleGUI::new, CuriosBauble.class)
        );

    }
}
