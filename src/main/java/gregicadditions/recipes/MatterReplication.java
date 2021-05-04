package gregicadditions.recipes;

import gregicadditions.GAConfig;
import gregtech.api.unification.material.type.FluidMaterial;
import gregtech.api.unification.material.type.IngotMaterial;
import gregtech.api.unification.material.type.Material;
import net.minecraftforge.fluids.FluidStack;

import static gregicadditions.GAMaterials.*;
import static gregtech.api.unification.material.Materials.UUMatter;
import static gregtech.api.unification.ore.OrePrefix.dust;

public class MatterReplication {
    public static void init() {
        GARecipeMaps.LARGE_MIXER_RECIPES.recipeBuilder()
                .fluidInputs(BosonicUUMatter.getFluid(1000))
                .fluidInputs(FermionicUUMatter.getFluid(1000))
                .fluidInputs(FreeElectronGas.getFluid(2000))
                .fluidOutputs(UUMatter.getFluid(1000))
                .EUt(7680)
                .duration(100)
                .buildAndRegister();
        for (Material material : Material.MATERIAL_REGISTRY) {
            if (material.element == null)
                continue;
            int mass = (int) material.getMass();
            FluidStack uuFluid = mass % 2 == 0 ? BosonicUUMatter.getFluid(mass) : FermionicUUMatter.getFluid(mass);
            if (((FluidMaterial) material).getMaterialFluid() != null) {
                int amount = material instanceof IngotMaterial ? 144 : 1000;
                GARecipeMaps.MASS_FAB_RECIPES.recipeBuilder()
                        .fluidInputs(((FluidMaterial) material).getFluid(amount))
                        .fluidOutputs(uuFluid)
                        .fluidOutputs(FreeElectronGas.getFluid(mass))
                        .duration(mass * GAConfig.Misc.replicationTimeFactor)
                        .EUt(32)
                        .buildAndRegister();
                if (material.hasFlag(DISABLE_REPLICATION))
                    continue;
                GARecipeMaps.REPLICATOR_RECIPES.recipeBuilder()
                        .fluidOutputs(((FluidMaterial) material).getFluid(amount))
                        .notConsumable(((FluidMaterial) material).getFluid(amount))
                        .fluidInputs(uuFluid)
                        .fluidInputs(FreeElectronGas.getFluid(mass))
                        .duration(mass * GAConfig.Misc.replicationTimeFactor)
                        .EUt(32)
                        .buildAndRegister();
                GARecipeMaps.REPLICATOR_RECIPES.recipeBuilder()
                        .fluidOutputs(((FluidMaterial) material).getFluid(amount))
                        .notConsumable(((FluidMaterial) material).getFluid(amount))
                        .fluidInputs(UUMatter.getFluid(mass))
                        .duration(mass * GAConfig.Misc.replicationTimeFactor)
                        .EUt(32)
                        .buildAndRegister();
            } else {
                GARecipeMaps.MASS_FAB_RECIPES.recipeBuilder()
                        .input(dust, material)
                        .fluidOutputs(uuFluid)
                        .fluidOutputs(FreeElectronGas.getFluid(mass))
                        .duration(mass * GAConfig.Misc.replicationTimeFactor)
                        .EUt(32)
                        .buildAndRegister();
                if (material.hasFlag(DISABLE_REPLICATION))
                    continue;
                GARecipeMaps.REPLICATOR_RECIPES.recipeBuilder()
                        .output(dust, material)
                        .notConsumable(dust, material)
                        .fluidInputs(uuFluid)
                        .fluidInputs(FreeElectronGas.getFluid(mass))
                        .duration(mass * GAConfig.Misc.replicationTimeFactor)
                        .EUt(32)
                        .buildAndRegister();
                GARecipeMaps.REPLICATOR_RECIPES.recipeBuilder()
                        .output(dust, material)
                        .notConsumable(dust, material)
                        .fluidInputs(UUMatter.getFluid(mass))
                        .duration(mass * GAConfig.Misc.replicationTimeFactor)
                        .EUt(32)
                        .buildAndRegister();
            }
        }
    }
}
