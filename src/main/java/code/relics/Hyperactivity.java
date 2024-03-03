package code.relics;

import code.CharacterFile;
import code.cards.Concentration;
import code.cards.RacingThoughts;
import code.cards.Struggle;
import code.cards.democards.complex.CreativeMind;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Random;

import static code.ModFile.makeID;

public class Hyperactivity extends AbstractEasyRelic {
    public static final String ID = makeID("Hyperactivity");

    public Hyperactivity() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT, CharacterFile.Enums.TEAL_COLOR);
        this.counter = 0;
    }

    @Override
    public void onExhaust(AbstractCard card) {
        this.counter++;
        if (this.counter >= 3) {
            this.counter -= 3;
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
        }
    }


    @Override
    public void atTurnStartPostDraw() {
        if (shouldAddStruggle()) {
            flash(); // Flash the relic when it activates

            // Create the Struggle card and check for the StruggleBus relic
            AbstractCard struggleCard = new Struggle();
            if (AbstractDungeon.player.hasRelic(StruggleBus.ID)) {
                struggleCard.upgrade();
            }
            // Add the (potentially upgraded) Struggle card to the player's hand
            addToBot(new MakeTempCardInHandAction(struggleCard, 1));
            addToBot(new MakeTempCardInHandAction(struggleCard.makeStatEquivalentCopy(), 1));
            addToBot(new MakeTempCardInHandAction(struggleCard.makeStatEquivalentCopy(), 1));

            // Generate random dialogue
            String[] dialogueOptions = {"Uh oh", "Oh no...", "Planned"};
            Random random = new Random();
            int randomIndex = random.nextInt(dialogueOptions.length);
            String randomDialogue = dialogueOptions[randomIndex];

            AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new TalkAction(true, randomDialogue, 1.2F, 1.2F));

        }
    }
    private boolean shouldAddStruggle() {
        boolean isEffectivelyEmptyHand = AbstractDungeon.player.hand.group.stream()
                .allMatch(card -> card.type == AbstractCard.CardType.CURSE || card.type == AbstractCard.CardType.STATUS);

        boolean isEffectivelyEmptyDrawPile = AbstractDungeon.player.drawPile.group.stream()
                .allMatch(card -> card.type == AbstractCard.CardType.CURSE || card.type == AbstractCard.CardType.STATUS);

        boolean isEffectivelyEmptyDiscardPile = AbstractDungeon.player.discardPile.group.stream()
                .allMatch(card -> card.type == AbstractCard.CardType.CURSE || card.type == AbstractCard.CardType.STATUS);

        // Check if all piles are empty or effectively empty (only contain unplayable cards)
        return (AbstractDungeon.player.hand.isEmpty() || isEffectivelyEmptyHand) &&
                (AbstractDungeon.player.drawPile.isEmpty() || isEffectivelyEmptyDrawPile) &&
                (AbstractDungeon.player.discardPile.isEmpty() || isEffectivelyEmptyDiscardPile);
    }
}