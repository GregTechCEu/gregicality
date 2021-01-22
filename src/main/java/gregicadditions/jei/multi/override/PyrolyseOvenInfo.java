package gregicadditions.jei.multi.override;

import gregicadditions.GAValues;
import gregicadditions.item.GAHeatingCoil;
import gregicadditions.item.GAMetaBlocks;
import gregicadditions.machines.GATileEntities;
import gregtech.api.metatileentity.multiblock.MultiblockControllerBase;
import gregtech.common.blocks.BlockMachineCasing;
import gregtech.common.blocks.BlockWireCoil;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.MetaTileEntities;
import gregtech.integration.jei.multiblock.MultiblockInfoPage;
import gregtech.integration.jei.multiblock.MultiblockShapeInfo;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;

import java.util.ArrayList;
import java.util.List;

public class PyrolyseOvenInfo extends MultiblockInfoPage {

    @Override
    public MultiblockControllerBase getController() {
        return GATileEntities.PYROLYSE_OVEN;
    }

    @Override
    public List<MultiblockShapeInfo> getMatchingShapes() {
        ArrayList<MultiblockShapeInfo> shapeInfo = new ArrayList<>();
        for (BlockWireCoil.CoilType coilType : BlockWireCoil.CoilType.values()) {
            if (!coilType.getName().equals("superconductor")) {
                if (!coilType.getName().equals("fusion_coil")) {

                    shapeInfo.add(MultiblockShapeInfo.builder()
                            .aisle("XXX", "ISF", "XXX")
                            .aisle("CCC", "C#C", "CCC")
                            .aisle("CCC", "C#C", "CCC")
                            .aisle("XXX", "BEH", "XXX")
                            .where('S', GATileEntities.PYROLYSE_OVEN, EnumFacing.NORTH)
                            .where('X', MetaBlocks.MACHINE_CASING.getState(BlockMachineCasing.MachineCasingType.ULV))
                            .where('C', MetaBlocks.WIRE_COIL.getState(coilType))
                            .where('#', Blocks.AIR.getDefaultState())
                            .where('I', MetaTileEntities.ITEM_IMPORT_BUS[GAValues.HV], EnumFacing.NORTH)
                            .where('F', MetaTileEntities.FLUID_IMPORT_HATCH[GAValues.HV], EnumFacing.NORTH)
                            .where('E', MetaTileEntities.ENERGY_INPUT_HATCH[GAValues.HV], EnumFacing.SOUTH)
                            .where('B', MetaTileEntities.ITEM_EXPORT_BUS[GAValues.HV], EnumFacing.SOUTH)
                            .where('H', MetaTileEntities.FLUID_EXPORT_HATCH[GAValues.HV], EnumFacing.SOUTH)
                            .build());
                }
            }
        }

        for (GAHeatingCoil.CoilType coilType : GAHeatingCoil.CoilType.values()) {
            shapeInfo.add(MultiblockShapeInfo.builder()
                    .aisle("XXX", "ISF", "XXX")
                    .aisle("CCC", "C#C", "CCC")
                    .aisle("CCC", "C#C", "CCC")
                    .aisle("XXX", "BEH", "XXX")
                    .where('S', GATileEntities.PYROLYSE_OVEN, EnumFacing.NORTH)
                    .where('X', MetaBlocks.MACHINE_CASING.getState(BlockMachineCasing.MachineCasingType.ULV))
                    .where('C', GAMetaBlocks.HEATING_COIL.getState(coilType))
                    .where('#', Blocks.AIR.getDefaultState())
                    .where('I', MetaTileEntities.ITEM_IMPORT_BUS[GAValues.HV], EnumFacing.NORTH)
                    .where('F', MetaTileEntities.FLUID_IMPORT_HATCH[GAValues.HV], EnumFacing.NORTH)
                    .where('E', MetaTileEntities.ENERGY_INPUT_HATCH[GAValues.HV], EnumFacing.SOUTH)
                    .where('B', MetaTileEntities.ITEM_EXPORT_BUS[GAValues.HV], EnumFacing.SOUTH)
                    .where('H', MetaTileEntities.FLUID_EXPORT_HATCH[GAValues.HV], EnumFacing.SOUTH)
                    .build());
        }
        return shapeInfo;
    }

    @Override
    public String[] getDescription() {
        return new String[]{I18n.format("gregtech.multiblock.pyrolyze_oven.description")};
    }

}
