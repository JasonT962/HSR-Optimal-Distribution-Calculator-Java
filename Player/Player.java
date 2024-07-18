package Player;
public class Player {
    public double baseHp = 0;
    public double hpUp = 0;
    public double flatHp = 0;

    public double baseAttack = 0;
    public double attackUp = 0;
    public double flatAttack = 0;

    public double baseDefence = 0;
    public double defenceUp = 0;
    public double flatDefence = 0;

    public double baseSpeed = 0;
    public double speedUp = 0;
    public double flatSpeed = 0;

    public double critRate = 5;
    public double critDamage = 50;

    public double damageBoost =  0;
    public double energyRegeneration = 0;
    public double breakEffect = 0;
    public double effectHitRate = 0;
    public double effectRes = 0;
    public double healingBoost = 0;

    public double skillMultiplier = 0;
    public double dualMultiplier = 0;

    // Extra
    public double characterLevel = 80;
    public double enemyLevel = 82;

    // Weak = 0, Normal = 20, Resist = 40
    public double enemyResistance = 20;
    public double resistancePenetration = 0;
    public double resistanceReduction = 0;

    public double defenceIgnore = 0;
    public double defenceReduction = 0;

    public double damageTaken = 0;

    // Enemy broken = 0, not broken = 10
    public double enemyToughness = 0;

    public scaling damageScaling = scaling.Attack;

    public enum scaling {
        Attack, Defence, HP
    }
}
