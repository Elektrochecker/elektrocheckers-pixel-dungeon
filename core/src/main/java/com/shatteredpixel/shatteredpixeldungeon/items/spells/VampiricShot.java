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
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bleeding;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.effects.FloatingText;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.plants.Sungrass;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class VampiricShot extends TargetedSpell {

	{
		image = ItemSpriteSheet.VAMP_SHOT;

		usesTargeting = true;
	}

	@Override
	protected void affectTarget(Ballistica bolt, Hero hero) {
		final Char ch = Actor.findChar(bolt.collisionPos);

		if (ch != null && !(ch instanceof Hero)) {
			int min = 5;
			int max = 8 + Dungeon.scalingDepth();
			int dmg = Random.NormalIntRange(min, max);
			int heal = Math.max(max - dmg, 1);

			heal = Math.min(heal, hero.HT - hero.HP);

			ch.damage(dmg, this);

			if (!ch.isImmune(Bleeding.class)) {
				if (heal > 0) {
					hero.HP += heal;
					hero.sprite.showStatusWithIcon( CharSprite.POSITIVE, Integer.toString( heal ), FloatingText.HEALING );
				}

				if(ch.isAlive()) Buff.affect(ch, Bleeding.class).set(1);
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
			inputs = new Class[] { Sungrass.Seed.class, ArcaneCatalyst.class };
			inQuantity = new int[] { 1, 1 };

			cost = 4;

			output = VampiricShot.class;
			outQuantity = 8;
		}

	}

}
