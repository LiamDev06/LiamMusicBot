package me.liamhbest.musicbot.commands;

import me.liamhbest.musicbot.LiamMusicBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class AnnounceChannelCommand extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event){

        if (event.getMessage().getContentRaw().equalsIgnoreCase("!setannouncechannel")) {
            if (LiamMusicBot.announcementChannel == null){
                EmbedBuilder embed = new EmbedBuilder();
                embed.setTitle("Announcement Channel");
                embed.setColor(Color.MAGENTA);
                embed.appendDescription("You set the song announcement channel to this channel (<#" + event.getChannel().getId() + ">).");
                event.getChannel().sendMessage(embed.build()).queue();

                LiamMusicBot.announcementChannel = event.getChannel();
                return;
            }

            if (event.getChannel().getIdLong() == LiamMusicBot.announcementChannel.getIdLong()){
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(Color.RED);
                embed.setAuthor("The announcement channel is already set to this channel!");
                event.getChannel().sendMessage(embed.build()).queue();
                return;
            }

            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("Announcement Channel");
            embed.setColor(Color.MAGENTA);
            embed.appendDescription("You changed the song announcement channel from <#" + LiamMusicBot.announcementChannel.getId() + "> to this channel (<#" + event.getChannel().getId() + ">).");
            event.getChannel().sendMessage(embed.build()).queue();

            LiamMusicBot.announcementChannel = event.getChannel();
        }

    }

}
















