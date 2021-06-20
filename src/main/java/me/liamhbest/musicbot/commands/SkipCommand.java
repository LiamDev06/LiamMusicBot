package me.liamhbest.musicbot.commands;

import me.liamhbest.musicbot.utility.Utils;
import me.liamhbest.musicbot.utility.lavaplayer.TrackScheduler;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class SkipCommand extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String command = "!skip";

        if (event.getMessage().getContentRaw().equalsIgnoreCase(command)) {
            if (!Utils.hasMusicBotPermission(event.getMember())) return;

            if (!Utils.botInVoiceChannel(event)) {
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(Color.RED);
                embed.setAuthor("There is no music currently playing!");
                event.getChannel().sendMessage(embed.build()).queue();
                return;
            }

            if (PlayCommand.widePlayer.getPlayingTrack() == null) {
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(Color.RED);
                embed.setAuthor("There is no music currently playing!");
                event.getChannel().sendMessage(embed.build()).queue();
                return;
            }

            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(Color.YELLOW);
            embed.setAuthor("Song skipped...");
            event.getChannel().sendMessage(embed.build()).queue();
            PlayCommand.widePlayer.stopTrack();

        }

    }
}







