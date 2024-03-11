package code.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.BetterOnApplyPowerPower;
import java.util.Objects;
import static code.ModFile.makeID;
import static code.util.Wiz.atb;

public class DexFixation extends AbstractEasyPower implements BetterOnApplyPowerPower {
    public static final String POWER_ID = makeID("DexFixation");
    public static final String NAME = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).NAME;
    public static final String[] DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).DESCRIPTIONS;

    public DexFixation(AbstractCreature owner, int amount) {
        super(POWER_ID, NAME, PowerType.BUFF, true, owner, amount);
        addToBot(new ApplyPowerAction(owner, owner, new DexterityPower(owner, 1), 1));
    }

    @Override
    public void updateDescription() {description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public boolean betterOnApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (Objects.equals(power.ID, StrengthPower.POWER_ID)&& target == owner) {
            addToTop(new ApplyPowerAction(target, source, new DexterityPower(target, power.amount)));
            return false;
        }
        return true;
    }

    public void atEndOfRound() {
        atb(new ReducePowerAction(owner, owner, this, 1));
        }
}