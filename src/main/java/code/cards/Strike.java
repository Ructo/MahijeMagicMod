package code.cards;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.ExhaustiveField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.ModFile.makeID;

public class Strike extends AbstractEasyCard {
    public final static String ID = makeID("Strike");
    // intellij stuff attack, enemy, basic, 6, 3,  , , , 
    private static final int USES_UNTIL_EXHAUST = 3;

    private int usesRemaining = USES_UNTIL_EXHAUST;
    public Strike() {
        super(ID, 1, CardType.ATTACK, CardRarity.BASIC, CardTarget.ENEMY);
        baseDamage = 6;
        tags.add(CardTags.STRIKE);
        tags.add(CardTags.STARTER_STRIKE);
        baseMagicNumber = magicNumber = USES_UNTIL_EXHAUST;

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);

    usesRemaining--;
        updateDescription();
        if (usesRemaining <= 0) {
        this.exhaust = true; // The card will now exhaust when used
        this.usesRemaining = USES_UNTIL_EXHAUST; // Reset the counter if you want the effect to apply every time the card is obtained
    }
        }
    private void updateDescription() {
        this.rawDescription = "Deal !D! damage. Exhaustive: " + usesRemaining;
        this.initializeDescription();
    }

    public void upp() {
        upgradeMagicNumber(-1);
        upgradeDamage(3);
        this.initializeDescription();
    }
}
