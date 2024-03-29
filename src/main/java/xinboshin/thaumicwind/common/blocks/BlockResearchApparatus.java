package xinboshin.thaumicwind.common.blocks;

import javax.annotation.Nonnull;

import xinboshin.thaumicwind.api.front.IModelProvider;
import xinboshin.thaumicwind.client.render.block.RenderResearchApparatus;
import xinboshin.thaumicwind.common.blocks.base.BlockBase;
import xinboshin.thaumicwind.common.tiles.TileResearchApparatus;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockResearchApparatus extends BlockBase implements IModelProvider, ITileEntityProvider {

	private final boolean isSentinel;

	public BlockResearchApparatus(boolean isSentinel) {
		super(getName(isSentinel), getMaterial(isSentinel), getMapColor(isSentinel), false);
		this.isSentinel = isSentinel;
		setHardness(2F);
		setResistance(20F);
		setSoundType(getSoundType(isSentinel));
		setLightOpacity(0);
	}

	private static String getName(boolean isSentinel) {
		return isSentinel ? "research_table" : "pedestal";
	}

	private static Material getMaterial(boolean isSentinel) {
		return isSentinel ? Material.WOOD : Material.ROCK;
	}

	private static MapColor getMapColor(boolean isSentinel) {
		return isSentinel ? MapColor.WOOD : MapColor.STONE;
	}

	private SoundType getSoundType(boolean isSentinel) {
		return isSentinel ? SoundType.WOOD : SoundType.STONE;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileResearchApparatus(isSentinel);
	}

	@Override
	public void initModel() {
		IModelProvider.registerBlockModel(this, 0, "inventory");

		if (isSentinel) { // register the tile renderer one time
			IModelProvider.registerTileRenderer(TileResearchApparatus.class, new RenderResearchApparatus());
		}
	}

	private void dropItem(World world, BlockPos pos) {
		TileEntity te = world.getTileEntity(pos);

		if (te instanceof TileResearchApparatus) {
			TileResearchApparatus tile = (TileResearchApparatus) te;
			ItemStack stack = tile.getObservableStack();

			if (!stack.isEmpty()) {
				spawnItem(world, stack, pos.getX(), pos.getY() + 0.6, pos.getZ());
				tile.setObservableStack(ItemStack.EMPTY);
			}
		}
	}

	protected void spawnItem(World world, ItemStack stack, double x, double y, double z) {
		float f = world.rand.nextFloat() * 0.8F + 0.1F;
		float f1 = world.rand.nextFloat() * 0.8F + 0.1F;
		float f2 = world.rand.nextFloat() * 0.8F + 0.1F;
		EntityItem item = new EntityItem(world, x + f, y + f1, z + f2, stack.copy());
		item.motionX = world.rand.nextGaussian() * 0.05;
		item.motionY = world.rand.nextGaussian() * 0.05 + 0.2;
		item.motionZ = world.rand.nextGaussian() * 0.05;
		world.spawnEntity(item);
	}

	@Override
	public void breakBlock(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
		dropItem(world, pos);
		super.breakBlock(world, pos, state);
	}

	@Override
	public void onBlockClicked(World world, BlockPos pos, EntityPlayer player) {
		if (!world.isRemote) {
			dropItem(world, pos);
			IBlockState state = world.getBlockState(pos);
			world.notifyBlockUpdate(pos, state, state, 8);
		}
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			TileEntity te = world.getTileEntity(pos);

			if (!(te instanceof TileResearchApparatus)) {
				return true;
			}

			TileResearchApparatus tile = ((TileResearchApparatus) te);
			ItemStack item = tile.getObservableStack();
			ItemStack stack = player.getHeldItem(hand);

			if (stack.isEmpty() && !item.isEmpty()) {
				world.notifyBlockUpdate(pos, state, state, 8);
			} else if (!stack.isEmpty() && item.isEmpty()) {
				tile.setObservableStack(stack.splitStack(1));

				if (stack.getCount() <= 0) {
					player.setHeldItem(hand, ItemStack.EMPTY);
				}

				world.notifyBlockUpdate(pos, state, state, 8);
			}
		}

		return true;
	}
}
