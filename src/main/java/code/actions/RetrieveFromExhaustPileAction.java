package code.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.Collections;

public class RetrieveFromExhaustPileAction extends AbstractGameAction {
    private AbstractPlayer p;

    public RetrieveFromExhaustPileAction() {
        this.p = AbstractDungeon.player;
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.p.exhaustPile.isEmpty()) {
                this.isDone = true;
                return;
            }

            if (this.p.hand.size() == 10) {
                this.p.createHandIsFullDialog();
                this.isDone = true;
                return;
            }

            Collections.shuffle(this.p.exhaustPile.group); // Shuffle the exhaust pile
            AbstractCard card = this.p.exhaustPile.getTopCard(); // Get the top card after shuffling

            card.unfadeOut();
            this.p.hand.addToHand(card);
            this.p.exhaustPile.removeCard(card);

            card.unhover();
            card.fadingOut = false;
            this.p.hand.refreshHandLayout();
            this.p.exhaustPile.group.forEach(c -> {
                c.unhover();
                c.target_x = CardGroup.DISCARD_PILE_X;
                c.target_y = 0.0F;
            });

            this.isDone = true;
        }
        tickDuration();
    }
}
