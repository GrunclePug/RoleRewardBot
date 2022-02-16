package com.grunclepug.rolerewardbot.bot.util;

import com.grunclepug.rolerewardbot.bot.core.Config;
import com.grunclepug.rolerewardbot.bot.core.Driver;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.user.update.UserUpdateActivitiesEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Date;

public class RoleReward extends ListenerAdapter {
    public static final String LOG_CHANNEL = "943345479293472768";
    public static final String VANITY_URL = "test.co";
    public static final String ROLE_ID = "943347321536671755";
    private static EmbedBuilder builder = new EmbedBuilder();

    @Override
    public void onUserUpdateActivities(UserUpdateActivitiesEvent event) {
        Date date = new Date();
        builder.clear();

        if(!event.getNewValue().isEmpty() && event.getNewValue().get(0).getName().contains(VANITY_URL)) {
            event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(ROLE_ID)).queue();

            builder.setTitle("__Role Rewarded__")
                    .addField("User", event.getMember().getAsMention() + "\n" + event.getMember().getId(), false)
                    .setFooter(Config.DATE_FORMAT.format(date), "https://i.imgur.com/mK2zlbr.png");

            try {
                Driver.jda.getTextChannelById(LOG_CHANNEL).sendMessage((Message) builder.build()).queue();
            } catch(Exception e) {
                // Ignore if embed is empty
            }
        } else {
            if(event.getMember().getRoles().contains(event.getGuild().getRoleById(ROLE_ID))) {
                event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById(ROLE_ID)).queue();

                builder.setTitle("__Role Removed__")
                        .addField("User", event.getMember().getAsMention() + "\n" + event.getMember().getId(), false)
                        .setFooter(Config.DATE_FORMAT.format(date), "https://i.imgur.com/mK2zlbr.png");

                try {
                    Driver.jda.getTextChannelById(LOG_CHANNEL).sendMessage((Message) builder.build()).queue();
                } catch(Exception e) {
                    // Ignore if embed is empty
                }
            }
        }
    }
}
