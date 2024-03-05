package code.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;

import static code.ModFile.makeID;

public class ScalingBlockPower extends AbstractEasyPower {
    public static final String POWER_ID = makeID("ScalingBlock");
    public static final String NAME = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).NAME;
    public static final String[] DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).DESCRIPTIONS;
    private int blockIncrement = 1; // Starting Block bonus

    public ScalingBlockPower(AbstractCreature owner, int amount) {
        super(POWER_ID, NAME, PowerType.BUFF, false, owner, amount);
        updateDescription();
    }

    @Override
    public void updateDescription() {
        description = "The first Skill each turn grants +" + blockIncrement + " Block. Each subsequent Skill grants +1 additional Block this turn.";
    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.SKILL) {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(owner, owner, blockIncrement));
            blockIncrement += 1; // Increase Block for the next Skill
        }
    }

    @Override
    public void atStartOfTurn() {
        blockIncrement = 1; // Reset Block increment at the start of each turn
    }
}
