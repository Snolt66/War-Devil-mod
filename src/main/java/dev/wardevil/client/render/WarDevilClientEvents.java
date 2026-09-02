package dev.wardevil.client.render;

import dev.wardevil.WarDevil;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.world.entity.player.PlayerModelType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RenderPlayerEvent;
import net.neoforged.neoforge.client.renderstate.RegisterRenderStateModifiersEvent;

@EventBusSubscriber(modid = WarDevil.MOD_ID, value = Dist.CLIENT)
public final class WarDevilClientEvents {
    private WarDevilClientEvents() {}

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(WarDevilRenderIds.WAR_FORM_LAYER, WarDevilModelGeometry::createBodyLayer);
    }

    @SubscribeEvent
    public static void addPlayerLayers(EntityRenderersEvent.AddLayers event) {
        for (PlayerModelType skin : event.getSkins()) {
            AvatarRenderer<AbstractClientPlayer> renderer = event.getPlayerRenderer(skin);
            if (renderer != null) renderer.addLayer(new WarDevilPlayerLayer(renderer, event.getEntityModels()));
        }
    }

    @SubscribeEvent
    public static void registerRenderStateModifiers(RegisterRenderStateModifiersEvent event) {
        WarDevilRenderStateHooks.register(event);
    }

    @SubscribeEvent
    public static void renderPlayerPre(RenderPlayerEvent.Pre<?> event) { WarDevilVanillaVisibility.onPre(event); }

    @SubscribeEvent
    public static void renderPlayerPost(RenderPlayerEvent.Post<?> event) { WarDevilVanillaVisibility.onPost(event); }
}
