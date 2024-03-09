package code.cards;

import code.actions.SwapCardsAction;
import code.cards.abstractCards.AbstractSwappableCard;
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

public class HyperBlock extends AbstractSwappableCard {
    public final static String ID = makeID("HyperBlock");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public HyperBlock() {
        this(new HyperStrike(null));
    }

    public HyperBlock(AbstractSwappableCard linkedCard) {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.SELF, TEAL_COLOR);
        baseBlock = 9;
        baseMagicNumber = magicNumber = 1;
        initializeDescription();
        if (linkedCard == null) {
            this.setLinkedCard(new HyperStrike(this));
        } else {
            this.setLinkedCard(linkedCard);
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        addToBot(new DrawCardAction(p, magicNumber));
    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(1);
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
            AbstractDungeon.actionManager.addToBottom(new SwapCardsAction(this, newCard));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new HyperBlock(null);
    }
}