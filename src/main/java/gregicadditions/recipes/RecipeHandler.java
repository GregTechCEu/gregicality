package gregicadditions.recipes;

import gregicadditions.GAConfig;
import gregicadditions.GAEnums;
import gregicadditions.GAUtility;
import gregicadditions.item.GAMetaBlocks;
import gregicadditions.item.GAMetaItems;
import gregicadditions.item.GAExplosive;
import gregicadditions.materials.SimpleDustMaterialStack;
import gregicadditions.recipes.chain.*;
import gregicadditions.recipes.chain.optical.Lasers;
import gregicadditions.recipes.chain.optical.OpticalCircuits;
import gregicadditions.recipes.chain.optical.OpticalComponents;
import gregicadditions.recipes.chain.optical.OpticalFiber;
import gregicadditions.recipes.chain.wetware.*;
import gregicadditions.recipes.impl.LargeRecipeBuilder;
import gregicadditions.utils.GALog;
import gregtech.api.GTValues;
import gregtech.api.recipes.*;
import gregtech.api.recipes.builders.SimpleRecipeBuilder;
import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.*;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import gregtech.api.util.ValidationResult;
import gregtech.common.items.MetaItems;
import gregtech.common.items.behaviors.TurbineRotorBehavior;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.ArrayUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static gregicadditions.GAMaterials.*;
import static gregicadditions.item.GAMetaItems.*;
import static gregicadditions.recipes.GARecipeMaps.*;
import static gregicadditions.recipes.helper.AdditionMethods.removeRecipesByInputs;
import static gregtech.api.GTValues.M;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.type.DustMaterial.MatFlags.NO_SMASHING;
import static gregtech.api.unification.material.type.Material.MatFlags.DECOMPOSITION_REQUIRES_HYDROGEN;
import static gregtech.api.unification.material.type.Material.MatFlags.DISABLE_DECOMPOSITION;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.items.MetaItems.WOODEN_FORM_BRICK;

public class RecipeHandler {

    private static final List<FluidMaterial> OLD_INSULATION_MATERIAL = Arrays.asList(Rubber, StyreneButadieneRubber, SiliconeRubber);


    private static final OrePrefix[] WIRE_DOUBLING_ORDER = new OrePrefix[]{
            wireGtSingle, wireGtDouble, wireGtQuadruple, wireGtOctal, wireGtHex
    };

    public static void register() {

        GAEnums.GAOrePrefix.gtMetalCasing.addProcessingHandler(IngotMaterial.class, RecipeHandler::processMetalCasing);
        turbineBlade.addProcessingHandler(IngotMaterial.class, RecipeHandler::processTurbine);
        ingot.addProcessingHandler(IngotMaterial.class, RecipeHandler::processIngotComposition);

        if (GAConfig.GT6.BendingCurvedPlates && GAConfig.GT6.BendingCylinders)
            GAEnums.GAOrePrefix.plateCurved.addProcessingHandler(IngotMaterial.class, RecipeHandler::processPlateCurved);
        if (GAConfig.GT6.PlateDoubleIngot && GAConfig.GT6.addDoubleIngots) {
            plate.addProcessingHandler(IngotMaterial.class, RecipeHandler::processDoubleIngot);
        }
        if (GAConfig.GT6.addRounds)
            GAEnums.GAOrePrefix.round.addProcessingHandler(IngotMaterial.class, RecipeHandler::processRound);
        if (GAConfig.GT6.BendingRings && GAConfig.GT6.BendingCylinders) {
            ring.addProcessingHandler(IngotMaterial.class, RecipeHandler::processRing);
        }
        if (GAConfig.GT5U.CablesGT5U) {
            for (OrePrefix wirePrefix : WIRE_DOUBLING_ORDER) {
                wirePrefix.addProcessingHandler(IngotMaterial.class, RecipeHandler::processWireGt);
            }
        }

        if (GAConfig.Misc.PackagerDustRecipes) {
            dustTiny.addProcessingHandler(DustMaterial.class, RecipeHandler::processTinyDust);
            dustSmall.addProcessingHandler(DustMaterial.class, RecipeHandler::processSmallDust);
            nugget.addProcessingHandler(IngotMaterial.class, RecipeHandler::processNugget);
        }

        if (GAConfig.GT5U.stickGT5U) {
            stick.addProcessingHandler(DustMaterial.class, RecipeHandler::processStick);
        }
        dust.addProcessingHandler(GemMaterial.class, RecipeHandler::processGem);
        foil.addProcessingHandler(IngotMaterial.class, RecipeHandler::processFoil);
    }

    public static void registerOreDict() {
        OreDictUnifier.registerOre(new ItemStack(Items.SNOWBALL), dust, Snow);
        OreDictUnifier.registerOre(new ItemStack(Blocks.SNOW), block, Snow);
        OreDictionary.registerOre("formWood", WOODEN_FORM_BRICK.getStackForm());
    }

    public static void initChains() {
        GoldChain.init();
        NaquadahChain.init();
        OpticalFiber.init();
        NuclearChain.init();
        PlasticChain.init();
        PlatinumSludgeGroupChain.init();
        TungstenChain.init();
        REEChain.init();
        BacteriaCultures.init();
        GrowthMedium.init();
        StemCells.init();
        SterilizedGrowthMedium.init();
        ProcessingUnits.init();
        Circuits.init();
        Batteries.init();
        RheniumChain.init();
        UHVMaterials.init();
        PEEKChain.init();
        ZylonChain.init();
        FullereneChain.init();
        BariumChain.init();
        UraniumChain.init();
        VanadiumChain.init();
        IodineChain.init();
        ZirconChain.init();
        ZincChain.init();
        AluminiumChain.init();
        AmmoniaChain.init();
        ChromiumChain.init();
        LithiumChain.init();
        WaferChain.init();
        BrineChain.init();
        FusionElementsChain.init();
        NanotubeChain.init();
        VariousChains.init();
        SuperconductorsSMDChain.init();
        FusionComponents.init();
        NiobiumTantalumChain.init();
        Lasers.init();
        Dyes.init();
        SensorEmitter.init();
        SeleniumChain.init();
        OpticalComponents.init();
        OpticalCircuits.init();
        WormholeGeneratorChain.init();
        CosmicComponents.init();
        SupraCausalComponents.init();
        UltimateMaterials.init();
        DigitalInterfaces.init();
        InsulationWireAssemblyChain.init();
        ArcFurnaceOxidation.init();
    }

    public static void registerLargeMachineRecipes() {
        registerLargeChemicalRecipes();
        registerLargeMixerRecipes();
        registerLargeForgeHammerRecipes();
        registerAlloyBlastRecipes();
        registerChemicalPlantRecipes();
        registerGreenHouseRecipes();
        registerLargeCentrifugeRecipes();
        registerLaserEngraverRecipes();
    }

