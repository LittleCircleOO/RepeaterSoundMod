package heyblack.repeatersound.util;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;

// Version Specific
//? if <=1.18.2 {
/*import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
*///?} else {
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
//?}

public class Commands
{
    /**
     * Fabric API's argument factories disappeared in 26.x (only ClientCommands.literal remains),
     * and the generic Brigadier factory fails inference inside builder chains,
     * so a typed copy is provided here, effective across all versions.
     */
    public static <T> RequiredArgumentBuilder<FabricClientCommandSource, T> argument(String name, ArgumentType<T> type)
    {
        return RequiredArgumentBuilder.argument(name, type);
    }
}
