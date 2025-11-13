package net.nerdypuzzle.curios.preferences;

import net.mcreator.plugin.JavaPlugin;
import net.mcreator.plugin.events.WorkspaceBuildStartedEvent;
import net.mcreator.plugin.events.workspace.MCreatorLoadedEvent;
import net.mcreator.plugin.events.workspace.WorkspaceSavedEvent;
import net.mcreator.preferences.PreferencesManager;
import net.mcreator.preferences.PreferencesSection;
import net.mcreator.preferences.entries.BooleanEntry;
import net.mcreator.workspace.Workspace;
import net.mcreator.workspace.elements.ModElement;
import net.nerdypuzzle.curios.Launcher;
import net.nerdypuzzle.curios.element.types.CuriosBauble;
import net.nerdypuzzle.curios.element.types.PluginElementTypes;

import java.util.ArrayList;
import java.util.List;

public class CuriosPreferences extends PreferencesSection {
    public static final CuriosBooleanEntry disableHeadToggle = new CuriosBooleanEntry("disable_head_toggle", false);
    public static final CuriosBooleanEntry disableNecklaceToggle = new CuriosBooleanEntry("disable_necklace_toggle", false);
    public static final CuriosBooleanEntry disableBackToggle = new CuriosBooleanEntry("disable_back_toggle", false);
    public static final CuriosBooleanEntry disableBodyToggle = new CuriosBooleanEntry("disable_body_toggle", false);
    public static final CuriosBooleanEntry disableBraceletToggle = new CuriosBooleanEntry("disable_bracelet_toggle", false);
    public static final CuriosBooleanEntry disableHandsToggle = new CuriosBooleanEntry("disable_hands_toggle", false);
    public static final CuriosBooleanEntry disableRingToggle = new CuriosBooleanEntry("disable_ring_toggle", false);
    public static final CuriosBooleanEntry disableBeltToggle = new CuriosBooleanEntry("disable_belt_toggle", false);
    public static final CuriosBooleanEntry disableCharmToggle = new CuriosBooleanEntry("disable_charm_toggle", false);
    public static final CuriosBooleanEntry disableCurioToggle = new CuriosBooleanEntry("disable_curio_toggle", false);

    public CuriosPreferences() {
        super("curios");
    }

    public static void register(JavaPlugin plugin) {
        PreferencesManager.getPreferencesRegistry().put("curios", new ArrayList<>() {{
            add(disableHeadToggle);
            add(disableNecklaceToggle);
            add(disableBackToggle);
            add(disableBodyToggle);
            add(disableBraceletToggle);
            add(disableHandsToggle);
            add(disableRingToggle);
            add(disableBeltToggle);
            add(disableCharmToggle);
            add(disableCurioToggle);
        }});
        plugin.addListener(WorkspaceBuildStartedEvent.class, event -> updateBaubleSlots(event.getMCreator().getWorkspace()));
        plugin.addListener(WorkspaceSavedEvent.BeforeSaving.class, event -> updateBaubleSlots(event.getWorkspace()));
        plugin.addListener(MCreatorLoadedEvent.class, event-> updateBaubleSlots(event.getMCreator().getWorkspace()));
    }

    public static void updateBaubleSlots(Workspace workspace) {
        workspace.getModElements().stream()
                .filter(modElement -> modElement.getType().equals(PluginElementTypes.CURIOSBAUBLE))
                .map(ModElement::getGeneratableElement)
                .map(element -> (CuriosBauble) element)
                .toList().forEach(bauble -> {
                    List<String> oldPreferences = List.copyOf(bauble.disabledToggles);
                    bauble.disabledToggles.clear();
                    if (disableHeadToggle.get())
                        bauble.disabledToggles.add("HEAD");
                    if (disableNecklaceToggle.get())
                        bauble.disabledToggles.add("NECKLACE");
                    if (disableBackToggle.get())
                        bauble.disabledToggles.add("BACK");
                    if (disableBodyToggle.get())
                        bauble.disabledToggles.add("BODY");
                    if (disableBraceletToggle.get())
                        bauble.disabledToggles.add("BRACELET");
                    if (disableHandsToggle.get())
                        bauble.disabledToggles.add("HANDS");
                    if (disableRingToggle.get())
                        bauble.disabledToggles.add("RING");
                    if (disableBeltToggle.get())
                        bauble.disabledToggles.add("BELT");
                    if (disableCharmToggle.get())
                        bauble.disabledToggles.add("CHARM");
                    if (disableCurioToggle.get())
                        bauble.disabledToggles.add("CURIO");
                    if (oldPreferences.isEmpty() || !oldPreferences.equals(bauble.disabledToggles)) {
                        try {
                            workspace.getGenerator().generateElement(bauble, false);
                            bauble.getModElement().reinit(workspace);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public String getSectionKey() {
        return "curios";
    }

    public static class CuriosBooleanEntry extends BooleanEntry {
        public CuriosBooleanEntry(String id, boolean value) {
            super(id, value);
        }

        @Override
        public PreferencesSection getSection() {
            return Launcher.CURIOS_PREFERENCES;
        }

        @Override
        public String getSectionKey() {
            return "curios";
        }
    }
}
