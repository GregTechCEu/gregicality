package gregicadditions.worldgen;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import exnihilocreatio.items.ore.Ore;
import gregicadditions.blocks.GABlockOre;
import gregicadditions.item.GAMetaBlocks;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.ore.StoneType;
import gregtech.api.unification.ore.StoneTypes;
import gregtech.api.util.GTUtility;
import gregtech.api.util.WorldBlockPredicate;
import gregtech.api.util.XSTR;
import gregtech.api.worldgen.config.FillerConfigUtils;
import gregtech.api.worldgen.config.OreConfigUtils;
import gregtech.api.worldgen.config.PredicateConfigUtils;
import gregtech.api.worldgen.filler.FillerEntry;
import gregtech.common.blocks.BlockOre;
import gregtech.common.blocks.MetaBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class GAFillerUtils {

    public static FillerEntry createBlockStateFiller(JsonElement element) {
        if (element instanceof JsonPrimitive) {
            String stringDeclaration = element.getAsString();
            return createSimpleFiller(stringDeclaration);

        } else if (element instanceof JsonObject) {
            JsonObject object = element.getAsJsonObject();
            if (object.has("block")) {
                IBlockState stateDefinition = PredicateConfigUtils.parseBlockStateDefinition(object);
                return FillerEntry.createSimpleFiller(stateDefinition);
            }
            Preconditions.checkArgument(object.has("type"), "Missing required type for block state predicate");
            String predicateType = object.get("type").getAsString();
            switch (predicateType) {
                case "weight_random":
                    return createWeightRandomStateFiller(object);
                case "state_match":
                    return createStateMatchFiller(object);
                default:
                    throw new IllegalArgumentException("Unknown filler match type: " + predicateType);
            }
        } else {
            throw new IllegalArgumentException("Unknown block state type " + element);
        }
    }

    private static FillerEntry createSimpleFiller(String stringDeclaration) {
        if (stringDeclaration.startsWith("block:")) {
            Block block = OreConfigUtils.getBlockByName(stringDeclaration.substring(6));
            return FillerEntry.createSimpleFiller(block.getDefaultState());

        } else if (stringDeclaration.startsWith("fluid:")) {
            String fluidName = stringDeclaration.substring(6);
            Fluid fluid = FluidRegistry.getFluid(fluidName);
            Preconditions.checkNotNull(fluid, "Fluid not found with name %s", fluidName);
            Preconditions.checkNotNull(fluid.getBlock(), "Block is not defined for fluid %s", fluidName);
            return FillerEntry.createSimpleFiller(fluid.getBlock().getDefaultState());

        } else if (stringDeclaration.startsWith("ore:")) {
            Map<StoneType, IBlockState> blockStateMap = getOreStateMap(stringDeclaration);
            return new OreFilterEntry(blockStateMap);

        } else if (stringDeclaration.startsWith("ore_dict:")) {
            String oreDictName = stringDeclaration.substring(9);
            IBlockState firstBlock = OreConfigUtils.getOreDictBlocks(oreDictName).get(0);
            return FillerEntry.createSimpleFiller(firstBlock);

        } else {
            throw new IllegalArgumentException("Unknown string block state declaration: " + stringDeclaration);
        }
    }

    public static Map<StoneType, IBlockState> getOreStateMap(String stringDeclaration) {
        DustMaterial material;
        String materialName;
        if (stringDeclaration.startsWith("ore:")) {
            if (stringDeclaration.contains("poor") || stringDeclaration.contains("rich") || stringDeclaration.contains("pure")) {
                String orePrefix = stringDeclaration.substring(4, 8);
                orePrefix = orePrefix.substring(0, 1).toUpperCase() + orePrefix.substring(1);
                materialName = stringDeclaration.substring(8).toLowerCase();
                material = OreConfigUtils.getMaterialByName(materialName);
                return getOreForMaterial(material, orePrefix);
            } else {
                materialName = stringDeclaration.substring(4);
                material = OreConfigUtils.getMaterialByName(materialName);
                return OreConfigUtils.getOreForMaterial(material);
            }
        } else {
            throw new IllegalArgumentException("Invalid string ore declaration: " + stringDeclaration);
        }
    }

    public static Map<StoneType, IBlockState> getOreForMaterial(DustMaterial material, String orePrefix) {
        List<GABlockOre> oreBlocks = GAMetaBlocks.GA_ORES.stream()
                .filter(ore -> ore.material == material && ore.getOrePrefix() == OrePrefix.valueOf("ore" + orePrefix))
                .collect(Collectors.toList());
        HashMap<StoneType, IBlockState> stoneTypeMap = new HashMap<>();
        for (BlockOre blockOre : oreBlocks) {
            for (StoneType stoneType : blockOre.STONE_TYPE.getAllowedValues()) {
                IBlockState blockState = blockOre.getOreBlock(stoneType);
                stoneTypeMap.put(stoneType, blockState);
            }
        }
        if (stoneTypeMap.isEmpty()) {
            throw new IllegalArgumentException("There is no ore generated for material " + material);
        }
        return stoneTypeMap;
    }


    private static FillerEntry createStateMatchFiller(JsonObject object) {
        JsonArray valuesArray = object.get("values").getAsJsonArray();
        JsonElement defaultElement = object.get("default");
        ArrayList<Pair<WorldBlockPredicate, FillerEntry>> matchers = new ArrayList<>();

        for (JsonElement valueDefinition : valuesArray) {
            Preconditions.checkArgument(valueDefinition.isJsonObject(), "Found invalid value definition: %s", valueDefinition.toString());
            JsonObject valueObject = valueDefinition.getAsJsonObject();
            WorldBlockPredicate predicate = PredicateConfigUtils.createBlockStatePredicate(valueObject.get("predicate"));
            FillerEntry filler = createBlockStateFiller(valueObject.get("value"));
            matchers.add(Pair.of(predicate, filler));
        }

        if (!defaultElement.isJsonNull()) {
            FillerEntry filler = createBlockStateFiller(defaultElement);
            WorldBlockPredicate predicate = (state, world, pos) -> true;
            matchers.add(Pair.of(predicate, filler));
        } else {
            WorldBlockPredicate predicate = (state, world, pos) -> true;
            FillerEntry fillerEntry = matchers.iterator().next().getRight();
            matchers.add(Pair.of(predicate, fillerEntry));
        }
        return new BlockStateMatcherEntry(matchers);
    }

    private static FillerEntry createWeightRandomStateFiller(JsonObject object) {
        JsonArray values = object.get("values").getAsJsonArray();
        ArrayList<Pair<Integer, FillerEntry>> randomList = new ArrayList<>();

        for (JsonElement randomElement : values) {
            JsonObject randomObject = randomElement.getAsJsonObject();
            int weight = randomObject.get("weight").getAsInt();
            Preconditions.checkArgument(weight > 0, "Invalid weight: %d", weight);
            FillerEntry filler = createBlockStateFiller(randomObject.get("value"));
            randomList.add(Pair.of(weight, filler));
        }

        return new WeightRandomMatcherEntry(randomList);
    }

    private static class OreFilterEntry implements FillerEntry {

        private final Map<StoneType, IBlockState> blockStateMap;
        private final ImmutableSet<IBlockState> allowedStates;
        private final StoneType defaultValue;

        public OreFilterEntry(Map<StoneType, IBlockState> blockStateMap) {
            this.blockStateMap = blockStateMap;
            this.defaultValue = blockStateMap.containsKey(StoneTypes.STONE) ? StoneTypes.STONE : blockStateMap.keySet().iterator().next();
            this.allowedStates = ImmutableSet.copyOf(blockStateMap.values());
        }

        @Override
        public IBlockState apply(IBlockState source, IBlockAccess blockAccess, BlockPos blockPos) {
            StoneType stoneType = StoneType.computeStoneType(source, blockAccess, blockPos);
            return blockStateMap.get(stoneType == null ? defaultValue : stoneType);
        }

        @Override
        public List<FillerEntry> getSubEntries() {
            return Collections.emptyList();
        }

        @Override
        public Set<IBlockState> getPossibleResults() {
            return allowedStates;
        }
    }

    private static class BlockStateMatcherEntry implements FillerEntry {

        private final List<Pair<WorldBlockPredicate, FillerEntry>> matchers;
        private final ImmutableList<FillerEntry> subEntries;
        private final ImmutableList<IBlockState> blockStates;

        public BlockStateMatcherEntry(List<Pair<WorldBlockPredicate, FillerEntry>> matchers) {
            this.matchers = matchers;
            ImmutableList.Builder<FillerEntry> entryBuilder = ImmutableList.builder();
            ImmutableList.Builder<IBlockState> stateBuilder = ImmutableList.builder();
            for (Pair<WorldBlockPredicate, FillerEntry> matcher : matchers) {
                entryBuilder.add(matcher.getRight());
                stateBuilder.addAll(matcher.getRight().getPossibleResults());
            }
            this.subEntries = entryBuilder.build();
            this.blockStates = stateBuilder.build();
        }

        @Override
        public IBlockState apply(IBlockState source, IBlockAccess blockAccess, BlockPos blockPos) {
            for (Pair<WorldBlockPredicate, FillerEntry> matcher : matchers) {
                if (matcher.getLeft().test(source, blockAccess, blockPos)) {
                    return matcher.getRight().apply(source, blockAccess, blockPos);
                }
            }
            return Blocks.AIR.getDefaultState();
        }

        @Override
        public List<FillerEntry> getSubEntries() {
            return subEntries;
        }

        @Override
        public Collection<IBlockState> getPossibleResults() {
            return blockStates;
        }
    }

    private static class WeightRandomMatcherEntry implements FillerEntry {

        private static final Random blockStateRandom = new XSTR();
        private final List<Pair<Integer, FillerEntry>> randomList;
        private final ImmutableList<FillerEntry> subEntries;
        private final ImmutableList<IBlockState> blockStates;

        public WeightRandomMatcherEntry(List<Pair<Integer, FillerEntry>> randomList) {
            this.randomList = randomList;
            ImmutableList.Builder<FillerEntry> entryBuilder = ImmutableList.builder();
            ImmutableList.Builder<IBlockState> stateBuilder = ImmutableList.builder();
            for (Pair<Integer, FillerEntry> randomEntry : randomList) {
                entryBuilder.add(randomEntry.getRight());
                stateBuilder.addAll(randomEntry.getRight().getPossibleResults());
            }
            this.subEntries = entryBuilder.build();
            this.blockStates = stateBuilder.build();
        }

        @Override
        public IBlockState apply(IBlockState source, IBlockAccess blockAccess, BlockPos blockPos) {
            int functionIndex = GTUtility.getRandomItem(blockStateRandom, randomList, randomList.size());
            FillerEntry randomFunction = randomList.get(functionIndex).getValue();
            return randomFunction.apply(source, blockAccess, blockPos);
        }

        @Override
        public List<FillerEntry> getSubEntries() {
            return subEntries;
        }

        @Override
        public Collection<IBlockState> getPossibleResults() {
            return blockStates;
        }
    }
}