    private static void processIngotComposition(OrePrefix ingot, IngotMaterial material) {
        if (material.materialComponents.size() <= 1 || material.blastFurnaceTemperature == 0 || material.hasFlag(DISABLE_AUTOGENERATED_MIXER_RECIPE))
            return;

        int totalInputAmount = 0;

        //compute outputs
        for (MaterialStack component : material.materialComponents) {
            totalInputAmount += component.amount;
        }


        AtomicInteger totalMaterial = new AtomicInteger(0);
        int fluidComponents = (int) material.materialComponents.stream().filter(mat -> mat.material instanceof FluidMaterial && !(mat.material instanceof DustMaterial)).count();
        if ((material.materialComponents.size() - fluidComponents) <= MIXER_RECIPES.getMaxInputs() && fluidComponents <= MIXER_RECIPES.getMaxFluidInputs()) {
            SimpleRecipeBuilder builder = MIXER_RECIPES.recipeBuilder().EUt(30).duration((int) (material.getAverageMass() * totalInputAmount));
            material.materialComponents.forEach(materialStack -> {
                if (materialStack.material instanceof DustMaterial) {
                    builder.input(dust, materialStack.material, (int) materialStack.amount);
                } else if (materialStack.material instanceof FluidMaterial) {
                    builder.fluidInputs(((FluidMaterial) materialStack.material).getFluid((int) (1000 * materialStack.amount)));
                } else if (materialStack instanceof SimpleDustMaterialStack) {
                    SimpleDustMaterialStack simpleDustMaterialStack = (SimpleDustMaterialStack) materialStack;
                    builder.inputs(simpleDustMaterialStack.simpleDustMaterial.getItemStack((int) simpleDustMaterialStack.amount));
                }
                totalMaterial.addAndGet((int) materialStack.amount);
            });
            builder.outputs(OreDictUnifier.get(dust, material, totalMaterial.get()));
            builder.buildAndRegister();
        } else if ((material.materialComponents.size() + 1 - fluidComponents) <= LARGE_MIXER_RECIPES.getMaxInputs() && fluidComponents <= LARGE_MIXER_RECIPES.getMaxFluidInputs()) {
            LargeRecipeBuilder builder = LARGE_MIXER_RECIPES.recipeBuilder().EUt(30).duration((int) (material.getAverageMass() * totalInputAmount * 2));
            builder.notConsumable(new IntCircuitIngredient((material.materialComponents.size())));
            material.materialComponents.forEach(materialStack -> {
                if (materialStack.material instanceof DustMaterial) {
                    builder.input(dust, materialStack.material, (int) materialStack.amount);
                } else if (materialStack.material instanceof FluidMaterial) {
                    builder.fluidInputs(((FluidMaterial) materialStack.material).getFluid((int) (1000 * materialStack.amount)));
                } else if (materialStack instanceof SimpleDustMaterialStack) {
                    SimpleDustMaterialStack simpleDustMaterialStack = (SimpleDustMaterialStack) materialStack;
                    builder.inputs(simpleDustMaterialStack.simpleDustMaterial.getItemStack((int) simpleDustMaterialStack.amount));
                }
                totalMaterial.addAndGet((int) materialStack.amount);
            });
            builder.outputs(OreDictUnifier.get(dust, material, totalMaterial.get()));
            builder.buildAndRegister();
        } else {
            GALog.logger.warn("Material " + material.getUnlocalizedName() + " has too many material components to generate a recipe in either normal or large mixer.");
        }

    }

    private static void processGem(OrePrefix dustPrefix, GemMaterial material) {
        ItemStack gemStack = OreDictUnifier.get(gem, material);
        ItemStack flawlessStack = OreDictUnifier.get(gemFlawless, material);
        ItemStack exquisiteStack = OreDictUnifier.get(gemExquisite, material);
        ItemStack tinyDarkAshStack = OreDictUnifier.get(dustTiny, Materials.DarkAsh);

        if (!material.hasFlag(GemMaterial.MatFlags.CRYSTALLISABLE) && !material.hasFlag(Material.MatFlags.EXPLOSIVE) && !material.hasFlag(Material.MatFlags.FLAMMABLE)) {
            removeRecipesByInputs(IMPLOSION_RECIPES, OreDictUnifier.get(dustPrefix, material, 4), new ItemStack(Blocks.TNT, 2));

            boolean whiteListed = !(material.equals(EnderEye) || material.equals(EnderPearl) || material.equals(NetherStar));
            ValidationResult<Recipe> result;

            // Blacklist for materials without flawless+ gems
            // Laser Engraving Recipes
            if (whiteListed) {

                // Gem -> Flawless
                result = LASER_ENGRAVER_RECIPES.recipeBuilder().duration(2400).EUt(2000)
                        .inputs(GTUtility.copyAmount(4, gemStack))
                        .notConsumable(OrePrefix.craftingLens, MarkerMaterials.Color.White)
                        .outputs(GTUtility.copyAmount(1, flawlessStack))
                        .build();
                RecipeMaps.LASER_ENGRAVER_RECIPES.addRecipe(result);

                // Flawless -> Exquisite
                result = LASER_ENGRAVER_RECIPES.recipeBuilder().duration(2400).EUt(2000)
                        .inputs(GTUtility.copyAmount(4, flawlessStack))
                        .notConsumable(OrePrefix.craftingLens, MarkerMaterials.Color.White)
                        .outputs(GTUtility.copyAmount(1, exquisiteStack))
                        .build();
                RecipeMaps.LASER_ENGRAVER_RECIPES.addRecipe(result);
            }

            // Implosion Compressor Recipes
            for (ItemStack explosive : explosives) {

                // Dust -> Gem
                result = RecipeMaps.IMPLOSION_RECIPES.recipeBuilder()
                        .input(dustPrefix, material, 4)
                        .inputs(explosive)
                        .outputs(GTUtility.copyAmount(3, gemStack), GTUtility.copyAmount(2, tinyDarkAshStack))
                        .build();
                RecipeMaps.IMPLOSION_RECIPES.addRecipe(result);

                // Blacklist for materials without flawless+ gems
                if (whiteListed) {

                    // Gem -> Flawless
                    result = RecipeMaps.IMPLOSION_RECIPES.recipeBuilder()
                            .inputs(GTUtility.copyAmount(3, gemStack))
                            .inputs(explosive)
                            .outputs(GTUtility.copyAmount(1, flawlessStack), GTUtility.copyAmount(2, tinyDarkAshStack))
                            .build();
                    RecipeMaps.IMPLOSION_RECIPES.addRecipe(result);

                    // Flawless -> Exquisite
                    result = RecipeMaps.IMPLOSION_RECIPES.recipeBuilder()
                            .inputs(GTUtility.copyAmount(3, flawlessStack))
                            .inputs(explosive)
                            .outputs(GTUtility.copyAmount(1, exquisiteStack), GTUtility.copyAmount(2, tinyDarkAshStack))
                            .build();
                    RecipeMaps.IMPLOSION_RECIPES.addRecipe(result);
                }
            }
        }
    }

    private static void processStick(OrePrefix stickPrefix, DustMaterial material) {
        if (material instanceof GemMaterial || material instanceof IngotMaterial) {
            OrePrefix orePrefix = material instanceof IngotMaterial ? ingot : gem;
            Recipe r = LATHE_RECIPES.findRecipe(Long.MAX_VALUE, Collections.singletonList(OreDictUnifier.get(orePrefix, material)), Collections.emptyList(), Integer.MAX_VALUE);
            if (r != null) {
                LATHE_RECIPES.removeRecipe(r);
                LATHE_RECIPES.recipeBuilder()
                        .input(orePrefix, material)
                        .outputs(OreDictUnifier.get(stickPrefix, material))
                        .outputs(OreDictUnifier.get(dustSmall, material, 2))
                        .duration((int) Math.max(material.getAverageMass() * 2, 1))
                        .EUt(16)
                        .buildAndRegister();
            }
        }
    }


    private static void processTinyDust(OrePrefix dustTiny, DustMaterial material) {
        removeRecipesByInputs(RecipeMaps.PACKER_RECIPES, OreDictUnifier.get(dustTiny, material, 9), IntCircuitIngredient.getIntegratedCircuit(1));
        PACKER_RECIPES.recipeBuilder().duration(100).EUt(4).input(dustTiny, material, 9).notConsumable(SCHEMATIC_DUST.getStackForm()).outputs(OreDictUnifier.get(dust, material)).buildAndRegister();
    }

    private static void processSmallDust(OrePrefix dustSmall, DustMaterial material) {
        removeRecipesByInputs(RecipeMaps.PACKER_RECIPES, OreDictUnifier.get(dustSmall, material, 4), IntCircuitIngredient.getIntegratedCircuit(2));
        PACKER_RECIPES.recipeBuilder().duration(100).EUt(4).input(dustSmall, material, 4).notConsumable(SCHEMATIC_DUST.getStackForm()).outputs(OreDictUnifier.get(dust, material)).buildAndRegister();
    }

