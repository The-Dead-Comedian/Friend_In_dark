package com.deadcomedian.friend.entity.custom;

import com.deadcomedian.friend.entity.ai.DwellerAttackGoal;
import com.deadcomedian.friend.entity.ai.DwellerDanceGoal;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;


public class DwellerEntity extends HostileEntity {


    ///////////////
    // VARIABLES //
    ///////////////


    int DanceCountdown = 0;
    int timesseen = 0;
    public int ticksLeft ;
    @Nullable
    private BlockPos songSource;
    private boolean songPlaying;
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState griddyAnimationState = new AnimationState();
    public final AnimationState gangamAnimationState = new AnimationState();


    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
    int RandomDanceNumber = getRandomNumber(1,3);
    private int idleAnimationTimeout = 0;
    public int attackAnimationTimeout = 0;

    //////////
    // MISC //
    //////////


    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ATTACKING, false);
        this.dataTracker.startTracking(DANCE, false);
    }
    public DwellerEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.ticksLeft =2400;
    }
    @Override
    public void tick() {
        super.tick();
        if(isPlayerStaring(MinecraftClient.getInstance().player)){
            timesseen++;
            this.teleportRandomly();
            System.out.println(timesseen);
        }





        ticksLeft--;
        if (ticksLeft < 0) {

            this.discard();
        }


        if(isSongPlaying()){
            DanceCountdown++;
            if(DanceCountdown == 30 && RandomDanceNumber == 2){
                RandomDanceNumber =  getRandomNumber(1,3);
                DanceCountdown=0;
            }else if (DanceCountdown == 10 && RandomDanceNumber == 1){
                RandomDanceNumber =  getRandomNumber(1,3);
                DanceCountdown=0;
            }



            if(this.getWorld().isClient()) {
                setupAnimationStates();
            }
        }
    }
    public void tickMovement() {
        if (this.songSource == null || !this.songSource.isWithinDistance(this.getPos(), 3.46) || !this.getWorld().getBlockState(this.songSource).isOf(Blocks.JUKEBOX)) {
            this.songPlaying = false;
            this.songSource = null;
        }
        if (this.getWorld().random.nextInt(400) == 0) {
            ParrotEntity.imitateNearbyMob(this.getWorld(), this);
        }
        super.tickMovement();
        this.setupAnimationStates();
    }
    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new DwellerAttackGoal(this, 1f, true));
        this.goalSelector.add(1, new DwellerDanceGoal(Blocks.JUKEBOX,this , 1f ,4));
        this.goalSelector.add(4, new WanderAroundFarGoal(this, 1D));
        this.goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 4f));
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.targetSelector.add(1, new ActiveTargetGoal(this, PlayerEntity.class, true));
    }
    public static DefaultAttributeContainer.Builder createDwellerAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 99999999)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2f)
                .add(EntityAttributes.GENERIC_ARMOR, 0.5f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2);
    }


    ///////////////
    // ANIMATION //
    ///////////////


    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0 && !this.isDancing()) {
            this.idleAnimationTimeout = this.random.nextInt(20) + 40;

            this.idleAnimationState.start(this.age);
            gangamAnimationState.stop();
            griddyAnimationState.stop();

        } else {
            --this.idleAnimationTimeout;
        }

        if(this.isAttacking() && attackAnimationTimeout <= 0) {
            attackAnimationTimeout = 40;
            attackAnimationState.startIfNotRunning(this.age);
            gangamAnimationState.stop();
            griddyAnimationState.stop();

        } else {
            --this.attackAnimationTimeout;
        }

        if(!this.isAttacking()) {
            attackAnimationState.stop();
        }
        DwellerDanceAnimation();

    }
    public void DwellerDanceAnimation(){
        if (RandomDanceNumber == 1 && this.isSongPlaying()){
            griddyAnimationState.startIfNotRunning(this.age);
            gangamAnimationState.stop();
            navigation.stop();
            idleAnimationState.stop();
        }else if(RandomDanceNumber == 2 && this.isSongPlaying()){
            gangamAnimationState.startIfNotRunning(this.age);
            griddyAnimationState.stop();
            navigation.stop();
            idleAnimationState.stop();
        }
        if(!this.isSongPlaying()){
            griddyAnimationState.stop();
            gangamAnimationState.stop();

        }

    }
    @Override
    protected void updateLimbs(float posDelta) {
        float f = this.getPose() == EntityPose.STANDING ? Math.min(posDelta * 6.0f, 1.0f) : 0.0f;
        this.limbAnimator.updateLimbs(f, 0.2f);
    }

    //////
    //AI//
    //////

        //  Dance

    private static final TrackedData<Boolean> DANCE =
            DataTracker.registerData(DwellerEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public void setDancing(boolean attacking) {
        this.dataTracker.set(DANCE, attacking);
    }
    public void setNearbySongPlaying(BlockPos songPosition, boolean playing) {
        this.songSource = songPosition;
        this.songPlaying = playing;
    }
    public boolean isSongPlaying() {
        return this.songPlaying;
    }
    public boolean isDancing() {
        return this.dataTracker.get(DANCE);
    }

        // Attacking

        private static final TrackedData<Boolean> ATTACKING =
                DataTracker.registerData(DwellerEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public void setAttacking(boolean attacking) {
        this.dataTracker.set(ATTACKING, attacking);
    }
    @Override
    public boolean isAttacking() {
        return this.dataTracker.get(ATTACKING);
    }
    public boolean tryAttack(Entity target){
        boolean bl = super.tryAttack(target);

        if(bl){
            float f = this.getWorld().getLocalDifficulty(this.getBlockPos()).getLocalDifficulty();
            if(this.getMainHandStack().isEmpty() && this.isOnFire() && this.random.nextFloat() < f * 0.3F){
                target.setOnFireFor(2 * (int)f);
            }
        }

        setAttacking(true);
        return bl;
    }

        // Anger

    boolean isPlayerStaring(PlayerEntity player) {

                if (player != null){
                    Vec3d vec3d = player.getRotationVec(1.0F).normalize();
                    Vec3d vec3d2 = new Vec3d(this.getX() - player.getX(), this.getEyeY() - player.getEyeY(), this.getZ() - player.getZ());
                    double d = vec3d2.length();
                    vec3d2 = vec3d2.normalize();
                    double e = vec3d.dotProduct(vec3d2);
                    return e > 1.0 - 0.025 / d ? player.canSee(this) : false;
                }
                else {
                return false;
                }

    }
    protected boolean teleportRandomly() {
        if (!this.getWorld().isClient() && this.isAlive()) {
            double d = this.getX() + (this.random.nextDouble() - 0.5) * 64.0;
            double e = this.getY() + (double)(this.random.nextInt(64) - 32);
            double f = this.getZ() + (this.random.nextDouble() - 0.5) * 64.0;
            return this.teleportTo(d, e, f);
        } else {
            return false;
        }
    }
    private boolean teleportTo(double x, double y, double z) {
        BlockPos.Mutable mutable = new BlockPos.Mutable(x, y, z);

        while(mutable.getY() > this.getWorld().getBottomY() && !this.getWorld().getBlockState(mutable).blocksMovement()) {
            mutable.move(Direction.DOWN);
        }

        BlockState blockState = this.getWorld().getBlockState(mutable);
        boolean bl = blockState.blocksMovement();
        boolean bl2 = blockState.getFluidState().isIn(FluidTags.WATER);
        if (bl && !bl2) {
            Vec3d vec3d = this.getPos();
            boolean bl3 = this.teleport(x, y, z, true);
            if (bl3) {
                this.getWorld().emitGameEvent(GameEvent.TELEPORT, vec3d, GameEvent.Emitter.of(this));
                if (!this.isSilent()) {
                    this.getWorld().playSound((PlayerEntity)null, this.prevX, this.prevY, this.prevZ, SoundEvents.ENTITY_ENDERMAN_TELEPORT, this.getSoundCategory(), 1.0F, 1.0F);
                    this.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, 1.0F, 1.0F);
                }
            }

            return bl3;
        } else {
            return false;
        }
    }

    //////////
    //SOUNDS//
    //////////


    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_FOX_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_CAT_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_DOLPHIN_DEATH;
    }
}

