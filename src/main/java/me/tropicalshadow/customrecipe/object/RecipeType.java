package me.tropicalshadow.customrecipe.object;

import org.bukkit.inventory.*;

public enum RecipeType {
    SHAPED(ShapedRecipe.class),
    SHAPELESS(ShapelessRecipe.class),
    FURNACE(FurnaceRecipe.class),
    BLASTING(BlastingRecipe.class),
    CAMPFIRE(CampfireRecipe.class),
    SMOKING(SmokingRecipe.class),
    MERCHANT(MerchantRecipe.class),
    SMITHING(SmithingRecipe.class),
    STONECUTTER(StonecuttingRecipe.class);

    private String name;
    private Class<? extends Recipe> type;

    RecipeType(Class<? extends Recipe> type){
        this.name = this.name();
        this.type = type;
    }

    static RecipeType fromName(String name){
        RecipeType output = null;
        for (RecipeType value : RecipeType.values()) {
            if(value.getName().equalsIgnoreCase(name))output =value;
        }
        return output;
    }
    public String getName(){
        return this.name;
    }

    public Class<? extends Recipe> getType() {
        return type;
    }
}
