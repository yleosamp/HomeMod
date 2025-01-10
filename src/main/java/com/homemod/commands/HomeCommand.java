package com.homemod.commands;

import com.homemod.data.HomeManager;
import com.homemod.events.CombatTracker;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public class HomeCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("home")
            .then(Commands.argument("name", StringArgumentType.word())
            .executes(context -> {
                ServerPlayer player = context.getSource().getPlayerOrException();
                String homeName = StringArgumentType.getString(context, "name");

                if (CombatTracker.isInCombat(player)) {
                    player.displayClientMessage(
                        new TextComponent("§c§lVocê não pode teleportar em combate!"),
                        true
                    );
                    return 0;
                }

                HomeManager.HomeLocation home = HomeManager.getHome(player, homeName);
                if (home == null) {
                    player.displayClientMessage(
                        new TextComponent("§c§lHome '" + homeName + "' não encontrada!"),
                        true
                    );
                    return 0;
                }

                player.displayClientMessage(
                    new TextComponent("§6§lTeleportando em 3 segundos..."),
                    true
                );

                new Thread(() -> {
                    try {
                        Thread.sleep(3000);
                        if (!CombatTracker.isInCombat(player)) {
                            ResourceLocation dimLocation = ResourceLocation.tryParse(home.dimension);
                            ResourceKey<Level> dimKey = ResourceKey.create(ResourceKey.createRegistryKey(
                                new ResourceLocation("dimension")), dimLocation);
                            
                            ServerLevel targetWorld = player.getServer().getLevel(dimKey);
                            player.teleportTo(targetWorld,
                                home.pos.getX() + 0.5,
                                home.pos.getY(),
                                home.pos.getZ() + 0.5,
                                player.getYRot(),
                                player.getXRot()
                            );
                            player.displayClientMessage(
                                new TextComponent("§a§lTeleportado com sucesso!"),
                                true
                            );
                        } else {
                            player.displayClientMessage(
                                new TextComponent("§c§lTeleporte cancelado - você entrou em combate!"),
                                true
                            );
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();

                return 1;
            })));
    }
}
