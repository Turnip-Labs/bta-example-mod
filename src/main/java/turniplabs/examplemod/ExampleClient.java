package turniplabs.examplemod;

import net.fabricmc.api.ClientModInitializer;
import turniplabs.halplibe.HalpLibe;

public class ExampleClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		HalpLibe.registerMod("examplemod", true);
	}
}
