package gregicadditions.machines.multi;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import com.google.common.collect.Lists;
import gregicadditions.GAUtility;
import gregicadditions.GAValues;
import gregicadditions.item.GAMetaBlocks;
import gregicadditions.worldgen.PumpjackHandler;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.impl.EnergyContainerList;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockWithDisplayBase;
import gregtech.api.multiblock.BlockPattern;
import gregtech.api.multiblock.FactoryBlockPattern;
import gregtech.api.multiblock.PatternMatchContext;
import gregtech.api.render.ICubeRenderer;
import gregtech.api.render.Textures;
import net.minecraft.block.state.IBlockState;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

import static gregtech.api.unification.material.Materials.Steel;

public class MetaTileEntityDrillingRig extends MultiblockWithDisplayBase {

    private static final MultiblockAbility<?>[] ALLOWED_ABILITIES = new MultiblockAbility[]{MultiblockAbility.IMPORT_FLUIDS, MultiblockAbility.EXPORT_FLUIDS, MultiblockAbility.INPUT_ENERGY};

    private IEnergyContainer energyContainer;
    private IMultipleTankHandler importFluidHandler;
    protected IMultipleTankHandler exportFluidHandler;

    private boolean isActive = false;
    private boolean done = false;
    private static PumpjackHandler.OilWorldInfo oilWorldInfo;


    public MetaTileEntityDrillingRig(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
        reinitializeStructurePattern();
    }


    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        resetTileAbilities();
        if (isActive)
            setActive(false);
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        initializeAbilities();
    }

    private void initializeAbilities() {
        this.importFluidHandler = new FluidTankList(true, getAbilities(MultiblockAbility.IMPORT_FLUIDS));
        this.exportFluidHandler = new FluidTankList(true, getAbilities(MultiblockAbility.EXPORT_FLUIDS));
        this.energyContainer = new EnergyContainerList(getAbilities(MultiblockAbility.INPUT_ENERGY));
    }

    private void resetTileAbilities() {
        this.importFluidHandler = new FluidTankList(true);
        this.exportFluidHandler = new FluidTankList(true);
        this.energyContainer = new EnergyContainerList(Lists.newArrayList());
    }

    public boolean drainEnergy() {
        if (energyContainer.getEnergyStored() >= getMaxVoltage()) {
            energyContainer.removeEnergy(getMaxVoltage());
            return true;
        }
        return false;
    }

    public long getMaxVoltage() {
        int tier = GAUtility.getTierByVoltage(energyContainer.getInputVoltage());
        return GAValues.V[tier];
    }

    @Override
    protected void updateFormedValid() {


        if (getWorld().isRemote) {
            return;
        }

        if (done || !drainEnergy()) {
            if (isActive)
                setActive(false);
            return;
        }

        if (!isActive)
            setActive(true);

        if (getTimer() % 20 == 0) {
            int residual = getResidualOil();
            if (availableOil() > 0 || residual > 0) {
                int oilAmnt = availableOil() <= 0 ? residual : availableOil();
                FluidStack out = new FluidStack(availableFluid(), (int) Math.min(getMaxVoltage(), oilAmnt));
                int drained = exportFluidHandler.fill(out, true);
                extractOil(drained);
            } else {
                done = true;
            }
        }
    }

    public int availableOil() {
        return PumpjackHandler.getFluidAmount(getWorld(), getWorld().getChunk(getPos()).x, getWorld().getChunk(getPos()).z);
    }

    public Fluid availableFluid() {
        return PumpjackHandler.getFluid(getWorld(), getWorld().getChunk(getPos()).x, getWorld().getChunk(getPos()).z);
    }

    public int getResidualOil() {
        return PumpjackHandler.getResidualFluid(getWorld(), getWorld().getChunk(getPos()).x, getWorld().getChunk(getPos()).z);
    }

    public void extractOil(int amount) {
        PumpjackHandler.depleteFluid(getWorld(), getWorld().getChunk(getPos()).x, getWorld().getChunk(getPos()).z, amount);
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        oilWorldInfo = PumpjackHandler.getOilWorldInfo(getWorld(), getWorld().getChunk(getPos()).x, getWorld().getChunk(getPos()).z);


        textList.add(new TextComponentString(oilWorldInfo.capacity + ""));
        textList.add(new TextComponentString(oilWorldInfo.current + ""));

        textList.add(new TextComponentString(oilWorldInfo.type.name));
        textList.add(new TextComponentString(oilWorldInfo.type.fluid));
        textList.add(new TextComponentString(oilWorldInfo.type.replenishRate + ""));

        if (oilWorldInfo.overrideType != null) {
            textList.add(new TextComponentString(oilWorldInfo.overrideType.name));
            textList.add(new TextComponentString(oilWorldInfo.overrideType.fluid));
        }
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("XXX", "XXX", "XXX")
                .aisle("XXX", "X#X", "XXX")
                .aisle("XXX", "XSX", "XXX")
                .setAmountAtLeast('L', 14)
                .where('S', this.selfPredicate())
                .where('L', statePredicate(this.getCasingState()))
                .where('X', statePredicate(new IBlockState[]{this.getCasingState()})
                        .or(abilityPartPredicate(ALLOWED_ABILITIES)))
                .where('#', isAirPredicate())
                .where('C', statePredicate(this.getCasingState()))
                .build();
    }

    public IBlockState getCasingState() {
        return GAMetaBlocks.getMetalCasingBlockState(Steel);
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return GAMetaBlocks.METAL_CASING.get(Steel);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(MetaTileEntityHolder metaTileEntityHolder) {
        return new MetaTileEntityDrillingRig(metaTileEntityId);
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeBoolean(isActive);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.isActive = buf.readBoolean();
    }

    protected void setActive(boolean active) {
        this.isActive = active;
        markDirty();
        if (!getWorld().isRemote) {
            writeCustomData(1, buf -> buf.writeBoolean(active));
        }
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if (dataId == 1) {
            this.isActive = buf.readBoolean();
            getHolder().scheduleChunkForRenderUpdate();
        }
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        Textures.MULTIBLOCK_WORKABLE_OVERLAY.render(renderState, translation, pipeline, getFrontFacing(), isActive);
    }
}
