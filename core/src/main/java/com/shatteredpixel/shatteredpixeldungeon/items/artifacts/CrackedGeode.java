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

package com.shatteredpixel.shatteredpixeldungeon.items.artifacts;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.MagicImmune;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Regeneration;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ElmoParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.Bag;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.ScrollHolder;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfEnergy;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.Alchemize;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.AquaBlast;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.FeatherFall;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.MagicBridge;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.PhaseShift;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.PrismaticImageSpell;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.ReclaimTrap;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.Recycle;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.Spell;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.SummonElemental;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.TargetedSpell;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.TelekineticGrab;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.Transfiguration;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.WildEnergy;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndBag;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;
import com.watabou.utils.Reflection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class CrackedGeode extends Artifact {

	{
		image = ItemSpriteSheet.ARTIFACT_GEODE;

		levelCap = 3;

		charge = chargesAtLevel[level()];
		partialCharge = 0;
		chargeCap = chargesAtLevel[level()];

		defaultAction = AC_CAST;
	}

	public static final String AC_CAST = "CAST";
	public static final String AC_ADD = "ADD";

	public static final int[] chargesAtLevel = { 1, 2, 3, 4 };

	public static final Class<?>[] spellClasses = new Class<?>[] {
			Alchemize.class,
			AquaBlast.class,
			FeatherFall.class,
			MagicBridge.class,
			PhaseShift.class,
			PrismaticImageSpell.class,
			ReclaimTrap.class,
			Recycle.class,
			SummonElemental.class,
			TelekineticGrab.class,
			Transfiguration.class,
			WildEnergy.class
	};

	private static final float[] castingProbs = new float[] {
			2, // Alchemize
			2, // AquaBlast
			1, // FeatherFall
			2, // MagicBridge
			2, // PhaseShift
			1, // PrismaticImageSpell
			2, // ReclaimTrap
			2, // Recycle
			1, // SummonElemental
			2, // TelekineticGrab
			2, // Transfiguration
			2 // WildEnergy
	};

	private static final HashMap<Class<? extends Spell>, Integer> spellColors = new HashMap<>();
	static {
		spellColors.put(Alchemize.class, 			0x2ee62e);
		spellColors.put(AquaBlast.class, 			0x66b3ff);
		spellColors.put(FeatherFall.class, 			0xa6a6a6);
		spellColors.put(MagicBridge.class, 			0x7d3900);
		spellColors.put(PhaseShift.class, 			0xee99ff);
		spellColors.put(PrismaticImageSpell.class, 	0x8ad8dd);
		spellColors.put(ReclaimTrap.class, 			0x1a1a1a);
		spellColors.put(Recycle.class, 				0xb5bfbf);
		spellColors.put(SummonElemental.class, 		0xff7f00);
		spellColors.put(TelekineticGrab.class, 		0xffffff);
		spellColors.put(Transfiguration.class, 		0xaf0061);
		spellColors.put(WildEnergy.class, 			0x404040);
	}

	private final ArrayList<Class> spellsToAdd = new ArrayList<>();

	private Class<?> loadedSpellClass;

	public CrackedGeode() {
		super();

		float[] upgrProbs = new float[] {
				2, // Alchemize
				2, // AquaBlast
				2, // FeatherFall
				2, // MagicBridge
				2, // PhaseShift
				2, // PrismaticImageSpell
				1, // ReclaimTrap
				0, // Recycle
				0, // SummonElemental
				2, // TelekineticGrab
				2, // Transfiguration
				2 // WildEnergy
		};

		int i = Random.chances(upgrProbs);

		while (i != -1 && spellsToAdd.size() < levelCap) {
			spellsToAdd.add(spellClasses[i]);
			upgrProbs[i] = 0;

			i = Random.chances(upgrProbs);
		}

		loadedSpellClass = loadNextSpell();
	}

	
	@Override
	public void execute(Hero hero, String action) {

		super.execute(hero, action);

		if (hero.buff(MagicImmune.class) != null) {
			return;
		}

		if (action.equals(AC_CAST)) {
			Spell s = (Spell) Reflection.newInstance(loadedSpellClass);
			GLog.i(s.toString());
			
			s.execute(hero, action);
			
			loadedSpellClass = loadNextSpell();
			
		} else if (action.equals(AC_ADD)) {
			GameScene.selectItem(itemSelector);
		}
	}

	private Class<?> loadNextSpell() {
		int i = Random.chances(castingProbs);
		return spellClasses[i];
	}

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		if (isEquipped(hero) && charge > 0 && !cursed && hero.buff(MagicImmune.class) == null) {
			actions.add(AC_CAST);
		}
		if (isEquipped(hero) && level() < levelCap && !cursed && hero.buff(MagicImmune.class) == null) {
			actions.add(AC_ADD);
		}
		return actions;
	}

	@Override
	public ItemSprite.Glowing glowing() {
		if (loadedSpellClass != null && isEquipped(Dungeon.hero)){
			return new ItemSprite.Glowing(spellColors.get(loadedSpellClass));
		}
		return null;
	}

	@Override
	protected ArtifactBuff passiveBuff() {
		return new geodeRecharge();
	}

	@Override
	public void charge(Hero target, float amount) {
		if (charge < chargeCap && !cursed && target.buff(MagicImmune.class) == null) {
			partialCharge += 0.1f * amount;
			if (partialCharge >= 1) {
				partialCharge--;
				charge++;
				updateQuickslot();
			}
		}
	}

	@Override
	public Item upgrade() {
		chargeCap = chargesAtLevel[level() + 1];

		// for artifact transmutation.
		while (!spellsToAdd.isEmpty() && spellsToAdd.size() > (levelCap - 1 - level()))
			spellsToAdd.remove(0);

		return super.upgrade();
	}

	@Override
	public String desc() {
		String desc = super.desc();

		if (isEquipped(Dungeon.hero)) {
			if (cursed) {
				desc += "\n\n" + Messages.get(this, "desc_cursed");
			}

			if (loadedSpellClass != null) {
				desc += "\n\n" +  Messages.get(this, "curr_spell", Messages.get(loadedSpellClass, "name"));
			}

			if (level() < levelCap && spellsToAdd.size() > 0) {
				desc += "\n\n" + Messages.get(this, "desc_index");
				for (int i = 0; i < spellsToAdd.size(); i++) {
					desc += "\n" + "- _" + Messages.get(spellsToAdd.get(i), "name") + "_";
				}
			}
		}

		return desc;
	}

	private static final String SPELLS = "spells";
	private static final String LOADED_SPELL = "loaded_spell";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(SPELLS, spellsToAdd.toArray(new Class[spellsToAdd.size()]));
		bundle.put(LOADED_SPELL, loadedSpellClass);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		spellsToAdd.clear();
		if (bundle.contains(SPELLS)) {
			Collections.addAll(spellsToAdd, bundle.getClassArray(SPELLS));
		}

		if(bundle.contains(LOADED_SPELL)) {
			loadedSpellClass = bundle.getClass(LOADED_SPELL);
		}
	}

	public class geodeRecharge extends ArtifactBuff {
		@Override
		public boolean act() {
			if (charge < chargeCap
					&& !cursed
					&& target.buff(MagicImmune.class) == null
					&& Regeneration.regenOn()) {
				float chargeGain = 1 / (120f - (chargeCap - charge) * 10f);
				chargeGain *= RingOfEnergy.artifactChargeMultiplier(target);
				partialCharge += chargeGain;

				if (partialCharge >= 1) {
					partialCharge--;
					charge++;

					if (charge == chargeCap) {
						partialCharge = 0;
					}
				}
			}

			updateQuickslot();

			spend(TICK);

			return true;
		}
	}

	protected WndBag.ItemSelector itemSelector = new WndBag.ItemSelector() {

		@Override
		public String textPrompt() {
			return Messages.get(CrackedGeode.class, "prompt");
		}

		@Override
		public Class<? extends Bag> preferredBag() {
			return ScrollHolder.class;
		}

		@Override
		public boolean itemSelectable(Item item) {
			return item instanceof Spell && item.isIdentified() && spellsToAdd.contains(item.getClass());
		}

		@Override
		public void onSelect(Item item) {
			if (item != null && item instanceof Spell && item.isIdentified()) {

				Hero hero = Dungeon.hero;

				for (int i = 0; (i < spellsToAdd.size()); i++) {
					if (spellsToAdd.get(i).equals(item.getClass())) {
						hero.sprite.operate(hero.pos);
						hero.busy();
						hero.spend(2f);
						Sample.INSTANCE.play(Assets.Sounds.CHAINS);
						hero.sprite.emitter().burst(ElmoParticle.FACTORY, 12);

						spellsToAdd.remove(i);
						item.detach(hero.belongings.backpack);

						upgrade();
						GLog.i(Messages.get(CrackedGeode.class, "infuse_spell"));
						return;
					}
				}

				GLog.w(Messages.get(CrackedGeode.class, "unable_spell"));

			} else if (item instanceof Spell && !item.isIdentified()) {

				GLog.w(Messages.get(CrackedGeode.class, "unknown_spell"));

			}
		}
	};
}
