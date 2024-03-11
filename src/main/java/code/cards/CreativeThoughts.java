package code.cards;

import basemod.cardmods.EtherealMod;
import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
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
@NoPools

public class CreativeThoughts extends AbstractFlipCard {
    public final static String ID = makeID("CreativeThoughts");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public CreativeThoughts() {
        this(new RacingThoughts(null));
    }

    public CreativeThoughts(AbstractFlipCard linkedCard) {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF, TEAL_COLOR);
        this.exhaust = true;
        baseMagicNumber = magicNumber = 2;
        if (linkedCard == null) {
            this.setLinkedCard(new RacingThoughts(this));
        } else {
            this.setLinkedCard(linkedCard);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(p, 1)); // Draw 1 card

        ArrayList<AbstractCard> eligibleCardsList = getCardsMatchingPredicate(c -> c.cost == 0, true);
        Collections.shuffle(eligibleCardsList); // Shuffle the list of eligible cards

        for (int i = 0; i < Math.min(eligibleCardsList.size(), magicNumber); i++) {
            AbstractCard modifiedCard = eligibleCardsList.get(i).makeStatEquivalentCopy(); // Create a copy of the card

            // Add Ethereal and Exhaust modifiers to the modifiedCard
            CardModifierManager.addModifier(modifiedCard, new EtherealMod());
            CardModifierManager.addModifier(modifiedCard, new ExhaustMod());

            addToBot(new MakeTempCardInHandAction(modifiedCard, 1, true));
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
            AbstractDungeon.actionManager.addToBottom(new FlipCardsAction(this, newCard));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new CreativeThoughts(null);
    }
}