package code.cards;

import code.cards.tempcards.ReadyWeapons;
import code.powers.ChargingUpPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.ModFile.makeID;
import static code.util.Wiz.applyToSelf;
import static code.util.Wiz.makeInHand;

public class ChargingUp extends AbstractEasyCard {
    public static final String ID = makeID("ChargingUp");

    public ChargingUp() {
        super(ID, 3, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        cardsToPreview = new ReadyWeapons();
        this.exhaust = true;
        baseBlock = 12;
            }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new ChargingUpPower(p));
        blck();
        addToBot((AbstractGameAction)new GainEnergyAction(3));
        addToBot(new MakeTempCardInHandAction(new ReadyWeapons(), 1));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            this.upgradeName();
            this.upgradeBlock(6);
            this.cardsToPreview.upgrade();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}