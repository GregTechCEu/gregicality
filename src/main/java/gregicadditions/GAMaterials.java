package gregicadditions;

import com.google.common.collect.ImmutableList;
import gregicadditions.materials.*;
import gregicadditions.utils.GALog;
import gregtech.api.unification.Element;
import gregtech.api.unification.material.IMaterialHandler;
import gregtech.api.unification.material.MaterialIconSet;
import gregtech.api.unification.material.type.*;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.common.pipelike.cable.WireProperties;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static com.google.common.collect.ImmutableList.of;
import static gregtech.api.unification.Element.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.type.DustMaterial.MatFlags.*;
import static gregtech.api.unification.material.type.FluidMaterial.MatFlags.GENERATE_FLUID_BLOCK;
import static gregtech.api.unification.material.type.FluidMaterial.MatFlags.GENERATE_PLASMA;
import static gregtech.api.unification.material.type.GemMaterial.MatFlags.GENERATE_LENSE;
import static gregtech.api.unification.material.type.GemMaterial.MatFlags.HIGH_SIFTER_OUTPUT;
import static gregtech.api.unification.material.type.IngotMaterial.MatFlags.*;
import static gregtech.api.unification.material.type.Material.MatFlags.*;
import static gregtech.api.unification.material.type.SolidMaterial.MatFlags.*;
import static gregtech.api.util.GTUtility.createFlag;

@IMaterialHandler.RegisterMaterialHandler
public class GAMaterials implements IMaterialHandler {

    public static final long GENERATE_METAL_CASING = createFlag(46);
    public static final long DISABLE_REPLICATION = createFlag(47);

    public static final long GENERATE_NUCLEAR_COMPOUND = createFlag(48);
    public static final long GENERATE_ISOTOPES_COMPOUND = createFlag(49);

    public static long STD_METAL = GENERATE_PLATE;
    static long EXT2_METAL = GENERATE_PLATE | GENERATE_DENSE | GENERATE_ROD | GENERATE_BOLT_SCREW | GENERATE_GEAR | GENERATE_FOIL | GENERATE_FINE_WIRE | GENERATE_LONG_ROD;
    static long CORE_METAL = EXT2_METAL | GENERATE_RING | GENERATE_FRAME | GENERATE_ROTOR | GENERATE_SMALL_GEAR | GENERATE_DENSE;
    public static final FluidMaterial FishOil = new FluidMaterial(999, "fish_oil", 14467421, MaterialIconSet.FLUID, ImmutableList.of(), GENERATE_FLUID_BLOCK | DISABLE_DECOMPOSITION);
    public static final FluidMaterial RawGrowthMedium = new FluidMaterial(998, "raw_growth_medium", 10777425, MaterialIconSet.FLUID, ImmutableList.of(), GENERATE_FLUID_BLOCK | DISABLE_DECOMPOSITION);
    public static final FluidMaterial SterileGrowthMedium = new FluidMaterial(997, "sterilized_growth_medium", 11306862, MaterialIconSet.FLUID, ImmutableList.of(), GENERATE_FLUID_BLOCK | DISABLE_DECOMPOSITION);
    public static final DustMaterial Meat = new DustMaterial(996, "meat", 12667980, MaterialIconSet.SAND, 1, ImmutableList.of(), DISABLE_DECOMPOSITION);
    public static final FluidMaterial NeutralMatter = new FluidMaterial(995, "neutral_matter", 3956968, MaterialIconSet.FLUID, ImmutableList.of(), DISABLE_DECOMPOSITION);
    public static final FluidMaterial PositiveMatter = new FluidMaterial(994, "positive_matter", 11279131, MaterialIconSet.FLUID, ImmutableList.of(), DISABLE_DECOMPOSITION);
    public static final IngotMaterial Neutronium = new IngotMaterial(993, "neutronium", 12829635, MaterialIconSet.METALLIC, 6, ImmutableList.of(), CORE_METAL | DISABLE_REPLICATION, Element.valueOf("Nt"), 24.0F, 12F, 655360);
    public static final DustMaterial Pyrotheum = new DustMaterial(991, "pyrotheum", 0xFF9A3C, MaterialIconSet.SAND, 1, ImmutableList.of(), DISABLE_DECOMPOSITION | EXCLUDE_BLOCK_CRAFTING_RECIPES | SMELT_INTO_FLUID);
    public static final DustMaterial EglinSteelBase = new DustMaterial(990, "eglin_steel_base", 0x8B4513, MaterialIconSet.SAND, 6, ImmutableList.of(new MaterialStack(Iron, 4), new MaterialStack(Kanthal, 1), new MaterialStack(Invar, 5)), 0);
    public static final IngotMaterial EglinSteel = new IngotMaterial(989, "eglin_steel", 0x8B4513, MaterialIconSet.METALLIC, 6, ImmutableList.of(new MaterialStack(GAMaterials.EglinSteelBase, 10), new MaterialStack(Sulfur, 1), new MaterialStack(Silicon, 1), new MaterialStack(Carbon, 1)), EXT2_METAL | GENERATE_METAL_CASING, null, 1048);
    public static final IngotMaterial Grisium = new IngotMaterial(987, "grisium", 0x355D6A, MaterialIconSet.METALLIC, 6, ImmutableList.of(new MaterialStack(Titanium, 9), new MaterialStack(Carbon, 9), new MaterialStack(Potassium, 9), new MaterialStack(Lithium, 9), new MaterialStack(Sulfur, 9), new MaterialStack(Hydrogen, 5)), EXT2_METAL | GENERATE_METAL_CASING, null, 3850);
    public static final IngotMaterial Inconel625 = new IngotMaterial(986, "inconel_a", 0x80C880, MaterialIconSet.METALLIC, 6, ImmutableList.of(new MaterialStack(Nickel, 3), new MaterialStack(Chrome, 7), new MaterialStack(Molybdenum, 10), new MaterialStack(Invar, 10), new MaterialStack(Nichrome, 13)), EXT2_METAL | GENERATE_METAL_CASING, null, 2425);
    public static final IngotMaterial MaragingSteel250 = new IngotMaterial(985, "maraging_steel_a", 0x92918D, MaterialIconSet.METALLIC, 6, ImmutableList.of(new MaterialStack(Steel, 16), new MaterialStack(Molybdenum, 1), new MaterialStack(Titanium, 1), new MaterialStack(Nickel, 4), new MaterialStack(Cobalt, 2)), EXT2_METAL | GENERATE_METAL_CASING, null, 2413);
    public static final IngotMaterial Potin = new IngotMaterial(984, "potin", 0xC99781, MaterialIconSet.METALLIC, 6, ImmutableList.of(new MaterialStack(Lead, 2), new MaterialStack(Bronze, 2), new MaterialStack(Tin, 1)), EXT2_METAL | GENERATE_METAL_CASING, null);
    public static final IngotMaterial Staballoy = new IngotMaterial(983, "staballoy", 0x444B42, MaterialIconSet.METALLIC, 6, ImmutableList.of(new MaterialStack(Uranium, 9), new MaterialStack(Titanium, 1)), EXT2_METAL | GENERATE_METAL_CASING, null, 3450);
    public static final IngotMaterial HastelloyN = new IngotMaterial(982, "hastelloy_n", 0xDDDDDD, MaterialIconSet.METALLIC, 6, ImmutableList.of(new MaterialStack(Yttrium, 2), new MaterialStack(Molybdenum, 4), new MaterialStack(Chrome, 2), new MaterialStack(Titanium, 2), new MaterialStack(Nickel, 15)), EXT2_METAL | GENERATE_METAL_CASING | GENERATE_DENSE, null, 4350);
    public static final IngotMaterial Tumbaga = new IngotMaterial(981, "tumbaga", 0xFFB20F, MaterialIconSet.METALLIC, 6, ImmutableList.of(new MaterialStack(Gold, 7), new MaterialStack(Bronze, 3)), EXT2_METAL | GENERATE_METAL_CASING, null, 1200);
    public static final IngotMaterial Stellite = new IngotMaterial(980, "stellite", 0x9991A5, MaterialIconSet.METALLIC, 6, ImmutableList.of(new MaterialStack(Cobalt, 9), new MaterialStack(Chrome, 9), new MaterialStack(Manganese, 5), new MaterialStack(Titanium, 2)), EXT2_METAL | GENERATE_METAL_CASING, null, 4310);
    public static final IngotMaterial Talonite = new IngotMaterial(979, "talonite", 0x9991A5, MaterialIconSet.METALLIC, 6, ImmutableList.of(new MaterialStack(Cobalt, 4), new MaterialStack(Chrome, 3), new MaterialStack(Phosphorus, 2), new MaterialStack(Molybdenum, 1)), EXT2_METAL | GENERATE_METAL_CASING, null, 3454);
    public static final FluidMaterial IronChloride = new FluidMaterial(978, "iron_chloride", 0x060b0b, MaterialIconSet.FLUID, ImmutableList.of(new MaterialStack(Iron, 1), new MaterialStack(Chlorine, 3)), DECOMPOSITION_BY_ELECTROLYZING);
    public static final IngotMaterial MVSuperconductorBase = new IngotMaterial(976, "mv_superconductor_base", 0x535353, MaterialIconSet.SHINY, 1, ImmutableList.of(new MaterialStack(Cadmium, 5), new MaterialStack(Magnesium, 1), new MaterialStack(Oxygen, 6)), STD_METAL, null, 1200);
    public static final IngotMaterial HVSuperconductorBase = new IngotMaterial(975, "hv_superconductor_base", 0x4a2400, MaterialIconSet.SHINY, 1, ImmutableList.of(new MaterialStack(Titanium, 1), new MaterialStack(Barium, 9), new MaterialStack(Copper, 10), new MaterialStack(Oxygen, 20)), STD_METAL, null, 3300);
    public static final IngotMaterial EVSuperconductorBase = new IngotMaterial(974, "ev_superconductor_base", 0x005800, MaterialIconSet.SHINY, 1, ImmutableList.of(new MaterialStack(Uranium, 1), new MaterialStack(Platinum, 3)), STD_METAL, null, 4400);
    public static final IngotMaterial IVSuperconductorBase = new IngotMaterial(973, "iv_superconductor_base", 0x300030, MaterialIconSet.SHINY, 1, ImmutableList.of(new MaterialStack(Vanadium, 1), new MaterialStack(Indium, 3)), STD_METAL, null, 5200);
    public static final IngotMaterial LuVSuperconductorBase = new IngotMaterial(972, "luv_superconductor_base", 0x7a3c00, MaterialIconSet.SHINY, 1, ImmutableList.of(new MaterialStack(Indium, 4), new MaterialStack(Bronze, 8), new MaterialStack(Barium, 2), new MaterialStack(Titanium, 1), new MaterialStack(Oxygen, 14)), STD_METAL, null, 6000);
    public static final IngotMaterial ZPMSuperconductorBase = new IngotMaterial(971, "zpm_superconductor_base", 0x111111, MaterialIconSet.SHINY, 1, ImmutableList.of(new MaterialStack(Naquadah, 4), new MaterialStack(Indium, 2), new MaterialStack(Palladium, 6), new MaterialStack(Osmium, 1)), STD_METAL, null, 8100);
    public static final IngotMaterial MVSuperconductor = new IngotMaterial(970, "mv_superconductor", 0x535353, MaterialIconSet.SHINY, 1, of(), DISABLE_DECOMPOSITION);
    public static final IngotMaterial HVSuperconductor = new IngotMaterial(969, "hv_superconductor", 0x4a2400, MaterialIconSet.SHINY, 1, of(), DISABLE_DECOMPOSITION);
    public static final IngotMaterial EVSuperconductor = new IngotMaterial(968, "ev_superconductor", 0x005800, MaterialIconSet.SHINY, 1, of(), DISABLE_DECOMPOSITION);
    public static final IngotMaterial IVSuperconductor = new IngotMaterial(967, "iv_superconductor", 0x300030, MaterialIconSet.SHINY, 1, of(), DISABLE_DECOMPOSITION);
    public static final IngotMaterial LuVSuperconductor = new IngotMaterial(966, "luv_superconductor", 0x7a3c00, MaterialIconSet.SHINY, 1, of(), DISABLE_DECOMPOSITION);
    public static final IngotMaterial ZPMSuperconductor = new IngotMaterial(964, "zpm_superconductor", 0x111111, MaterialIconSet.SHINY, 1, of(), DISABLE_DECOMPOSITION);
    public static final IngotMaterial Enderium = new IngotMaterial(963, "enderium", 0x23524a, MaterialIconSet.METALLIC, 3, ImmutableList.of(new MaterialStack(Lead, 3), new MaterialStack(Platinum, 1), new MaterialStack(EnderPearl, 1)), EXT2_METAL | DISABLE_DECOMPOSITION, null, 8.0F, 3.0F, 1280, 4500);
    public static final DustMaterial MicaPulp = new DustMaterial(962, "mica_based", 0x917445, MaterialIconSet.SAND, 1, ImmutableList.of(), DISABLE_DECOMPOSITION);
    public static final DustMaterial AluminoSilicateWool = new DustMaterial(961, "alumino_silicate_wool", 0xbbbbbb, MaterialIconSet.SAND, 1, ImmutableList.of(), DISABLE_DECOMPOSITION);
    public static final DustMaterial QuartzSand = new DustMaterial(960, "sand", 0xd2cfbc, MaterialIconSet.SAND, 1, ImmutableList.of(), DISABLE_DECOMPOSITION);
    public static final DustMaterial Massicot = new DustMaterial(959, "massicot", 8943149, MaterialIconSet.SAND, 1, ImmutableList.of(new MaterialStack(Lead, 1), new MaterialStack(Oxygen, 1)), 0);
    public static final DustMaterial AntimonyTrioxide = new DustMaterial(958, "antimony_trioxide", 8092544, MaterialIconSet.SAND, 1, ImmutableList.of(new MaterialStack(Antimony, 2), new MaterialStack(Oxygen, 3)), 0);
    public static final DustMaterial Zincite = new DustMaterial(957, "zincite", 8947843, MaterialIconSet.SAND, 1, ImmutableList.of(new MaterialStack(Zinc, 1), new MaterialStack(Oxygen, 1)), 0);
    public static final DustMaterial CobaltOxide = new DustMaterial(956, "cobalt_oxide", 3556352, MaterialIconSet.SAND, 1, ImmutableList.of(new MaterialStack(Cobalt, 1), new MaterialStack(Oxygen, 1)), 0);
    public static final DustMaterial ArsenicTrioxide = new DustMaterial(955, "arsenic_trioxide", 15856113, MaterialIconSet.ROUGH, 1, ImmutableList.of(new MaterialStack(Arsenic, 2), new MaterialStack(Oxygen, 3)), 0);
    public static final DustMaterial CupricOxide = new DustMaterial(954, "cupric_oxide", 526344, MaterialIconSet.SAND, 1, ImmutableList.of(new MaterialStack(Copper, 1), new MaterialStack(Oxygen, 1)), 0);
    public static final DustMaterial Ferrosilite = new DustMaterial(953, "ferrosilite", 5256470, MaterialIconSet.SAND, 1, ImmutableList.of(new MaterialStack(Iron, 1), new MaterialStack(Silicon, 1), new MaterialStack(Oxygen, 3)), 0);
    public static final DustMaterial Cryotheum = new DustMaterial(952, "cryotheum", 0x01F3F6, MaterialIconSet.SAND, 1, ImmutableList.of(), DISABLE_DECOMPOSITION | EXCLUDE_BLOCK_CRAFTING_RECIPES | SMELT_INTO_FLUID);
    public static final DustMaterial Blizz = new DustMaterial(951, "blizz", 0x01F3F6, MaterialIconSet.DULL, 1, ImmutableList.of(), NO_SMELTING | SMELT_INTO_FLUID | MORTAR_GRINDABLE | BURNING);
    public static final DustMaterial Snow = new DustMaterial(950, "snow", 0xFFFFFF, MaterialIconSet.OPAL, 1, ImmutableList.of(), NO_SMELTING);
    //    public static final FluidMaterial HighPressureSteam = new FluidMaterial(949, "high_pressure_steam", 0xFFFFFF, MaterialIconSet.GAS, of(new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 1)), NO_RECYCLING | GENERATE_FLUID_BLOCK | DISABLE_DECOMPOSITION).setFluidTemperature(1000);
    public static final FluidMaterial HighOctaneGasoline = new FluidMaterial(948, "high_octane", 0xC7860B, MaterialIconSet.FLUID, ImmutableList.of(), NO_RECYCLING | GENERATE_FLUID_BLOCK | DISABLE_DECOMPOSITION);
    public static final FluidMaterial Octane = new FluidMaterial(947, "octane", 0xCBCBCB, MaterialIconSet.FLUID, ImmutableList.of(), NO_RECYCLING | GENERATE_FLUID_BLOCK | DISABLE_DECOMPOSITION);
    public static final FluidMaterial EthylTertButylEther = new FluidMaterial(946, "ethyl_tert_butyl_ether", 0xCBCBCB, MaterialIconSet.FLUID, ImmutableList.of(), NO_RECYCLING | GENERATE_FLUID_BLOCK | DISABLE_DECOMPOSITION);
    public static final FluidMaterial Gasoline = new FluidMaterial(945, "gasoline", 0xC7860B, MaterialIconSet.FLUID, ImmutableList.of(), NO_RECYCLING | GENERATE_FLUID_BLOCK | DISABLE_DECOMPOSITION);
    public static final FluidMaterial RawGasoline = new FluidMaterial(944, "raw_gasoline", 0xC5560C, MaterialIconSet.FLUID, ImmutableList.of(), NO_RECYCLING | GENERATE_FLUID_BLOCK | DISABLE_DECOMPOSITION);
    public static final IngotMaterial Nitinol60 = new IngotMaterial(943, "nitinol_a", 0xCCB0EC, MaterialIconSet.METALLIC, 4, ImmutableList.of(new MaterialStack(Nickel, 2), new MaterialStack(Titanium, 3)), EXT2_METAL | GENERATE_METAL_CASING, null, 0);
    public static final IngotMaterial BabbittAlloy = new IngotMaterial(942, "babbitt_alloy", 0xA19CA4, MaterialIconSet.METALLIC, 4, ImmutableList.of(new MaterialStack(Tin, 5), new MaterialStack(Lead, 36), new MaterialStack(Antimony, 8), new MaterialStack(Arsenic, 1)), EXT2_METAL | GENERATE_METAL_CASING, null, 737);
    public static final IngotMaterial HG1223 = new IngotMaterial(941, "hg_alloy", 0x245397, MaterialIconSet.METALLIC, 4, ImmutableList.of(new MaterialStack(Mercury, 1), new MaterialStack(Barium, 2), new MaterialStack(Calcium, 2), new MaterialStack(Copper, 3), new MaterialStack(Oxygen, 8)), EXT2_METAL | GENERATE_METAL_CASING | GENERATE_DENSE, null, 5925);
    public static final IngotMaterial IncoloyMA956 = new IngotMaterial(940, "incoloy_ma", 0xAABEBB, MaterialIconSet.METALLIC, 4, ImmutableList.of(new MaterialStack(Iron, 16), new MaterialStack(Aluminium, 3), new MaterialStack(Chrome, 5), new MaterialStack(Yttrium, 1)), EXT2_METAL | GENERATE_METAL_CASING, null, 5925);
    public static final FluidMaterial RocketFuelH8N4C2O4 = new FluidMaterial(939, "rocket_fuel_a", 0x5ECB22, MaterialIconSet.FLUID, of(), NO_RECYCLING | GENERATE_FLUID_BLOCK | DISABLE_DECOMPOSITION);
    public static final FluidMaterial NitrogenTetroxide = new FluidMaterial(938, "nitrogen_tetroxide", 0xBE6800, MaterialIconSet.FLUID, of(), NO_RECYCLING | GENERATE_FLUID_BLOCK | DISABLE_DECOMPOSITION);
    public static final FluidMaterial CoalTar = new FluidMaterial(937, "coal_tar", 0x5E3122, MaterialIconSet.FLUID, of(), NO_RECYCLING | GENERATE_FLUID_BLOCK | DISABLE_DECOMPOSITION);
    public static final FluidMaterial CoalTarOil = new FluidMaterial(936, "coal_tar_oil", 0xB5B553, MaterialIconSet.FLUID, of(), NO_RECYCLING | GENERATE_FLUID_BLOCK | DISABLE_DECOMPOSITION);
    public static final FluidMaterial SulfuricCoalTarOil = new FluidMaterial(935, "sulfuric_coal_tar_oil", 0xFFFFAD, MaterialIconSet.FLUID, of(), NO_RECYCLING | GENERATE_FLUID_BLOCK | DISABLE_DECOMPOSITION);
    public static final FluidMaterial Anthracene = new FluidMaterial(934, "anthracene", 0xA2ACA2, MaterialIconSet.FLUID, of(), NO_RECYCLING | GENERATE_FLUID_BLOCK | DISABLE_DECOMPOSITION);
    public static final FluidMaterial Kerosene = new FluidMaterial(933, "kerosene", 0xD570D5, MaterialIconSet.FLUID, of(), NO_RECYCLING | GENERATE_FLUID_BLOCK | DISABLE_DECOMPOSITION);
    public static final FluidMaterial EthylBenzene = new FluidMaterial(932, "ethylbenzene", 0xD5D5D5, MaterialIconSet.FLUID, of(), NO_RECYCLING | GENERATE_FLUID_BLOCK | DISABLE_DECOMPOSITION);
    public static final FluidMaterial MonoMethylHydrazine = new FluidMaterial(931, "monomethylhydrazine", 0xFFFFFF, MaterialIconSet.FLUID, of(), NO_RECYCLING | GENERATE_FLUID_BLOCK | DISABLE_DECOMPOSITION);
    public static final FluidMaterial Hydrazine = new FluidMaterial(930, "hydrazine", 0xFFFFFF, MaterialIconSet.FLUID, of(), NO_RECYCLING | GENERATE_FLUID_BLOCK | DISABLE_DECOMPOSITION);
    public static final FluidMaterial HydrogenPeroxide = new FluidMaterial(929, "hydrogen_peroxide", 0xD1FFFF, MaterialIconSet.FLUID, of(), NO_RECYCLING | GENERATE_FLUID_BLOCK | DISABLE_DECOMPOSITION);
    public static final FluidMaterial EthylAnthraQuinone = new FluidMaterial(928, "ethylanthraquinone", 0xFFFF00, MaterialIconSet.FLUID, of(), NO_RECYCLING | GENERATE_FLUID_BLOCK | DISABLE_DECOMPOSITION);
    public static final FluidMaterial EthylAnthraHydroQuinone = new FluidMaterial(927, "ethylanthrahydroquinone", 0xFFFF47, MaterialIconSet.FLUID, of(), NO_RECYCLING | GENERATE_FLUID_BLOCK | DISABLE_DECOMPOSITION);
    public static final DustMaterial PhthalicAnhydride = new DustMaterial(926, "phthalicanhydride", 0xD1D1D1, MaterialIconSet.SAND, 1, of(), NO_RECYCLING | GENERATE_FLUID_BLOCK | DISABLE_DECOMPOSITION);
    public static final FluidMaterial PhthalicAcid = new FluidMaterial(925, "phthalicacid", 0xD1D1D1, MaterialIconSet.FLUID, of(), NO_RECYCLING | GENERATE_FLUID_BLOCK | DISABLE_DECOMPOSITION);
    public static final FluidMaterial Naphtalene = new FluidMaterial(924, "naphtalene", 0xFFFFFF, MaterialIconSet.FLUID, of(), NO_RECYCLING | GENERATE_FLUID_BLOCK | DISABLE_DECOMPOSITION);
    public static final FluidMaterial DenseHydrazineFuelMixture = new FluidMaterial(923, "dense_hydrazine_fuel_mixture", 0x5E2B4A, MaterialIconSet.FLUID, of(), NO_RECYCLING | GENERATE_FLUID_BLOCK | DISABLE_DECOMPOSITION);
    public static final FluidMaterial RocketFuelCN3H7O3 = new FluidMaterial(922, "rocket_fuel_b", 0xBE46C5, MaterialIconSet.FLUID, of(), NO_RECYCLING | GENERATE_FLUID_BLOCK | DISABLE_DECOMPOSITION);
    public static final FluidMaterial RP1RocketFuel = new FluidMaterial(921, "rocket_fuel_c", 0xFF503C, MaterialIconSet.FLUID, of(), NO_RECYCLING | GENERATE_FLUID_BLOCK | DISABLE_DECOMPOSITION);
    public static final FluidMaterial RP1 = new FluidMaterial(920, "rp", 0xFF6E5D, MaterialIconSet.FLUID, of(), NO_RECYCLING | GENERATE_FLUID_BLOCK | DISABLE_DECOMPOSITION);
    public static final FluidMaterial LiquidOxygen = new FluidMaterial(919, "liquid_oxygen", 0x81FFFD, MaterialIconSet.FLUID, of(), NO_RECYCLING | GENERATE_FLUID_BLOCK | DISABLE_DECOMPOSITION).setFluidTemperature(54);
    public static final FluidMaterial FermentationBase = new FluidMaterial(918, "fermentation_base", 0x3D5917, MaterialIconSet.FLUID, of(), NO_RECYCLING | GENERATE_FLUID_BLOCK | DISABLE_DECOMPOSITION);
    public static final FluidMaterial LiquidHydrogen = new FluidMaterial(917, "liquid_hydrogen", 0x3AFFC6, MaterialIconSet.FLUID, of(), NO_RECYCLING | GENERATE_FLUID_BLOCK | DISABLE_DECOMPOSITION).setFluidTemperature(14);
    public static final FluidMaterial Xenon = new FluidMaterial(916, "xenon", 0x270095, MaterialIconSet.FLUID, of(), NO_RECYCLING | GENERATE_FLUID_BLOCK | DISABLE_DECOMPOSITION, Xe);
    public static final FluidMaterial Neon = new FluidMaterial(915, "neon", 0xFF422A, MaterialIconSet.FLUID, of(), NO_RECYCLING | DISABLE_DECOMPOSITION | GENERATE_PLASMA, Ne);
    public static final FluidMaterial Krypton = new FluidMaterial(914, "krypton", 0x31C42F, MaterialIconSet.FLUID, of(), NO_RECYCLING | GENERATE_PLASMA | DISABLE_DECOMPOSITION, Kr);
    public static final IngotMaterial Zirconium = new IngotMaterial(912, "zirconium", 0xE0E1E1, MaterialIconSet.METALLIC, 6, of(), EXT2_METAL, Zr);
    public static final GemMaterial CubicZirconia = new GemMaterial(911, "cubic_zirconia", 0xFFDFE2, MaterialIconSet.DIAMOND, 6, of(new MaterialStack(Zirconium, 1), new MaterialStack(Oxygen, 2)), NO_RECYCLING | NO_SMELTING | GENERATE_LENSE);
    public static final GemMaterial Prasiolite = new GemMaterial(910, "prasiolite", 0x9EB749, MaterialIconSet.QUARTZ, 2, of(new MaterialStack(Silicon, 5), new MaterialStack(Oxygen, 10), new MaterialStack(Iron, 1)), GENERATE_ORE | GENERATE_LENSE);
    public static final DustMaterial Dibismusthydroborat = new DustMaterial(909, "dibismuthhydroborat", 0x00B749, MaterialIconSet.SAND, 2, of(new MaterialStack(Bismuth, 2), new MaterialStack(Hydrogen, 1), new MaterialStack(Boron, 1)), 0);
    public static final DustMaterial BismuthTellurite = new DustMaterial(908, "bismuth_tellurite", 0x006B38, MaterialIconSet.SAND, 2, of(new MaterialStack(Bismuth, 2), new MaterialStack(Tellurium, 3)), 0);
    public static final DustMaterial CircuitCompoundMK3 = new DustMaterial(907, "circuit_compound_mkc", 0x003316, MaterialIconSet.SAND, 2, of(new MaterialStack(IndiumGalliumPhosphide, 1), new MaterialStack(Dibismusthydroborat, 3), new MaterialStack(BismuthTellurite, 2)), 0);
    public static final DustMaterial YttriumOxide = new DustMaterial(906, "yttrium_oxide", 0xC6EBB3, MaterialIconSet.SAND, 2, of(new MaterialStack(Yttrium, 2), new MaterialStack(Oxygen, 3)), 0);
    public static final GemMaterial MagnetoResonatic = new GemMaterial(913, "magneto_resonatic", 0xFF97FF, MaterialIconSet.MAGNETIC, 2, of(new MaterialStack(Prasiolite, 3), new MaterialStack(BismuthTellurite, 6), new MaterialStack(CubicZirconia, 1), new MaterialStack(SteelMagnetic, 1)), NO_RECYCLING | DISABLE_DECOMPOSITION | FLAMMABLE | HIGH_SIFTER_OUTPUT | NO_SMELTING);
    public static final IngotMaterial ZirconiumCarbide = new IngotMaterial(905, "zirconium_carbide", 0xFFDACD, MaterialIconSet.SHINY, 2, of(new MaterialStack(Zirconium, 1), new MaterialStack(Carbon, 1)), EXT2_METAL | GENERATE_METAL_CASING, null, 0);
    public static final DustMaterial Zirkelite = new DustMaterial(904, "zirkelite", 0x6B5E6A, MaterialIconSet.DULL, 4, of(new MaterialStack(Calcium, 2), new MaterialStack(Thorium, 2), new MaterialStack(Cerium, 1), new MaterialStack(Zirconium, 7), new MaterialStack(Rutile, 6), new MaterialStack(Niobium, 4), new MaterialStack(Oxygen, 10)), GENERATE_ORE);
    public static final FluidMaterial PlatinumConcentrate = new FluidMaterial(903, "platinum_concentrate", Platinum.materialRGB, MaterialIconSet.FLUID, of(), 0);
    public static final DustMaterial PlatinumSaltCrude = new DustMaterial(902, "platinum_salt", Platinum.materialRGB, MaterialIconSet.DULL, 2, of(), 0);
    public static final DustMaterial PlatinumSaltRefined = new DustMaterial(901, "refined_platinum_salt", Platinum.materialRGB, MaterialIconSet.METALLIC, 2, of(), 0);
    public static final DustMaterial PlatinumMetallicPowder = new DustMaterial(900, "platinum_metallic_powder", Platinum.materialRGB, MaterialIconSet.METALLIC, 2, of(), GENERATE_ORE);
    public static final FluidMaterial AquaRegia = new FluidMaterial(899, "aqua_regia", 0xFFB132, MaterialIconSet.FLUID, of(), 0);
    public static final DustMaterial PlatinumResidue = new DustMaterial(898, "platinum_residue", 0x64632E, MaterialIconSet.ROUGH, 2, of(), 0);
    public static final FluidMaterial AmmoniumChloride = new FluidMaterial(897, "ammonium_chloride", 0xFFFFFF, MaterialIconSet.FLUID, of(), 0);
    public static final DustMaterial PlatinumRawPowder = new DustMaterial(896, "reprecipitated_platinum", Platinum.materialRGB, MaterialIconSet.METALLIC, 2, of(), 0);
    public static final FluidMaterial PalladiumAmmonia = new FluidMaterial(895, "palladium_enriched_ammonia", Platinum.materialRGB, MaterialIconSet.FLUID, of(), 0);
    public static final DustMaterial PalladiumMetallicPowder = new DustMaterial(894, "palladium_metallic_powder", Palladium.materialRGB, MaterialIconSet.METALLIC, 2, of(), GENERATE_ORE);
    public static final DustMaterial PalladiumRawPowder = new DustMaterial(893, "reprecipitated_palladium", Palladium.materialRGB, MaterialIconSet.METALLIC, 2, of(), 0);
    public static final DustMaterial PalladiumSalt = new DustMaterial(892, "palladium_salt", Palladium.materialRGB, MaterialIconSet.METALLIC, 2, of(), 0);
    public static final FluidMaterial SodiumFormate = new FluidMaterial(891, "sodium_formate", 0xFFAAAA, MaterialIconSet.FLUID, of(), 0);
    public static final DustMaterial SodiumSulfate = new DustMaterial(890, "sodium_sulfate", 0xFFFFFF, MaterialIconSet.ROUGH, 2, of(new MaterialStack(Sodium, 2), new MaterialStack(Sulfur, 1), new MaterialStack(Oxygen, 4)), 0);
    public static final FluidMaterial FormicAcid = new FluidMaterial(889, "formic_acid", 0xFFAA77, MaterialIconSet.FLUID, of(), 0);
    public static final DustMaterial PotassiumDisulfate = new DustMaterial(888, "potassium_disulfate", 0xFBBB66, MaterialIconSet.DULL, 2, of(new MaterialStack(Potassium, 2), new MaterialStack(Sulfur, 2), new MaterialStack(Oxygen, 7)), EXCLUDE_BLOCK_CRAFTING_RECIPES | SMELT_INTO_FLUID);
    public static final DustMaterial LeachResidue = new DustMaterial(887, "leach_residue", 0x644629, MaterialIconSet.ROUGH, 2, of(), 0);
    public static final FluidMaterial RhodiumSulfate = new FluidMaterial(886, "rhodium_sulfate", 0xEEAA55, MaterialIconSet.FLUID, of(), 0);
    public static final FluidMaterial RhodiumSulfateSolution = new FluidMaterial(885, "rhodium_sulfate_solution", 0xFFBB66, MaterialIconSet.FLUID, of(), 0);
    public static final DustMaterial CalciumChloride = new DustMaterial(884, "calcium_chloride", 0xFFFFFF, MaterialIconSet.DULL, 2, of(new MaterialStack(Calcium, 1), new MaterialStack(Chlorine, 2)), 0);
    public static final IngotMaterial Ruthenium = new IngotMaterial(883, "ruthenium", 0x646464, MaterialIconSet.METALLIC, 2, of(), EXT2_METAL, Element.Ru, 2607);
    public static final DustMaterial SodiumRuthenate = new DustMaterial(882, "sodium_ruthenate", 0x3A40CB, MaterialIconSet.SHINY, 2, of(), 0);
    public static final DustMaterial RutheniumTetroxide = new DustMaterial(881, "ruthenium_tetroxide", 0xC7C7C7, MaterialIconSet.DULL, 2, of(), SMELT_INTO_FLUID | GENERATE_FLUID_BLOCK | EXCLUDE_BLOCK_CRAFTING_RECIPES);
    public static final FluidMaterial HotRutheniumTetroxideSolution = new FluidMaterial(880, "hot_ruthenium_tetroxide_solution", 0xC7C7C7, MaterialIconSet.FLUID, of(), 0);
    public static final FluidMaterial RutheniumTetroxideSolution = new FluidMaterial(879, "ruthenium_tetroxide_solution", 0xC7C7C7, MaterialIconSet.FLUID, of(), 0);
    public static final DustMaterial IrOsLeachResidue = new DustMaterial(878, "rarest_metal_residue", 0x644629, MaterialIconSet.ROUGH, 2, of(), GENERATE_ORE);
    public static final DustMaterial IrLeachResidue = new DustMaterial(877, "iridium_metal_residue", 0x846649, MaterialIconSet.ROUGH, 2, of(), GENERATE_ORE);
    public static final DustMaterial PGSDResidue = new DustMaterial(876, "sludge_dust_residue", 0x846649, MaterialIconSet.DULL, 2, of(), 0);
    public static final FluidMaterial AcidicOsmiumSolution = new FluidMaterial(875, "acidic_osmium_solution", 0x846649, MaterialIconSet.FLUID, of(), 0);
    public static final DustMaterial IridiumDioxide = new DustMaterial(874, "iridium_dioxide", 0x846649, MaterialIconSet.DULL, 0, of(), EXCLUDE_BLOCK_CRAFTING_RECIPES | SMELT_INTO_FLUID);
    public static final FluidMaterial OsmiumSolution = new FluidMaterial(873, "osmium_solution", 0x846649, MaterialIconSet.FLUID, of(), 0);
    public static final FluidMaterial AcidicIridiumSolution = new FluidMaterial(872, "acidic_iridium_solution", 0x846649, MaterialIconSet.FLUID, of(), 0);
    public static final DustMaterial IridiumChloride = new DustMaterial(871, "iridium_chloride", 0x846649, MaterialIconSet.LAPIS, 2, of(), 0);
    public static final DustMaterial PGSDResidue2 = new DustMaterial(870, "metallic_sludge_dust_residue", 0x846649, MaterialIconSet.DULL, 2, of(), 0);
    public static final IngotMaterial Rhodium = new IngotMaterial(869, "rhodium", 0xF4F4F4, MaterialIconSet.METALLIC, 2, of(), EXT2_METAL, Element.Rh, 2237);
    public static final DustMaterial CrudeRhodiumMetall = new DustMaterial(868, "crude_rhodium_metal", 0x666666, MaterialIconSet.DULL, 2, of(), 0);
    public static final GemMaterial RhodiumSalt = new GemMaterial(867, "rhodium_salt", 0x848484, MaterialIconSet.GEM_VERTICAL, 2, of(), 0);
    public static final FluidMaterial RhodiumSaltSolution = new FluidMaterial(866, "rhodium_salt_solution", 0x667788, MaterialIconSet.FLUID, of(), 0);
    public static final DustMaterial SodiumNitrate = new DustMaterial(865, "sodium_nitrate", 0x846684, MaterialIconSet.ROUGH, 2, of(), 0);
    public static final DustMaterial RhodiumNitrate = new DustMaterial(864, "rhodium_nitrate", 0x776649, MaterialIconSet.QUARTZ, 2, of(), 0);
    public static final DustMaterial ZincSulfate = new DustMaterial(863, "zinc_sulfate", 0x846649, MaterialIconSet.QUARTZ, 2, of(), 0);
    public static final DustMaterial RhodiumFilterCake = new DustMaterial(862, "rhodium_filter_cake", 0x776649, MaterialIconSet.QUARTZ, 2, of(), 0);
    public static final FluidMaterial RhodiumFilterCakeSolution = new FluidMaterial(861, "rhodium_filter_cake_solution", 0x667788, MaterialIconSet.FLUID, of(), 0);
    public static final DustMaterial ReRhodium = new DustMaterial(860, "reprecipitated_rhodium", 0x776649, MaterialIconSet.QUARTZ, 2, of(), 0);
    public static final IngotMaterial RhodiumPlatedPalladium = new IngotMaterial(859, "rhodium_plated_palladium", Palladium.materialRGB, MaterialIconSet.METALLIC, 2, of(new MaterialStack(Palladium, 3), new MaterialStack(Rhodium, 1)), EXT2_METAL | DISABLE_DECOMPOSITION, null, 4500);
    public static final IngotMaterial Ruridit = new IngotMaterial(858, "ruridit", 0xA4A4A4, MaterialIconSet.METALLIC, 2, of(new MaterialStack(Ruthenium, 2), new MaterialStack(Iridium, 1)), CORE_METAL, null, 9950);

