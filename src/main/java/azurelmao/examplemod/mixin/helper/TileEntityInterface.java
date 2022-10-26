package azurelmao.examplemod.mixin.helper;

import net.minecraft.src.TileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = TileEntity.class, remap = false)
public interface TileEntityInterface {

    @Invoker("addMapping")
    static void callAddMapping(Class clazz, String s) {
        throw new AssertionError();
    }
}