    private static void processNugget(OrePrefix nugget, IngotMaterial material) {
        removeRecipesByInputs(RecipeMaps.PACKER_RECIPES, OreDictUnifier.get(nugget, material, 9), IntCircuitIngredient.getIntegratedCircuit(1));
        PACKER_RECIPES.recipeBuilder().duration(100).EUt(4).input(nugget, material, 9).notConsumable(SCHEMATIC_3X3.getStackForm()).outputs(OreDictUnifier.get(ingot, material)).buildAndRegister();

        removeRecipesByInputs(RecipeMaps.UNPACKER_RECIPES, OreDictUnifier.get(ingot, material, 1), IntCircuitIngredient.getIntegratedCircuit(1));
        UNPACKER_RECIPES.recipeBuilder().duration(100).EUt(4).input(ingot, material, 1).notConsumable(SCHEMATIC_3X3.getStackForm()).outputs(OreDictUnifier.get(nugget, material, 9)).buildAndRegister();
    }


    // TODO This is very broken
    /*
     * ULV, LV: rubber
     * MV: polycap
     * HV: PE
     * EV: PVC
     * IV, LuV: Polyphenylene
     * ZPM, UV: PBI
     * UHV, UEV: PEEK
     * UIV, UMV: Zylon + insulation assembly
     * UXV, MAX: fullerene + insulation assembly
     *
     * Try to synchronize with machine hulls somewhat
     */
    private static void processWireGt(OrePrefix wireGt, IngotMaterial material) {
        if (material.cableProperties == null) return;
        int cableAmount = (int) (wireGt.materialAmount * 2 / M);
        OrePrefix cablePrefix = valueOf("cable" + wireGt.name().substring(4));
        ItemStack cableStack = OreDictUnifier.get(cablePrefix, material);


        for (FluidMaterial fluid : OLD_INSULATION_MATERIAL) {
            removeRecipesByInputs(RecipeMaps.ASSEMBLER_RECIPES, new ItemStack[]{OreDictUnifier.get(wireGtSingle, material), IntCircuitIngredient.getIntegratedCircuit(24)}, new FluidStack[]{fluid.getFluid(144)});
            removeRecipesByInputs(RecipeMaps.ASSEMBLER_RECIPES, new ItemStack[]{OreDictUnifier.get(wireGtSingle, material, 2), IntCircuitIngredient.getIntegratedCircuit(25)}, new FluidStack[]{fluid.getFluid(288)});
            removeRecipesByInputs(RecipeMaps.ASSEMBLER_RECIPES, new ItemStack[]{OreDictUnifier.get(wireGtSingle, material, 4), IntCircuitIngredient.getIntegratedCircuit(26)}, new FluidStack[]{fluid.getFluid(576)});
            removeRecipesByInputs(RecipeMaps.ASSEMBLER_RECIPES, new ItemStack[]{OreDictUnifier.get(wireGtSingle, material, 8), IntCircuitIngredient.getIntegratedCircuit(27)}, new FluidStack[]{fluid.getFluid(1152)});
            removeRecipesByInputs(RecipeMaps.ASSEMBLER_RECIPES, new ItemStack[]{OreDictUnifier.get(wireGtSingle, material, 16), IntCircuitIngredient.getIntegratedCircuit(28)}, new FluidStack[]{fluid.getFluid(2304)});
            removeRecipesByInputs(RecipeMaps.ASSEMBLER_RECIPES, new ItemStack[]{OreDictUnifier.get(wireGtDouble, material), IntCircuitIngredient.getIntegratedCircuit(24)}, new FluidStack[]{fluid.getFluid(288)});
            removeRecipesByInputs(RecipeMaps.ASSEMBLER_RECIPES, new ItemStack[]{OreDictUnifier.get(wireGtQuadruple, material), IntCircuitIngredient.getIntegratedCircuit(24)}, new FluidStack[]{fluid.getFluid(576)});
            removeRecipesByInputs(RecipeMaps.ASSEMBLER_RECIPES, new ItemStack[]{OreDictUnifier.get(wireGtOctal, material), IntCircuitIngredient.getIntegratedCircuit(24)}, new FluidStack[]{fluid.getFluid(1152)});
            removeRecipesByInputs(RecipeMaps.ASSEMBLER_RECIPES, new ItemStack[]{OreDictUnifier.get(wireGtHex, material), IntCircuitIngredient.getIntegratedCircuit(24)}, new FluidStack[]{fluid.getFluid(2304)});
        }

        int tier = GAUtility.getTierByVoltage(material.cableProperties.voltage);
        int cableSize = ArrayUtils.indexOf(WIRE_DOUBLING_ORDER, wireGt);
        int expensiveAmount = (cableAmount == 1) ? 1 : cableAmount / 2;

        if (wireGt != wireGtSingle) {
            switch (tier) {
                case 0:
                case 1:
                    RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().circuitMeta(24 + cableSize).input(wireGtSingle, material, cableAmount).input(foil, Rubber, cableAmount).outputs(cableStack).duration(150).EUt(8).buildAndRegister();
                case 2:
                case 3:
                    RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().circuitMeta(24 + cableSize).input(wireGtSingle, material, cableAmount).input(foil, Polycaprolactam, cableAmount).outputs(cableStack).duration(150).EUt(8).buildAndRegister();
                case 4:
                case 5:
                    RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().circuitMeta(24 + cableSize).input(wireGtSingle, material, cableAmount).input(foil, Plastic, cableAmount).outputs(cableStack).duration(150).EUt(8).buildAndRegister();
                case 6:
                case 7:
                    RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().circuitMeta(24 + cableSize).input(wireGtSingle, material, cableAmount).input(foil, PolyvinylChloride, cableAmount).outputs(cableStack).duration(150).EUt(8).buildAndRegister();
                case 8:
                case 9:
                    RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().circuitMeta(24 + cableSize).input(wireGtSingle, material, cableAmount).input(foil, PolyphenyleneSulfide, cableAmount).outputs(cableStack).duration(150).EUt(8).buildAndRegister();
                case 10:
                case 11:
                    RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().circuitMeta(24 + cableSize).input(wireGtSingle, material, cableAmount).input(foil, Polyetheretherketone, cableAmount).outputs(cableStack).duration(150).EUt(8).buildAndRegister();
                case 12:
                case 13:
                    RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().circuitMeta(24 + cableSize).input(wireGtSingle, material, cableAmount).inputs(INSULATION_WIRE_ASSEMBLY.getStackForm(expensiveAmount)).input(foil, Zylon, expensiveAmount).outputs(cableStack).duration(150).EUt(8).buildAndRegister();
                default:
                    RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().circuitMeta(24 + cableSize).input(wireGtSingle, material, cableAmount).inputs(INSULATION_WIRE_ASSEMBLY.getStackForm(expensiveAmount)).input(foil, FullerenePolymerMatrix, expensiveAmount).outputs(cableStack).duration(150).EUt(8).buildAndRegister();
            }
        }
        switch (tier) {
            case 0:
            case 1:
                RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().circuitMeta(24).input(wireGt, material).input(foil, Rubber, cableAmount).outputs(cableStack).duration(150).EUt(8).buildAndRegister();
            case 2:
            case 3:
                RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().circuitMeta(24).input(wireGt, material).input(foil, Polycaprolactam, cableAmount).outputs(cableStack).duration(150).EUt(8).buildAndRegister();
            case 4:
            case 5:
                RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().circuitMeta(24).input(wireGt, material).input(foil, Plastic, cableAmount).outputs(cableStack).duration(150).EUt(8).buildAndRegister();
            case 6:
            case 7:
                RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().circuitMeta(24).input(wireGt, material).input(foil, PolyvinylChloride, cableAmount).outputs(cableStack).duration(150).EUt(8).buildAndRegister();
            case 8:
            case 9:
                RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().circuitMeta(24).input(wireGt, material).input(foil, PolyphenyleneSulfide, cableAmount).outputs(cableStack).duration(150).EUt(8).buildAndRegister();
            case 10:
            case 11:
                RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().circuitMeta(24).input(wireGt, material).input(foil, Polyetheretherketone, cableAmount).outputs(cableStack).duration(150).EUt(8).buildAndRegister();
            case 12:
            case 13:
                RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().circuitMeta(24).input(wireGt, material).input(foil, Zylon, expensiveAmount).inputs(INSULATION_WIRE_ASSEMBLY.getStackForm(expensiveAmount)).outputs(cableStack).duration(150).EUt(8).buildAndRegister();
            default:
                RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().circuitMeta(24).input(wireGt, material).input(foil, FullerenePolymerMatrix, expensiveAmount).inputs(INSULATION_WIRE_ASSEMBLY.getStackForm(expensiveAmount)).outputs(cableStack).duration(150).EUt(8).buildAndRegister();
        }
    }

