package azurelmao.examplemod.mixin.helper;

import net.minecraft.src.RecipesBlastFurnace;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(value = RecipesBlastFurnace.class, remap = false)
public interface RecipesBlastFurnaceInterface {

    @Accessor("smeltingList")
    void setSmeltingList(Map map);
}
