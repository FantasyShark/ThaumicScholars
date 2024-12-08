package fantasyshark.thaumicscholars.common.entity.foci;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.casters.FocusEffect;
import thaumcraft.api.casters.FocusEngine;
import thaumcraft.api.casters.FocusPackage;
import thaumcraft.api.casters.Trajectory;
import thaumcraft.common.entities.monster.EntitySpellBat;
import thaumcraft.common.lib.utils.EntityUtils;
import thaumcraft.common.lib.utils.Utils;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class EntitySpellGolem extends EntityMob implements IEntityAdditionalSpawnData {

    private BlockPos currentFlightTarget;
    public EntityLivingBase owner = null;
    FocusPackage focusPackage;
    private UUID ownerUniqueId;
    private static final DataParameter<Boolean> FRIENDLY;
    public int damBonus = 0;
    private int attackTime;
    FocusEffect[] effects = null;
    public int color = 16777215;

    public EntitySpellGolem(World world) {
        super(world);
        this.setSize(0.5F, 0.9F);
    }

    public EntitySpellGolem(FocusPackage pac, boolean friendly) {
        super(pac.world);
        this.setSize(0.5F, 0.9F);
        this.focusPackage = pac;
        this.setOwner(pac.getCaster());
        this.setIsFriendly(friendly);
    }

    public void entityInit() {
        super.entityInit();
        this.getDataManager().register(FRIENDLY, false);
    }

    public boolean getIsFriendly() {
        return ((Boolean)this.getDataManager().get(FRIENDLY)).booleanValue();
    }

    public void setIsFriendly(boolean par1) {
        this.getDataManager().set(FRIENDLY, par1);
    }

    public void writeSpawnData(ByteBuf data) {
        Utils.writeNBTTagCompoundToBuffer(data, this.focusPackage.serialize());
    }

    public void readSpawnData(ByteBuf data) {
        try {
            this.focusPackage = new FocusPackage();
            this.focusPackage.deserialize(Utils.readNBTTagCompoundFromBuffer(data));
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    public void setOwner(@Nullable EntityLivingBase ownerIn) {
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

    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender() {
        return 15728880;
    }

    public float getBrightness() {
        return 1.0F;
    }

    protected float getSoundVolume() {
        return 0.1F;
    }

    protected float getSoundPitch() {
        return super.getSoundPitch() * 0.95F;
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_BAT_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_BAT_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_BAT_DEATH;
    }

    public boolean canBePushed() {
        return false;
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
    }

    public Team getTeam() {
        EntityLivingBase entitylivingbase = this.getOwner();
        return entitylivingbase != null ? entitylivingbase.getTeam() : super.getTeam();
    }

    public boolean isOnSameTeam(Entity otherEntity) {
        EntityLivingBase owner = this.getOwner();
        if (otherEntity == owner) {
            return true;
        } else if (owner == null) {
            return super.isOnSameTeam(otherEntity);
        } else {
            return owner.isOnSameTeam(otherEntity) || otherEntity.isOnSameTeam(owner);
        }
    }

    public void onUpdate() {
        super.onUpdate();
        if (!this.world.isRemote && (this.ticksExisted > 600 || this.getOwner() == null)) {
            this.setDead();
        }

        this.motionY *= 0.6000000238418579D;
        if (this.isEntityAlive() && this.world.isRemote) {
            if (this.effects == null) {
                this.effects = this.focusPackage.getFocusEffects();
                int r = 0;
                int g = 0;
                int b = 0;
                FocusEffect[] var4 = this.effects;
                int var5 = var4.length;

                for(int var6 = 0; var6 < var5; ++var6) {
                    FocusEffect ef = var4[var6];
                    Color c = new Color(FocusEngine.getElementColor(ef.getKey()));
                    r += c.getRed();
                    g += c.getGreen();
                    b += c.getBlue();
                }

                r /= this.effects.length;
                g /= this.effects.length;
                b /= this.effects.length;
                Color c = new Color(r, g, b);
                this.color = c.getRGB();
            }

            if (this.effects != null && this.effects.length > 0) {
                FocusEffect eff = this.effects[this.rand.nextInt(this.effects.length)];
                eff.renderParticleFX(this.world, this.posX + this.world.rand.nextGaussian() * 0.125D, this.posY + (double)(this.height / 2.0F) + this.world.rand.nextGaussian() * 0.125D, this.posZ + this.world.rand.nextGaussian() * 0.125D, 0.0D, 0.0D, 0.0D);
            }
        }

    }

    protected void updateAITasks() {
        super.updateAITasks();
        if (this.attackTime > 0) {
            --this.attackTime;
        }

        BlockPos blockpos = new BlockPos(this);
        BlockPos blockpos1 = blockpos.up();
        double var1;
        double var3;
        double var5;
        float var7;
        float var8;
        if (this.getAttackTarget() == null) {
            if (this.currentFlightTarget != null && (!this.world.isAirBlock(this.currentFlightTarget) || this.currentFlightTarget.getY() < 1)) {
                this.currentFlightTarget = null;
            }

            if (this.currentFlightTarget == null || this.rand.nextInt(30) == 0 || this.getDistanceSqToCenter(this.currentFlightTarget) < 4.0D) {
                this.currentFlightTarget = new BlockPos((int)this.posX + this.rand.nextInt(7) - this.rand.nextInt(7), (int)this.posY + this.rand.nextInt(6) - 2, (int)this.posZ + this.rand.nextInt(7) - this.rand.nextInt(7));
            }

            var1 = (double)this.currentFlightTarget.getX() + 0.5D - this.posX;
            var3 = (double)this.currentFlightTarget.getY() + 0.1D - this.posY;
            var5 = (double)this.currentFlightTarget.getZ() + 0.5D - this.posZ;
            this.motionX += (Math.signum(var1) * 0.5D - this.motionX) * 0.10000000149011612D;
            this.motionY += (Math.signum(var3) * 0.699999988079071D - this.motionY) * 0.10000000149011612D;
            this.motionZ += (Math.signum(var5) * 0.5D - this.motionZ) * 0.10000000149011612D;
            var7 = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0D / 3.141592653589793D) - 90.0F;
            var8 = MathHelper.wrapDegrees(var7 - this.rotationYaw);
            this.moveForward = 0.5F;
            this.rotationYaw += var8;
        } else {
            var1 = this.getAttackTarget().posX - this.posX;
            var3 = this.getAttackTarget().posY + (double)(this.getAttackTarget().getEyeHeight() * 0.66F) - this.posY;
            var5 = this.getAttackTarget().posZ - this.posZ;
            this.motionX += (Math.signum(var1) * 0.5D - this.motionX) * 0.10000000149011612D;
            this.motionY += (Math.signum(var3) * 0.699999988079071D - this.motionY) * 0.10000000149011612D;
            this.motionZ += (Math.signum(var5) * 0.5D - this.motionZ) * 0.10000000149011612D;
            var7 = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0D / 3.141592653589793D) - 90.0F;
            var8 = MathHelper.wrapDegrees(var7 - this.rotationYaw);
            this.moveForward = 0.5F;
            this.rotationYaw += var8;
        }

        if (this.getAttackTarget() == null) {
            this.setAttackTarget(this.findTargetToAttack());
        } else if (this.getAttackTarget().isEntityAlive()) {
            float f = this.getAttackTarget().getDistance(this);
            if (this.isEntityAlive() && this.canEntityBeSeen(this.getAttackTarget())) {
                this.attackEntity(this.getAttackTarget(), f);
            }
        } else {
            this.setAttackTarget((EntityLivingBase)null);
        }

        if (!this.getIsFriendly() && this.getAttackTarget() instanceof EntityPlayer && ((EntityPlayer)this.getAttackTarget()).capabilities.disableDamage) {
            this.setAttackTarget((EntityLivingBase)null);
        }

    }

    protected boolean canTriggerWalking() {
        return false;
    }

    public void fall(float par1, float damageMultiplier) {
    }

    protected void updateFallState(double p_180433_1_, boolean p_180433_3_, IBlockState state, BlockPos pos) {
    }

    public boolean doesEntityNotTriggerPressurePlate() {
        return true;
    }

    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        return super.attackEntityFrom(par1DamageSource, par2);
    }

    protected void attackEntity(Entity target, float par2) {
        if (this.attackTime <= 0 && par2 < Math.max(2.5F, target.width * 1.1F) && target.getEntityBoundingBox().maxY > this.getEntityBoundingBox().minY && target.getEntityBoundingBox().minY < this.getEntityBoundingBox().maxY) {
            this.attackTime = 40;
            if (!this.world.isRemote) {
                RayTraceResult ray = new RayTraceResult(target);
                ray.hitVec = target.getPositionVector().addVector(0.0D, (double)(target.height / 2.0F), 0.0D);
                Trajectory tra = new Trajectory(this.getPositionVector(), this.getPositionVector().subtractReverse(ray.hitVec));
                FocusEngine.runFocusPackage(this.focusPackage.copy(this.getOwner()), new Trajectory[]{tra}, new RayTraceResult[]{ray});
                this.setHealth(this.getHealth() - 1.0F);
            }

            this.playSound(SoundEvents.ENTITY_BAT_HURT, 0.5F, 0.9F + this.world.rand.nextFloat() * 0.2F);
        }

    }

    protected void collideWithEntity(Entity entityIn) {
        if (!this.getIsFriendly()) {
            super.collideWithEntity(entityIn);
        }
    }

    protected EntityLivingBase findTargetToAttack() {
        double var1 = 12.0D;
        List<EntityLivingBase> list = EntityUtils.getEntitiesInRange(this.world, this.posX, this.posY, this.posZ, this, EntityLivingBase.class, var1);
        double d = 1.7976931348623157E308D;
        EntityLivingBase ret = null;
        Iterator var7 = list.iterator();

        while(true) {
            EntityLivingBase e;
            while(true) {
                do {
                    if (!var7.hasNext()) {
                        return ret;
                    }

                    e = (EntityLivingBase)var7.next();
                } while(e.isDead);

                if (this.getIsFriendly()) {
                    if (!EntityUtils.isFriendly(this.getOwner(), e)) {
                        continue;
                    }
                    break;
                } else if (!EntityUtils.isFriendly(this.getOwner(), e) && !this.isOnSameTeam(e)) {
                    break;
                }
            }

            double ed = this.getDistanceSq(e);
            if (ed < d) {
                d = ed;
                ret = e;
            }
        }
    }

    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.ownerUniqueId = nbt.getUniqueId("OwnerUUID");
        this.setIsFriendly(nbt.getBoolean("friendly"));

        try {
            this.focusPackage = new FocusPackage();
            this.focusPackage.deserialize(nbt.getCompoundTag("pack"));
        } catch (Exception var3) {
            ;
        }

    }

    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        if (this.ownerUniqueId != null) {
            nbt.setUniqueId("OwnerUUID", this.ownerUniqueId);
        }

        nbt.setTag("pack", this.focusPackage.serialize());
        nbt.setBoolean("friendly", this.getIsFriendly());
    }

    public boolean getCanSpawnHere() {
        int i = MathHelper.floor(this.posX);
        int j = MathHelper.floor(this.getEntityBoundingBox().minY);
        int k = MathHelper.floor(this.posZ);
        BlockPos blockpos = new BlockPos(i, j, k);
        int var4 = this.world.getLight(blockpos);
        byte var5 = 7;
        return var4 > this.rand.nextInt(var5) ? false : super.getCanSpawnHere();
    }

    protected boolean canDropLoot() {
        return false;
    }

    protected boolean isValidLightLevel() {
        return true;
    }

    static {
        FRIENDLY = EntityDataManager.createKey(EntitySpellBat.class, DataSerializers.BOOLEAN);
    }

}
