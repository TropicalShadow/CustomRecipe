package me.tropicalshadow.customrecipe.utils;

import me.tropicalshadow.customrecipe.CustomRecipe;
import me.tropicalshadow.customrecipe.object.CRecipe;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class FileHandler {

    public static void initFiles(){
        CustomRecipe plugin = CustomRecipe.getPlugin();
        plugin.saveDefaultConfig();
        File RecipesFolder = new File(plugin.getDataFolder(),"recipes");
        if(!RecipesFolder.exists())RecipesFolder.mkdir();
    }

    public static File getFile(String name){
        return new File(CustomRecipe.getPlugin().getDataFolder(),name);
    }

    public static ArrayList<String> getRecipeFileNames(){
        ArrayList<String> output = new ArrayList<>();
        File RecipesFolder = new File(CustomRecipe.getPlugin().getDataFolder(), "recipes");
        File[] files = RecipesFolder.listFiles();
        if(files == null || files.length <= 0) return output;
        for (File file : files) {
            output.add(file.getName());
        }
        return output;
    }
    public static ArrayList<File> getRecipeFiles(){
        ArrayList<File> output = new ArrayList<>();
        File RecipesFolder = new File(CustomRecipe.getPlugin().getDataFolder(), "recipes");
        File[] files = RecipesFolder.listFiles();
        if(files == null || files.length <= 0) return output;
        Collections.addAll(output, files);
        return output;
    }
    public static CRecipe getRecipeFromFile(File RecipeFile){
        if(!RecipeFile.exists())return null;
        YamlConfiguration yamlFile = YamlConfiguration.loadConfiguration(RecipeFile);
        CRecipe recipe = (CRecipe) yamlFile.get("recipe");
        return recipe;
    }
    public static boolean saveRecipeToFile(CRecipe recipe){
        String name = recipe.getName();
        File file = new File(CustomRecipe.getPlugin().getDataFolder(), name);
        try{
            if(!file.createNewFile())return false;
            YamlConfiguration yamlFile = YamlConfiguration.loadConfiguration(file);
            yamlFile.set("recipe",recipe);
            yamlFile.save(file);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }


}
