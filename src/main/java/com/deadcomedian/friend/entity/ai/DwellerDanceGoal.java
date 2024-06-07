package com.deadcomedian.friend.entity.ai;

import com.deadcomedian.friend.entity.custom.DwellerEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.entity.ai.goal.StepAndDestroyBlockGoal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;


public class DwellerDanceGoal extends MoveToTargetPosGoal {

    private final DwellerEntity entity;

    private final Block targetBlock;
    private int counter;

    public DwellerDanceGoal(Block targetBlock, DwellerEntity mob, double speed, int maxYDifference) {
        super(mob, speed, 24, maxYDifference);
        this.targetBlock = targetBlock;
        this.entity = mob;
    }


    @Override
    public double getDesiredDistanceToTarget() {
        return 5.0;
    }

    @Override
    public boolean canStart() {
        if (this.findTargetPos()) {
            this.cooldown = StepAndDestroyBlockGoal.toGoalTicks(20);
            return true;
        }
        this.cooldown = this.getInterval(this.mob);
        return false;
    }

    @Override
    public void start() {
        super.start();
        this.counter = 0;
    }

    @Override
    public void stop() {
        super.stop();
        this.entity.fallDistance = 1.0f;
    }
    public void tickStepping(WorldAccess world, BlockPos pos) {
    }

    @Override
    protected BlockPos getTargetPos() {
        return this.targetPos;
    }

    @Override
    public void tick() {
        super.tick();

        World world = this.entity.getWorld();
        BlockPos blockPos = this.entity.getBlockPos();
        BlockPos blockpos2 = this.getTargetPos();
        System.out.println(blockPos);
        System.out.println(blockpos2);
        Random random = this.entity.getRandom();

        if(this.hasReached() && blockpos2 != null){
            double d;
            Vec3d vec3d;


            if (this.counter > 0) {
                vec3d = this.entity.getVelocity();
                this.entity.setVelocity(vec3d.x, 0.3, vec3d.z);
            }
        }


    }





    @Override
    protected boolean isTargetPos(WorldView world, BlockPos pos) {
        BlockState blockstate =world.getBlockState(pos);

        return blockstate.isOf(Blocks.JUKEBOX);
    }
}
