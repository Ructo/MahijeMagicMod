package code.cards;

import basemod.cardmods.EtherealMod;
import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import code.actions.FlipCardsAction;
import code.cards.abstractCards.AbstractFlipCard;
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

public class RacingThoughts extends AbstractFlipCard {
    public final static String ID = makeID("RacingThoughts");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public RacingThoughts() {
        this(new CreativeThoughts(null));
    }

    public RacingThoughts(AbstractFlipCard linkedCard) {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF, TEAL_COLOR);
        this.exhaust = true;
        baseMagicNumber = magicNumber = 2; // Draw 2 cards
        if (linkedCard == null) {
            this.setLinkedCard(new CreativeThoughts(this));
        } else {
            this.setLinkedCard(linkedCard);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(p, magicNumber)); // Draw 2 cards

        ArrayList<AbstractCard> eligibleCardsList = getCardsMatchingPredicate(c -> c.cost == 0, true);
        if (!eligibleCardsList.isEmpty()) { // Ensure there is at least one card to select
            Collections.shuffle(eligibleCardsList); // Shuffle the list of eligible cards
            AbstractCard randomCard = eligibleCardsList.get(0).makeStatEquivalentCopy(); // Create a copy of the first card

            // Add Ethereal and Exhaust modifiers to the randomCard
            CardModifierManager.addModifier(randomCard, new EtherealMod());
            CardModifierManager.addModifier(randomCard, new ExhaustMod());

            addToBot(new MakeTempCardInHandAction(randomCard, 1, true)); // Add the modified random card to hand
        }
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
        return new RacingThoughts(null);
    }
}