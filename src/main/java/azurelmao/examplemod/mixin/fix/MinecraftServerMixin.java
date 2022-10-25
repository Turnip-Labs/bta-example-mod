package azurelmao.examplemod.mixin.fix;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.Dimension;
import net.minecraft.src.EntityTracker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = MinecraftServer.class, remap = false)
public class MinecraftServerMixin {

    // Mixin to fix custom dimensions on multiplayer

    @Shadow
    public EntityTracker[] entityTracker;

    @Inject(method = "startServer", at = @At(value = "HEAD"))
    private void examplemod_startServer(CallbackInfoReturnable<Boolean> cir) {
        entityTracker = new EntityTracker[Dimension.dimensionList.length];
    }
}
