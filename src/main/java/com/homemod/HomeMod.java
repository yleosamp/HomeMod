package com.homemod;

import com.homemod.commands.*;
import com.homemod.events.CombatTracker;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod("homemod")
public class HomeMod {
    public HomeMod() {
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new CombatTracker());
    }

    @SubscribeEvent
    public void onCommandsRegister(RegisterCommandsEvent event) {
        SetHomeCommand.register(event.getDispatcher());
        DelHomeCommand.register(event.getDispatcher());
        HomeCommand.register(event.getDispatcher());
        ListHomesCommand.register(event.getDispatcher());
    }
}
