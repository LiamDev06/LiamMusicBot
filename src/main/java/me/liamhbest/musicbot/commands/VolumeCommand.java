package me.liamhbest.musicbot.commands;

import me.liamhbest.musicbot.utility.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class VolumeCommand extends ListenerAdapter {

    public static int volume = 100;

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        String command = "!volume";
        String[] args = event.getMessage().getContentRaw().split(" ");

        if (args[0].equalsIgnoreCase(command) && !event.getAuthor().isBot()) {
            if (!Utils.hasMusicBotPermission(event.getMember())) return;

            if (args.length > 1){
                volume = Integer.parseInt(args[1]);
                event.getChannel().sendMessage(":loud_sound: Changed the volume from `" + PlayCommand.widePlayer.getVolume() + "` to `" + volume + "`.").queue();
                PlayCommand.widePlayer.setVolume(volume);
            } else {
                EmbedBuilder embed = new EmbedBuilder();
                embed.setTitle(":loud_sound: Volume");
                embed.appendDescription("The volume is currently set to `" + PlayCommand.widePlayer.getVolume() + "`\n");
                embed.appendDescription("You can change the volume by doing **!volume [value]**.");
                embed.setColor(Color.ORANGE);
                event.getChannel().sendMessage(embed.build()).queue();
            }

        }

    }

}







