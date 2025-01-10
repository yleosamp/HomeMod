package com.homemod.commands;

import com.homemod.data.HomeManager;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;

public class DelHomeCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("delhome")
            .then(Commands.argument("name", StringArgumentType.word())
            .executes(context -> {
                ServerPlayer player = context.getSource().getPlayerOrException();
                String homeName = StringArgumentType.getString(context, "name");

                if (HomeManager.deleteHome(player, homeName)) {
                    player.displayClientMessage(
                        new TextComponent("§a§lHome '" + homeName + "' deletada com sucesso!"),
                        true
                    );
                } else {
                    player.displayClientMessage(
                        new TextComponent("§c§lHome '" + homeName + "' não encontrada!"),
                        true
                    );
                }
                return 1;
            })));
    }
}
