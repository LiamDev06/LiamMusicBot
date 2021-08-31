package me.liamhbest.musicbot.commands;

import me.liamhbest.musicbot.utility.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

import java.awt.*;

public class StopCommand extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        String command = "!stop";

        if (event.getMessage().getContentRaw().equalsIgnoreCase(command)
        && !event.getAuthor().isBot()) {
            if (!Utils.hasMusicBotPermission(event.getMember())) return;

            AudioManager audioManager = event.getGuild().getAudioManager();
            VoiceChannel connectedChannel = event.getGuild().getSelfMember().getVoiceState().getChannel();
            if (connectedChannel == null){
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(Color.YELLOW);
                embed.setAuthor("The bot is currently not in a voice channel and therefore this command is useless!");
                event.getChannel().sendMessage(embed.build()).queue();
                return;
            }

            audioManager.closeAudioConnection();
            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(Color.RED);
            embed.setTitle(":red_circle: Bot Stopped");
            embed.appendDescription("You force stopped all music and the bot left the voice channel!");
            event.getChannel().sendMessage(embed.build()).queue();
        }


    }

}





