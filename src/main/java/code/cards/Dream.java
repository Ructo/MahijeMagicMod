package code.cards;

import code.actions.FlipCardsAction;
import code.cards.abstractCards.AbstractFlipCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static code.CharacterFile.Enums.TEAL_COLOR;
import static code.ModFile.makeID;

public class Dream extends AbstractFlipCard {
    public final static String ID = makeID("Dream");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public Dream() {
        this(new Plan(null));
    }

    public Dream(AbstractFlipCard linkedCard) {
        super(ID, 2, CardType.SKILL, CardRarity.RARE, CardTarget.SELF, TEAL_COLOR);
        baseMagicNumber = magicNumber = 2;
        this.exhaust = true;
        initializeDescription();
        if (linkedCard == null) {
            this.setLinkedCard(new Plan(this));
        } else {
            this.setLinkedCard(linkedCard);
        }
    }
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower(StrengthPower.POWER_ID)) {
            int strAmt = p.getPower(StrengthPower.POWER_ID).amount;
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, strAmt), strAmt));
        }
        if (p.hasPower(DexterityPower.POWER_ID)) {
            int dexAmt = p.getPower(DexterityPower.POWER_ID).amount;
            addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, dexAmt), dexAmt));
        }
    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(1);
            this.cardsToPreview.upgrade();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public void onRightClick() {
        if (AbstractDungeon.player != null && !AbstractDungeon.isScreenUp) {
            AbstractCard newCard = this.cardsToPreview.makeStatEquivalentCopy();
            AbstractDungeon.actionManager.addToBottom(new FlipCardsAction(this, newCard));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Dream(null);
    }
}