    public static final DustMaterial PotassiumNitrade = new DustMaterial(849, "potassium_nitrade", 0x81228D, MaterialIconSet.DULL, 0, of(new MaterialStack(Potassium, 1), new MaterialStack(Nitrogen, 1), new MaterialStack(Oxygen, 3)), 0);
    public static final DustMaterial ChromiumTrioxide = new DustMaterial(848, "chromium_trioxide", 0xFFE4E1, MaterialIconSet.DULL, 0, of(new MaterialStack(Chrome, 1), new MaterialStack(Oxygen, 3)), 0);
    public static final FluidMaterial Nitrochlorobenzene = new FluidMaterial(847, "nitrochlorobenzene", 0x8FB51A, MaterialIconSet.DULL, of(), 0);
    public static final FluidMaterial Dimethylbenzene = new FluidMaterial(846, "dimethylbenzene", 0x669C40, MaterialIconSet.DULL, of(), 0);
    public static final DustMaterial Potassiumdichromate = new DustMaterial(845, "potassiumdichromate", 0xFF087F, MaterialIconSet.DULL, 0, of(), 0);
    public static final FluidMaterial Dichlorobenzidine = new FluidMaterial(843, "dichlorobenzidine", 0xA1DEA6, MaterialIconSet.DULL, of(), 0);
    public static final FluidMaterial Diaminobenzidine = new FluidMaterial(842, "diaminobenzidine", 0x337D59, MaterialIconSet.DULL, of(), 0);
    public static final FluidMaterial Diphenylisophtalate = new FluidMaterial(841, "diphenylisophtalate", 0x246E57, MaterialIconSet.DULL, of(), 0);
    public static final IngotMaterial Polybenzimidazole = new IngotMaterial(840, "polybenzimidazole", 0x2D2D2D, MaterialIconSet.DULL, 0, of(), EXCLUDE_BLOCK_CRAFTING_RECIPES | SMELT_INTO_FLUID);
    public static final FluidMaterial Chlorobenzene = new FluidMaterial(839, "chlorobenzene", 0x326A3E, MaterialIconSet.DULL, of(), 0);
    public static final IngotMaterial Polonium = new IngotMaterial(838, "polonium", 0xC9D47E, MaterialIconSet.DULL, 4, of(), 0, Po);
    public static final IngotMaterial Copernicium = new IngotMaterial(837, "copernicium", 0xFFFEFF, MaterialIconSet.DULL, 4, of(), 0, Cn);
    public static final DustMaterial CopperLeach = new DustMaterial(836, "copper_leach", 0x765A30, MaterialIconSet.DULL, 2, of(new MaterialStack(Copper, 1), new MaterialStack(RareEarth, 1)), GENERATE_FLUID_BLOCK | DISABLE_DECOMPOSITION | SMELT_INTO_FLUID | NO_SMELTING);
    public static final DustMaterial SilverOxide = new DustMaterial(835, "silver_oxide", 0x4D4D4D, MaterialIconSet.DULL, 2, of(new MaterialStack(Silver, 2), new MaterialStack(Oxygen, 1)), 0);
    public static final DustMaterial SilverChloride = new DustMaterial(834, "silver_chloride", 0xFEFEFE, MaterialIconSet.DULL, 2, of(new MaterialStack(Silver, 1), new MaterialStack(Chlorine, 1)), DISABLE_DECOMPOSITION | GENERATE_FLUID_BLOCK);
    public static final FluidMaterial PreciousLeachNitrate = new FluidMaterial(833, "precious_leach_nitrate", 0x1D1F4D, MaterialIconSet.DULL, of(new MaterialStack(CopperLeach, 1), new MaterialStack(Silver, 1)), DISABLE_DECOMPOSITION);
    public static final DustMaterial PotassiumMetabisulfite = new DustMaterial(832, "potassium_metabisulfite", 0xFFFFFF, MaterialIconSet.DULL, 2, of(new MaterialStack(Potassium, 2), new MaterialStack(Sulfur, 2), new MaterialStack(Oxygen, 5)), 0);
    public static final FluidMaterial ChloroauricAcid = new FluidMaterial(831, "chloroauric_acid", 0xDFD11F, MaterialIconSet.SHINY, of(new MaterialStack(Hydrogen, 1), new MaterialStack(Gold, 1), new MaterialStack(Chlorine, 4)), DISABLE_DECOMPOSITION);
    public static final DustMaterial LeadNitrate = new DustMaterial(830, "lead_nitrate", 0xFEFEFE, MaterialIconSet.DULL, 2, of(new MaterialStack(Lead, 1), new MaterialStack(NitrogenTetroxide, 2)), 0);
    public static final DustMaterial GoldLeach = new DustMaterial(829, "gold_leach", 0xBBA52B, MaterialIconSet.DULL, 2, of(new MaterialStack(Gold, 1), new MaterialStack(RareEarth, 1)), DISABLE_DECOMPOSITION);
    public static final IngotMaterial GoldAlloy = new IngotMaterial(828, "gold_alloy", 0xBBA52B, MaterialIconSet.SHINY, 2, of(new MaterialStack(GoldLeach, 1), new MaterialStack(CopperLeach, 3)), DISABLE_DECOMPOSITION);
    public static final IngotMaterial PreciousMetal = new IngotMaterial(827, "precious_metal", 0xB99023, MaterialIconSet.SHINY, 2, of(new MaterialStack(GoldLeach, 1), new MaterialStack(RareEarth, 1)), DISABLE_DECOMPOSITION | GENERATE_ORE, null);

    //Thorium
    public static final RadioactiveMaterial ThoriumRadioactive = new RadioactiveMaterial(Thorium);
    public static final IsotopeMaterial Thorium232Isotope = new IsotopeMaterial(Thorium, RadioactiveMaterial.REGISTRY.get(Thorium), 232);
    public static final IsotopeMaterial Thorium233 = new IsotopeMaterial(825, RadioactiveMaterial.REGISTRY.get(Thorium), 233, 0);

    //Protactinium
    public static final RadioactiveMaterial Protactinium = new RadioactiveMaterial(824, "protactinium", 0xA78B6D, MaterialIconSet.METALLIC, 3, of(), EXT2_METAL, Pa, 0, 0, 0, 0);
    public static final IsotopeMaterial Protactinium233 = new IsotopeMaterial(823, RadioactiveMaterial.REGISTRY.get(Protactinium.getMaterial()), 233, 0);

    //uranium
    public static final RadioactiveMaterial UraniumRadioactive = new RadioactiveMaterial(822, "uranium_radioactive", Uranium.materialRGB, MaterialIconSet.METALLIC, 3, of(), EXT2_METAL | GENERATE_ORE, U, 0, 0, 0, 0);

    public static final IsotopeMaterial Uranium238Isotope = new IsotopeMaterial(Uranium, RadioactiveMaterial.REGISTRY.get(UraniumRadioactive.getMaterial()), 238);
    public static final IsotopeMaterial Uranium233 = new IsotopeMaterial(821, RadioactiveMaterial.REGISTRY.get(UraniumRadioactive.getMaterial()), 233, 0);
    public static final IsotopeMaterial Uranium234 = new IsotopeMaterial(820, RadioactiveMaterial.REGISTRY.get(UraniumRadioactive.getMaterial()), 234, 0);
    public static final IsotopeMaterial Uranium235Isotope = new IsotopeMaterial(Uranium235, RadioactiveMaterial.REGISTRY.get(UraniumRadioactive.getMaterial()), 235);
    public static final IsotopeMaterial Uranium239 = new IsotopeMaterial(819, RadioactiveMaterial.REGISTRY.get(UraniumRadioactive.getMaterial()), 239, 0);

    //Neptunium
    public static final RadioactiveMaterial Neptunium = new RadioactiveMaterial(818, "neptunium", 0x284D7B, MaterialIconSet.METALLIC, 3, of(), EXT2_METAL, Np, 0, 0, 0, 0);
    public static final IsotopeMaterial Neptunium235 = new IsotopeMaterial(817, RadioactiveMaterial.REGISTRY.get(Neptunium.getMaterial()), 235, 0);
    public static final IsotopeMaterial Neptunium237 = new IsotopeMaterial(816, RadioactiveMaterial.REGISTRY.get(Neptunium.getMaterial()), 237, 0);
    public static final IsotopeMaterial Neptunium239 = new IsotopeMaterial(815, RadioactiveMaterial.REGISTRY.get(Neptunium.getMaterial()), 239, 0);

    //plutonium
    public static final RadioactiveMaterial PlutoniumRadioactive = new RadioactiveMaterial(814, "plutonium_radioactive", Plutonium.materialRGB, MaterialIconSet.METALLIC, 3, of(), EXT2_METAL, Pu, 0, 0, 0, 0);
    public static final IsotopeMaterial Plutonium239 = new IsotopeMaterial(813, RadioactiveMaterial.REGISTRY.get(PlutoniumRadioactive.getMaterial()), 239, 0);
    public static final IsotopeMaterial Plutonium240 = new IsotopeMaterial(812, RadioactiveMaterial.REGISTRY.get(PlutoniumRadioactive.getMaterial()), 240, 0);
    public static final IsotopeMaterial Plutonium241Isotope = new IsotopeMaterial(Plutonium241, RadioactiveMaterial.REGISTRY.get(PlutoniumRadioactive.getMaterial()), 241);
    public static final IsotopeMaterial Plutonium244Isotope = new IsotopeMaterial(Plutonium, RadioactiveMaterial.REGISTRY.get(PlutoniumRadioactive.getMaterial()), 244);
    public static final IsotopeMaterial Plutonium245 = new IsotopeMaterial(811, RadioactiveMaterial.REGISTRY.get(PlutoniumRadioactive.getMaterial()), 245, 0);

    //americium
    public static final RadioactiveMaterial AmericiumRadioactive = new RadioactiveMaterial(Americium);
    public static final IsotopeMaterial Americium241 = new IsotopeMaterial(810, RadioactiveMaterial.REGISTRY.get(Americium), 241, 0);
    public static final IsotopeMaterial Americium243 = new IsotopeMaterial(809, RadioactiveMaterial.REGISTRY.get(Americium), 243, 0);
    public static final IsotopeMaterial Americium245 = new IsotopeMaterial(808, RadioactiveMaterial.REGISTRY.get(Americium), 245, 0);

    //curium
    public static final RadioactiveMaterial Curium = new RadioactiveMaterial(807, "curium", 0x7B544E, MaterialIconSet.METALLIC, 3, of(), EXT2_METAL, Cm, 0, 0, 0, 0);
    public static final IsotopeMaterial Curium245 = new IsotopeMaterial(806, RadioactiveMaterial.REGISTRY.get(Curium.getMaterial()), 245, 0);
    public static final IsotopeMaterial Curium246 = new IsotopeMaterial(805, RadioactiveMaterial.REGISTRY.get(Curium.getMaterial()), 246, 0);
    public static final IsotopeMaterial Curium247 = new IsotopeMaterial(804, RadioactiveMaterial.REGISTRY.get(Curium.getMaterial()), 247, 0);
    public static final IsotopeMaterial Curium250 = new IsotopeMaterial(803, RadioactiveMaterial.REGISTRY.get(Curium.getMaterial()), 250, 0);
    public static final IsotopeMaterial Curium251 = new IsotopeMaterial(802, RadioactiveMaterial.REGISTRY.get(Curium.getMaterial()), 251, 0);

    //Berkelium
    public static final RadioactiveMaterial Berkelium = new RadioactiveMaterial(801, "berkelium", 0x645A88, MaterialIconSet.METALLIC, 3, of(), EXT2_METAL, Bk, 0, 0, 0, 0);
    public static final IsotopeMaterial Berkelium247 = new IsotopeMaterial(800, RadioactiveMaterial.REGISTRY.get(Berkelium.getMaterial()), 247, 0);
    public static final IsotopeMaterial Berkelium249 = new IsotopeMaterial(799, RadioactiveMaterial.REGISTRY.get(Berkelium.getMaterial()), 249, 0);
    public static final IsotopeMaterial Berkelium251 = new IsotopeMaterial(798, RadioactiveMaterial.REGISTRY.get(Berkelium.getMaterial()), 251, 0);

    //Californium
    public static final RadioactiveMaterial Californium = new RadioactiveMaterial(797, "californium", 0xA85A12, MaterialIconSet.METALLIC, 3, of(), EXT2_METAL, Cf, 0, 0, 0, 0);
    public static final IsotopeMaterial Californium251 = new IsotopeMaterial(796, RadioactiveMaterial.REGISTRY.get(Californium.getMaterial()), 251, 0);
    public static final IsotopeMaterial Californium252 = new IsotopeMaterial(795, RadioactiveMaterial.REGISTRY.get(Californium.getMaterial()), 252, 0);
    public static final IsotopeMaterial Californium253 = new IsotopeMaterial(794, RadioactiveMaterial.REGISTRY.get(Californium.getMaterial()), 253, 0);
    public static final IsotopeMaterial Californium256 = new IsotopeMaterial(793, RadioactiveMaterial.REGISTRY.get(Californium.getMaterial()), 256, 0);
    public static final IsotopeMaterial Californium257 = new IsotopeMaterial(792, RadioactiveMaterial.REGISTRY.get(Californium.getMaterial()), 257, 0);

    //Einsteinium
    public static final RadioactiveMaterial Einsteinium = new RadioactiveMaterial(791, "einsteinium", 0xCE9F00, MaterialIconSet.METALLIC, 3, of(), EXT2_METAL, Es, 0, 0, 0, 0);
    public static final IsotopeMaterial Einsteinium253 = new IsotopeMaterial(790, RadioactiveMaterial.REGISTRY.get(Einsteinium.getMaterial()), 253, 0);
    public static final IsotopeMaterial Einsteinium255 = new IsotopeMaterial(789, RadioactiveMaterial.REGISTRY.get(Einsteinium.getMaterial()), 255, 0);
    public static final IsotopeMaterial Einsteinium257 = new IsotopeMaterial(787, RadioactiveMaterial.REGISTRY.get(Einsteinium.getMaterial()), 257, 0);

    //Fermium
    public static final RadioactiveMaterial Fermium = new RadioactiveMaterial(786, "fermium", 0x984ACF, MaterialIconSet.METALLIC, 3, of(), EXT2_METAL, Fm, 0, 0, 0, 0);
    public static final IsotopeMaterial Fermium257 = new IsotopeMaterial(785, RadioactiveMaterial.REGISTRY.get(Fermium.getMaterial()), 257, 0);
    public static final IsotopeMaterial Fermium258 = new IsotopeMaterial(784, RadioactiveMaterial.REGISTRY.get(Fermium.getMaterial()), 258, 0);
    public static final IsotopeMaterial Fermium259 = new IsotopeMaterial(783, RadioactiveMaterial.REGISTRY.get(Fermium.getMaterial()), 259, 0);
    public static final IsotopeMaterial Fermium262 = new IsotopeMaterial(782, RadioactiveMaterial.REGISTRY.get(Fermium.getMaterial()), 262, 0);
    public static final IsotopeMaterial Fermium263 = new IsotopeMaterial(781, RadioactiveMaterial.REGISTRY.get(Fermium.getMaterial()), 263, 0);

