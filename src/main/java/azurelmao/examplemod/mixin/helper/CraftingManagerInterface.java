package azurelmao.examplemod.mixin.helper;

import net.minecraft.src.CraftingManager;
import net.minecraft.src.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;

@Mixin(value = CraftingManager.class, remap = false)
public interface CraftingManagerInterface {

    @Invoker("addRecipe")
    void callAddRecipe(ItemStack itemstack, Object[] aobj);

    @Invoker("addShapelessRecipe")
    void callAddShapelessRecipe(ItemStack itemstack, Object[] aobj);

    @Accessor("recipes")
    void setRecipes(List list);
}
