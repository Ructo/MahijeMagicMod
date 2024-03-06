package code.cards;

import code.cards.AbstractEasyCard;
import code.powers.ReadyWeaponsPower;
import code.powers.ChargingUpPower;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.PurgeField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

import static code.ModFile.makeID;
import static code.util.Wiz.applyToSelf;

public class ReadyWeapons extends AbstractEasyCard {
    public static final String ID = makeID("ReadyWeapons");

    public ReadyWeapons() {
        super(ID, 3, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF);
        cardsToPreview = new HyperElectroBeam();
        PurgeField.purge.set(this, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower(ChargingUpPower.POWER_ID)) {
            addToBot(new RemoveSpecificPowerAction(p, p, ChargingUpPower.POWER_ID));
        }
        applyToSelf(new ReadyWeaponsPower(p));
        AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.ApplyPowerAction(p, p, new VigorPower(p, 6), 6));
        addToBot((AbstractGameAction)new GainEnergyAction(6));
        addToBot(new MakeTempCardInHandAction(new HyperElectroBeam(), 1));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            this.upgradeName();
            this.cardsToPreview.upgrade();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}