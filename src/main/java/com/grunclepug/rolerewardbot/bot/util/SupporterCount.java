package com.grunclepug.rolerewardbot.bot.util;

import com.grunclepug.rolerewardbot.bot.core.Driver;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;

import java.util.List;

/**
 * Class to update and manage Supporter Count
 * @author GrunclePug
 */
public class SupporterCount {
    private static final String GUILD_ID = "942793537332842546";
    private static int count = 0;

    private SupporterCount() {}

    /**
     * Load the amount of Supporters
     */
    public static void loadCount() {
        count = 0;
        Guild guild = Driver.jda.getGuildById(GUILD_ID);
        List<Member> members = guild.getMembers();
        for(Member m : members) {
            if(m.getRoles().contains(Driver.jda.getRoleById(RoleReward.ROLE_ID))) {
                count += 1;
            }
        }
    }

    /**
     * Update the amount of Supporters
     * @param change Increment/Decrement the amount of Supporters by 1. use "ADD" or "REMOVE".
     */
    public static void updateCount(String change) {
        switch(change.toUpperCase()) {
            case "ADD":
                count += 1;
                break;
            case "REMOVE":
                if(count > 0) {
                    count -= 1;
                }
                break;
            default:
                System.out.println("Error: invalid option, please choose 'ADD' or 'REMOVE'");
        }
    }

    /**
     * Accessor for amount of Supporters
     * @return Amount of Supporters
     */
    public static int getCount() {
        return count;
    }
}
