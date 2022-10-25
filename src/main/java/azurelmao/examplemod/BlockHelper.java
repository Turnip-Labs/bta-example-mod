package azurelmao.examplemod;

import azurelmao.examplemod.mixin.helper.BlockInterface;
import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemBlock;
import net.minecraft.src.Material;
import net.minecraft.src.StepSound;

public class BlockHelper {

    public static Block createBlock(int id, String name, int x, int y, Material material, StepSound stepSound, float hardness, float resistance, float lightValue) {
        Block block = new Block(id, material);
        block.setTexCoords(x, y);
        block.setBlockName(name);

        ((BlockInterface) block).callSetHardness(hardness);
        ((BlockInterface) block).callSetResistance(resistance);
        ((BlockInterface) block).callSetStepSound(stepSound);
        ((BlockInterface) block).callSetLightValue(lightValue);

        Item.itemsList[block.blockID] = new ItemBlock(block.blockID - Block.blocksList.length);

        return block;
    }

    public static Block createBlock(int id, String name, int topX, int topY, int bottomX, int bottomY, int sidesX, int sidesY, Material material, StepSound stepSound, float hardness, float resistance, float lightValue) {
        Block block = new Block(id, material);
        block.setTexCoords(topX, topY, bottomX, bottomY, sidesX, sidesY);
        block.setBlockName(name);

        ((BlockInterface) block).callSetHardness(hardness);
        ((BlockInterface) block).callSetResistance(resistance);
        ((BlockInterface) block).callSetStepSound(stepSound);
        ((BlockInterface) block).callSetLightValue(lightValue);

        Item.itemsList[block.blockID] = new ItemBlock(block.blockID - Block.blocksList.length);

        return block;
    }

    public static Block createBlock(int id, String name, int topX, int topY, int bottomX, int bottomY, int northX, int northY, int eastX, int eastY, int southX, int southY, int westX, int westY, Material material, StepSound stepSound, float hardness, float resistance, float lightValue) {
        Block block = new Block(id, material);
        block.setTexCoords(topX, topY, bottomX, bottomY, northX, northY, eastX, eastY, southX, southY, westX, westY);
        block.setBlockName(name);

        ((BlockInterface) block).callSetHardness(hardness);
        ((BlockInterface) block).callSetResistance(resistance);
        ((BlockInterface) block).callSetStepSound(stepSound);
        ((BlockInterface) block).callSetLightValue(lightValue);

        Item.itemsList[block.blockID] = new ItemBlock(block.blockID - Block.blocksList.length);

        return block;
    }
}
