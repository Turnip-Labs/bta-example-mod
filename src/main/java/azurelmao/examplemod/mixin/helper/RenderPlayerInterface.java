package azurelmao.examplemod.mixin.helper;

import net.minecraft.src.RenderPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = RenderPlayer.class, remap = false)
public interface RenderPlayerInterface {

    @Accessor("armorFilenamePrefix")
    static String[] getArmorFilenamePrefix() {
        throw new AssertionError();
    }

    @Accessor("armorFilenamePrefix")
    static void setArmorFilenamePrefix(String[] strings) {
        throw new AssertionError();
    }
}
