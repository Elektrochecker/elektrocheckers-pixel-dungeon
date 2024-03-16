/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2024 Evan Debenham
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

package com.shatteredpixel.shatteredpixeldungeon.items.weapon.enchantments;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Corrosion;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.CorrosionParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite.Glowing;
import com.watabou.utils.Random;

public class Corroding extends Weapon.Enchantment {

	private static ItemSprite.Glowing ORANGE_CORR = new ItemSprite.Glowing(0xAA4400);

	@Override
	public int proc(Weapon weapon, Char attacker, Char defender, int damage) {

		int level = Math.max(0, weapon.buffedLvl());

		float procChance = (level + 1f) / (level + 3f) * procChanceMultiplier(attacker); // lvl 0 - 33%, lvl 1 - 50%, lvl 2 - 60%

		if (Random.Float() < procChance) {

			int strength = 2 + Dungeon.scalingDepth()/5;
			float duration = Random.Float(2, 3) + Dungeon.scalingDepth()/5;

			float powerMulti = Math.max(1f, procChance);

			if (defender.buff(Corrosion.class) == null) {
				Buff.affect(defender, Corrosion.class).set(duration, strength, Corroding.class);
			}

			powerMulti -= 1;

			if (powerMulti > 0) {
				strength += powerMulti;
				duration *= powerMulti;
				Buff.affect(defender, Corrosion.class).set(duration, strength, Corroding.class);
			}

			defender.sprite.emitter().burst(CorrosionParticle.SPLASH, level + 1);

		}

		return damage;

	}

	@Override
	public Glowing glowing() {
		return ORANGE_CORR;
	}
}
