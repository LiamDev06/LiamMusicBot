package me.liamhbest.musicbot.commands;

import me.liamhbest.musicbot.utility.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.awt.*;

public class LoopCommand extends ListenerAdapter {

    public static boolean shouldLoop;

    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event){
        String command = "!loop";

        if (event.getMessage().getContentRaw().equalsIgnoreCase(command)) {
            if (!Utils.hasMusicBotPermission(event.getMember())) return;

            if (!Utils.botInVoiceChannel(event)) {
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(Color.RED);
                embed.setAuthor("There is no music currently playing!");
                event.getChannel().sendMessage(embed.build()).queue();
                return;
            }

            if (PlayCommand.widePlayer.getPlayingTrack() == null){
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(Color.RED);
                embed.setAuthor("There is no music currently playing!");
                event.getChannel().sendMessage(embed.build()).queue();
                return;
            }

            if (shouldLoop) {
                shouldLoop = false;
                EmbedBuilder embed = new EmbedBuilder();
                embed.appendDescription(":loop: Loop mode has now been **disabled**.");
                event.getChannel().sendMessage(embed.build()).queue();
            } else {
                shouldLoop = true;
                EmbedBuilder embed = new EmbedBuilder();
                embed.appendDescription(":loop: Loop mode has now been **enabled**.");
                event.getChannel().sendMessage(embed.build()).queue();
            }
        }
    }

}
