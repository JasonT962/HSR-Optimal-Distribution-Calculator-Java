package OptimalDistributionCalculator;

import java.util.ArrayList;

import Player.*;
import Player.Relic.mainstat;
import Player.Relic.substat;

import DamageCalculators.*;

public class Optimizer {
    private Player player;
    private DamageCalculator calculator;

    // Relic roll values
    private double rollHP = 3.89;
    private double rollFlatHP = 38.1;
    private double rollAttack = 3.89;
    private double rollFlatAttack = 19.05;
    private double rollDefence = 4.86;
    private double rollFlatDefence = 19.05;
    private double rollSpeed = 2.3;
    private double rollCR = 2.92;
    private double rollCD = 5.83;
    private double rollBE = 5.83;
    private double rollEHR = 3.89;
    private double rollEffRes = 5.83;

    // Set to 5 if you want relics to start with 4 substats, set to 4 if you want relics to start with 3 substats
    private double addRolls = 4;

    public Optimizer(DamageCalculator calculator) {
        player = new Player();
        this.calculator = calculator;
    }

    // Compares main stat combinations and returns the set with the highest average damage
    public Relic[] SetMainStats() {
        Relic head = new Relic(mainstat.FlatHP, substat.None, substat.None, substat.None, substat.None);
        Relic hands = new Relic(mainstat.FlatAttack, substat.None, substat.None, substat.None, substat.None);
        Relic body = new Relic(mainstat.None, substat.None, substat.None, substat.None, substat.None);
        Relic feet = new Relic(mainstat.None, substat.None, substat.None, substat.None, substat.None);
        Relic sphere = new Relic(mainstat.None, substat.None, substat.None, substat.None, substat.None);
        Relic rope = new Relic(mainstat.None, substat.None, substat.None, substat.None, substat.None);

        // List of possible main stats for body, feet, sphere and rope
        ArrayList<mainstat> bodylist = new ArrayList<mainstat>();
        bodylist.add(mainstat.HP);
        bodylist.add(mainstat.Attack);
        bodylist.add(mainstat.Defence);
        bodylist.add(mainstat.CritRate);
        bodylist.add(mainstat.CritDamage);
        bodylist.add(mainstat.HealingBoost);
        bodylist.add(mainstat.EffectHitRate);

        ArrayList<mainstat> feetlist = new ArrayList<mainstat>();
        feetlist.add(mainstat.HP);
        feetlist.add(mainstat.Attack);
        feetlist.add(mainstat.Defence);
        feetlist.add(mainstat.Speed);

        ArrayList<mainstat> spherelist = new ArrayList<mainstat>();
        spherelist.add(mainstat.HP);
        spherelist.add(mainstat.Attack);
        spherelist.add(mainstat.Defence);
        spherelist.add(mainstat.DamageBoost);

        ArrayList<mainstat> ropelist = new ArrayList<mainstat>();
        ropelist.add(mainstat.HP);
        ropelist.add(mainstat.Attack);
        ropelist.add(mainstat.Defence);
        ropelist.add(mainstat.BreakEffect);
        ropelist.add(mainstat.EnergyRegeneration);

        Relic[] defaultSet = {head,hands,body,feet,sphere,rope};
        double defaultDamage = MaxDamage(defaultSet);

        
        // Filter out main stats that don't increase damage
        // Body
        ArrayList<mainstat> toKeep = new ArrayList<mainstat>();
        for (int i = 0; i < bodylist.size(); i++) {
            body = new Relic(bodylist.get(i), substat.None, substat.None, substat.None, substat.None);
            Relic[] temp = {head,hands,body,feet,sphere,rope};
            if (MaxDamage(temp) > defaultDamage) {
                toKeep.add(bodylist.get(i));
            }
        }
        bodylist = toKeep;
        body = new Relic(mainstat.None, substat.None, substat.None, substat.None, substat.None);

        // Feet
        toKeep = new ArrayList<mainstat>();
        for (int i = 0; i < feetlist.size(); i++) {
            feet = new Relic(feetlist.get(i), substat.None, substat.None, substat.None, substat.None);
            Relic[] temp = {head,hands,body,feet,sphere,rope};
            if (MaxDamage(temp) > defaultDamage) {
                toKeep.add(feetlist.get(i));
            }
        }
        feetlist = toKeep;
        feet = new Relic(mainstat.None, substat.None, substat.None, substat.None, substat.None);
        
        // Sphere
        toKeep = new ArrayList<mainstat>();
        for (int i = 0; i < spherelist.size(); i++) {
            sphere = new Relic(spherelist.get(i), substat.None, substat.None, substat.None, substat.None);
            Relic[] temp = {head,hands,body,feet,sphere,rope};
            if (MaxDamage(temp) > defaultDamage) {
                toKeep.add(spherelist.get(i));
            }
        }
        spherelist = toKeep;
        sphere = new Relic(mainstat.None, substat.None, substat.None, substat.None, substat.None);

        // Rope
        toKeep = new ArrayList<mainstat>();
        for (int i = 0; i < ropelist.size(); i++) {
            rope = new Relic(ropelist.get(i), substat.None, substat.None, substat.None, substat.None);
            Relic[] temp = {head,hands,body,feet,sphere,rope};
            if (MaxDamage(temp) > defaultDamage) {
                toKeep.add(ropelist.get(i));
            }
        }
        ropelist = toKeep;
        rope = new Relic(mainstat.None, substat.None, substat.None, substat.None, substat.None);

        double highestDamage = 0;
        double damage = 0;

        //The set we're going to change to compare to previous highest damage
        Relic[] testSet = {head,hands,body,feet,sphere,rope};

        //The set which contains the current highest damage
        Relic[] finalSet = {head,hands,body,feet,sphere,rope};

        // Loops through all possible main stat combinations
        for (int i = 0; i < bodylist.size(); i++) {
            for (int j = 0; j < feetlist.size(); j++) {
                for (int k = 0; k < spherelist.size(); k++) {
                    for (int l = 0; l < ropelist.size(); l++) {
                        body = new Relic(bodylist.get(i), substat.None, substat.None, substat.None, substat.None);
                        feet = new Relic(feetlist.get(j), substat.None, substat.None, substat.None, substat.None);
                        sphere = new Relic(spherelist.get(k), substat.None, substat.None, substat.None, substat.None);
                        rope = new Relic(ropelist.get(l), substat.None, substat.None, substat.None, substat.None);

                        testSet = new Relic[] {head,hands,body,feet,sphere,rope};
                        damage = MaxDamage(SetSubStats(testSet)); // Applies best substats and damage the set can do

                        if (damage > highestDamage) {
                            highestDamage = damage;
                            finalSet = testSet;
                        }
                    }
                }
            }
        }
        return finalSet;
    }

