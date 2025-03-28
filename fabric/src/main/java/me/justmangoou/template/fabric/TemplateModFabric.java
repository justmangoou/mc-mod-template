package me.justmangoou.template.fabric;

import net.fabricmc.api.ModInitializer;
import me.justmangoou.template.TemplateModCommon;

public class TemplateModFabric implements ModInitializer {
	@Override
	public void onInitialize() {
		TemplateModCommon.init();
	}
}
