package com.grunclepug.rolerewardbot.bot.core;

import com.grunclepug.rolerewardbot.bot.util.RoleReward;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;

/**
 * Driver for RoleRewardBot
 */
public class Driver {
    public static JDA jda;

    /**
     * Main Method
     * @param args Arguments from terminal
     */
    public static void main(String[] args) {
        try {
            new Driver().setup();
            System.out.println("Intent Status: " + Driver.jda.getGatewayIntents().contains(GatewayIntent.GUILD_PRESENCES));
        } catch(LoginException e) {
            e.printStackTrace(System.err);
        }
    }

    /**
     * Initialize Bot
     * @throws LoginException Error connecting to Discord
     */
    private void setup() throws LoginException {
        Config.readFile();

        jda = JDABuilder.createDefault(Config.getToken()).enableCache(CacheFlag.ACTIVITY).setMemberCachePolicy(MemberCachePolicy.ONLINE).enableIntents(GatewayIntent.GUILD_PRESENCES).build();
        jda.getPresence().setStatus(OnlineStatus.ONLINE);
        jda.getPresence().setActivity(Activity.watching("user status for role rewards | GrunclePug#7015"));

        jda.addEventListener(new RoleReward());
    }
}
