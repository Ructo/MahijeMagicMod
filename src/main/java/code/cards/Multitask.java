package code.cards;

import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.actions.utility.QueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import code.cards.AbstractEasyCard;

import static code.ModFile.makeID;

public class Multitask extends AbstractEasyCard {
    public static final String ID = makeID("Multitask");
    private static boolean looping;

    public Multitask() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        this.exhaust = true;
        // Initialize cardsToPreview with a default card (e.g., Dazed)
        this.cardsToPreview = new Dazed();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.upgraded) {
            AbstractCard lastCardPlayed = getLastNonMultitaskCardPlayed();
            if (lastCardPlayed != null) {
                AbstractCard cardToReplay = lastCardPlayed.makeStatEquivalentCopy();
                cardToReplay.freeToPlayOnce = true;

                AbstractMonster targetMonster = (lastCardPlayed.target == AbstractCard.CardTarget.ENEMY) ? AbstractDungeon.getRandomMonster() : null;
                AbstractDungeon.actionManager.addToBottom(new QueueCardAction(cardToReplay, targetMonster));
            }
        }

        // Play the top card of the draw pile.
        AbstractDungeon.actionManager.addToBottom(new PlayTopCardAction(null, false));
    }

    private AbstractCard getLastNonMultitaskCardPlayed() {
        for (int i = AbstractDungeon.actionManager.cardsPlayedThisTurn.size() - 1; i >= 0; i--) {
            AbstractCard card = AbstractDungeon.actionManager.cardsPlayedThisTurn.get(i);
            if (!card.cardID.equals(Multitask.ID)) {
                return card;
            }
        }
        return null; // Return null if no suitable card is found.
    }

    public void applyPowers() {
        super.applyPowers();
        if (this.cardsToPreview != null && !looping) {
            looping = true;
            this.cardsToPreview.applyPowers();
            looping = false;
        }
    }

    @Override
    public void upp() {
        if (this.cardsToPreview != null && !looping) {
            looping = true;
            this.cardsToPreview.upgrade();
            looping = false;
        }

        this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        initializeDescription();
    }

    @Override
    public void hover() {
        super.hover();
        updatePreview();
    }

    private void updatePreview() {
        AbstractCard lastCardPlayed = getLastNonMultitaskCardPlayed();
        if (lastCardPlayed != null) {
            this.cardsToPreview = lastCardPlayed.makeStatEquivalentCopy();
        } else {
            // Set cardsToPreview to a default card (e.g., Dazed)
            this.cardsToPreview = new Dazed();
        }
    }
}