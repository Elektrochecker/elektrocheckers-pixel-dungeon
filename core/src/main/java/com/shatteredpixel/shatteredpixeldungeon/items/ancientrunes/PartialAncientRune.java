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

import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfStrength;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.exotic.ExoticPotion;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ExoticScroll;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Reflection;

public class PartialAncientRune extends AncientRuneInventory {
	
	{
		image = ItemSpriteSheet.ANCIENTRUNE_PARTIAL;
	}

	protected boolean usableOnItem(Item item) {
		return item instanceof ExoticPotion || item instanceof ExoticScroll;
	}
	
	@Override
	protected void onItemSelected(Item item) {
		item.detach(curUser.belongings.backpack);
		Item result;

		if (item instanceof ExoticScroll) {
			result = Reflection.newInstance(ExoticScroll.exoToReg.get(item.getClass()));
		} else if (item instanceof ExoticPotion) {
			result = Reflection.newInstance(ExoticPotion.exoToReg.get(item.getClass()));
		} else {
			//refund target
			item.collect();
			return;
		}

		
		Reflection.newInstance(result.getClass()).collect();
		Reflection.newInstance(result.getClass()).collect();
		
		if (!(result instanceof PotionOfStrength || result instanceof ScrollOfUpgrade)) {
			Reflection.newInstance(result.getClass()).collect();
		}
	}
	
	@Override
	public int value() {
		return 250;
	}
}
