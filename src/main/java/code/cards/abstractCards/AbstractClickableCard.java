//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package code.cards.abstractCards;

import code.cards.AbstractEasyCard;
import com.evacipated.cardcrawl.mod.stslib.patches.HitboxRightClick;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public abstract class AbstractClickableCard extends AbstractEasyCard {
    public AbstractClickableCard(final String id, final int cost, final CardType type, final CardRarity rarity, final CardTarget target, final CardColor color) {
        super(id, cost, type, rarity, target, color);
    }

    public void update() {
        super.update();
        if (CardCrawlGame.isInARun()) {
            this.clickUpdate();
        }

    }

    public void clickUpdate() {
        if (!AbstractDungeon.isScreenUp && (Boolean)HitboxRightClick.rightClicked.get(this.hb) && !AbstractDungeon.actionManager.turnHasEnded) {
            this.onRightClick();
        }

    }

    public abstract void onRightClick();
}
