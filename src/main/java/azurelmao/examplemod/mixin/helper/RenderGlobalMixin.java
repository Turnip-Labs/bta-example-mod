package azurelmao.examplemod.mixin.helper;

import azurelmao.examplemod.helper.ParticleHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.src.EntityFX;
import net.minecraft.src.RenderGlobal;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(value = RenderGlobal.class, remap = false)
public class RenderGlobalMixin {

    @Shadow
    private Minecraft mc;

    @Shadow
    private World worldObj;

    @Inject(method = "spawnParticle", at = @At(value = "RETURN"))
    private void examplemod_spawnParticle(String s, double x, double y, double z, double motionX, double motionY, double motionZ, CallbackInfo ci) {
        if (mc != null && mc.renderViewEntity != null && mc.effectRenderer != null) {
            double d6 = this.mc.renderViewEntity.posX - x;
            double d7 = this.mc.renderViewEntity.posY - y;
            double d8 = this.mc.renderViewEntity.posZ - z;
            double d9 = 16.0;

            if (!(d6 * d6 + d7 * d7 + d8 * d8 > d9 * d9)) {
                Map<String, Class<? extends EntityFX>> particles = ParticleHelper.particles;
                for (String name : particles.keySet()) {
                    if (s.equals(name)) {
                        Class<? extends EntityFX> clazz = particles.get(name);

                        try {
                            mc.effectRenderer.addEffect(clazz.getDeclaredConstructor(World.class, double.class, double.class, double.class, double.class, double.class, double.class).newInstance(worldObj, x, y, z, motionX, motionY, motionZ));
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }

                    }
                }
            }
        }
    }

}
