package gregicadditions.recipes;

import gregicadditions.GAMaterials;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.FluidMaterial;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.ore.OrePrefix;

public class MatterReplication {
    public static void init() {
        //Mass Fab
        GARecipeMaps.MASS_FAB_RECIPES.recipeBuilder().duration((int) (Materials.Hydrogen.getMass() * 500)).EUt(32).fluidInputs(Materials.Hydrogen.getFluid(1000)).fluidOutputs(GAMaterials.PositiveMatter.getFluid(1)).buildAndRegister();
        for (Material m : Material.MATERIAL_REGISTRY) {
            if (m.getProtons() > 0 && m.getNeutrons() > 0 && m.getMass() != 98 && m instanceof FluidMaterial && OreDictUnifier.get(OrePrefix.dust, m).isEmpty()) {
                GARecipeMaps.MASS_FAB_RECIPES.recipeBuilder().duration((int) (m.getAverageMass() * 500)).EUt(32).fluidInputs(((FluidMaterial) m).getFluid(1000)).fluidOutputs(GAMaterials.PositiveMatter.getFluid((int) (m.getAverageProtons())), GAMaterials.NeutralMatter.getFluid((int) (m.getAverageNeutrons()))).buildAndRegister();
            }
        }

        //Replicator
        GARecipeMaps.REPLICATOR_RECIPES.recipeBuilder().duration((int) (Materials.Hydrogen.getMass() * 1000)).EUt(32).inputs(FluidCellIngredient.getIngredient(Materials.Hydrogen, 0)).fluidOutputs(Materials.Hydrogen.getFluid(1000)).fluidInputs(GAMaterials.PositiveMatter.getFluid(1)).buildAndRegister();
        for (Material m : Material.MATERIAL_REGISTRY) {
            if (m.getProtons() > 0 && m.getNeutrons() > 0 && m.getMass() != 98 && m instanceof FluidMaterial && OreDictUnifier.get(OrePrefix.dust, m).isEmpty() && m != Materials.Air && m != Materials.LiquidAir) {
                GARecipeMaps.REPLICATOR_RECIPES.recipeBuilder().duration((int) (m.getAverageMass() * 1000)).EUt(32)
                        .inputs(FluidCellIngredient.getIngredient((FluidMaterial) m, 0))
                        .fluidOutputs(((FluidMaterial) m).getFluid(1000)).fluidInputs(GAMaterials.PositiveMatter.getFluid((int) (m.getAverageProtons())), GAMaterials.NeutralMatter.getFluid((int) (m.getAverageNeutrons())))
                        .buildAndRegister();
            }
        }
    }
}
