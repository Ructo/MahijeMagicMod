package code.cards;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.ExhaustiveField;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.ArrayList;
import java.util.Collections;

import static code.ModFile.makeID;
import static code.util.Wiz.getCardsMatchingPredicate;

public class RacingThoughts extends AbstractEasyCard {
    public final static String ID = makeID("RacingThoughts");

    public RacingThoughts() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        ExhaustiveVariable.setBaseValue(this, 3);
        baseMagicNumber = magicNumber = 2; // Draw 2 cards
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> eligibleCardsList = getCardsMatchingPredicate(c -> c.cost == 0, true);
        Collections.shuffle(eligibleCardsList); // Shuffle the list of eligible cards
        AbstractCard randomCard = eligibleCardsList.get(0); // Select the first card from the shuffled list

        addToBot(new DrawCardAction(p, magicNumber)); // Draw 2 cards
        addToBot(new MakeTempCardInHandAction(randomCard)); // Add the random card to hand

    }

    public void upp() {
        upgradeMagicNumber(1);
    }

    @Override
    public AbstractCard makeCopy() {
        return new RacingThoughts();
    }
}