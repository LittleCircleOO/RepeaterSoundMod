package heyblack.repeatersound;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import heyblack.repeatersound.config.ConfigManager;
import heyblack.repeatersound.config.ConfigOption;
import heyblack.repeatersound.util.InteractionMode;
import heyblack.repeatersound.util.Commands;
import heyblack.repeatersound.util.ServerCloseCallback;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.sounds.SoundEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Version Specific
// Fabric-Command-Api
//? if <=1.18.2 {
/*import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import static net.fabricmc.fabric.api.client.command.v1.ClientCommandManager.literal;
*///?} elif >=26.1 {
/*import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommands.literal;
*///?} else {
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;
//?}
// Registry
//? if <=1.19.2 {
/*import net.minecraft.core.Registry;
*///?} else {
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
//?}
// Identifier
//? if >=1.21.11 {
/*import net.minecraft.resources.Identifier;
*///?} else {
import net.minecraft.resources.ResourceLocation;
//?}

public class RepeaterSound implements ClientModInitializer
{
    public static final String MOD_ID = "repeatersound";
    public static final String MOD_VERSION = FabricLoader.getInstance().getModContainer(MOD_ID).orElseThrow(RuntimeException::new).getMetadata().getVersion().getFriendlyString();

    public static final SoundEvent BLOCK_REPEATER_CLICK = register("repeater_click");
    public static final SoundEvent BLOCK_REDSTONE_WIRE_CLICK = register("redstone_wire_click");
    public static final SoundEvent BLOCK_DAYLIGHT_DETECTOR_CLICK = register("daylight_detector_click");
    public static final SoundEvent CLICK_ALARM = register("click_alarm");

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void onInitializeClient()
    {
        ConfigManager cfg = ConfigManager.getInstance();

        // Fabric-Api Specific
        //? if <=1.18.2 {
        /*ClientCommandManager.DISPATCHER.register(
        *///?} else {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, environment) -> dispatcher.register(
        //?}
                literal("repeatersound")
                        .then(literal("setBasePitch")
                                .then(Commands.argument(ConfigOption.BASE_PITCH.id, FloatArgumentType.floatArg())
                                        .executes(ctx -> cfg.setConfigCommand(
                                                ConfigOption.BASE_PITCH.id,
                                                String.valueOf(FloatArgumentType.getFloat(ctx, ConfigOption.BASE_PITCH.id)),
                                                ctx.getSource().getPlayer()
                                        ))))

                        .then(literal("useRandomPitch")
                                .then(Commands.argument(ConfigOption.USE_RANDOM.id, BoolArgumentType.bool())
                                        .executes(ctx -> cfg.setConfigCommand(
                                                ConfigOption.USE_RANDOM.id,
                                                String.valueOf(BoolArgumentType.getBool(ctx, ConfigOption.USE_RANDOM.id)),
                                                ctx.getSource().getPlayer()
                                        ))))

                        .then(literal("setVolume")
                                .then(Commands.argument(ConfigOption.VOLUME.id, FloatArgumentType.floatArg())
                                        .executes(ctx -> cfg.setConfigCommand(
                                                ConfigOption.VOLUME.id,
                                                String.valueOf(FloatArgumentType.getFloat(ctx, ConfigOption.VOLUME.id)),
                                                ctx.getSource().getPlayer()
                                        ))))

                        .then(literal("interactionMode")
                                .then(Commands.argument(ConfigOption.INTERACTION_MODE.id, StringArgumentType.string())
                                        .suggests(
                                                (ctx, builder) ->
                                                {
                                                    for (InteractionMode type : InteractionMode.values())
                                                    {
                                                        builder.suggest(type.id);
                                                    }

                                                    return builder.buildFuture();
                                                }
                                        )
                                        .executes(ctx -> cfg.setConfigCommand(
                                                ConfigOption.INTERACTION_MODE.id,
                                                StringArgumentType.getString(ctx, ConfigOption.INTERACTION_MODE.id),
                                                ctx.getSource().getPlayer()
                                        ))))

                        .then(literal("alarmMessage")
                                .then(Commands.argument(ConfigOption.ALARM_MESSAGE.id, StringArgumentType.string())
                                        .executes(ctx -> cfg.setConfigCommand(
                                                ConfigOption.ALARM_MESSAGE.id,
                                                String.valueOf(StringArgumentType.getString(ctx, ConfigOption.ALARM_MESSAGE.id)),
                                                ctx.getSource().getPlayer()
                                        ))))

                        .then(literal("disabledMessage")
                                .then(Commands.argument(ConfigOption.DISABLED_MESSAGE.id, StringArgumentType.string())
                                        .executes(ctx -> cfg.setConfigCommand(
                                                ConfigOption.DISABLED_MESSAGE.id,
                                                String.valueOf(StringArgumentType.getString(ctx, ConfigOption.DISABLED_MESSAGE.id)),
                                                ctx.getSource().getPlayer()
                                        ))))

        //? if <=1.18.2 {
        /*);
        *///?} else {
        ));
        //?}

        ServerCloseCallback.EVENT.register(cfg);
    }

    private static SoundEvent register(String id)
    {
        // Version Specific
        // Identifier
        //? if >=1.21.11 {
        /*Identifier identifier = Identifier.fromNamespaceAndPath(MOD_ID, id);
        *///?} elif >=1.21 {
        ResourceLocation identifier = ResourceLocation.fromNamespaceAndPath(MOD_ID, id);
        //?} else {
        /*ResourceLocation identifier = new ResourceLocation(MOD_ID, id);
        *///?}
        // Registry, SoundEvent factory
        //? if <=1.19.2 {
        /*return Registry.register(Registry.SOUND_EVENT, identifier, new SoundEvent(identifier));
        *///?} else {
        return Registry.register(BuiltInRegistries.SOUND_EVENT, identifier, SoundEvent.createVariableRangeEvent(identifier));
        //?}
    }

    public static void info(String s) {
        LOGGER.info("[RepeaterSound] " + s);
    }

    public static void warn(String s) {
        LOGGER.warn("[RepeaterSound] " + s);
    }

    public static void error(String s) {
        LOGGER.error("[RepeaterSound] " + s);
    }
}
