package net.nerdypuzzle.curios;

import net.mcreator.plugin.events.ApplicationLoadedEvent;
import net.nerdypuzzle.curios.element.types.PluginElementTypes;
import net.mcreator.plugin.JavaPlugin;
import net.mcreator.plugin.Plugin;
import net.mcreator.plugin.events.PreGeneratorsLoadingEvent;
import net.nerdypuzzle.curios.preferences.CuriosPreferences;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Launcher extends JavaPlugin {

    public static final CuriosPreferences CURIOS_PREFERENCES = new CuriosPreferences();
	private static final Logger LOG = LogManager.getLogger("Curios Plugin");

	public Launcher(Plugin plugin) {
		super(plugin);

		addListener(PreGeneratorsLoadingEvent.class, event -> PluginElementTypes.load());
        addListener(ApplicationLoadedEvent.class, event-> CuriosPreferences.register(this));

		LOG.info("Plugin was loaded");
	}

}