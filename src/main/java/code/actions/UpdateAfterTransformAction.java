//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package code.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class UpdateAfterTransformAction extends AbstractGameAction {
    private final AbstractCard cardToUpdate;

    public UpdateAfterTransformAction(AbstractCard cardToUpdate) {
        this.cardToUpdate = cardToUpdate;
    }

    public void update() {
        AbstractDungeon.player.hand.applyPowers();
        AbstractDungeon.player.hand.glowCheck();
        this.cardToUpdate.superFlash();
        this.cardToUpdate.initializeDescription();
        this.isDone = true;
    }
}
