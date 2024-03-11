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
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndBag;
import com.watabou.noosa.audio.Sample;

public abstract class AncientRuneInventory extends AncientRune {

	public static final String AC_CAST = "CAST";

	{
		stackable = false;
		defaultAction = AC_CAST;
	}

	protected boolean usableOnItem(Item item) {
		return true;
	}

	protected abstract void onItemSelected(Item item);

	private String inventoryTitle() {
		return Messages.get(this, "name");
	}

	@Override
	protected void onCast(Hero hero) {
		GameScene.selectItem(itemSelector);

		updateQuickslot();
		Invisibility.dispel();
		hero.spendAndNext(1f);
	}

	protected WndBag.ItemSelector itemSelector = new WndBag.ItemSelector() {

		@Override
		public String textPrompt() {
			return inventoryTitle();
		}

		@Override
		public boolean itemSelectable(Item item) {
			return usableOnItem(item);
		}

		@Override
		public void onSelect(Item item) {

			// FIXME this safety check shouldn't be necessary
			// it would be better to eliminate the curItem static variable.
			if (!(curItem instanceof AncientRuneInventory)) {
				return;
			}

			if (item != null) {

				// curItem = detach(curUser.belongings.backpack);
				curItem = detach(curUser.belongings.backpack);

				((AncientRuneInventory) curItem).onItemSelected(item);

				Sample.INSTANCE.play(Assets.Sounds.READ);

			}
		}
	};
}
