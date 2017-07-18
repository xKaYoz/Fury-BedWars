package me.kayoz.bedwars.utils.generators;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.*;
import org.bukkit.util.NumberConversions;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by KaYoz on 7/12/2017.
 * Subscribe to me on Youtube:
 * http://www.youtube.com/c/KaYozMC/
 */

public class Generator implements Serializable{
    @Getter @Setter
    private World world;
    @Getter @Setter
    private double x;
    @Getter @Setter
    private double y;
    @Getter @Setter
    private double z;
    @Getter @Setter
    private Material drop;
    @Getter @Setter
    private String name;

    public Generator(String name, World world, double x, double y, double z, Material type){
        this.name = name;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.drop = type;
    }

    public Generator(String name, Location location, Material type){
        this.name = name;
        this.world = location.getWorld();
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
        this.drop = type;
    }

    @Utility
    public Map<String, Object> serialize(){
        Map<String, Object> data = new HashMap<>();

        data.put("world", this.world.getName());
        data.put("x", this.x);
        data.put("y", this.y);
        data.put("z", this.z);
        data.put("Material", this.drop.toString());

        return data;
    }

    public static Generator deserialize(String name, Map<String, Object> args){
        World world = Bukkit.getWorld((String) args.get("world"));
        if (world == null) {
            throw new IllegalArgumentException("unknown world");
        }
        return new Generator(name, world, NumberConversions.toDouble(args.get("x")),
                NumberConversions.toDouble(args.get("y")), NumberConversions.toDouble(args.get("z")), Material.getMaterial((String) args.get("Material")));
    }

}