package azurelmao.examplemod.helper;

import azurelmao.examplemod.mixin.helper.RenderPlayerInterface;
import net.minecraft.src.helper.DamageType;
import net.minecraft.src.material.ArmorMaterial;
import org.apache.commons.lang3.ArrayUtils;

public class ArmorHelper {

    /**
     * @param textureName name of the armor texture file.
     * @param durability durability of your armor. Will be different than in-game due to how it gets allocated between armor pieces.
     * @param combat combat damage reduction in percent. Can be more than 100.
     * @param blast blast damage reduction in percent.
     * @param fire fire damage reduction in percent.
     * @param fall fall damage reduction in percent.
     * @return the new ArmorMaterial.
     */
    public static ArmorMaterial createArmorMaterial(String textureName, int durability, float combat, float blast, float fire, float fall) {
        String[] armorFilenamePrefix = RenderPlayerInterface.getArmorFilenamePrefix();
        armorFilenamePrefix = ArrayUtils.add(armorFilenamePrefix, textureName);
        RenderPlayerInterface.setArmorFilenamePrefix(armorFilenamePrefix);

        ArmorMaterial armorMaterial = new ArmorMaterial(textureName, armorFilenamePrefix.length - 1, durability);
        ArmorMaterial.setProtectionValuePercent(armorMaterial, DamageType.COMBAT, combat);
        ArmorMaterial.setProtectionValuePercent(armorMaterial, DamageType.BLAST, blast);
        ArmorMaterial.setProtectionValuePercent(armorMaterial, DamageType.FIRE, fire);
        ArmorMaterial.setProtectionValuePercent(armorMaterial, DamageType.FALL, fall);

        return armorMaterial;
    }
}
