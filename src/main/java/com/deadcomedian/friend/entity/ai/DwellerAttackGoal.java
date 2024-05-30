package com.deadcomedian.friend.entity.ai;

import com.deadcomedian.friend.entity.custom.DwellerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.Hand;

public class DwellerAttackGoal extends MeleeAttackGoal {
    private final DwellerEntity entity;
    private int attackDelay = 20;
    private int ticksUntilNextAttack = 20;
    private boolean shouldCountTillNextAttack = false;

    public DwellerAttackGoal(PathAwareEntity mob, double speed, boolean pauseWhenMobIdle) {
        super(mob, speed, pauseWhenMobIdle);
        entity = ((DwellerEntity) mob);
    }

    @Override
    public void start() {
        super.start();
        attackDelay = 20;
        ticksUntilNextAttack = 20;
    }


    protected void attack(LivingEntity pEnemy) {
        if (isEnemyWithinAttackDistance(pEnemy)) {
            shouldCountTillNextAttack = true;

            if(isTimeToAttack()) {
                this.mob.getLookControl().lookAt(pEnemy.getX(), pEnemy.getEyeY(), pEnemy.getZ());
                performAttack(pEnemy);
            }
        } else {
            resetAttackCooldown();
            shouldCountTillNextAttack = false;
            entity.setAttacking(false);
            entity.attackAnimationTimeout = 0;
        }
    }

    private boolean isEnemyWithinAttackDistance(LivingEntity pEnemy) {
        return this.entity.distanceTo(pEnemy) <= 2f; // TODO
    }

    protected void resetAttackCooldown() {
        this.ticksUntilNextAttack = this.getTickCount(attackDelay * 2);
    }

    protected boolean isTimeToAttack() {
        return this.ticksUntilNextAttack <= 0;
    }

    protected void performAttack(LivingEntity pEnemy) {
        entity.setAttacking(true);
        this.resetAttackCooldown();
        this.mob.swingHand(Hand.MAIN_HAND);
        this.mob.tryAttack(pEnemy);
    }

    @Override
    public void tick() {
        super.tick();
        if(shouldCountTillNextAttack) {
            this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);
            if(ticksUntilNextAttack == 0) {
                entity.setAttacking(false);
            }
        }
    }

    @Override
    public void stop() {
        entity.setAttacking(false);
        super.stop();
    }
}