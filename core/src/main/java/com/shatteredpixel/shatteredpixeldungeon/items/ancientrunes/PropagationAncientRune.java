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

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.effects.Flare;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;

public class PropagationAncientRune extends AncientRune {

	{
		image = ItemSpriteSheet.ANCIENTRUNE_PROPAGATION;
	}

	@Override
	protected void onCast(Hero hero) {
		Buff.affect(curUser, AncientRunePropagationTracker.class).setBoosted(1);
		Buff.affect(curUser, AncientRunePropagationTracker.class).setBoosted(2);
		Buff.affect(curUser, AncientRunePropagationTracker.class).setBoosted(3);
		Buff.affect(curUser, AncientRunePropagationTracker.class).setBoosted(4);

		new Flare(6, 32).color(0xFFFF00, true).show(curUser.sprite, 2f);
		Sample.INSTANCE.playDelayed(Assets.Sounds.LEVELUP, 0.2f, 0.7f, 1.2f);
		Sample.INSTANCE.playDelayed(Assets.Sounds.LEVELUP, 0.4f, 0.7f, 1.2f);
		Sample.INSTANCE.playDelayed(Assets.Sounds.LEVELUP, 0.6f, 0.7f, 1.2f);
		Sample.INSTANCE.playDelayed(Assets.Sounds.LEVELUP, 0.8f, 0.7f, 1.2f);

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

	public static class AncientRunePropagationTracker extends Buff {

		{
			type = buffType.POSITIVE;
			revivePersists = true;
		}

		private boolean[] boostedTiers = new boolean[5];

		private static final String BOOSTED_TIERS = "boosted_tiers";

		@Override
		public void storeInBundle(Bundle bundle) {
			super.storeInBundle(bundle);
			bundle.put(BOOSTED_TIERS, boostedTiers);
		}

		@Override
		public void restoreFromBundle(Bundle bundle) {
			super.restoreFromBundle(bundle);
			boostedTiers = bundle.getBooleanArray(BOOSTED_TIERS);
		}

		public void setBoosted(int tier) {
			boostedTiers[tier] = true;
		}

		public boolean isBoosted(int tier) {
			return boostedTiers[tier];
		}

	}
}
