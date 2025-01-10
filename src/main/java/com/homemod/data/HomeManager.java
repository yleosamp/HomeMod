package com.homemod.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.core.BlockPos;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

@Mod.EventBusSubscriber
public class HomeManager {
    private static final Map<String, Map<String, HomeLocation>> playerHomes = new HashMap<>();
    private static final int MAX_HOMES = 3;
    private static final String SAVE_FILE = "homes.json";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static class HomeLocation {
        public final BlockPos pos;
        public final String dimension;

        public HomeLocation(BlockPos pos, String dimension) {
            this.pos = pos;
            this.dimension = dimension;
        }
    }

    public static boolean addHome(ServerPlayer player, String homeName) {
        String playerName = player.getName().getString();
        Map<String, HomeLocation> homes = playerHomes.computeIfAbsent(playerName, k -> new HashMap<>());

        if (homes.size() >= MAX_HOMES && !homes.containsKey(homeName)) {
            return false;
        }

        homes.put(homeName, new HomeLocation(
            player.blockPosition(),
            player.level.dimension().location().toString()
        ));
        saveHomes();
        return true;
    }

    public static boolean deleteHome(ServerPlayer player, String homeName) {
        String playerName = player.getName().getString();
        Map<String, HomeLocation> homes = playerHomes.get(playerName);
        if (homes != null) {
            boolean result = homes.remove(homeName) != null;
            if (result) {
                saveHomes();
            }
            return result;
        }
        return false;
    }

    public static HomeLocation getHome(ServerPlayer player, String homeName) {
        String playerName = player.getName().getString();
        Map<String, HomeLocation> homes = playerHomes.get(playerName);
        if (homes != null) {
            return homes.get(homeName);
        }
        return null;
    }

    public static Map<String, HomeLocation> getPlayerHomes(ServerPlayer player) {
        return playerHomes.get(player.getName().getString());
    }

    private static void saveHomes() {
        try (Writer writer = new FileWriter(SAVE_FILE)) {
            GSON.toJson(playerHomes, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadHomes() {
        File file = new File(SAVE_FILE);
        if (file.exists()) {
            try (Reader reader = new FileReader(file)) {
                Type type = new TypeToken<Map<String, Map<String, HomeLocation>>>(){}.getType();
                Map<String, Map<String, HomeLocation>> loaded = GSON.fromJson(reader, type);
                if (loaded != null) {
                    playerHomes.clear();
                    playerHomes.putAll(loaded);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event) {
        loadHomes();
    }

    @SubscribeEvent
    public static void onServerStopping(ServerStoppingEvent event) {
        saveHomes();
    }
}
