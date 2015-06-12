package sonar.calculator.mod.common.block.generators;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.api.IWrench;
import sonar.calculator.mod.common.tileentity.generators.TileEntityGenerator;
import sonar.calculator.mod.network.CalculatorGui;
import sonar.calculator.mod.utils.helpers.CalculatorHelper;
import sonar.core.common.block.SonarMachineBlock;
import sonar.core.utils.SonarMaterials;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;

public class RedstoneExtractor extends SonarMachineBlock implements IWrench  {
	private Random rand = new Random();
	private static boolean keepInventory;

	public RedstoneExtractor() {
		super(SonarMaterials.machine, false);
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean operateBlock(World world, int x, int y, int z,
			EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (player != null) {
			if (!world.isRemote) {
				player.openGui(Calculator.instance,
						CalculatorGui.RedstoneExtractor, world, x, y, z);
			}
		}
		return true;
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);
		TileEntityGenerator.RedstoneExtractor generator = (TileEntityGenerator.RedstoneExtractor) world
				.getTileEntity(x, y, z);
		generator.updateHandlers();
	}

	@Override
	public void onNeighborChange(IBlockAccess world, int x, int y, int z,
			int tileX, int tileY, int tileZ) {
			TileEntity tileentity = world.getTileEntity(x, y, z);
				if (tileentity != null	&& tileentity instanceof TileEntityGenerator.RedstoneExtractor) {
					TileEntityGenerator.RedstoneExtractor generator = (TileEntityGenerator.RedstoneExtractor) world.getTileEntity(x, y, z);
					generator.updateHandlers();
				}
			
	}
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block){
		TileEntity tileentity = world.getTileEntity(x, y, z);
		if (tileentity != null	&& tileentity instanceof TileEntityGenerator.RedstoneExtractor) {
			TileEntityGenerator.RedstoneExtractor generator = (TileEntityGenerator.RedstoneExtractor) world.getTileEntity(x, y, z);
			generator.updateHandlers();
		}
	}
	

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityGenerator.RedstoneExtractor();
	}

	@Override
	public boolean dropStandard(World world, int x, int y, int z) {
		return false;
	}

	@Override
	public void addSpecialToolTip(ItemStack stack, EntityPlayer player,
			List list) {
		CalculatorHelper.addEnergytoToolTip(stack, player, list);
		CalculatorHelper.addItemLevelToolTip(stack, player, list);
		
		
	}

	@Override
	public void standardInfo(ItemStack stack, EntityPlayer player, List list) {
		if (CalculatorConfig.energyStorageType == 2) {
			list.add(StatCollector.translateToLocal("energy.generate")+": "
					+ CalculatorConfig.redstoneRF / 4 + " EU/t");

		} else {
			list.add(StatCollector.translateToLocal("energy.generate")+": "
					+ CalculatorConfig.redstoneRF + " RF/t");
		}
	}
}