    // Returns set with substats that gives highest average damage
    public Relic[] SetSubStats(Relic[] set) {
        // Reset substats for all relics
        for (int i = 0; i < set.length; i++) {
            set[i].sub1 = substat.None;
            set[i].sub2 = substat.None;
            set[i].sub3 = substat.None;
            set[i].sub4 = substat.None;
        }

        // Loops through each relic in the set
        for (int i = 0; i < set.length; i++) {
            ArrayList<substat> substatList = new ArrayList<substat>();
            substatList.add(substat.HP);
            substatList.add(substat.FlatHP);
            substatList.add(substat.Attack);
            substatList.add(substat.FlatAttack);
            substatList.add(substat.Defence);
            substatList.add(substat.FlatDefence);
            substatList.add(substat.CritRate);
            substatList.add(substat.CritDamage);
            substatList.add(substat.EffectRes);
            substatList.add(substat.BreakEffect);
            substatList.add(substat.EffectHitRate);
            substatList.add(substat.Speed);

            // Removes substat from list that is the same as main stat
            for (int j = 0; j < substatList.size(); j++) {
                if (set[i].main.name().equals(substatList.get(j).name())) {
                    substatList.remove(j);
                    break;
                }
            }

            double highestDamage = 0;
            double damage = 0;
            substat bestSubstat = substatList.get(substatList.size()-1); // Set default best substat to last one in list (Speed)

            // Substat 1
            for (int j = 0; j < substatList.size(); j++) {
                set[i].sub1 = substatList.get(j);
                damage = MaxDamage(set);

                if (damage > highestDamage) {
                    highestDamage = damage;
                    bestSubstat = substatList.get(j);
                }
            }
            set[i].sub1 = bestSubstat;
            substatList.remove(bestSubstat);
            bestSubstat = substatList.get(substatList.size()-1);

            // Substat 2
            for (int j = 0; j < substatList.size(); j++) {
                set[i].sub2 = substatList.get(j);
                damage = MaxDamage(set);

                if (damage > highestDamage) {
                    highestDamage = damage;
                    bestSubstat = substatList.get(j);
                }
            }
            set[i].sub2 = bestSubstat;
            substatList.remove(bestSubstat);
            bestSubstat = substatList.get(substatList.size()-1);

            // Substat 3
            for (int j = 0; j < substatList.size(); j++) {
                set[i].sub3 = substatList.get(j);
                damage = MaxDamage(set);

                if (damage > highestDamage) {
                    highestDamage = damage;
                    bestSubstat = substatList.get(j);
                }
            }
            set[i].sub3 = bestSubstat;
            substatList.remove(bestSubstat);
            bestSubstat = substatList.get(substatList.size()-1);

            // Substat 4
            for (int j = 0; j < substatList.size(); j++) {
                set[i].sub4 = substatList.get(j);
                damage = MaxDamage(set);

                if (damage > highestDamage) {
                    highestDamage = damage;
                    bestSubstat = substatList.get(j);
                }
            }
            set[i].sub4 = bestSubstat;
            substatList.remove(bestSubstat);
            bestSubstat = substatList.get(substatList.size()-1);
        }
        return set;
    }

