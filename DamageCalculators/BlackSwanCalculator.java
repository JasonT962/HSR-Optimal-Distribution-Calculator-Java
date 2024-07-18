package DamageCalculators;

import Player.*;

public class BlackSwanCalculator extends DamageCalculator {
    public double Damage(Player player) {
        double totalHp = (player.baseHp * ((player.hpUp/100)+1)) + player.flatHp;
        double totalAttack = (player.baseAttack * ((player.attackUp/100)+1)) + player.flatAttack;
        double totalDefence = (player.baseDefence * ((player.defenceUp/100)+1)) + player.flatDefence;
        double skillMultiplier = player.skillMultiplier/100;

        // Black Swan passive
        double passive = (player.effectHitRate * 0.6);
        if (passive > 72) {
            passive = 72;
        }

        double damageBoost = ((player.damageBoost + passive)/100)+1;
        

        double resDamageReduction = 1 - (player.enemyResistance - (player.resistancePenetration + player.resistanceReduction)) / 100;
        if (resDamageReduction < 0.1) {
            resDamageReduction = 0.1; // Resistance cannot go above 90%
        }
        else if (resDamageReduction > 2) {
            resDamageReduction = 2; // Resistance cannot go below -100%
        }

        // Calculations for enemy defence
        double defIgnoreReduction = player.defenceIgnore + player.defenceReduction;
        if (defIgnoreReduction > 100) {
            defIgnoreReduction = 100;
        }
        double defDamageReduction = (player.characterLevel+20) / ((1-((defIgnoreReduction)/100))*(player.enemyLevel+20) + player.characterLevel + 20);

        double damageTaken = 1 + (player.damageTaken/100);
        double enemyToughness = 1 - (player.enemyToughness/100);
        
        if (player.damageScaling == Player.scaling.Attack) {
            return totalAttack * skillMultiplier * damageBoost * resDamageReduction * defDamageReduction * damageTaken * enemyToughness;
        }
        else if (player.damageScaling == Player.scaling.Defence) {
            return totalDefence * skillMultiplier * damageBoost * defDamageReduction * resDamageReduction * damageTaken * enemyToughness;
        }
        else if (player.damageScaling == Player.scaling.HP) {
            return totalHp * skillMultiplier * damageBoost * defDamageReduction * resDamageReduction * damageTaken * enemyToughness;
        }
        else {
            System.out.println("Error in damage calculator class");
            return -1;
        }
    }

    public double CritDamage(Player player) {
        double critDamage = ((player.critDamage/100)+1);
        return critDamage * Damage(player);
    }

    public double AverageDamage(Player player) {
        double critRate = player.critRate;
        if (critRate >= 100) {
            critRate = 100;
        }
        else if (critRate <= 0) {
            critRate = 0;
        }
        critRate = critRate/100;
        double critDamage = ((player.critDamage/100)+1);
        
        return ((critRate * critDamage) + (1 - critRate)) * Damage(player);
    }
}