    //Mendelevium
    public static final RadioactiveMaterial Mendelevium = new RadioactiveMaterial(780, "mendelevium", 0x1D4ACF, MaterialIconSet.METALLIC, 3, of(), EXT2_METAL, Md, 0, 0, 0, 0);
    public static final IsotopeMaterial Mendelevium259 = new IsotopeMaterial(779, RadioactiveMaterial.REGISTRY.get(Mendelevium.getMaterial()), 259, 0);
    public static final IsotopeMaterial Mendelevium261 = new IsotopeMaterial(778, RadioactiveMaterial.REGISTRY.get(Mendelevium.getMaterial()), 261, 0);
    public static final IsotopeMaterial Mendelevium263 = new IsotopeMaterial(777, RadioactiveMaterial.REGISTRY.get(Mendelevium.getMaterial()), 263, 0);


    public static final DustMaterial SodiumPotassiumAlloy = new DustMaterial(776, "sodium_potassium_alloy", 0x252525, MaterialIconSet.SHINY, 2, of(new MaterialStack(Sodium, 7), new MaterialStack(Potassium, 3)), SMELT_INTO_FLUID);
    public static final IngotMaterial LithiumFluoride = new IngotMaterial(774, "lithium_fluoride", 0x757575, MaterialIconSet.SHINY, 2, of(new MaterialStack(Lithium, 1), new MaterialStack(Fluorine, 1)), 0); //LithiumHydroxide + Hydrogen = LithiumFluoride
    public static final DustMaterial SodiumFluoride = new DustMaterial(773, "sodium_fluoride", 0xFDFDFD, MaterialIconSet.DULL, 2, of(new MaterialStack(Sodium, 1), new MaterialStack(Fluorine, 1)), 0);
    public static final DustMaterial PotassiumFluoride = new DustMaterial(772, "potassium_fluoride", 0xFDFDFD, MaterialIconSet.DULL, 2, of(new MaterialStack(Potassium, 1), new MaterialStack(Fluorine, 1)), 0);
    public static final DustMaterial FLiNaK = new DustMaterial(771, "flinak", 0x252525, MaterialIconSet.DULL, 2, of(new MaterialStack(LithiumFluoride, 1), new MaterialStack(SodiumFluoride, 1), new MaterialStack(PotassiumFluoride, 1)), SMELT_INTO_FLUID);
    public static final IngotMaterial BerylliumFluoride = new IngotMaterial(770, "beryllium_fluoride", 0x757575, MaterialIconSet.SHINY, 2, of(new MaterialStack(Beryllium, 1), new MaterialStack(Fluorine, 2)), 0);
    public static final DustMaterial FLiBe = new DustMaterial(769, "flibe", 0x252525, MaterialIconSet.DULL, 2, of(new MaterialStack(LithiumFluoride, 1), new MaterialStack(BerylliumFluoride, 1)), SMELT_INTO_FLUID);
    public static final DustMaterial LeadBismuthEutectic = new IngotMaterial(768, "lead_bismuth_eutatic", 0x757575, MaterialIconSet.SHINY, 2, of(new MaterialStack(Lead, 3), new MaterialStack(Bismuth, 7)), SMELT_INTO_FLUID);
    public static final IngotMaterial Francium = new IngotMaterial(767, "francium", 0xAAAAAA, MaterialIconSet.SHINY, 2, of(), 0, Fr);
    public static final IngotMaterial Radium = new IngotMaterial(766, "radium", 0xFFC840, MaterialIconSet.SHINY, 2, of(), 0, Ra);
    public static final IngotMaterial Actinium = new IngotMaterial(765, "actinium", 0xC3D1FF, MaterialIconSet.SHINY, 2, of(), 0, Ac);
    public static final IngotMaterial Hafnium = new IngotMaterial(764, "hafnium", 0xB6BAB4, MaterialIconSet.SHINY, 2, of(), 0, Hf);
    public static final IngotMaterial Rhenium = new IngotMaterial(763, "rhenium", 0xB6BAB4, MaterialIconSet.SHINY, 2, of(), EXT2_METAL, Re);
    public static final IngotMaterial Technetium = new IngotMaterial(762, "technetium", 0xB6BAB4, MaterialIconSet.SHINY, 2, of(), 0, Tc);
    public static final IngotMaterial Thallium = new IngotMaterial(761, "thalium", 0xB6BAB4, MaterialIconSet.SHINY, 2, of(), 0, Tl);
    public static final IngotMaterial Germanium = new IngotMaterial(760, "germanium", 0xB6BAB4, MaterialIconSet.SHINY, 2, of(), 0, Ge);
    public static final DustMaterial Selenium = new IngotMaterial(759, "selenium", 0xB6BA6B, MaterialIconSet.SHINY, 2, of(), 0, Se);
    public static final FluidMaterial Bromine = new FluidMaterial(758, "bromine", 0xB64D6B, MaterialIconSet.SHINY, of(), 0, Br);
    public static final DustMaterial Iodine = new DustMaterial(757, "iodine", 0x2C344F, MaterialIconSet.SHINY, 2, of(), 0, I);
    public static final IngotMaterial Astatine = new IngotMaterial(756, "astatine", 0xB6BAB4, MaterialIconSet.SHINY, 2, of(), 0, At);
    public static final IngotMaterial AbyssalAlloy = new IngotMaterial(755, "abyssal_alloy", 0x9E706A, MaterialIconSet.METALLIC, 6, of(new MaterialStack(StainlessSteel, 5), new MaterialStack(TungstenCarbide, 5), new MaterialStack(Nichrome, 5), new MaterialStack(Bronze, 5), new MaterialStack(IncoloyMA956, 5), new MaterialStack(Iodine, 1), new MaterialStack(Germanium, 1), new MaterialStack(Radon, 1)), EXT2_METAL | DISABLE_DECOMPOSITION, null, 9625);
    public static final DustMaterial OrganicFertilizer = new DustMaterial(754, "organic_fertilizer", 0xDDDDDD, MaterialIconSet.SHINY, 2, of(new MaterialStack(Calcium, 5), new MaterialStack(Phosphate, 3), new MaterialStack(Hydrogen, 1), new MaterialStack(Oxygen, 1)), 0);

    public static final DustMaterial CalciumTungstate = new DustMaterial(753, "calcium_tungstate", 0x6e6867, MaterialIconSet.SHINY, 0, of(new MaterialStack(Tungsten, 1), new MaterialStack(Calcium, 1), new MaterialStack(Oxygen, 4)), DISABLE_DECOMPOSITION);
    public static final FluidMaterial SodiumTungstate = new FluidMaterial(752, "sodium_tungstate", 0x7a7777, MaterialIconSet.FLUID, of(new MaterialStack(Sodium, 2), new MaterialStack(Tungsten, 1), new MaterialStack(Oxygen, 4)), DISABLE_DECOMPOSITION);
    public static final DustMaterial TungsticAcid = new DustMaterial(751, "tungstic_acid", 0xFFE700, MaterialIconSet.SHINY, 0, of(new MaterialStack(Hydrogen, 2), new MaterialStack(Tungsten, 1), new MaterialStack(Oxygen, 4)), DISABLE_DECOMPOSITION);
    public static final DustMaterial TungstenTrioxide = new DustMaterial(750, "tungsten_trioxide", 0x99FF97, Tungsten.materialIconSet, 0, of(new MaterialStack(Tungsten, 1), new MaterialStack(Oxygen, 3)), DISABLE_DECOMPOSITION);
    public static final DustMaterial TungstenHexachloride = new DustMaterial(749, "tungsten_hexachloride", 0x533f75, MaterialIconSet.METALLIC, 0, of(new MaterialStack(Tungsten, 1), new MaterialStack(Chlorine, 6)), DISABLE_DECOMPOSITION);

    public static final DustMaterial NaquadricCompound = new DustMaterial(748, "naquadric_compound", Naquadah.materialRGB, Naquadah.materialIconSet, Naquadah.harvestLevel, Naquadah.materialComponents, GENERATE_ORE);
    public static final DustMaterial EnrichedNaquadricCompound = new DustMaterial(747, "enriched_naquadric_compound", NaquadahEnriched.materialRGB, NaquadahEnriched.materialIconSet, NaquadahEnriched.harvestLevel, NaquadahEnriched.materialComponents, GENERATE_ORE);
    public static final DustMaterial NaquadriaticCompound = new DustMaterial(746, "naquadriatic_compound", Naquadria.materialRGB, Naquadria.materialIconSet, Naquadria.harvestLevel, Naquadria.materialComponents, GENERATE_ORE);

    public static final IngotMaterial Rutherfordium = new IngotMaterial(743, "rutherfordium", 0xFFF6A1, MaterialIconSet.SHINY, 7, of(), EXT2_METAL, Rf);
    public static final IngotMaterial Dubnium = new IngotMaterial(742, "dubnium", 0xD3FDFF, MaterialIconSet.SHINY, 7, of(), EXT2_METAL, Db);

    public static final IngotMaterial ReactorSteel = new IngotMaterial(741, "reactor_steel", 0xB4B3B0, MaterialIconSet.SHINY, 2, of(new MaterialStack(Iron, 15), new MaterialStack(Niobium, 1), new MaterialStack(Vanadium, 4), new MaterialStack(Carbon, 2)), DISABLE_DECOMPOSITION | GENERATE_DENSE);

    public static final IngotMaterial Seaborgium = new IngotMaterial(736, "seaborgium", 0x19c5ff, MaterialIconSet.SHINY, 7, of(), CORE_METAL | GENERATE_FRAME, Sg);
    public static final IngotMaterial Bohrium = new IngotMaterial(735, "bohrium", 0xdc57ff, MaterialIconSet.SHINY, 7, of(), CORE_METAL | GENERATE_FRAME, Bh);

    public static final IngotMaterial Incoloy813 = new IngotMaterial(734, "incoloy813", 0x37bf7e, MaterialIconSet.SHINY, 2, of(new MaterialStack(VanadiumSteel, 4), new MaterialStack(Osmiridium, 2), new MaterialStack(Technetium, 3), new MaterialStack(Germanium, 4), new MaterialStack(Iridium, 7), new MaterialStack(Duranium, 5), new MaterialStack(Californium252.getMaterial(), 1)), EXT2_METAL | DISABLE_DECOMPOSITION | GENERATE_METAL_CASING, null, 10000);
    public static final IngotMaterial EnrichedNaquadahAlloy = new IngotMaterial(733, "enriched_naquadah_alloy", 0x403f3d, MaterialIconSet.SHINY, 2, of(new MaterialStack(NaquadahEnriched, 4), new MaterialStack(Rhodium, 2), new MaterialStack(Ruthenium, 2), new MaterialStack(Dubnium, 1), new MaterialStack(Rubidium, 2), new MaterialStack(Einsteinium255.getMaterial(), 1)), EXT2_METAL | DISABLE_DECOMPOSITION | GENERATE_METAL_CASING, null, 10000);
    public static final IngotMaterial HastelloyX78 = new IngotMaterial(732, "hastelloy_x78", 0x6ba3e3, MaterialIconSet.SHINY, 2, of(new MaterialStack(NaquadahAlloy, 10), new MaterialStack(Rhenium, 5), new MaterialStack(Naquadria, 4), new MaterialStack(Gadolinium, 3), new MaterialStack(Strontium, 2), new MaterialStack(Polonium, 3), new MaterialStack(Rutherfordium, 2), new MaterialStack(Fermium258.getMaterial(), 1)), EXT2_METAL | DISABLE_DECOMPOSITION | GENERATE_METAL_CASING, null, 25000);
    public static final IngotMaterial HastelloyK243 = new IngotMaterial(731, "hastelloy_k243", 0xa5f564, MaterialIconSet.SHINY, 2, of(new MaterialStack(HastelloyX78, 5), new MaterialStack(NiobiumNitride, 2), new MaterialStack(Tritanium, 4), new MaterialStack(TungstenCarbide, 4), new MaterialStack(Promethium, 4), new MaterialStack(Mendelevium261.getMaterial(), 1)), EXT2_METAL | DISABLE_DECOMPOSITION | GENERATE_METAL_CASING, null, 25000);

    public static final IngotMaterial Polyetheretherketone = new IngotMaterial(730, "polyetheretherketone", 0x403e37, MaterialIconSet.DULL, 2, of(), EXCLUDE_BLOCK_CRAFTING_RECIPES | SMELT_INTO_FLUID | GENERATE_FOIL, null);
    public static final IngotMaterial Zylon = new IngotMaterial(729, "zylon", 0xFFE000, MaterialIconSet.SHINY, 2, of(), EXCLUDE_BLOCK_CRAFTING_RECIPES | SMELT_INTO_FLUID | GENERATE_FOIL, null);
    public static final IngotMaterial FullerenePolymerMatrix = new IngotMaterial(728, "fullerene_polymer_matrix", 0x403e37, MaterialIconSet.DULL, 2, of(), EXCLUDE_BLOCK_CRAFTING_RECIPES | SMELT_INTO_FLUID | GENERATE_FOIL, null);

    public static final IngotMaterial UMVSuperconductorBase = new IngotMaterial(725, "umv_superconductor_base", 0x883afc, MaterialIconSet.SHINY, 1, of(), STD_METAL, null, 75000);
    public static final IngotMaterial UMVSuperconductor = new IngotMaterial(724, "umv_superconductor", 0x883afc, MaterialIconSet.SHINY, 1, of(), DISABLE_DECOMPOSITION);
    public static final IngotMaterial UXVSuperconductorBase = new IngotMaterial(723, "uxv_superconductor_base", 0xe34b5a, MaterialIconSet.SHINY, 1, of(), STD_METAL, null, 100000);
    public static final IngotMaterial UXVSuperconductor = new IngotMaterial(722, "uxv_superconductor", 0xe34b5a, MaterialIconSet.SHINY, 1, of(), DISABLE_DECOMPOSITION);

    public static final SimpleFluidMaterial NaquadricSolution = new SimpleFluidMaterial("naquadric_solution", 0x232225);
    public static final SimpleFluidMaterial EnrichedNaquadricSolution = new SimpleFluidMaterial("enriched_naquadric_solution", 0x312735);
    public static final SimpleFluidMaterial NaquadriaticSolution = new SimpleFluidMaterial("naquadriatic_solution", 0x312735);
    public static final SimpleDustMaterial AntimonyTrifluoride = new SimpleDustMaterial("antimony_trifluoride", 0xc7c7c7, (short) 0, Antimony.materialIconSet, of(new MaterialStack(Antimony, 1), new MaterialStack(Fluorine, 3)));
    public static final SimpleFluidMaterial AntimonyPentafluoride = new SimpleFluidMaterial("antimony_pentafluoride", Antimony.materialRGB);
    public static final SimpleFluidMaterial FluoroantimonicAcid = new SimpleFluidMaterial("fluoroantimonic_acid", 0x8da2a5);
    public static final SimpleFluidMaterial FluoronaquadricAcid = new SimpleFluidMaterial("fluoronaquadric_acid", 0x485d60);
    public static final SimpleFluidMaterial EnrichedFluoronaquadricAcid = new SimpleFluidMaterial("enriched_fluoronaquadric_acid", 0x485d60);
    public static final SimpleFluidMaterial FluoronaquadriaticAcid = new SimpleFluidMaterial("fluoronaquadriatic_acid", 0x485d60);
    public static final SimpleFluidMaterial NaquadahDifluoride = new SimpleFluidMaterial("naquadah_difluoride", 0x324649);
    public static final SimpleFluidMaterial EnrichedNaquadahDifluoride = new SimpleFluidMaterial("enriched_naquadah_difluoride", 0x141e1f);
    public static final SimpleFluidMaterial NaquadriaDifluoride = new SimpleFluidMaterial("naquadria_difluoride", 0x141e1f);
    public static final SimpleDustMaterial IndiumTrifluoride = new SimpleDustMaterial("indium_trifluoride", 0x2b0f48, (short) 1, Indium.materialIconSet, of(new MaterialStack(Indium, 1), new MaterialStack(Fluorine, 3)));
    public static final SimpleDustMaterial IndiumTrioxide = new SimpleDustMaterial("indium_trioxide", 0x2b0f48, (short) 2, Indium.materialIconSet, of(new MaterialStack(Indium, 1), new MaterialStack(Oxygen, 3)));
    public static final SimpleDustMaterial NaquadahConcentrate = new SimpleDustMaterial("naquadah_concentrate", Naquadah.materialRGB, (short) 3, Naquadah.materialIconSet);
    public static final SimpleDustMaterial EnrichedNaquadahConcentrate = new SimpleDustMaterial("enriched_naquadah_concentrate", NaquadahEnriched.materialRGB, (short) 4, NaquadahEnriched.materialIconSet);
    public static final SimpleDustMaterial NaquadriaConcentrate = new SimpleDustMaterial("naquadria_concentrate", Naquadria.materialRGB, (short) 5, Naquadria.materialIconSet);

    public static final SimpleFluidMaterial NaquadriaHexafluoride = new SimpleFluidMaterial("naquadria_hexafluoride", 0x111c27);
    public static final SimpleFluidMaterial RadonDifluoride = new SimpleFluidMaterial("radon_difluoride", 0x9966ff);
    public static final SimpleFluidMaterial RadonNaquadriaoctafluoride = new SimpleFluidMaterial("radon_naquadriaoctafluoride", 0x111c27);
    public static final SimpleFluidMaterial XenonTrioxide = new SimpleFluidMaterial("xenon_trioxide", 0x432791);
    public static final SimpleFluidMaterial CesiumFluoride = new SimpleFluidMaterial("cesium_fluoride", 0xabab69);
    public static final SimpleFluidMaterial CesiumXenontrioxideFluoride = new SimpleFluidMaterial("cesium_xenontrioxide_fluoride", 0x3333cc);
    public static final SimpleFluidMaterial RadonTrioxide = new SimpleFluidMaterial("radon_trioxide", 0x9966ff);
    public static final SimpleFluidMaterial NaquadriaCesiumXenonNonfluoride = new SimpleFluidMaterial("naquadria_cesium_xenon_nonfluoride", 0x1c1c5e);
    public static final SimpleFluidMaterial NitrosylFluoride = new SimpleFluidMaterial("nitrosyl_fluoride", NitricOxide.materialRGB);
    public static final SimpleFluidMaterial NitrosoniumOctafluoroxenate = new SimpleFluidMaterial("nitrosonium_octafluoroxenate", 0x3f3f83);
    public static final SimpleFluidMaterial NaquadriaCesiumfluoride = new SimpleFluidMaterial("naquadria_cesiumfluoride", 0x636379);

    public static final SimpleFluidMaterial EnrichedNaquadahhexafluoride = new SimpleFluidMaterial("enriched_naquadahhexafluoride", 0x030330);
    public static final SimpleFluidMaterial EnrichedXenonHexafluoronaquadate = new SimpleFluidMaterial("enriched_xenon_hexafluoronaquadate", 0x1e1ec2);
    public static final SimpleFluidMaterial AuricChloride = new SimpleFluidMaterial("auric_chloride", 0xdffb50);
    public static final SimpleFluidMaterial BromineTrifluoride = new SimpleFluidMaterial("bromine_trifluoride", 0xfcde1d);
    public static final SimpleDustMaterial AuricFluoride = new SimpleDustMaterial("auric_fluoride", 0xdffb50, (short) 6, MaterialIconSet.SHINY, of(new MaterialStack(Gold, 1), new MaterialStack(Fluorine, 3)));
    public static final SimpleFluidMaterial XenoauricFluoroantimonicAcid = new SimpleFluidMaterial("xenoauric_fluoroantimonic_acid", 0x685b08);

    public static final SimpleFluidMaterial NaquadahSulfate = new SimpleFluidMaterial("naquadah_sulfate", 0x38330f);

    public static final SimpleFluidMaterial NaquadahSolution = new SimpleFluidMaterial("naquadah_solution", 0x523b3a);
    public static final SimpleFluidMaterial ClearNaquadahLiquid = new SimpleFluidMaterial("clear_naquadah_liquid", 0xa89f9e);
    public static final SimpleFluidMaterial ComplicatedNaquadahGas = new SimpleFluidMaterial("complicated_naquadah_gas", 0x403d3d);
    public static final SimpleFluidMaterial ComplicatedHeavyNaquadah = new SimpleFluidMaterial("complicated_heavy_naquadah", 0x403d3d);
    public static final SimpleFluidMaterial ComplicatedMediumNaquadah = new SimpleFluidMaterial("complicated_medium_naquadah", 0x403d3d);
    public static final SimpleFluidMaterial ComplicatedLightNaquadah = new SimpleFluidMaterial("complicated_light_naquadah", 0x403d3d);
    public static final SimpleFluidMaterial NaquadahGas = new SimpleFluidMaterial("naquadah_gas", 0x575757);
    public static final SimpleFluidMaterial LightNaquadah = new SimpleFluidMaterial("light_naquadah", 0x2e2e2e);
    public static final SimpleFluidMaterial MediumNaquadah = new SimpleFluidMaterial("medium_naquadah", 0x2e2e2e);
    public static final SimpleFluidMaterial HeavyNaquadah = new SimpleFluidMaterial("heavy_naquadah", 0x2e2e2e);
    public static final SimpleFluidMaterial FlCrackedLightNaquadah = new SimpleFluidMaterial("fl_cracked_light_naquadah", 0x505e5b);
    public static final SimpleFluidMaterial FlCrackedMediumNaquadah = new SimpleFluidMaterial("fl_cracked_medium_naquadah", 0x505e5b);
    public static final SimpleFluidMaterial FlCrackedHeavyNaquadah = new SimpleFluidMaterial("fl_cracked_heavy_naquadah", 0x505e5b);
    public static final SimpleFluidMaterial LightNaquadahFuel = new SimpleFluidMaterial("light_naquadah_fuel", 0x2e2e2e);
    public static final SimpleFluidMaterial MediumNaquadahFuel = new SimpleFluidMaterial("medium_naquadah_fuel", 0x2e2e2e);
    public static final SimpleFluidMaterial HeavyNaquadahFuel = new SimpleFluidMaterial("heavy_naquadah_fuel", 0x2e2e2e);

    public static final SimpleFluidMaterial AmmoniaNitrate = new SimpleFluidMaterial("ammonia_nitrate", Ammonia.materialRGB);

    public static final SimpleFluidMaterial ENaquadahSolution = new SimpleFluidMaterial("e_naquadah_solution", 0x523b3a);
    public static final SimpleFluidMaterial ClearENaquadahLiquid = new SimpleFluidMaterial("clear_e_naquadah_liquid", 0xa89f9e);
    public static final SimpleFluidMaterial ComplicatedHeavyENaquadah = new SimpleFluidMaterial("complicated_heavy_e_naquadah", 0x403d3d);
    public static final SimpleFluidMaterial ComplicatedMediumENaquadah = new SimpleFluidMaterial("complicated_medium_e_naquadah", 0x403d3d);
    public static final SimpleFluidMaterial ComplicatedLightENaquadah = new SimpleFluidMaterial("complicated_light_e_naquadah", 0x403d3d);
    public static final SimpleFluidMaterial LightENaquadah = new SimpleFluidMaterial("light_e_naquadah", 0x2e2e2e);
    public static final SimpleFluidMaterial MediumENaquadah = new SimpleFluidMaterial("medium_e_naquadah", 0x2e2e2e);
    public static final SimpleFluidMaterial HeavyENaquadah = new SimpleFluidMaterial("heavy_e_naquadah", 0x2e2e2e);
    public static final SimpleFluidMaterial RnCrackedLighteNaquadah = new SimpleFluidMaterial("rn_cracked_light_e_naquadah", 0x505e5b);
    public static final SimpleFluidMaterial RnCrackedMediumENaquadah = new SimpleFluidMaterial("rn_cracked_medium_e_naquadah", 0x505e5b);
    public static final SimpleFluidMaterial RnCrackedHeavyENaquadah = new SimpleFluidMaterial("rn_cracked_heavy_e_naquadah", 0x505e5b);
    public static final SimpleFluidMaterial LightENaquadahFuel = new SimpleFluidMaterial("light_e_naquadah_fuel", 0x2e2e2e);
    public static final SimpleFluidMaterial MediumENaquadahFuel = new SimpleFluidMaterial("medium_e_naquadah_fuel", 0x2e2e2e);
    public static final SimpleFluidMaterial HeavyENaquadahFuel = new SimpleFluidMaterial("heavy_e_naquadah_fuel", 0x2e2e2e);

    public static final SimpleFluidMaterial NaquadriaSolution = new SimpleFluidMaterial("naquadria_solution", 0x523b3a);
    public static final SimpleFluidMaterial HyperFuelI = new SimpleFluidMaterial("hyper_fluid_i", 0xfaff5e);
    public static final SimpleFluidMaterial HyperFuelII = new SimpleFluidMaterial("hyper_fluid_ii", 0xd8db67);
    public static final SimpleFluidMaterial HyperFuelIII = new SimpleFluidMaterial("hyper_fluid_iii", 0x8f9146);
    public static final SimpleFluidMaterial HyperFuelIV = new SimpleFluidMaterial("hyper_fluid_iv", 0x4d4e31);

    public static final SimpleFluidMaterial AcidicSaltWater = new SimpleFluidMaterial("acidic_salt_water", 0x006960);
    public static final SimpleFluidMaterial SulfuricBromineSolution = new SimpleFluidMaterial("sulfuric_bromine_solution", 0xff5100);
    public static final SimpleFluidMaterial HotVapourMixture = new SimpleFluidMaterial("hot_vapour_mixture", 0xff5100);
    public static final SimpleFluidMaterial DampBromine = new SimpleFluidMaterial("damp_bromine", 0xe17594);

