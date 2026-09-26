package heyblack.repeatersound.util;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

// Version Specific
//? if <1.19 {
/*import net.minecraft.network.chat.TextComponent;
*///?}

public class Texts
{
    // Version Specific
    //? if >=1.19 {
    public static Component literal(String s)
    {
        return Component.literal(s);
    }
    //?} else {
    /*public static Component literal(String s)
    {
        return new TextComponent(s);
    }
    *///?}

    public static void send(Player player, String message, boolean actionBar)
    {
        // Version Specific
        //? if >=26.1 {
        /*player.sendSystemMessage(literal(message));
        *///?} else {
        player.displayClientMessage(literal(message), actionBar);
        //?}
    }
}
