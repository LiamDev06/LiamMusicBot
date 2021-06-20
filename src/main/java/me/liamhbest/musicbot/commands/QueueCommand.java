package me.liamhbest.musicbot.commands;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import me.liamhbest.musicbot.utility.Utils;
import me.liamhbest.musicbot.utility.lavaplayer.TrackScheduler;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class QueueCommand extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        String command = "!queue";

        if (event.getMessage().getContentRaw().equalsIgnoreCase(command) &&
                !event.getAuthor().isBot()) {
            if (!Utils.hasMusicBotPermission(event.getMember())) return;

            if (!Utils.botInVoiceChannel(event)) {
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(Color.RED);
                embed.setAuthor("There is no music currently playing!");
                event.getChannel().sendMessage(embed.build()).queue();
                return;
            }

            if (TrackScheduler.queue.isEmpty()){
                EmbedBuilder embed = new EmbedBuilder();
                embed.setTitle("Queue Empty");
                embed.setColor(Color.YELLOW);
                embed.appendDescription("The queue is currently empty!\n");
                embed.appendDescription("You can add songs to it by doing !queueadd [link]");
                event.getChannel().sendMessage(embed.build()).queue();
                return;
            }

            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("Song Queue");
            embed.setColor(Color.ORANGE);
            int times = 0;

            for (AudioTrack track : TrackScheduler.queue){
                times++;
                embed.appendDescription("**" + times + "**: " + track.getInfo().author + ", " + track.getInfo().title + "\n");
            }

            event.getChannel().sendMessage(embed.build()).queue();

        }

    }

}










