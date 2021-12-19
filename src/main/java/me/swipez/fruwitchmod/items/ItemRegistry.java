package me.swipez.fruwitchmod.items;

import me.swipez.fruwitchmod.FruWitchMod;
import me.swipez.fruwitchmod.items.staffs.StaffItem;
import me.swipez.fruwitchmod.items.staffs.UltimateStaffItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ItemRegistry {

    public static Item HEALING_STAFF = new StaffItem(new Item.Settings().maxCount(1));
    public static Item SPEED_STAFF = new StaffItem(new Item.Settings().maxCount(1));
    public static Item JUMP_STAFF = new StaffItem(new Item.Settings().maxCount(1));

    public static Item ULTIMATE_STAFF = new UltimateStaffItem(new Item.Settings().maxCount(1));

    public static Item HEALING_CRYSTAL = new Item(new Item.Settings());
    public static Item LEAPING_CRYSTAL = new Item(new Item.Settings());
    public static Item SWIFTNESS_CRYSTAL = new Item(new Item.Settings());

    public static void register(){
        Registry.register(Registry.ITEM, new Identifier(FruWitchMod.MOD_ID, "heal_staff"), HEALING_STAFF);
        Registry.register(Registry.ITEM, new Identifier(FruWitchMod.MOD_ID, "speed_staff"), SPEED_STAFF);
        Registry.register(Registry.ITEM, new Identifier(FruWitchMod.MOD_ID, "jump_staff"), JUMP_STAFF);

        Registry.register(Registry.ITEM, new Identifier(FruWitchMod.MOD_ID, "heal_crystal"), HEALING_CRYSTAL);
        Registry.register(Registry.ITEM, new Identifier(FruWitchMod.MOD_ID, "speed_crystal"), SWIFTNESS_CRYSTAL);
        Registry.register(Registry.ITEM, new Identifier(FruWitchMod.MOD_ID, "jump_crystal"), LEAPING_CRYSTAL);

        Registry.register(Registry.ITEM, new Identifier(FruWitchMod.MOD_ID, "ultimate_staff"), ULTIMATE_STAFF);
    }
}