    public static final SimpleFluidMaterial Butyraldehyde = new SimpleFluidMaterial("butyraldehyde", 0xe7cf6e);
    public static final SimpleFluidMaterial Ethylhexanol = new SimpleFluidMaterial("ethylhexanol", 0xfeea9a);
    public static final SimpleFluidMaterial DiethylhexylPhosphoricAcid = new SimpleFluidMaterial("di_ethylhexyl_phosphoric_acid", 0xffff99);
    public static final SimpleFluidMaterial TrisodiumPhosphate = new SimpleFluidMaterial("trisodium_phosphate", 0x70ffc8);
    public static final SimpleFluidMaterial RareEarthHydroxidesSolution = new SimpleFluidMaterial("rare_earth_hydroxides_solution", 0xcfb37d);
    public static final SimpleFluidMaterial RareEarthChloridesSolution = new SimpleFluidMaterial("rare_earth_chlorides_solution", 0x164b45);
    public static final SimpleDustMaterial ThUSludge = new SimpleDustMaterial("thorium_uranium_sludge", 0x002908, (short) 7, MaterialIconSet.DULL);
    public static final SimpleFluidMaterial LaNdOxidesSolution = new SimpleFluidMaterial("la_nd_oxides_solution", 0x9ce3db);
    public static final SimpleFluidMaterial SmGdOxidesSolution = new SimpleFluidMaterial("sm_gd_oxides_solution", 0xffff99);
    public static final SimpleFluidMaterial TbHoOxidesSolution = new SimpleFluidMaterial("tb_ho_oxides_solution", 0x99ff99);
    public static final SimpleFluidMaterial ErLuOxidesSolution = new SimpleFluidMaterial("er_lu_oxides_solution", 0xffb3ff);
    public static final SimpleDustMaterial LanthanumOxide = new SimpleDustMaterial("lanthanum_oxide", Lanthanum.materialRGB, (short) 8, Lanthanum.materialIconSet, of(new MaterialStack(Lanthanum, 1), new MaterialStack(Oxygen, 1)));
    public static final SimpleDustMaterial PraseodymiumOxide = new SimpleDustMaterial("praseodymium_oxide", Praseodymium.materialRGB, (short) 9, Praseodymium.materialIconSet, of(new MaterialStack(Praseodymium, 1), new MaterialStack(Oxygen, 1)));
    public static final SimpleDustMaterial NeodymiumOxide = new SimpleDustMaterial("neodymium_oxide", Neodymium.materialRGB, (short) 10, Neodymium.materialIconSet, of(new MaterialStack(Neodymium, 1), new MaterialStack(Oxygen, 1)));
    public static final SimpleDustMaterial CeriumOxide = new SimpleDustMaterial("cerium_oxide", Cerium.materialRGB, (short) 11, Cerium.materialIconSet, of(new MaterialStack(Cerium, 1), new MaterialStack(Oxygen, 1)));
    public static final SimpleDustMaterial EuropiumOxide = new SimpleDustMaterial("europium_oxide", Europium.materialRGB, (short) 12, Europium.materialIconSet, of(new MaterialStack(Europium, 1), new MaterialStack(Oxygen, 1)));
    public static final SimpleDustMaterial GadoliniumOxide = new SimpleDustMaterial("gadolinium_oxide", Gadolinium.materialRGB, (short) 13, Gadolinium.materialIconSet, of(new MaterialStack(Gadolinium, 1), new MaterialStack(Oxygen, 1)));
    public static final SimpleDustMaterial SamariumOxide = new SimpleDustMaterial("samarium_oxide", Samarium.materialRGB, (short) 14, Samarium.materialIconSet, of(new MaterialStack(Samarium, 1), new MaterialStack(Oxygen, 1)));
    //public static final SimpleDustMaterial YttriumOxide = new SimpleDustMaterial("Yttrium_oxide",Yttrium.materialRGB, (short) 7, Yttrium.materialIconSet, of(new MaterialStack(Yttrium, 1), new MaterialStack(Oxygen, 1)));
    public static final SimpleDustMaterial TerbiumOxide = new SimpleDustMaterial("terbium_oxide", Terbium.materialRGB, (short) 15, Terbium.materialIconSet, of(new MaterialStack(Terbium, 1), new MaterialStack(Oxygen, 1)));
    public static final SimpleDustMaterial DysprosiumOxide = new SimpleDustMaterial("dysprosium_oxide", Dysprosium.materialRGB, (short) 16, Dysprosium.materialIconSet, of(new MaterialStack(Dysprosium, 1), new MaterialStack(Oxygen, 1)));
    public static final SimpleDustMaterial HolmiumOxide = new SimpleDustMaterial("holmium_oxide", Holmium.materialRGB, (short) 17, Holmium.materialIconSet, of(new MaterialStack(Holmium, 1), new MaterialStack(Oxygen, 1)));
    public static final SimpleDustMaterial ErbiumOxide = new SimpleDustMaterial("erbium_oxide", Erbium.materialRGB, (short) 18, Erbium.materialIconSet, of(new MaterialStack(Erbium, 1), new MaterialStack(Oxygen, 1)));
    public static final SimpleDustMaterial ThuliumOxide = new SimpleDustMaterial("thulium_oxide", Thulium.materialRGB, (short) 19, Thulium.materialIconSet, of(new MaterialStack(Thulium, 1), new MaterialStack(Oxygen, 1)));
    public static final SimpleDustMaterial YtterbiumOxide = new SimpleDustMaterial("ytterbium_oxide", Ytterbium.materialRGB, (short) 20, Ytterbium.materialIconSet, of(new MaterialStack(Ytterbium, 1), new MaterialStack(Oxygen, 1)));
    public static final SimpleDustMaterial LutetiumOxide = new SimpleDustMaterial("lutetium_oxide", Lutetium.materialRGB, (short) 21, Lutetium.materialIconSet, of(new MaterialStack(Lutetium, 1), new MaterialStack(Oxygen, 1)));
    public static final SimpleDustMaterial ScandiumOxide = new SimpleDustMaterial("scandium_oxide", Scandium.materialRGB, (short) 22, Scandium.materialIconSet, of(new MaterialStack(Scandium, 1), new MaterialStack(Oxygen, 1)));

    public static final SimpleFluidMaterial SupercooledCryotheum = new SimpleFluidMaterial("supercooled_cryotheum", Cryotheum.materialRGB);

    public static final SimpleDustMaterial CalciumCarbide = new SimpleDustMaterial("calcium_carbide", 0x807b70, (short) 23, MaterialIconSet.DULL, of(new MaterialStack(Calcium, 1), new MaterialStack(Carbon, 1)));
    public static final SimpleDustMaterial CalciumHydroxide = new SimpleDustMaterial("calcium_hydroxide", 0x5f8764, (short) 24, MaterialIconSet.DULL, of(new MaterialStack(Calcium, 1), new MaterialStack(Hydrogen, 1), new MaterialStack(Oxygen, 1)));
    public static final SimpleDustMaterial BetaPinene = new SimpleDustMaterial("beta_pinene", 0x61ad6b, (short) 25, MaterialIconSet.DULL);
    public static final SimpleDustMaterial Yeast = new SimpleDustMaterial("yeast", 0xf0e660, (short) 26, MaterialIconSet.DULL);
    public static final SimpleDustMaterial Glutamine = new SimpleDustMaterial("glutamine", 0xede9b4, (short) 27, MaterialIconSet.DULL);
    public static final SimpleDustMaterial SilicaGel = new SimpleDustMaterial("silica_gel", 0x61daff, (short) 28, MaterialIconSet.DULL, of(new MaterialStack(Silicon, 1), new MaterialStack(Oxygen, 2)));
    public static final SimpleDustMaterial SilicaAluminaGel = new SimpleDustMaterial("silica_alumina_gel", 0x558d9e, (short) 29, MaterialIconSet.DULL);
    public static final SimpleDustMaterial ZeoliteSievingPellets = new SimpleDustMaterial("zeolite_sieving_pellets", 0xa17bd1, (short) 30, MaterialIconSet.DULL);
    public static final SimpleDustMaterial WetZeoliteSievingPellets = new SimpleDustMaterial("wet_zeolite_sieving_pellets", 0x392f45, (short) 31, MaterialIconSet.DULL);
    public static final SimpleDustMaterial GreenAlgae = new SimpleDustMaterial("green_algae", 0x192b1b, (short) 32, MaterialIconSet.DULL);
    public static final SimpleDustMaterial BrownAlgae = new SimpleDustMaterial("brown_algae", 0x2b2519, (short) 33, MaterialIconSet.DULL);
    public static final SimpleDustMaterial RedAlgae = new SimpleDustMaterial("red_algae", 0x401313, (short) 34, MaterialIconSet.DULL);
    public static final SimpleDustMaterial DryRedAlgae = new SimpleDustMaterial("dry_red_algae", 0x8c1c1c, (short) 35, MaterialIconSet.DULL);
    public static final SimpleDustMaterial RedAlgaePowder = new SimpleDustMaterial("red_algae_powder", 0xcc2f2f, (short) 36, MaterialIconSet.DULL);
    public static final SimpleDustMaterial PreFreezeAgar = new SimpleDustMaterial("pre_freeze_agar", 0x132b0d, (short) 37, MaterialIconSet.DULL);
    public static final SimpleDustMaterial FrozenAgarCrystals = new SimpleDustMaterial("frozen_agar_crystals", 0x68db4b, (short) 38, MaterialIconSet.DULL);
    public static final SimpleDustMaterial Agar = new SimpleDustMaterial("agar", 0x38a31d, (short) 39, MaterialIconSet.DULL);
    public static final SimpleDustMaterial BrevibacteriumFlavium = new SimpleDustMaterial("brevibacterium_flavium", 0x2c4d24, (short) 40, MaterialIconSet.DULL);
    public static final SimpleDustMaterial StreptococcusPyogenes = new SimpleDustMaterial("streptococcus_pyogenes", 0x1c3b15, (short) 41, MaterialIconSet.DULL);
    public static final SimpleDustMaterial EschericiaColi = new SimpleDustMaterial("eschericia_coli", 0x2d4228, (short) 42, MaterialIconSet.DULL);
    public static final SimpleDustMaterial BifidobacteriumBreve = new SimpleDustMaterial("bifidobacterium_breve", 0x377528, (short) 43, MaterialIconSet.DULL);
    public static final SimpleDustMaterial Alumina = new SimpleDustMaterial("alumina", 0x0b585c, (short) 44, MaterialIconSet.DULL, of(new MaterialStack(Aluminium, 2), new MaterialStack(Oxygen, 6)));
    public static final SimpleDustMaterial CupriavidusNecator = new SimpleDustMaterial("cupriavidus_necator", 0x22704f, (short) 46, MaterialIconSet.DULL);
    public static final SimpleDustMaterial Shewanella = new SimpleDustMaterial("shewanella", 0x8752ab, (short) 47, MaterialIconSet.DULL);
    public static final SimpleDustMaterial ZirconiumTetrachloride = new SimpleDustMaterial("zirconium_tetrachloride", 0xF0F0F0, (short) 54, MaterialIconSet.SHINY, of(new MaterialStack(Zirconium, 1), new MaterialStack(Chlorine, 4)));
    public static final SimpleDustMaterial SiliconCarbide = new SimpleDustMaterial("silicon_carbide", 0x0f0f0f, (short) 55, MaterialIconSet.SHINY, of(new MaterialStack(Silicon, 1), new MaterialStack(Carbon, 1)));


    //

    public static final SimpleFluidMaterial Turpentine = new SimpleFluidMaterial("turpentine", 0x93bd46);
    public static final SimpleFluidMaterial Acetylene = new SimpleFluidMaterial("acetylene", 0x959c60);
    public static final SimpleFluidMaterial Formaldehyde = new SimpleFluidMaterial("formaldehyde", 0x95a13a);
    public static final SimpleFluidMaterial PropargylAlcohol = new SimpleFluidMaterial("propargyl_alcohol", 0xbfb32a);
    public static final SimpleFluidMaterial PropargylChloride = new SimpleFluidMaterial("propargyl_chloride", 0x918924);
    public static final SimpleFluidMaterial Citral = new SimpleFluidMaterial("citral", 0xf2e541);
    public static final SimpleFluidMaterial BetaIonone = new SimpleFluidMaterial("beta_ionone", 0xdc5ce6);
    public static final SimpleFluidMaterial VitaminA = new SimpleFluidMaterial("vitamin_a", 0x8d5c91);
    public static final SimpleFluidMaterial EthyleneOxide = new SimpleFluidMaterial("ethylene_oxide", 0xa0c3de);
    public static final SimpleFluidMaterial Ethanolamine = new SimpleFluidMaterial("ethanolamine", 0x6f7d87);
    public static final SimpleFluidMaterial Biotin = new SimpleFluidMaterial("biotin", 0x68cc6a);
    public static final SimpleFluidMaterial B27Supplement = new SimpleFluidMaterial("b27_supplement", 0x386939);
    public static final SimpleFluidMaterial CleanAmmoniaSolution = new SimpleFluidMaterial("clear_ammonia_solution", 0x53c9a0);
    public static final SimpleFluidMaterial Catalase = new SimpleFluidMaterial("catalase", 0xdb6596);
    public static final SimpleFluidMaterial Blood = new SimpleFluidMaterial("blood", 0x5c0606);
    public static final SimpleFluidMaterial BloodCells = new SimpleFluidMaterial("blood_cells", 0xad2424);
    public static final SimpleFluidMaterial BloodPlasma = new SimpleFluidMaterial("blood_plasma", 0xe37171);
    public static final SimpleFluidMaterial BFGF = new SimpleFluidMaterial("bfgf", 0xb365e0);
    public static final SimpleFluidMaterial EGF = new SimpleFluidMaterial("egf", 0x815799);
    public static final SimpleFluidMaterial NitroBenzene = new SimpleFluidMaterial("nitro_benzene", 0x81c951);
    public static final SimpleFluidMaterial Aniline = new SimpleFluidMaterial("aniline", 0x4c911d);
    public static final SimpleFluidMaterial ChlorosulfuricAcid = new SimpleFluidMaterial("chlorosulfuric_acid", 0x916c1d);
    public static final SimpleFluidMaterial AcidicMixture = new SimpleFluidMaterial("acidic_mixture", 0xedb53e);
    public static final SimpleFluidMaterial BenzenesulfonylChloride = new SimpleFluidMaterial("benzenesulfonyl_chloride", 0xa67a1c);
    public static final SimpleFluidMaterial Sulfanilamide = new SimpleFluidMaterial("sulfanilamide", 0x523b0a);
    public static final SimpleFluidMaterial SilicaGelBase = new SimpleFluidMaterial("silica_gel_base", 0x27a176);
    public static final SimpleFluidMaterial Ethanol100 = new SimpleFluidMaterial("ethanol_100", Ethanol.materialRGB);
    public static final SimpleFluidMaterial PiranhaSolution = new SimpleFluidMaterial("piranha_solution", 0x4820ab);
    public static final SimpleFluidMaterial WaterAgarMix = new SimpleFluidMaterial("water_agar_mix", 0x48dbbe);
    public static final SimpleFluidMaterial BacterialGrowthMedium = new SimpleFluidMaterial("bacterial_growth_medium", 0x0b2e12);
    public static final SimpleFluidMaterial DepletedGrowthMedium = new SimpleFluidMaterial("depleted_growth_medium", 0x071209);
    public static final SimpleFluidMaterial AnimalCells = new SimpleFluidMaterial("animal_cells", 0xc94996);
    public static final SimpleFluidMaterial RapidlyReplicatingAnimalCells = new SimpleFluidMaterial("rapidly_replicating_animal_cells", 0x7a335e);
    public static final SimpleFluidMaterial MycGene = new SimpleFluidMaterial("myc_gene", 0x445724);
    public static final SimpleFluidMaterial Oct4Gene = new SimpleFluidMaterial("oct_4_gene", 0x374f0d);
    public static final SimpleFluidMaterial SOX2Gene = new SimpleFluidMaterial("sox_2_gene", 0x5d8714);
    public static final SimpleFluidMaterial KFL4Gene = new SimpleFluidMaterial("kfl_4_gene", 0x759143);
    public static final SimpleFluidMaterial Cas9 = new SimpleFluidMaterial("cas_9", 0x5f6e46);
    public static final SimpleFluidMaterial GenePlasmids = new SimpleFluidMaterial("pluripotency_induction_gene_plasmids", 0xabe053);
    public static final SimpleFluidMaterial Chitin = new SimpleFluidMaterial("chitin", 0xcbd479);
    public static final SimpleFluidMaterial Chitosan = new SimpleFluidMaterial("chitosan", 0xb1bd42);
    public static final SimpleFluidMaterial GeneTherapyFluid = new SimpleFluidMaterial("pluripotency_induction_gene_therapy_fluid", 0x6b2f66);
    public static final SimpleFluidMaterial Resin = new SimpleFluidMaterial("resin", 0x3d2f11);
    public static final SimpleFluidMaterial LinoleicAcid = new SimpleFluidMaterial("linoleic_acid", 0xD5D257);
    public static final SimpleFluidMaterial SiliconFluoride = new SimpleFluidMaterial("silicon_fluoride", 0xB2B4B4);
    public static final SimpleFluidMaterial CarbonFluoride = new SimpleFluidMaterial("carbone_fluoride", 0xE6E6E6);
    public static final SimpleFluidMaterial PhosphorusTrichloride = new SimpleFluidMaterial("phosphorus_trichloride", 0xE6E6E6);
    public static final SimpleFluidMaterial PhosphorylChloride = new SimpleFluidMaterial("phosphoryl_chloride", 0xE6E6E6);
    public static final SimpleFluidMaterial TributylPhosphate = new SimpleFluidMaterial("tributyl_phosphate", 0x7C5B2C);
    public static final SimpleFluidMaterial Butanol = new SimpleFluidMaterial("butanol", 0x755f30);
    public static final SimpleFluidMaterial RedOil = new SimpleFluidMaterial("red_oil", 0x7C1500);

    public static final SimpleFluidMaterial HydrogenCyanide = new SimpleFluidMaterial("hydrogen_cyanide", 0xb6d1ae);
    public static final SimpleFluidMaterial SodiumCyanide = new SimpleFluidMaterial("sodium_cyanide", 0x5f7c8c);
    public static final SimpleFluidMaterial GoldCyanide = new SimpleFluidMaterial("gold_cyanide", 0x8c8761);
    public static final SimpleFluidMaterial ChlorideLeachedSolution = new SimpleFluidMaterial("chloride_leached_solution", 0x41472e);
    public static final SimpleFluidMaterial MolybdenumFlue = new SimpleFluidMaterial("molybdenum_flue_gas", 0x333338);
    public static final SimpleFluidMaterial RheniumSulfuricSolution = new SimpleFluidMaterial("rhenium_sulfuric_solution", 0xbabaff);
    public static final SimpleFluidMaterial AmmoniumSulfate = new SimpleFluidMaterial("ammonium_sulfate", 0x6464f5);
    public static final SimpleFluidMaterial AmmoniumPerrhenate = new SimpleFluidMaterial("ammonium_perrhenate", 0x1c1c45);
    public static final SimpleDustMaterial GoldDepleteMolybdenite = new SimpleDustMaterial("gold_deplete_molybdenite", 0x7c7c8f, (short) 48, MaterialIconSet.SHINY);
    public static final SimpleDustMaterial MolybdenumConcentrate = new SimpleDustMaterial("molybdenum_concentrate", 0x565666, (short) 49, MaterialIconSet.SHINY);
    public static final SimpleDustMaterial MolybdenumTrioxide = new SimpleDustMaterial("molybdenum_trioxide", 0x666685, (short) 50, MaterialIconSet.SHINY, of(new MaterialStack(Molybdenum, 1), new MaterialStack(Oxygen, 3)));
    public static final SimpleDustMaterial CopperChloride = new SimpleDustMaterial("copper_chloride", 0xf5b35d, (short) 51, MaterialIconSet.SHINY, of(new MaterialStack(Copper, 1), new MaterialStack(Chlorine, 1)));
    public static final SimpleDustMaterial BismuthChloride = new SimpleDustMaterial("bismuth_chloride", 0x95f5d7, (short) 52, MaterialIconSet.SHINY, of(new MaterialStack(Bismuth, 1), new MaterialStack(Chlorine, 1)));
    public static final SimpleDustMaterial LeadChloride = new SimpleDustMaterial("lead_chloride", 0xbf95f5, (short) 53, MaterialIconSet.SHINY, of(new MaterialStack(Lead, 1), new MaterialStack(Chlorine, 1)));

    public static final SimpleFluidMaterial ElectronDegenerateRheniumPlasma = new SimpleFluidMaterial("degenerate_rhenium_plasma", 0x6666FF);
    public static final SimpleFluidMaterial LiquidHelium = new SimpleFluidMaterial("liquid_helium", Helium.materialRGB);

    public static final SimpleDustMaterial ZirconiumTetrafluoride = new SimpleDustMaterial("zirconium_tetrafluoride", 0xeeeeee, (short) 56, MaterialIconSet.DULL, of(new MaterialStack(Zirconium, 1), new MaterialStack(Fluorine, 6)));
    public static final SimpleDustMaterial BariumDifluoride = new SimpleDustMaterial("barium_difluoride", 0xdddddd, (short) 57, MaterialIconSet.DULL, of(new MaterialStack(Barium, 1), new MaterialStack(Fluorine, 2)));
    public static final SimpleDustMaterial LanthanumTrifluoride = new SimpleDustMaterial("lanthanum_trifluoride", 0xeeeeee, (short) 58, MaterialIconSet.DULL, of(new MaterialStack(Lanthanum, 1), new MaterialStack(Fluorine, 3)));
    public static final SimpleDustMaterial AluminiumTrifluoride = new SimpleDustMaterial("aluminium_trifluoride", 0xeeeeee, (short) 59, MaterialIconSet.DULL, of(new MaterialStack(Aluminium, 1), new MaterialStack(Fluorine, 3)));
    public static final SimpleDustMaterial ErbiumTrifluoride = new SimpleDustMaterial("erbium_trifluoride", 0xF3E1E5, (short) 60, MaterialIconSet.DULL, of(new MaterialStack(Erbium, 1), new MaterialStack(Fluorine, 3)));
    public static final SimpleDustMaterial ZBLANDust = new SimpleDustMaterial("zblan_dust", 0xFFFFFF, (short) 61, MaterialIconSet.DULL);
    public static final SimpleDustMaterial ErbiumDopedZBLANDust = new SimpleDustMaterial("erbium_doped_zblan_dust", 0xFFF0F0, (short) 62, MaterialIconSet.DULL);

    public static final SimpleFluidMaterial BoricAcid = new SimpleFluidMaterial("boric_acid", 0xD5D2D7);
    public static final SimpleFluidMaterial FluoroBoricAcid = new SimpleFluidMaterial("fluoroboric_acid", 0xD5D2D7);
    public static final SimpleFluidMaterial BenzenediazoniumTetrafluoroborate = new SimpleFluidMaterial("benzenediazonium_tetrafluoroborate", 0xD5D2D7);
    public static final SimpleFluidMaterial BoronFluoride = new SimpleFluidMaterial("boron_fluoride", 0xD5D2D7);
    public static final SimpleFluidMaterial FluoroBenzene = new SimpleFluidMaterial("fluoro_benzene", 0xD5D2D7);
    public static final SimpleFluidMaterial SodiumNitrateSolution = new SimpleFluidMaterial("sodium_nitrate_solution", 0xA09ED7);
    public static final SimpleFluidMaterial Fluorotoluene = new SimpleFluidMaterial("fluorotoluene", 0xE0DA99);
    public static final SimpleFluidMaterial Xylene = new SimpleFluidMaterial("xylene", 0xB9575E);
    public static final SimpleFluidMaterial XyleneZeoliteMixture = new SimpleFluidMaterial("xylene_zeolite", 0xB9785E);
    public static final SimpleFluidMaterial PXylene = new SimpleFluidMaterial("p_xylene", 0xB9575E);
    public static final SimpleFluidMaterial Dibromomethylbenzene = new SimpleFluidMaterial("dibromomethylbenzene", 0x0A1D2C);
    public static final SimpleFluidMaterial AceticAnhydride = new SimpleFluidMaterial("acetic_anhydride", 0xD5DDDF);
    public static final SimpleFluidMaterial Isochloropropane = new SimpleFluidMaterial("isochloropropane", 0xD5DD95);
    public static final SimpleFluidMaterial Resorcinol = new SimpleFluidMaterial("resorcinol", 0xD5DDBE);
    public static final SimpleFluidMaterial Dinitrodipropanyloxybenzene = new SimpleFluidMaterial("dinitrodipropanyloxybenzene", 0x83945F);
    public static final SimpleFluidMaterial Naphthaldehyde = new SimpleFluidMaterial("napthaldehyde", 0xBCA853);
    public static final SimpleFluidMaterial HydrobromicAcid = new SimpleFluidMaterial("hydrobromic_acid", 0xBC6C53);
    public static final SimpleFluidMaterial PhosphorusChloride = new SimpleFluidMaterial("phosphorus_chloride", 0x7C9C53);
    public static final SimpleFluidMaterial ThionylChloride = new SimpleFluidMaterial("thionyl_chloride", 0xF9F7E5);
    public static final SimpleFluidMaterial Diisopropylcarbodiimide = new SimpleFluidMaterial("diisopropylcarbodiimide", 0xA0CFFE);
    public static final SimpleFluidMaterial Pyridine = new SimpleFluidMaterial("pyridine", 0x755f30);
    public static final SimpleFluidMaterial Phenylpentanoicacid = new SimpleFluidMaterial("phenylpentanoicacid", 0x755f30);
    public static final SimpleFluidMaterial Dimethylsulfide = new SimpleFluidMaterial("dimethylsulfide", 0x755f30);
    public static final SimpleFluidMaterial BenzoylChloride = new SimpleFluidMaterial("benzoyl_chloride", 0x755f30);
    public static final SimpleFluidMaterial Silvertetrafluoroborate = new SimpleFluidMaterial("silvertetrafluoroborate", 0x755f30);
    public static final SimpleFluidMaterial PCBA = new SimpleFluidMaterial("pcba", 0x755f30);
    public static final SimpleFluidMaterial PCBS = new SimpleFluidMaterial("pcbs", 0x755f30);
    public static final SimpleFluidMaterial Ferrocene = new SimpleFluidMaterial("ferrocene", 0x755f30);
    public static final SimpleFluidMaterial Ferrocenylfulleropyrrolidine = new SimpleFluidMaterial("ferrocenylfulleropyrddolidine", 0x755f30);
    public static final SimpleFluidMaterial CoCABCatalyst = new SimpleFluidMaterial("cocab_catalyst", 0x755f30);
    public static final SimpleFluidMaterial Hydroquinone = new SimpleFluidMaterial("hydroquinone", 0x755f30);
    public static final SimpleFluidMaterial Propylene = new SimpleFluidMaterial("propylene", 0x755f30);
    public static final SimpleFluidMaterial SodiumAcetate = new SimpleFluidMaterial("sodium_acetate", 0x755f30);
    public static final SimpleFluidMaterial PotassiumHydroxide = new SimpleFluidMaterial("potassium_hydroxide", 0x755f30);
    public static final SimpleFluidMaterial Methylamine = new SimpleFluidMaterial("methylamine", 0x755f30);
    public static final SimpleFluidMaterial Phosgene = new SimpleFluidMaterial("phosgene", 0x755f30);
    public static final SimpleFluidMaterial IsopropylAlcohol = new SimpleFluidMaterial("isopropyl_alcohol", 0x755f30);


