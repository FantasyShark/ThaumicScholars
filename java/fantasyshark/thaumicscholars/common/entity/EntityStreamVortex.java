package fantasyshark.thaumicscholars.common.entity;

import fantasyshark.thaumicscholars.ThaumicScholars;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import thaumcraft.client.fx.FXDispatcher;
import thaumcraft.common.lib.utils.EntityUtils;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;


public class EntityStreamVortex extends Entity {

    public static final String ID = "stream_vortex";
    public static final String NAME = ThaumicScholars.MODID + ".StreamVortex";

    private int duration;
    private EntityLivingBase owner;
    private UUID ownerUniqueId;

    public EntityStreamVortex(World worldIn) {
        super(worldIn);
    }

    public EntityStreamVortex(World worldIn, EntityLivingBase entityIn, int dur) {

        super(worldIn);
        this.setOwner(entityIn);
        this.setSize(0.15F, 0.15F);
        this.setPosition(entityIn.posX, entityIn.posY, entityIn.posZ);
        this.setDuration(dur);
    }

    public void entityInit() {
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int durationIn) {
        this.duration = durationIn;
    }

    public void setOwner (@Nullable EntityLivingBase ownerIn) {

        this.owner = ownerIn;
        this.ownerUniqueId = ownerIn == null ? null : ownerIn.getUniqueID();
    }

    @Nullable
    public EntityLivingBase getOwner() {

        if (this.owner == null && this.ownerUniqueId != null && this.world instanceof WorldServer) {

            Entity entity = ((WorldServer)this.world).getEntityFromUuid(this.ownerUniqueId);
            if (entity instanceof EntityLivingBase) {

                this.owner = (EntityLivingBase)entity;
            }
        }

        return this.owner;
    }

    public void writeEntityToNBT(NBTTagCompound nbt) {

        nbt.setInteger("Age", this.ticksExisted);
        nbt.setInteger("Duration", this.duration);
    }

    public void readEntityFromNBT(NBTTagCompound nbt) {

        this.ticksExisted = nbt.getInteger("Age");
        this.duration = nbt.getInteger("Duration");
    }

    public void onUpdate() {

        super.onUpdate();
        int count = 0;
        if(!this.world.isRemote && (this.ticksExisted > 5 * 20 || this.getOwner() == null)) {
            this.setDead();
        }

        if (this.isEntityAlive()) {
            if (this.world.isRemote) {

                List<EntityItem> stuff = EntityUtils.getEntitiesInRange(this.world, this.posX, this.posY, this.posZ, this, EntityItem.class, 10.0D);
                List<Entity> targets = this.world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(this.posX - 10, this.posY - 10, this.posZ - 10, this.posX + 10, this.posY + 10, this.posZ + 10));
                if (stuff != null && stuff.size() > 0) {
                    Iterator var5 = stuff.iterator();

                    while (var5.hasNext()) {
                        EntityItem e = (EntityItem) var5.next();
                        if (!e.isDead) {
                            double d6 = e.posX - this.posX;
                            double d8 = e.posY - this.posY + (double) (this.height / 2.0F);
                            double d10 = e.posZ - this.posZ;
                            double d11 = (double) MathHelper.sqrt(d6 * d6 + d8 * d8 + d10 * d10);
                            d6 /= d11;
                            d8 /= d11;
                            d10 /= d11;
                            double d13 = 0.3D;
                            e.motionX -= d6 * d13;
                            e.motionY -= d8 * d13 - 0.1D;
                            e.motionZ -= d10 * d13;
                            if (e.motionX > 0.25D) {
                                e.motionX = 0.25D;
                            }

                            if (e.motionX < -0.25D) {
                                e.motionX = -0.25D;
                            }

                            if (e.motionY > 0.25D) {
                                e.motionY = 0.25D;
                            }

                            if (e.motionY < -0.25D) {
                                e.motionY = -0.25D;
                            }

                            if (e.motionZ > 0.25D) {
                                e.motionZ = 0.25D;
                            }

                            if (e.motionZ < -0.25D) {
                                e.motionZ = -0.25D;
                            }

                            if (this.world.isRemote) {
                                FXDispatcher.INSTANCE.crucibleBubble((float) e.posX + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F, (float) e.posY + e.height + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F, (float) e.posZ + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F, 0.33F, 0.33F, 1.0F);
                            }
                        }
                    }
                }
                if (!this.world.isRemote) {
                    if (targets.size() > 0) {
                        for (count = 0; count < targets.size(); ++count) {
                            final Entity entity = targets.get(count);
                            if (!(entity == this) && entity instanceof EntityLivingBase) {
                                final Vec3d p = new Vec3d(this.posX, this.posY, this.posZ);
                                final Vec3d t = new Vec3d(entity.posX, entity.posY, entity.posZ);
                                final double distance = p.distanceTo(t) + 0.1;
                                final Vec3d r = new Vec3d(t.x - p.x, t.y - p.y, t.z - p.z);
                                final Entity entity2 = entity;
                                entity2.motionX -= r.x / 10.0 / distance;
                                final Entity entity3 = entity;
                                entity3.motionY -= r.y / 5.0 / distance;
                                final Entity entity4 = entity;
                                entity4.motionZ -= r.z / 10.0 / distance;

                                FXDispatcher.INSTANCE.crucibleBubble((float) entity.posX + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F, (float) entity.posY + entity.height + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F, (float) entity.posZ + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F, 0.33F, 0.33F, 1.0F);
                            }


                        }
                    }

                }
            }
        }
    }
}
