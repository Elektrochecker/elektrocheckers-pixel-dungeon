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

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.effects.MagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.ui.QuickSlotButton;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;

public abstract class AncientRuneTargeted extends AncientRune {

	protected int collisionProperties = Ballistica.STOP_TARGET;

	@Override
	protected void onCast(Hero hero) {
		GameScene.selectCell(targeter);
	}

	protected abstract void affectTarget(Ballistica bolt, Hero hero);

	protected void fx(Ballistica bolt, Callback callback) {
		MagicMissile.boltFromChar(curUser.sprite.parent,
				MagicMissile.MAGIC_MISSILE,
				curUser.sprite,
				bolt.collisionPos,
				callback);
		Sample.INSTANCE.play(Assets.Sounds.ZAP);
	}

	private static CellSelector.Listener targeter = new CellSelector.Listener() {

		@Override
		public void onSelect(Integer target) {

			if (target != null) {

				// FIXME this safety check shouldn't be necessary
				// it would be better to eliminate the curItem static variable.
				final AncientRuneTargeted curRune;
				if (curItem instanceof AncientRuneTargeted) {
					curRune = (AncientRuneTargeted) curItem;
				} else {
					return;
				}

				final Ballistica shot = new Ballistica(curUser.pos, target, curRune.collisionProperties);
				int cell = shot.collisionPos;

				curUser.sprite.zap(cell);

				// attempts to target the cell aimed at if something is there, otherwise targets
				// the collision pos.
				if (Actor.findChar(target) != null)
					QuickSlotButton.target(Actor.findChar(target));
				else
					QuickSlotButton.target(Actor.findChar(cell));

				curUser.busy();

				curRune.fx(shot, new Callback() {
					public void call() {
						curRune.affectTarget(shot, curUser);
						Invisibility.dispel();
						curRune.updateQuickslot();
						curUser.spendAndNext(1f);
					}
				});

			}

		}

		@Override
		public String prompt() {
			return Messages.get(AncientRuneTargeted.class, "prompt");
		}
	};

}