    private static void processRing(OrePrefix ring, IngotMaterial material) {
        if (!material.hasFlag(NO_SMASHING)) {
            ModHandler.removeRecipes(OreDictUnifier.get(ring, material));
            ModHandler.addShapedRecipe("tod_to_ring_" + material.toString(), OreDictUnifier.get(ring, material), "hS", " C", 'S', OreDictUnifier.get(stick, material), 'C', "craftingToolBendingCylinderSmall");
        }
    }

    private static void processFoil(OrePrefix foil, IngotMaterial material) {
        if (!OreDictUnifier.get(foil, material).isEmpty()) {
            if (GAConfig.GT6.BendingFoils) {
                ModHandler.addShapedRecipe("foil_" + material.toString(), OreDictUnifier.get(foil, material, 2), "hPC", 'P', new UnificationEntry(plate, material), 'C', "craftingToolBendingCylinder");
            }
            if (GAConfig.GT6.BendingFoilsAutomatic) {
                //Foil recipes
                removeRecipesByInputs(RecipeMaps.BENDER_RECIPES, OreDictUnifier.get(plate, material), IntCircuitIngredient.getIntegratedCircuit(0));
                CLUSTER_MILL_RECIPES.recipeBuilder().EUt(24).duration((int) material.getMass()).input(plate, material).outputs(OreDictUnifier.get(foil, material, 4)).buildAndRegister();
            }
        }
    }

    private static void processRound(OrePrefix round, IngotMaterial material) {
        ModHandler.addShapedRecipe("round" + material.toString(), OreDictUnifier.get(round, material), "fN", "N ", 'N', new UnificationEntry(nugget, material));
        LATHE_RECIPES.recipeBuilder().EUt(8).duration(100).input(nugget, material).outputs(OreDictUnifier.get(round, material)).buildAndRegister();
    }

    private static void processDoubleIngot(OrePrefix plate, IngotMaterial material) {
        ModHandler.removeRecipes(OreDictUnifier.get(plate, material));
        ModHandler.addShapedRecipe("ingot_double_" + material.toString(), OreDictUnifier.get(valueOf("ingotDouble"), material), "h", "I", "I", 'I', new UnificationEntry(ingot, material));
        ModHandler.addShapedRecipe("double_ingot_to_plate_" + material.toString(), OreDictUnifier.get(plate, material), "h", "I", 'I', OreDictUnifier.get(valueOf("ingotDouble"), material));
    }

    private static void processPlateCurved(OrePrefix plateCurved, IngotMaterial material) {

        ModHandler.addShapedRecipe("curved_plate_" + material.toString(), OreDictUnifier.get(plateCurved, material), "h", "P", "C", 'P', new UnificationEntry(plate, material), 'C', "craftingToolBendingCylinder");
        ModHandler.addShapedRecipe("flatten_plate_" + material.toString(), OreDictUnifier.get(plate, material), "h", "C", 'C', new UnificationEntry(plateCurved, material));
        BENDER_RECIPES.recipeBuilder().EUt(24).duration((int) material.getMass()).input(plate, material).circuitMeta(1).outputs(OreDictUnifier.get(plateCurved, material)).buildAndRegister();
        BENDER_RECIPES.recipeBuilder().EUt(24).duration((int) material.getMass()).input(plateCurved, material).circuitMeta(0).outputs(OreDictUnifier.get(plate, material)).buildAndRegister();

        if (!OreDictUnifier.get(rotor, material).isEmpty() && GAConfig.GT6.BendingRotors) {
            ModHandler.removeRecipes(OreDictUnifier.get(rotor, material));
            ModHandler.addShapedRecipe("ga_rotor_" + material.toString(), OreDictUnifier.get(rotor, material), "ChC", "SRf", "CdC", 'C', OreDictUnifier.get(plateCurved, material), 'S', OreDictUnifier.get(screw, material), 'R', OreDictUnifier.get(ring, material));
            ASSEMBLER_RECIPES.recipeBuilder().duration(240).EUt(24).inputs(OreDictUnifier.get(plateCurved, material, 4), OreDictUnifier.get(ring, material)).fluidInputs(SolderingAlloy.getFluid(32)).outputs(OreDictUnifier.get(rotor, material)).buildAndRegister();
        }

        if (!OreDictUnifier.get(pipeMedium, material).isEmpty() && GAConfig.GT6.BendingPipes && !OreDictUnifier.get(plateCurved, material).isEmpty()) {
            ModHandler.removeRecipes(OreDictUnifier.get(pipeSmall, material, 4));
            ModHandler.removeRecipes(OreDictUnifier.get(pipeMedium, material, 2));
            ModHandler.removeRecipes(OreDictUnifier.get(pipeLarge, material, 1));
            ModHandler.addShapedRecipe("pipe_ga_" + material.toString(), OreDictUnifier.get(pipeMedium, material, 2), "PPP", "wCh", "PPP", 'P', OreDictUnifier.get(plateCurved, material), 'C', "craftingToolBendingCylinder");
            ModHandler.addShapedRecipe("pipe_ga_large_" + material.toString(), OreDictUnifier.get(pipeLarge, material), "PhP", "PCP", "PwP", 'P', OreDictUnifier.get(plateCurved, material), 'C', "craftingToolBendingCylinder");
            ModHandler.addShapedRecipe("pipe_ga_small_" + material.toString(), OreDictUnifier.get(pipeSmall, material, 4), "PwP", "PCP", "PhP", 'P', OreDictUnifier.get(plateCurved, material), 'C', "craftingToolBendingCylinder");
        }
    }

    private static void processMetalCasing(OrePrefix prefix, IngotMaterial material) {
        if (material.hasFlag(GENERATE_METAL_CASING)) {
            ItemStack metalCasingStack = OreDictUnifier.get(prefix, material, 3);
            ModHandler.addShapedRecipe(String.format("metal_casing_%s", material), metalCasingStack,
                    "PhP", "PBP", "PwP",
                    'P', new UnificationEntry(plate, material),
                    'B', new UnificationEntry(frameGt, material));


            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                    .input(plate, material, 6)
                    .input(frameGt, material, 1)
                    .notConsumable(new IntCircuitIngredient(0))
                    .outputs(metalCasingStack)
                    .EUt(8).duration(200)
                    .buildAndRegister();
        }
    }

