package dev.client.tenacity.utils.player;

import java.lang.reflect.Field;

import com.craftrise.b0;
import com.craftrise.b3;
import com.craftrise.gM;
import com.craftrise.lG;
import com.craftrise.lH;
import com.craftrise.pI;

import cr.launcher.BlockPos;
import cr.launcher.Config;
import cr.launcher.main.a;
import dev.client.tenacity.module.impl.movement.Scaffold;
import dev.utils.Utils;
import dev.utils.player.RotationUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class ScaffoldUtils implements Utils {

    public static class BlockCache {
        public BlockPos position;
        public lG facing;

        public BlockCache(final BlockPos position, final lG facing) {
            this.position = position;
            this.facing = facing;
        }

        public BlockPos getPosition() {
            return this.position;
        }

        private lG getFacing() {
            return this.facing;
        }

    }
    public static boolean isMoving(){
        return a.q != null && (a.q.l.b.a(5L) != 0f || a.q.l.c.a(5L) != 0f);
    }
    public static Vec3 getPositionVector() {
        return new Vec3(a.q.bE, a.q.aY, a.q.bH);
    }

    public static BlockCache grab() {
        final lG[] invert = {lG.UP, lG.DOWN, lG.SOUTH, lG.NORTH,
        		lG.EAST, lG.WEST};
        BlockPos position = new BlockPos(0, 0, 0);
        if (isMoving() && !Config.gameSettings.bO.a("Null")) {
            for (double n2 = Scaffold.extend.getValue() + 0.0001, n3 = 0.0; n3 <= n2; n3 += n2 / (Math.floor(n2))) {
                final BlockPos blockPos2 = new BlockPos(
                        a.q.bE - MathHelper.sin(RotationUtils.clampRotation()) * n3,
                        a.q.aY - 1.0,
                        a.q.bH + MathHelper.cos(RotationUtils.clampRotation()) * n3);
                final pI blockState = Config.getMinecraft().bu.getBlockState(blockPos2);
                if (blockState != null && blockState.a() == lH.A) {
                    position = blockPos2;
                    break;
                }
            }
        } else {
            position = new BlockPos(new BlockPos(getPositionVector().xCoord,
            		getPositionVector().yCoord, getPositionVector().zCoord))
                    .offset(lG.DOWN);
        }
        lG[] values;
        for (int length = (values = lG.values()).length, i = 0; i < length; i++) {
            final lG facing = values[i];
            final BlockPos offset = position.offset(facing);
                return new BlockCache(offset, invert[facing.ordinal()]);
        }
        final BlockPos[] offsets = {new BlockPos(-1, 0, 0), new BlockPos(1, 0, 0), new BlockPos(0, 0, -1),
                new BlockPos(0, 0, 1)};
        BlockPos[] array;
        for (int length2 = (array = offsets).length, j = 0; j < length2; ++j) {
            final BlockPos offset2 = array[j];
            final BlockPos offsetPos = position.add(offset2.d, 0, offset2.c);
                lG[] values2;
                for (int length3 = (values2 = lG.values()).length, k = 0; k < length3; ++k) {
                    final lG facing2 = values2[k];
                    final BlockPos offset3 = offsetPos.offset(facing2);
                        return new BlockCache(offset3, invert[facing2.ordinal()]);
                
            }

        }
        return null;
    }

    public static int grabBlockSlot() {
        int slot = -1;
        int highestStack = -1;
        boolean didGetHotbar = false;
        for (int i = 0; i < 9; ++i) {
            final gM itemStack = a.q.J.e[i];
            if (itemStack != null  && itemStack.a > 0) {
                if (a.q.J.e[i].a > highestStack && i < 9) {
                    highestStack = a.q.J.e[i].a;
                    slot = i;
                    if (slot == getLastHotbarSlot()) {
                        didGetHotbar = true;
                    }
                }
                if (i > 8 && !didGetHotbar) {
                    int hotbarNum = getFreeHotbarSlot();
                    if (hotbarNum != -1 && a.q.J.e[i].a > highestStack) {
                        highestStack = a.q.J.e[i].a;
                        slot = i;
                    }
                }
            }
        }
        if (slot > 8) {
            int hotbarNum = getFreeHotbarSlot();
            if (hotbarNum != -1) {
            	Config.getMinecraft().b.a(a.q.b1.i,slot,hotbarNum,2,a.q,5L);
            } else {
                return -1;
            }
        }
        return slot;
    }

    public static int getLastHotbarSlot() {
        int hotbarNum = -1;
        for (int k = 0; k < 9; k++) {
            final gM itemStack = a.q.J.e[k];
            if (itemStack != null && itemStack.a > 1) {
                hotbarNum = k;
                continue;
            }
        }
        return hotbarNum;
    }

    public static int getFreeHotbarSlot() {
        int hotbarNum = -1;
        for (int k = 0; k < 9; k++) {
            if (a.q.J.e[k] == null) {
                hotbarNum = k;
                continue;
            } else {
                hotbarNum = 7;
            }
        }
        return hotbarNum;
    }


    public static boolean canIItemBePlaced(b3 item) {
        if (getIdFromItem(item) == 116)
            return false;
        if (getIdFromItem(item) == 30)
            return false;
        if (getIdFromItem(item) == 31)
            return false;
        if (getIdFromItem(item) == 175)
            return false;
        if (getIdFromItem(item) == 28)
            return false;
        if (getIdFromItem(item) == 27)
            return false;
        if (getIdFromItem(item) == 66)
            return false;
        if (getIdFromItem(item) == 157)
            return false;
        if (getIdFromItem(item) == 31)
            return false;
        if (getIdFromItem(item) == 6)
            return false;
        if (getIdFromItem(item) == 31)
            return false;
        if (getIdFromItem(item) == 32)
            return false;
        if (getIdFromItem(item) == 140)
            return false;
        if (getIdFromItem(item) == 390)
            return false;
        if (getIdFromItem(item) == 37)
            return false;
        if (getIdFromItem(item) == 38)
            return false;
        if (getIdFromItem(item) == 39)
            return false;
        if (getIdFromItem(item) == 40)
            return false;
        if (getIdFromItem(item) == 69)
            return false;
        if (getIdFromItem(item) == 50)
            return false;
        if (getIdFromItem(item) == 75)
            return false;
        if (getIdFromItem(item) == 76)
            return false;
        if (getIdFromItem(item) == 54)
            return false;
        if (getIdFromItem(item) == 130)
            return false;
        if (getIdFromItem(item) == 146)
            return false;
        if (getIdFromItem(item) == 342)
            return false;
        if (getIdFromItem(item) == 12)
            return false;
        if (getIdFromItem(item) == 77)
            return false;
        if (getIdFromItem(item) == 143)
            return false;
        if (getIdFromItem(item) == 46)
            return false;
        return true;
    }

    public static Vec3 getHypixelVec3(BlockCache data) {
        BlockPos pos = data.position;
        lG face = data.facing;
        double x = (double) pos.d + 0.5, y = (double) pos.c + 0.5, z = (double) pos.a + 0.5;
        if (face != lG.UP && face != lG.DOWN) {
            y += 0.5;
        } else {
            x += 0.3;
            z += 0.3;
        }
        if (face == lG.WEST || face == lG.EAST) {
            z += 0.15;
        }
        if (face == lG.SOUTH || face == lG.NORTH) {
            x += 0.15;
        }
        return new Vec3(x, y, z);
    }
    public static int getIdFromItem(b3 itemIn)
    {
        return itemIn == null ? 0 : b3.n.a(itemIn);
    }

    public static boolean canItemBePlaced(b3 item) {
        if (getIdFromItem(item) == 116)
            return false;
        if (getIdFromItem(item) == 30)
            return false;
        if (getIdFromItem(item) == 31)
            return false;
        if (getIdFromItem(item) == 175)
            return false;
        if (getIdFromItem(item) == 28)
            return false;
        if (getIdFromItem(item) == 27)
            return false;
        if (getIdFromItem(item) == 66)
            return false;
        if (getIdFromItem(item) == 157)
            return false;
        if (getIdFromItem(item) == 31)
            return false;
        if (getIdFromItem(item) == 6)
            return false;
        if (getIdFromItem(item) == 31)
            return false;
        if (getIdFromItem(item) == 32)
            return false;
        if (getIdFromItem(item) == 140)
            return false;
        if (getIdFromItem(item) == 390)
            return false;
        if (getIdFromItem(item) == 37)
            return false;
        if (getIdFromItem(item) == 38)
            return false;
        if (getIdFromItem(item) == 39)
            return false;
        if (getIdFromItem(item) == 40)
            return false;
        if (getIdFromItem(item) == 69)
            return false;
        if (getIdFromItem(item) == 50)
            return false;
        if (getIdFromItem(item) == 75)
            return false;
        if (getIdFromItem(item) == 76)
            return false;
        if (getIdFromItem(item) == 54)
            return false;
        if (getIdFromItem(item) == 130)
            return false;
        if (getIdFromItem(item) == 146)
            return false;
        if (getIdFromItem(item) == 342)
            return false;
        if (getIdFromItem(item) == 12)
            return false;
        if (getIdFromItem(item) == 77)
            return false;
        if (getIdFromItem(item) == 143)
            return false;
        if (getIdFromItem(item) == 46)
            return false;
        if (getIdFromItem(item) == 145)
            return false;

        return true;
    }

}