    public static final SimpleDustMaterial PotassiumCyanide = new SimpleDustMaterial("potassium_cyanide", 0xbf95f5, (short) 63, MaterialIconSet.DULL);
    public static final SimpleDustMaterial SuccinicAcid = new SimpleDustMaterial("succinic_acid", 0xbf95f5, (short) 64, MaterialIconSet.DULL);
    public static final SimpleDustMaterial Succinimide = new SimpleDustMaterial("succinimide", 0xbf95f5, (short) 65, MaterialIconSet.DULL);
    public static final SimpleDustMaterial Bromosuccinimide = new SimpleDustMaterial("bromo_succinimide", 0xbf95f5, (short) 66, MaterialIconSet.DULL);
    public static final SimpleDustMaterial Benzophenanthrenylacetonitrile = new SimpleDustMaterial("benzophenanthrenylacetonitrile", 0xbf95f5, (short) 67, MaterialIconSet.DULL);
    public static final SimpleDustMaterial UnfoldedFullerene = new SimpleDustMaterial("unfolded_fullerene", 0xbf95f5, (short) 68, MaterialIconSet.DULL);
    public static final SimpleDustMaterial Fullerene = new SimpleDustMaterial("fullerene", 0xbf95f5, (short) 69, MaterialIconSet.DULL);
    public static final SimpleDustMaterial TiAlChloride = new SimpleDustMaterial("tial_chloride", 0xbf95f5, (short) 70, MaterialIconSet.DULL);
    public static final SimpleDustMaterial Dimethylaminopyridine = new SimpleDustMaterial("dimethylaminopyridine", 0xbf95f5, (short) 71, MaterialIconSet.DULL);
    public static final SimpleDustMaterial PdIrReOCeOS = new SimpleDustMaterial("pdirreoceos", 0xbf95f5, (short) 72, MaterialIconSet.DULL);
    public static final SimpleDustMaterial SodiumEthoxide = new SimpleDustMaterial("sodium_ethoxide", 0xbf95f5, (short) 73, MaterialIconSet.DULL);
    public static final SimpleDustMaterial MgClBrominide = new SimpleDustMaterial("mgcl_bromide", 0xbf95f5, (short) 74, MaterialIconSet.DULL);
    public static final SimpleDustMaterial NMethylglicine = new SimpleDustMaterial("n_methylglicine", 0xbf95f5, (short) 75, MaterialIconSet.DULL);
    public static final SimpleDustMaterial SodiumNitrite = new SimpleDustMaterial("sodium_nitrite", 0xbf95f5, (short) 76, MaterialIconSet.DULL);
    public static final SimpleDustMaterial ZnFeAlClCatalyst = new SimpleDustMaterial("znfealcl_catalyst", 0xbf95f5, (short) 77, MaterialIconSet.SHINY);
    public static final SimpleDustMaterial Difluorobenzophenone = new SimpleDustMaterial("difluorobenzophenone", 0xbf95f5, (short) 78, MaterialIconSet.SHINY);
    public static final SimpleDustMaterial AluminiumChloride = new SimpleDustMaterial("aluminium_chloride", 0xbf95f5, (short) 79, MaterialIconSet.SHINY);
    public static final SimpleDustMaterial PdFullereneMatrix = new SimpleDustMaterial("pd_fullerene_matrix", 0xbf95f5, (short) 80, MaterialIconSet.SHINY);

    public static final SimpleDustMaterial Terephthalaldehyde = new SimpleDustMaterial("terephthalaldehyde", 0xbf95f5, (short) 81, MaterialIconSet.DULL);
    public static final SimpleDustMaterial PreZylon = new SimpleDustMaterial("pre_zylon", 0xbf95f5, (short) 82, MaterialIconSet.DULL);
    public static final SimpleDustMaterial AuPdCCatalyst = new SimpleDustMaterial("aupdc_catalyst", 0xbf95f5, (short) 83, MaterialIconSet.DULL);
    public static final SimpleDustMaterial Cyanonaphtalene = new SimpleDustMaterial("cyanonaphtalene", 0xbf95f5, (short) 84, MaterialIconSet.DULL);
    public static final SimpleDustMaterial TinChloride = new SimpleDustMaterial("tin_chloride", 0xbf95f5, (short) 85, MaterialIconSet.DULL);
    public static final SimpleDustMaterial Triphenylphosphine = new SimpleDustMaterial("triphenylphosphine", 0xbf95f5, (short) 86, MaterialIconSet.DULL);
    public static final SimpleDustMaterial Methylbenzophenanthrene = new SimpleDustMaterial("methylbenzophenanthrene", 0xbf95f5, (short) 87, MaterialIconSet.DULL);

    public static final SimpleDustMaterial VanadiumSlag = new SimpleDustMaterial("vanadium_slag", 0xbf95f5, (short) 88, MaterialIconSet.DULL);
    public static final SimpleDustMaterial VanadiumSlagDust = new SimpleDustMaterial("vanadium_slag_dust", 0xf2ef1b, (short) 89, MaterialIconSet.DULL);
    public static final SimpleDustMaterial SodiumVanadate = new SimpleDustMaterial("sodium_vanadate", 0xf2ef1b, (short) 90, MaterialIconSet.DULL);
    public static final SimpleDustMaterial AmmoniumVanadate = new SimpleDustMaterial("ammonium_vanadate", 0xf2ef1b, (short) 91, MaterialIconSet.DULL);
    public static final SimpleDustMaterial VanadiumOxide = new SimpleDustMaterial("vanadium_oxide", 0xbf95f5, (short) 92, MaterialIconSet.SHINY);
    public static final SimpleFluidMaterial VanadiumWasteSolution = new SimpleFluidMaterial("vanadium_waste_solution", 0xbf95f5);

    public static final SimpleDustMaterial BariumSulfide = new SimpleDustMaterial("barium_sulfide", 0xc2c2be, (short) 93, MaterialIconSet.DULL);
    public static final SimpleDustMaterial BariumCarbonate = new SimpleDustMaterial("barium_carbonate", 0xfcfcfa, (short) 94, MaterialIconSet.DULL);
    public static final SimpleDustMaterial BariumOxide = new SimpleDustMaterial("barium_oxide", 0xfcfcfa, (short) 95, MaterialIconSet.DULL);
    public static final SimpleDustMaterial BariumAluminate = new SimpleDustMaterial("barium_aluminate", 0xfcfcfa, (short) 96, MaterialIconSet.DULL);

    public static final SimpleDustMaterial PitchblendeBaCOmix = new SimpleDustMaterial("pitchblende_barium_mixture", 0xb8c319, (short) 97, MaterialIconSet.DULL);
    public static final SimpleFluidMaterial UranylChlorideSolution = new SimpleFluidMaterial("uranyl_chloride_solution", 0xdfe018);
    public static final SimpleFluidMaterial UranylNitrateSolution = new SimpleFluidMaterial("uranyl_nitrate_solution", 0xdfe018);
    public static final SimpleFluidMaterial UraniumSulfateWasteSolution = new SimpleFluidMaterial("uranium_sulfate_waste_solution", 0xdfe018);
    public static final SimpleFluidMaterial PurifiedUranylNitrate = new SimpleFluidMaterial("purified_uranyl_nitrate_solution", 0xeff028);
    public static final SimpleFluidMaterial UraniumDiuranate = new SimpleFluidMaterial("uranium_diuranate", 0xeff028);
    public static final SimpleFluidMaterial HotUraniumDiuranate = new SimpleFluidMaterial("hot_uranium_diuranate", 0xeff028);
    public static final SimpleFluidMaterial HotPotassiumUranylTricarbonate = new SimpleFluidMaterial("hot_potassium_uranyl_carbonate", 0xeff028);
    public static final SimpleDustMaterial PotassiumUranylTricarbonate = new SimpleDustMaterial("potassium_uranyl_carbonate", 0xeff028, (short) 98, MaterialIconSet.DULL);
    public static final SimpleFluidMaterial UraniumRefinementWasteSolution = new SimpleFluidMaterial("uranium_refinement_waste_solution", 0xeff028);
    public static final SimpleDustMaterial UraniumPeroxideThoriumOxide = new SimpleDustMaterial("uranium_peroxide_thorium_oxide", 0x202020, (short) 99, MaterialIconSet.SHINY);
    public static final SimpleDustMaterial UraniumThoriumOxide = new SimpleDustMaterial("uranium_thorium_oxide", 0x202020, (short) 100, MaterialIconSet.SHINY);
    public static final SimpleDustMaterial UranylThoriumSulfate = new SimpleDustMaterial("uranium_thorium_sulfate", 0xe7e848, (short) 101, MaterialIconSet.SHINY);
    public static final SimpleDustMaterial UranylThoriumNitrate = new SimpleDustMaterial("uranium_thorium_nitrate", 0xe7e848, (short) 102, MaterialIconSet.SHINY);
    public static final SimpleDustMaterial UraniumOxideThoriumNitrate = new SimpleDustMaterial("uranium_oxide_thorium_nitrate", 0x33bd45, (short) 103, MaterialIconSet.SHINY);
    public static final SimpleFluidMaterial ThoriumNitrateSolution = new SimpleFluidMaterial("thorium_nitrate_solution", 0x33bd45);

    public static final SimpleDustMaterial CesiumHydroxide = new SimpleDustMaterial("cesium_hydroxide", 0xfcfcfa, (short) 104, MaterialIconSet.DULL);
    public static final SimpleDustMaterial AluminiumHydroxide = new SimpleDustMaterial("aluminium_hydroxide", 0xfcfcfa, (short) 105, MaterialIconSet.DULL);
    public static final SimpleDustMaterial PotassiumCarbonate = new SimpleDustMaterial("potassium_carbonate", 0xfcfcfa, (short) 106, MaterialIconSet.DULL);

    public static final SimpleFluidMaterial SodiumHexafluoroaluminate = new SimpleFluidMaterial("sodium_hexafluoroaluminate", 0xfcfcfa);

    public static final SimpleDustMaterial ChromiumOxide = new SimpleDustMaterial("chromium_oxide", 0x61eb49, (short) 107, MaterialIconSet.DULL);
    public static final SimpleFluidMaterial SodiumCarbonateSolution = new SimpleFluidMaterial("sodium_carbonate_solution", 0xfcfcfa);
    public static final SimpleFluidMaterial SodiumSulfateSolution = new SimpleFluidMaterial("sodium_sulfate_solution", 0xfcfcfa);
    public static final SimpleFluidMaterial SodiumChromateSolution = new SimpleFluidMaterial("sodium_chromate_solution", 0xf2e70f);
    public static final SimpleFluidMaterial SodiumDichromateSolution = new SimpleFluidMaterial("sodium_dichromate_solution", 0xf2750f);

    public static final SimpleDustMaterial NiAlOCatalyst = new SimpleDustMaterial("nialo_catalyst", 0x0af0af, (short) 108, MaterialIconSet.DULL);
    public static final SimpleDustMaterial FeCrOCatalyst = new SimpleDustMaterial("fecro_catalyst", 0x8C4517, (short) 109, MaterialIconSet.DULL);
    public static final SimpleFluidMaterial PoorNitrogenMix = new SimpleFluidMaterial("poor_nitrogen_mix", 0xa0c3de);
    public static final SimpleFluidMaterial RichNitrogenMix = new SimpleFluidMaterial("rich_nitrogen_mix", 0x6891d8);
    public static final SimpleFluidMaterial OxidisedNitrogenMix = new SimpleFluidMaterial("oxidised_nitrogen_mix", 0x708ACD);
    public static final SimpleFluidMaterial PurifiedNitrogenMix = new SimpleFluidMaterial("purified_nitrogen_mix", 0x6891d8);
    public static final SimpleFluidMaterial HotPurifiedNitrogenMix = new SimpleFluidMaterial("hot_purified_nitrogen_mix", 0x6891d8);
    public static final SimpleFluidMaterial CarbonatedEthanolamine = new SimpleFluidMaterial("carbonated_ethanolamine", 0x6f7d87);
    public static final SimpleFluidMaterial AmmoniaRichMix = new SimpleFluidMaterial("ammonia_rich_mix", 0x2f5d99);

    public static final SimpleDustMaterial RoastedSpodumene = new SimpleDustMaterial("roasted_spodumene", 0x3d3d29, (short) 110, MaterialIconSet.DULL);
    public static final SimpleDustMaterial RoastedLepidolite = new SimpleDustMaterial("roasted_lepidolite", 0x470024, (short) 111, MaterialIconSet.DULL);
    public static final SimpleDustMaterial LithiumChloride = new SimpleDustMaterial("lithium_chloride", 0xfcfcfa, (short) 112, MaterialIconSet.DULL);
    public static final SimpleDustMaterial LiKChlorideEutetic = new SimpleDustMaterial("lik_chloride_eutetic", 0xcac0c1, (short) 113, MaterialIconSet.DULL);
    public static final SimpleDustMaterial CalciumFluoride = new SimpleDustMaterial("calcium_fluoride", 0x04c464, (short) 114, MaterialIconSet.DULL);
    public static final SimpleDustMaterial PotassiumSulfate = new SimpleDustMaterial("potassium_sulfate", 0xfcfcfa, (short) 115, MaterialIconSet.DULL);
    public static final SimpleDustMaterial AluminiumSulfate = new SimpleDustMaterial("aluminium_sulfate", 0xfcfcfa, (short) 116, MaterialIconSet.DULL);
    public static final SimpleFluidMaterial DissolvedLithiumOre = new SimpleFluidMaterial("dissolved_lithium_ores", 0x664850);
    public static final SimpleFluidMaterial LithiumCarbonateSolution = new SimpleFluidMaterial("lithium_carbonate_solution", 0xfcfcfa);
    public static final SimpleFluidMaterial LithiumChlorideSolution = new SimpleFluidMaterial("lithium_chloride_solution", 0xfcfcfa);
    public static final SimpleFluidMaterial MoltenLiKChlorideEutetic = new SimpleFluidMaterial("molten_lik_chloride_eutetic", 0xcac0c1);

    public static final SimpleFluidMaterial CalicheIodateBrine = new SimpleFluidMaterial("caliche_iodate_brine", 0xffe6660);
    public static final SimpleFluidMaterial IodideSolution = new SimpleFluidMaterial("iodide_solution", 0x08081c);
    public static final SimpleFluidMaterial CalicheNitrateSolution = new SimpleFluidMaterial("caliche_nitrate_solution", 0xffe6660);
    public static final SimpleFluidMaterial CalicheIodineBrine = new SimpleFluidMaterial("caliche_iodine_brine", 0xffe6660);
    public static final SimpleFluidMaterial KeroseneIodineSolution = new SimpleFluidMaterial("kerosene_iodine_solution", 0x08081c);
    public static final SimpleFluidMaterial IodizedBrine = new SimpleFluidMaterial("iodized_brine", 0x525242);
    public static final SimpleFluidMaterial IodineBrineMix = new SimpleFluidMaterial("iodine_brine_mix", 0x525242);
    public static final SimpleFluidMaterial IodineSlurry = new SimpleFluidMaterial("iodine_slurry", 0x08081c);
    public static final SimpleFluidMaterial Brine = new SimpleFluidMaterial("brine", 0xfcfc8a);

    public static final SimpleDustMaterial BariumHydroxide = new SimpleDustMaterial("barium_hydroxide", 0xfcfcfa, (short) 117, MaterialIconSet.DULL);
    public static final SimpleDustMaterial HafniumOxide = new SimpleDustMaterial("hafnium_oxide", 0x404040, (short) 118, MaterialIconSet.SHINY);
    public static final SimpleDustMaterial SiliconChloride = new SimpleDustMaterial("silicon_chloride", 0xfcfcfa, (short) 119, MaterialIconSet.DULL);
    public static final SimpleDustMaterial HafniumChloride = new SimpleDustMaterial("hafnium_chloride", 0x404040, (short) 120, MaterialIconSet.DULL);
    public static final SimpleFluidMaterial MesitylOxide = new SimpleFluidMaterial("mesityl_oxide", 0xfcfcfa);
    public static final SimpleFluidMaterial MethylIsobutylKetone = new SimpleFluidMaterial("methyl_isobutyl_ketone", 0xfcfcfa);
    public static final SimpleFluidMaterial ThiocyanicAcid = new SimpleFluidMaterial("thiocyanic_acid", 0xfcfc30);
    public static final SimpleFluidMaterial ZrHfSeparationMix = new SimpleFluidMaterial("zrhf_separation_mix", 0xfcfc95);
    public static final SimpleFluidMaterial ZrHfChloride = new SimpleFluidMaterial("zrhf_chloride", 0x51d351);
    public static final SimpleFluidMaterial ZrHfOxyChloride = new SimpleFluidMaterial("zrhf_oxychloride", 0x51d351);
    public static final SimpleFluidMaterial ZirconChlorinatingResidue = new SimpleFluidMaterial("zircon_chlorinating_residue", 0x51d351);

    public static final SimpleDustMaterial ZincCokePellets = new SimpleDustMaterial("zinc_coke_pellets", 0xfcfcfa, (short) 121, MaterialIconSet.DULL);
    public static final SimpleDustMaterial ZincResidualSlag = new SimpleDustMaterial("zinc_residual_slag", 0xfcfcfa, (short) 122, MaterialIconSet.DULL);
    public static final SimpleDustMaterial ZincFlueDust = new SimpleDustMaterial("zinc_flue_dust", 0xfcfca, (short) 123, MaterialIconSet.DULL);
    public static final SimpleDustMaterial ZincLeachingResidue = new SimpleDustMaterial("zinc_leaching_residue", 0xfcfcfa, (short) 124, MaterialIconSet.DULL);
    public static final SimpleDustMaterial FineZincSlagDust = new SimpleDustMaterial("fine_zinc_slag_dust", 0xfcfcfa, (short) 125, MaterialIconSet.FINE);
    public static final SimpleDustMaterial IndiumHydroxide = new SimpleDustMaterial("indium_hydroxide", 0xfcfcfa, (short) 126, MaterialIconSet.SHINY);
    public static final SimpleDustMaterial CadmiumZincDust = new SimpleDustMaterial("cadmium_zinc_dust", 0xfcfca, (short) 127, MaterialIconSet.DULL);
    public static final SimpleDustMaterial ThalliumResidue = new SimpleDustMaterial("thallium_residue", 0xfcfcfa, (short) 128, MaterialIconSet.DULL);
    public static final SimpleDustMaterial ThalliumChloride = new SimpleDustMaterial("thallium_chloride", 0xfcfcfa, (short) 129, MaterialIconSet.SHINY);
    public static final SimpleDustMaterial ZincChloride = new SimpleDustMaterial("zinc_chloride", 0xfcfcfa, (short) 130, MaterialIconSet.DULL);
    public static final SimpleDustMaterial SodiumSulfite = new SimpleDustMaterial("sodium_sulfite", 0xfcfcfa, (short) 131, MaterialIconSet.DULL);
    public static final SimpleDustMaterial Cellulose = new SimpleDustMaterial("cellulose", 0xfcfcfa, (short) 132, MaterialIconSet.DULL);
    public static final SimpleDustMaterial GermaniumOxide = new SimpleDustMaterial("germanium_oxide", 0xfcfcfa, (short) 133, MaterialIconSet.SHINY);
    public static final SimpleDustMaterial SodiumPhosphate = new SimpleDustMaterial("sodium_phosphate", 0xfcfcfa, (short) 134, MaterialIconSet.DULL);

    public static final SimpleFluidMaterial ZincExhaustMixture = new SimpleFluidMaterial("zinc_exhaust_mixture", 0xfcfcfa);
    public static final SimpleFluidMaterial ZincSlagSlurry = new SimpleFluidMaterial("zinc_slag_slurry", 0xfcfcfa);
    public static final SimpleFluidMaterial MetalRichSlagSlurry = new SimpleFluidMaterial("metal_slag_slurry", 0xfcfcfa);
    public static final SimpleFluidMaterial AcidicMetalSlurry = new SimpleFluidMaterial("acidic_metal_slurry", 0xfcfcfa);
    public static final SimpleFluidMaterial SeparatedMetalSlurry = new SimpleFluidMaterial("separated_metal_slurry", 0xfcfcfa);
    public static final SimpleFluidMaterial MetalHydroxideMix = new SimpleFluidMaterial("metal_hydroxide_mix", 0xfcfcfa);
    public static final SimpleFluidMaterial ZincPoorMix = new SimpleFluidMaterial("zinc_poor_mix", 0xfcfcfa);
    public static final SimpleFluidMaterial IronPoorMix = new SimpleFluidMaterial("iron_poor_mix", 0xfcfcfa);
    public static final SimpleFluidMaterial IndiumHydroxideConcentrate = new SimpleFluidMaterial("indium_hydroxide_concentrate", 0xfcfcfa);
    public static final SimpleFluidMaterial CadmiumThalliumLiquor = new SimpleFluidMaterial("cdtl_liquor", 0xfcfcfa);
    public static final SimpleFluidMaterial ZincAmalgam = new SimpleFluidMaterial("zinc_amalgam", 0xfcfcfa);
    public static final SimpleFluidMaterial CadmiumSulfateSolution = new SimpleFluidMaterial("cadmium_sulfate", 0xfcfcfa);
    public static final SimpleFluidMaterial ThalliumSulfateSolution = new SimpleFluidMaterial("thallium_sulfate", 0xfcfcfa);
    public static final SimpleFluidMaterial PolyphenolMix = new SimpleFluidMaterial("polyphenol_mix", 0xfcfcfa);
    public static final SimpleFluidMaterial AcidifiedPolyphenolMix = new SimpleFluidMaterial("acidified_polyphenol_mix", 0xfcfcfa);
    public static final SimpleFluidMaterial Diethylether = new SimpleFluidMaterial("diethylether", 0xfcfcfa);
    public static final SimpleFluidMaterial TannicAcid = new SimpleFluidMaterial("tannic_acid", 0xfcfcfa);
    public static final SimpleFluidMaterial GermanicAcidSolution = new SimpleFluidMaterial("germanic_acid_solution", 0xfcfcfa);
    public static final SimpleFluidMaterial GermaniumChloride = new SimpleFluidMaterial("germanium_chloride", 0xfcfcfa);
    public static final SimpleFluidMaterial SodiumHydroxideSolution = new SimpleFluidMaterial("sodium_hydroxide_solution", 0xfcfcfa);
    public static final SimpleFluidMaterial IronSulfate = new SimpleFluidMaterial("iron_sulfate", 0xfcfcfa);

    public static final SimpleFluidMaterial LithiumHydroxideSolution = new SimpleFluidMaterial("lithium_hydroxide_solution", 0xfcfcfa);
    public static final SimpleFluidMaterial LithiumPeroxideSolution = new SimpleFluidMaterial("lithium_peroxide", 0xfcfcfa);
    public static final SimpleFluidMaterial LithiumCarbonatePureSolution = new SimpleFluidMaterial("lithium_carbonate_pure_solution", 0xfcfcfa);
    public static final SimpleFluidMaterial Ozone = new SimpleFluidMaterial("ozone", 0x0099FF);
    public static final SimpleFluidMaterial NitrogenPentoxide = new SimpleFluidMaterial("nitrogen_pentoxide", 0x0033C0);


    public static final SimpleDustMaterial AcrylicFibers = new SimpleDustMaterial("acrylic_fibers", 0xfcfcfa, (short) 135, MaterialIconSet.FINE);
    public static final SimpleDustMaterial UranylNitrate = new SimpleDustMaterial("uranyl_nitrate", 0x33bd45, (short) 136, MaterialIconSet.DULL);
    public static final SimpleDustMaterial CalciumSalts = new SimpleDustMaterial("calcium_salts", 0xcacac8, (short) 137, MaterialIconSet.DULL);
    public static final SimpleDustMaterial SodiumSalts = new SimpleDustMaterial("sodium_salts", 0xcacac8, (short) 138, MaterialIconSet.DULL);
    public static final SimpleDustMaterial PotassiumMagnesiumSalts = new SimpleDustMaterial("kmg_salts", 0xcacac8, (short) 139, MaterialIconSet.DULL);
    public static final SimpleDustMaterial CalciumMagnesiumSalts = new SimpleDustMaterial("camg_salts", 0xcacac8, (short) 140, MaterialIconSet.DULL);
    public static final SimpleDustMaterial SodiumAluminiumHydride = new SimpleDustMaterial("sodium_aluminium_hydride", 0x98cafc, (short) 141, MaterialIconSet.DULL);
    public static final SimpleDustMaterial LithiumAluminiumHydride = new SimpleDustMaterial("lithium_aluminium_hydride", 0xc0defc, (short) 142, MaterialIconSet.DULL);
    public static final SimpleDustMaterial SodiumAzanide = new SimpleDustMaterial("sodium_azanide", 0xfcfcfa, (short) 143, MaterialIconSet.DULL);
    public static final SimpleDustMaterial SodiumAzide = new SimpleDustMaterial("sodium_azide", 0xfcfcfa, (short) 144, MaterialIconSet.DULL);
    public static final SimpleDustMaterial Glucosamine = new SimpleDustMaterial("glucosamine", 0xfcfcfa, (short) 145, MaterialIconSet.DULL);
    public static final SimpleDustMaterial AluminiumHydride = new SimpleDustMaterial("aluminium_hydride", 0x0b585c, (short) 146, MaterialIconSet.DULL);
    public static final SimpleDustMaterial SodiumHydride = new SimpleDustMaterial("sodium_hydride", 0xcacac8, (short) 147, MaterialIconSet.DULL);
    public static final SimpleDustMaterial DehydrogenationCatalyst = new SimpleDustMaterial("dehydrogenation_catalyst", 0x6464f5, (short) 148, MaterialIconSet.SHINY);
    public static final SimpleDustMaterial PolystyreneNanoParticles = new SimpleDustMaterial("polystryrene_nanoparticles", 0x888079, (short) 149, MaterialIconSet.FINE);
    public static final SimpleDustMaterial MagnesiumSulfate = new SimpleDustMaterial("magnesium_sulfate", 0xcacac8, (short) 150, MaterialIconSet.DULL);
    public static final SimpleDustMaterial SodiumMolybdate = new SimpleDustMaterial("sodium_molybdate", 0xfcfc00, (short) 151, MaterialIconSet.DULL);
    public static final SimpleDustMaterial SodiumPhosphomolybdate = new SimpleDustMaterial("sodium_phosphomolybdate", 0xfcfc00, (short) 152, MaterialIconSet.SHINY);
    public static final SimpleDustMaterial SodiumPhosphotungstate = new SimpleDustMaterial("sodium_phosphotungstate", 0x7a7777, (short) 153, MaterialIconSet.SHINY);
    public static final SimpleDustMaterial PrecipitatedAmmoniumSulfate = new SimpleDustMaterial("solid_ammonium_sulfate", 0x6464f5, (short) 154, MaterialIconSet.DULL);
    public static final SimpleDustMaterial Fructose = new SimpleDustMaterial("fructose", 0xfcfcfa, (short) 165, MaterialIconSet.DULL);
    public static final SimpleDustMaterial Glucose = new SimpleDustMaterial("glucose", 0xfcfcfa, (short) 166, MaterialIconSet.DULL);
    public static final SimpleDustMaterial MagnesiumHydroxide = new SimpleDustMaterial("magnesium_hydroxide", 0xcacac8, (short) 167, MaterialIconSet.DULL);
    public static final SimpleDustMaterial StrontiumSulfate = new SimpleDustMaterial("strontium_sulfate", 0xcacac8, (short) 168, MaterialIconSet.DULL);
    public static final SimpleDustMaterial StrontiumOxide = new SimpleDustMaterial("strontium_oxide", 0xcacac8, (short) 169, MaterialIconSet.DULL);