    //Returns the highest average damage an relic set could do
    public double MaxDamage(Relic[] set) {
        // Refresh player so previous stats added get reset
        player = new Player();

        // Roll Limits for each substat
        double hpUpLimit = 0;
        double flatHPLimit = 0;
        double attackUpLimit = 0;
        double flatAttackLimit = 0;
        double defenceUpLimit = 0;
        double flatDefenceLimit = 0;
        double speedLimit = 0;
        double crLimit = 0;
        double cdLimit = 0;
        double beLimit = 0;
        double ehrLimit = 0;
        double effResLimit = 0;

        // Apply main stats and sub stats
        for (int i = 0; i < set.length; i++) {
            switch(set[i].main) {
                case HP:
                    player.hpUp += 43.2;
                    break;
                case FlatHP:
                    player.flatHp += 705.6;
                    break;
                case Attack:
                    player.attackUp += 43.2;
                    break;
                case FlatAttack:
                    player.flatAttack += 352.8;
                    break;
                case Defence:
                    player.defenceUp += 54;
                    break;
                case Speed:
                    player.flatSpeed += 25.03;
                    break;
                case CritRate:
                    player.critRate += 32.4;
                    break;
                case CritDamage:
                    player.critDamage += 64.8;
                    break;
                case EnergyRegeneration:
                    player.energyRegeneration += 19.44;
                    break;
                case BreakEffect:
                    player.breakEffect += 64.8;
                    break;
                case EffectHitRate:
                    player.effectHitRate += 43.2;
                    break;
                case DamageBoost:
                    player.damageBoost += 38.88;
                    break;
                case HealingBoost:
                    player.healingBoost += 34.56;
                    break;
                case None:
                    break;
                default:
                    break;
            }

            switch(set[i].sub1) {
                case HP:
                    player.hpUp += rollHP;
                    hpUpLimit += addRolls;
                    break;
                case FlatHP:
                    player.flatHp += rollFlatHP;
                    flatHPLimit += addRolls;
                    break;
                case Attack:
                    player.attackUp += rollAttack;
                    attackUpLimit += addRolls;
                    break;
                case FlatAttack:
                    player.flatAttack += rollFlatAttack;
                    flatAttackLimit += addRolls;
                    break;
                case Defence:
                    player.defenceUp += rollDefence;
                    defenceUpLimit += addRolls;
                    break;
                case FlatDefence:
                    player.flatDefence += rollFlatDefence;
                    flatDefenceLimit += addRolls;
                    break;
                case Speed:
                    player.flatSpeed += rollSpeed;
                    speedLimit += addRolls;
                    break;
                case CritRate:
                    player.critRate += rollCR;
                    crLimit += addRolls;
                    break;
                case CritDamage:
                    player.critDamage += rollCD;
                    cdLimit += addRolls;
                    break;
                case BreakEffect:
                    player.breakEffect += rollBE;
                    beLimit += addRolls;
                    break;
                case EffectHitRate:
                    player.effectHitRate += rollEHR;
                    ehrLimit += addRolls;
                    break;
                case EffectRes:
                    player.effectRes += rollEffRes;
                    effResLimit += addRolls;
                    break;
                case None:
                    break;
                default:
                    break;
            }

            switch(set[i].sub2) {
                case HP:
                    player.hpUp += rollHP;
                    hpUpLimit += addRolls;
                    break;
                case FlatHP:
                    player.flatHp += rollFlatHP;
                    flatHPLimit += addRolls;
                    break;
                case Attack:
                    player.attackUp += rollAttack;
                    attackUpLimit += addRolls;
                    break;
                case FlatAttack:
                    player.flatAttack += rollFlatAttack;
                    flatAttackLimit += addRolls;
                    break;
                case Defence:
                    player.defenceUp += rollDefence;
                    defenceUpLimit += addRolls;
                    break;
                case FlatDefence:
                    player.flatDefence += rollFlatDefence;
                    flatDefenceLimit += addRolls;
                    break;
                case Speed:
                    player.flatSpeed += rollSpeed;
                    speedLimit += addRolls;
                    break;
                case CritRate:
                    player.critRate += rollCR;
                    crLimit += addRolls;
                    break;
                case CritDamage:
                    player.critDamage += rollCD;
                    cdLimit += addRolls;
                    break;
                case BreakEffect:
                    player.breakEffect += rollBE;
                    beLimit += addRolls;
                    break;
                case EffectHitRate:
                    player.effectHitRate += rollEHR;
                    ehrLimit += addRolls;
                    break;
                case EffectRes:
                    player.effectRes += rollEffRes;
                    effResLimit += addRolls;
                    break;
                case None:
                    break;
                default:
                    break;
            }

            switch(set[i].sub3) {
                case HP:
                    player.hpUp += rollHP;
                    hpUpLimit += addRolls;
                    break;
                case FlatHP:
                    player.flatHp += rollFlatHP;
                    flatHPLimit += addRolls;
                    break;
                case Attack:
                    player.attackUp += rollAttack;
                    attackUpLimit += addRolls;
                    break;
                case FlatAttack:
                    player.flatAttack += rollFlatAttack;
                    flatAttackLimit += addRolls;
                    break;
                case Defence:
                    player.defenceUp += rollDefence;
                    defenceUpLimit += addRolls;
                    break;
                case FlatDefence:
                    player.flatDefence += rollFlatDefence;
                    flatDefenceLimit += addRolls;
                    break;
                case Speed:
                    player.flatSpeed += rollSpeed;
                    speedLimit += addRolls;
                    break;
                case CritRate:
                    player.critRate += rollCR;
                    crLimit += addRolls;
                    break;
                case CritDamage:
                    player.critDamage += rollCD;
                    cdLimit += addRolls;
                    break;
                case BreakEffect:
                    player.breakEffect += rollBE;
                    beLimit += addRolls;
                    break;
                case EffectHitRate:
                    player.effectHitRate += rollEHR;
                    ehrLimit += addRolls;
                    break;
                case EffectRes:
                    player.effectRes += rollEffRes;
                    effResLimit += addRolls;
                    break;
                case None:
                    break;
                default:
                    break;
            }

            switch(set[i].sub4) {
                case HP:
                    player.hpUp += rollHP;
                    hpUpLimit += addRolls;
                    break;
                case FlatHP:
                    player.flatHp += rollFlatHP;
                    flatHPLimit += addRolls;
                    break;
                case Attack:
                    player.attackUp += rollAttack;
                    attackUpLimit += addRolls;
                    break;
                case FlatAttack:
                    player.flatAttack += rollFlatAttack;
                    flatAttackLimit += addRolls;
                    break;
                case Defence:
                    player.defenceUp += rollDefence;
                    defenceUpLimit += addRolls;
                    break;
                case FlatDefence:
                    player.flatDefence += rollFlatDefence;
                    flatDefenceLimit += addRolls;
                    break;
                case Speed:
                    player.flatSpeed += rollSpeed;
                    speedLimit += addRolls;
                    break;
                case CritRate:
                    player.critRate += rollCR;
                    crLimit += addRolls;
                    break;
                case CritDamage:
                    player.critDamage += rollCD;
                    cdLimit += addRolls;
                    break;
                case BreakEffect:
                    player.breakEffect += rollBE;
                    beLimit += addRolls;
                    break;
                case EffectHitRate:
                    player.effectHitRate += rollEHR;
                    ehrLimit += addRolls;
                    break;
                case EffectRes:
                    player.effectRes += rollEffRes;
                    effResLimit += addRolls;
                    break;
                case None:
                    break;
                default:
                    break;
            }
        }

        // Find which substat roll gives the highest average damage then add it
        for (int i = 0; i < addRolls * set.length; i++) {
            player.hpUp += rollHP;
            double damage1 = calculator.AverageDamage(player);
            player.hpUp -= rollHP;

            player.flatHp += rollFlatHP;
            double damage2 = calculator.AverageDamage(player);
            player.flatHp -= rollFlatHP;

            player.attackUp += rollAttack;
            double damage3 = calculator.AverageDamage(player);
            player.attackUp -= rollAttack;

            player.flatAttack += rollFlatAttack;
            double damage4 = calculator.AverageDamage(player);
            player.flatAttack -= rollFlatAttack;

            player.defenceUp += rollDefence;
            double damage5 = calculator.AverageDamage(player);
            player.defenceUp -= rollDefence;

            player.flatDefence += rollFlatDefence;
            double damage6 = calculator.AverageDamage(player);
            player.flatDefence -= rollFlatDefence;

            player.flatSpeed += rollSpeed;
            double damage7 = calculator.AverageDamage(player);
            player.flatSpeed -= rollSpeed;

            player.critRate += rollCR;
            double damage8 = calculator.AverageDamage(player);
            player.critRate -= rollCR;

            player.critDamage += rollCD;
            double damage9 = calculator.AverageDamage(player);
            player.critDamage -= rollCD;

            player.breakEffect += rollBE;
            double damage10 = calculator.AverageDamage(player);
            player.breakEffect -= rollBE;

            player.effectHitRate += rollEHR;
            double damage11 = calculator.AverageDamage(player);
            player.effectHitRate -= rollEHR;

            player.effectRes += rollEffRes;
            double damage12 = calculator.AverageDamage(player);
            player.effectRes -= rollEffRes;

            // If substat has no rolls left, then damage for that roll is void
            if (hpUpLimit == 0) {
                damage1 = 0;
            }
            if (flatHPLimit == 0) {
                damage2 = 0;
            }
            if (attackUpLimit == 0) {
                damage3 = 0;
            }
            if (flatAttackLimit == 0) {
                damage4 = 0;
            }
            if (defenceUpLimit == 0) {
                damage5 = 0;
            }
            if (flatDefenceLimit == 0) {
                damage6 = 0;
            }
            if (speedLimit == 0) {
                damage7 = 0;
            }
            if (crLimit == 0) {
                damage8 = 0;
            }
            if (cdLimit == 0) {
                damage9 = 0;
            }
            if (beLimit == 0) {
                damage10 = 0;
            }
            if (ehrLimit == 0) {
                damage11 = 0;
            }
            if (effResLimit == 0) {
                damage12 = 0;
            }

            if (damage1 >= max(damage2,damage3,damage4,damage5,damage6,damage7,damage8,damage9,damage10,damage11,damage12)) {
                player.hpUp += rollHP;
                hpUpLimit -= 1;
            }
            else if (damage2 >= max(damage1,damage3,damage4,damage5,damage6,damage7,damage8,damage9,damage10,damage11,damage12)) {
                player.flatHp += rollFlatHP;
                flatHPLimit -= 1;
            }
            else if (damage3 >= max(damage1,damage2,damage4,damage5,damage6,damage7,damage8,damage9,damage10,damage11,damage12)) {
                player.attackUp += rollAttack;
                attackUpLimit -= 1;
            }
            else if (damage4 >= max(damage1,damage2,damage3,damage5,damage6,damage7,damage8,damage9,damage10,damage11,damage12)) {
                player.flatAttack += rollFlatAttack;
                flatAttackLimit -= 1;
            }
            else if (damage5 >= max(damage1,damage2,damage3,damage4,damage6,damage7,damage8,damage9,damage10,damage11,damage12)) {
                player.defenceUp += rollDefence;
                defenceUpLimit -= 1;
            }
            else if (damage6 >= max(damage1,damage2,damage3,damage4,damage5,damage7,damage8,damage9,damage10,damage11,damage12)) {
                player.flatDefence += rollFlatDefence;
                flatDefenceLimit -= 1;
            }
            else if (damage7 >= max(damage1,damage2,damage3,damage4,damage5,damage6,damage8,damage9,damage10,damage11,damage12)) {
                player.flatSpeed += rollSpeed;
                speedLimit -= 1;
            }
            else if (damage8 >= max(damage1,damage2,damage3,damage4,damage5,damage6,damage7,damage9,damage10,damage11,damage12)) {
                player.critRate += rollCR;
                crLimit -= 1;
            }
            else if (damage9 >= max(damage1,damage2,damage3,damage4,damage5,damage6,damage7,damage8,damage10,damage11,damage12)) {
                player.critDamage += rollCD;
                cdLimit -= 1;
            }
            else if (damage10 >= max(damage1,damage2,damage3,damage4,damage5,damage6,damage7,damage8,damage9,damage11,damage12)) {
                player.breakEffect += rollBE;
                beLimit -= 1;
            }
            else if (damage11 >= max(damage1,damage2,damage3,damage4,damage5,damage6,damage7,damage8,damage9,damage10,damage12)) {
                player.effectHitRate += rollEHR;
                ehrLimit -= 1;
            }
            else if (damage12 >= max(damage1,damage2,damage3,damage4,damage5,damage6,damage7,damage8,damage9,damage10,damage11)) {
                player.effectRes += rollEffRes;
                effResLimit -= 1;
            }
            else {
                System.out.println("Error: Problem with checking next best substat roll");
            }
        }
        return calculator.AverageDamage(player);
    }

