package me.tropicalshadow.customrecipe;

import me.tropicalshadow.customrecipe.object.CRecipe;
import me.tropicalshadow.customrecipe.object.RecipeType;
import me.tropicalshadow.customrecipe.utils.FileHandler;
import me.tropicalshadow.customrecipe.utils.Logging;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.inventory.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;

public final class CustomRecipe extends JavaPlugin {


    private static CustomRecipe plugin;
    private ArrayList<NamespacedKey> recipeKeys = new ArrayList<>();
    @Override
    public void onEnable() {
        plugin = this;
        ConfigurationSerialization.registerClass(CRecipe.class);
        FileHandler.initFiles();
        ReadAndAddRecipes();
        Logging.info("CustomRecipe Enabled");
    }

    @Override
    public void onDisable() {
        recipeKeys.forEach(Bukkit::removeRecipe);
        plugin = null;
        Logging.info("CustomRecipe Disabled");
    }

    public void ReadAndAddRecipes(){
        ArrayList<File> files = FileHandler.getRecipeFiles();
        files.forEach(file->{
            CRecipe recipe = FileHandler.getRecipeFromFile(file);
            if(recipe == null){
                Logging.danger("File: "+ file.getName()+ " Failed to load as CRecipe.");
                return;
            }
            NamespacedKey key = new NamespacedKey(this,file.getName());
            if(recipe.getType()== RecipeType.CAMPFIRE){
                CookingRecipe<CampfireRecipe> CampRecipe = new CampfireRecipe(key,recipe.getOutput().get(0),recipe.getInput().get(0).getType(),recipe.getExperience(),recipe.getCookingTime());
                Bukkit.addRecipe(CampRecipe);
            }else if(recipe.getType() == RecipeType.BLASTING){
                CookingRecipe<BlastingRecipe> BlastRecipe = new BlastingRecipe(key,recipe.getOutput().get(0),recipe.getInput().get(0).getType(), recipe.getExperience(),recipe.getCookingTime());
                Bukkit.addRecipe(BlastRecipe);
            }else if (recipe.getType() == RecipeType.FURNACE){
                CookingRecipe<FurnaceRecipe> FurnaceRecipe = new FurnaceRecipe(key,recipe.getOutput().get(0),recipe.getInput().get(0).getType(),recipe.getExperience(),recipe.getCookingTime() );
                Bukkit.addRecipe(FurnaceRecipe);
            }else if(recipe.getType() == RecipeType.SMOKING){
                CookingRecipe<SmokingRecipe> SmokingRecipe = new SmokingRecipe(key,recipe.getOutput().get(0),recipe.getInput().get(0).getType(),recipe.getExperience(),recipe.getCookingTime());
                Bukkit.addRecipe(SmokingRecipe);
            }else if(recipe.getType() == RecipeType.SHAPELESS){
                ShapelessRecipe shapelessRecipe = new ShapelessRecipe(key,recipe.getOutput().get(0));
                for (ItemStack itemStack : recipe.getInput()) {
                    if(itemStack.getData()!=null){
                        shapelessRecipe.addIngredient(itemStack.getAmount(), itemStack.getData());
                    }else{
                        shapelessRecipe.addIngredient(itemStack.getAmount(), itemStack.getType());
                    }
                }
                Bukkit.addRecipe(shapelessRecipe);
            }else if(recipe.getType() == RecipeType.SHAPED){
                ShapedRecipe shapedRecipe = new ShapedRecipe(key,recipe.getOutput().get(0));
                shapedRecipe.shape("012","345","678");
                for (int i = 0; i < 9; i++) {
                    ItemStack item = recipe.getInput().get(i);
                    if(item.getData() != null){
                        shapedRecipe.setIngredient(String.valueOf(i).toCharArray()[0],item.getData());
                    }else{
                        shapedRecipe.setIngredient(String.valueOf(i).toCharArray()[0],item.getType());
                    }
                }
                Bukkit.addRecipe(shapedRecipe);
            }
            recipeKeys.add(key);
        });
    }

    public static CustomRecipe getPlugin() {
        return plugin;
    }
}
