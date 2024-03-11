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

package com.shatteredpixel.shatteredpixeldungeon.items.spells;

import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Statistics;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Degrade;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.ancientrunes.ContravariantAncientRune;
import com.shatteredpixel.shatteredpixeldungeon.items.ancientrunes.CovariantAncientRune;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

//should be a spell
public class InvariantAncientRune extends Spell {

	{
		image = ItemSpriteSheet.ANCIENTRUNE_INVARIANT;
	}

	@Override
	protected void onCast(Hero hero) {
		ScrollOfUpgrade scroll = new ScrollOfUpgrade() {
			@Override
			protected void onItemSelected(Item item) {
				upgrade(curUser);
				Degrade.detach(curUser, Degrade.class);

				item.upgrade();
				item.upgrade();
				item.upgrade();

				Badges.validateItemLevelAquired(item);
				Statistics.upgradesUsed += 3;
				Badges.validateMageUnlock();
			}
		};
		scroll.execute(hero);

		detach(curUser.belongings.backpack);
		updateQuickslot();
	}

	@Override
	public int value() {
		return 250;
	}

	public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe {

		{
			inputs = new Class[] { ContravariantAncientRune.class, CovariantAncientRune.class };
			inQuantity = new int[] { 1, 1 };

			cost = 8;

			output = InvariantAncientRune.class;
			outQuantity = 1;
		}

	}
}
