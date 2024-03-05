package code.cards;

import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.actions.utility.QueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import code.cards.AbstractEasyCard;

import static code.ModFile.makeID;

public class Multitask extends AbstractEasyCard {
    public static final String ID = makeID("Multitask");

    public Multitask() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        this.exhaust = true;
        // Initialize cardsToPreview here if you want a default preview card
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Play the top card of the draw pile.
        AbstractCard lastCardPlayed = getLastNonMultitaskCardPlayed();
        AbstractDungeon.actionManager.addToBottom(new PlayTopCardAction(AbstractDungeon.getCurrRoom().monsters.getRandomMonster(null, true, AbstractDungeon.cardRandomRng), false));

        if (lastCardPlayed != null) {
            AbstractCard cardToReplay = lastCardPlayed.makeStatEquivalentCopy();
            cardToReplay.freeToPlayOnce = true;

            // Check if the last played card targets ALL enemies and adjust action accordingly
            if (lastCardPlayed.target == AbstractCard.CardTarget.ALL_ENEMY) {
                // Use QueueCardAction with the card and flag it to be auto-played
                AbstractDungeon.actionManager.addToBottom(new QueueCardAction(cardToReplay, null));
            } else {
                // Use QueueCardAction with the card and the target monster
                AbstractDungeon.actionManager.addToBottom(new QueueCardAction(cardToReplay, m));
            }


            // Update preview with the last played card
            this.cardsToPreview = lastCardPlayed.makeStatEquivalentCopy();
        }
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

    @Override
    public void upp() {
        upgradeBaseCost(1); // Reduce the energy cost to 1 upon upgrade.
    }
}
