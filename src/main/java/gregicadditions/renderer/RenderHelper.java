package gregicadditions.renderer;

import codechicken.lib.texture.TextureUtils;
import codechicken.lib.vec.Matrix4;
import gregtech.api.gui.resources.TextureArea;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
import java.util.List;

@SideOnly(Side.CLIENT)
public class RenderHelper {

    @SideOnly(Side.CLIENT)
    public static void renderFluidOverLay(float x, float y, float width, float height, float z, FluidStack fluidStack, float alpha) {
        if (fluidStack != null) {
            int color = fluidStack.getFluid().getColor(fluidStack);
            float r = (color >> 16 & 255) / 255.0f;
            float g = (color >> 8 & 255) / 255.0f;
            float b = (color & 255) / 255.0f;
            TextureAtlasSprite sprite = TextureUtils.getTexture(fluidStack.getFluid().getStill(fluidStack));
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            GlStateManager.disableAlpha();
            //GlStateManager.disableLighting();
            Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            Tessellator tess = Tessellator.getInstance();
            BufferBuilder buf = tess.getBuffer();

            buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
            double uMin = sprite.getInterpolatedU(16D - width * 16D), uMax = sprite.getInterpolatedU(width * 16D);
            double vMin = sprite.getMinV(), vMax = sprite.getInterpolatedV(height * 16D);
            buf.pos(x, y, z).tex(uMin, vMin).color(r, g, b, alpha).endVertex();
            buf.pos(x, y + height, z).tex(uMin, vMax).color(r, g, b, alpha).endVertex();
            buf.pos(x + width, y + height, z).tex(uMax, vMax).color(r, g, b, alpha).endVertex();
            buf.pos(x + width, y, z).tex(uMax, vMin).color(r, g, b, alpha).endVertex();
            tess.draw();

            //GlStateManager.enableLighting();
            GlStateManager.enableAlpha();
            GlStateManager.disableBlend();
            GlStateManager.color(1F, 1F, 1F, 1F);
        }
    }

    @SideOnly(Side.CLIENT)
    public static void renderRect(float x, float y, float width, float height, float z, int color) {
        renderGradientRect(x, y, width, height, z, color, color, false);
    }

