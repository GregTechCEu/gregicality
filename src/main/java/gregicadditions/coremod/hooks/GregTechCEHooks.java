package gregicadditions.coremod.hooks;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.vec.Matrix4;
import gregicadditions.covers.CoverDigitalInterface;
import gregtech.api.capability.impl.EnergyContainerBatteryBuffer;
import gregtech.api.capability.impl.EnergyContainerHandler;
import gregtech.api.cover.CoverBehavior;
import gregtech.api.metatileentity.IFastRenderMetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public class GregTechCEHooks {

    //origin: gregtech/api/metatileentity/MetaTileEntityHolder/shouldCoverRenderPass
    public static boolean shouldCoverRenderPass(MetaTileEntityHolder holder, int pass) {
        MetaTileEntity metaTE = holder.getMetaTileEntity();
        if (metaTE == null) return false;
        for (EnumFacing side: EnumFacing.VALUES){
            CoverBehavior cover = metaTE.getCoverAtSide(side);
            if (cover instanceof IFastRenderMetaTileEntity && ((IFastRenderMetaTileEntity) cover).shouldRenderInPass(pass)) {
                return true;
            }
        }
        return false;
    }

    //origin: gregtech/api/metatileentity/MetaTileEntityHolder/renderTileEntityFast
    public static void renderTileEntityFast(TileEntity te, double x, double y, double z, float partialTicks, BufferBuilder buffer) {
        if(te instanceof MetaTileEntityHolder){
            MetaTileEntity metaTE = ((MetaTileEntityHolder) te).getMetaTileEntity();
            if (metaTE != null){
                CCRenderState renderState = CCRenderState.instance();
                renderState.reset();
                renderState.bind(buffer);
                Matrix4 translation = (new Matrix4()).translate(x, y, z);
                for (EnumFacing side: EnumFacing.VALUES){
                    CoverBehavior cover = metaTE.getCoverAtSide(side);
                    if (cover instanceof IFastRenderMetaTileEntity) {
                        renderState.setBrightness(te.getWorld(), te.getPos().offset(side));
                        ((IFastRenderMetaTileEntity)cover).renderMetaTileEntityFast(renderState, translation, partialTicks);
                    }
                }
            }
        }
    }

    private static void updateCoverDigitalInterface(MetaTileEntity metaTileEntity, long energyAdded) {
        if (metaTileEntity != null ) {
            for (EnumFacing side : EnumFacing.VALUES) {
                CoverBehavior cover = metaTileEntity.getCoverAtSide(side);
                if (cover instanceof CoverDigitalInterface) {
                    ((CoverDigitalInterface) cover).setEnergyChanged(energyAdded);
                }
            }
        }
    }

    //origin: gregtech.api.capability.impl.EnergyContainerHandler.setEnergyStored()
    public static void setEnergyStored(EnergyContainerHandler energyContainerHandler, long energyStored) {
        if (energyStored != 0) {
            updateCoverDigitalInterface(energyContainerHandler.getMetaTileEntity(), energyStored - energyContainerHandler.getEnergyStored());
        }
        energyContainerHandler.setEnergyStored(energyStored);
    }

    //origin: gregtech/api/capability/impl/EnergyContainerBatteryBuffer.changeEnergy() (though gtce not gonna use it. might be called, so hacking it too)
    public static void changeEnergy(EnergyContainerBatteryBuffer batteryBuffer, long energyAdded) {
        if (energyAdded != 0) {
            updateCoverDigitalInterface(batteryBuffer.getMetaTileEntity(), energyAdded);
        }
    }

    //origin: gregtech/api/capability/impl/EnergyContainerBatteryBuffer.acceptEnergyFromNetwork()
    public static void acceptEnergyFromNetwork(EnergyContainerBatteryBuffer batteryBuffer, long V, long amperageUsed) {
        if (amperageUsed != 0) {
            updateCoverDigitalInterface(batteryBuffer.getMetaTileEntity(), V * amperageUsed);
        }
    }

    //origin: gregtech/api/capability/impl/EnergyContainerBatteryBuffer.update()
    public static void update(EnergyContainerBatteryBuffer batteryBuffer, long V, long amperageUsed) {
        if (amperageUsed != 0) {
            updateCoverDigitalInterface(batteryBuffer.getMetaTileEntity(), -V * amperageUsed);
        }
    }
}
