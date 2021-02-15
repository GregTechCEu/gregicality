package gregicadditions.recipes.chain;

import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import gregtech.api.unification.OreDictUnifier;

import static gregicadditions.GAMaterials.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;

public class BariumChain {
    public static void init() {
        CHEMICAL_RECIPES.recipeBuilder().duration(100).EUt(480)
                .input(dust, Barium)
                .fluidInputs(HydrochloricAcid.getFluid(2000))
                .outputs(BariumChloride.getItemStack())
                .fluidOutputs(Hydrogen.getFluid(2000))
                .buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(50).EUt(480)
                .inputs(BariumChloride.getItemStack())
                .fluidInputs(SulfuricAcid.getFluid(1000))
                .outputs(OreDictUnifier.get(dust, Barite))
                .fluidOutputs(HydrochloricAcid.getFluid(2000))
                .buildAndRegister();
        BLAST_RECIPES.recipeBuilder().duration(480).EUt(500).blastFurnaceTemp(1200)
                .input(dust, Barite)
                .input(dust, Carbon, 2)
                .outputs(BariumSulfide.getItemStack())
                .fluidOutputs(CarbonDioxide.getFluid(2000))
                .buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(400).EUt(500)
                .inputs(BariumSulfide.getItemStack())
                .fluidInputs(Water.getFluid(1000))
                .fluidInputs(CarbonDioxide.getFluid(1000))
                .outputs(BariumCarbonate.getItemStack())
                .fluidOutputs(HydrogenSulfide.getFluid(1000))
                .buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder().duration(100).EUt(250)
                .inputs(BariumCarbonate.getItemStack())
                .outputs(BariumOxide.getItemStack())
                .notConsumable(new IntCircuitIngredient(0))
                .fluidOutputs(CarbonDioxide.getFluid(1000))
                .buildAndRegister();
        BLAST_RECIPES.recipeBuilder().duration(240).EUt(500).blastFurnaceTemp(700)
                .inputs(BariumOxide.getItemStack(2))
                .input(dustSmall, Aluminium, 8)
                .outputs(OreDictUnifier.get(ingot, Barium))
                .outputs(BariumAluminate.getItemStack(2))
                .buildAndRegister();
        CENTRIFUGE_RECIPES.recipeBuilder().duration(300).EUt(500)
                .inputs(BariumAluminate.getItemStack(2))
                .outputs(BariumOxide.getItemStack())
                .outputs(Alumina.getItemStack(2))
                .buildAndRegister();
    }

}
