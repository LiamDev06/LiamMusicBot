package me.liamhbest.musicbot.utility.lavaplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventListener;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
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
        // Player was paused
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(Color.MAGENTA);
        embed.setTitle(":stopwatch: Song Paused");
        embed.appendDescription("You paused the current song that is playing.");
        channel.sendMessage(embed.build()).queue();
    }

    @Override
    public void onPlayerResume(AudioPlayer player) {
        // Player was resumed
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(Color.MAGENTA);
        embed.setTitle(":white_check_mark: Song Resumed");
        embed.appendDescription("You started playing the song again after the pause.");
        channel.sendMessage(embed.build()).queue();
    }

    @Override
    public void onTrackStart(AudioPlayer player, AudioTrack track) {
        // A track started playing
        queue.remove(track);
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(Color.GREEN);
        embed.setTitle(":loud_sound: Now Playing");
        embed.appendDescription(track.getInfo().title + " - " + track.getInfo().author);
        channel.sendMessage(embed.build()).queue();
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        if (!queue.isEmpty()) {
            player.playTrack(queue.get(0));
        }

        // endReason == FINISHED: A track finished or died by an exception (mayStartNext = true).
        // endReason == LOAD_FAILED: Loading of a track failed (mayStartNext = true).
        // endReason == STOPPED: The player was stopped.
        // endReason == REPLACED: Another track started playing while this had not finished
        // endReason == CLEANUP: Player hasn't been queried for a while, if you want you can put a
        //                       clone of this back to your queue
    }

    @Override
    public void onTrackException(AudioPlayer player, AudioTrack track, FriendlyException exception) {
        // An already playing track threw an exception (track end event will still be received separately)
    }

    @Override
    public void onTrackStuck(AudioPlayer player, AudioTrack track, long thresholdMs) {
        // Audio track has been unable to provide us any audio, might want to just start a new track
    }
}
