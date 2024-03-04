//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package code.cards.abstractCards;

import code.actions.SwapCardsAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public abstract class AbstractSwappableCard extends AbstractClickableCard {
    private AbstractGameAction action;
    private static boolean looping;

    public AbstractSwappableCard(String id, int cost, AbstractCard.CardType type, AbstractCard.CardRarity rarity, AbstractCard.CardTarget target, AbstractCard.CardColor color) {
        super(id, cost, type, rarity, target, color);
    }

    protected void setLinkedCard(AbstractSwappableCard linkedCard) {
        if (linkedCard != null) {
            this.cardsToPreview = linkedCard;
            this.cardsToPreview.cardsToPreview = this;
        }

    }

    public void upgrade() {
        if (this.cardsToPreview != null && !looping) {
            looping = true;
            this.cardsToPreview.upgrade();
            looping = false;
        }
    }

    public void applyPowers() {
        super.applyPowers();
        if (this.cardsToPreview != null && !looping) {
            looping = true;
            this.cardsToPreview.applyPowers();
            looping = false;
        }
    }

    public void onRightClick() {
        if (this.canSwap() && this.action == null && this.cardsToPreview != null) {
            CardCrawlGame.sound.play("CARD_SELECT", 0.1F);
            this.action = new SwapCardsAction(this, this.cardsToPreview);
            this.addToTop(this.action);
        }

    }

    public void update() {
        super.update();
        if (this.action != null && this.action.isDone) {
            this.action = null;
        }

    }

    public boolean canSwap() {
        return true;
    }

    public void onSwapOut() {
    }

    public void onSwapIn() {
    }
}
