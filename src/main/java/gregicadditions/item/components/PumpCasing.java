package gregicadditions.item.components;

import gregtech.common.blocks.VariantBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.IStringSerializable;

public class PumpCasing extends VariantBlock<PumpCasing.CasingType> {

    public PumpCasing() {
        super(Material.IRON);
        setTranslationKey("ga_pump_casing");
        setHardness(5.0f);
        setResistance(10.0f);
        setSoundType(SoundType.METAL);
        setHarvestLevel("wrench", 2);
        setDefaultState(getState(CasingType.PUMP_LV));
    }

    public enum CasingType implements IStringSerializable {
        PUMP_LV("pump_lv", 1),
        PUMP_MV("pump_mv", 2),
        PUMP_HV("pump_hv", 3),
        PUMP_EV("pump_ev", 4),
        PUMP_IV("pump_iv", 5),
        PUMP_LUV("pump_luv", 6),
        PUMP_ZPM("pump_zpm", 7),
        PUMP_UV("pump_uv", 8),
        PUMP_UHV("pump_uhv", 9),
        PUMP_UEV("pump_uev", 10),
        PUMP_UIV("pump_uiv", 11),
        PUMP_UMV("pump_umv", 12),
        PUMP_UXV("pump_uxv", 13),
        PUMP_MAX("pump_max", 14);

        private final String name;
        private final int tier;

        CasingType(String name, int tier) {
            this.name = name;
            this.tier = tier;
        }

        public int getTier() {
            return tier;
        }

        @Override
        public String getName() {
            return this.name;
        }
    }
}
