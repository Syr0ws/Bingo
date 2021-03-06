package com.github.syr0ws.bingo.plugin.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class LocationUtil {

    private static final List<Material> DANGEROUS_BLOCKS = Collections.singletonList(Material.LAVA);

    public static Location getLocation(ConfigurationSection section) {

        double x = section.getDouble("x");
        double y = section.getDouble("y");
        double z = section.getDouble("z");

        float yaw = (float) section.getDouble("yaw");
        float pitch = (float) section.getDouble("pitch");

        String worldName = section.getString("world", "");
        World world = Bukkit.getWorld(worldName);

        if(world == null)
            throw new IllegalArgumentException(String.format("World '%s' doesn't exist.", worldName));

        return new Location(world, x, y, z, yaw, pitch);
    }

    public static void findNearestSafePlace(Location location) {

        Block block = location.getBlock();

        boolean x = true, z = false;

        while(DANGEROUS_BLOCKS.contains(block.getType())) {

            double locX = location.getX() + (x ? -1 : 0);
            double locZ = location.getZ() + (z ? -1 : 0);
            double locY = location.getWorld().getHighestBlockYAt((int) locX, (int) locZ);

            location.setX(locX);
            location.setY(locY);
            location.setZ(locZ);

            block = location.getBlock();

            x = !x;
            z = !z;
        }
    }

    public static Location findRandomSafeLocation(World world, int radius) {

        Random random = new Random();

        double x = random.nextInt(radius + 1) + 0.5;
        double z = random.nextInt(radius + 1) + 0.5;
        int y = world.getHighestBlockYAt((int) x, (int) z);

        Location location = new Location(world, x, y, z, 180, 0);

        LocationUtil.findNearestSafePlace(location);

        location.setY(location.getY() + 1);

        return location;
    }
}
