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

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.effects.Flare;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.Armor;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class ExistenceAncientRune extends AncientRune {

	{
		image = ItemSpriteSheet.ANCIENTRUNE_EXISTENCE;
	}

	@Override
	protected void onCast(Hero hero) {
		Item i = genEquipmentDrop();
		i.collect();

		switch (i.level()) {
			default:
			case 1:
				new Flare(6, 32).color(0xFF00CC, true).show(curUser.sprite, 2f);
				break;
			case 2:
				new Flare(6, 32).color(0xFFCC00, true).show(curUser.sprite, 2f);
				break;
		}

		detach(curUser.belongings.backpack);
		curUser.spend(1f);
		curUser.busy();
		(curUser.sprite).operate(curUser.pos);
		Invisibility.dispel();
	}

	@Override
	public int value() {
		return 250;
	}

	private static Item genEquipmentDrop() {
		Item result;
		
		int floorset = (Dungeon.depth) / 5;
		int floorset_afterbosses = (Dungeon.depth - 1) / 5;
		
		switch (Random.Int(5)) {
			default:
			case 0:
			case 1:
				Weapon w = Generator.randomWeapon(floorset, true);
				if (!w.hasGoodEnchant() && Random.Int(10) < 3)
					w.enchant();
				else if (w.hasCurseEnchant())
					w.enchant(null);
				result = w;
				break;
			case 2:
				Armor a = Generator.randomArmor(floorset);
				if (!a.hasGoodGlyph() && Random.Int(10) < 3)
					a.inscribe();
				else if (a.hasCurseGlyph())
					a.inscribe(null);
				result = a;
				break;
			case 3:
				result = Generator.randomUsingDefaults(Generator.Category.RING);
				break;
			case 4:
				result = Generator.random(Generator.Category.WAND);
				break;
		}

		if (result.isUpgradable()) {
			//+2 chances: 0% sewers, 20% prison, 40% caves, 60% metro, 80% halls
			if(Random.Int(5) < floorset_afterbosses + 1) {
				result.level(2);
			} else {
				result.level(1);
			}
		}

		result.cursed = false;
		result.cursedKnown = true;
		return result;
	}
}
