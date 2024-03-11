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

package com.shatteredpixel.shatteredpixeldungeon.items.scrolls;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Hex;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.Flare;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;

public class ScrollOfTrauma extends Scroll {

	{
		icon = ItemSpriteSheet.Icons.SCROLL_TRAUMA;
	}

	@Override
	public void doRead() {
		detach(curUser.belongings.backpack);

		new Flare(5, 32).color(0xCC00CC, true).show(curUser.sprite, 2f);
		Sample.INSTANCE.play(Assets.Sounds.READ);

		int count = 0;
		Mob affected = null;
		for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0])) {
			if (mob.properties().contains(Char.Property.BOSS) || mob.properties().contains(Char.Property.MINIBOSS)) {
				Buff.affect(mob, Hex.class, Hex.DURATION * 0.6f);
			} else if (mob.alignment != Char.Alignment.ALLY && Dungeon.level.heroFOV[mob.pos]) {
				Buff.affect(mob, Hex.class, Hex.DURATION * 2);

				if (mob.buff(Hex.class) != null) {
					count++;
					affected = mob;
				}
			}
		}

		switch (count) {
			case 0:
				GLog.i(Messages.get(this, "light"));
				break;
			case 1:
				GLog.i(Messages.get(this, "light", affected.name()));
				break;
			default:
				GLog.i(Messages.get(this, "light"));
		}
		identify();

		readAnimation();
	}

	@Override
	public int value() {
		return isKnown() ? 40 * quantity : super.value();
	}
}
