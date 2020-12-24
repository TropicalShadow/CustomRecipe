package me.tropicalshadow.customrecipe.object;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CRecipe implements ConfigurationSerializable {
    private String name;
    private RecipeType type;
    private int cookingTime;
    private float experience;
    private ArrayList<ItemStack> input;
    private ArrayList<ItemStack> output;


    public CRecipe(String name,RecipeType type,float experience,int cookingTime,ArrayList<ItemStack> input,ArrayList<ItemStack> output){
        this.name = name.toLowerCase();
        this.type = type;
        this.experience = experience;
        this.cookingTime = cookingTime;
        this.input = input;
        this.output = output;
    }

    public CRecipe(Map<String, Object> serializedCRecipe){
        this.name = (String) serializedCRecipe.get("name");
        this.type = RecipeType.fromName((String) serializedCRecipe.get("type"));
        this.experience = (float) serializedCRecipe.get("experience");
        this.cookingTime = (int) serializedCRecipe.get("cookTime");

        //Deserialize Input Items
        ArrayList<Map<String, Object>> mappedInputItems = (ArrayList<Map<String, Object>>) serializedCRecipe.get("input");
        ArrayList<ItemStack> deserializedInputItemContainer = new ArrayList<>(); // temporary container for deserialized allergies
        for(Map<String, Object> serializedItemStack : mappedInputItems)
            deserializedInputItemContainer.add(ItemStack.deserialize(serializedItemStack));
        this.input = deserializedInputItemContainer;

        //Deserialize Output Items
        ArrayList<Map<String, Object>> mappedOutputItems = (ArrayList<Map<String, Object>>) serializedCRecipe.get("output");
        ArrayList<ItemStack> deserializedOutputItemContainer = new ArrayList<>();
        mappedOutputItems.forEach(serializedItemStack -> deserializedOutputItemContainer.add(ItemStack.deserialize(serializedItemStack)));
        this.output = deserializedOutputItemContainer;
    }
    @Override
    public Map<String, Object> serialize() {
        HashMap<String, Object> mapSerializer = new HashMap<>();
        mapSerializer.put("name",this.name.toLowerCase());
        mapSerializer.put("type",this.type.getName());
        mapSerializer.put("experience",this.experience);
        mapSerializer.put("cookTime",this.cookingTime);
        ArrayList<Map<String, Object>> tempSerializeInputItems = new ArrayList<>();
        ArrayList<Map<String, Object>> tempSerializeOutputItem = new ArrayList<>();
        this.input.forEach(itemStack -> tempSerializeInputItems.add(itemStack.serialize()));
        this.output.forEach(itemStack -> tempSerializeOutputItem.add(itemStack.serialize()));
        mapSerializer.put("input",tempSerializeInputItems);
        mapSerializer.put("output",tempSerializeOutputItem);
        return mapSerializer;
    }


    public String getName() {
        return name;
    }

    public RecipeType getType() {
        return type;
    }

    public float getExperience() {
        return experience;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    public ArrayList<ItemStack> getInput() {
        return input;
    }

    public ArrayList<ItemStack> getOutput() {
        return output;
    }
}
