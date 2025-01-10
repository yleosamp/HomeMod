package com.homemod.data;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.*;

public class HomeManager {
    private static final Map<UUID, Map<String, HomeLocation>> playerHomes = new HashMap<>();
    private static final int MAX_HOMES = 3;

    public static class HomeLocation {
        public final BlockPos pos;
        public final String dimension;

        public HomeLocation(BlockPos pos, String dimension) {
            this.pos = pos;
            this.dimension = dimension;
        }
    }

    public static boolean addHome(ServerPlayer player, String homeName) {
        UUID playerId = player.getUUID();
        Map<String, HomeLocation> homes = playerHomes.computeIfAbsent(playerId, k -> new HashMap<>());

        if (homes.size() >= MAX_HOMES && !homes.containsKey(homeName)) {
            return false;
        }

        homes.put(homeName, new HomeLocation(
            player.blockPosition(),
            player.level.dimension().location().toString()
        ));
        return true;
    }

    public static boolean deleteHome(ServerPlayer player, String homeName) {
        UUID playerId = player.getUUID();
        Map<String, HomeLocation> homes = playerHomes.get(playerId);
        if (homes != null) {
            return homes.remove(homeName) != null;
        }
        return false;
    }

    public static HomeLocation getHome(ServerPlayer player, String homeName) {
        UUID playerId = player.getUUID();
        Map<String, HomeLocation> homes = playerHomes.get(playerId);
        if (homes != null) {
            return homes.get(homeName);
        }
        return null;
    }

    public static Map<String, HomeLocation> getPlayerHomes(ServerPlayer player) {
        return playerHomes.get(player.getUUID());
    }
}
