package com.craftinc747.discord;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.jetbrains.annotations.NotNull;

public class GuildMessageListener extends ListenerAdapter {

    public Server server;
    public Message message;
    public long id;

    public void onGuildMessageReceived (@NotNull GuildMessageReceivedEvent event) {
        server = Bukkit.getServer();
        server.broadcastMessage(event.getMessage().getContentRaw());

    }
}
