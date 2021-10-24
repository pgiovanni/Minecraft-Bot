package com.craftinc747.discord;

import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.java.JavaPlugin;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import javax.security.auth.login.LoginException;
import javax.xml.crypto.Data;
import java.io.File;
import java.net.*;

public class BroadcastReader extends JavaPlugin implements Listener {

    static public JDA jda;
    public String guildId;
    public String channelId;
    public String botId;
    public Guild guild;
    static public TextChannel channel;
    public File config;
    public DatagramSocket datagramSocket;
    public Inet4Address inet4Address;

    @Override
    public void onEnable() {

        try {
            this.inet4Address = (Inet4Address) Inet4Address.getByName("73.160.255.34");
            this.datagramSocket = new DatagramSocket(8080, inet4Address);
        }
        catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }

        config = new File(getDataFolder(), "src/main/resources/config.yml");
            if(!config.exists()){
                config.getParentFile().mkdirs();
                saveResource("src/main/resources/config.yml", false);
            }

        botId = "ODgyMzYyOTE5OTA2NjA3MTk4.YS6SgQ.eFeRMpA1OpKsiemm8CibA9Sf20Q";

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
        assert guild != null;
        channel = guild.getTextChannelById(channelId);
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

