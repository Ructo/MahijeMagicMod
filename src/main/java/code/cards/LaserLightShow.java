package code.cards;

import code.actions.LaserLightShowAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.unique.BladeFuryAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import code.cards.tempcards.Lasers;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.ModFile.makeID;

public class LaserLightShow extends AbstractEasyCard {
    public static final String ID = makeID("LaserLightShow");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public LaserLightShow() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        this.cardsToPreview = new Lasers();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LaserLightShowAction(this.upgraded));
    }

    public AbstractCard makeCopy() {
        return new LaserLightShow();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.cardsToPreview.upgrade();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}