package azurelmao.examplemod.helper;

import net.minecraft.src.EntityFX;

import java.util.HashMap;
import java.util.Map;

public class ParticleHelper {

    public static Map<String, Class<? extends EntityFX>> particles = new HashMap<>();

    public static void createParticle(Class<? extends EntityFX> clazz, String name) {
        particles.put(name, clazz);
    }
}
