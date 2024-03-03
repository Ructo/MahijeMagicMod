package code.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import code.powers.SupertasterPower;
import basemod.abstracts.CustomCard;

import static code.ModFile.makeID;

public class Supertaster extends AbstractEasyCard {
    public final static String ID = makeID( "Supertaster");

    public Supertaster() {
        super(ID, 1, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = 1; // This represents the amount to modify buffs/debuffs.
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        p.addPower(new SupertasterPower(p, this.magicNumber));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(0); // As an example upgrade, reduce the cost.
        }
    }
}
