package gregicadditions.materials;

import gregtech.api.GTValues;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.info.MaterialIconSet;


import static gregicadditions.GAMaterials.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.*;
import static gregtech.api.unification.material.info.MaterialFlags.NO_SMELTING;

public class GAThirdDegreeMaterials {

    public static void register() {
        HotRutheniumTetroxideSolution = new Material.Builder(15500, "hot_ruthenium_tetroxide_solution")
                .fluid()
                .color(0xC7C7C7)
                .flags(DISABLE_DECOMPOSITION)
                .components(RutheniumTetroxideSolution, 1, Water, 1)
                .build();

        AcidicOsmiumSolution = new Material.Builder(15501,"acidic_osmium_solution")
                .fluid()
                .color(OsmiumTetroxideSolution.getMaterialRGB()-20)
                .flags(DISABLE_DECOMPOSITION)
                .components(OsmiumTetroxideSolution, 1, HydrochloricAcid, 1)
                .build();

        AcidicIridiumSolution = new Material.Builder(15502,"acidic_iridium_solution")
                .fluid()
                .color(IridiumDioxide.getMaterialRGB()-20)
                .flags(DISABLE_DECOMPOSITION)
                .components(IridiumDioxide, 1, HydrochloricAcid, 1)
                .build();

        MagnetoResonatic = new Material.Builder(15503, "magneto_resonatic")
                .gem(2)
                .color(0xFF97FF).iconSet(MaterialIconSet.MAGNETIC)
                .flags(DISABLE_DECOMPOSITION, FLAMMABLE , HIGH_SIFTER_OUTPUT, NO_SMELTING, GENERATE_LENS)
                .components(Prasiolite, 3, BismuthTelluride, 6, CubicZirconia, 1, SteelMagnetic, 1)
                .build();

        EglinSteel = new Material.Builder(15504, "eglin_steel")
                .ingot(6)
                .color(0x8B4513).iconSet(MaterialIconSet.METALLIC)
                .flags(GA_EXT2_METAL)
                .components(EglinSteelBase, 10, Sulfur, 1, Silicon, 1, Carbon, 1)
                .blastTemp(1048)
                .build();

        HastelloyX78 = new Material.Builder(15505, "hastelloy_x78")
                .ingot(2)
                .color(0x6BA3E3).iconSet(MaterialIconSet.SHINY)
                .flags(GA_EXT2_METAL, DISABLE_DECOMPOSITION)
                .components(NaquadahAlloy, 10, Rhenium, 5, Naquadria, 4, Gadolinium, 3, Strontium, 2, Polonium, 3, Rutherfordium, 2, Fermium258, 1)
                .blastTemp(12300)
                .build();

        TitanSteel = new Material.Builder(15506, "titan_steel")
                .ingot(7)
                .color(0xAA0D0D).iconSet(MaterialIconSet.SHINY)
                .flags(GA_CORE_METAL, DISABLE_DECOMPOSITION)
                .components(TungstenTitaniumCarbide, 3, Jasper, 3)
                .cableProperties(GTValues.V[GTValues.UEV], 2 ,16)
                .blastTemp(9200)
                .build();

        Cinobite = new Material.Builder(15507, "cinobite")
                .ingot(5)
                .color(0x010101).iconSet(MaterialIconSet.SHINY)
                .flags(GA_CORE_METAL, DISABLE_DECOMPOSITION)
                .components(Zeron100, 8, Naquadria, 4, Gadolinium, 3, Aluminium, 2, Mercury, 1, Tin, 1, Titanium, 6, Osmiridium, 1)
                .cableProperties(GTValues.V[GTValues.UIV], 4, 64)
                .blastTemp(11565)
                .build();

        IodineBrineMix = new Material.Builder(15508, "iodine_brine_mix")
                .fluid()
                .color(0x525242)
                .flags(DISABLE_DECOMPOSITION)
                .components(Iodine, 1, RareEarth, 1)
                .build();

        MetalHydroxideMix = new Material.Builder(15509, "metal_hydroxide_mix")
                .fluid()
                .color(Zinc.getMaterialRGB()-30)
                .flags(DISABLE_DECOMPOSITION)
                .components(Zinc, 1, Oxygen, 1, Hydrogen, 1, RareEarth, 1)
                .build();

        CaCBaSMixture = new Material.Builder(15510, "cacbas_mixture")
                .fluid()
                .colorAverage()
                .flags(DISABLE_DECOMPOSITION)
                .components(CalciumCarbonateSolution, 1, BariumSulfateSolution, 1)
                .build();

        LubricantClaySlurry = new Material.Builder(15511, "lubricant_clay_slurry")
                .fluid()
                .colorAverage()
                .flags(DISABLE_DECOMPOSITION)
                .components(Lubricant, 1, BentoniteClaySlurry, 1)
                .build();

        ATLEthylene = new Material.Builder(15512, "atl_ethylene_mixture")
                .fluid()
                .colorAverage()
                .flags(DISABLE_DECOMPOSITION)
                .components(ATL, 1, Ethylene, 1)
                .build();

        ErbiumDopedZBLANDust = new Material.Builder(15513, "erbium_doped_zblan_dust")
                .dust()
                .colorAverage().iconSet(MaterialIconSet.SHINY)
                .flags(DISABLE_DECOMPOSITION)
                .components(ZBLANDust, 1 , ErbiumTrifluoride, 1)
                .build();

        RoastedSpodumene = new Material.Builder(15514,"roasted_spodumene")
                .dust()
                .color(0x3d3d29)
                .flags(DISABLE_DECOMPOSITION)
                .components(Lithium, 1, Aluminium, 1, Silicon, 2, Oxygen, 6)
                .build();

        RoastedLepidolite = new Material.Builder(15515,"roasted_lepidolite")
                .dust()
                .color(0x470024)
                .flags(DISABLE_DECOMPOSITION)
                .components(Potassium, 1, Lithium, 3, Aluminium, 4, Oxygen, 8)
                .build();

        CassiteriteCokePellets = new Material.Builder(15516, "cassiterite_coke_pellets")
                .dust()
                .color(0x8f8f8f).iconSet(MaterialIconSet.ROUGH)
                .flags(DISABLE_DECOMPOSITION)
                .components(Cassiterite, 3, Coke, 1)
                .build();

        LanthanumFullereneNanotubes = new Material.Builder(15517, "lanthanum_fullerene_nanotubes")
                .dust()
                .color(LanthanumFullereneMix.getMaterialRGB()*3/5).iconSet(MaterialIconSet.SHINY)
                .flags(DISABLE_DECOMPOSITION)
                .components(Lanthanum, 2, Fullerene, 2 , CarbonNanotubes, 1)
                .build();

    }
}