    private static void processTurbine(OrePrefix toolPrefix, IngotMaterial material) {
        removeRecipesByInputs(RecipeMaps.ASSEMBLER_RECIPES, OreDictUnifier.get(plate, material, 5), OreDictUnifier.get(screw, material, 2), IntCircuitIngredient.getIntegratedCircuit(10));
        removeRecipesByInputs(RecipeMaps.ASSEMBLER_RECIPES, OreDictUnifier.get(stickLong, Titanium), OreDictUnifier.get(turbineBlade, material, 8));
        ModHandler.removeRecipeByName(new ResourceLocation("gregtech:" + String.format("turbine_blade_%s", material)));

        ItemStack hugeTurbineRotorStackForm = GAMetaItems.HUGE_TURBINE_ROTOR.getStackForm();
        ItemStack largeTurbineRotorStackForm = GAMetaItems.LARGE_TURBINE_ROTOR.getStackForm();
        ItemStack mediumTurbineRotorStackForm = GAMetaItems.MEDIUM_TURBINE_ROTOR.getStackForm();
        ItemStack smallTurbineRotorStackForm = GAMetaItems.SMALL_TURBINE_ROTOR.getStackForm();

        TurbineRotorBehavior.getInstanceFor(smallTurbineRotorStackForm).setPartMaterial(smallTurbineRotorStackForm, material);
        TurbineRotorBehavior.getInstanceFor(mediumTurbineRotorStackForm).setPartMaterial(mediumTurbineRotorStackForm, material);
        TurbineRotorBehavior.getInstanceFor(largeTurbineRotorStackForm).setPartMaterial(largeTurbineRotorStackForm, material);
        TurbineRotorBehavior.getInstanceFor(hugeTurbineRotorStackForm).setPartMaterial(hugeTurbineRotorStackForm, material);

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .circuitMeta(0)
                .input(turbineBlade, material, 4)
                .input(stickLong, Titanium)
                .outputs(smallTurbineRotorStackForm)
                .duration(200).EUt(400)
                .buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .circuitMeta(1)
                .input(turbineBlade, material, 8)
                .input(stickLong, Tungsten)
                .outputs(mediumTurbineRotorStackForm)
                .duration(400).EUt(800)
                .buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .circuitMeta(2)
                .input(turbineBlade, material, 16)
                .input(stickLong, Osmium)
                .outputs(largeTurbineRotorStackForm)
                .duration(800).EUt(1600)
                .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .circuitMeta(3)
                .input(turbineBlade, material, 32)
                .input(stickLong, Rutherfordium)
                .outputs(hugeTurbineRotorStackForm)
                .duration(1600).EUt(3200)
                .buildAndRegister();

        RecipeMaps.FORMING_PRESS_RECIPES.recipeBuilder()
                .input(plateDense, material, 5)
                .input(screw, material, 2)
                .outputs(OreDictUnifier.get(toolPrefix, material))
                .duration(20).EUt(256)
                .buildAndRegister();

    }


    private static void registerLargeChemicalRecipes() {
        RecipeMaps.CHEMICAL_RECIPES.getRecipeList().forEach(recipe -> {
            LargeRecipeBuilder largeRecipeMap = GARecipeMaps.LARGE_CHEMICAL_RECIPES.recipeBuilder()
                    .EUt(recipe.getEUt())
                    .duration(recipe.getDuration())
                    .fluidInputs(recipe.getFluidInputs())
                    .inputsIngredients(recipe.getInputs())
                    .outputs(recipe.getOutputs())
                    .fluidOutputs(recipe.getFluidOutputs());

            recipe.getChancedOutputs().forEach(chanceEntry -> largeRecipeMap.chancedOutput(chanceEntry.getItemStack(), chanceEntry.getChance(), chanceEntry.getBoostPerTier()));
            largeRecipeMap.buildAndRegister();
        });
    }

    private static void registerLaserEngraverRecipes() {
        LASER_ENGRAVER_RECIPES.getRecipeList().forEach(recipe -> {
            LargeRecipeBuilder largeRecipeMap = LARGE_ENGRAVER_RECIPES.recipeBuilder()
                    .EUt(recipe.getEUt())
                    .duration(recipe.getDuration())
                    .fluidInputs(recipe.getFluidInputs())
                    .inputsIngredients(recipe.getInputs())
                    .outputs(recipe.getOutputs())
                    .fluidOutputs(recipe.getFluidOutputs());

            recipe.getChancedOutputs().forEach(chanceEntry -> largeRecipeMap.chancedOutput(chanceEntry.getItemStack(), chanceEntry.getChance(), chanceEntry.getBoostPerTier()));
            largeRecipeMap.buildAndRegister();
        });
    }

    private static void registerLargeMixerRecipes() {
        RecipeMaps.MIXER_RECIPES.getRecipeList().forEach(recipe -> {
            List<CountableIngredient> inputList = new ArrayList<>();
            IntCircuitIngredient circuitIngredient = null;

            for (CountableIngredient input : recipe.getInputs()) {
                if (!(input.getIngredient() instanceof IntCircuitIngredient))
                    inputList.add(input);
                else
                    circuitIngredient = (IntCircuitIngredient) input.getIngredient();
            }

            if (circuitIngredient == null)
                circuitIngredient = new IntCircuitIngredient(inputList.size() + recipe.getFluidInputs().size());

            GARecipeMaps.LARGE_MIXER_RECIPES.recipeBuilder()
                    .notConsumable(circuitIngredient)
                    .EUt(recipe.getEUt())
                    .duration(recipe.getDuration())
                    .fluidInputs(recipe.getFluidInputs())
                    .inputsIngredients(inputList)
                    .outputs(recipe.getOutputs())
                    .fluidOutputs(recipe.getFluidOutputs())
                    .buildAndRegister();
        });
    }

    private static void registerLargeCentrifugeRecipes() {
        CENTRIFUGE_RECIPES.getRecipeList().forEach(recipe -> {
            LargeRecipeBuilder builder = LARGE_CENTRIFUGE_RECIPES.recipeBuilder()
                    .EUt(recipe.getEUt())
                    .duration(recipe.getDuration())
                    .fluidInputs(recipe.getFluidInputs())
                    .inputsIngredients(recipe.getInputs())
                    .outputs(recipe.getOutputs())
                    .fluidOutputs(recipe.getFluidOutputs());
            recipe.getChancedOutputs().forEach(chanceEntry -> builder.chancedOutput(chanceEntry.getItemStack(), chanceEntry.getChance(), chanceEntry.getBoostPerTier()));
            builder.buildAndRegister();
        });
    }

    private static void registerLargeForgeHammerRecipes() {
        RecipeMaps.FORGE_HAMMER_RECIPES.getRecipeList().forEach(recipe -> {
            LargeRecipeBuilder builder = GARecipeMaps.LARGE_FORGE_HAMMER_RECIPES.recipeBuilder()
                    .EUt(recipe.getEUt())
                    .duration(recipe.getDuration())
                    .fluidInputs(Lubricant.getFluid(2))
                    .inputsIngredients(recipe.getInputs())
                    .outputs(recipe.getOutputs())
                    .fluidOutputs(recipe.getFluidOutputs());
            recipe.getChancedOutputs().forEach(chanceEntry -> builder.chancedOutput(chanceEntry.getItemStack(), chanceEntry.getChance(), chanceEntry.getBoostPerTier()));
            builder.buildAndRegister();
        });
    }

    private static void registerAlloyBlastRecipes() {
        for (Material material : Material.MATERIAL_REGISTRY) {
            if (!(material instanceof IngotMaterial))
                continue;
            IngotMaterial ingotMaterial = (IngotMaterial) material;
            if (ingotMaterial.blastFurnaceTemperature == 0)
                continue;

            GARecipeMaps.LARGE_MIXER_RECIPES.getRecipeList().stream()
                    .filter(recipe -> recipe.getOutputs().size() == 1)
                    .filter(recipe -> recipe.getFluidOutputs().size() == 0)
                    .filter(recipe -> recipe.getOutputs().get(0).isItemEqualIgnoreDurability(OreDictUnifier.get(dust, ingotMaterial)))
                    .findFirst()
                    .ifPresent(recipe -> {
                        ItemStack itemStack = recipe.getOutputs().get(0);
                        IngotMaterial ingot = ((IngotMaterial) (OreDictUnifier.getUnificationEntry(itemStack).material));
                        int duration = Math.max(1, (int) (ingot.getAverageMass() * ingot.blastFurnaceTemperature / 50L));
                        GARecipeMaps.BLAST_ALLOY_RECIPES.recipeBuilder()
                                .duration(duration * 80 / 100)
                                .EUt(120 * itemStack.getCount())
                                .fluidInputs(recipe.getFluidInputs())
                                .inputsIngredients(recipe.getInputs())
                                .fluidOutputs(ingot.getFluid(itemStack.getCount() * GTValues.L)).buildAndRegister();

                    });
        }
    }

