package me.liamhbest.musicbot.commands;

import me.liamhbest.musicbot.utility.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class LoopCommand extends ListenerAdapter {

    public static boolean shouldLoop;

    public void GuildMessageReceivedEvent(GuildMessageReceivedEvent event){
        String command = "!loop";
        String[] args = event.getMessage().getContentRaw().split(" ");

        if (args[0].equalsIgnoreCase(command)) {
            /*
            if (!Utils.hasMusicBotPermission(event.getMember()) ||
            !event.getAuthor().getName().equalsIgnoreCase("LiamHBest")) return;

             */

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
            } else {
                shouldLoop = true;
                EmbedBuilder embed = new EmbedBuilder();
                embed.appendDescription(":loop: Loop mode has now been **enabled**.");
            }
        }
    }

}
