package code.cards;

import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.ModFile.makeID;

public class Alert extends AbstractEasyCard {
    public final static String ID = makeID("Alert");
    // intellij stuff skill, self, basic, , ,  5, 3, ,

    public Alert() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        ExhaustiveVariable.setBaseValue(this, 1);
        baseBlock = 4;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        blck();
}

    public void upp() {
        upgradeBlock(2);
        ExhaustiveVariable.upgrade(this, 1);
        this.initializeDescription();

    }
}