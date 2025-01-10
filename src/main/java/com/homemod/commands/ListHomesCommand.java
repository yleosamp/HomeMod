package com.homemod.commands;

import com.homemod.data.HomeManager;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;

import java.util.Map;

public class ListHomesCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("homes")
            .executes(context -> {
                ServerPlayer player = context.getSource().getPlayerOrException();
                Map<String, HomeManager.HomeLocation> homes = HomeManager.getPlayerHomes(player);

                if (homes == null || homes.isEmpty()) {
                    player.displayClientMessage(
                        new TextComponent("§e§lVocê não tem nenhuma home definida!"),
                        false
                    );
                    return 0;
                }

                player.displayClientMessage(
                    new TextComponent("§6§l=== Suas Homes ==="),
                    false
                );

                homes.forEach((name, location) -> {
                    player.displayClientMessage(
                        new TextComponent(String.format(
                            "§e- %s§r: §7%d, %d, %d §8(%s)",
                            name,
                            location.pos.getX(),
                            location.pos.getY(),
                            location.pos.getZ(),
                            location.dimension
                        )),
                        false
                    );
                });

                return 1;
            }));
    }
} 