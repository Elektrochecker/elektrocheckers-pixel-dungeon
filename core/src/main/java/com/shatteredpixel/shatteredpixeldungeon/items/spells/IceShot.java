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

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Freezing;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Chill;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Frost;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.plants.Icecap;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class IceShot extends TargetedSpell {

	{
		image = ItemSpriteSheet.ICE_SHOT;

		usesTargeting = true;
	}

	@Override
	protected void affectTarget(Ballistica bolt, Hero hero) {
		final Char ch = Actor.findChar(bolt.collisionPos);
		int cell = bolt.collisionPos;

		if (bolt.path.size() < bolt.dist) {
			cell = bolt.path.get(bolt.dist - 1);
		}

		

		if (ch != null) {
			if (Dungeon.level.water[cell] && !ch.isImmune(Freezing.class)) {
				Buff.affect(ch, Frost.class, 2f);
			} else {
				Buff.affect(ch, Chill.class, 4f);
			}
		} else {
			if (!Dungeon.level.solid[cell]) {
				GameScene.add(Blob.seed(cell, 2, Freezing.class));
			}
		}
	}

	@Override
	public int value() {
		// prices of ingredients, divided by output quantity, rounds down
		return (int) ((10 + 40) * (quantity / 8f));
	}

	public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe {

		{
			inputs = new Class[] { Icecap.Seed.class, ArcaneCatalyst.class };
			inQuantity = new int[] { 1, 1 };

			cost = 2;

			output = IceShot.class;
			outQuantity = 8;
		}

	}

}