    public static final SimpleFluidMaterial NitrousOxide = new SimpleFluidMaterial("nitrous_oxide", 0x2e628b);
    public static final SimpleFluidMaterial AcryloNitrile = new SimpleFluidMaterial("acrylonitrile", 0x9999ff);
    public static final SimpleFluidMaterial SodiumThiocyanate = new SimpleFluidMaterial("sodium_thiocyanate", 0xfcfcfa);
    public static final SimpleFluidMaterial PolyacrylonitrileSolution = new SimpleFluidMaterial("polyacrylonitrile_solution", 0x9999ff);
    public static final SimpleFluidMaterial MethylFormate = new SimpleFluidMaterial("methyl_formate", 0Xff9999);
    public static final SimpleFluidMaterial WetFormamide = new SimpleFluidMaterial("wet_formamide", 0x33CCFF);
    public static final SimpleFluidMaterial Formamide = new SimpleFluidMaterial("formamide", 0x33CCFF);
    public static final SimpleFluidMaterial HydroxilamineDisulfate = new SimpleFluidMaterial("hydroxilamine_disulfate", 0x99add6);
    public static final SimpleFluidMaterial Hydroxilamine = new SimpleFluidMaterial("hydroxilamine", 0x99cc99);
    public static final SimpleFluidMaterial DilutedAmmonia = new SimpleFluidMaterial("diluted_ammonia", 0x1c38d1);
    public static final SimpleFluidMaterial Amidoxime = new SimpleFluidMaterial("amidoxime", 0x66ff33);
    public static final SimpleFluidMaterial PureUranylNitrateSolution = new SimpleFluidMaterial("pure_uranyl_nitrate", 0x33bd45);
    public static final SimpleFluidMaterial AcetateSolution = new SimpleFluidMaterial("acetate_solution", 0xffc78f);
    public static final SimpleFluidMaterial CarbonSulfide = new SimpleFluidMaterial("carbon_sulfide", 0x40ffbf);
    public static final SimpleFluidMaterial AmineMixture = new SimpleFluidMaterial("amine_mixture", 0x755f30);
    public static final SimpleFluidMaterial DimethylthiocarbamoilChloride = new SimpleFluidMaterial("dimethylthiocarbamoil_chloride", 0xd9ff26);
    public static final SimpleFluidMaterial Trimethylamine = new SimpleFluidMaterial("trimetylamine", 0x755f30);
    public static final SimpleFluidMaterial Mercaphenol = new SimpleFluidMaterial("mercaphenol", 0xbaaf18);
    public static final SimpleFluidMaterial Dimethylformamide = new SimpleFluidMaterial("dimethylformamide", 0x42bdff);
    public static final SimpleFluidMaterial HydrogenCrackedDMF = new SimpleFluidMaterial("hydrogen_cracked_dmf", 0x42bdff);
    public static final SimpleFluidMaterial Oct1ene = new SimpleFluidMaterial("1_octene", 0xdadada);
    public static final SimpleFluidMaterial CetaneTrimethylAmmoniumBromide = new SimpleFluidMaterial("cetane_trimethyl_ammonium_bromide", 0xb9c1c9);
    public static final SimpleFluidMaterial AmmoniumPersulfate = new SimpleFluidMaterial("ammonium_persulfate", 0x6464f5);
    public static final SimpleFluidMaterial DebrominatedWater = new SimpleFluidMaterial("debrominated_brine", 0x0000ff);
    public static final SimpleFluidMaterial SeaWater = new SimpleFluidMaterial("sea_water", 0x0000FF);
    public static final SimpleFluidMaterial ConcentratedBrine = new SimpleFluidMaterial("concentrated_brine", 0xfcfc95);
    public static final SimpleFluidMaterial CalciumFreeBrine = new SimpleFluidMaterial("calcium_free_brine", 0xfcfca6);
    public static final SimpleFluidMaterial SodiumFreeBrine = new SimpleFluidMaterial("sodium_free_brine", 0xfcfcb1);
    public static final SimpleFluidMaterial PotassiumFreeBrine = new SimpleFluidMaterial("potassium_free_brine", 0xfcfcbc);
    public static final SimpleFluidMaterial BoronFreeSolution = new SimpleFluidMaterial("boron_free_solution", 0xfcfccd);
    public static final SimpleFluidMaterial SodiumLithiumSolution = new SimpleFluidMaterial("sodium_lithium_solution", 0xfcfccd);
    public static final SimpleFluidMaterial ChilledBrine = new SimpleFluidMaterial("chilled_brine", 0xfcfc95);
    public static final SimpleFluidMaterial MagnesiumContainingBrine = new SimpleFluidMaterial("magnesium_containing_brine", 0xfcfcbc);
    public static final SimpleFluidMaterial BrominatedBrine = new SimpleFluidMaterial("brominated_brine", 0xfdd48d);
    public static final SimpleFluidMaterial AcidicBrominatedBrine = new SimpleFluidMaterial("acidic_brominated_brine", 0xfdd48d);
    public static final SimpleFluidMaterial SodiumChlorideSolution = new SimpleFluidMaterial("sodium_chloride_solution", 0xfcfcfa);


    public static final SimpleDustMaterial Biphenyl = new SimpleDustMaterial("biphenyl", 0x003366, (short) 155, MaterialIconSet.DULL);
    public static final SimpleDustMaterial Diiodobiphenyl = new SimpleDustMaterial("diiodobiphenyl", 0x000f66, (short) 156, MaterialIconSet.DULL);
    public static final SimpleDustMaterial Bipyridine = new SimpleDustMaterial("bipyridine", 0X978662, (short) 170, MaterialIconSet.DULL);
    public static final SimpleDustMaterial PalladiumChloride = new SimpleDustMaterial("palladium_chloride", 0xb9c0c7, (short) 157, MaterialIconSet.DULL);
    public static final SimpleDustMaterial PalladiumBisDibenzylidieneacetone = new SimpleDustMaterial("palladium_bisdibenzylidieneacetone", 0Xbe81a0, (short) 158, MaterialIconSet.DULL);
    public static final SimpleDustMaterial PotassiumTetrachloroplatinate = new SimpleDustMaterial("potassium_tetrachloroplatinate", 0xffba54, (short) 159, MaterialIconSet.DULL);
    public static final SimpleDustMaterial NickelTriphenylPhosphite = new SimpleDustMaterial("nickel_triphenyl_phosphite", 0xd9d973, (short) 160, MaterialIconSet.SHINY);
    public static final SimpleDustMaterial Dichlorocycloctadieneplatinium = new SimpleDustMaterial("dichlorocycloctadieneplatinium", 0xe0f78a, (short) 161, MaterialIconSet.SHINY);
    public static final SimpleDustMaterial GrapheneNanotubeMix = new SimpleDustMaterial("graphene_nanotube_mix", 0x2c2c2c, (short) 162, MaterialIconSet.DULL);
    public static final SimpleDustMaterial GrapheneAlignedCNT = new SimpleDustMaterial("graphene_aligned_cnt", 0x2c2c2c, (short) 163, MaterialIconSet.SHINY);
    public static final SimpleDustMaterial NiAlCatalyst = new SimpleDustMaterial("nial_catalyst", 0x6ea2ff, (short) 164, MaterialIconSet.DULL);
    public static final SimpleDustMaterial TitaniumNitrate = new SimpleDustMaterial("titanium_nitrate", 0xFF0066, (short) 171, MaterialIconSet.DULL);


    public static final SimpleFluidMaterial ButhylLithium = new SimpleFluidMaterial("butyl_lithium", 0xfcfcfa);
    public static final SimpleFluidMaterial Acetaldehyde = new SimpleFluidMaterial("acetaldehyde", 0xFF9933);
    public static final SimpleFluidMaterial Benzaldehyde = new SimpleFluidMaterial("benzaldehyde", 0xb26f22);
    public static final SimpleFluidMaterial Dibenzyldieneacetone = new SimpleFluidMaterial("dibenzylidieneacetone", 0Xcc6699);
    public static final SimpleFluidMaterial TetramethyltinChloride = new SimpleFluidMaterial("tetramethyltin_chloride", 0x8c8075);
    public static final SimpleFluidMaterial ChloroPlatinicAcid = new SimpleFluidMaterial("chloroplatinic_acid", 0xffba54);
    public static final SimpleFluidMaterial Cyclooctadiene = new SimpleFluidMaterial("cyclooctadiene", 0x33CC33);
    public static final SimpleFluidMaterial Cycloparaphenylene = new SimpleFluidMaterial("cycloparaphenylene", 0x333333);
    public static final SimpleFluidMaterial SuperheavyMix = new SimpleFluidMaterial("superheavy_mix", 0x403737);
    public static final SimpleFluidMaterial NeutronPlasma = new SimpleFluidMaterial("neutron_plasma", 0xf0e9e9);

    public static final SimpleDustMaterial TitaniumTetrafluoride = new SimpleDustMaterial("titanium_tetrafluoride", Titanium.materialRGB, (short) 172, MaterialIconSet.SHINY);
    public static final SimpleFluidMaterial HotMetastableOganesson = new SimpleFluidMaterial("hot_oganesson", 0x521973);
    public static final SimpleFluidMaterial MoltenTitaniumTetrafluoride = new SimpleFluidMaterial("titanium_tetrafluoride_liquid", Titanium.materialRGB);
    public static final SimpleFluidMaterial MoltenTitanium50Tetrafluoride = new SimpleFluidMaterial("titanium50_tetrafluoride_liquid", Titanium.materialRGB);
    public static final SimpleFluidMaterial Carbon12 = new SimpleFluidMaterial("carbon_12", Carbon.materialRGB);
    public static final SimpleFluidMaterial Carbon13 = new SimpleFluidMaterial("carbon_13", Carbon.materialRGB);
    public static final SimpleFluidMaterial Nitrogen14 = new SimpleFluidMaterial("nitrogen_14", Nitrogen.materialRGB);
    public static final SimpleFluidMaterial NItrogen15 = new SimpleFluidMaterial("nitrogen_15", Nitrogen.materialRGB);
    public static final SimpleFluidMaterial CNOcatalyst = new SimpleFluidMaterial("cno_catalyst", (Nitrogen.materialRGB + Carbon.materialRGB) / 2);
    public static final SimpleFluidMaterial Calcium44 = new SimpleFluidMaterial("calcium_44", Calcium.materialRGB);
    public static final SimpleFluidMaterial OgannesonBreedingBase = new SimpleFluidMaterial("og_breeding_base", ((Titanium.materialRGB + 0xA85A12) / 2));
    public static final SimpleFluidMaterial QuassifissioningPlasma = new SimpleFluidMaterial("quasifissioning_plasma", 0xD5CB54);
    public static final SimpleFluidMaterial Ytterbium178 = new SimpleFluidMaterial("ytterbium_178", Ytterbium.materialRGB);
    public static final SimpleFluidMaterial FlYbPlasma = new SimpleFluidMaterial("flyb_plasma", (Ytterbium.materialRGB + 0x521973) / 2);
    public static final SimpleFluidMaterial Chromium48 = new SimpleFluidMaterial("chromium48", Chrome.materialRGB);
    public static final SimpleFluidMaterial Iron52 = new SimpleFluidMaterial("iron52", Iron.materialRGB);
    public static final SimpleFluidMaterial Nickel56 = new SimpleFluidMaterial("nickel56", Nickel.materialRGB);
    public static final SimpleFluidMaterial Titanium44 = new SimpleFluidMaterial("titanium44", Titanium.materialRGB);
    public static final SimpleFluidMaterial HeliumCNO = new SimpleFluidMaterial("helium_rich_cno", 0x59ffa6);
    public static final SimpleFluidMaterial PlasmaChromium48 = new SimpleFluidMaterial("chromium48_plasma", Chrome.materialRGB);
    public static final SimpleFluidMaterial PlasmaIron52 = new SimpleFluidMaterial("iron52_plasma", Iron.materialRGB);
    public static final SimpleFluidMaterial PlasmaNickel56 = new SimpleFluidMaterial("nickel56_plasma", Nickel.materialRGB);
    public static final SimpleFluidMaterial PlasmaTitanium44 = new SimpleFluidMaterial("titanium44_plasma", Titanium.materialRGB);
    public static final SimpleFluidMaterial PlasmaHeliumCNO = new SimpleFluidMaterial("helium_rich_cno_plasma", 0x59ffa6);

    public static final SimpleDustMaterial AnodicSlime = new SimpleDustMaterial("anodic_slime", CopperLeach.materialRGB, (short) 173, MaterialIconSet.DULL);
    public static final SimpleDustMaterial TelluriumOxide = new SimpleDustMaterial("tellurium_oxide", 0xFFFF66, (short) 174, MaterialIconSet.SHINY);
    public static final SimpleDustMaterial SeleniumOxide = new SimpleDustMaterial("selenium_oxide", 0xFFFF66, (short) 175, MaterialIconSet.SHINY);
    public static final SimpleFluidMaterial SelenateTellurateMix = new SimpleFluidMaterial("selenate_tellurate_mixture", 0x765A30);
    public static final SimpleFluidMaterial SelenateSolution = new SimpleFluidMaterial("selenate_solution", 0xc1c46a);
    public static final SimpleFluidMaterial CopperRefiningSolution = new SimpleFluidMaterial("copper_refining_solution", 0x765A30);

    public static final SimpleFluidMaterial SodiumHydroxideBauxite = new SimpleFluidMaterial("sodium_hydroxide_bauxite", 0xbf731a);
    public static final SimpleFluidMaterial ImpureAluminiumHydroxideSolution = new SimpleFluidMaterial("impure_aloh3_soution", 0xd8653e);
    public static final SimpleFluidMaterial PureAluminiumHydroxideSolution = new SimpleFluidMaterial("pure_aloh3_soution", 0xfcfcfa);
    public static final SimpleFluidMaterial RedMud = new SimpleFluidMaterial("red_mud", 0xcc3300);
    public static final SimpleFluidMaterial NeutralisedRedMud = new SimpleFluidMaterial("neutralised_red_mud", 0xcc3300);
    public static final SimpleFluidMaterial FerricREEChloride = new SimpleFluidMaterial("ferric_ree_chloride", 0x30301a);
    public static final SimpleFluidMaterial RedSlurry = new SimpleFluidMaterial("red_slurry", 0xcc3300);
    public static final SimpleFluidMaterial TitaniumSulfate = new SimpleFluidMaterial("titanium_sulfate", 0xdc3d7c);
    public static final SimpleFluidMaterial RubySlurry = new SimpleFluidMaterial("ruby_slurry", Ruby.materialRGB);
    public static final SimpleFluidMaterial SapphireSlurry = new SimpleFluidMaterial("sapphire_slurry", Sapphire.materialRGB);
    public static final SimpleFluidMaterial GreenSapphireSlurry = new SimpleFluidMaterial("green_sapphire_slurry", GreenSapphire.materialRGB);
    public static final SimpleFluidMaterial DiluteNitricAcid = new SimpleFluidMaterial("dilute_nitric_acid", (NitricAcid.materialRGB + Water.materialRGB) / 2);

    public static final SimpleDustMaterial ManganeseSulfate = new SimpleDustMaterial("manganese_sulfate", 0xfcfcfa, (short) 176, MaterialIconSet.DULL);
    public static final SimpleDustMaterial TinSlag = new SimpleDustMaterial("tin_slag", 0xc8b9a9, (short) 177, MaterialIconSet.DULL);
    public static final SimpleDustMaterial NbTaContainingDust = new SimpleDustMaterial("nbta_containing_dust", 0xc8b9a9, (short) 178, MaterialIconSet.DULL);
    public static final SimpleDustMaterial NiobiumTantalumOxide = new SimpleDustMaterial("niobium_tantalum_oxide", 0xfcfcfa, (short) 179, MaterialIconSet.SHINY);
    public static final SimpleDustMaterial FusedColumbite = new SimpleDustMaterial("fused_columbite", 0xCCCC00, (short) 180, MaterialIconSet.DULL);
    public static final SimpleDustMaterial LeachedColumbite = new SimpleDustMaterial("leached_columbite", 0xCCCC00, (short) 181, MaterialIconSet.SHINY);
    public static final SimpleDustMaterial FusedTantalite = new SimpleDustMaterial("fused_tantalite", 0x915028, (short) 182, MaterialIconSet.DULL);
    public static final SimpleDustMaterial LeachedTantalite = new SimpleDustMaterial("leached_tantalite", 0x915028, (short) 183, MaterialIconSet.SHINY);
    public static final SimpleDustMaterial ColumbiteMinorOxideResidue = new SimpleDustMaterial("columbite_minor_oxide_residue", 0x915028, (short) 184, MaterialIconSet.DULL);
    public static final SimpleDustMaterial TantaliteMinorOxideResidue = new SimpleDustMaterial("tantalite_minor_oxide_residue", 0xCCCC00, (short) 185, MaterialIconSet.DULL);
    public static final SimpleDustMaterial LeachedPyrochlore = new SimpleDustMaterial("leached_pyrochlore", 0x996633, (short) 186, MaterialIconSet.SHINY);
    public static final SimpleDustMaterial AcidicLeachedPyrochlore = new SimpleDustMaterial("acidic_leached_pyrochlore", 0x996633, (short) 187, MaterialIconSet.SHINY);
    public static final SimpleDustMaterial PotasssiumFluoroNiobate = new SimpleDustMaterial("potassium_fluoroniobate", 0x73ff00, (short) 188, MaterialIconSet.SHINY);
    public static final SimpleDustMaterial PotasssiumFluoroTantalate = new SimpleDustMaterial("potassium_fluorotantalate", 0x73ff00, (short) 189, MaterialIconSet.SHINY);
    public static final SimpleDustMaterial BariumPeroxide = new SimpleDustMaterial("barium_peroxide", 0xfcfcfa, (short) 190, MaterialIconSet.DULL);
    public static final SimpleDustMaterial CassiteriteCokePellets = new SimpleDustMaterial("cassiterite_coke_pellets", 0x8f8f8f, (short) 191, MaterialIconSet.DULL);
    public static final SimpleDustMaterial IronSulfateDust = new SimpleDustMaterial("iron_sulfate_dust", IronSulfate.rgb, (short) 192, MaterialIconSet.DULL);
    public static final SimpleDustMaterial StrontiumCarbonate = new SimpleDustMaterial("strontium_carbonate", StrontiumSulfate.rgb, (short) 193, MaterialIconSet.DULL);

    public static final SimpleFluidMaterial NbTaSeparationMixture = new SimpleFluidMaterial("nbta_separation_mixture", 0xbcac93);
    public static final SimpleFluidMaterial FluoroniobicAcid = new SimpleFluidMaterial("fluroniobic_acid", 0x73ff00);
    public static final SimpleFluidMaterial FluorotantalicAcid = new SimpleFluidMaterial("flurotantalic_acid", 0x73ff00);
    public static final SimpleFluidMaterial NbTaFluorideMix = new SimpleFluidMaterial("nbta_fluoride_mix", 0xbcac93);
    public static final SimpleFluidMaterial OxypentafluoroNiobate = new SimpleFluidMaterial("oxypentafluoroniobate", 0x73ff00);
    public static final SimpleFluidMaterial HeptafluoroTantalate = new SimpleFluidMaterial("heptafluorotantalate", 0x73ff00);
    public static final SimpleFluidMaterial REEThUSulfateSolution = new SimpleFluidMaterial("reethu_sulfate_solution", 0x89be5c);
    public static final SimpleFluidMaterial RareEarthNitrateSolution = new SimpleFluidMaterial("rare_earth_nitrate_solution", 0xcfb37d);
    public static final SimpleFluidMaterial AlkalineEarthSulfateSolution = new SimpleFluidMaterial("alkalineearth_sulphate", 0xe6ebff);

    public static final SimpleFluidMaterial WetEthyleneOxide = new SimpleFluidMaterial("wet_etylene_oxide", 0x90b3ff);
    public static final SimpleFluidMaterial EthyleneGlycol = new SimpleFluidMaterial("ethylene_glycol", 0x8080fa);
    public static final SimpleFluidMaterial DichlorineMonoxide = new SimpleFluidMaterial("dichlorine_monoxide", 0xfcfcfa);
    public static final SimpleDustMaterial SodiumBicarbonate = new SimpleDustMaterial("sodium_bicarbonate", 0xfcfcfa, (short) 194, MaterialIconSet.DULL);
    public static final SimpleDustMaterial SodiumHypochlorite = new SimpleDustMaterial("sodium_hypochlorite", 0x6cff50, (short) 195, MaterialIconSet.DULL);
    public static final SimpleFluidMaterial Chloroethanol = new SimpleFluidMaterial("chloroethanol", 0xcfb050);
    public static final SimpleFluidMaterial Choline = new SimpleFluidMaterial("choline", 0x63e45f);
    public static final SimpleFluidMaterial ATL = new SimpleFluidMaterial("atl", 0x709c4a);
    public static final SimpleFluidMaterial HotNitrogen = new SimpleFluidMaterial("hot_nitrogen", Nitrogen.materialRGB);
    public static final SimpleDustMaterial DehydratedLignite = new SimpleDustMaterial("dehydrated_lignite", 0x5c4020, (short) 196, MaterialIconSet.DULL);
    public static final SimpleDustMaterial BCEPellet = new SimpleDustMaterial("bce_pellet", 0x3c3020, (short) 197, MaterialIconSet.LIGNITE);
    public static final SimpleFluidMaterial ViscoelasticPolyurethane = new SimpleFluidMaterial("viscoelastic_polyurethane", 0xeffcef);
    public static final SimpleFluidMaterial ViscoelasticPolyurethaneFoam = new SimpleFluidMaterial("viscoelastic_polyurethane_foam", 0xeffcef);
    public static final SimpleFluidMaterial CalciumCarbonateSolution = new SimpleFluidMaterial("calcium_carbonate_solution", Calcite.materialRGB);
    public static final SimpleFluidMaterial BariumSulfateSolution = new SimpleFluidMaterial("barium_sulfate_solution", Barite.materialRGB);
    public static final SimpleFluidMaterial BentoniteClaySlurry = new SimpleFluidMaterial("bentonite_clay_solution", 0xdbc9c5);
    public static final SimpleFluidMaterial DrillingMud = new SimpleFluidMaterial("drilling_mud", 0x996600);
    public static final SimpleFluidMaterial UsedDrillingMud = new SimpleFluidMaterial("used_drilling_mud", 0x998833);
    public static final SimpleFluidMaterial TolueneDiisocyanate = new SimpleFluidMaterial("toluene_diisocyanate", 0xbaf6ca);

