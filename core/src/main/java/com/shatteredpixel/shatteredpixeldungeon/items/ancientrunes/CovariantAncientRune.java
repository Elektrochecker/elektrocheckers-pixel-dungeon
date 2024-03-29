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

import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.InvariantAncientRune;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class CovariantAncientRune extends AncientRuneInventory {

	{
		image = ItemSpriteSheet.ANCIENTRUNE_COVARIANT;
	}

	@Override
	protected boolean usableOnItem(Item item) {
		return (item instanceof AncientRune || item instanceof InvariantAncientRune) && !(item == this);
	}

	@Override
	protected void onItemSelected(Item item) {
		Item oldrune = item.detach(curUser.belongings.backpack);

		new ContravariantAncientRune().collect();
		new CovariantAncientRune().collect();

		curUser.sprite.emitter().start(Speck.factory(Speck.CHANGE), 0.2f, 10);
	}

	@Override
	public int value() {
		return 250;
	}
}
