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

package com.shatteredpixel.shatteredpixeldungeon.ui.changelist;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.GreaterTransmutation;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.MagicBridge;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.PrismaticImageSpell;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.Transfiguration;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.ChangesScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.Icons;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.watabou.noosa.Image;

import java.util.ArrayList;

public class vModded_Changes {
	public static void addAllChanges( ArrayList<ChangeInfo> changeInfos ){
		add_modded_updates(changeInfos);
		add_Modded_Content(changeInfos);
	}

	public static void add_modded_updates( ArrayList<ChangeInfo> changeInfos ) {
		ChangeInfo changes = new ChangeInfo("v.T4 updates", true, "");
		changes.hardlight(0x10e3a7);
		changeInfos.add(changes);

		changes = new ChangeInfo("v2.1.4T4.0", false, null);
		changes.hardlight(Window.TITLE_COLOR);
		changeInfos.add(changes);

		changes.addButton(new ChangeButton(Icons.get(Icons.PREFS), Messages.get(ChangesScene.class, "misc"),
				"_-_Updated game to include SHPD changes up to version 2.3.2.\n\n" +
				"_-_The Connectivity tab has been removed from the settings. Only game updates are being checked, and only when not using a metered connection.\n\n" +
				"_-_Improved Scroll of Damnation read fx"
		));

		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.ANCIENTRUNE_NABLA), "Ancient Runes",
				"Ancient runes are consumables with unusually powerful effects. They can be stored in the scroll/spell holder.\n\n" +
				"Two ancient runes can be acquired per run: one being awarded each after defeating Goo or Yog-Dzewa."
		));

		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.GREATER_TRANSMUTATION), "Greater Transmutation",
		"A more powerful transmutation spell that is capable of transforming even ancient runes."
		));


		
		changes = new ChangeInfo("v.T3 updates", true, "");
		changes.hardlight(0x10e3a7);
		changeInfos.add(changes);

		changes = new ChangeInfo("v2.1.4T3.3", false, null);
		changes.hardlight(Window.TITLE_COLOR);
		changeInfos.add(changes);

		changes.addButton(new ChangeButton(Icons.get(Icons.PREFS), Messages.get(ChangesScene.class, "misc"),
			"_-_reduced library room loot amount to 2-3 scrolls."
		));

		changes.addButton(new ChangeButton(new Image(Assets.Sprites.SPINNER, 144, 0, 16, 16), Messages.get(ChangesScene.class, "bugfixes"),
			"_-_fixed a bug where the ring of wealth would not drop wands.\n\n" +
			"_-_fixed some spelling mistakes in item descriptions."
		));

		changes.addButton( new ChangeButton(new Image(Assets.Environment.TILES_CITY, 3, 16*16+2, 10, 12), "Rune table",
				"The rune table spawns in library rooms and lets the player perform alchemy."
		));

		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.RING_GARNET), "Ring of Might",
				"The ring of might has been reworked to focus more on HP rather than strength.\n" +
				"It now grants +1 STR at all levels, but max HP gain is increased from 3.5% to 15%."
		));

		changes = new ChangeInfo("v2.1.4T3.2", false, null);
		changes.hardlight(Window.TITLE_COLOR);
		changeInfos.add(changes);

		changes.addButton(new ChangeButton(Icons.get(Icons.PREFS), Messages.get(ChangesScene.class, "misc"),
			"_-_Updated game to include SHPD changes up to version 2.1.4.\n\n" +
			"_-_Other languages have been restored, but only english is supported by the content from this mod.\n" +
			"Default language is now always english.\n\n" +
			"_-_Added a source button that takes players to this projects github page.\n\n" +
			"_-_All heroes are now unlocked by default.\n\n" +
			"_-_Secret well rooms no longer spawn wells of awareness.\n\n" +
			"_-_buffed loot quantity from locked and secret library (scroll) rooms to 2-4 scrolls, up from 1-3.\n\n" +
			"_-_Slightly buffed wand of cosmic travel low roll damage scaling.\n\n"+
			"_-_Orb of sacrifice levelup cost reduced, slightly decreased health cost and description is more transparent.\n\n" +
			"_-_Increased spawn rate of paralytic gas potions, and decreased spawn rate of potions of toxic gas."
		));

		changes.addButton(new ChangeButton(new Image(Assets.Sprites.SPINNER, 144, 0, 16, 16), Messages.get(ChangesScene.class, "bugfixes"),
			"_-_fixed a bug where the orb of sacrifice had no button to cast directly from inverntory.\n\n" +
			"_-_added missing weapon textures from v2.1"
		));
		
		changes.addButton( new ChangeButton(new Image(Assets.Environment.TILES_SEWERS, 8*16, 4*16, 16, 16), "Hidden rooms",
				"A new hidden room type containing spells has been added."
		));

		changes.addButton( new ChangeButton(new Image(Assets.Environment.TILES_SEWERS, 2*16, 16, 16, 16), "New well",
				"a new type of well has been added: the well of enchantment.\n\n" +
				"Drinking water from this well will enchant the heros equipment, and throwing an item inside will enchant it with a higher chance for rare enchants.\n\n" +
				"Single item enchanting rate: _40%_ uncommon, _60%_ rare"
		));

		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.TRANSFIGURATION), "New spells",
				"three new spells have been added:\n\n" +
				"_Shifting Lands_:\n" + Messages.get(Transfiguration.class, "desc") + "\n\n" +
				"_Magical Bridge_:\n" + Messages.get(MagicBridge.class, "desc") + "\n\n" +
				"_Prismatic Image_:\n" + Messages.get(PrismaticImageSpell.class, "desc") + "\nReplaces previously removed scroll of prismatic image."
		));

		
		changes = new ChangeInfo("v2.0.2T3.1", false, null);
		changes.hardlight(Window.TITLE_COLOR);
		changeInfos.add(changes);

		changes.addButton(new ChangeButton(Icons.get(Icons.PREFS), Messages.get(ChangesScene.class, "misc"),
		"updated game to include SHPD changes up to version 2.0.2."
		));

		changes.addButton(new ChangeButton(new Image(Assets.Sprites.SPINNER, 144, 0, 16, 16), Messages.get(ChangesScene.class, "bugfixes"),
		"fixed a bug where my new wands would still damage antimagic champions."
		));

		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.ARTIFACT_TALISMAN), "Talisman of foresight",
				"The talisman of foresight now furrows tall grass when scrying."
		));
 
		changes = new ChangeInfo("v2.0.0T3", false, null);
		changes.hardlight(Window.TITLE_COLOR);
 		changeInfos.add(changes);
 
		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.EXOTIC_JERA), "New scrolls",
				"The scroll of trauma and corresponding exotic scroll of damnation can now be acquired.\n\n" +
				"A scroll of Trauma will inflict enemies in sight with 60 turns of HEX.\n\n" +
				"A scroll of damnation will inflict doom to all enemies instead."
		));

		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.SEED_VOIDPEARL), "New Plants",
				"Ghostbulb and Voidpearl are new plants which can be found in the dungeon.\n\n" +
				"Trampling a ghostbulb will summon a mirror image to aid the trampler in combat.\n\n" +
				"Trampling a Voidpearl will kill the Target and remove any Item on the same Tile from the Game.\n\n\n" +
				"Trampling a blindweed as the warden now grants illumination instead of invisibility."
		));

		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.VOID_DART), "New Darts",
				"New Darts were added to complement the new seed types.\n\n" +
				"Ghost darts will summon copies of both the user and target of the dart.\n\n" +
				"Void darts will deal flat damage to their target."
		));

		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.SCROLL_NAUDIZ), "Scroll of mirror image",
				"The scroll of mirror image has been removed. Why? Cause fuck 'em, that's why."
		));

		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.ARTIFACT_TOOLKIT), "Changed recipes",
				"Wooly bomb now requires a stone of flock instead of a scroll of mirror image.\n\n" +
				"Using seeds of ghostbulb to make potions will result in a potion of invisibility. Voidpearl seeds will make a potion of experience.\n" +
				"Maybe I will add new potions in the future."
		));
	}

	public static void add_Modded_Content( ArrayList<ChangeInfo> changeInfos ) {

		ChangeInfo changes = new ChangeInfo("v.T2", true, "");
		changes.hardlight(0x10e3a7);
		changeInfos.add(changes);
 
		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.WAND_COSMOS), "Wand of cosmic travel",
				"This wand shoots a beam that collides with the first enemy hit, and swaps it's place with the caster's, dealing damage in the process. Immobile units hit will suffer bonus damage instead of being teleported.\n\n"+
				"The base damage of this wand is unusually low, but it has good upgrade scaling. This is to not make the wand's utility at low levels too strong, but still give the upgraded wand enough damage"
		));
 
		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.WAND_CHAOS), "Wand of chaos",
				"This wand shoots a beam that pierces terrain, will go farther the more it is upgraded, and will apply random debuffs to enemies hit, dealing damage."
		));
 
		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.ARTIFACT_ORB), "Orb of sacrifice",
				"This new artifact inflicts blood loss on enemies. It has no charge limitation, but the user pays in blood. The directly targeted enemy gets 1 more bleed damage (translates to more than 1 damage)\n\n"
				+"HP cost is reduced by ring of Energy's artefact recharging and Ring of tenacity. Rings do not reduce exp gained towards artefact levels.\n\nAdditional range is gained per artifact level"
		));
 
		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.RING_SAPPHIRE), "Ring of Wealth",
				"The Ring of Wealth now drops upgraded Wands instead of Artifacts. This makes it sronger than Shattered's Ring of Wealth."
		));
 
 	}
}
