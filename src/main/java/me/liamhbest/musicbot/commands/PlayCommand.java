package me.liamhbest.musicbot.commands;

import com.sedmelluq.discord.lavaplayer.format.AudioDataFormat;
import com.sedmelluq.discord.lavaplayer.format.AudioPlayerInputStream;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeSearchProvider;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import me.liamhbest.musicbot.utility.Utils;
import me.liamhbest.musicbot.utility.lavaplayer.AudioPlayerSendHandler;
import me.liamhbest.musicbot.utility.lavaplayer.TrackScheduler;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.sound.sampled.AudioInputStream;
import java.awt.*;
import java.util.function.Function;

public class PlayCommand extends ListenerAdapter {

    public static AudioPlayer widePlayer;
    public static TrackScheduler trackScheduler;
    public static AudioPlayerManager playerManager;

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        String command = "!play";
        String[] args = event.getMessage().getContentRaw().split(" ");

        if (args[0].equalsIgnoreCase(command)){
            if (!Utils.hasMusicBotPermission(event.getMember())) return;

            if (args.length <= 1){
                if (Utils.botInVoiceChannel(event)) {
                    EmbedBuilder embed = new EmbedBuilder();
                    embed.setColor(Color.GREEN);
                    embed.setAuthor("Please enter something to a song for the bot to play. Use !play [link]");
                    event.getChannel().sendMessage(embed.build()).queue();
                } else {
                    EmbedBuilder embed = new EmbedBuilder();
                    embed.setColor(Color.YELLOW);
                    embed.setAuthor("The bot needs to be in a voice channel to perform this command! Do !start while being in a voice channel.");
                    event.getChannel().sendMessage(embed.build()).queue();
                }
                return;
            }

            if (!Utils.botInVoiceChannel(event)) {
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(Color.YELLOW);
                embed.setAuthor("The bot needs to be in a voice channel to perform this command! Do !start while being in a voice channel.");
                event.getChannel().sendMessage(embed.build()).queue();
                return;
            }

            playerManager = new DefaultAudioPlayerManager();
            AudioSourceManagers.registerRemoteSources(playerManager);
            AudioPlayer player = playerManager.createPlayer();
            widePlayer = player;

            trackScheduler = new TrackScheduler(player);
            player.addListener(trackScheduler);
            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(Color.MAGENTA);
            embed.setTitle(":computer: Searching...");
            embed.appendDescription("I am browsing through the internet trying to search and load your song...");
            event.getChannel().sendMessage(embed.build()).queue();
            event.getGuild().getAudioManager().setSendingHandler(new AudioPlayerSendHandler(player));

            String identifier = args[1];

            if (!identifier.contains("youtube.com")) {
                // Create search from this
                YoutubeSearchProvider youtubeSearchProvider = new YoutubeSearchProvider();

                Function<AudioTrackInfo, AudioTrack> trackFunction = audioTrackInfo -> null;
                AudioTrack audioTrack = (AudioTrack) youtubeSearchProvider.loadSearchResult(identifier, trackFunction);
            } else {
                identifier = identifier+"=0";
            }

            playerManager.loadItem(identifier, new AudioLoadResultHandler() {
                @Override
                public void trackLoaded(AudioTrack track) {
                    trackScheduler.play(track, event.getChannel());
                }

                @Override
                public void playlistLoaded(AudioPlaylist playlist) {
                    boolean firstTime = false;

                    for (AudioTrack track : playlist.getTracks()) {
                        if (!firstTime){
                            firstTime = true;
                            TrackScheduler.play(track, event.getChannel());
                        } else {
                            TrackScheduler.queue.add(track);
                        }
                    }
                    EmbedBuilder embed = new EmbedBuilder();
                    embed.setColor(Color.MAGENTA);
                    embed.setTitle(":satellite: Playlist Queued");
                    embed.appendDescription("You have queued an entire playlist.");
                    event.getChannel().sendMessage(embed.build()).queue();
                }

                @Override
                public void noMatches() {
                    // Notify the user that we've got nothing
                    EmbedBuilder embed = new EmbedBuilder();
                    embed.setColor(Color.YELLOW);
                    embed.setTitle(":no_entry: Song not found!");
                    embed.appendDescription("The bot was not able to find the song you were looking for!\n");
                    embed.appendDescription("Please use youtube video links for the highest accuracy!");
                    event.getChannel().sendMessage(embed.build()).queue();
                }

                @Override
                public void loadFailed(FriendlyException error) {
                    // Notify the user that everything explode
                    EmbedBuilder embed = new EmbedBuilder();
                    embed.setColor(Color.RED);
                    embed.setTitle(":no_entry: Error!");
                    embed.appendDescription("Something went wrong while trying to play that track!\n");
                    embed.appendDescription("Please try again and contact the bot developer LiamHBest#0001 if this keeps happening!");
                    event.getChannel().sendMessage(embed.build()).queue();

                    error.printStackTrace();
                }
            });

            AudioDataFormat format = playerManager.getConfiguration().getOutputFormat();
            AudioInputStream stream = AudioPlayerInputStream.createStream(player, format, 10000L, false);

        }


    }

}


















