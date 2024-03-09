package code.cards;

import code.actions.SwapCardsAction;
import code.cards.abstractCards.AbstractSwappableCard;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.ExhaustiveField;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.ArrayList;
import java.util.Collections;

import static code.CharacterFile.Enums.TEAL_COLOR;
import static code.ModFile.makeID;
import static code.util.Wiz.getCardsMatchingPredicate;

public class RacingThoughts extends AbstractSwappableCard {
    public final static String ID = makeID("RacingThoughts");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public RacingThoughts() {
        this(new CreativeThoughts(null));
    }

    public RacingThoughts(AbstractSwappableCard linkedCard) {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF, TEAL_COLOR);
        ExhaustiveVariable.setBaseValue(this, 3);
        baseMagicNumber = magicNumber = 2; // Draw 2 cards
        if (linkedCard == null) {
            this.setLinkedCard(new CreativeThoughts(this));
        } else {
            this.setLinkedCard(linkedCard);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> eligibleCardsList = getCardsMatchingPredicate(c -> c.cost == 0, true);
        Collections.shuffle(eligibleCardsList); // Shuffle the list of eligible cards
        AbstractCard randomCard = eligibleCardsList.get(0); // Select the first card from the shuffled list

        addToBot(new DrawCardAction(p, magicNumber)); // Draw 2 cards
        addToBot(new MakeTempCardInHandAction(randomCard)); // Add the random card to hand

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
            AbstractDungeon.actionManager.addToBottom(new SwapCardsAction(this, newCard));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new RacingThoughts(null);
    }
}