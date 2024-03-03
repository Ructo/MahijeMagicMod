package code.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.PummelDamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.ModFile.makeID;

public class Flail extends AbstractEasyCard {
    public final static String ID = makeID("Flail");
    // intellij stuff ATTACK, ENEMY, COMMON, 1, 1, 1, 1, 0, 0

    public Flail() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 2;
        baseMagicNumber = magicNumber = 3;
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 1; i < this.magicNumber; i++)
            addToBot(new PummelDamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn)));
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}
