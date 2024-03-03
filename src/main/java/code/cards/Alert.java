package code.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.ModFile.makeID;

public class Alert extends AbstractEasyCard {
    public final static String ID = makeID("Alert");
    // intellij stuff skill, self, basic, , ,  5, 3, ,
    private static final int USES_UNTIL_EXHAUST = 3;

    private int usesRemaining = USES_UNTIL_EXHAUST;
    public Alert() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 4;
        baseMagicNumber = magicNumber = USES_UNTIL_EXHAUST;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        blck();

        usesRemaining--;
        updateDescription();
        if (usesRemaining <= 0) {
            this.exhaust = true;
            this.usesRemaining = USES_UNTIL_EXHAUST;
        }
    }
    private void updateDescription() {
        this.rawDescription = "Gain !B! Block then gain !B! Block more! Exhaustive: " + usesRemaining;
        this.initializeDescription();
    }

    public void upp() {
        upgradeBlock(2);
        this.initializeDescription();

    }
}