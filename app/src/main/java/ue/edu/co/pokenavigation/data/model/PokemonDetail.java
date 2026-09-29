package ue.edu.co.pokenavigation.data.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class PokemonDetail {
    private String name;
    private int height;
    private int weight;

    @SerializedName("base_experience")
    private int baseExperience;

    private Sprites sprites;
    private List<TypeSlot> types;

    public String getName() { return name; }
    public int getHeight() { return height; }
    public int getWeight() { return weight; }
    public int getBaseExperience() { return baseExperience; }
    public Sprites getSprites() { return sprites; }
    public List<TypeSlot> getTypes() { return types; }
}