    //Prints player's stats and relic main stats
    public void printStats(Relic[] set) {
        double totalHP = (player.baseHp * ((player.hpUp/100)+1)) + player.flatHp;
        double totalAttack = (player.baseAttack * ((player.attackUp/100)+1)) + player.flatAttack;
        double totalDefence = (player.baseDefence * ((player.defenceUp/100)+1)) + player.flatDefence;
        double totalSpeed = (player.baseSpeed * ((player.speedUp/100)+1)) + player.flatSpeed;
        System.out.println("HP: "+totalHP);
        System.out.println("Attack: "+totalAttack);
        System.out.println("Defence: "+totalDefence);
        System.out.println("Speed: "+totalSpeed);
        System.out.println("Crit Rate: "+player.critRate);
        System.out.println("Crit Damage: "+player.critDamage);
        System.out.println("Damage Boost: "+player.damageBoost);
        System.out.println("Energy Regeneration Rate: "+player.energyRegeneration);
        System.out.println("Break Effect: "+player.breakEffect);
        System.out.println("Effect Hit Rate: "+player.effectHitRate);
        System.out.println("Effect Res: "+player.effectRes);
        System.out.println();
        System.out.println("Damage(on crit): "+calculator.CritDamage(player));
        System.out.println("Damage(no crit): "+calculator.Damage(player));
        System.out.println("Average Damage: "+calculator.AverageDamage(player));
        System.out.println();
        System.out.println("Body: "+set[2].main.name());
        System.out.println("Feet: "+set[3].main.name());
        System.out.println("Sphere: "+set[4].main.name());
        System.out.println("Rope: "+set[5].main.name());
    }

