package code.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.ModFile.makeID;

public class Defend extends AbstractEasyCard {
    public final static String ID = makeID("Defend");
    // intellij stuff skill, self, basic, , ,  5, 3, , 
    private static final int USES_UNTIL_EXHAUST = 3;

    private int usesRemaining = USES_UNTIL_EXHAUST;
    public Defend() {
        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
        baseBlock = 5;
        tags.add(CardTags.STARTER_DEFEND);
        baseMagicNumber = magicNumber = USES_UNTIL_EXHAUST;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();

    usesRemaining--;
    updateDescription();
        if (usesRemaining <= 0) {
        this.exhaust = true;
        this.usesRemaining = USES_UNTIL_EXHAUST;
    }
}
private void updateDescription() {
    this.rawDescription = "Gain !B! Block. Exhaustive: " + usesRemaining;
    this.initializeDescription();
}

public void upp() {
    upgradeMagicNumber(-1);
    upgradeBlock(3);
    this.initializeDescription();

}
}