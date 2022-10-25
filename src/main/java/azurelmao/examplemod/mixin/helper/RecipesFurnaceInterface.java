package azurelmao.examplemod.mixin.helper;

import net.minecraft.src.RecipesFurnace;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(value = RecipesFurnace.class, remap = false)
public interface RecipesFurnaceInterface {

    @Accessor("smeltingList")
    void setSmeltingList(Map map);
}
