package code.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import code.powers.Tasty;

import static code.ModFile.makeID;

public class Supertaster extends AbstractEasyCard {
    public final static String ID = makeID( "Supertaster");

    public Supertaster() {
        super(ID, 2, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = 1; // This represents the amount to modify buffs/debuffs.
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        p.addPower(new Tasty(p, this.magicNumber));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(1); // As an example upgrade, reduce the cost.
        }
    }
}
