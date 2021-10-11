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
import java.sql.*;

public class BroadcastReader extends JavaPlugin implements Listener {

    static public JDA jda;
    public String guildId;
    public String channelId;
    public String botId;
    public Guild guild;
    static public TextChannel channel;
    public Connection con;
    public ResultSet resultSet;
    public Statement statement;
    public FileConfiguration config;

    @Override
    public void onEnable() {

        config = this.getConfig();


        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/logReader", "paul", "Smellyf33t");
            statement = con.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM GuildInformation WHERE GuildName='Paul G';");
            while(resultSet.next()) {
                guildId = resultSet.getString(2);
                channelId = resultSet.getString(3);
            }
            con.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
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

