package code.cards;

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

    private static final int USES_UNTIL_EXHAUST = 3;
    private int usesRemaining = USES_UNTIL_EXHAUST;

    public RacingThoughts() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2; // Draw 2 cards
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> eligibleCardsList = getCardsMatchingPredicate(c -> c.cost == 0, true);
        Collections.shuffle(eligibleCardsList); // Shuffle the list of eligible cards
        AbstractCard randomCard = eligibleCardsList.get(0); // Select the first card from the shuffled list

        addToBot(new DrawCardAction(p, magicNumber)); // Draw 2 cards
        addToBot(new MakeTempCardInHandAction(randomCard)); // Add the random card to hand

        usesRemaining--;
        if (usesRemaining <= 0) {
            exhaust = true; // Exhaust the card after being played 3 times
        }
    }

    public void upp() {
        upgradeBaseCost(0);
    }

    @Override
    public AbstractCard makeCopy() {
        return new RacingThoughts();
    }
}