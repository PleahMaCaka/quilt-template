package com.pleahmacaka.example_mod.mixin.binding;

import com.pleahmacaka.example_mod.binding.TitleScreenBindingMixinKt;
import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenBindingMixin {
    @Inject(method = "init", at = @At("TAIL"))
    public void exampleMod$onInit(CallbackInfo ci) {
        TitleScreenBindingMixinKt.titleScreenBinding();
    }
}
