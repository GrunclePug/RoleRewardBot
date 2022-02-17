package com.grunclepug.rolerewardbot.bot.util;

import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SupporterCountLoader extends ListenerAdapter {
    public void onReady(ReadyEvent event) {
        SupporterCount.loadCount();
    }
}