    @SideOnly(Side.CLIENT)
    public static void renderGradientRect(float x, float y, float width, float height, float z, int startColor, int endColor, boolean horizontal) {
        float startAlpha = (float) (startColor >> 24 & 255) / 255.0F;
        float startRed = (float) (startColor >> 16 & 255) / 255.0F;
        float startGreen = (float) (startColor >> 8 & 255) / 255.0F;
        float startBlue = (float) (startColor & 255) / 255.0F;
        float endAlpha = (float) (endColor >> 24 & 255) / 255.0F;
        float endRed = (float) (endColor >> 16 & 255) / 255.0F;
        float endGreen = (float) (endColor >> 8 & 255) / 255.0F;
        float endBlue = (float) (endColor & 255) / 255.0F;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        if (horizontal) {
            buffer.pos(x + width, y, z).color(endRed, endGreen, endBlue, endAlpha).endVertex();
            buffer.pos(x, y, z).color(startRed, startGreen, startBlue, startAlpha).endVertex();
            buffer.pos(x, y + height, z).color(startRed, startGreen, startBlue, startAlpha).endVertex();
            buffer.pos(x + width, y + height, z).color(endRed, endGreen, endBlue, endAlpha).endVertex();
            tessellator.draw();
        } else {
            buffer.pos(x + width, y, z).color(startRed, startGreen, startBlue, startAlpha).endVertex();
            buffer.pos(x, y, z).color(startRed, startGreen, startBlue, startAlpha).endVertex();
            buffer.pos(x, y + height, z).color(endRed, endGreen, endBlue, endAlpha).endVertex();
            buffer.pos(x + width, y + height, z).color(endRed, endGreen, endBlue, endAlpha).endVertex();
            tessellator.draw();
        }
        GlStateManager.shadeModel(GL11.GL_FLAT);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    @SideOnly(Side.CLIENT)
    public static void renderTextureArea(TextureArea textureArea, float x, float y, float width, float height, float z) {
        double imageU = textureArea.offsetX;
        double imageV = textureArea.offsetY;
        double imageWidth = textureArea.imageWidth;
        double imageHeight = textureArea.imageHeight;
        Minecraft.getMinecraft().renderEngine.bindTexture(textureArea.imageLocation);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(x, y + height, z).tex(imageU, imageV + imageHeight).endVertex();
        bufferbuilder.pos(x + width, y + height, z).tex(imageU + imageWidth, imageV + imageHeight).endVertex();
        bufferbuilder.pos(x + width, y, z).tex(imageU + imageWidth, imageV).endVertex();
        bufferbuilder.pos(x, y, z).tex(imageU, imageV).endVertex();
        tessellator.draw();
    }

    @SideOnly(Side.CLIENT)
    public static void renderSlot(Slot slot, FontRenderer fr) {
        ItemStack stack = slot.getStack();
        if (!stack.isEmpty() && slot.isEnabled()) {
            net.minecraft.client.renderer.RenderHelper.enableStandardItemLighting();
            GlStateManager.pushMatrix();
            GlStateManager.scale(1, 1, 0);
            GlStateManager.translate(slot.xPos, slot.yPos, 0);
            RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
            renderItem.renderItemAndEffectIntoGUI(stack, 0, 0);
            String text = stack.getCount() > 1? Integer.toString(stack.getCount()) : null;

            if (!stack.isEmpty())
            {
                if (stack.getCount() != 1)
                {
                    String s = text == null ? String.valueOf(stack.getCount()) : text;
                    GlStateManager.disableLighting();
                    GlStateManager.disableBlend();
                    fr.drawStringWithShadow(s, (float)(17 - fr.getStringWidth(s)), (float)9, 16777215);
                    GlStateManager.enableLighting();
                    GlStateManager.enableBlend();
                }

                if (stack.getItem().showDurabilityBar(stack))
                {
                    GlStateManager.disableLighting();
                    GlStateManager.disableTexture2D();
                    GlStateManager.disableAlpha();
                    GlStateManager.disableBlend();
                    Tessellator tessellator = Tessellator.getInstance();
                    BufferBuilder bufferbuilder = tessellator.getBuffer();
                    double health = stack.getItem().getDurabilityForDisplay(stack);
                    int rgbfordisplay = stack.getItem().getRGBDurabilityForDisplay(stack);
                    int i = Math.round(13.0F - (float)health * 13.0F);
                    draw(bufferbuilder, 2, 13, 13, 2, 0, 0, 0, 255);
                    draw(bufferbuilder, 2, 13, i, 1, rgbfordisplay >> 16 & 255, rgbfordisplay >> 8 & 255, rgbfordisplay & 255, 255);
                    GlStateManager.enableBlend();
                    GlStateManager.enableAlpha();
                    GlStateManager.enableTexture2D();
                    GlStateManager.enableLighting();
                }

                EntityPlayerSP entityplayersp = Minecraft.getMinecraft().player;
                float f3 = entityplayersp == null ? 0.0F : entityplayersp.getCooldownTracker().getCooldown(stack.getItem(), Minecraft.getMinecraft().getRenderPartialTicks());

                if (f3 > 0.0F)
                {
                    GlStateManager.disableLighting();
                    GlStateManager.disableTexture2D();
                    Tessellator tessellator = Tessellator.getInstance();
                    BufferBuilder bufferBuilder = tessellator.getBuffer();
                    draw(bufferBuilder, 0, MathHelper.floor(16.0F * (1.0F - f3)), 16, MathHelper.ceil(16.0F * f3), 255, 255, 255, 127);
                    GlStateManager.enableTexture2D();
                    GlStateManager.enableLighting();
                }
            }

            GlStateManager.popMatrix();
            net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
        }
    }

    @SideOnly(Side.CLIENT)
    private static void draw(BufferBuilder renderer, int x, int y, int width, int height, int red, int green, int blue, int alpha)
    {
        renderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        renderer.pos(x, y, 0.0D).color(red, green, blue, alpha).endVertex();
        renderer.pos((x), y + height, 0.0D).color(red, green, blue, alpha).endVertex();
        renderer.pos((x + width), y + height, 0.0D).color(red, green, blue, alpha).endVertex();
        renderer.pos((x + width), y, 0.0D).color(red, green, blue, alpha).endVertex();
        Tessellator.getInstance().draw();
    }

    @SideOnly(Side.CLIENT)
    public static void renderItemOverLay(float x, float y, float z, float scale, ItemStack itemStack) {
        net.minecraft.client.renderer.RenderHelper.enableStandardItemLighting();
        GlStateManager.pushMatrix();
        GlStateManager.scale(scale, scale, 0.0001f);
        GlStateManager.translate(x * 16, y * 16, z * 16);
        RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
        renderItem.renderItemAndEffectIntoGUI(itemStack, 0, 0 );
        GlStateManager.popMatrix();
        net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
    }

    @SideOnly(Side.CLIENT)
    public static void renderText(float x, float y, float z, float scale, int color, final String renderedText, boolean centered) {
        GlStateManager.pushMatrix();
        final FontRenderer fr = Minecraft.getMinecraft().fontRenderer;
        final int width = fr.getStringWidth( renderedText );
        GlStateManager.translate(x, y - scale * 4, z);
        GlStateManager.scale(scale, scale, scale);
        GlStateManager.translate(-0.5f * (centered? 1:0)*width, 0.0f, 0.5f );

        fr.drawString(renderedText, 0, 0, color);
        GlStateManager.popMatrix();
    }

    @SideOnly(Side.CLIENT)
    public static void renderLine(float x1, float y1, float x2, float y2, float lineWidth, int color) {
        float hypo = (float) Math.sqrt((y1 - y2) * (y1 - y2) + (x1 - x2) * (x1 - x2));
        float w = (x2 - x1) / hypo * lineWidth;
        float h = (y1 - y2) / hypo * lineWidth;

        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_DST_ALPHA);
        GlStateManager.color(((color >> 16) & 0xFF) / 255f, ((color >> 8) & 0xFF) / 255f, (color & 0xFF) / 255f, ((color >> 24) & 0xFF) / 255f);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
        if (w * h > 0) {
            bufferbuilder.pos(x1 - w, y1 - h, 0.01D).endVertex();
            bufferbuilder.pos(x1 + w, y1 + h, 0.01D).endVertex();
            bufferbuilder.pos(x2 + w, y2 + h, 0.01D).endVertex();
            bufferbuilder.pos(x2 - w, y2 - h, 0.01D).endVertex();
        } else {
            h = (y2 - y1) / hypo * lineWidth;
            bufferbuilder.pos(x1 + w, y1 - h, 0.01D).endVertex();
            bufferbuilder.pos(x1 - w, y1 + h, 0.01D).endVertex();
            bufferbuilder.pos(x2 - w, y2 + h, 0.01D).endVertex();
            bufferbuilder.pos(x2 + w, y2 - h, 0.01D).endVertex();
        }
        tessellator.draw();
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
        GlStateManager.color(1,1,1,1);
    }

