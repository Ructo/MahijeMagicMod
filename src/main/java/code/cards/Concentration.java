package code.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.powers.DexterityPower;

import static code.ModFile.makeID;

public class Concentration extends AbstractEasyCard {
    public final static String ID = makeID("Concentration");
    // intellij stuff SKILL, SELF, COMMON, 7, 10, 0, 0, 0, 0
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public Concentration() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
        baseSecondMagic = secondMagic = 1;
        exhaust = true;
        this.rawDescription = DESCRIPTION;
        initializeDescription();

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.upgraded) {
            addToBot((AbstractGameAction) new ExhaustAction(1, false));
            addToBot((AbstractGameAction) new ApplyPowerAction((AbstractCreature) p, (AbstractCreature) p, (AbstractPower) new StrengthPower((AbstractCreature) p, this.magicNumber)));
            addToBot((AbstractGameAction) new ApplyPowerAction((AbstractCreature) p, (AbstractCreature) p, (AbstractPower) new DexterityPower((AbstractCreature) p, this.secondMagic)));
        } else {
            addToBot((AbstractGameAction) new ExhaustAction(1, true, false, false));
            addToBot((AbstractGameAction) new ApplyPowerAction((AbstractCreature) p, (AbstractCreature) p, (AbstractPower) new StrengthPower((AbstractCreature) p, this.magicNumber)));
            addToBot((AbstractGameAction) new ApplyPowerAction((AbstractCreature) p, (AbstractCreature) p, (AbstractPower) new DexterityPower((AbstractCreature) p, this.secondMagic)));
        }
    }

    public void upp() {
        upgradeMagicNumber(1);
        upgradeSecondMagic(0);
        upgradeBaseCost(0);
        this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        initializeDescription();

    }
}