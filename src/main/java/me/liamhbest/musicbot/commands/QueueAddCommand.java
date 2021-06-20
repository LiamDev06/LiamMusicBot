package me.liamhbest.musicbot.commands;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import me.liamhbest.musicbot.utility.Utils;
import me.liamhbest.musicbot.utility.lavaplayer.TrackScheduler;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class QueueAddCommand extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        String command = "!queueadd";
        String[] args = event.getMessage().getContentRaw().split(" ");

        if (args[0].equalsIgnoreCase(command)) {
            if (!Utils.hasMusicBotPermission(event.getMember())) return;

            if (!Utils.botInVoiceChannel(event)) {
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(Color.RED);
                embed.setAuthor("There is no music currently playing!");
                event.getChannel().sendMessage(embed.build()).queue();
                return;
            }

            if (args.length <= 1){
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(Color.YELLOW);
                embed.setAuthor("Please enter something to a song for the bot to play. Use !queueadd [link]");
                event.getChannel().sendMessage(embed.build()).queue();
                return;
            }

            PlayCommand.playerManager.loadItem(args[1] + "=0", new AudioLoadResultHandler() {
                @Override
                public void trackLoaded(AudioTrack track) {
                    TrackScheduler.queue.add(track);
                    EmbedBuilder embed = new EmbedBuilder();
                    embed.setColor(Color.MAGENTA);
                    embed.setTitle(":satellite: Song Queued");
                    embed.appendDescription("You have queued " + track.getInfo().title + ".");
                    event.getChannel().sendMessage(embed.build()).queue();
                }

                @Override
                public void playlistLoaded(AudioPlaylist playlist) {
                    TrackScheduler.queue.addAll(playlist.getTracks());
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
                public void loadFailed(FriendlyException throwable) {
                    // Notify the user that everything explode
                    EmbedBuilder embed = new EmbedBuilder();
                    embed.setColor(Color.RED);
                    embed.setTitle(":no_entry: Error!");
                    embed.appendDescription("Something went wrong while trying to play that track!\n");
                    embed.appendDescription("Please try again and contact the bot developer Liam if this keeps happening!");
                    event.getChannel().sendMessage(embed.build()).queue();
                }
            });


        }

    }

}