    @SideOnly(Side.CLIENT)
    public static void renderLineChart(List<Long> data, long max, float x, float y, float width, float height, float lineWidth, int color) {
        float durX = data.size() > 1 ? width / (data.size() - 1) : 0;
        float hY = max > 0 ? height / max : 0;

        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_DST_ALPHA);
        GlStateManager.color(((color >> 16) & 0xFF) / 255f, ((color >> 8) & 0xFF) / 255f, (color & 0xFF) / 255f, ((color >> 24) & 0xFF) / 255f);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
        float last_x = x + 0 * durX;
        float last_y = y - data.get(0) * hY;
        for (int i = 0; i < data.size(); i++) {
            float _x = x + i * durX;
            float _y = y - data.get(i) * hY;
            // draw lines
            if (i != 0) {
                bufferbuilder.pos(last_x, last_y - lineWidth, 0.01D).endVertex();
                bufferbuilder.pos(last_x, last_y + lineWidth, 0.01D).endVertex();
                bufferbuilder.pos(_x, _y + lineWidth, 0.01D).endVertex();
                bufferbuilder.pos(_x, _y - lineWidth, 0.01D).endVertex();
                last_x = _x;
                last_y = _y;
            }
            // draw points
            bufferbuilder.pos(_x - 3 * lineWidth, _y, 0.01D).endVertex();
            bufferbuilder.pos(_x, _y + 3 * lineWidth, 0.01D).endVertex();
            bufferbuilder.pos(_x + 3 * lineWidth, _y, 0.01D).endVertex();
            bufferbuilder.pos(_x, _y - 3 * lineWidth, 0.01D).endVertex();
        }
        tessellator.draw();

        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
    }

    /***
     * avoid z-fighting. not familiar with the CCL, its a trick.
     * //TODO could DisableDepthMask in the CCL?
     * @param translation origin
     * @param side facing
     * @param layer level
     * @return adjust
     */
    @SideOnly(Side.CLIENT)
    public static Matrix4 adjustTrans(Matrix4 translation, EnumFacing side, int layer) {
        Matrix4 trans = translation.copy();
        switch (side){
            case DOWN:
                trans.translate(0 , -0.001D * layer,0);
                break;
            case UP:
                trans.translate(0 , 0.001D * layer,0);
                break;
            case NORTH:
                trans.translate(0 , 0,-0.001D * layer);
                break;
            case SOUTH:
                trans.translate(0 , 0,0.001D * layer);
                break;
            case EAST:
                trans.translate(0.001D * layer, 0,0);
                break;
            case WEST:
                trans.translate(-0.001D * layer, 0,0);
                break;
        }
        return trans;
    }

    @SideOnly(Side.CLIENT)
    public static void moveToFace(double x, double y, double z, EnumFacing face) {
        GlStateManager.translate( x + 0.5 + face.getXOffset() * 0.5, y + 0.5 + face.getYOffset() * 0.5, z + 0.5 + face.getZOffset() * 0.5);
    }

    @SideOnly(Side.CLIENT)
    public static void rotateToFace(EnumFacing face, EnumFacing spin) {
        switch( face ) {
            case UP:
                GlStateManager.scale( 1.0f, -1.0f, 1.0f );
                GlStateManager.rotate( 90.0f, 1.0f, 0.0f, 0.0f );
                GlStateManager.rotate(spin == EnumFacing.EAST ? 90 : spin == EnumFacing.SOUTH ? 180 : spin == EnumFacing.WEST ? -90 : 0, 0, 0, 1);
                break;
            case DOWN:
                GlStateManager.scale( 1.0f, -1.0f, 1.0f );
                GlStateManager.rotate( -90.0f, 1.0f, 0.0f, 0.0f );
                GlStateManager.rotate(spin == EnumFacing.EAST ? 90 : spin == EnumFacing.NORTH ? 180 : spin == EnumFacing.WEST ? -90 : 0, 0, 0, 1);
                break;
            case EAST:
                GlStateManager.scale( -1.0f, -1.0f, -1.0f );
                GlStateManager.rotate( -90.0f, 0.0f, 1.0f, 0.0f );
                break;
            case WEST:
                GlStateManager.scale( -1.0f, -1.0f, -1.0f );
                GlStateManager.rotate( 90.0f, 0.0f, 1.0f, 0.0f );
                break;
            case NORTH:
                GlStateManager.scale( -1.0f, -1.0f, -1.0f );
                break;
            case SOUTH:
                GlStateManager.scale( -1.0f, -1.0f, -1.0f );
                GlStateManager.rotate( 180.0f, 0.0f, 1.0f, 0.0f );
                break;
            default:
                break;
        }
    }

    /***
     * used to render pixels in stencil mask. (e.g. Restrict rendering results to be displayed only in Monitor Screens)
     * if you want to do the similar things in Gui(2D) not World(3D), plz consider using the RenderUtil.useScissor from
     * GregTech that you don't need to draw mask to build a rect mask easily.
     * @param mask draw mask
     * @param renderInMask render logic in the mask
     * @param shouldRenderMask should mask be rendered too
     */
    @SideOnly(Side.CLIENT)
    public static void useStencil(@NotNull Runnable mask, @NotNull Runnable renderInMask, boolean shouldRenderMask) {
        GL11.glStencilMask(0xFF);
        GL11.glClearStencil(0);
        GL11.glClear(GL11.GL_STENCIL_BUFFER_BIT) ;
        GL11.glEnable(GL11.GL_STENCIL_TEST);

        GL11.glStencilFunc(GL11.GL_ALWAYS, 1, 0xFF);
        GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_REPLACE);

        if(!shouldRenderMask) {
            GL11.glColorMask(false, false, false, false);
            GL11.glDepthMask(false);
        }

        mask.run();

        if(!shouldRenderMask) {
            GL11.glColorMask(true, true, true, true);
            GL11.glDepthMask(true);
        }

        GL11.glStencilMask(0x00);
        GL11.glStencilFunc(GL11.GL_EQUAL, 1, 0xFF);
        GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_KEEP);

        renderInMask.run();

        GL11.glDisable(GL11.GL_STENCIL_TEST);
    }

}