    private static void registerChemicalPlantRecipes() {
        RecipeMaps.BREWING_RECIPES.getRecipeList().forEach(recipe -> {
            FluidStack fluidInput = recipe.getFluidInputs().get(0).copy();
            fluidInput.amount = (fluidInput.amount * 10 * 125 / 100);
            CountableIngredient itemInput = new CountableIngredient(recipe.getInputs().get(0).getIngredient(), recipe.getInputs().get(0).getCount() * 10);
            FluidStack fluidOutput = FermentationBase.getFluid(recipe.getFluidOutputs().get(0).amount * 10);

            GARecipeMaps.CHEMICAL_PLANT_RECIPES.recipeBuilder()
                    .EUt(recipe.getEUt() * 10)
                    .duration(recipe.getDuration() * 10)
                    .fluidInputs(fluidInput)
                    .inputsIngredients(Collections.singleton(itemInput))
                    .fluidOutputs(fluidOutput)
                    .buildAndRegister();
        });
    }

    private static void registerGreenHouseRecipes() {

        GREEN_HOUSE_RECIPES.recipeBuilder().duration(1000).notConsumable(new IntCircuitIngredient(0)).fluidInputs(Water.getFluid(2000)).notConsumable(new ItemStack(Items.POTATO)).outputs(new ItemStack(Items.POTATO, 1)).chancedOutput(new ItemStack(Items.POISONOUS_POTATO, 1), 100, 50).buildAndRegister();
        GREEN_HOUSE_RECIPES.recipeBuilder().duration(1000).notConsumable(new IntCircuitIngredient(0)).fluidInputs(Water.getFluid(2000)).notConsumable(new ItemStack(Items.CARROT)).outputs(new ItemStack(Items.CARROT, 1)).buildAndRegister();
        GREEN_HOUSE_RECIPES.recipeBuilder().duration(1000).notConsumable(new IntCircuitIngredient(0)).fluidInputs(Water.getFluid(2000)).notConsumable(new ItemStack(Blocks.CACTUS)).outputs(new ItemStack(Blocks.CACTUS, 1)).buildAndRegister();
        GREEN_HOUSE_RECIPES.recipeBuilder().duration(1000).notConsumable(new IntCircuitIngredient(0)).fluidInputs(Water.getFluid(2000)).notConsumable(new ItemStack(Items.REEDS)).outputs(new ItemStack(Items.REEDS, 1)).buildAndRegister();
        GREEN_HOUSE_RECIPES.recipeBuilder().duration(1000).notConsumable(new IntCircuitIngredient(0)).fluidInputs(Water.getFluid(2000)).notConsumable(new ItemStack(Blocks.BROWN_MUSHROOM)).outputs(new ItemStack(Blocks.BROWN_MUSHROOM, 1)).buildAndRegister();
        GREEN_HOUSE_RECIPES.recipeBuilder().duration(1000).notConsumable(new IntCircuitIngredient(0)).fluidInputs(Water.getFluid(2000)).notConsumable(new ItemStack(Blocks.RED_MUSHROOM)).outputs(new ItemStack(Blocks.RED_MUSHROOM, 1)).buildAndRegister();
        GREEN_HOUSE_RECIPES.recipeBuilder().duration(1000).notConsumable(new IntCircuitIngredient(0)).fluidInputs(Water.getFluid(2000)).notConsumable(new ItemStack(Items.BEETROOT)).outputs(new ItemStack(Items.BEETROOT, 1)).buildAndRegister();
        GREEN_HOUSE_RECIPES.recipeBuilder().duration(1000).notConsumable(new IntCircuitIngredient(0)).fluidInputs(Water.getFluid(2000)).notConsumable(new ItemStack(Items.MELON_SEEDS)).outputs(new ItemStack(Items.MELON, 1)).chancedOutput(new ItemStack(Items.MELON_SEEDS), 100, 50).buildAndRegister();
        GREEN_HOUSE_RECIPES.recipeBuilder().duration(1000).notConsumable(new IntCircuitIngredient(0)).fluidInputs(Water.getFluid(2000)).notConsumable(new ItemStack(Items.PUMPKIN_SEEDS)).outputs(new ItemStack(Blocks.PUMPKIN)).chancedOutput(new ItemStack(Items.PUMPKIN_SEEDS), 100, 50).buildAndRegister();

        GREEN_HOUSE_RECIPES.recipeBuilder().duration(900).notConsumable(new IntCircuitIngredient(1)).inputs(new ItemStack(Items.DYE, 1, 15)).fluidInputs(Water.getFluid(2000)).notConsumable(new ItemStack(Items.POTATO)).outputs(new ItemStack(Items.POTATO, 2)).chancedOutput(new ItemStack(Items.POISONOUS_POTATO, 1), 100, 50).buildAndRegister();
        GREEN_HOUSE_RECIPES.recipeBuilder().duration(900).notConsumable(new IntCircuitIngredient(1)).inputs(new ItemStack(Items.DYE, 1, 15)).fluidInputs(Water.getFluid(2000)).notConsumable(new ItemStack(Items.CARROT)).outputs(new ItemStack(Items.CARROT, 2)).buildAndRegister();
        GREEN_HOUSE_RECIPES.recipeBuilder().duration(900).notConsumable(new IntCircuitIngredient(1)).inputs(new ItemStack(Items.DYE, 1, 15)).fluidInputs(Water.getFluid(2000)).notConsumable(new ItemStack(Blocks.CACTUS)).outputs(new ItemStack(Blocks.CACTUS, 2)).buildAndRegister();
        GREEN_HOUSE_RECIPES.recipeBuilder().duration(900).notConsumable(new IntCircuitIngredient(1)).inputs(new ItemStack(Items.DYE, 1, 15)).fluidInputs(Water.getFluid(2000)).notConsumable(new ItemStack(Items.REEDS)).outputs(new ItemStack(Items.REEDS, 2)).buildAndRegister();
        GREEN_HOUSE_RECIPES.recipeBuilder().duration(900).notConsumable(new IntCircuitIngredient(1)).inputs(new ItemStack(Items.DYE, 1, 15)).fluidInputs(Water.getFluid(2000)).notConsumable(new ItemStack(Blocks.BROWN_MUSHROOM)).outputs(new ItemStack(Blocks.BROWN_MUSHROOM, 2)).buildAndRegister();
        GREEN_HOUSE_RECIPES.recipeBuilder().duration(900).notConsumable(new IntCircuitIngredient(1)).inputs(new ItemStack(Items.DYE, 1, 15)).fluidInputs(Water.getFluid(2000)).notConsumable(new ItemStack(Blocks.RED_MUSHROOM)).outputs(new ItemStack(Blocks.RED_MUSHROOM, 2)).buildAndRegister();
        GREEN_HOUSE_RECIPES.recipeBuilder().duration(900).notConsumable(new IntCircuitIngredient(1)).inputs(new ItemStack(Items.DYE, 1, 15)).fluidInputs(Water.getFluid(2000)).notConsumable(new ItemStack(Items.BEETROOT)).outputs(new ItemStack(Items.BEETROOT, 2)).buildAndRegister();
        GREEN_HOUSE_RECIPES.recipeBuilder().duration(900).notConsumable(new IntCircuitIngredient(1)).inputs(new ItemStack(Items.DYE, 1, 15)).fluidInputs(Water.getFluid(2000)).notConsumable(new ItemStack(Items.MELON_SEEDS)).outputs(new ItemStack(Items.MELON, 2)).chancedOutput(new ItemStack(Items.MELON_SEEDS), 100, 50).buildAndRegister();
        GREEN_HOUSE_RECIPES.recipeBuilder().duration(900).notConsumable(new IntCircuitIngredient(1)).inputs(new ItemStack(Items.DYE, 1, 15)).fluidInputs(Water.getFluid(2000)).notConsumable(new ItemStack(Items.PUMPKIN_SEEDS)).outputs(new ItemStack(Blocks.PUMPKIN, 2)).chancedOutput(new ItemStack(Items.PUMPKIN_SEEDS), 100, 50).buildAndRegister();


        GREEN_HOUSE_RECIPES.recipeBuilder().duration(600).notConsumable(new IntCircuitIngredient(2)).input(dust, OrganicFertilizer).fluidInputs(Water.getFluid(2000)).notConsumable(new ItemStack(Items.POTATO)).outputs(new ItemStack(Items.POTATO, 3)).chancedOutput(new ItemStack(Items.POISONOUS_POTATO, 1), 100, 50).buildAndRegister();
        GREEN_HOUSE_RECIPES.recipeBuilder().duration(600).notConsumable(new IntCircuitIngredient(2)).input(dust, OrganicFertilizer).fluidInputs(Water.getFluid(2000)).notConsumable(new ItemStack(Items.CARROT)).outputs(new ItemStack(Items.CARROT, 3)).buildAndRegister();
        GREEN_HOUSE_RECIPES.recipeBuilder().duration(600).notConsumable(new IntCircuitIngredient(2)).input(dust, OrganicFertilizer).fluidInputs(Water.getFluid(2000)).notConsumable(new ItemStack(Blocks.CACTUS)).outputs(new ItemStack(Blocks.CACTUS, 3)).buildAndRegister();
        GREEN_HOUSE_RECIPES.recipeBuilder().duration(600).notConsumable(new IntCircuitIngredient(2)).input(dust, OrganicFertilizer).fluidInputs(Water.getFluid(2000)).notConsumable(new ItemStack(Items.REEDS)).outputs(new ItemStack(Items.REEDS, 3)).buildAndRegister();
        GREEN_HOUSE_RECIPES.recipeBuilder().duration(600).notConsumable(new IntCircuitIngredient(2)).input(dust, OrganicFertilizer).fluidInputs(Water.getFluid(2000)).notConsumable(new ItemStack(Blocks.BROWN_MUSHROOM)).outputs(new ItemStack(Blocks.BROWN_MUSHROOM, 3)).buildAndRegister();
        GREEN_HOUSE_RECIPES.recipeBuilder().duration(600).notConsumable(new IntCircuitIngredient(2)).input(dust, OrganicFertilizer).fluidInputs(Water.getFluid(2000)).notConsumable(new ItemStack(Blocks.RED_MUSHROOM)).outputs(new ItemStack(Blocks.RED_MUSHROOM, 3)).buildAndRegister();
        GREEN_HOUSE_RECIPES.recipeBuilder().duration(600).notConsumable(new IntCircuitIngredient(2)).input(dust, OrganicFertilizer).fluidInputs(Water.getFluid(2000)).notConsumable(new ItemStack(Items.BEETROOT)).outputs(new ItemStack(Items.BEETROOT, 3)).buildAndRegister();
        GREEN_HOUSE_RECIPES.recipeBuilder().duration(600).notConsumable(new IntCircuitIngredient(2)).input(dust, OrganicFertilizer).fluidInputs(Water.getFluid(2000)).notConsumable(new ItemStack(Items.MELON_SEEDS)).outputs(new ItemStack(Items.MELON, 3)).chancedOutput(new ItemStack(Items.MELON_SEEDS), 100, 50).buildAndRegister();
        GREEN_HOUSE_RECIPES.recipeBuilder().duration(600).notConsumable(new IntCircuitIngredient(2)).input(dust, OrganicFertilizer).fluidInputs(Water.getFluid(2000)).notConsumable(new ItemStack(Items.PUMPKIN_SEEDS)).outputs(new ItemStack(Blocks.PUMPKIN, 3)).chancedOutput(new ItemStack(Items.PUMPKIN_SEEDS), 100, 50).buildAndRegister();


        Arrays.stream(OreDictionary.getOreNames()).filter(name -> name.startsWith("seed")).forEach(name -> {

            String oreName = name.substring(4);

            if (oreName.length() <= 0) {
                return;
            }
            String seedName = "seed" + titleCase(oreName);
            String cropName = "essence" + titleCase(oreName);

            List<ItemStack> registeredSeeds = OreDictionary.getOres(seedName, false);
            List<ItemStack> registeredCrops = OreDictionary.getOres(cropName, false);

            if (registeredSeeds.isEmpty() || registeredCrops.isEmpty()) {
                return;
            }

            ItemStack seed = registeredSeeds.get(0).copy();
            ItemStack essence = registeredCrops.get(0).copy();


            GREEN_HOUSE_RECIPES.recipeBuilder().duration(1000).fluidInputs(Water.getFluid(2000)).notConsumable(new IntCircuitIngredient(0)).notConsumable(seed).outputs(essence).chancedOutput(seed, 100, 50).buildAndRegister();

            essence = registeredCrops.get(0).copy();
            essence.setCount(3);
            GREEN_HOUSE_RECIPES.recipeBuilder().duration(600).fluidInputs(Water.getFluid(2000)).notConsumable(new IntCircuitIngredient(2)).notConsumable(seed).input(dust, OrganicFertilizer).outputs(essence).chancedOutput(seed, 100, 50).buildAndRegister();

        });

    }

