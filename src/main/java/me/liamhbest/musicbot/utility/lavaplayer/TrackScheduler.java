package me.liamhbest.musicbot.utility.lavaplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventListener;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import me.liamhbest.musicbot.LiamMusicBot;
import me.liamhbest.musicbot.commands.LoopCommand;
import me.liamhbest.musicbot.commands.PlayCommand;
import me.liamhbest.musicbot.commands.VolumeCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.util.ArrayList;

public class TrackScheduler extends AudioEventAdapter implements AudioEventListener {

    public static AudioPlayer player = null;
    public static ArrayList<AudioTrack> queue = new ArrayList<>();
    private static TextChannel channel;

    public TrackScheduler(AudioPlayer player1) {
        player = player1;
    }

    public static void play(AudioTrack track, TextChannel sendChannel){
        channel = sendChannel;
        player.playTrack(track);
    }

    @Override
    public void onPlayerPause(AudioPlayer player) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(Color.MAGENTA);
        embed.setTitle(":stopwatch: Song Paused");
        embed.appendDescription("You paused the current song that is playing.");
        channel.sendMessage(embed.build()).queue();
    }

    @Override
    public void onPlayerResume(AudioPlayer player) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(Color.MAGENTA);
        embed.setTitle(":white_check_mark: Song Resumed");
        embed.appendDescription("You started playing the song again after the pause.");
        channel.sendMessage(embed.build()).queue();
    }

    @Override
    public void onTrackStart(AudioPlayer player, AudioTrack track) {
        try {
            queue.remove(track);
        } catch (Exception ignored){ }

        PlayCommand.widePlayer.setVolume(VolumeCommand.volume);
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(Color.GREEN);
        embed.setTitle(":loud_sound: Now Playing");
        embed.appendDescription(track.getInfo().title + " - " + track.getInfo().author);

        if (LiamMusicBot.announcementChannel == null){
            channel.sendMessage(embed.build()).queue();
            return;
        }

        LiamMusicBot.announcementChannel.sendMessage(embed.build()).queue();
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        if (LoopCommand.shouldLoop) {
            channel.sendMessage(":loop: Looping song...").queue();
            track.setPosition(0);
            player.playTrack(track);
            return;
        }

        if (!queue.isEmpty()) {
            player.playTrack(queue.get(0));
        }

    }

    @Override
    public void onTrackException(AudioPlayer player, AudioTrack track, FriendlyException exception) {
        //TODO Something went wrong with that track, starting a new one
    }

    @Override
    public void onTrackStuck(AudioPlayer player, AudioTrack track, long thresholdMs) {
        //TODO Something went wrong with that track, starting a new one
    }
}
