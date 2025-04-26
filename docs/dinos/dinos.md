# Dinos

## Table of Contents

- [Occurrence](#occurrence)
    - [Dino rarity](#dinoStats-rarity)
    - [Stats](#stats)
- [Obtaining](#obtaining)
- [Usage](#usage)
    - [PvP dinoStats](#pvp-dinoStats)
    - [PvE dinoStats](#pve-dinoStats)
    - [Utility dinoStats](#utility-dinoStats)
- [Mechanics](#mechanics)
    - [Stats](#stats)
    - [Levelling](#levelling)
    - [Breeding](#breeding)
- [More ideas](#more-ideas)

## Occurrence

- Dinos **spawn randomly** across the map. They are **invisible** to the player until they come close to them.
- Once a player comes close to a wild dinoStats they are granted the option to **either tame or kill** the wild
  dinoStats:
    - **Taming**: when a dinoStats is successfully tamed, it is added to your inventory/dinoStats list.
    - **Killing**: you can select a few of your dinoStats to fight the wild dinoStats, your dinoStats then **battle the
      wild dinoStats**.
      Dinos might die during this battle (not sure yet if they should actually disappear or not) so choose your team
      wisely.  
      Once the dinoStats is killed you can harvest it to **gain resources**
      ( [meat](../resources.md#meat) / [hide](../resources.md#hide) ). The amount scales depending on the size of the
      killed dinoStats.

### Dino rarity

- **Common**
    - Spawn often in early-game areas
    - Levels vary
- **Rare**
    - Spawn less frequent and in more late-game areas
    - Levels tend to be lower on average

### Stats

- Dinos have certain stats (even wild dinoStats) for:
    - **Health**: the dinoStats's health
    - **Damage**: the damage dealt by the dinoStats
    - **Critical chance**: the chance an attack turns into a critical attack
    - **Critical damage**: the damage amplifier for critical attacks
    - **Speed**: determines the **order of attack** in a battle, the dinoStats with the highest speed attacks first (not
      sure
      if this stat should be able to get mutated, might scale too large)

## Obtaining

- Dinos can be tamed with **certain equipment** (tranquilizer arrow with a crossbow or something completely different).
- The dinoStats's stats will only become visible once it is tamed so you might still end up with a dinoStats that has
  awful stats.

## Usage

Different dinoStats have **different uses** from fighting to farming to granting buffs.

### PvP dinoStats

Some dinoStats are specifically meant for **battle**:

- Giga: high damage, not very tanky
- Rex: solid damage with high HP

### PvE dinoStats

These dinoStats are more focused on **farming and utility**

- Ankylosaurus: improved metal harvesting
- Doedicurus: improved stone harvesting

### Utility dinoStats

Give the player (or other dinoStats) a **boost** when a utility dinoStats is equipped (e.g. speed boost, dmg boost, ...)

## Mechanics

### Stats

Every dinoStats has **underlying points** for every stat.  
These underlying points are determined once the dinoStats is tamed (see [obtaining](#obtaining)).  
These points are similar for every species (they have the same range).

The actual stats are based on these underlying points, the actual stats will **scale differently for every species**
e.g.:

- A giga with 50 (underlying) points into damage will have 600 actual damage, a rex with 50 points into damage will only
  have 400 actual damage
- A giga with 100 points into health will have 30k actual health, a rex with 100 points into health will have 50k actual
  health.  
  This way different species automatically end up with different usages: giga's are very damage based, they have very
  high damage but lower health while rexes are more of a hybrid with solid damage while also being fairly tanky.
  Therefore, rexes will probably be better used in boss fights since they can take a few more hits than giga's could.

### Levelling

- A dinoStats can obtain XP through various ways:
    - **Over time XP** will slowly be gained
    - **Player interactions** will give small XP increases (e.g. when the player uses a dinoStats for farming)
    - **Battles** (especially bosses) will give large amounts of XP to all dinoStats that were deployed in the battle.

Every XP level can be **used to increase any stat** of the dinoStats even further.  
Higher XP levels will take more XP and thus longer to obtain.

### Breeding

As mentioned in [stats](#stats), dinoStats have an underlying point system for each stat.  
These **stats can be improved** in 2 ways:

1. **Leveling**: a certain amount of XP levels can be put into any stat, XP is slowly gained over time or faster through
   battles.
2. **Breed mutations**: when 2 dinoStats of the **same species breed**, an egg/baby will be spawned. This baby's stats
   will
   be based on those of the parents. For every stat, it inherits the same amount of points from either parent. There is
   a small chance for the baby to have a **mutation on a random stat**. This mutation will slightly increase the points
   of this stat. Then the player can breed the dinoStats with the mutated stat and try to get another mutation on the
   same
   stat to continuously improve it step by step.

Breeding 2 dinoStats will cost both **time and resources**:

- Maybe each breeding interval requires a certain item that is either crafted or maybe only obtainable through
  challenges
- The breeding itself will take time and the egg hatching/gestation (pregnancy) will also take time
- Once the baby is born it will require the player's attention: the player has to make sure it doesn't starve, the
  player will have to do certain missions with it to get imprint and thus better stats on the dinoStats, ...

## More ideas

1. **([Rarity](#dinoStats-rarity)) Legendary dinoStats**:
    - Very rare spawns (chance at the end of a boss fight, chance inside a dangerous cave, ...)
    - Kinda like shiny pokemons: normal dinoStats but with a small chance to be "better"
    - Special (harder) way of taming them, higher risk of failing the tame
2. **([Utility dinoStats](#utility-dinoStats)) Flying dinoStats**:
    - Faster traveling
    - Can fly over water
    - Can fly to the top of mountains
3. **([Levelling](#levelling)) Level cap**:
    - Not sure yet if there should be a cap to XP levels.
    - If the higher levels end up requiring large amounts of XP this might not be a problem, then the player can still
      use XP to strengthen their dinoStats slowly over time

## Types

### Tyrannosaurus Rex (DPS / Bruiser)

- **Basic attack**: Bite - A powerful chomp dealing moderate damage
- **Active ability**: Savage Roar - Increases damage by 30% for 3 rounds, decreases by 10% each round
- **Passive ability**: Blood Thirst - Restores 10% of damage dealt as health
- **Stats ability**: Apex Predator - Increased critical chance and damage

### Triceratops (Tank / Support)

- **Basic attack**: Horn Bash - Strikes the target with its horns
- **Active ability**: Shield Charge - Rushes into an enemy, dealing damage and stunning them for 2 rounds
- **Passive ability**: Guardian Stance - Reflects 20% of damage taken back to the attacker, with a 30% chance to deal
  20% more damage
- **Stats ability**: Thick Hide - Increased health and armor
- **Utility ability**: Increases wood and thatch carrying capacity

### Velociraptor (DPS / Assassin)

- **Basic attack**: Claw Strike - A quick strike dealing low damage
- **Active ability**: Pack Ambush - Leaps at the enemy with the lowest HP, dealing high single-target damage and
  applying a bleed effect for 3 rounds
- **Passive ability**: Pack Hunter - When launching an active ability, all allied Velociraptors attack a random enemy
  that has a bleed effect dealing 50% of the damage
- **Stats ability**: Agile Hunter - Increased speed and critical chance

### Brachiosaurus (Support / Healer)

- **Basic attack**: Tail Whip - Swings its tail and deals damage to 2 random enemies
- **Active ability**: Herbivore's Blessing - Heals 3 random allies for 10% of their max health each round for 3 rounds
- **Passive ability**: Soothing Presence - Every critical hit on an enemy heals a random ally for 5% of their max health
- 
- **Utility ability**: Fertile Lands - Increases berry and fiber harvesting and carrying capacity

### Spinosaurus (Hybrid Tank-DPS)

- **Basic attack**: Ripping Claw - A strong attack that slightly heals the Spinosaurus
- **Active ability**: Frenzied Onslaught - Attack 2 random enemies, there is a 40% chance to apply 1 layer of Frenzy to
  the enemy
- **Passive ability**: Frenzy Slaughter - When the Spinosaurus is hit by a critical strike, deals 20% of the damage to
  all enemies with at least one layer of Frenzy
- **Stats ability**: Thick Scales - Increased armor  