    public static final SimpleDustMaterial CopperGalliumIndiumMix = new SimpleDustMaterial("copper_gallium_indium_mix", (Indium.materialRGB + Copper.materialRGB + Gallium.materialRGB) / 3, (short) 198, MaterialIconSet.DULL);
    public static final SimpleDustMaterial CopperGalliumIndiumSelenide = new SimpleDustMaterial("copper_gallium_indium_selenide", (CopperGalliumIndiumMix.rgb + Selenium.materialRGB) / 2, (short) 199, MaterialIconSet.DULL);
    public static final SimpleDustMaterial LanthanumCalciumManganate = new SimpleDustMaterial("lanthanum_gallium_manganate", 0x8aa07b, (short) 200, MaterialIconSet.DULL);
    public static final SimpleDustMaterial AluminiumComplex = new SimpleDustMaterial("aluminium_complex", 0x3f5a9f, (short) 201, MaterialIconSet.DULL);
    public static final SimpleDustMaterial BismuthRuthenate = new SimpleDustMaterial("bismuth_ruthenate", 0x94cf5c, (short) 202, MaterialIconSet.DULL);
    public static final SimpleDustMaterial BismuthIridiate = new SimpleDustMaterial("bismuth_iridiate", 0x478a6b, (short) 203, MaterialIconSet.DULL);
    public static final SimpleDustMaterial PEDOT = new SimpleDustMaterial("pedot", 0x5cef20, (short) 204, MaterialIconSet.DULL);
    public static final SimpleDustMaterial IronPlatinumCatalyst = new SimpleDustMaterial("iron_platinum_catalyst", Iron.materialRGB / 2 + Platinum.materialRGB / 2, (short) 205, MaterialIconSet.SHINY);
    public static final SimpleDustMaterial YttriumNitrate = new SimpleDustMaterial("yttrium_nitrate", 0xdadafc, (short) 206, MaterialIconSet.SHINY);
    public static final SimpleDustMaterial CopperNitrate = new SimpleDustMaterial("copper_nitrate", 0xcaecec, (short) 254, MaterialIconSet.SHINY);
    public static final SimpleDustMaterial BariumNitrate = new SimpleDustMaterial("barium_nitrate", 0xfcfcfa, (short) 207, MaterialIconSet.SHINY);
    public static final SimpleDustMaterial WellMixedYBCOxides = new SimpleDustMaterial("well_mixed_ybc_oxides", 0x2c3429, (short) 208, MaterialIconSet.DULL);
    public static final SimpleDustMaterial PiledTBCC = new SimpleDustMaterial("piled_tbcc", 0x669900, (short) 209, MaterialIconSet.SHINY);
    public static final SimpleDustMaterial ActiniumOxalate = new SimpleDustMaterial("actinium_oxalate", Actinium.materialRGB, (short) 210, MaterialIconSet.SHINY);
    public static final SimpleDustMaterial ActiniumHydride = new SimpleDustMaterial("actinium_hydride", Actinium.materialRGB, (short) 211, MaterialIconSet.SHINY);
    public static final SimpleDustMaterial LanthanumFullereneMix = new SimpleDustMaterial("lanthanum_fullerene_mix", 0xdfcafa, (short) 212, MaterialIconSet.SHINY);
    public static final SimpleDustMaterial LanthanumEmbeddedFullerene = new SimpleDustMaterial("lanthanum_embedded_fullerene", 0x99cc00, (short) 213, MaterialIconSet.SHINY);
    public static final SimpleDustMaterial IronIodide = new SimpleDustMaterial("iron_iodide", 0x7a7a66, (short) 214, MaterialIconSet.DULL);
    public static final SimpleDustMaterial PotassiumIodide = new SimpleDustMaterial("potassium_iodide", 0x7a7a66, (short) 215, MaterialIconSet.DULL);
    public static final SimpleDustMaterial ThalliumIodide = new SimpleDustMaterial("thallium_iodide", 0x7a7a66, (short) 216, MaterialIconSet.DULL);
    public static final SimpleDustMaterial ScandiumIodide = new SimpleDustMaterial("scandium_iodide", 0x7a7a66, (short) 217, MaterialIconSet.DULL);
    public static final SimpleDustMaterial RubidiumIodide = new SimpleDustMaterial("rubidium_iodide", 0x7a7a66, (short) 218, MaterialIconSet.DULL);
    public static final SimpleDustMaterial IndiumIodide = new SimpleDustMaterial("indium_iodide", 0x7a7a66, (short) 219, MaterialIconSet.DULL);
    public static final SimpleDustMaterial GalliumIodide = new SimpleDustMaterial("gallium_iodide", 0x7a7a66, (short) 220, MaterialIconSet.DULL);
    public static final SimpleDustMaterial UVAHalideMix = new SimpleDustMaterial("uva_halide_mix", 0x660066, (short) 221, MaterialIconSet.DULL);
    public static final SimpleDustMaterial WhiteHalideMix = new SimpleDustMaterial("white_halide_mix", 0x660066, (short) 222, MaterialIconSet.DULL);
    public static final SimpleDustMaterial BlueHalideMix = new SimpleDustMaterial("blue_halide_mix", 0x660066, (short) 223, MaterialIconSet.DULL);
    public static final SimpleDustMaterial GreenHalideMix = new SimpleDustMaterial("green_halide_mix", 0x660066, (short) 224, MaterialIconSet.DULL);
    public static final SimpleDustMaterial RedHalideMix = new SimpleDustMaterial("red_halide_mix", 0x660066, (short) 225, MaterialIconSet.DULL);
    public static final SimpleDustMaterial CarbonylPurifiedIron = new SimpleDustMaterial("carbonyl_purified_iron", Iron.materialRGB, (short) 226, MaterialIconSet.SHINY);
    public static final SimpleDustMaterial BariumTriflate = new SimpleDustMaterial("barium_triflate", 0xdfcfcf, (short) 227, MaterialIconSet.SHINY);
    public static final SimpleDustMaterial ScandiumTriflate = new SimpleDustMaterial("scandium_triflate", 0xdfcfcf, (short) 228, MaterialIconSet.SHINY);
    public static final SimpleDustMaterial SodiumThiosulfate = new SimpleDustMaterial("sodium_thiosulfate", 0x2090fc, (short) 229, MaterialIconSet.DULL);
    public static final SimpleDustMaterial TitaniumCyclopentanyl = new SimpleDustMaterial("titanium_cyclopentanyl", 0xbc30bc, (short) 230, MaterialIconSet.DULL);
    public static final SimpleDustMaterial SodiumBromide = new SimpleDustMaterial("sodium_bromide", 0xfeaffc, (short) 231, MaterialIconSet.DULL);
    public static final SimpleDustMaterial FranciumCarbide = new SimpleDustMaterial("francium_carbide", Francium.materialRGB, (short) 232, MaterialIconSet.SHINY);
    public static final SimpleDustMaterial BoronCarbide = new SimpleDustMaterial("boron_carbide", 0x303030, (short) 233, MaterialIconSet.FINE);
    public static final SimpleDustMaterial BoronFranciumCarbide = new SimpleDustMaterial("boron_francium_carbide", 0x808080, (short) 234, MaterialIconSet.SHINY);
    public static final SimpleDustMaterial MixedAstatideSalts = new SimpleDustMaterial("mixed_astatide_salts", 0x6df63f, (short) 235, MaterialIconSet.SHINY);
    public static final SimpleDustMaterial SodiumIodide = new SimpleDustMaterial("sodium_iodide", 0x555588, (short) 236, MaterialIconSet.DULL);
    public static final SimpleDustMaterial SodiumIodate = new SimpleDustMaterial("sodium_iodate", 0x11116d, (short) 237, MaterialIconSet.DULL);
    public static final SimpleDustMaterial SodiumPeriodate = new SimpleDustMaterial("sodium_periodate", 0x11116d, (short) 238, MaterialIconSet.DULL);
    public static final SimpleDustMaterial SodiumSeaborgate = new SimpleDustMaterial("sodium_seaborgate", 0x55bbd4, (short) 239, MaterialIconSet.SHINY);
    public static final SimpleDustMaterial OsmiumTetroxide = new SimpleDustMaterial("osmium_tetroxide", 0x82cbd6, (short) 240, MaterialIconSet.SHINY);
    public static final SimpleDustMaterial StrontiumChloride = new SimpleDustMaterial("strontium_chloride", 0x3a9aba, (short) 241, MaterialIconSet.SHINY);
    public static final SimpleDustMaterial YttriumEuropiumVanadate = new SimpleDustMaterial("yttrium_europium_vanadate", 0xfcfcfa, (short) 242, MaterialIconSet.DULL);
    public static final SimpleDustMaterial StrontiumEuropiumAluminate = new SimpleDustMaterial("strontium_europium_aluminate", 0xfcfcfa, (short) 243, MaterialIconSet.DULL);
    public static final SimpleDustMaterial BariumStrontiumTitanate = new SimpleDustMaterial("barium_strontium_titanate", 0xFF0066, (short) 244, MaterialIconSet.SHINY);
    public static final SimpleDustMaterial RutheniumDioxide = new SimpleDustMaterial("ruthenium_dioxide", RutheniumTetroxide.materialRGB, (short) 245, MaterialIconSet.DULL);
    public static final SimpleDustMaterial PotassiumManganate = new SimpleDustMaterial("potassium_manganate", 0xaf20af, (short) 246, MaterialIconSet.DULL);
    public static final SimpleDustMaterial BariumChloride = new SimpleDustMaterial("barium_chloride", 0xfcfcfa, (short) 247, MaterialIconSet.DULL);
    public static final SimpleDustMaterial TantalumOxide = new SimpleDustMaterial("tantalum_oxide", 0xfcfcfa, (short) 248, MaterialIconSet.SHINY);
    public static final SimpleDustMaterial ZirconylChloride = new SimpleDustMaterial("zirconyl_chloride", ZirconiumTetrachloride.rgb, (short) 249, MaterialIconSet.DULL);
    public static final SimpleDustMaterial LeadSenenide = new SimpleDustMaterial("lead_selenide", 0xfcfcfa, (short) 250, MaterialIconSet.DULL);
    public static final SimpleDustMaterial LeadScandiumTantalate = new SimpleDustMaterial("lead_scandium_tantalate", 0xfcfcfa, (short) 251, MaterialIconSet.SHINY);
    public static final SimpleDustMaterial BETS = new SimpleDustMaterial("bets", 0x7ada00, (short) 253, MaterialIconSet.SHINY);
    public static final SimpleDustMaterial MagnetorestrictiveAlloy = new SimpleDustMaterial("magnetorestrictive_alloy", 0xafefef, (short) 252, MaterialIconSet.DULL, of(new MaterialStack(Terbium, 4), new MaterialStack(Dysprosium, 7), new MaterialStack(Iron, 10), new MaterialStack(Cobalt, 5), new MaterialStack(Boron, 2), new MaterialStack(Silicon, 1), new MaterialStack(Carbon, 1)));
    public static final SimpleDustMaterial BoronOxide = new SimpleDustMaterial("boron_oxide",0xfcfcfa,(short) 256,MaterialIconSet.DULL);
    public static final SimpleDustMaterial LithiumAluminiumFluoride = new SimpleDustMaterial("lithium_aluminium_fluoride",0xfcfcfa,(short) 263,MaterialIconSet.DULL);

    public static final SimpleFluidMaterial HydroselenicAcid = new SimpleFluidMaterial("hydroselenic_acid", Selenium.materialRGB);
    public static final SimpleFluidMaterial Aminophenol = new SimpleFluidMaterial("aminophenol", 0xafca3a);
    public static final SimpleFluidMaterial Hydroquinoline = new SimpleFluidMaterial("hydroquinoline", 0x3a9a71);
    public static final SimpleFluidMaterial Perbromothiophene = new SimpleFluidMaterial("perbromothiophene", 0x87cc17);
    public static final SimpleFluidMaterial Dimetoxythiophene = new SimpleFluidMaterial("dimetoxythiophene", 0x90ff43);
    public static final SimpleFluidMaterial EDOT = new SimpleFluidMaterial("ethylenedioxythiophene", 0x7a9996);
    public static final SimpleFluidMaterial CitricAcid = new SimpleFluidMaterial("citric_acid", 0xffcc00);
    public static final SimpleFluidMaterial GasMixture = new SimpleFluidMaterial("gas_mixture", 0x003ffa);
    public static final SimpleFluidMaterial OxalicAcid = new SimpleFluidMaterial("oxalic_acid", 0x4aaae2);
    public static final SimpleFluidMaterial Trimethylchlorosilane = new SimpleFluidMaterial("trimethylchlorosilane", Dimethyldichlorosilane.materialRGB);
    public static final SimpleFluidMaterial Bromoacrolein = new SimpleFluidMaterial("dibromoacrolein", 0x4a4a4a);
    public static final SimpleFluidMaterial Bromohydrothiine = new SimpleFluidMaterial("bromodihydrothiine", 0x40ff3a);
    public static final SimpleFluidMaterial Lithiumthiinediselenide = new SimpleFluidMaterial("lithiumthiinediselenide", 0x7ada00);
    public static final SimpleFluidMaterial Bromobutane = new SimpleFluidMaterial("bromobutane", 0xff3333);
    public static final SimpleFluidMaterial AstatideSolution = new SimpleFluidMaterial("astatide_solution", 0x6df63f);
    public static final SimpleFluidMaterial Biperfluoromethanedisulfide = new SimpleFluidMaterial("biperfluoromethanedisulfide", 0x3ada40);
    public static final SimpleFluidMaterial BariumTriflateSolution = new SimpleFluidMaterial("barium_triflate_solution", BariumTriflate.rgb);
    public static final SimpleFluidMaterial BariumStrontiumAcetateSolution = new SimpleFluidMaterial("basr_acetate_solution", 0x9a9b98);
    public static final SimpleFluidMaterial TitaniumIsopropoxide = new SimpleFluidMaterial("titanium_isopropoxide", 0xFF0066);
    public static final SimpleFluidMaterial BariumChlorideSolution = new SimpleFluidMaterial("barium_chloride_solution", BariumChloride.rgb);
    public static final SimpleFluidMaterial IronCarbonyl = new SimpleFluidMaterial("iron_carbonyl", 0xff8000);
    public static final SimpleFluidMaterial PurifiedIronCarbonyl = new SimpleFluidMaterial("purified_iron_carbonyl", 0xff8000);
    public static final SimpleFluidMaterial BismuthNitrateSoluton = new SimpleFluidMaterial("bismuth_nitrate_solution", BismuthChloride.rgb);
    public static final SimpleFluidMaterial BariumTitanatePreparation = new SimpleFluidMaterial("barium_titanate_preparation", 0x99FF99);
    public static final SimpleFluidMaterial BariumStrontiumTitanatePreparation = new SimpleFluidMaterial("basr_titanate_preparation", 0xFF0066);
    public static final SimpleFluidMaterial CarbonTetrachloride = new SimpleFluidMaterial("carbon_tetrachloride", 0x2d8020);
    public static final SimpleFluidMaterial Chloroethane = new SimpleFluidMaterial("chloroethane", 0x33aa33);
    public static final SimpleFluidMaterial FranciumAstatideSolution = new SimpleFluidMaterial("francium_astatide_solution", SeaWater.rgb);
    public static final SimpleFluidMaterial ActiniumSuperhydridePlasma = new SimpleFluidMaterial("actinium_superhydride_plasma", Actinium.materialRGB * 9 / 8);
    public static final SimpleFluidMaterial Diborane = new SimpleFluidMaterial("diborane",Boron.materialRGB);

    public static final IngotMaterial Quantum = new IngotMaterial(857, "quantum", 0x0f0f0f, MaterialIconSet.SHINY, 7, of(new MaterialStack(Stellite, 15), new MaterialStack(Jasper, 5), new MaterialStack(Gallium, 5), new MaterialStack(Americium241.getMaterial(), 5), new MaterialStack(Palladium, 5), new MaterialStack(Bismuth, 5), new MaterialStack(Germanium, 5), new SimpleDustMaterialStack(SiliconCarbide, 5)), CORE_METAL | DISABLE_DECOMPOSITION | DISABLE_REPLICATION, null, 25000);
    public static final IngotMaterial BlackTitanium = new IngotMaterial(856, "black_titanium", 0x6C003B, MaterialIconSet.SHINY, 7, of(new MaterialStack(Titanium, 26), new MaterialStack(Lanthanum, 6), new MaterialStack(Tungsten, 4), new MaterialStack(Cobalt, 3), new MaterialStack(Manganese, 2), new MaterialStack(Phosphorus, 2), new MaterialStack(Palladium, 2), new MaterialStack(Niobium, 1), new MaterialStack(Argon, 5)), CORE_METAL | DISABLE_DECOMPOSITION, null, Titanium.blastFurnaceTemperature * 16);
    public static final IngotMaterial TungstenTitaniumCarbide = new IngotMaterial(855, "tungsten_titanium_carbide", 0x800d0d, MaterialIconSet.SHINY, 7, of(new MaterialStack(TungstenCarbide, 7), new MaterialStack(Titanium, 3)), CORE_METAL | DISABLE_DECOMPOSITION, null, 4422);
    public static final IngotMaterial TitanSteel = new IngotMaterial(854, "titan_steel", 0xAA0d0d, MaterialIconSet.SHINY, 7, of(new MaterialStack(TungstenTitaniumCarbide, 3), new MaterialStack(Jasper, 3)), CORE_METAL | DISABLE_DECOMPOSITION, null, 11765);
    public static final IngotMaterial Inconel792 = new IngotMaterial(853, "inconel_b", 0x6CF076, MaterialIconSet.SHINY, 5, of(new MaterialStack(Nickel, 2), new MaterialStack(Niobium, 1), new MaterialStack(Aluminium, 2), new MaterialStack(Nichrome, 1)), CORE_METAL | DISABLE_DECOMPOSITION, null, 6200);
    public static final IngotMaterial Pikyonium = new IngotMaterial(852, "pikyonium", 0x3467BA, MaterialIconSet.SHINY, 7, of(new MaterialStack(Inconel792, 8), new MaterialStack(EglinSteel, 5), new MaterialStack(NaquadahEnriched, 4), new MaterialStack(Cerium, 3), new MaterialStack(Antimony, 2), new MaterialStack(Platinum, 2), new MaterialStack(Ytterbium, 1), new MaterialStack(TungstenSteel, 4)), CORE_METAL | DISABLE_DECOMPOSITION, null, 11765);
    public static final IngotMaterial Lafium = new IngotMaterial(851, "lafium", 0x0d0d60, MaterialIconSet.SHINY, 7, of(new MaterialStack(HastelloyN, 8), new MaterialStack(Naquadah, 4), new MaterialStack(Samarium, 2), new MaterialStack(Tungsten, 4), new MaterialStack(Argon, 2), new MaterialStack(Aluminium, 6), new MaterialStack(Nickel, 8), new MaterialStack(Carbon, 2)), CORE_METAL | DISABLE_DECOMPOSITION, null, 9865);
    public static final IngotMaterial Zeron100 = new IngotMaterial(850, "zeron", 0xB4B414, MaterialIconSet.SHINY, 5, of(new MaterialStack(Chrome, 13), new MaterialStack(Nickel, 3), new MaterialStack(Molybdenum, 2), new MaterialStack(Copper, 10), new MaterialStack(Tungsten, 2), new MaterialStack(Steel, 20)), CORE_METAL | DISABLE_DECOMPOSITION, null, 6100);
    public static final IngotMaterial Cinobite = new IngotMaterial(721, "cinobite", 0x010101, MaterialIconSet.SHINY, 5, of(new MaterialStack(Zeron100, 8), new MaterialStack(Naquadria, 4), new MaterialStack(Gadolinium, 3), new MaterialStack(Aluminium, 2), new MaterialStack(Mercury, 1), new MaterialStack(Tin, 1), new MaterialStack(Titanium, 6), new MaterialStack(Osmiridium, 1)), CORE_METAL | DISABLE_DECOMPOSITION, null, 12565);
    public static final IngotMaterial HDCS = new IngotMaterial(720, "hdcs", 0x334433, MaterialIconSet.SHINY, 5, of(new MaterialStack(TungstenSteel, 12), new MaterialStack(HSSS, 9), new MaterialStack(HSSG, 6), new MaterialStack(Ruridit, 3), new MaterialStack(MagnetoResonatic, 2), new MaterialStack(Plutonium, 1)), CORE_METAL | DISABLE_DECOMPOSITION, null, 9000);
    public static final IngotMaterial Trinium = new IngotMaterial(719, "trinium", 0x9aa19c, MaterialIconSet.SHINY, 7, of(), CORE_METAL | GENERATE_ORE, Element.valueOf("Ke"), 12500);
    public static final IngotMaterial Adamantium = new IngotMaterial(718, "adamantium", 0x2d365c, MaterialIconSet.SHINY, 7, of(), CORE_METAL, Element.valueOf("Ad"), 27500);
    public static final IngotMaterial Vibranium = new IngotMaterial(717, "vibranium", 0x828aad, MaterialIconSet.SHINY, 7, of(), CORE_METAL, Element.valueOf("Vb"), 55000);
    public static final IngotMaterial ProtoAdamantium = new IngotMaterial(716, "proto_adamantium", 0x4662d4, MaterialIconSet.SHINY, 7, of(new MaterialStack(Adamantium, 3), new MaterialStack(Promethium, 2)), CORE_METAL, null, 35000);
    public static final IngotMaterial TriniumTitanium = new IngotMaterial(715, "trinium_titanium", 0x9986a3, MaterialIconSet.SHINY, 7, of(new MaterialStack(Trinium, 2), new MaterialStack(Titanium, 1)), CORE_METAL, null, 24000);
    public static final IngotMaterial Taranium = new IngotMaterial(714, "taranium", 0x0c0c0d, MaterialIconSet.SHINY, 7, of(), CORE_METAL, Element.valueOf("Tn"), 15000);
    public static final GemMaterial Zircon = new GemMaterial(713, "zircon", 0xeb9e3f, MaterialIconSet.GEM_VERTICAL, 7, of(), GENERATE_ORE);
    public static final DustMaterial Caliche = new DustMaterial(712, "caliche", 0xeb9e3f, MaterialIconSet.DULL, 7, of(), GENERATE_ORE);
    public static final FluidMaterial IodizedOil = new FluidMaterial(711, "iodized_oil", 0x666666, MaterialIconSet.FLUID, of(), 0);
    public static final IngotMaterial LithiumTitanate = new IngotMaterial(710, "lithium_titanate", 0xfe71a9, MaterialIconSet.SHINY, 5, of(new MaterialStack(Lithium, 2), new MaterialStack(Titanium, 1), new MaterialStack(Oxygen, 3)), GENERATE_PLATE | DISABLE_DECOMPOSITION | CORE_METAL, null, 2500);
    public static final IngotMaterial CarbonNanotubes = new IngotMaterial(709, "carbon_nanotubes", 0x2c2c2c, MaterialIconSet.SHINY, 5, of(), EXCLUDE_BLOCK_CRAFTING_RECIPES | SMELT_INTO_FLUID | GENERATE_FOIL, null);
    public static final IngotMaterial Titanium50 = new IngotMaterial(708, "titanium50", Titanium.materialRGB, MaterialIconSet.SHINY, 5, of(), 0);
    public static final IngotMaterial MetastableOganesson = new IngotMaterial(707, "metastable_oganesson", 0xE61C24, MaterialIconSet.SHINY, 7, of(), CORE_METAL, Element.valueOf("Og"), 38000);
    public static final IngotMaterial MetastableFlerovium = new IngotMaterial(706, "metastable_flerovium", 0x521973, MaterialIconSet.SHINY, 7, of(), CORE_METAL, Element.valueOf("Fl"), 65000);
    public static final IngotMaterial ElectricallyImpureCopper = new IngotMaterial(705, "electrically_impure_copper", 0x765A30, MaterialIconSet.DULL, 2, of(), GENERATE_PLATE);
    public static final DustMaterial Rhodocrosite = new DustMaterial(704, "rhodocrosite", 0xff6699, MaterialIconSet.SHINY, 2, of(new MaterialStack(Manganese, 1), new MaterialStack(Carbon, 1), new MaterialStack(Oxygen, 3)), GENERATE_ORE);
    public static final DustMaterial Fluorite = new DustMaterial(703, "fluorite", 0x009933, MaterialIconSet.SHINY, 2, of(new MaterialStack(Calcium, 1), new MaterialStack(Fluorine, 2)), GENERATE_ORE);
    public static final DustMaterial Columbite = new DustMaterial(702, "columbite", 0xCCCC00, MaterialIconSet.SHINY, 2, of(new MaterialStack(Iron, 1), new MaterialStack(Niobium, 2), new MaterialStack(Oxygen, 6)), GENERATE_ORE);
    public static final DustMaterial Pyrochlore = new DustMaterial(701, "pyrochlore", 0x996633, MaterialIconSet.SHINY, 2, of(), GENERATE_ORE);
    public static final IngotMaterial Polyurethane = new IngotMaterial(700, "polyurethane", 0xeffcef, MaterialIconSet.DULL, 2, of(), EXCLUDE_BLOCK_CRAFTING_RECIPES);
    public static final IngotMaterial ThoriumDopedTungsten = new IngotMaterial(699, "thoria_doped_tungsten", Tungsten.materialRGB, MaterialIconSet.SHINY, 2, of(), GENERATE_ROD | GENERATE_FINE_WIRE);
    public static final IngotMaterial WoodsGlass = new IngotMaterial(698, "woods_glass", 0x730099, MaterialIconSet.SHINY, 2, of(), GENERATE_PLATE);
    public static final IngotMaterial BariumTitanate = new IngotMaterial(697, "barium_titanate", 0x99FF99, MaterialIconSet.SHINY, 2, of(), GENERATE_ROD);
    public static final GemMaterial LeadZirconateTitanate = new GemMaterial(696, "lead_zirconate_titanate", 0x359ade, MaterialIconSet.OPAL, 3, of(), GENERATE_PLATE | EXCLUDE_BLOCK_CRAFTING_RECIPES | DISABLE_DECOMPOSITION);

    public static final SimpleDustMaterial BETSPerrhenate = new SimpleDustMaterial("bets_perrhenate", 0x7ada00, (short) 255, MaterialIconSet.SHINY, of(new MaterialStack(Rhenium, 1), new MaterialStack(Carbon, 10), new MaterialStack(Hydrogen, 8), new MaterialStack(Sulfur, 4), new MaterialStack(Selenium, 4), new MaterialStack(Oxygen, 4)));
    public static final SimpleDustMaterial TBCCODust = new SimpleDustMaterial("tbcco_dust", 0x669900, (short) 257, MaterialIconSet.SHINY, of(new MaterialStack(Thallium, 2), new MaterialStack(Barium, 2), new MaterialStack(Calcium, 2), new MaterialStack(Copper, 3), new MaterialStack(Oxygen, 10)));
    public static final SimpleDustMaterial BorocarbideDust = new SimpleDustMaterial("borocarbide_dust", 0x9a9a2a, (short) 258, MaterialIconSet.SHINY, of(new MaterialStack(Boron, 4), new MaterialStack(Carbon, 7), new MaterialStack(Francium, 3), new MaterialStack(Astatine, 6), new MaterialStack(Holmium, 2), new MaterialStack(Thulium, 2), new MaterialStack(MetastableFlerovium, 2), new MaterialStack(Platinum, 2)));
    public static final SimpleDustMaterial ActiniumSuperhydride = new SimpleDustMaterial("actinium_superhydride", Actinium.materialRGB * 9 / 8, (short) 259, MaterialIconSet.SHINY, of(new MaterialStack(Actinium, 1), new MaterialStack(Hydrogen, 8)));
    public static final SimpleDustMaterial StrontiumSuperconductorDust = new SimpleDustMaterial("strontium_superconductor_dust", 0x45abf4, (short) 260, MaterialIconSet.SHINY, of(new MaterialStack(Ruthenium, 1), new MaterialStack(Seaborgium, 1), new MaterialStack(Strontium, 2), new MaterialStack(Oxygen, 8)));
    public static final SimpleDustMaterial FullereneSuperconductiveDust = new SimpleDustMaterial("fullerene_superconductor_dust", 0x99cc00, (short) 261, MaterialIconSet.SHINY, of(new MaterialStack(Lanthanum, 2), new MaterialStack(Caesium, 3), new MaterialStack(Rubidium, 3), new MaterialStack(Carbon, 120)));

    public static final IngotMaterial UVSuperconductorBase = new IngotMaterial(745, "uv_superconductor_base", 0xe0d207, MaterialIconSet.SHINY, 1, ImmutableList.of(new MaterialStack(Naquadria, 4), new MaterialStack(Osmiridium, 3), new MaterialStack(Rutherfordium, 1), new MaterialStack(Samarium, 1)), STD_METAL, null, 8900);
    public static final IngotMaterial UVSuperconductor = new IngotMaterial(744, "uv_superconductor", 0xe0d207, MaterialIconSet.SHINY, 1, of(new MaterialStack(UVSuperconductorBase, 1), new MaterialStack(Nitrogen, 1)), DISABLE_DECOMPOSITION);
    public static final IngotMaterial UHVSuperconductorBase = new IngotMaterial(740, "uhv_superconductor_base", 0x359ffc, MaterialIconSet.SHINY, 1, of(new SimpleDustMaterialStack(TBCCODust, 1), new SimpleDustMaterialStack(StrontiumSuperconductorDust, 1), new MaterialStack(TriniumTitanium, 2)), STD_METAL, null, 10000);
    public static final IngotMaterial UHVSuperconductor = new IngotMaterial(739, "uhv_superconductor", 0x359ffc, MaterialIconSet.SHINY, 1, of(new MaterialStack(UHVSuperconductorBase, 1), new MaterialStack(Nitrogen, 1)), DISABLE_DECOMPOSITION);
    public static final IngotMaterial UEVSuperconductorBase = new IngotMaterial(738, "uev_superconductor_base", 0x954fe0, MaterialIconSet.SHINY, 1, of(new SimpleDustMaterialStack(ActiniumSuperhydride, 1), new SimpleDustMaterialStack(BETSPerrhenate, 1), new MaterialStack(Vibranium, 2), new MaterialStack(Quantum, 1)), STD_METAL, null, 25000);
    public static final IngotMaterial UEVSuperconductor = new IngotMaterial(737, "uev_superconductor", 0x954fe0, MaterialIconSet.SHINY, 1, of(new MaterialStack(UEVSuperconductorBase, 1), new MaterialStack(Nitrogen, 1)), DISABLE_DECOMPOSITION);
    public static final IngotMaterial UIVSuperconductorBase = new IngotMaterial(727, "uiv_superconductor_base", 0x8bf743, MaterialIconSet.SHINY, 1, of(new SimpleDustMaterialStack(BorocarbideDust, 2), new SimpleDustMaterialStack(FullereneSuperconductiveDust, 1), new MaterialStack(MetastableOganesson, 2), new MaterialStack(ProtoAdamantium, 2)), STD_METAL, null, 50000);
    public static final IngotMaterial UIVSuperconductor = new IngotMaterial(726, "uiv_superconductor", 0x8bf743, MaterialIconSet.SHINY, 1, of(new MaterialStack(UIVSuperconductorBase, 1), new MaterialStack(Nitrogen, 1)), DISABLE_DECOMPOSITION);

