package heyblack.repeatersound;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import heyblack.repeatersound.config.ConfigManager;
import heyblack.repeatersound.util.ServerCloseCallback;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class RepeaterSound implements ClientModInitializer
{
    public static final String MOD_ID = "repeatersound";
    public static final String MOD_VERSION = FabricLoader.getInstance().getModContainer(MOD_ID).orElseThrow(RuntimeException::new).getMetadata().getVersion().getFriendlyString();

    public static final SoundEvent BLOCK_REPEATER_CLICK = register("repeater_click");
    public static final SoundEvent BLOCK_REDSTONE_WIRE_CLICK = register("redstone_wire_click");
    public static final SoundEvent BLOCK_DAYLIGHT_DETECTOR_CLICK = register("daylight_detector_click");
    public static final SoundEvent CLICK_ALARM = register("click_alarm");

    @Override
    public void onInitializeClient()
    {
        ConfigManager cfg = ConfigManager.getInstance();

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(
                ClientCommandManager.literal("repeatersound")
                        .then(ClientCommandManager.literal("setBasePitch")
                                .then(ClientCommandManager.argument("basePitch", FloatArgumentType.floatArg())
                                        .executes(ctx -> cfg.setConfig(
                                                "basePitch",
                                                String.valueOf(FloatArgumentType.getFloat(ctx, "basePitch")),
                                                ctx.getSource().getPlayer()
                                        ))))

                        .then(ClientCommandManager.literal("useRandomPitch")
                                .then(ClientCommandManager.argument("useRandom", BoolArgumentType.bool())
                                        .executes(ctx -> cfg.setConfig(
                                                "useRandom",
                                                String.valueOf(BoolArgumentType.getBool(ctx, "useRandom")),
                                                ctx.getSource().getPlayer()
                                        ))))

                        .then(ClientCommandManager.literal("setVolume")
                                .then(ClientCommandManager.argument("volume", FloatArgumentType.floatArg())
                                        .executes(ctx -> cfg.setConfig(
                                                "volume",
                                                String.valueOf(FloatArgumentType.getFloat(ctx, "volume")),
                                                ctx.getSource().getPlayer()
                                        ))))

                        .then(ClientCommandManager.literal("interactionMode")
                                .then(ClientCommandManager.argument("mode", StringArgumentType.string())
                                        .suggests(
                                                (ctx, builder) ->
                                                {
                                                    builder.suggest("NORMAL");
                                                    builder.suggest("ALARM");
                                                    builder.suggest("DISABLED");

                                                    return builder.buildFuture();
                                                }
                                        )
                                        .executes(ctx -> cfg.setConfig(
                                                "interactionMode",
                                                StringArgumentType.getString(ctx, "mode"),
                                                ctx.getSource().getPlayer()
                                        ))))

                        .then(ClientCommandManager.literal("alarmMessage")
                                .then(ClientCommandManager.argument("message", StringArgumentType.string())
                                        .executes(ctx -> cfg.setConfig(
                                                "alarmMessage",
                                                String.valueOf(StringArgumentType.getString(ctx, "message")),
                                                ctx.getSource().getPlayer()
                                        ))))

                        .then(ClientCommandManager.literal("disabledMessage")
                                .then(ClientCommandManager.argument("message", StringArgumentType.string())
                                        .executes(ctx -> cfg.setConfig(
                                                "disabledMessage",
                                                String.valueOf(StringArgumentType.getString(ctx, "message")),
                                                ctx.getSource().getPlayer()
                                        )))))
        );

        ServerCloseCallback.EVENT.register(cfg);
    }

    private static SoundEvent register(String id) {
        Identifier identifier = Identifier.of("repeatersound", id);
        return Registry.register(Registries.SOUND_EVENT, identifier, SoundEvent.of(identifier));
    }
}