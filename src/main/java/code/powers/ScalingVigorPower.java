package code.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;

import static code.ModFile.makeID;

public class ScalingVigorPower extends AbstractEasyPower {
    public static final String POWER_ID = makeID("ScalingVigorPower");
    public static final String NAME = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).NAME;
    public static final String[] DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).DESCRIPTIONS;
    private int vigorIncrement = 2; // Starting Vigor bonus

    public ScalingVigorPower(AbstractCreature owner, int amount) {
        super(POWER_ID, NAME, PowerType.BUFF, false, owner, amount);
        updateDescription();
    }

    @Override
    public void updateDescription() {
        description = "The first Attack each turn gains +" + vigorIncrement + " Vigor. Each subsequent Attack gains +2 additional Vigor than the previous one this turn.";
    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK) {
            AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.ApplyPowerAction(owner, owner, new VigorPower(owner, vigorIncrement), vigorIncrement));
            vigorIncrement += 2; // Increase Vigor for the next Attack
        }
    }

    @Override
    public void atStartOfTurn() {
        vigorIncrement = 2; // Reset Vigor increment at the start of each turn
    }
}
