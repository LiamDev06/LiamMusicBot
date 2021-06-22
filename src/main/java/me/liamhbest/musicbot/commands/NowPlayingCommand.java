package me.liamhbest.musicbot.commands;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import me.liamhbest.musicbot.utility.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class NowPlayingCommand extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        String command1 = "!nowplaying";
        String command2 = "!np";

        if (event.getMessage().getContentRaw().equalsIgnoreCase(command1) ||
        event.getMessage().getContentRaw().equalsIgnoreCase(command2) &&
        !event.getAuthor().isBot()){
            if (!Utils.hasMusicBotPermission(event.getMember())) return;

            if (!Utils.botInVoiceChannel(event)) {
                event.getChannel().sendMessage("Nothing is currently playing").queue();
                return;
            }

            if (PlayCommand.widePlayer.getPlayingTrack() == null){
                event.getChannel().sendMessage("Nothing is currently playing").queue();
                return;
            }

            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle(":notes: Currently Playing");
            embed.setColor(Color.YELLOW);
            embed.appendDescription("**Title:** " + PlayCommand.widePlayer.getPlayingTrack().getInfo().title + "\n");
            embed.appendDescription("**Artist:** " + PlayCommand.widePlayer.getPlayingTrack().getInfo().author + "\n");
            embed.appendDescription("**Volume:** " + PlayCommand.widePlayer.getVolume() + "\n\n");
            embed.appendDescription(":musical_note:  " + getProgressBar(PlayCommand.widePlayer.getPlayingTrack()) + "  " +
                    getTimer());
            event.getChannel().sendMessage(embed.build()).queue();
        }

    }

    public static String getProgressBar(AudioTrack track) {
        float percentage = (100f / track.getDuration() * track.getPosition());
        return "[" + repeat("▬", (int) Math.round((double) percentage / 10)) +
                "](https://github.com/LiamHBest0608)" +
                repeat("▬", 10 - (int) Math.round((double) percentage / 10));
    }

    public static String repeat(String input, int times){
        StringBuilder output = new StringBuilder();

        for (int i = 0; i<times; i++){
            output.append(input);
        }

        return output.toString();
    }

    public static String getTimer(){
        // 00:44/05:09

        long positionSeconds = ((PlayCommand.widePlayer.getPlayingTrack().getPosition()/1000)/60);
        long positionMinute = ((PlayCommand.widePlayer.getPlayingTrack().getPosition()/1000)%60);

        long durationSeconds = ((PlayCommand.widePlayer.getPlayingTrack().getDuration()/1000)/60);
        long durationMinute = ((PlayCommand.widePlayer.getPlayingTrack().getDuration()/1000)%60);
        
        String output = positionSeconds + ":" + positionMinute + "/" + durationSeconds + ":" + durationMinute;

        return output;
    }



}




