/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2024 Evan Debenham
 * 
 * Elektrocheckers Pixel Dungeon
 * Copyright (C) 2023-2024 Timon Lilje
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.shatteredpixel.shatteredpixeldungeon.items.ancientrunes;

import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.Transmuting;
import com.shatteredpixel.shatteredpixeldungeon.items.EquipableItem;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.KindOfWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.Armor;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.Armor.Glyph;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.Pickaxe;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.Wand;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon.Enchantment;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MagesStaff;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.shatteredpixeldungeon.journal.Catalog;
import com.shatteredpixel.shatteredpixeldungeon.levels.MiningLevel;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.utils.Random;

public class NablaAncientRune extends AncientRuneInventory {

	{
		image = ItemSpriteSheet.ANCIENTRUNE_NABLA;
	}

	// 20% common, 30% uncommon, 50% rare enchantment or glyph
	private static float[] enchantmentChances = {20,30,50};

	@Override
	protected boolean usableOnItem(Item item) {
		// all melee weapons, except pickaxe when in a mining level
		if (item instanceof MeleeWeapon) {
			return !(item instanceof Pickaxe && Dungeon.level instanceof MiningLevel);
		} else if (item instanceof Armor) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	protected void onItemSelected(Item item) {
		item.detach(curUser.belongings.backpack);

		Item result = changeItem(item);

		int slot = Dungeon.quickslot.getSlot(item);
		if (item.isEquipped(Dungeon.hero)) {
			item.cursed = false; // to allow it to be unequipped

			if (item instanceof KindOfWeapon && Dungeon.hero.belongings.secondWep() == item) {
				((EquipableItem) item).doUnequip(Dungeon.hero, false);
				((KindOfWeapon) result).equipSecondary(Dungeon.hero);
			} else {
				((EquipableItem) item).doUnequip(Dungeon.hero, false);
				((EquipableItem) result).doEquip(Dungeon.hero);
			}
			Dungeon.hero.spend(-Dungeon.hero.cooldown()); // cancel equip/unequip time
		} else {
			item.detach(Dungeon.hero.belongings.backpack);
			if (!result.collect()) {
				Dungeon.level.drop(result, curUser.pos).sprite.drop();
			} else if (result.stackable && Dungeon.hero.belongings.getSimilar(result) != null) {
				result = Dungeon.hero.belongings.getSimilar(result);
			}
		}
		if (slot != -1
				&& result.defaultAction() != null
				&& !Dungeon.quickslot.isNonePlaceholder(slot)
				&& Dungeon.hero.belongings.contains(result)) {
			Dungeon.quickslot.setSlot(slot, result);
		}

		if (result.isIdentified()) {
			Catalog.setSeen(result.getClass());
		}
		Transmuting.show(curUser, item, result);
		curUser.sprite.emitter().start(Speck.factory(Speck.CHANGE), 0.2f, 10);
		GLog.p(Messages.get(this, "morph"));
	}

	@Override
	public int value() {
		return 250;
	}

	public static Item changeItem(Item item) {
		if (item instanceof MagesStaff) {
			return changeStaff((MagesStaff) item);

		} else if (item instanceof Armor) {
			return changeArmor((Armor) item);

		} else if (item instanceof MeleeWeapon) {
			return changeWeapon((MeleeWeapon) item);

		} else {
			return null;
		}
	}

	private static Armor changeArmor(Armor a) {
		Armor changedArmor = a;

		Class<? extends Armor.Glyph> existing = ((Armor) a).glyph != null ? ((Armor) a).glyph.getClass() : null;

		Glyph glyph;
		
		int enchTier = Random.chances(enchantmentChances);
		switch (enchTier) {
			default:
			case 0:
				glyph = Armor.Glyph.randomCommon(existing);
				break;
			case 1:
				glyph = Armor.Glyph.randomUncommon(existing);
				break;
			case 2:
				glyph = Armor.Glyph.randomRare(existing);
				break;
		}
		

		changedArmor.glyph = glyph;
		changedArmor.glyphHardened = true;

		return changedArmor;
	}

	private static Weapon changeWeapon(MeleeWeapon w) {
		Weapon changedWeapon;
		Generator.Category newTier;

		int tier = ((MeleeWeapon) w).tier - 0;

		if (tier < 1) {
			tier = 1;
		}

		newTier = Generator.wepTiers[tier - 1];

		do {
			changedWeapon = (Weapon) Generator.randomUsingDefaults(newTier);
		} while (Challenges.isItemBlocked(changedWeapon) || changedWeapon.getClass() == w.getClass());

		changedWeapon.level(0);
		changedWeapon.quantity(1);
		int level = w.trueLevel();
		if (level > 0) {
			changedWeapon.upgrade(level);
		} else if (level < 0) {
			changedWeapon.degrade(-level);
		}

		Class<? extends Weapon.Enchantment> existing = ((Weapon) w).enchantment != null
				? ((Weapon) w).enchantment.getClass()
				: null;

		Enchantment ench;

		int enchTier = Random.chances(enchantmentChances);
		switch (enchTier) {
			default:
			case 0:
				ench = Weapon.Enchantment.randomCommon(existing);
				break;
			case 1:
				ench = Weapon.Enchantment.randomUncommon(existing);
				break;
			case 2:
				ench = Weapon.Enchantment.randomRare(existing);
				break;
		}

		changedWeapon.enchantment = ench;
		changedWeapon.masteryPotionBonus = w.masteryPotionBonus;
		changedWeapon.levelKnown = w.levelKnown;
		changedWeapon.cursedKnown = w.cursedKnown;
		changedWeapon.cursed = w.cursed;
		changedWeapon.augment = w.augment;
		changedWeapon.enchantHardened = true;

		return changedWeapon;

	}

	private static MagesStaff changeStaff(MagesStaff staff) {
		MagesStaff changedStaff = staff;

		Class<? extends Weapon.Enchantment> existing = ((MagesStaff) staff).enchantment != null
				? ((MagesStaff) staff).enchantment.getClass()
				: null;
		Enchantment ench = Weapon.Enchantment.randomRare(existing);

		if (staff.wandClass() != null) {
			Wand n;
			do {
				n = (Wand) Generator.randomUsingDefaults(Generator.Category.WAND);
			} while (Challenges.isItemBlocked(n) || n.getClass() == staff.wandClass());
			n.level(0);
			n.identify();
			changedStaff.imbueWand(n, null);
		}

		changedStaff.enchantment = ench;
		changedStaff.enchantHardened = true;

		return changedStaff;
	}
}
