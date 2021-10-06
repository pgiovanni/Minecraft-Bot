package com.craftinc747.discord;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.java.JavaPlugin;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import javax.security.auth.login.LoginException;

public class BroadcastReader extends JavaPlugin implements Listener  {

    static public JDA jda;
    public String guildId;
    public String channelId;
    public String botId;
    public Guild guild;
    static public TextChannel channel;

    @Override
    public void onEnable() {

        guildId = "758813062848708670";
        channelId = "758813063448363011";
        botId = "ODgyMzYyOTE5OTA2NjA3MTk4.YS6SgQ.gVcH_kzRTwlWvEYKMiuYZPM50iM";

        getServer().getPluginManager().registerEvents(this, this);

        try {
            jda = JDABuilder.createDefault(botId).addEventListeners(new GuildMessageListener()).build();
        } catch (LoginException e) {
            e.printStackTrace();
        }

        try {
            jda.awaitReady();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        guild = jda.getGuildById(guildId);
        if (guild == null)
            System.out.println("guild is null");
        channel = guild.getTextChannelById(channelId);
        if (channel == null)
            System.out.println("channel is null");
    }
    @EventHandler
    public void onPlayerChatEvent (AsyncPlayerChatEvent event) {
        channel.sendMessage(event.getMessage()).queue();
    }

    @EventHandler
    public void onPlayerCommandPreprocessEvent (PlayerCommandPreprocessEvent event) {
        channel.sendMessage(event.getMessage()).queue();
    }

    @EventHandler
    public void onDisable (PluginDisableEvent event) {
        jda.shutdown();
    }
}

