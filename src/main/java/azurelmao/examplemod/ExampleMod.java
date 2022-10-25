package azurelmao.examplemod;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ExampleMod implements ModInitializer {
    public static final String MOD_ID = "examplemod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static String name(String name) {
        return ExampleMod.MOD_ID + "." + name;
    }

    @Override
    public void onInitialize() {
        LOGGER.info("ExampleMod initialized.");
    }
}
