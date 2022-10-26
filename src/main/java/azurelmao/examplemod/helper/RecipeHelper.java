package azurelmao.examplemod.helper;

import azurelmao.examplemod.ExampleMod;
import azurelmao.examplemod.mixin.helper.CraftingManagerInterface;
import azurelmao.examplemod.mixin.helper.RecipesBlastFurnaceInterface;
import azurelmao.examplemod.mixin.helper.RecipesFurnaceInterface;
import net.minecraft.src.*;

import java.util.List;
import java.util.Map;

public class RecipeHelper {
    public static final CraftingManager craftingManager = CraftingManager.getInstance();
    public static final RecipesFurnace smeltingManager = RecipesFurnace.smelting();
    public static final RecipesBlastFurnace blastingManager = RecipesBlastFurnace.smelting();

    public static class Crafting {

        public static void createRecipe(Item outputItem, int amount, Object[] aobj) {
            ((CraftingManagerInterface) craftingManager).callAddRecipe(new ItemStack(outputItem, amount), aobj);
        }

        public static void createRecipe(Block outputBlock, int amount, Object[] aobj) {
            ((CraftingManagerInterface) craftingManager).callAddRecipe(new ItemStack(outputBlock, amount), aobj);
        }

        public static void createShapelessRecipe(Item outputItem, int amount, Object[] aobj) {
            ((CraftingManagerInterface) craftingManager).callAddShapelessRecipe(new ItemStack(outputItem, amount), aobj);
        }

        public static void createShapelessRecipe(Block outputBlock, int amount, Object[] aobj) {
            ((CraftingManagerInterface) craftingManager).callAddShapelessRecipe(new ItemStack(outputBlock, amount), aobj);
        }

        public static void removeRecipe(Item outputItem) {
            List recipes = craftingManager.getRecipeList();
            IRecipe theRecipe = null;

            for (Object recipe : recipes) {
                if (recipe instanceof RecipeShaped && ((RecipeShaped) recipe).recipeOutput.itemID == outputItem.itemID) {
                    theRecipe = (IRecipe) recipe;
                    break;
                }
                else if (recipe instanceof RecipeShapeless  && ((RecipeShapeless) recipe).recipeOutput.itemID == outputItem.itemID) {
                    theRecipe = (IRecipe) recipe;
                    break;
                }
            }

            if (theRecipe == null) {
                ExampleMod.LOGGER.info("Couldn't find recipe with output: " + outputItem.getItemName());
                return;
            }

            recipes.remove(theRecipe);
            ((CraftingManagerInterface) craftingManager).setRecipes(recipes);
        }
        public static void removeRecipe(Block outputBlock) {
            List recipes = craftingManager.getRecipeList();
            IRecipe theRecipe = null;

            for (Object recipe : recipes) {
                if (recipe instanceof RecipeShaped && ((RecipeShaped) recipe).recipeOutput.itemID == outputBlock.blockID) {
                    theRecipe = (IRecipe) recipe;
                    break;
                }
                else if (recipe instanceof RecipeShapeless  && ((RecipeShapeless) recipe).recipeOutput.itemID == outputBlock.blockID) {
                    theRecipe = (IRecipe) recipe;
                    break;
                }
            }

            if (theRecipe == null) {
                ExampleMod.LOGGER.info("Couldn't find crafting recipe with output: " + outputBlock.getBlockName(0));
                return;
            }

            recipes.remove(theRecipe);
            ((CraftingManagerInterface) craftingManager).setRecipes(recipes);
        }
    }

    public static class Smelting {

        public static void createRecipe(Item outputItem, Item inputItem) {
            smeltingManager.addSmelting(inputItem.itemID, new ItemStack(outputItem));
        }

        public static void createRecipe(Item outputItem, Block inputItem) {
            smeltingManager.addSmelting(inputItem.blockID, new ItemStack(outputItem));
        }

        public static void createRecipe(Block outputItem, Item inputItem) {
            smeltingManager.addSmelting(inputItem.itemID, new ItemStack(outputItem));
        }

        public static void createRecipe(Block outputItem, Block inputItem) {
            smeltingManager.addSmelting(inputItem.blockID, new ItemStack(outputItem));
        }

        public static void removeRecipe(Item inputItem) {
            Map recipes = smeltingManager.getSmeltingList();

            if (recipes.containsKey(inputItem)) {
                ExampleMod.LOGGER.info("Couldn't find smelting recipe with input: " + inputItem.getItemName());
                return;
            }

            recipes.remove(inputItem);
            ((RecipesFurnaceInterface) smeltingManager).setSmeltingList(recipes);
        }

        public static void removeRecipe(Block inputItem) {
            Map recipes = smeltingManager.getSmeltingList();

            if (recipes.containsKey(inputItem)) {
                ExampleMod.LOGGER.info("Couldn't find smelting recipe with input: " + inputItem.getBlockName(0));
                return;
            }

            recipes.remove(inputItem);
            ((RecipesFurnaceInterface) smeltingManager).setSmeltingList(recipes);
        }
    }

    public static class Blasting {
        public static void createRecipe(Item outputItem, Item inputItem) {
            blastingManager.addSmelting(inputItem.itemID, new ItemStack(outputItem));
        }

        public static void createRecipe(Item outputItem, Block inputItem) {
            blastingManager.addSmelting(inputItem.blockID, new ItemStack(outputItem));
        }

        public static void createRecipe(Block outputItem, Item inputItem) {
            blastingManager.addSmelting(inputItem.itemID, new ItemStack(outputItem));
        }

        public static void createRecipe(Block outputItem, Block inputItem) {
            blastingManager.addSmelting(inputItem.blockID, new ItemStack(outputItem));
        }

        public static void removeRecipe(Item inputItem) {
            Map recipes = blastingManager.getSmeltingList();

            if (recipes.containsKey(inputItem)) {
                ExampleMod.LOGGER.info("Couldn't find blasting recipe with input: " + inputItem.getItemName());
                return;
            }

            recipes.remove(inputItem);
            ((RecipesBlastFurnaceInterface) blastingManager).setSmeltingList(recipes);
        }

        public static void removeRecipe(Block inputItem) {
            Map recipes = blastingManager.getSmeltingList();

            if (recipes.containsKey(inputItem)) {
                ExampleMod.LOGGER.info("Couldn't find blasting recipe with input: " + inputItem.getBlockName(0));
                return;
            }

            recipes.remove(inputItem);
            ((RecipesBlastFurnaceInterface) blastingManager).setSmeltingList(recipes);
        }
    }

}
