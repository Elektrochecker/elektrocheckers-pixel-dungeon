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

import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class FieldOperatorAncientRune extends AncientRuneTargeted {

	{
		image = ItemSpriteSheet.ANCIENTRUNE_FIELD;
	}

	@Override
	protected void affectTarget(Ballistica bolt, Hero hero) {
		final Char ch = Actor.findChar(bolt.collisionPos);

		if (ch != null && !(ch instanceof Hero)) {
			ch.damage(1000, this);
			detach(curUser.belongings.backpack);
		}
	}

	@Override
	public int value() {
		return 250;
	}
}
