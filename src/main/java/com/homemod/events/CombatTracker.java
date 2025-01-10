package com.homemod.events;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CombatTracker {
    private static final Map<UUID, Long> combatTimers = new HashMap<>();
    private static final long COMBAT_COOLDOWN = 10000; // 10 segundos

    @SubscribeEvent
    public void onDamage(LivingDamageEvent event) {
        if (event.getEntity() instanceof ServerPlayer) {
            combatTimers.put(event.getEntity().getUUID(), System.currentTimeMillis());
        }
        if (event.getSource().getEntity() instanceof ServerPlayer) {
            combatTimers.put(event.getSource().getEntity().getUUID(), System.currentTimeMillis());
        }
    }

    public static boolean isInCombat(ServerPlayer player) {
        Long lastCombat = combatTimers.get(player.getUUID());
        return lastCombat != null && 
               System.currentTimeMillis() - lastCombat < COMBAT_COOLDOWN;
    }
}
