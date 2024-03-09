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

public class CreativeThoughts extends AbstractSwappableCard {
    public final static String ID = makeID("CreativeThoughts");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public CreativeThoughts() {
        this(new RacingThoughts(null));
    }

    public CreativeThoughts(AbstractSwappableCard linkedCard) {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF, TEAL_COLOR);
        ExhaustiveVariable.setBaseValue(this, 3);
        baseMagicNumber = magicNumber = 2;
        if (linkedCard == null) {
            this.setLinkedCard(new RacingThoughts(this));
        } else {
            this.setLinkedCard(linkedCard);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(p, 1));
        for (int i = 0; i < magicNumber; i++) {
            ArrayList<AbstractCard> eligibleCardsList = getCardsMatchingPredicate(c -> c.cost == 0, true);
            Collections.shuffle(eligibleCardsList); // Shuffle the list of eligible cards
            AbstractCard randomCard = eligibleCardsList.get(0); // Select the first card from the shuffled list
            addToBot(new MakeTempCardInHandAction(randomCard));
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            this.cardsToPreview.upgrade();
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
        return new CreativeThoughts(null);
    }
}