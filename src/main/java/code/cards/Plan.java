package code.cards;

import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import code.actions.FlipCardsAction;
import code.cards.abstractCards.AbstractFlipCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.CharacterFile.Enums.TEAL_COLOR;
import static code.ModFile.makeID;
@NoPools
public class Plan extends AbstractFlipCard {
    public final static String ID = makeID("Plan");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public Plan() {
        this(new Dream(null));
    }

    public Plan(AbstractFlipCard linkedCard) {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF, TEAL_COLOR);
        baseMagicNumber = magicNumber = 3;
        initializeDescription();
        if (linkedCard == null) {
            this.setLinkedCard(new Dream(this));
        } else {
            this.setLinkedCard(linkedCard);
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(p, magicNumber));
    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
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
        return new Plan(null);
    }
}