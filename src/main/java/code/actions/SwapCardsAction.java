//
// Source code recreated from a .class file by IntelliJ IDEA


package code.actions;

import code.cards.abstractCards.AbstractSwappableCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.actions.common.TransformCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.Iterator;

public class SwapCardsAction extends AbstractGameAction {
    private AbstractCard toReplace;
    private AbstractCard newCard;

    public SwapCardsAction(AbstractCard toReplace, AbstractCard newCard) {
        this.actionType = ActionType.SPECIAL;
        this.duration = Settings.ACTION_DUR_MED;
        this.toReplace = toReplace;
        this.newCard = newCard;
    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        int index = 0;
        boolean found = false;

        for(Iterator var4 = p.hand.group.iterator(); var4.hasNext(); ++index) {
            AbstractCard card = (AbstractCard)var4.next();
            if (card == this.toReplace) {
                found = true;
                break;
            }
        }

        if (found && this.toReplace != null) {
            if (this.toReplace instanceof AbstractSwappableCard && this.newCard instanceof AbstractSwappableCard) {
                ((AbstractSwappableCard)this.toReplace).onSwapOut();
                ((AbstractSwappableCard)this.newCard).onSwapIn();
            }

            this.newCard.cardsToPreview = this.toReplace.makeStatEquivalentCopy();
            this.newCard.applyPowers();
            this.newCard.cardsToPreview.applyPowers();



            if (AbstractDungeon.player.hoveredCard == this.toReplace) {
                AbstractDungeon.player.releaseCard();
            }

            AbstractDungeon.actionManager.cardQueue.removeIf((q) -> {
                return q.card == this.toReplace;
            });
            this.addToTop(new UpdateAfterTransformAction(this.newCard));
            this.addToTop(new TransformCardInHandAction(index, this.newCard));
        }

        this.isDone = true;
    }
}
