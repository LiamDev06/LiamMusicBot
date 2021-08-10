package me.liamhbest.musicbot.utility;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.internal.audio.ConnectionStage;

public class Utils {

    private static String channelID;

    public static boolean botInVoiceChannel(GuildMessageReceivedEvent event){
        VoiceChannel connectedChannel = event.getGuild().getSelfMember().getVoiceState().getChannel();
        if (connectedChannel == null){
            return false;
        } else {
            return true;
        }
    }

    private static boolean hasRole(Member member, String roleName) {
        for (int i=0; i<member.getRoles().size(); i++){
            if (roleName.equals(member.getRoles().get(i).getName())){
                return true;
            }
        }
        return false;
    }

    public static boolean hasMusicBotPermission(Member member){
        return hasRole(member, "Music Bot Access")
                || member.getId().equalsIgnoreCase("392381841639997451")
                || hasRole(member, "Metadata 12");
    }


}














