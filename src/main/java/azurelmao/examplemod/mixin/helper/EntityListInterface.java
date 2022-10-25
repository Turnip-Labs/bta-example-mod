package azurelmao.examplemod.mixin.helper;

import net.minecraft.src.EntityList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = EntityList.class, remap = false)
public interface EntityListInterface {

    @Invoker("addMapping")
    static void callAddMapping(Class clazz, String name, int id) {
        throw new AssertionError();
    }
}
