package me.liamhbest.musicbot.commands;

import me.liamhbest.musicbot.utility.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class MusicHelpCommand extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        String command1 = "!musichelp";
        String command2 = "!mhelp";

        if (event.getMessage().getContentRaw().equalsIgnoreCase(command1) ||
                event.getMessage().getContentRaw().equalsIgnoreCase(command2) &&
                        !event.getAuthor().isBot()){
            if (!Utils.hasMusicBotPermission(event.getMember())) return;

            String newLine = "\n";
            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(Color.GREEN);
            embed.setTitle("Music Bot Help List");
            embed.appendDescription("__Current Supported Commands:__\n");
            embed.appendDescription("**!musichelp/mhelp** - view this help list"+newLine);
            embed.appendDescription("**!queue** - view the current queue"+newLine);
            embed.appendDescription("**!queueadd** - add a song to the queue (you can add entire YT playlists)"+newLine);
            embed.appendDescription("**!np/nowplaying** - view the song that's currently playing"+newLine);
            embed.appendDescription("**!setannouncechannel** - sets the channel where song will be announced"+newLine);
            embed.appendDescription("**!pause** - pause current song"+newLine);
            embed.appendDescription("**!resume** - resume a paused song"+newLine);
            embed.appendDescription("**!loop** - toggle loop mode on and off"+newLine);
            embed.appendDescription("**!volume** - view current volume"+newLine);
            embed.appendDescription("**!volume [value]** - set the music volume"+newLine);
            embed.appendDescription("**!forward [value][s/m]** - go forward in the song"+newLine);
            embed.appendDescription("**!rewind [value][s/m]** - rewind in the song"+newLine);
            embed.appendDescription("**!play [link]** - abort what's currently playing and play what you want"+newLine);
            embed.appendDescription("**!skip** - skips current song and moves on to next one if there's more songs in the queue"+newLine);
            embed.appendDescription("**!stop** - stop all music and make the bot leave"+newLine);
            embed.appendDescription("**!start** - start the system and make the bot join your current voice channel"+newLine);
            embed.appendDescription("\n");
            embed.appendDescription("__Other Settings:__\n");

            for (Role role : event.getGuild().getRoles()){
                if ((role.getName().contains("Music") || role.getName().contains("music"))
                && (role.getName().contains("access") || role.getName().contains("Access"))) {
                    embed.appendDescription("**Bot Access Role:** <@&" + role.getId() + ">");
                    break;
                }
            }

            event.getChannel().sendMessage(embed.build()).queue();
        }
    }

}
