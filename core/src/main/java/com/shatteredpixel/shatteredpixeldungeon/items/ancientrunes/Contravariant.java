/*
 * Elektrocheckers Pixel Dungeon
 * Copyright (C) 2024-2034 Timon Lilje
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

import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class Contravariant extends AncientRune {
	
	{
		image = ItemSpriteSheet.ANCIENTRUNE_CONTRAVARIANT;
	}
	
	@Override
	protected void onCast(Hero hero) {
	}
	
	@Override
	public int value() {
		//prices of ingredients, divided by output quantity, rounds down
		return 250;
	}
}
