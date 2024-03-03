package code.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.ModFile.makeID;

public class Struggle extends AbstractEasyCard {
    public final static String ID = makeID("Struggle");
    // intellij stuff ATTACK, ENEMY, COMMON, 1, 1, 1, 1, 0, 0

    public Struggle() {
        super(ID, 1, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY);
        baseDamage = 1;
        baseBlock = 1;
        this.exhaust = false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
    }


    public void upp() {
        upgradeDamage(4);
        upgradeBlock(2);
        upgradeMagicNumber(0);
        upgradeBaseCost(0);
        this.exhaust = true;

    }
}