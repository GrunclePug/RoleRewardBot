package com.grunclepug.rolerewardbot.bot.util;

import com.grunclepug.rolerewardbot.bot.core.Config;
import com.grunclepug.rolerewardbot.bot.core.Driver;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.user.update.UserUpdateActivitiesEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.Date;

/**
 * Event Listener to check updates to user status
 * @author GrunclePug
 */
public class RoleReward extends ListenerAdapter {
    public static final String LOG_CHANNEL = "943334280417910796";
    public static final String VANITY_URL = "test.co";
    public static final String ROLE_ID = "943609974842593362";
    private static EmbedBuilder builder = new EmbedBuilder();

    /**
     * Check for user custom status/activity updates
     * @param event UserUpdateActivities event
     */
    @Override
    public void onUserUpdateActivities(UserUpdateActivitiesEvent event) {
        Date date = new Date();
        builder.clear();

        if(!event.getNewValue().isEmpty() && event.getNewValue().get(0).getName().contains(VANITY_URL)) {
            event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(ROLE_ID)).queue();
            SupporterCount.updateCount("ADD");

            builder.setTitle("New Supporter!")
                    .appendDescription("\uD83D\uDC51 " + event.getMember().getAsMention() + " has put " + VANITY_URL + " in their status! They now have the " + event.getGuild().getRoleById(ROLE_ID).getAsMention() + " role.")
                    .setColor(Color.GREEN)
                    .setThumbnail(event.getGuild().getIconUrl())
                    .setFooter(Config.DATE_FORMAT.format(date), "https://imgur.com/Y76sIok.png");

            try {
                Driver.jda.getTextChannelById(LOG_CHANNEL).sendMessageEmbeds(builder.build()).queue();
                Driver.jda.getTextChannelById(LOG_CHANNEL).getManager().setName("Supporters: " + SupporterCount.getCount()).queue();
            } catch(Exception e) {
                // Ignore if embed is empty
            }
        } else {
            if(event.getMember().getRoles().contains(event.getGuild().getRoleById(ROLE_ID))) {
                event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById(ROLE_ID)).queue();
                SupporterCount.updateCount("REMOVE");

                builder.setTitle("Supporter Revoked")
                        .appendDescription("\uD83D\uDFE5 " + event.getMember().getAsMention() + " has removed " + VANITY_URL + " from their status :(. The " + event.getGuild().getRoleById(ROLE_ID).getAsMention() + " role has been revoked.")
                        .setColor(Color.RED)
                        .setThumbnail(event.getGuild().getIconUrl())
                        .setFooter(Config.DATE_FORMAT.format(date), "https://imgur.com/Y76sIok.png");

                try {
                    Driver.jda.getTextChannelById(LOG_CHANNEL).sendMessageEmbeds(builder.build()).queue();
                    Driver.jda.getTextChannelById(LOG_CHANNEL).getManager().setName("Supporters: " + SupporterCount.getCount()).queue();
                } catch(Exception e) {
                    // Ignore if user doesn't have role
                }
            }
        }
    }
}
