package heyblack.repeatersound.mixin;

import heyblack.repeatersound.config.ConfigManager;
import heyblack.repeatersound.config.ConfigOption;
import heyblack.repeatersound.util.AffectedBlocks;
import heyblack.repeatersound.util.InteractionMode;
import heyblack.repeatersound.util.Texts;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(value = EnvType.CLIENT)
@Mixin(MultiPlayerGameMode.class)
public class MultiPlayerGameModeMixin
{
    @Inject(method = "useItemOn", at = @At(value = "HEAD"), cancellable = true)
    // Version Specific
    //? if <=1.18.2 {
    /*public void disableInteraction(LocalPlayer player, ClientLevel level, InteractionHand hand, BlockHitResult hitResult, CallbackInfoReturnable<InteractionResult> cir)
    *///?} else {
    public void disableInteraction(LocalPlayer player, InteractionHand hand, BlockHitResult hitResult, CallbackInfoReturnable<InteractionResult> cir)
    //?}
    {
        ConfigManager cfg = ConfigManager.getInstance();
        InteractionMode mode = InteractionMode.valueOf(cfg.getConfig(ConfigOption.INTERACTION_MODE.id));
        if (mode == InteractionMode.DISABLED)
        {
            // Version Specific
            // Minecraft.level is used instead of Entity.level():
            // the latter's intermediary id drifts between adjacent versions
            //? if >1.18.2 {
            ClientLevel level = Minecraft.getInstance().level;
            //?}
            Block block = level.getBlockState(hitResult.getBlockPos()).getBlock();
            if (AffectedBlocks.get().contains(block))
            {
                Texts.send(player, cfg.getConfig(ConfigOption.DISABLED_MESSAGE.id), true);
                cir.setReturnValue(InteractionResult.FAIL);
            }
        }
    }
}