    public static String titleCase(String input) {
        return input.substring(0, 1).toUpperCase(Locale.US) + input.substring(1);
    }


    public static void runRecipeGeneration() {
        for (Material material : Material.MATERIAL_REGISTRY) {
            if (material instanceof FluidMaterial) {
                OrePrefix prefix = material instanceof DustMaterial ? dust : null;
                processDecomposition(prefix, (FluidMaterial) material);
            }
        }
    }

    public static void processDecomposition(OrePrefix decomposePrefix, FluidMaterial material) {
        if (material.materialComponents.isEmpty() || !material.hasFlag(Material.MatFlags.DECOMPOSITION_BY_CENTRIFUGING) ||
                //disable decomposition if explicitly disabled for this material or for one of it's components
                material.hasFlag(DISABLE_DECOMPOSITION)) return;

        ArrayList<ItemStack> outputs = new ArrayList<>();
        ArrayList<FluidStack> fluidOutputs = new ArrayList<>();
        int totalInputAmount = 0;

        //compute outputs
        for (MaterialStack component : material.materialComponents) {
            totalInputAmount += component.amount;
            if (component.material instanceof DustMaterial) {
                outputs.add(OreDictUnifier.get(dust, component.material, (int) component.amount));
            } else if (component.material instanceof FluidMaterial) {
                FluidMaterial componentMaterial = (FluidMaterial) component.material;
                fluidOutputs.add(componentMaterial.getFluid((int) (1000 * component.amount)));
            }
        }

        //generate builder
        RecipeBuilder<?> builder;
        if (!material.hasFlag(Material.MatFlags.DECOMPOSITION_BY_ELECTROLYZING)) {
            builder = LARGE_CENTRIFUGE_RECIPES.recipeBuilder()
                    .duration((int) Math.ceil(material.getAverageMass() * totalInputAmount * 1.5))
                    .EUt(30);
        } else {
            return;
        }
        builder.outputs(outputs);
        builder.fluidOutputs(fluidOutputs);

        //finish builder
        if (decomposePrefix != null) {
            builder.input(decomposePrefix, material, totalInputAmount);
        } else {
            builder.fluidInputs(material.getFluid(1000 * totalInputAmount));
        }
        if (material.hasFlag(DECOMPOSITION_REQUIRES_HYDROGEN)) {
            builder.fluidInputs(Materials.Hydrogen.getFluid(1000 * totalInputAmount));
        }

        //register recipe
        builder.buildAndRegister();
    }

