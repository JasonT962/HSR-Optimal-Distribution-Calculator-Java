package Player;
public class Relic {
    public enum mainstat {
        HP, FlatHP, Attack, FlatAttack, Defence, Speed, CritRate,
        CritDamage, EnergyRegeneration, BreakEffect, EffectHitRate, EffectRes,
        DamageBoost, HealingBoost, None
    }

    public enum substat {
        HP, FlatHP, Attack, FlatAttack, Defence, FlatDefence, Speed,
        CritRate, CritDamage, BreakEffect, EffectHitRate, EffectRes, None
    }

    public mainstat main;
    public substat sub1;
    public substat sub2;
    public substat sub3;
    public substat sub4;

    public Relic() {
        main = mainstat.None;
        sub1 = substat.None;
        sub2 = substat.None;
        sub3 = substat.None;
        sub4 = substat.None;
    }

    public Relic(mainstat main, substat sub1, substat sub2, substat sub3, substat sub4) {
        this.main = main;
        this.sub1 = sub1;
        this.sub2 = sub2;
        this.sub3 = sub3;
        this.sub4 = sub4;
    }
}