    public void printRelics(Relic[] set) {
        System.out.println("Head: "+set[0].main.name()+","+set[0].sub1.name()+","+set[0].sub2.name()+","+set[0].sub3.name()+","+set[0].sub4.name());
        System.out.println("Hand: "+set[1].main.name()+","+set[1].sub1.name()+","+set[1].sub2.name()+","+set[1].sub3.name()+","+set[1].sub4.name());
        System.out.println("Body: "+set[2].main.name()+","+set[2].sub1.name()+","+set[2].sub2.name()+","+set[2].sub3.name()+","+set[2].sub4.name());
        System.out.println("Feet: "+set[3].main.name()+","+set[3].sub1.name()+","+set[3].sub2.name()+","+set[3].sub3.name()+","+set[3].sub4.name());
        System.out.println("Sphere: "+set[4].main.name()+","+set[4].sub1.name()+","+set[4].sub2.name()+","+set[4].sub3.name()+","+set[4].sub4.name());
        System.out.println("Rope: "+set[5].main.name()+","+set[5].sub1.name()+","+set[5].sub2.name()+","+set[5].sub3.name()+","+set[5].sub4.name());
    }

    // Combines other methods to show you optimal relic main stats and stats
    public void Optimize() {
        Relic[] set = SetSubStats(SetMainStats());
        MaxDamage(set);
        printStats(set);
    }

    // Simple method that returns the highest value from any number of parameters
    public double max(double... values) {
        double highestVal = 0;
        for (double val : values) {
            if (val > highestVal) {
                highestVal = val;
            }
        }
        return highestVal;
    }
}