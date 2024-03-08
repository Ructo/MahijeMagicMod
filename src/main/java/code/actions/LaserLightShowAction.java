package code.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import code.cards.tempcards.Lasers;

public class LaserLightShowAction extends AbstractGameAction {
    private final boolean upgrade;

    public LaserLightShowAction(boolean upgraded) {
        this.upgrade = upgraded;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = DEFAULT_DURATION; // Ensure duration is set correctly
    }

    @Override
    public void update() {
        if (this.duration == this.startDuration) {
            if (!AbstractDungeon.player.hand.isEmpty()) {
                AbstractDungeon.handCardSelectScreen.open("Select cards to exhaust.", AbstractDungeon.player.hand.size(), true, true, false, false, true);
                this.tickDuration();
                return;
            }
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            int selectedCardsCount = AbstractDungeon.handCardSelectScreen.selectedCards.group.size();
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                AbstractDungeon.player.hand.moveToExhaustPile(c);
            }

            // Creating Lasers cards after exhausting selected cards
            for (int i = 0; i < selectedCardsCount; i++) {
                AbstractCard laser = new Lasers();
                if (upgrade) {
                    laser.upgrade();
                }
                addToBot(new MakeTempCardInHandAction(laser, true));
            }

            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone = true;
        }
    }
}
