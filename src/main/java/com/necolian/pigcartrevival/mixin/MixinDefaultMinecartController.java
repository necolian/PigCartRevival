package com.necolian.pigcartrevival.mixin;

import com.necolian.pigcartrevival.IMixinDefaultMinecartController;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.entity.vehicle.DefaultMinecartController;
import net.minecraft.entity.vehicle.MinecartController;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DefaultMinecartController.class)
public abstract class MixinDefaultMinecartController extends MinecartController implements IMixinDefaultMinecartController {

    @Unique
    private double dx;
    @Unique
    private double dz;
    @Unique
    private boolean move = false;

    protected MixinDefaultMinecartController(AbstractMinecartEntity minecart) {
        super(minecart);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void onTick(CallbackInfo ci) {
        if (!move) return;

        Vec3d velocity = this.getVelocity();
        double horizontalSpeed = velocity.horizontalLength();

        Vec3d inputVec = new Vec3d(dx, 0, dz);
        double boost = 0.03;
        double maxSpeed = 0.8;

        if (horizontalSpeed > 0) {
            // 現在の水平速度ベクトル
            Vec3d horizontalVel = new Vec3d(velocity.x, 0, velocity.z);

            // 内積で方向確認
            double dot = horizontalVel.dotProduct(inputVec);

            if (dot < 0) {
                // 逆方向 → 減速
                double decel = boost * 2; // 減速は加速より速くできる
                double newSpeed = horizontalSpeed - decel;
                if (newSpeed < 0) newSpeed = 0;

                // 速度ベクトルを縮小
                Vec3d newVel = horizontalVel.normalize().multiply(newSpeed);
                this.setVelocity(new Vec3d(newVel.x, velocity.y, newVel.z));
                return;
            }
        }

// 同方向または停止中 → 通常加速
        double newX = velocity.x + dx * boost;
        double newZ = velocity.z + dz * boost;
        double speed = Math.sqrt(newX * newX + newZ * newZ);
        if (speed > maxSpeed) {
            double scale = maxSpeed / speed;
            newX *= scale;
            newZ *= scale;
        }
        this.setVelocity(new Vec3d(newX, velocity.y, newZ));

        move = false;
    }

    @Override
    public void pigCartRevival$setPigCartDirection(double dx, double dz) {
        this.dx = dx;
        this.dz = dz;
        this.move = true;
    }

}