    public static final SimpleFluidMaterial SupercriticalSteam = new SimpleFluidMaterial("supercritical_steam", Steam.materialRGB);
    public static final SimpleFluidMaterial SupercriticalDeuterium = new SimpleFluidMaterial("supercritical_deuterium", Deuterium.materialRGB);
    public static final SimpleFluidMaterial SupercriticalSodiumPotassiumAlloy = new SimpleFluidMaterial("supercritical_sodium_potassium_alloy", SodiumPotassiumAlloy.materialRGB);
    public static final SimpleFluidMaterial SupercriticalSodium = new SimpleFluidMaterial("supercritical_sodium", Sodium.materialRGB);
    public static final SimpleFluidMaterial SupercriticalFLiNaK = new SimpleFluidMaterial("supercritical_flinak", FLiNaK.materialRGB);
    public static final SimpleFluidMaterial SupercriticalFLiBe = new SimpleFluidMaterial("supercritical_flibe", FLiBe.materialRGB);
    public static final SimpleFluidMaterial SupercriticalLeadBismuthEutectic = new SimpleFluidMaterial("supercritical_lead_bismuth_eutectic", LeadBismuthEutectic.materialRGB);

    public static Material UEV = new MarkerMaterial("UEV");
    public static Material UIV = new MarkerMaterial("UIV");
    public static Material UMV = new MarkerMaterial("UMV");
    public static Material UXV = new MarkerMaterial("UXV");
    public static Material MAX = new MarkerMaterial("MAX");

    //free id 775

    @Override
    public void onMaterialsInit() {
        initNuclearMaterial();
        platinumProcess();
        goldProcess();
        naqProcess();

        if (GAConfig.Misc.tungstenProcess) {
            Scheelite.addFlag(DISABLE_DECOMPOSITION);
            Tungstate.addFlag(DISABLE_DECOMPOSITION);
        }

        NiobiumTitanium.setFluidPipeProperties(450, 2900, true);
        Polybenzimidazole.setFluidPipeProperties(450, 900, true);
        Enderium.setFluidPipeProperties(650, 1500, true);
        Naquadah.setFluidPipeProperties(1000, 19000, true);
        Ultimet.setFluidPipeProperties(1500, 12000, true);
        Zeron100.setFluidPipeProperties(1750, 15000, true);
        Lafium.setFluidPipeProperties(2000, 23000, true);
        Neutronium.setFluidPipeProperties(2800, 1000000, true);

        MVSuperconductorBase.setCableProperties(128, 4, 2);
        HVSuperconductorBase.setCableProperties(512, 4, 2);
        EVSuperconductorBase.setCableProperties(2048, 4, 2);
        IVSuperconductorBase.setCableProperties(8192, 4, 2);
        LuVSuperconductorBase.setCableProperties(32768, 4, 2);
        ZPMSuperconductorBase.setCableProperties(131072, 4, 2);
        UVSuperconductorBase.setCableProperties(524288, 4, 2);

        MVSuperconductor.setCableProperties(128, 4, 0);
        ignoreCable(MVSuperconductor);
        HVSuperconductor.setCableProperties(512, 4, 0);
        ignoreCable(HVSuperconductor);
        EVSuperconductor.setCableProperties(2048, 4, 0);
        ignoreCable(EVSuperconductor);
        IVSuperconductor.setCableProperties(8192, 4, 0);
        ignoreCable(IVSuperconductor);
        LuVSuperconductor.setCableProperties(32768, 4, 0);
        ignoreCable(LuVSuperconductor);
        ZPMSuperconductor.setCableProperties(131072, 4, 0);
        ignoreCable(ZPMSuperconductor);
        UVSuperconductor.setCableProperties(524288, 4, 0);
        ignoreCable(UVSuperconductor);
        ignoreCable(UHVSuperconductor);
        ignoreCable(UEVSuperconductor);
        ignoreCable(UIVSuperconductor);
        ignoreCable(UMVSuperconductor);
        ignoreCable(UXVSuperconductor);


        addCableAboveGTCELimit(UHVSuperconductorBase, new WireProperties(GAValues.V[GAValues.UHV], 4, 2));
        addCableAboveGTCELimit(UEVSuperconductorBase, new WireProperties(GAValues.V[GAValues.UEV], 4, 2));
        addCableAboveGTCELimit(UIVSuperconductorBase, new WireProperties(GAValues.V[GAValues.UIV], 4, 2));
        addCableAboveGTCELimit(UMVSuperconductorBase, new WireProperties(GAValues.V[GAValues.UMV], 4, 2));
        addCableAboveGTCELimit(UXVSuperconductorBase, new WireProperties(GAValues.V[GAValues.UXV], 4, 2));
        addCableAboveGTCELimit(TungstenTitaniumCarbide, new WireProperties(GAValues.V[GAValues.UHV], 4, 16));
        addCableAboveGTCELimit(AbyssalAlloy, new WireProperties(GAValues.V[GAValues.UHV], 2, 8));
        addCableAboveGTCELimit(EnrichedNaquadahAlloy, new WireProperties(GAValues.V[GAValues.UHV], 1, 4));
        addCableAboveGTCELimit(Pikyonium, new WireProperties(GAValues.V[GAValues.UEV], 4, 32));
        addCableAboveGTCELimit(TitanSteel, new WireProperties(GAValues.V[GAValues.UEV], 2, 16));
        addCableAboveGTCELimit(Cinobite, new WireProperties(GAValues.V[GAValues.UIV], 4, 64));
        addCableAboveGTCELimit(BlackTitanium, new WireProperties(GAValues.V[GAValues.UIV], 2, 32));
        addCableAboveGTCELimit(Neutronium, new WireProperties(GAValues.V[GAValues.UMV], 2, 32));
        addCableAboveGTCELimit(UHVSuperconductor, new WireProperties(GAValues.V[GAValues.UHV], 4, 0));
        addCableAboveGTCELimit(UEVSuperconductor, new WireProperties(GAValues.V[GAValues.UEV], 4, 0));
        addCableAboveGTCELimit(UIVSuperconductor, new WireProperties(GAValues.V[GAValues.UIV], 4, 0));
        addCableAboveGTCELimit(UMVSuperconductor, new WireProperties(GAValues.V[GAValues.UMV], 4, 0));
        addCableAboveGTCELimit(UXVSuperconductor, new WireProperties(GAValues.V[GAValues.UXV], 4, 0));


        Radon.addFlag(GENERATE_PLASMA);
        Potassium.addFlag(GENERATE_PLASMA);
        Helium.addFlag(GENERATE_PLASMA);
        Oxygen.addFlag(GENERATE_PLASMA);
        Iron.addFlag(GENERATE_PLASMA);
        Nickel.addFlag(GENERATE_PLASMA);
        Carbon.addFlag(GENERATE_PLASMA);
        Magnesium.addFlag(GENERATE_PLASMA);
        Silicon.addFlag(GENERATE_PLASMA);
        Sulfur.addFlag(GENERATE_PLASMA);
        Argon.addFlag(GENERATE_PLASMA);
        Calcium.addFlag(GENERATE_PLASMA);
        Titanium.addFlag(GENERATE_PLASMA);
        Sulfur.addFlag(SMELT_INTO_FLUID);

        Tellurium.addFlag(GENERATE_ORE);
        Diatomite.addFlag(GENERATE_ORE);
        GarnetSand.addFlag(GENERATE_ORE);
        Mica.addFlag(GENERATE_ORE);
        Asbestos.addFlag(GENERATE_ORE);
        Kyanite.addFlag(GENERATE_ORE);
        Pollucite.addFlag(GENERATE_ORE);
        BasalticMineralSand.addFlag(GENERATE_ORE);
        GraniticMineralSand.addFlag(GENERATE_ORE);
        FullersEarth.addFlag(GENERATE_ORE);
        Gypsum.addFlag(GENERATE_ORE);
        Zeolite.addFlag(GENERATE_ORE);
        Kaolinite.addFlag(GENERATE_ORE);
        Dolomite.addFlag(GENERATE_ORE);
        Wollastonite.addFlag(GENERATE_ORE);
        Trona.addFlag(GENERATE_ORE);
        Andradite.addFlag(GENERATE_ORE);
        Vermiculite.addFlag(GENERATE_ORE);
        Alunite.addFlag(GENERATE_ORE);
        GlauconiteSand.addFlag(GENERATE_ORE);
        Niter.addFlag(GENERATE_ORE);
        Duranium.addFlag(GENERATE_FINE_WIRE);

        Spodumene.addFlag(DISABLE_DECOMPOSITION);
        Lepidolite.addFlag(DISABLE_DECOMPOSITION);
        Chromite.addFlag(DISABLE_DECOMPOSITION);
        Barite.addFlag(DISABLE_DECOMPOSITION);
        VanadiumMagnetite.addFlag(DISABLE_DECOMPOSITION);
        Tritanium.addFlag(CORE_METAL);
        Boron.addFlag(GENERATE_ROD);
        TungstenCarbide.addFlag(DISABLE_DECOMPOSITION);
        YttriumBariumCuprate.addFlag(GENERATE_FINE_WIRE);
        Manganese.addFlag(GENERATE_FOIL);
        Naquadah.addFlag(GENERATE_FOIL);
        NaquadahEnriched.addFlag(GENERATE_FOIL);
        Duranium.addFlag(GENERATE_FOIL);
        Graphene.addFlag(GENERATE_FOIL);
        Polytetrafluoroethylene.addFlag(GENERATE_FOIL);
        Rubber.addFlag(GENERATE_FOIL);
        Polybenzimidazole.addFlag(GENERATE_FOIL);
        Polycaprolactam.addFlag(GENERATE_FOIL);
        Polystyrene.addFlag(GENERATE_FOIL);
        Plastic.addFlag(GENERATE_FOIL);
        ReinforcedEpoxyResin.addFlag(GENERATE_FINE_WIRE);
        Plutonium.addFlag(GENERATE_FINE_WIRE);
        Europium.addFlag(GENERATE_FINE_WIRE | GENERATE_LONG_ROD);
        Cerium.addFlag(GENERATE_FINE_WIRE);
        Arsenic.addFlag(SMELT_INTO_FLUID);
        Polonium.addFlag(SMELT_INTO_FLUID);
        Copernicium.addFlag(GENERATE_FLUID_BLOCK);

        //turbine component
        Cobalt.addFlag(GENERATE_BOLT_SCREW);
        Manganese.addFlag(GENERATE_BOLT_SCREW);
        Manganese.addFlag(GENERATE_DENSE);
        Molybdenum.addFlag(GENERATE_BOLT_SCREW);
        Neodymium.addFlag(GENERATE_BOLT_SCREW);


        GreenSapphire.addFlag(GENERATE_PLATE);
        GreenSapphire.addFlag(GENERATE_LENSE);
        Iron.addFlag(GENERATE_METAL_CASING);
        Tritanium.addFlag(GENERATE_FRAME);
        RedSteel.addFlag(GENERATE_GEAR);
        RedSteel.addFlag(GENERATE_METAL_CASING);
        Titanium.addFlag(GENERATE_METAL_CASING);
        StainlessSteel.addFlag(GENERATE_METAL_CASING);
        Steel.addFlag(GENERATE_METAL_CASING);
        TungstenSteel.addFlag(GENERATE_METAL_CASING);
        Aluminium.addFlag(GENERATE_METAL_CASING);
        Invar.addFlag(GENERATE_METAL_CASING);
        Lead.addFlag(GENERATE_METAL_CASING);
        BlackSteel.addFlag(GENERATE_METAL_CASING | GENERATE_GEAR);
        HSSG.addFlag(GENERATE_METAL_CASING);
        HSSS.addFlag(GENERATE_METAL_CASING);
        Naquadria.addFlag(GENERATE_METAL_CASING | GENERATE_DENSE | GENERATE_GEAR);
        Lead.addFlag(GENERATE_DENSE);
        StainlessSteel.addFlag(GENERATE_DENSE);

        Tritanium.addFlag(CORE_METAL);
        Duranium.addFlag(CORE_METAL);
        Apatite.addFlag(GENERATE_ROD);
        Iron.addFlag(GENERATE_LONG_ROD);
        Bronze.addFlag(GENERATE_LONG_ROD);
        Steel.addFlag(GENERATE_LONG_ROD);
        StainlessSteel.addFlag(GENERATE_LONG_ROD);

        Steel.addFlag(DISABLE_DECOMPOSITION);

        Rubber.addFlag(GENERATE_BOLT_SCREW);

        Plastic.addFlag(GENERATE_ROTOR);
        Apatite.addFlag(GENERATE_BOLT_SCREW);
        Salt.addOreByProducts(Borax);
        RockSalt.addOreByProducts(Borax);
        Lepidolite.addOreByProducts(Boron);
        Zirkelite.addOreByProducts(Thorium, Zirconium, Cerium);
        Caliche.addOreByProducts(Niter, Saltpeter, Lepidolite);
        Zircon.addOreByProducts(Cobalt, Lead, UraniumRadioactive.getMaterial());

        OrePrefix.block.setIgnored(Pyrotheum);
        OrePrefix.block.setIgnored(Cryotheum);
        OrePrefix.block.setIgnored(Snow);
        OrePrefix.block.setIgnored(MagnetoResonatic);
        OrePrefix.block.setIgnored(PotassiumDisulfate);
        OrePrefix.dust.setIgnored(Snow);
        OrePrefix.dustSmall.setIgnored(Snow);
        OrePrefix.dustTiny.setIgnored(Snow);

        Magnetite.setDirectSmelting(Iron);

        Duranium.addFlag(GENERATE_FOIL);
        Graphene.addFlag(GENERATE_FOIL);

        Thorium.addFlag(GENERATE_ROD);

        for (Material material : Material.MATERIAL_REGISTRY) {
            if (material instanceof IngotMaterial && material.hasFlag(GENERATE_METAL_CASING)) {
                material.addFlag(GENERATE_FRAME);
                material.addFlag(GENERATE_PLATE);
            }
            if (material instanceof IngotMaterial && ((IngotMaterial) material).toolSpeed > 0) {
                material.addFlag(GENERATE_DENSE);
            }
        }
    }

    public static void naqProcess() {
        EnrichedNaquadricCompound.addOreByProducts(NaquadricCompound, NaquadriaticCompound);
        NaquadricCompound.addOreByProducts(EnrichedNaquadricCompound);
        Naquadria.addFlag(DISABLE_REPLICATION);
        Naquadah.addFlag(DISABLE_REPLICATION);
        NaquadahEnriched.addFlag(DISABLE_REPLICATION);
        NaquadahAlloy.addFlag(DISABLE_DECOMPOSITION);
        ZPMSuperconductorBase.addFlag(DISABLE_DECOMPOSITION);
        UVSuperconductor.addFlag(DISABLE_DECOMPOSITION);
    }

    public static void goldProcess() {
        PreciousMetal.setOreMultiplier(3);

        Bornite.oreByProducts.clear();
        Bornite.addOreByProducts(Pyrite, Cobalt, Cadmium, PreciousMetal);

        Chalcopyrite.oreByProducts.clear();
        Chalcopyrite.addOreByProducts(Pyrite, Cobalt, Cadmium, PreciousMetal);

        Copper.oreByProducts.clear();
        Copper.addOreByProducts(Cobalt, PreciousMetal, Nickel);

        Glowstone.oreByProducts.clear();
        Glowstone.addOreByProducts(Redstone, PreciousMetal);

        Magnetite.oreByProducts.clear();
        Magnetite.addOreByProducts(Iron, PreciousMetal);
    }

    public static void platinumProcess() {
        OrePrefix.block.setIgnored(RutheniumTetroxide);
        PlatinumMetallicPowder.setOreMultiplier(2);
        PlatinumMetallicPowder.washedIn = SodiumPersulfate;
        PlatinumMetallicPowder.addOreByProducts(Nickel, IrLeachResidue, IrOsLeachResidue, PlatinumMetallicPowder);
        PalladiumMetallicPowder.setOreMultiplier(2);
        Nickel.oreByProducts.clear();
        Nickel.addOreByProducts(Cobalt, PlatinumMetallicPowder, Iron);
        Iridium.oreByProducts.clear();
        Iridium.addOreByProducts(PlatinumMetallicPowder, IrOsLeachResidue);
        Platinum.oreByProducts.clear();
        Platinum.addOreByProducts(Nickel, IrLeachResidue);
        Osmium.oreByProducts.clear();
        Osmium.addOreByProducts(IrLeachResidue);
        IrOsLeachResidue.addOreByProducts(IrLeachResidue, IrLeachResidue, IrLeachResidue, IrOsLeachResidue);
        IrOsLeachResidue.washedIn = SodiumPersulfate;
        IrLeachResidue.addOreByProducts(PlatinumMetallicPowder, IrOsLeachResidue);
        IrLeachResidue.washedIn = SodiumPersulfate;
    }


    public static void initNuclearMaterial() {
        Pitchblende.addFlag(DISABLE_DECOMPOSITION);
        Pitchblende.oreByProducts.clear();
        Pitchblende.addOreByProducts(Thorium, UraniumRadioactive.getMaterial(), Lead);
        OrePrefix.block.setIgnored(SodiumPotassiumAlloy);
        OrePrefix.block.setIgnored(FLiNaK);
        OrePrefix.block.setIgnored(FLiBe);
        OrePrefix.block.setIgnored(LeadBismuthEutectic);

        Uranium.addFlag(GENERATE_LONG_ROD);
        Plutonium.addFlag(GENERATE_LONG_ROD);

        ThoriumRadioactive.complexity = 100;
        Protactinium.complexity = 100;
        UraniumRadioactive.complexity = 100;
        Neptunium.complexity = 115;
        PlutoniumRadioactive.complexity = 120;
        AmericiumRadioactive.complexity = 135;
        Curium.complexity = 145;
        Berkelium.complexity = 150;
        Californium.complexity = 160;
        Einsteinium.complexity = 170;
        Fermium.complexity = 185;
        Mendelevium.complexity = 200;

        Thorium.addFlag(GENERATE_LONG_ROD);
        Uranium235.addFlag(GENERATE_LONG_ROD);

        Thorium232Isotope.fertile = true;
        Thorium232Isotope.isotopeDecay.put(Thorium233, 100);
        Thorium232Isotope.isotopeDecay.put(Protactinium233, 1000);
        Thorium232Isotope.isotopeDecay.put(Uranium233, 8900);

        Thorium233.isotopeDecay.put(Protactinium233, 9000);

        Protactinium233.isotopeDecay.put(Uranium233, 9000);

        //Uranium
        UraniumRadioactive.composition.put(Uranium238Isotope, 9890);
        UraniumRadioactive.composition.put(Uranium235Isotope, 100);
        UraniumRadioactive.composition.put(Uranium234, 10);

        Uranium233.fissile = true;
        Uranium235Isotope.fissile = true;
        Uranium234.fertile = true;
        Uranium238Isotope.fertile = true;

        Uranium235Isotope.baseHeat = 10;
        Uranium233.baseHeat = 7;

        Uranium234.isotopeDecay.put(Uranium235Isotope, 9000);
        Uranium238Isotope.isotopeDecay.put(Uranium239, 100);
        Uranium238Isotope.isotopeDecay.put(Neptunium239, 1000);
        Uranium238Isotope.isotopeDecay.put(Plutonium239, 8900);
        Uranium239.isotopeDecay.put(Neptunium239, 9000);


        //neptunium
        Neptunium.composition.put(Neptunium235, 2000);
        Neptunium.composition.put(Neptunium237, 5000);
        Neptunium.composition.put(Neptunium239, 3000);

        Neptunium237.fissile = true;
        Neptunium237.baseHeat = 11;

        Neptunium237.isotopeDecay.put(Protactinium233, 9000);
        Neptunium239.isotopeDecay.put(Plutonium239, 9000);
        Neptunium235.isotopeDecay.put(Uranium235Isotope, 9000);

        //plutonium
        PlutoniumRadioactive.composition.put(Plutonium244Isotope, 9890);
        PlutoniumRadioactive.composition.put(Plutonium241Isotope, 100);
        PlutoniumRadioactive.composition.put(Plutonium240, 10);

        Plutonium241Isotope.fissile = true;
        Plutonium239.fissile = true;
        Plutonium240.fertile = true;
        Plutonium244Isotope.fertile = true;

        Plutonium241Isotope.baseHeat = 13;
        Plutonium239.baseHeat = 10;

        Plutonium240.isotopeDecay.put(Plutonium241Isotope, 9000);
        Plutonium244Isotope.isotopeDecay.put(Plutonium245, 100);
        Plutonium244Isotope.isotopeDecay.put(Americium245, 1000);
        Plutonium244Isotope.isotopeDecay.put(Curium245, 8900);
        Plutonium245.isotopeDecay.put(Americium245, 9000);

        //Americium
        AmericiumRadioactive.composition.put(Americium241, 2000);
        AmericiumRadioactive.composition.put(Americium243, 5000);
        AmericiumRadioactive.composition.put(Americium245, 3000);

        Americium243.fissile = true;
        Americium243.baseHeat = 14;

        Americium243.isotopeDecay.put(Neptunium239, 9000);
        Americium245.isotopeDecay.put(Curium245, 9000);
        Americium241.isotopeDecay.put(Plutonium241Isotope, 9000);

        //Curium
        Curium.composition.put(Curium250, 9890);
        Curium.composition.put(Curium247, 100);
        Curium.composition.put(Curium246, 10);

        Curium245.fissile = true;
        Curium247.fissile = true;
        Curium246.fertile = true;
        Curium250.fertile = true;

        Curium245.baseHeat = 13;
        Curium247.baseHeat = 16;

        Curium246.isotopeDecay.put(Curium247, 9000);
        Curium250.isotopeDecay.put(Curium251, 100);
        Curium250.isotopeDecay.put(Berkelium251, 1000);
        Curium250.isotopeDecay.put(Californium251, 8900);
        Curium251.isotopeDecay.put(Americium245, 9000);

        //Berkelium
        Berkelium.composition.put(Berkelium247, 2000);
        Berkelium.composition.put(Berkelium249, 5000);
        Berkelium.composition.put(Berkelium251, 3000);

        Berkelium249.fissile = true;
        Berkelium249.baseHeat = 17;

        Berkelium249.isotopeDecay.put(Americium245, 9000);
        Berkelium251.isotopeDecay.put(Californium251, 9000);
        Berkelium247.isotopeDecay.put(Curium247, 9000);

        //Californium
        Californium.composition.put(Californium252, 9890);
        Californium.composition.put(Californium253, 100);
        Californium.composition.put(Californium256, 10);

        Californium251.fissile = true;
        Californium253.fissile = true;
        Californium252.fertile = true;
        Californium256.fertile = true;

        Californium251.baseHeat = 16;
        Californium253.baseHeat = 19;

        Californium252.isotopeDecay.put(Californium253, 9000);
        Californium256.isotopeDecay.put(Californium257, 100);
        Californium256.isotopeDecay.put(Einsteinium257, 1000);
        Californium256.isotopeDecay.put(Fermium257, 8900);
        Californium257.isotopeDecay.put(Einsteinium257, 9000);

        //Einsteinium
        Einsteinium.composition.put(Einsteinium253, 2000);
        Einsteinium.composition.put(Einsteinium255, 5000);
        Einsteinium.composition.put(Einsteinium257, 3000);

        Einsteinium255.fissile = true;
        Einsteinium255.baseHeat = 20;

        Einsteinium255.isotopeDecay.put(Berkelium251, 9000);
        Einsteinium257.isotopeDecay.put(Fermium257, 9000);
        Einsteinium253.isotopeDecay.put(Californium253, 9000);


        //Fermium
        Fermium.composition.put(Fermium258, 9890);
        Fermium.composition.put(Fermium259, 100);
        Fermium.composition.put(Fermium262, 10);

        Fermium257.fissile = true;
        Fermium259.fissile = true;
        Fermium258.fertile = true;
        Fermium262.fertile = true;

        Fermium257.baseHeat = 19;
        Fermium259.baseHeat = 22;

        Fermium258.isotopeDecay.put(Fermium259, 9000);
        Fermium262.isotopeDecay.put(Fermium263, 1000);
        Fermium262.isotopeDecay.put(Mendelevium263, 9000);
        Fermium263.isotopeDecay.put(Mendelevium263, 9000);

        //Mendelevium
        Mendelevium.composition.put(Mendelevium259, 2000);
        Mendelevium.composition.put(Mendelevium261, 5000);
        Mendelevium.composition.put(Mendelevium263, 3000);

        Mendelevium261.fissile = true;
        Mendelevium261.baseHeat = 23;
        Mendelevium261.isotopeDecay.put(Einsteinium257, 9000);
        Mendelevium259.isotopeDecay.put(Fermium259, 9000);

    }

    public static void ignoreCable(Material m) {
        if (m instanceof IngotMaterial && ((IngotMaterial) m).cableProperties != null) {
            OrePrefix.cableGtSingle.setIgnored(m);
            OrePrefix.cableGtDouble.setIgnored(m);
            OrePrefix.cableGtQuadruple.setIgnored(m);
            OrePrefix.cableGtOctal.setIgnored(m);
            OrePrefix.cableGtHex.setIgnored(m);
        }
    }

    public static void addCableAboveGTCELimit(Material m, WireProperties p) {

        if (m instanceof IngotMaterial) {
            ((IngotMaterial) m).setCableProperties(p.voltage, p.amperage, p.lossPerBlock);
        }
    }

    public static void setBlastFurnaceTemperature(IngotMaterial material, int temperature) {
        try {
            Field blastFurnaceTemperature = IngotMaterial.class.getField("blastFurnaceTemperature");
            blastFurnaceTemperature.setAccessible(true);

            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(blastFurnaceTemperature, blastFurnaceTemperature.getModifiers() & ~Modifier.FINAL);

            blastFurnaceTemperature.setInt(material, temperature);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            GALog.logger.error("setBlastFurnaceTemperature doesnt seems to works", e);
        }
    }

    public static void removeFlags(Material material, long flags) {
        if (!material.hasFlag(flags)) {
            return;
        }
        try {
            Field materialGenerationFlags = ObfuscationReflectionHelper.findField(Material.class, "materialGenerationFlags");
            materialGenerationFlags.setLong(material, materialGenerationFlags.getLong(material) ^ flags);
        } catch (IllegalAccessException e) {
            GALog.logger.error("Remove flags doesnt seems to works", e);
        }
    }

}