    public static void generatedRecipes() {
        List<ResourceLocation> recipesToRemove = new ArrayList<>();

        for (IRecipe recipe : CraftingManager.REGISTRY) {
            if (recipe.getRecipeOutput().isEmpty()) {
                //dont know how it can be possible but its appear
                continue;
            }
            if (recipe.getIngredients().size() == 9) {
                if (recipe.getIngredients().get(0).getMatchingStacks().length > 0 && Block.getBlockFromItem(recipe.getRecipeOutput().getItem()) != Blocks.AIR) {
                    boolean match = true;
                    for (int i = 1; i < recipe.getIngredients().size(); i++) {
                        if (recipe.getIngredients().get(i).getMatchingStacks().length == 0 || !recipe.getIngredients().get(0).getMatchingStacks()[0].isItemEqual(recipe.getIngredients().get(i).getMatchingStacks()[0])) {
                            match = false;
                            break;
                        }
                    }
                    if (match) {
                        if (GAConfig.GT5U.Remove3x3BlockRecipes) recipesToRemove.add(recipe.getRegistryName());
                        if (GAConfig.GT5U.GenerateCompressorRecipes && !recipe.getIngredients().get(0).test(new ItemStack(Items.WHEAT))) {
                            COMPRESSOR_RECIPES.recipeBuilder().duration(400).EUt(2)
                                    .inputs(CountableIngredient.from(recipe.getIngredients().get(0).getMatchingStacks()[0], recipe.getIngredients().size()))
                                    .outputs(recipe.getRecipeOutput())
                                    .buildAndRegister();
                        } else {
                            PACKER_RECIPES.recipeBuilder().duration(100).EUt(4)
                                    .inputs(CountableIngredient.from(recipe.getIngredients().get(0).getMatchingStacks()[0], recipe.getIngredients().size()))
                                    .notConsumable(SCHEMATIC_3X3.getStackForm())
                                    .outputs(recipe.getRecipeOutput())
                                    .buildAndRegister();
                        }
                    }
                }
            }
            if (recipe.getIngredients().size() == 9) {
                if (recipe.getIngredients().get(0).getMatchingStacks().length > 0 && Block.getBlockFromItem(recipe.getRecipeOutput().getItem()) == Blocks.AIR) {
                    boolean match = true;
                    for (int i = 1; i < recipe.getIngredients().size(); i++) {
                        if (recipe.getIngredients().get(i).getMatchingStacks().length == 0 || !recipe.getIngredients().get(0).getMatchingStacks()[0].isItemEqual(recipe.getIngredients().get(i).getMatchingStacks()[0])) {
                            match = false;
                            break;
                        }
                    }
                    if (match && !recipesToRemove.contains(recipe.getRegistryName()) && !hasPrefix(recipe.getRecipeOutput(), "dust", "dustTiny") && !hasPrefix(recipe.getRecipeOutput(), "ingot") && recipe.getRecipeOutput().getCount() == 1 && GAConfig.Misc.Packager3x3Recipes) {
                        PACKER_RECIPES.recipeBuilder().duration(100).EUt(4)
                                .inputs(CountableIngredient.from(recipe.getIngredients().get(0).getMatchingStacks()[0], recipe.getIngredients().size()))
                                .notConsumable(SCHEMATIC_3X3.getStackForm())
                                .outputs(recipe.getRecipeOutput())
                                .buildAndRegister();
                    }
                }
            }
            if (recipe.getIngredients().size() == 4) {
                if (recipe.getIngredients().get(0).getMatchingStacks().length > 0 && Block.getBlockFromItem(recipe.getRecipeOutput().getItem()) != Blocks.QUARTZ_BLOCK) {
                    boolean match = true;
                    for (int i = 1; i < recipe.getIngredients().size(); i++) {
                        if (recipe.getIngredients().get(i).getMatchingStacks().length == 0 || !recipe.getIngredients().get(0).getMatchingStacks()[0].isItemEqual(recipe.getIngredients().get(i).getMatchingStacks()[0])) {
                            match = false;
                            break;
                        }
                    }
                    if (match && !recipesToRemove.contains(recipe.getRegistryName()) && !hasPrefix(recipe.getRecipeOutput(), "dust", "dustSmall") && recipe.getRecipeOutput().getCount() == 1 && GAConfig.Misc.Packager2x2Recipes) {
                        PACKER_RECIPES.recipeBuilder().duration(100).EUt(4)
                                .inputs(CountableIngredient.from(recipe.getIngredients().get(0).getMatchingStacks()[0], recipe.getIngredients().size()))
                                .notConsumable(SCHEMATIC_2X2.getStackForm())
                                .outputs(recipe.getRecipeOutput())
                                .buildAndRegister();
                    }
                }
            }
            if (recipe.getIngredients().size() == 1 && recipe.getIngredients().get(0).getMatchingStacks().length > 0 && recipe.getRecipeOutput().getCount() == 9 && !hasPrefix(recipe.getIngredients().get(0).getMatchingStacks()[0], "ingot") && Block.getBlockFromItem(recipe.getIngredients().get(0).getMatchingStacks()[0].getItem()) != Blocks.AIR && Block.getBlockFromItem(recipe.getIngredients().get(0).getMatchingStacks()[0].getItem()) != Blocks.SLIME_BLOCK) {
                boolean isIngot = false;
                for (int i : OreDictionary.getOreIDs(recipe.getRecipeOutput())) {
                    if (OreDictionary.getOreName(i).startsWith("ingot")) {
                        isIngot = true;
                        break;
                    }
                }
                if (GAConfig.GT5U.RemoveBlockUncraftingRecipes) recipesToRemove.add(recipe.getRegistryName());
                if (!isIngot) {
                    FORGE_HAMMER_RECIPES.recipeBuilder().duration(100).EUt(24)
                            .inputs(recipe.getIngredients().get(0).getMatchingStacks()[0])
                            .outputs(recipe.getRecipeOutput())
                            .buildAndRegister();
                }
            }
            if (recipe.getIngredients().size() == 1 && recipe.getIngredients().get(0).getMatchingStacks().length > 0 && recipe.getRecipeOutput().getCount() == 9 && !hasPrefix(recipe.getIngredients().get(0).getMatchingStacks()[0], "ingot")) {
                if (!recipesToRemove.contains(recipe.getRegistryName()) && GAConfig.Misc.Unpackager3x3Recipes) {
                    UNPACKER_RECIPES.recipeBuilder().duration(100).EUt(8)
                            .inputs(recipe.getIngredients().get(0).getMatchingStacks()[0])
                            .notConsumable(SCHEMATIC_3X3.getStackForm())
                            .outputs(recipe.getRecipeOutput())
                            .buildAndRegister();
                }
            }

        }

        recipesToRemove.add(new ResourceLocation("gtadditions:block_compress_clay"));
        recipesToRemove.add(new ResourceLocation("gtadditions:block_decompress_clay"));

        for (ResourceLocation r : recipesToRemove)
            ModHandler.removeRecipeByName(r);
        recipesToRemove.clear();

        if (GAConfig.GT5U.GenerateCompressorRecipes) {
            ModHandler.removeRecipeByName(new ResourceLocation("minecraft:glowstone"));
            ModHandler.removeRecipeByName(new ResourceLocation("gregtech:block_compress_glowstone"));
            ModHandler.removeRecipeByName(new ResourceLocation("minecraft:quartz_block"));
            ModHandler.removeRecipeByName(new ResourceLocation("gregtech:block_compress_nether_quartz"));
            ModHandler.removeRecipeByName(new ResourceLocation("gregtech:block_decompress_nether_quartz"));
            ModHandler.removeRecipeByName(new ResourceLocation("gregtech:nether_quartz_block_to_nether_quartz"));
            FORGE_HAMMER_RECIPES.recipeBuilder().duration(100).EUt(24)
                    .inputs(OreDictUnifier.get(block, NetherQuartz))
                    .outputs(OreDictUnifier.get(gem, NetherQuartz, 4))
                    .buildAndRegister();

            COMPRESSOR_RECIPES.recipeBuilder().duration(400).EUt(2)
                    .input(gem, Materials.NetherQuartz, 4)
                    .outputs(new ItemStack(Blocks.QUARTZ_BLOCK)).
                    buildAndRegister();
        }


        if (GAConfig.GT5U.DisableLogToCharcoalSmeltg) {
            List<ItemStack> allWoodLogs = OreDictionary.getOres("logWood").stream().flatMap(stack -> ModHandler.getAllSubItems(stack).stream()).collect(Collectors.toList());

            for (ItemStack stack : allWoodLogs) {
                ItemStack smeltingOutput = ModHandler.getSmeltingOutput(stack);
                if (!smeltingOutput.isEmpty() && smeltingOutput.getItem() == Items.COAL && smeltingOutput.getMetadata() == 1) {
                    ItemStack woodStack = stack.copy();
                    woodStack.setItemDamage(OreDictionary.WILDCARD_VALUE);
                    ModHandler.removeFurnaceSmelting(woodStack);
                }
            }
        }
    }
}
