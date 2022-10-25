package azurelmao.examplemod.mixin.helper;

import net.minecraft.src.Block;
import net.minecraft.src.StepSound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = Block.class, remap = false)
public interface BlockInterface {

    @Invoker("setHardness")
    Block callSetHardness(float f);

    @Invoker("setResistance")
    Block callSetResistance(float f);

    @Invoker("setStepSound")
    Block callSetStepSound(StepSound stepSound);

    @Invoker("setLightValue")
    Block callSetLightValue(float f);
}
