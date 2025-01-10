package com.homemod.commands;

import com.homemod.data.HomeManager;
import com.homemod.events.CombatTracker;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;

public class SetHomeCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("sethome")
            .then(Commands.argument("name", StringArgumentType.word())
            .executes(context -> {
                ServerPlayer player = context.getSource().getPlayerOrException();
                String homeName = StringArgumentType.getString(context, "name");

                if (CombatTracker.isInCombat(player)) {
                    player.displayClientMessage(
                        new TextComponent("§c§lVocê não pode setar home em combate!"),
                        true
                    );
                    return 0;
                }

                if (HomeManager.addHome(player, homeName)) {
                    player.displayClientMessage(
                        new TextComponent("§a§lHome '" + homeName + "' definida com sucesso!"),
                        true
                    );
                } else {
                    player.displayClientMessage(
                        new TextComponent("§c§lVocê já atingiu o limite de 3 homes!"),
                        true
                    );
                }
                return 1;
            })));
    }
}
