package com.finderfeed.quick_draw.renderables;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.MatrixUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.StainedGlassPaneBlock;
import org.joml.Matrix4f;

import static net.minecraft.client.renderer.entity.ItemRenderer.*;
import static net.minecraft.client.renderer.entity.ItemRenderer.getFoilBuffer;

public class RenderHelper {

    public static void renderScaledGuiItemCentered(GuiGraphics graphics, ItemStack stack, float x, float y, float scale, double zOffset){
        BakedModel bakedmodel = Minecraft.getInstance().getItemRenderer().getModel(stack,Minecraft.getInstance().level, Minecraft.getInstance().player, 0);
        PoseStack matrices = graphics.pose();

        matrices.pushPose();
        matrices.translate(x ,y , zOffset);
        matrices.mulPose((new Matrix4f()).scaling(1.0F, -1.0F, 1.0F));
        matrices.scale(16.0F*scale, 16.0F*scale, 16.0F*scale);
        boolean flag = !bakedmodel.usesBlockLight();
        if (flag) {
            Lighting.setupForFlatItems();
        }

        renderItemStack(stack, ItemDisplayContext.GUI, false, matrices, graphics.bufferSource(), 15728880, OverlayTexture.NO_OVERLAY, bakedmodel);


        graphics.flush();
        if (flag) {
            Lighting.setupFor3DItems();
        }
        matrices.popPose();

        matrices.pushPose();
        matrices.translate(x ,y ,zOffset);
        matrices.scale(scale, scale, scale);
        graphics.renderItemDecorations(Minecraft.getInstance().font,stack,-8,-8);
        matrices.popPose();
    }

    public static void renderItemStack(ItemStack stack, ItemDisplayContext ctx, boolean idk, PoseStack matrices, MultiBufferSource src, int x, int y, BakedModel mdl) {
        if (!stack.isEmpty()) {
            matrices.pushPose();
            boolean flag = ctx == ItemDisplayContext.GUI || ctx == ItemDisplayContext.GROUND || ctx == ItemDisplayContext.FIXED;
            if (flag) {
                if (stack.is(Items.TRIDENT)) {
                    mdl = Minecraft.getInstance().getItemRenderer().getItemModelShaper().getModelManager().getModel(ModelResourceLocation.vanilla("trident", "inventory"));
                } else if (stack.is(Items.SPYGLASS)) {
                    mdl = Minecraft.getInstance().getItemRenderer().getItemModelShaper().getModelManager().getModel(ModelResourceLocation.vanilla("spyglass", "inventory"));
                }
            }

            mdl = net.neoforged.neoforge.client.ClientHooks.handleCameraTransforms(matrices, mdl, ctx, idk);
            matrices.translate(-0.5F, -0.5F, -0.5F);
            if (!mdl.isCustomRenderer() && (!stack.is(Items.TRIDENT) || flag)) {
                boolean flag1;
                if (ctx != ItemDisplayContext.GUI && !ctx.firstPerson() && stack.getItem() instanceof BlockItem) {
                    Block block = ((BlockItem)stack.getItem()).getBlock();
                    flag1 = !(block instanceof HalfTransparentBlock) && !(block instanceof StainedGlassPaneBlock);
                } else {
                    flag1 = true;
                }
                for (var model : mdl.getRenderPasses(stack, flag1)) {
                    for (var rendertype : model.getRenderTypes(stack, flag1)) {
                        VertexConsumer vertexconsumer;
                        if (( stack.is(ItemTags.COMPASSES) || stack.is(Items.CLOCK)) && stack.hasFoil()) {
                            matrices.pushPose();
                            PoseStack.Pose posestack$pose = matrices.last();
                            if (ctx == ItemDisplayContext.GUI) {
                                MatrixUtil.mulComponentWise(posestack$pose.pose(), 0.5F);
                            } else if (ctx.firstPerson()) {
                                MatrixUtil.mulComponentWise(posestack$pose.pose(), 0.75F);
                            }

                            if (flag1) {
                                vertexconsumer = getCompassFoilBuffer(src, rendertype, posestack$pose);
                            } else {
                                vertexconsumer = getCompassFoilBuffer(src, rendertype, posestack$pose);
                            }

                            matrices.popPose();
                        } else if (flag1) {
                            vertexconsumer = getFoilBufferDirect(src, rendertype, true, stack.hasFoil());
                        } else {
                            vertexconsumer = getFoilBuffer(src, rendertype, true, stack.hasFoil());
                        }

                        Minecraft.getInstance().getItemRenderer().renderModelLists(model, stack, x, y, matrices, vertexconsumer);
                    }
                }
            } else {
                net.neoforged.neoforge.client.extensions.common.IClientItemExtensions.of(stack).getCustomRenderer().renderByItem(stack, ctx, matrices, src, x, y);
            }

            matrices.popPose();
        }
    }

}
